//package com.itszuvalex.femtocraft.industry.fabrication.resources
//
//import java.util
//import java.util.UUID
//
//import com.itszuvalex.femtocraft.industry.fabrication.SchematicWrapper
//import com.itszuvalex.femtocraft.industry.fabrication.traits.{ICraftingRecipes, IProvider, IResource}
//
//import scala.collection.JavaConversions._
//import scala.collection.mutable.ArrayBuffer
//
///**
// * Created by Itszuvalex on 1/20/15.
// */
//class ResourceAssemblerSchematics(private val recipe: Array[SchematicWrapper]) extends ICraftingRecipes[Array[SchematicWrapper], Array[Int]] {
//  /**
//   *
//   * @return List of resources generated from this crafting node.
//   */
//  override def result: util.Collection[IResource] = {
//    val ar = recipe.schematic.getRecipe(recipe.itemStack)
//    if (ar != null)
//      ArrayBuffer(new ResourceItemStack(ar.output))
//    else ArrayBuffer()
//  }
//
//
//  /**
//   *
//   * @param other
//   * @return True if this is equivalent to other, and contains >= the amount of resources of other.
//   */
//  override def contains(other: IResource[Array[SchematicWrapper], Array[Int]]): Boolean = ???
//
//  /**
//   *
//   * @param other Amount of this reource to consume.
//   * @return IResource containing the amount utilized.
//   */
//  override def utilize(other: IResource[Array[SchematicWrapper], Array[Int]]): ResourceAssemblerSchematics = ???
//
//  /**
//   *
//   * @param other
//   * @return True if this is equivalent with resource.  Example, power storage with same tier, item stack with same id and damage, etc.
//   */
//  override def equivalent(other: IResource[Array[SchematicWrapper], Array[Int]]): Boolean = ???
//
//  /**
//   *
//   * @return List of resources required to perform this crafting job.
//   */
//  override def prerequisites: util.Collection[IResource] = {
//    val ar = recipe.schematic.getRecipe(recipe.itemStack)
//    if (ar != null)
//      ar.input.filterNot(_ == null).map(new ResourceItemStack(_)).toBuffer
//    else ArrayBuffer()
//  }
//
//
//  /**
//   *
//   * @param resource Amount of this resource to use.
//   * @return IResource containing this amount, if possible.
//   */
//  override def utilize(resource: Int): ResourceAssemblerSchematics = ???
//
//  /**
//   *
//   * @return Get the contained resource, directly.
//   */
//  override def resource = recipe
//
//  /**
//   *
//   * @return Get a measurement of how much of this resource is present.
//   */
//  override def amount = recipe.schematic.usesRemaining(recipe.itemStack)
//
//  override def resourceName = "Assembler Recipe" + (if (recipe.schematic.getRecipe(recipe.itemStack) != null) " " + recipe.schematic.getRecipe(recipe.itemStack).output.getDisplayName else "")
//}
