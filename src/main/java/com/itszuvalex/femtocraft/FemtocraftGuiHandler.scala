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
package com.itszuvalex.femtocraft

import com.itszuvalex.femtocraft.industry.containers._
import com.itszuvalex.femtocraft.industry.gui._
import com.itszuvalex.femtocraft.industry.tiles._
import com.itszuvalex.femtocraft.power.containers._
import com.itszuvalex.femtocraft.power.gui._
import com.itszuvalex.femtocraft.power.tiles._
import com.itszuvalex.femtocraft.research.containers.ContainerResearchConsole
import com.itszuvalex.femtocraft.research.gui.{GuiResearch, GuiResearchConsole}
import com.itszuvalex.femtocraft.research.tiles.TileEntityResearchConsole
import cpw.mods.fml.common.network.IGuiHandler
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

object FemtocraftGuiHandler {
  val ResearchComputerGuiID                 = 0
  val ResearchConsoleGuiID                  = 1
  val EncoderGuiID                          = 4
  val MicroFurnaceGuiID                     = 5
  val MicroDeconstructorGuiID               = 6
  val MicroReconstructorGuiID               = 7
  val NanoInnervatorGuiID                   = 8
  val NanoDismantlerGuiID                   = 9
  val NanoFabricatorGuiID                   = 10
  val NanoHorologeGuiID                     = 11
  val NanoEnmesherGuiID                     = 12
  val FemtoImpulserGuiID                    = 13
  val FemtoRepurposerGuiID                  = 14
  val FemtoCoagulatorGuiID                  = 15
  val FemtoChronoshifterGuiID               = 16
  val FemtoEntanglerGuiID                   = 17
  val MicroCubeGuiID                        = 30
  val MicroEngineGuiID                      = 31
  val NanoCubeGuiID                         = 35
  val NanoFissionReactorGuiID               = 36
  val NanoMagnetohydrodynamicGeneratorGuiID = 37
  val FemtoCubeGuiID                        = 40
  val PhlegethonTunnelGuiID                 = 41
}

class FemtocraftGuiHandler extends IGuiHandler {
  override def getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = {
    val te: TileEntity = world.getTileEntity(x, y, z)
    (ID, te) match {
      case (FemtocraftGuiHandler.ResearchConsoleGuiID, te: TileEntityResearchConsole)                               => new ContainerResearchConsole(player.inventory, te)
      case (FemtocraftGuiHandler.EncoderGuiID, te: TileEntityEncoder)                                               => new ContainerEncoder(player, player.inventory, te)
      case (FemtocraftGuiHandler.MicroFurnaceGuiID, te: TileEntityBaseEntityMicroFurnace)                           => new ContainerMicroFurnace(player, player.inventory, te)
      case (FemtocraftGuiHandler.MicroDeconstructorGuiID, te: TileEntityBaseEntityMicroDeconstructor)               => new ContainerMicroDeconstructor(player, player.inventory, te)
      case (FemtocraftGuiHandler.MicroReconstructorGuiID, te: TileEntityBaseEntityMicroReconstructor)               => new ContainerMicroReconstructor(player, player.inventory, te)
      case (FemtocraftGuiHandler.NanoInnervatorGuiID, te: TileEntityNanoInnervator)                                 => new ContainerNanoInnervator(player, player.inventory, te)
      case (FemtocraftGuiHandler.NanoDismantlerGuiID, te: TileEntityNanoDismantler)                                 => new ContainerNanoDismantler(player, player.inventory, te)
      case (FemtocraftGuiHandler.NanoFabricatorGuiID, te: TileEntityNanoFabricator)                                 => new ContainerNanoFabricator(player, player.inventory, te)
      case (FemtocraftGuiHandler.NanoHorologeGuiID, te: TileEntityBaseEntityNanoHorologe)                           => new ContainerNanoHorologe(player, player.inventory, te)
      case (FemtocraftGuiHandler.NanoEnmesherGuiID, te: TileEntityBaseEntityNanoEnmesher)                           => new ContainerNanoEnmesher(player, player.inventory, te)
      case (FemtocraftGuiHandler.FemtoImpulserGuiID, te: TileEntityFemtoImpulser)                                   => new ContainerFemtoImpulser(player, player.inventory, te)
      case (FemtocraftGuiHandler.FemtoRepurposerGuiID, te: TileEntityFemtoRepurposer)                               => new ContainerFemtoRepurposer(player, player.inventory, te)
      case (FemtocraftGuiHandler.FemtoCoagulatorGuiID, te: TileEntityFemtoCoagulator)                               => new ContainerFemtoCoagulator(player, player.inventory, te)
      case (FemtocraftGuiHandler.FemtoChronoshifterGuiID, te: TileEntityFemtoChronoshifter)                         => new ContainerFemtoChronoshifter(player, player.inventory, te)
      case (FemtocraftGuiHandler.FemtoEntanglerGuiID, te: TileEntityFemtoEntangler)                                 => new ContainerFemtoEntangler(player, player.inventory, te)
      case (FemtocraftGuiHandler.MicroCubeGuiID, te: TileEntityMicroCube)                                           => new ContainerMicroCube(te)
      case (FemtocraftGuiHandler.MicroEngineGuiID, _)                                                               => null
      case (FemtocraftGuiHandler.NanoCubeGuiID, te: TileEntityNanoCubePort)                                         => new ContainerNanoCube(te)
      case (FemtocraftGuiHandler.NanoFissionReactorGuiID, te: TileEntityNanoFissionReactorCore)                     => new ContainerNanoFissionReactor(player, player.inventory, te)
      case (FemtocraftGuiHandler.NanoMagnetohydrodynamicGeneratorGuiID, te: TileEntityMagnetohydrodynamicGenerator) => new ContainerMagnetoHydrodynamicGenerator(te)
      case (FemtocraftGuiHandler.FemtoCubeGuiID, te: TileEntityFemtoCubePort)                                       => new ContainerFemtoCube(te)
      case (FemtocraftGuiHandler.PhlegethonTunnelGuiID, te: TileEntityPhlegethonTunnelCore)                         => new ContainerPhlegethonTunnel(player, player.inventory, te)
      case (_, _)                                                                                                   => null
    }
  }

