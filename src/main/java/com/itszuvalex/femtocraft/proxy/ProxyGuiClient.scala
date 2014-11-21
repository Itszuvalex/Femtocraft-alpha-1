package com.itszuvalex.femtocraft.proxy

import com.itszuvalex.femtocraft.FemtocraftGuiConstants
import com.itszuvalex.femtocraft.industry.gui._
import com.itszuvalex.femtocraft.industry.tiles._
import com.itszuvalex.femtocraft.power.gui._
import com.itszuvalex.femtocraft.power.tiles._
import com.itszuvalex.femtocraft.research.gui.{GuiResearch, GuiResearchConsole}
import com.itszuvalex.femtocraft.research.tiles.TileEntityResearchConsole
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

/**
 * Created by Christopher Harris (Itszuvalex) on 11/21/14.
 */
class ProxyGuiClient extends ProxyGuiCommon {
  override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = {
    (ID, world.getTileEntity(x, y, z)) match {
      case (FemtocraftGuiConstants.ResearchComputerGuiID, _)                                                          => new GuiResearch(player.getCommandSenderName)
      case (FemtocraftGuiConstants.ResearchConsoleGuiID, te: TileEntityResearchConsole)                               => new GuiResearchConsole(player.inventory, te)
      case (FemtocraftGuiConstants.EncoderGuiID, te: TileEntityEncoder)                                               => new GuiEncoder(player, player.inventory, te)
      case (FemtocraftGuiConstants.MicroFurnaceGuiID, te: TileEntityBaseEntityMicroFurnace)                           => new GuiMicroFurnace(player, player.inventory, te)
      case (FemtocraftGuiConstants.MicroDeconstructorGuiID, te: TileEntityBaseEntityMicroDeconstructor)               => new GuiMicroDeconstructor(player, player.inventory, te)
      case (FemtocraftGuiConstants.MicroReconstructorGuiID, te: TileEntityBaseEntityMicroReconstructor)               => new GuiMicroReconstructor(player, player.inventory, te)
      case (FemtocraftGuiConstants.NanoInnervatorGuiID, te: TileEntityNanoInnervator)                                 => new GuiNanoInnervator(player, player.inventory, te)
      case (FemtocraftGuiConstants.NanoDismantlerGuiID, te: TileEntityNanoDismantler)                                 => new GuiNanoDismantler(player, player.inventory, te)
      case (FemtocraftGuiConstants.NanoFabricatorGuiID, te: TileEntityNanoFabricator)                                 => new GuiNanoFabricator(player, player.inventory, te)
      case (FemtocraftGuiConstants.NanoHorologeGuiID, te: TileEntityBaseEntityNanoHorologe)                           => new GuiNanoHorologe(player, player.inventory, te)
      case (FemtocraftGuiConstants.NanoEnmesherGuiID, te: TileEntityBaseEntityNanoEnmesher)                           => new GuiNanoEnmesher(player, player.inventory, te)
      case (FemtocraftGuiConstants.FemtoImpulserGuiID, te: TileEntityFemtoImpulser)                                   => new GuiFemtoImpulser(player, player.inventory, te)
      case (FemtocraftGuiConstants.FemtoRepurposerGuiID, te: TileEntityFemtoRepurposer)                               => new GuiFemtoRepurposer(player, player.inventory, te)
      case (FemtocraftGuiConstants.FemtoCoagulatorGuiID, te: TileEntityFemtoCoagulator)                               => new GuiFemtoCoagulator(player, player.inventory, te)
      case (FemtocraftGuiConstants.FemtoChronoshifterGuiID, te: TileEntityFemtoChronoshifter)                         => new GuiFemtoChronoshifter(player, player.inventory, te)
      case (FemtocraftGuiConstants.FemtoEntanglerGuiID, te: TileEntityFemtoEntangler)                                 => new GuiFemtoEntangler(player, player.inventory, te)
      case (FemtocraftGuiConstants.MicroCubeGuiID, te: TileEntityMicroCube)                                           => new GuiMicroCube(te)
      case (FemtocraftGuiConstants.MicroEngineGuiID, _)                                                               => null
      case (FemtocraftGuiConstants.NanoFissionReactorGuiID, te: TileEntityNanoFissionReactorCore)                     => new GuiNanoFissionReactor(player, player.inventory, te)
      case (FemtocraftGuiConstants.NanoMagnetohydrodynamicGeneratorGuiID, te: TileEntityMagnetohydrodynamicGenerator) => new GuiMagnetohydrodynamicGenerator(te)
      case (FemtocraftGuiConstants.NanoCubeGuiID, te: TileEntityNanoCubePort)                                         => new GuiNanoCube(te)
      case (FemtocraftGuiConstants.FemtoCubeGuiID, te: TileEntityFemtoCubePort)                                       => new GuiFemtoCube(te)
      case (FemtocraftGuiConstants.PhlegethonTunnelGuiID, te: TileEntityPhlegethonTunnelCore)                         => new GuiPhlegethonTunnel(player, player.inventory, te)
      case (_, _)                                                                                                   => null
    }
  }
}
