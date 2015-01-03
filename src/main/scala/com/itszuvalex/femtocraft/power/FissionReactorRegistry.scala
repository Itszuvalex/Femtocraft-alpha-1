package com.itszuvalex.femtocraft.power

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.implicits.IDImplicits._
import net.minecraft.init.{Blocks, Items}
import net.minecraft.item.ItemStack

import scala.collection.mutable

/**
 * Created by Chris on 8/19/2014.
 */
object FissionReactorRegistry {
  private val thoriumMap = new mutable.HashMap[Int, FissionReactorReagent]
  private val saltMap    = new mutable.HashMap[Int, FissionReactorReagent]
  private val heatMap    = new mutable.HashMap[Int, FissionReactorReagent]

  def init() {
    addFissionReactorHeatSource(new ItemStack(Blocks.snow), 0, -1)
    addFissionReactorHeatSource(new ItemStack(Blocks.ice), 0, -10)
    addFissionReactorHeatSource(new ItemStack(Items.lava_bucket), 0, 100)
    addFissionReactorHeatSource(new ItemStack(Items.fire_charge), 0, 20)
    addFissionReactorHeatSource(new ItemStack(Items.water_bucket), 0, -5)
    addFissionReactorSaltSource(new ItemStack(Femtocraft.itemIngotThFaSalt), 1000, 10)
    addFissionReactorThoriumSource(new ItemStack(Femtocraft.itemIngotThorium), 1000, 20)
  }

  def addFissionReactorThoriumSource(item: ItemStack, amount: Int, temp: Float) {
    addFissionReactorThoriumSource(new FissionReactorReagent(item, amount, temp))
  }

  def addFissionReactorThoriumSource(reagent: FissionReactorReagent) {
    thoriumMap.put(reagent.item.getItem.itemID, reagent)
  }

  def addFissionReactorSaltSource(item: ItemStack, amount: Int, temp: Float) {
    addFissionReactorSaltSource(new FissionReactorReagent(item, amount, temp))
  }

  def addFissionReactorSaltSource(reagent: FissionReactorReagent) {
    saltMap.put(reagent.item.getItem.itemID, reagent)
  }

  def addFissionReactorHeatSource(item: ItemStack, amount: Int, heat: Float) {
    addFissionReactorHeatSource(new FissionReactorReagent(item, amount, heat))
  }

  def addFissionReactorHeatSource(reagent: FissionReactorReagent) {
    heatMap.put(reagent.item.getItem.itemID, reagent)
  }

  def getThoriumSource(item: ItemStack): FissionReactorReagent = thoriumMap.get(item.getItem.itemID).orNull

  def getSaltSource(item: ItemStack): FissionReactorReagent = saltMap.get(item.getItem.itemID).orNull

  def getHeatSource(item: ItemStack): FissionReactorReagent = heatMap.get(item.getItem.itemID).orNull

  class FissionReactorReagent(val item: ItemStack, val amount: Int, val temp: Float)

}

