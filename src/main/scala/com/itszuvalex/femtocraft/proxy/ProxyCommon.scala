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
package com.itszuvalex.femtocraft.proxy

import com.itszuvalex.femtocraft.core.tiles.{TileEntityDimensionalTear, TileEntityOreLodestone}
import com.itszuvalex.femtocraft.industry.tiles._
import com.itszuvalex.femtocraft.power.plasma.TileEntityPlasma
import com.itszuvalex.femtocraft.power.tiles._
import com.itszuvalex.femtocraft.research.tiles.{TileEntityResearchComputer, TileEntityResearchConsole}
import com.itszuvalex.femtocraft.transport.items.tiles.TileEntityVacuumTube
import com.itszuvalex.femtocraft.transport.liquids.tiles.TileEntitySuctionPipe
import com.itszuvalex.femtocraft.utility.tiles.{TileEntitySpatialAlternator, TileEntitySpatialCage}
import cpw.mods.fml.common.registry.GameRegistry

class ProxyCommon {
  def registerRendering() {
  }

  def registerTileEntities() {
    GameRegistry.registerTileEntity(classOf[TileEntityOreLodestone], "TileEntityOreLodestone")
    GameRegistry.registerTileEntity(classOf[TileEntityResearchComputer], "TileEntityResearchComputer")
    GameRegistry.registerTileEntity(classOf[TileEntityResearchConsole], "TileEntityResearchConsole")
    GameRegistry.registerTileEntity(classOf[TileEntityPowerProducerTest], "TileEntityPowerProducerTest")
    GameRegistry.registerTileEntity(classOf[TileEntityPowerConsumerTest], "TileEntityPowerConsumerTest")
    GameRegistry.registerTileEntity(classOf[TileEntityBaseEntityMicroFurnace], "TileEntityMicroFurnace")
    GameRegistry.registerTileEntity(classOf[TileEntityBaseEntityMicroDeconstructor], "TileEntityMicroDeconstructor")
    GameRegistry.registerTileEntity(classOf[TileEntityBaseEntityMicroReconstructor], "TileEntityMicroReconstructor")
    GameRegistry.registerTileEntity(classOf[TileEntityEncoder], "TileEntityEncoder")
    GameRegistry.registerTileEntity(classOf[TileEntityNanoInnervator], "TileEntityNanoInnervator")
    GameRegistry.registerTileEntity(classOf[TileEntityNanoDismantler], "TileEntityNanoDeconstructor")
    GameRegistry.registerTileEntity(classOf[TileEntityNanoFabricator], "TileEntityNanoFabricator")
    GameRegistry.registerTileEntity(classOf[TileEntityBaseEntityNanoEnmesher], "TileEntityNanoEnmesher")
    GameRegistry.registerTileEntity(classOf[TileEntityBaseEntityNanoHorologe], "TileEntityNanoHorologe")
    GameRegistry.registerTileEntity(classOf[TileEntityFemtoImpulser], "TileEntityFemtoImpulser")
    GameRegistry.registerTileEntity(classOf[TileEntityFemtoRepurposer], "TileEntityFemtoRepurposer")
    GameRegistry.registerTileEntity(classOf[TileEntityFemtoCoagulator], "TileEntityFemtoCoagulator")
    GameRegistry.registerTileEntity(classOf[TileEntityFemtoEntangler], "TileEntityFemtoEntangler")
    GameRegistry.registerTileEntity(classOf[TileEntityFemtoChronoshifter], "TileEntityFemtoChronoshifter")
    GameRegistry.registerTileEntity(classOf[TileEntityMicroCable], "TileEntityMicroCable")
    GameRegistry.registerTileEntity(classOf[TileEntityNanoCable], "TileEntityNanoCable")
    GameRegistry.registerTileEntity(classOf[TileEntityFemtoCable], "TileEntityFemtoCable")
    GameRegistry.registerTileEntity(classOf[TileEntityMicroCube], "TileEntityMicroCube")
    GameRegistry.registerTileEntity(classOf[TileEntityNanoCubeFrame], "TileEntityNanoCubeFrame")
    GameRegistry.registerTileEntity(classOf[TileEntityNanoCubePort], "TileEntityNanoCubePort")
    GameRegistry.registerTileEntity(classOf[TileEntityFemtoCubeFrame], "TileEntityFemtoCubeFrame")
    GameRegistry.registerTileEntity(classOf[TileEntityFemtoCubeChassis], "TileEntityFemtoCubeChassis")
    GameRegistry.registerTileEntity(classOf[TileEntityFemtoCubePort], "TileEntityFemtoCubePort")
    GameRegistry.registerTileEntity(classOf[TileEntityCryoEndothermalChargingBase], "TileEntityCryoEndothermalChargingBase")
    GameRegistry.registerTileEntity(classOf[TileEntityCryoEndothermalChargingCoil], "TileEntityCryoEndothermalChargingCoil")
    GameRegistry.registerTileEntity(classOf[TileEntityNanoFissionReactorCore], "TileEntityNanoFissionReactorCore")
    GameRegistry.registerTileEntity(classOf[TileEntityNanoFissionReactorHousing], "TileEntityNanoFissionReactorHousing")
    GameRegistry.registerTileEntity(classOf[TileEntityMagnetohydrodynamicGenerator], "TileEntityMagnetohydrodynamicGenerator")
    GameRegistry.registerTileEntity(classOf[TileEntitySteamGenerator], "TileEntitySteamGenerator")
    GameRegistry.registerTileEntity(classOf[TileEntityDecontaminationChamber], "TileEntityDecontaminationChamber")
    GameRegistry.registerTileEntity(classOf[TileEntityPhlegethonTunnelCore], "TileEntityPhlegethonTunnelCore")
    GameRegistry.registerTileEntity(classOf[TileEntityPhlegethonTunnelFrame], "TileEntityPhlegethonTunnelFrame")
    GameRegistry.registerTileEntity(classOf[TileEntitySisyphusStabilizer], "TileEntitySisyphusStabilizer")
    GameRegistry.registerTileEntity(classOf[TileEntityFemtoStellaratorCore], "TileEntityFemtoStellaratorCore")
    GameRegistry.registerTileEntity(classOf[TileEntityFemtoStellaratorOpticalMaser], "TileEntityFemtoStellaratorOpticalMaser")
    GameRegistry.registerTileEntity(classOf[TileEntityFemtoStellaratorHousing], "TileEntityFemtoStellaratorHousing")
    GameRegistry.registerTileEntity(classOf[TileEntityFemtoStellaratorFocus], "TileEntityFemtoStellaratorFocus")
    GameRegistry.registerTileEntity(classOf[TileEntityVacuumTube], "TileEntityVacuumTube")
    GameRegistry.registerTileEntity(classOf[TileEntityAtmosphericChargingBase], "TileEntityAtmosphericChargingBase")
    GameRegistry.registerTileEntity(classOf[TileEntityMagneticInductionGenerator], "TileEntityMagneticInductionGenerator")
    GameRegistry.registerTileEntity(classOf[TileEntitySuctionPipe], "TileEntitySuctionPipe")
    GameRegistry.registerTileEntity(classOf[TileEntityOrbitalEqualizer], "TileEntityOrbitalEqualizer")
    GameRegistry.registerTileEntity(classOf[TileEntityNullEqualizer], "TileEntityNullEqualizer")
    GameRegistry.registerTileEntity(classOf[TileEntityPlasma], "TileEntityPlasma")
    GameRegistry.registerTileEntity(classOf[TileEntityPlasmaConduit], "TileEntityPlasmaConduit")

    GameRegistry.registerTileEntity(classOf[TileEntityLaser], "TileEntityLaser")
    GameRegistry.registerTileEntity(classOf[TileEntityPhotonEmitter], "TileEntityPhotonEmitter")

    GameRegistry.registerTileEntity(classOf[TileEntitySpatialAlternator], "TileEntitySpatialAlternator")
    GameRegistry.registerTileEntity(classOf[TileEntitySpatialCage], "TileEntitySpatialCage")

    GameRegistry.registerTileEntity(classOf[TileEntityDimensionalTear], "TileEntityDimensionalTear")
  }

  def registerBlockRenderers() {
  }

  def registerTickHandlers() {
  }
}
