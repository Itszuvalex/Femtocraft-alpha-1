/*******************************************************************************
 * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 * Itszuvalex@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/

package femtocraft;

import cpw.mods.fml.common.network.IGuiHandler;
import femtocraft.industry.containers.*;
import femtocraft.industry.gui.*;
import femtocraft.industry.tiles.*;
import femtocraft.power.containers.ContainerFemtoCube;
import femtocraft.power.containers.ContainerMicroCube;
import femtocraft.power.containers.ContainerNanoCube;
import femtocraft.power.gui.GuiFemtoCube;
import femtocraft.power.gui.GuiMicroCube;
import femtocraft.power.gui.GuiNanoCube;
import femtocraft.power.tiles.TileEntityFemtoCubePort;
import femtocraft.power.tiles.TileEntityMicroCube;
import femtocraft.power.tiles.TileEntityNanoCubePort;
import femtocraft.research.containers.ContainerResearchConsole;
import femtocraft.research.gui.GuiResearch;
import femtocraft.research.gui.GuiResearchConsole;
import femtocraft.research.tiles.TileEntityResearchComputer;
import femtocraft.research.tiles.TileEntityResearchConsole;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class FemtocraftGuiHandler implements IGuiHandler {

    // Can switch on type of tile entity, or can alternatively switch on type
    // ID.
    // However, going to have to pull tile entity anyways, so might as well just
    // use that.

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (tileEntity instanceof TileEntityFemtoImpulser) {
            return new ContainerFemtoImpulser(player.inventory, (TileEntityFemtoImpulser) tileEntity);
        }
        else if (tileEntity instanceof TileEntityNanoInnervator) {
            return new ContainerNanoInnervator(player.inventory,
                                               (TileEntityNanoInnervator) tileEntity);
        }
        else if (tileEntity instanceof TileEntityBaseEntityMicroFurnace) {
            return new ContainerMicroFurnace(player.inventory,
                                             (TileEntityBaseEntityMicroFurnace) tileEntity);
        }
        else if (tileEntity instanceof TileEntityNanoDismantler) {
            return new ContainerNanoDismantler(player.inventory, (TileEntityNanoDismantler) tileEntity);
        }
        else if (tileEntity instanceof TileEntityFemtoRepurposer) {
            return new ContainerFemtoRepurposer(player.inventory, (TileEntityFemtoRepurposer) tileEntity);
        }
        else if (tileEntity instanceof TileEntityBaseEntityMicroDeconstructor) {
            return new ContainerMicroDeconstructor(player.inventory,
                                                   (TileEntityBaseEntityMicroDeconstructor) tileEntity);
        }
        else if (tileEntity instanceof TileEntityFemtoCoagulator) {
            return new ContainerFemtoCoagulator(player.inventory,
                                                (TileEntityFemtoCoagulator)
                                                        tileEntity
            );
        }
        else if (tileEntity instanceof TileEntityNanoFabricator) {
            return new ContainerNanoFabricator(player.inventory,
                                               (TileEntityNanoFabricator)
                                                       tileEntity
            );
        }
        else if (tileEntity instanceof TileEntityBaseEntityMicroReconstructor) {
            return new ContainerMicroReconstructor(player.inventory,
                                                   (TileEntityBaseEntityMicroReconstructor) tileEntity);
        }
        else if (tileEntity instanceof TileEntityEncoder) {
            return new ContainerEncoder(player.inventory,
                                        (TileEntityEncoder) tileEntity);
        }
        else if (tileEntity instanceof TileEntityFemtoCubePort) {
            return new ContainerFemtoCube((TileEntityFemtoCubePort) tileEntity);
        }
        else if (tileEntity instanceof TileEntityNanoCubePort) {
            return new ContainerNanoCube((TileEntityNanoCubePort) tileEntity);
        }
        else if (tileEntity instanceof TileEntityMicroCube) {
            return new ContainerMicroCube((TileEntityMicroCube) tileEntity);
        }
        else if (tileEntity instanceof TileEntityResearchConsole) {
            return new ContainerResearchConsole(player.inventory,
                                                (TileEntityResearchConsole) tileEntity);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (tileEntity instanceof TileEntityFemtoImpulser) {
            return new GuiFemtoImpulser(player.inventory, (TileEntityFemtoImpulser) tileEntity);
        }
        else if (tileEntity instanceof TileEntityNanoInnervator) {
            return new GuiNanoInnervator(player.inventory,
                                         (TileEntityNanoInnervator) tileEntity);
        }
        else if (tileEntity instanceof TileEntityBaseEntityMicroFurnace) {
            return new GuiMicroFurnace(player.inventory,
                                       (TileEntityBaseEntityMicroFurnace) tileEntity);
        }
        else if (tileEntity instanceof TileEntityFemtoRepurposer) {
            return new GuiFemtoRepurposer(player.inventory, (TileEntityFemtoRepurposer) tileEntity);
        }
        else if (tileEntity instanceof TileEntityNanoDismantler) {
            return new GuiNanoDismantler(player.inventory, (TileEntityNanoDismantler) tileEntity);
        }
        else if (tileEntity instanceof TileEntityBaseEntityMicroDeconstructor) {
            return new GuiMicroDeconstructor(player.inventory,
                                             (TileEntityBaseEntityMicroDeconstructor) tileEntity);
        }
        else if (tileEntity instanceof TileEntityFemtoCoagulator) {
            return new GuiFemtoCoagulator(player.inventory, (TileEntityFemtoCoagulator) tileEntity);
        }
        else if (tileEntity instanceof TileEntityNanoFabricator) {
            return new GuiNanoFabricator(player.inventory,
                                         (TileEntityNanoFabricator) tileEntity);
        }
        else if (tileEntity instanceof TileEntityBaseEntityMicroReconstructor) {
            return new GuiMicroReconstructor(player.inventory,
                                             (TileEntityBaseEntityMicroReconstructor) tileEntity);
        }
        else if (tileEntity instanceof TileEntityEncoder) {
            return new GuiEncoder(player.inventory,
                                  (TileEntityEncoder) tileEntity);
        }
        else if (tileEntity instanceof TileEntityFemtoCubePort) {
            return new GuiFemtoCube((TileEntityFemtoCubePort) tileEntity);
        }
        else if (tileEntity instanceof TileEntityNanoCubePort) {
            return new GuiNanoCube((TileEntityNanoCubePort) tileEntity);
        }
        else if (tileEntity instanceof TileEntityResearchComputer) {
            return new GuiResearch(player.username);
        }
        else if (tileEntity instanceof TileEntityMicroCube) {
            return new GuiMicroCube((TileEntityMicroCube) tileEntity);
        }
        else if (tileEntity instanceof TileEntityResearchConsole) {
            return new GuiResearchConsole(player.inventory,
                                          (TileEntityResearchConsole) tileEntity);
        }

        return null;
    }

}
