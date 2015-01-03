package com.itszuvalex.femtocraft.core

import java.io.File
import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.core.Configurable
import com.itszuvalex.femtocraft.configuration.{AutoGenConfig, MagnetismXMLLoader, XMLMagnetismMappings}
import com.itszuvalex.femtocraft.api.implicits.IDImplicits._
import com.itszuvalex.femtocraft.api.utils.FemtocraftFileUtils
import net.minecraft.block.Block
import net.minecraft.init.{Blocks, Items}
import net.minecraft.item.{Item, ItemStack}
import org.apache.logging.log4j.Level

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

/**
 * Created by Chris on 9/20/2014.
 */
@Configurable object MagnetRegistry {

  val NUGGET_LODESTONE           = 10
  @Configurable(comment = "Maximum depth to search the magnet tree.")
  val MAXIMUM_DEPTH              = 3000
  @Configurable(comment = "Set true to show magnetism value in tooltip at all.")
  val showMagnetismTooltip       = true
  @Configurable(comment = "Set this to true to only show magnetism value if advanced tooltips are on.")
  val magnetismTooltipIsAdvanced = false
  val ORE_LODESTONE              = 50
  val CHUNK_LODESTONE            = 40
  val ORE_IRON                   = 5
  val INGOT_IRON                 = 5
  private val idToStrengthMap: util.Map[Integer, Integer] = new util.TreeMap[Integer, Integer]

  def init() {
    registerMagnetMappings()
    registerMagnetTree()
  }

  def registerMagnet(block: Block, strength: Int): Boolean = block != null && registerMagnet(new ItemStack(block), strength)

  def registerMagnet(item: ItemStack, strength: Int): Boolean = item != null && registerMagnet(item.itemID, strength)

  def registerMagnet(id: Int, strength: Int): Boolean = idToStrengthMap.put(id, strength) != null

  def registerMagnet(item: Item, strength: Int): Boolean = item != null && registerMagnet(item.itemID, strength)

  def getMagnetStrength(block: Block): Int = if (block == null) 0 else getMagnetStrength(new ItemStack(block))

  def getMagnetStrength(item: ItemStack): Int = if (item == null) 0 else getMagnetStrength(item.itemID)

  def getMagnetStrength(item: Item): Int = if (item == null) 0 else getMagnetStrength(item.itemID)

  def getMagnetStrength(id: Int): Int = idToStrengthMap.get(id)

  def isMagnet(block: Block): Boolean = block != null && isMagnet(new ItemStack(block))

  def isMagnet(item: ItemStack): Boolean = item != null && isMagnet(item.itemID)

  def isMagnet(id: Int): Boolean = idToStrengthMap.containsKey(id)

  def isMagnet(item: Item): Boolean = item != null && isMagnet(item.itemID)

  private def registerMagnetTree() {
    Femtocraft.log(Level.INFO, "Registering all magnets.")
    val recipes = Femtocraft.recipeManager.assemblyRecipes.getAllRecipes
    var changed = true
    var depth = 0
    while (changed && depth < MAXIMUM_DEPTH) {
      changed = false
      recipes.view.foreach { recipe =>
        val prevStrength = if (isMagnet(recipe.output)) getMagnetStrength(recipe.output) else 0
        var strength = 0
        for (input <- recipe.input) {
          strength += (if (isMagnet(input)) getMagnetStrength(input) else 0)
        }
        if (strength > 0) {
          if ((strength / recipe.output.stackSize) != prevStrength) {
            registerMagnet(recipe.output, strength / recipe.output.stackSize)
            changed = true
          }
        }
                           }
      depth += 1
    }
    Femtocraft.log(Level.INFO, "Finished registering magnets.  Total depth was " + depth + ".")
  }

  private def registerMagnetMappings(): Unit = {
    val customMappings = new MagnetismXMLLoader(new File(FemtocraftFileUtils.customConfigPath + "Magnetism/")).loadItems()
    customMappings.view.foreach(registerMapping)

    val recipes = new XMLMagnetismMappings(new File(FemtocraftFileUtils.autogenConfigPath, "MagnetismMappings.xml"))
    if (!AutoGenConfig.shouldRegenFile(recipes.file) && recipes.initialized) {
      recipes.loadCustomRecipes().view.foreach(registerMapping)
    }
    else {
      val defaults = getMagnetDefaults
      recipes.seedInitialRecipes(defaults)
      defaults.view.foreach(registerMapping)
      AutoGenConfig.markFileRegenerated(recipes.file)
    }
  }

  private def registerMapping(mapping: (ItemStack, Int)) = registerMagnet(mapping._1, mapping._2)

  private def getMagnetDefaults: ArrayBuffer[(ItemStack, Int)] = {
    val ret = new ArrayBuffer[(ItemStack, Int)]()
    ret.append((new ItemStack(Blocks.iron_ore), ORE_IRON))
    ret append ((new ItemStack(Items.iron_ingot), INGOT_IRON))
    ret.append((new ItemStack(Femtocraft.blockOreLodestone), ORE_LODESTONE))
    ret.append((new ItemStack(Femtocraft.itemNuggetLodestone), NUGGET_LODESTONE))
    ret.append((new ItemStack(Femtocraft.itemChunkLodestone), CHUNK_LODESTONE))
    ret
  }
}

