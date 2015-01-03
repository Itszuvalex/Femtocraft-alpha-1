package com.itszuvalex.femtocraft.api.items

import com.itszuvalex.femtocraft.api.core.Configurable
import com.itszuvalex.femtocraft.api.industry.AssemblerRecipe
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.IIcon


@Configurable object IAssemblerSchematic {
  val INFINITE_USE_DAMAGE      : Int   = -1
  /**
   * Set to the correct value by Femtocraft as it loads.
   */
  var placeholderIcon          : IIcon = null
  @Configurable(comment = "How many uses does an infinite use schematic count for when calculating mass costs? " + "(This is a float to allow finer tuning - it will be cast to integer where it matters.")
  var infiniteUseMassMultiplier: Float = 200
}

/**
 * @author Itszuvalex
 *
 *         Interface for Items that wish to be able to be used as Assembler Schematics in the encoder or reconstructor.
 */
@Configurable trait IAssemblerSchematic extends Item {

  /**
   * @param stack The IAssemblerSchematic stack
   * @return The AssemblerRecipe represented with this Schematic
   */
  def getRecipe(stack: ItemStack): AssemblerRecipe

  /**
   *
   * @param stack The IAssemblerSchematic stack
   * @return Whether this Schematic has has a Recipe encoded or not.
   */
  def hasRecipe(stack: ItemStack): Boolean

  /**
   * @param stack  ItemStack to encode recipe into
   * @param recipe Recipe to encode into itemStack
   * @return True if recipe successfully encoded, false for any other reason (itemStack already has recipe?, failed to
   *         read NBT, etc.)
   */
  def setRecipe(stack: ItemStack, recipe: AssemblerRecipe): Boolean

  /**
   * @param stack ItemStack to find uses remaining of - generally is remaining damage
   * @return How many uses remain, or -1 if infinite.
   */
  def usesRemaining(stack: ItemStack): Int

  /**
   * @param recipe Recipe that will be imprinted upon this Schematic
   * @return Amount of fluidMass required to create this recipe, in mB.
   */
  def massRequired(recipe: AssemblerRecipe): Int

  /**
   * @param stack - ItemStack of this schematic.
   * @return True if this schematic is still valid, false if schematic is no longer valid (i.e. damage).
   */
  def onAssemble(stack: ItemStack): Boolean

  /**
   * @param stack ItemStack that is breaking down
   * @return ItemStack that takes the place of @stack, if onAssemble() returns false;
   */
  def resultOfBreakdown(stack: ItemStack): ItemStack
}
