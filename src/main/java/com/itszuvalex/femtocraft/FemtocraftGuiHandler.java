///*
// * ******************************************************************************
// *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
// *  * Itszuvalex@gmail.com
// *  *
// *  * This program is free software; you can redistribute it and/or
// *  * modify it under the terms of the GNU General Public License
// *  * as published by the Free Software Foundation; either version 2
// *  * of the License, or (at your option) any later version.
// *  *
// *  * This program is distributed in the hope that it will be useful,
// *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
// *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *  * GNU General Public License for more details.
// *  *
// *  * You should have received a copy of the GNU General Public License
// *  * along with this program; if not, write to the Free Software
// *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
// *  *****************************************************************************
// */
//
//package com.itszuvalex.femtocraft;
//
//import com.itszuvalex.femtocraft.industry.containers.*;
//import com.itszuvalex.femtocraft.industry.gui.*;
//import com.itszuvalex.femtocraft.industry.tiles.*;
//import com.itszuvalex.femtocraft.power.containers.*;
//import com.itszuvalex.femtocraft.power.gui.*;
//import com.itszuvalex.femtocraft.power.tiles.*;
//import com.itszuvalex.femtocraft.research.containers.ContainerResearchConsole;
//import com.itszuvalex.femtocraft.research.gui.GuiResearch;
//import com.itszuvalex.femtocraft.research.gui.GuiResearchConsole;
//import com.itszuvalex.femtocraft.research.tiles.TileEntityResearchConsole;
//import cpw.mods.fml.common.network.IGuiHandler;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.world.World;
//
//public class FemtocraftGuiHandler implements IGuiHandler {
//    //Research
//    public static final int ResearchComputerGuiID = 0;
//    public static final int ResearchConsoleGuiID = 1;
//
//    //Industry
//    public static final int EncoderGuiID = 4;
//    public static final int MicroFurnaceGuiID = 5;
//    public static final int MicroDeconstructorGuiID = 6;
//    public static final int MicroReconstructorGuiID = 7;
//    public static final int NanoInnervatorGuiID = 8;
//    public static final int NanoDismantlerGuiID = 9;
//    public static final int NanoFabricatorGuiID = 10;
//    public static final int NanoHorologeGuiID = 11;
//    public static final int NanoEnmesherGuiID = 12;
//    public static final int FemtoImpulserGuiID = 13;
//    public static final int FemtoRepurposerGuiID = 14;
//    public static final int FemtoCoagulatorGuiID = 15;
//    public static final int FemtoChronoshifterGuiID = 16;
//    public static final int FemtoEntanglerGuiID = 17;
//
//    //Power
//    public static final int MicroCubeGuiID = 30;
//    public static final int MicroEngineGuiID = 31;
//    public static final int NanoCubeGuiID = 35;
//    public static final int NanoFissionReactorGuiID = 36;
//    public static final int NanoMagnetohydrodynamicGeneratorGuiID = 37;
//    public static final int FemtoCubeGuiID = 40;
//    public static final int PhlegethonTunnelGuiID = 41;
//
//    public static final String PACKET_CHANNEL = Femtocraft.GUI_CHANNEL();
//
//
//    @Override
//    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
//                                      int x, int y, int z) {
//        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
//
//        switch (ID) {
//            case ResearchConsoleGuiID:
//                return new ContainerResearchConsole(player.inventory,
//                        (TileEntityResearchConsole) tileEntity);
//            case EncoderGuiID:
//                return new ContainerEncoder(player, player.inventory,
//                        (TileEntityEncoder) tileEntity);
//            case MicroFurnaceGuiID:
//                return new ContainerMicroFurnace(player, player.inventory,
//                        (TileEntityBaseEntityMicroFurnace) tileEntity);
//            case MicroDeconstructorGuiID:
//                return new ContainerMicroDeconstructor(player, player.inventory,
//                        (TileEntityBaseEntityMicroDeconstructor) tileEntity);
//            case MicroReconstructorGuiID:
//                return new ContainerMicroReconstructor(player, player.inventory,
//                        (TileEntityBaseEntityMicroReconstructor) tileEntity);
//            case NanoInnervatorGuiID:
//                return new ContainerNanoInnervator(player, player.inventory,
//                        (TileEntityNanoInnervator) tileEntity);
//            case NanoDismantlerGuiID:
//                return new ContainerNanoDismantler(player, player.inventory, (TileEntityNanoDismantler) tileEntity);
//            case NanoFabricatorGuiID:
//                return new ContainerNanoFabricator(player, player.inventory,
//                        (TileEntityNanoFabricator)
//                                tileEntity
//                );
//            case NanoHorologeGuiID:
//                return new ContainerNanoHorologe(player, player.inventory, (TileEntityBaseEntityNanoHorologe)
//                        tileEntity);
//            case NanoEnmesherGuiID:
//                return new ContainerNanoEnmesher(player, player.inventory, (TileEntityBaseEntityNanoEnmesher)
//                        tileEntity);
//            case FemtoImpulserGuiID:
//                return new ContainerFemtoImpulser(player, player.inventory, (TileEntityFemtoImpulser) tileEntity);
//            case FemtoRepurposerGuiID:
//                return new ContainerFemtoRepurposer(player, player.inventory, (TileEntityFemtoRepurposer) tileEntity);
//            case FemtoCoagulatorGuiID:
//                return new ContainerFemtoCoagulator(player, player.inventory,
//                        (TileEntityFemtoCoagulator)
//                                tileEntity
//                );
//            case FemtoChronoshifterGuiID:
//                return new ContainerFemtoChronoshifter(player, player.inventory,
//                        (TileEntityFemtoChronoshifter) tileEntity);
//            case FemtoEntanglerGuiID:
//                return new ContainerFemtoEntangler(player, player.inventory, (TileEntityFemtoEntangler) tileEntity);
//            case MicroCubeGuiID:
//                return new ContainerMicroCube((TileEntityMicroCube) tileEntity);
//            case MicroEngineGuiID:
//            case NanoCubeGuiID:
//                return new ContainerNanoCube((TileEntityNanoCubePort) tileEntity);
//            case NanoFissionReactorGuiID:
//                return new ContainerNanoFissionReactor(player, player.inventory, (
//                        TileEntityNanoFissionReactorCore) tileEntity);
//            case NanoMagnetohydrodynamicGeneratorGuiID:
//                return new ContainerMagnetoHydrodynamicGenerator((TileEntityMagnetohydrodynamicGenerator) tileEntity);
//            case FemtoCubeGuiID:
//                return new ContainerFemtoCube((TileEntityFemtoCubePort) tileEntity);
//            case PhlegethonTunnelGuiID:
//                return new ContainerPhlegethonTunnel(player, player.inventory, (TileEntityPhlegethonTunnelCore)
//                        tileEntity);
//            default:
//                return null;
//        }
//    }
//
//
//    // Can switch on type of tile entity, or can alternatively switch on type
//    // ID.
//    // However, going to have to pull tile entity anyways, so might as well just
//    // use that.
//
//    @Override
//    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
//                                      int x, int y, int z) {
//        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
//
//        switch (ID) {
//            case ResearchComputerGuiID:
//                return new GuiResearch(player.username);
//            case ResearchConsoleGuiID:
//                return new GuiResearchConsole(player.inventory,
//                        (TileEntityResearchConsole) tileEntity);
//            case EncoderGuiID:
//                return new GuiEncoder(player, player.inventory,
//                        (TileEntityEncoder) tileEntity);
//            case MicroFurnaceGuiID:
//                return new GuiMicroFurnace(player, player.inventory,
//                        (TileEntityBaseEntityMicroFurnace) tileEntity);
//            case MicroDeconstructorGuiID:
//                return new GuiMicroDeconstructor(player, player.inventory,
//                        (TileEntityBaseEntityMicroDeconstructor) tileEntity);
//            case MicroReconstructorGuiID:
//                return new GuiMicroReconstructor(player, player.inventory,
//                        (TileEntityBaseEntityMicroReconstructor) tileEntity);
//            case NanoInnervatorGuiID:
//                return new GuiNanoInnervator(player, player.inventory,
//                        (TileEntityNanoInnervator) tileEntity);
//            case NanoDismantlerGuiID:
//                return new GuiNanoDismantler(player, player.inventory, (TileEntityNanoDismantler) tileEntity);
//            case NanoFabricatorGuiID:
//                return new GuiNanoFabricator(player, player.inventory,
//                        (TileEntityNanoFabricator) tileEntity);
//            case NanoHorologeGuiID:
//                return new GuiNanoHorologe(player, player.inventory, (TileEntityBaseEntityNanoHorologe) tileEntity);
//            case NanoEnmesherGuiID:
//                return new GuiNanoEnmesher(player, player.inventory, (TileEntityBaseEntityNanoEnmesher) tileEntity);
//            case FemtoImpulserGuiID:
//                return new GuiFemtoImpulser(player, player.inventory, (TileEntityFemtoImpulser) tileEntity);
//            case FemtoRepurposerGuiID:
//                return new GuiFemtoRepurposer(player, player.inventory, (TileEntityFemtoRepurposer) tileEntity);
//            case FemtoCoagulatorGuiID:
//                return new GuiFemtoCoagulator(player, player.inventory, (TileEntityFemtoCoagulator) tileEntity);
//            case FemtoChronoshifterGuiID:
//                return new GuiFemtoChronoshifter(player, player.inventory, (TileEntityFemtoChronoshifter) tileEntity);
//            case FemtoEntanglerGuiID:
//                return new GuiFemtoEntangler(player, player.inventory, (TileEntityFemtoEntangler) tileEntity);
//            case MicroCubeGuiID:
//                return new GuiMicroCube((TileEntityMicroCube) tileEntity);
//            case MicroEngineGuiID:
//                break;//TODO
//            case NanoFissionReactorGuiID:
//                return new GuiNanoFissionReactor(player, player.inventory, (TileEntityNanoFissionReactorCore)
//                        tileEntity);
//            case NanoMagnetohydrodynamicGeneratorGuiID:
//                return new GuiMagnetohydrodynamicGenerator((TileEntityMagnetohydrodynamicGenerator) tileEntity);
//            case NanoCubeGuiID:
//                return new GuiNanoCube((TileEntityNanoCubePort) tileEntity);
//            case FemtoCubeGuiID:
//                return new GuiFemtoCube((TileEntityFemtoCubePort) tileEntity);
//            case PhlegethonTunnelGuiID:
//                return new GuiPhlegethonTunnel(player, player.inventory, (TileEntityPhlegethonTunnelCore) tileEntity);
//            default:
//                return null;
//        }
//
//        return null;
//    }
//}
