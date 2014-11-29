package com.itszuvalex.femtocraft.core

import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.configuration.Configurable
import com.itszuvalex.femtocraft.implicits.IDImplicits._
import net.minecraft.block.Block
import net.minecraft.init.{Blocks, Items}
import net.minecraft.item.{Item, ItemStack}
import org.apache.logging.log4j.Level

import scala.collection.JavaConversions._

/**
 * Created by Chris on 9/20/2014.
 */
@Configurable object MagnetRegistry {
  private val idToStrengthMap: util.Map[Integer, Integer] = new util.TreeMap[Integer, Integer]
  @Configurable(comment = "Maximum depth to search the magnet tree.")
  var MAXIMUM_DEPTH = 3000
  @Configurable(comment = "Set true to show magnetism value in tooltip at all.")
  var showMagnetismTooltip = true
  @Configurable(comment = "Set this to true to only show magnetism value if advanced tooltips are on.")
  var magnetismTooltipIsAdvanced = false
  @Configurable
  var ORE_LODESTONE = 50
  @Configurable
  var NUGGET_LODESTONE = 10
  @Configurable
  var CHUNK_LODESTONE = 40
  @Configurable
  var ORE_IRON = 5
  @Configurable
  var INGOT_IRON = 5

  def init() {
    registerMagnetDefaults()
    registerMagnetTree()
  }

  def registerMagnet(block: Block, strength: Int): Boolean = block != null && registerMagnet(new ItemStack(block), strength)

  def registerMagnet(id: Int, strength: Int): Boolean = idToStrengthMap.put(id, strength) != null

  def registerMagnet(item: Item, strength: Int): Boolean = item != null && registerMagnet(item.itemID, strength)

  def registerMagnet(item: ItemStack, strength: Int): Boolean = item != null && registerMagnet(item.itemID, strength)

  def getMagnetStrength(block: Block): Int = if (block == null) 0 else getMagnetStrength(new ItemStack(block))

  def getMagnetStrength(item: ItemStack): Int = if (item == null) 0 else getMagnetStrength(item.itemID)

  def getMagnetStrength(item: Item): Int = if (item == null) 0 else getMagnetStrength(item.itemID)

  def getMagnetStrength(id: Int): Int = idToStrengthMap.get(id)

  def isMagnet(block: Block): Boolean = block != null && isMagnet(new ItemStack(block))

  def isMagnet(item: ItemStack): Boolean = item != null && isMagnet(item.itemID)

  def isMagnet(item: Item): Boolean = item != null && isMagnet(item.itemID)

  def isMagnet(id: Int): Boolean = idToStrengthMap.containsKey(id)

  private def registerMagnetTree() {
    Femtocraft.log(Level.INFO, "Registering all magnets.")
    val recipes = Femtocraft.recipeManager.assemblyRecipes.getAllRecipes
    var changed = true
    var depth = 0
    while (changed && depth < MAXIMUM_DEPTH) {
      changed = false
      for (recipe <- recipes) {
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

  private def registerMagnetDefaults() {
    registerMagnet(Blocks.iron_ore, ORE_IRON)
    registerMagnet(Items.iron_ingot, INGOT_IRON)
    registerMagnet(Femtocraft.blockOreLodestone, ORE_LODESTONE)
    registerMagnet(Femtocraft.itemNuggetLodestone, NUGGET_LODESTONE)
    registerMagnet(Femtocraft.itemChunkLodestone, CHUNK_LODESTONE)
  }
}

