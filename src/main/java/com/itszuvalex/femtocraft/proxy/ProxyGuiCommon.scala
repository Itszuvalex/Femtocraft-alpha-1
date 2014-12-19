package com.itszuvalex.femtocraft.proxy

import com.itszuvalex.femtocraft.FemtocraftGuiConstants
import com.itszuvalex.femtocraft.industry.containers._
import com.itszuvalex.femtocraft.industry.tiles._
import com.itszuvalex.femtocraft.power.containers._
import com.itszuvalex.femtocraft.power.tiles._
import com.itszuvalex.femtocraft.research.containers.ContainerResearchConsole
import com.itszuvalex.femtocraft.research.tiles.TileEntityResearchConsole
import cpw.mods.fml.common.network.IGuiHandler
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

/**
 * Created by Christopher Harris (Itszuvalex) on 11/21/14.
 */
class ProxyGuiCommon extends IGuiHandler {
  override def getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = {
    (ID, world.getTileEntity(x, y, z)) match {
      case (FemtocraftGuiConstants.ResearchConsoleGuiID, te: TileEntityResearchConsole)                               => new ContainerResearchConsole(player.inventory, te)
      case (FemtocraftGuiConstants.EncoderGuiID, te: TileEntityEncoder)                                               => new ContainerEncoder(player, player.inventory, te)
      case (FemtocraftGuiConstants.MicroFurnaceGuiID, te: TileEntityBaseEntityMicroFurnace)                           => new ContainerMicroFurnace(player, player.inventory, te)
      case (FemtocraftGuiConstants.MicroDeconstructorGuiID, te: TileEntityBaseEntityMicroDeconstructor)               => new ContainerMicroDeconstructor(player, player.inventory, te)
      case (FemtocraftGuiConstants.MicroReconstructorGuiID, te: TileEntityBaseEntityMicroReconstructor)               => new ContainerMicroReconstructor(player, player.inventory, te)
      case (FemtocraftGuiConstants.NanoInnervatorGuiID, te: TileEntityNanoInnervator)                                 => new ContainerNanoInnervator(player, player.inventory, te)
      case (FemtocraftGuiConstants.NanoDismantlerGuiID, te: TileEntityNanoDismantler)                                 => new ContainerNanoDismantler(player, player.inventory, te)
      case (FemtocraftGuiConstants.NanoFabricatorGuiID, te: TileEntityNanoFabricator)                                 => new ContainerNanoFabricator(player, player.inventory, te)
      case (FemtocraftGuiConstants.NanoHorologeGuiID, te: TileEntityBaseEntityNanoHorologe)                           => new ContainerNanoHorologe(player, player.inventory, te)
      case (FemtocraftGuiConstants.NanoEnmesherGuiID, te: TileEntityBaseEntityNanoEnmesher)                           => new ContainerNanoEnmesher(player, player.inventory, te)
      case (FemtocraftGuiConstants.FemtoImpulserGuiID, te: TileEntityFemtoImpulser)                                   => new ContainerFemtoImpulser(player, player.inventory, te)
      case (FemtocraftGuiConstants.FemtoRepurposerGuiID, te: TileEntityFemtoRepurposer)                               => new ContainerFemtoRepurposer(player, player.inventory, te)
      case (FemtocraftGuiConstants.FemtoCoagulatorGuiID, te: TileEntityFemtoCoagulator)                               => new ContainerFemtoCoagulator(player, player.inventory, te)
      case (FemtocraftGuiConstants.FemtoChronoshifterGuiID, te: TileEntityFemtoChronoshifter)                         => new ContainerFemtoChronoshifter(player, player.inventory, te)
      case (FemtocraftGuiConstants.FemtoEntanglerGuiID, te: TileEntityFemtoEntangler)                                 => new ContainerFemtoEntangler(player, player.inventory, te)
      case (FemtocraftGuiConstants.MicroCubeGuiID, te: TileEntityMicroCube)                                           => new ContainerMicroCube(te)
      case (FemtocraftGuiConstants.MicroEngineGuiID, _)                                                               => null
      case (FemtocraftGuiConstants.NanoCubeGuiID, te: TileEntityNanoCubePort)                                         => new ContainerNanoCube(te)
      case (FemtocraftGuiConstants.NanoFissionReactorGuiID, te: TileEntityNanoFissionReactorCore)                     => new ContainerNanoFissionReactor(player, player.inventory, te)
      case (FemtocraftGuiConstants.NanoMagnetohydrodynamicGeneratorGuiID, te: TileEntityMagnetohydrodynamicGenerator) => new ContainerMagnetoHydrodynamicGenerator(te)
      case (FemtocraftGuiConstants.DecontaminationChamberID, te: TileEntityDecontaminationChamber)                    => new ContainerDecontaminationChamber(te)
      case (FemtocraftGuiConstants.FemtoCubeGuiID, te: TileEntityFemtoCubePort)                                       => new ContainerFemtoCube(te)
      case (FemtocraftGuiConstants.PhlegethonTunnelGuiID, te: TileEntityPhlegethonTunnelCore)                         => new ContainerPhlegethonTunnel(player, player.inventory, te)
      case (_, _)                                                                                                     => null
    }
  }

  override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = null
}
