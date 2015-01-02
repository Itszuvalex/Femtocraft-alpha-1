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

import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.core.Configurable
import com.itszuvalex.femtocraft.api.power.PowerContainer
import com.itszuvalex.femtocraft.industry.tiles.TileEntityFemtoCoagulator._
import com.itszuvalex.femtocraft.research.FemtocraftTechnologies
import com.itszuvalex.femtocraft.{Femtocraft, FemtocraftGuiConstants}

object TileEntityFemtoCoagulator {
  @Configurable(comment = "Assembler recipe tech level maximum.")                     val ASSEMBLER_TECH_LEVEL  = EnumTechLevel.FEMTO
  @Configurable(comment = "Power tech level.")                                        val TECH_LEVEL            = EnumTechLevel.FEMTO
  @Configurable(comment = "Power storage maximum.")                                   val POWER_STORAGE         = 100000
  @Configurable(comment = "Maximum number of items allowed at a time, pre upgrade.")  val MAX_SMELT_PRE         = 32
  @Configurable(comment = "Ticks required to process, pre upgrade.")                  val TICKS_TO_COOK_PRE     = 20
  @Configurable(comment = "Power per item to begin processing, pre upgrade.")         val POWER_TO_COOK_PRE     = 160
  @Configurable(comment = "Name of technology for max smelt upgrade.")                val MAX_SMELT_UPGRADE     = FemtocraftTechnologies.MULTI_DIMENSIONAL_INDUSTRY
  @Configurable(comment = "Name of technology for ticks to cook upgrade.")            val TICKS_TO_COOK_UPGRADE = FemtocraftTechnologies.CAUSALITY_SINGULARITY
  @Configurable(comment = "Name of technology for power to cook upgrade.")            val POWER_TO_COOK_UPGRADE = ""
  @Configurable(comment = "Maximum number of items allowed at a time, post upgrade.") val MAX_SMELT_POST        = 64
  @Configurable(comment = "Ticks required to process, post upgrade.")                 val TICKS_TO_COOK_POST    = 0
  @Configurable(comment = "Power per item to begin processing, post upgrade.")        val POWER_TO_COOK_POST    = 160
}

@Configurable class TileEntityFemtoCoagulator extends TileEntityBaseEntityMicroReconstructor {
  override def defaultContainer = new PowerContainer(TECH_LEVEL, POWER_STORAGE)

  override def getGuiID = FemtocraftGuiConstants.FemtoCoagulatorGuiID

  protected override def getTicksToCook = if (Femtocraft.researchManager.hasPlayerResearchedTechnology(getOwner, TICKS_TO_COOK_UPGRADE)) TICKS_TO_COOK_POST else TICKS_TO_COOK_PRE

  protected override def getPowerToCook = if (Femtocraft.researchManager.hasPlayerResearchedTechnology(getOwner, POWER_TO_COOK_UPGRADE)) POWER_TO_COOK_POST else POWER_TO_COOK_PRE

  protected override def getAssemblerTech = ASSEMBLER_TECH_LEVEL

  protected override def getMaxSimultaneousSmelt = if (Femtocraft.researchManager.hasPlayerResearchedTechnology(getOwner, MAX_SMELT_UPGRADE)) MAX_SMELT_POST else MAX_SMELT_PRE
}
