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

package com.itszuvalex.femtocraft;

import com.itszuvalex.femtocraft.industry.containers.*;
import com.itszuvalex.femtocraft.industry.gui.*;
import com.itszuvalex.femtocraft.industry.tiles.*;
import com.itszuvalex.femtocraft.power.containers.ContainerFemtoCube;
import com.itszuvalex.femtocraft.power.containers.ContainerMicroCube;
import com.itszuvalex.femtocraft.power.containers.ContainerNanoCube;
import com.itszuvalex.femtocraft.power.gui.GuiFemtoCube;
import com.itszuvalex.femtocraft.power.gui.GuiMicroCube;
import com.itszuvalex.femtocraft.power.gui.GuiNanoCube;
import com.itszuvalex.femtocraft.power.tiles.TileEntityFemtoCubePort;
import com.itszuvalex.femtocraft.power.tiles.TileEntityMicroCube;
import com.itszuvalex.femtocraft.power.tiles.TileEntityNanoCubePort;
import com.itszuvalex.femtocraft.research.containers.ContainerResearchConsole;
import com.itszuvalex.femtocraft.research.gui.GuiResearch;
import com.itszuvalex.femtocraft.research.gui.GuiResearchConsole;
import com.itszuvalex.femtocraft.research.tiles.TileEntityResearchConsole;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class FemtocraftGuiHandler implements IGuiHandler {
    //Research
    public static final int ResearchComputerGuiID = 0;
    public static final int ResearchConsoleGuiID = 1;

    //Industry
    public static final int EncoderGuiID = 4;
    public static final int MicroFurnaceGuiID = 5;
    public static final int MicroDeconstructorGuiID = 6;
    public static final int MicroReconstructorGuiID = 7;
    public static final int NanoInnervatorGuiID = 8;
    public static final int NanoDismantlerGuiID = 9;
    public static final int NanoFabricatorGuiID = 10;
    public static final int NanoHorologeGuiID = 11;
    public static final int NanoEnmesherGuiID = 12;
    public static final int FemtoImpulserGuiID = 13;
    public static final int FemtoRepurposerGuiID = 14;
    public static final int FemtoCoagulatorGuiID = 15;
    public static final int FemtoChronoshifterGuiID = 16;
    public static final int FemtoEntanglerGuiID = 17;

    //Power
    public static final int MicroCubeGuiID = 30;
    public static final int MicroEngineGuiID = 31;
    public static final int NanoCubeGuiID = 35;
    public static final int FemtoCubeGuiID = 40;


    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        switch (ID) {
            case ResearchConsoleGuiID:
                return new ContainerResearchConsole(player.inventory,
                        (TileEntityResearchConsole) tileEntity);
            case EncoderGuiID:
                return new ContainerEncoder(player.inventory,
                        (TileEntityEncoder) tileEntity);
            case MicroFurnaceGuiID:
                return new ContainerMicroFurnace(player.inventory,
                        (TileEntityBaseEntityMicroFurnace) tileEntity);
            case MicroDeconstructorGuiID:
                return new ContainerMicroDeconstructor(player.inventory,
                        (TileEntityBaseEntityMicroDeconstructor) tileEntity);
            case MicroReconstructorGuiID:
                return new ContainerMicroReconstructor(player.inventory,
                        (TileEntityBaseEntityMicroReconstructor) tileEntity);
            case NanoInnervatorGuiID:
                return new ContainerNanoInnervator(player.inventory,
                        (TileEntityNanoInnervator) tileEntity);
            case NanoDismantlerGuiID:
                return new ContainerNanoDismantler(player.inventory, (TileEntityNanoDismantler) tileEntity);
            case NanoFabricatorGuiID:
                return new ContainerNanoFabricator(player.inventory,
                        (TileEntityNanoFabricator)
                                tileEntity
                );
            case NanoHorologeGuiID:
                return new ContainerNanoHorologe(player.inventory, (TileEntityBaseEntityNanoHorologe) tileEntity);
            case NanoEnmesherGuiID:
                break;                //TODO
            case FemtoImpulserGuiID:
                return new ContainerFemtoImpulser(player.inventory, (TileEntityFemtoImpulser) tileEntity);
            case FemtoRepurposerGuiID:
                return new ContainerFemtoRepurposer(player.inventory, (TileEntityFemtoRepurposer) tileEntity);
            case FemtoCoagulatorGuiID:
                return new ContainerFemtoCoagulator(player.inventory,
                        (TileEntityFemtoCoagulator)
                                tileEntity
                );
            case FemtoChronoshifterGuiID:
                break;       //TODO
            case FemtoEntanglerGuiID:
                break;             //TODO
            case MicroCubeGuiID:
                return new ContainerMicroCube((TileEntityMicroCube) tileEntity);
            case MicroEngineGuiID:
            case NanoCubeGuiID:
                return new ContainerNanoCube((TileEntityNanoCubePort) tileEntity);
            case FemtoCubeGuiID:
                return new ContainerFemtoCube((TileEntityFemtoCubePort) tileEntity);
            default:
                return null;
        }

        return null;
    }


    // Can switch on type of tile entity, or can alternatively switch on type
    // ID.
    // However, going to have to pull tile entity anyways, so might as well just
    // use that.

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        switch (ID) {
            case ResearchComputerGuiID:
                return new GuiResearch(player.username);
            case ResearchConsoleGuiID:
                return new GuiResearchConsole(player.inventory,
                        (TileEntityResearchConsole) tileEntity);
            case EncoderGuiID:
                return new GuiEncoder(player.inventory,
                        (TileEntityEncoder) tileEntity);
            case MicroFurnaceGuiID:
                return new GuiMicroFurnace(player.inventory,
                        (TileEntityBaseEntityMicroFurnace) tileEntity);
            case MicroDeconstructorGuiID:
                return new GuiMicroDeconstructor(player.inventory,
                        (TileEntityBaseEntityMicroDeconstructor) tileEntity);
            case MicroReconstructorGuiID:
                return new GuiMicroReconstructor(player.inventory,
                        (TileEntityBaseEntityMicroReconstructor) tileEntity);
            case NanoInnervatorGuiID:
                return new GuiNanoInnervator(player.inventory,
                        (TileEntityNanoInnervator) tileEntity);
            case NanoDismantlerGuiID:
                return new GuiNanoDismantler(player.inventory, (TileEntityNanoDismantler) tileEntity);
            case NanoFabricatorGuiID:
                return new GuiNanoFabricator(player.inventory,
                        (TileEntityNanoFabricator) tileEntity);
            case NanoHorologeGuiID:
                return new GuiNanoHorologe(player.inventory, (TileEntityBaseEntityNanoHorologe) tileEntity);
            case NanoEnmesherGuiID:
                break; //TODO
            case FemtoImpulserGuiID:
                return new GuiFemtoImpulser(player.inventory, (TileEntityFemtoImpulser) tileEntity);
            case FemtoRepurposerGuiID:
                return new GuiFemtoRepurposer(player.inventory, (TileEntityFemtoRepurposer) tileEntity);
            case FemtoCoagulatorGuiID:
                return new GuiFemtoCoagulator(player.inventory, (TileEntityFemtoCoagulator) tileEntity);
            case FemtoChronoshifterGuiID:
                break;         //TODO
            case FemtoEntanglerGuiID:
                break;               //TODO
            case MicroCubeGuiID:
                return new GuiMicroCube((TileEntityMicroCube) tileEntity);
            case MicroEngineGuiID:
                break;//TODO
            case NanoCubeGuiID:
                return new GuiNanoCube((TileEntityNanoCubePort) tileEntity);
            case FemtoCubeGuiID:
                return new GuiFemtoCube((TileEntityFemtoCubePort) tileEntity);
            default:
                return null;
        }

        return null;
    }
}
