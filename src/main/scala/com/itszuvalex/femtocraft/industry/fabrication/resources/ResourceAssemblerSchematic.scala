package com.itszuvalex.femtocraft.industry.fabrication.resources

import java.util

import com.itszuvalex.femtocraft.industry.fabrication.SchematicWrapper
import com.itszuvalex.femtocraft.industry.fabrication.traits.{ICraftingRecipe, IResource}

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

/**
 * Created by Itszuvalex on 1/20/15.
 */
class ResourceAssemblerSchematic(private val recipe: SchematicWrapper) extends ICraftingRecipe[SchematicWrapper, Int] {
  /**
   *
   * @return List of resources generated from this crafting node.
   */
  override def result: util.Collection[IResource] = {
    val ar = recipe.schematic.getRecipe(recipe.itemStack)
    if (ar != null)
      ArrayBuffer(new ResourceItemStack(ar.output))
    else ArrayBuffer()
  }


  /**
   *
   * @return List of resources required to perform this crafting job.
   */
  override def prerequisites: util.Collection[IResource] = {
    val ar = recipe.schematic.getRecipe(recipe.itemStack)
    if (ar != null)
      ar.input.filterNot(_ == null).map(new ResourceItemStack(_)).toBuffer
    else ArrayBuffer()
  }


  /**
   *
   * @param resource Amount of this resource to use.
   * @return IResource containing this amount, if possible.
   */
  override def utilize(resource: Int): ResourceAssemblerSchematic = ???

  /**
   *
   * @return Get the contained resource, directly.
   */
  override def resource = recipe

  /**
   *
   * @return Get a measurement of how much of this resource is present.
   */
  override def amount = recipe.schematic.usesRemaining(recipe.itemStack)

  override def resourceName = "Assembler Recipe" + (if (recipe.schematic.getRecipe(recipe.itemStack) != null) " " + recipe.schematic.getRecipe(recipe.itemStack).output.getDisplayName else "")
}