  override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = {
    val te: TileEntity = world.getTileEntity(x, y, z)
    (ID, te) match {
      case (FemtocraftGuiHandler.ResearchComputerGuiID, _)                                                          => new GuiResearch(player.getCommandSenderName)
      case (FemtocraftGuiHandler.ResearchConsoleGuiID, te: TileEntityResearchConsole)                               => new GuiResearchConsole(player.inventory, te)
      case (FemtocraftGuiHandler.EncoderGuiID, te: TileEntityEncoder)                                               => new GuiEncoder(player, player.inventory, te)
      case (FemtocraftGuiHandler.MicroFurnaceGuiID, te: TileEntityBaseEntityMicroFurnace)                           => new GuiMicroFurnace(player, player.inventory, te)
      case (FemtocraftGuiHandler.MicroDeconstructorGuiID, te: TileEntityBaseEntityMicroDeconstructor)               => new GuiMicroDeconstructor(player, player.inventory, te)
      case (FemtocraftGuiHandler.MicroReconstructorGuiID, te: TileEntityBaseEntityMicroReconstructor)               => new GuiMicroReconstructor(player, player.inventory, te)
      case (FemtocraftGuiHandler.NanoInnervatorGuiID, te: TileEntityNanoInnervator)                                 => new GuiNanoInnervator(player, player.inventory, te)
      case (FemtocraftGuiHandler.NanoDismantlerGuiID, te: TileEntityNanoDismantler)                                 => new GuiNanoDismantler(player, player.inventory, te)
      case (FemtocraftGuiHandler.NanoFabricatorGuiID, te: TileEntityNanoFabricator)                                 => new GuiNanoFabricator(player, player.inventory, te)
      case (FemtocraftGuiHandler.NanoHorologeGuiID, te: TileEntityBaseEntityNanoHorologe)                           => new GuiNanoHorologe(player, player.inventory, te)
      case (FemtocraftGuiHandler.NanoEnmesherGuiID, te: TileEntityBaseEntityNanoEnmesher)                           => new GuiNanoEnmesher(player, player.inventory, te)
      case (FemtocraftGuiHandler.FemtoImpulserGuiID, te: TileEntityFemtoImpulser)                                   => new GuiFemtoImpulser(player, player.inventory, te)
      case (FemtocraftGuiHandler.FemtoRepurposerGuiID, te: TileEntityFemtoRepurposer)                               => new GuiFemtoRepurposer(player, player.inventory, te)
      case (FemtocraftGuiHandler.FemtoCoagulatorGuiID, te: TileEntityFemtoCoagulator)                               => new GuiFemtoCoagulator(player, player.inventory, te)
      case (FemtocraftGuiHandler.FemtoChronoshifterGuiID, te: TileEntityFemtoChronoshifter)                         => new GuiFemtoChronoshifter(player, player.inventory, te)
      case (FemtocraftGuiHandler.FemtoEntanglerGuiID, te: TileEntityFemtoEntangler)                                 => new GuiFemtoEntangler(player, player.inventory, te)
      case (FemtocraftGuiHandler.MicroCubeGuiID, te: TileEntityMicroCube)                                           => new GuiMicroCube(te)
      case (FemtocraftGuiHandler.MicroEngineGuiID, _)                                                               => null
      case (FemtocraftGuiHandler.NanoFissionReactorGuiID, te: TileEntityNanoFissionReactorCore)                     => new GuiNanoFissionReactor(player, player.inventory, te)
      case (FemtocraftGuiHandler.NanoMagnetohydrodynamicGeneratorGuiID, te: TileEntityMagnetohydrodynamicGenerator) => new GuiMagnetohydrodynamicGenerator(te)
      case (FemtocraftGuiHandler.NanoCubeGuiID, te: TileEntityNanoCubePort)                                         => new GuiNanoCube(te)
      case (FemtocraftGuiHandler.FemtoCubeGuiID, te: TileEntityFemtoCubePort)                                       => new GuiFemtoCube(te)
      case (FemtocraftGuiHandler.PhlegethonTunnelGuiID, te: TileEntityPhlegethonTunnelCore)                         => new GuiPhlegethonTunnel(player, player.inventory, te)
      case (_, _)                                                                                                   => null
    }
  }
}
