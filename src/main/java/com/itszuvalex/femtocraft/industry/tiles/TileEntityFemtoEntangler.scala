/*
 * ******************************************************************************
 *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 *  * Itszuvalex@gmail.com
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *  *****************************************************************************
 */
package com.itszuvalex.femtocraft.industry.tiles

import com.itszuvalex.femtocraft.FemtocraftGuiConstants
import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.core.Configurable
import com.itszuvalex.femtocraft.api.power.PowerContainer
import com.itszuvalex.femtocraft.industry.tiles.TileEntityFemtoEntangler._

@Configurable object TileEntityFemtoEntangler {
  val outputSlot               = 13
  val inventorySize            = 14
  @Configurable(comment = "Recipe tech level.")
  val RECIPE_TECH_LEVEL        = EnumTechLevel.FEMTO
  @Configurable(comment = "Power tech level.")
  val TECH_LEVEL               = EnumTechLevel.FEMTO
  @Configurable(comment = "Power storage maximum.")
  val POWER_STORAGE            = 100000
  @Configurable(comment = "Power per item to begin processing.")
  val POWER_TO_COOK            = 160
  @Configurable(comment = "Multiplier for tick processing time of Dimensional Recipes.")
  val TICKS_TO_COOK_MULTIPLIER = .5f
}

@Configurable class TileEntityFemtoEntangler extends TileEntityBaseEntityNanoEnmesher {
  inventory.setInventorySize(inventorySize)

  override def defaultContainer = new PowerContainer(TECH_LEVEL, POWER_STORAGE)

  override def getGuiID = FemtocraftGuiConstants.FemtoEntanglerGuiID

  protected override def getTickMultiplier = TICKS_TO_COOK_MULTIPLIER

  protected override def getOutputSlotIndex = outputSlot

  protected override def getPowerToCook = POWER_TO_COOK

  protected override def getTechLevel = RECIPE_TECH_LEVEL
}