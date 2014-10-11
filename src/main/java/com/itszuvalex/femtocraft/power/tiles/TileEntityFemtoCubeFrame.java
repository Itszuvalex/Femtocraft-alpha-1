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

package com.itszuvalex.femtocraft.power.tiles;

import com.itszuvalex.femtocraft.FemtocraftGuiHandler;
import com.itszuvalex.femtocraft.core.multiblock.IMultiBlockComponent;
import com.itszuvalex.femtocraft.core.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils.Saveable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityFemtoCubeFrame extends TileEntityBase implements
        IMultiBlockComponent {
    private
    @Saveable(desc = true)
    MultiBlockInfo info;

    public TileEntityFemtoCubeFrame() {
        super();
        info = new MultiBlockInfo();
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(),
                    info.z());
            // Big Oops? Or chunk unloaded...despite having player activating it
            // >.>
            if (te == null) {
                return false;
            }

            par5EntityPlayer.openGui(getMod(), getGuiID(), worldObj, info.x(),
                    info.y(), info.z());
            return true;
        }
        return false;
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiHandler.FemtoCubeGuiID();
    }

    @Override
    public boolean hasGUI() {
        return info.isValidMultiBlock();
    }

    @Override
    public boolean isValidMultiBlock() {
        return info.isValidMultiBlock();
    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        boolean result = info.formMultiBlock(world, x, y, z);
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord,
                worldObj.getBlockId(xCoord, yCoord, zCoord));
        return result;
    }

    @Override
    public boolean breakMultiBlock(World world, int x, int y, int z) {
        boolean result = info.breakMultiBlock(world, x, y, z);
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord,
                worldObj.getBlockId(xCoord, yCoord, zCoord));
        return result;
    }

    @Override
    public MultiBlockInfo getInfo() {
        return info;
    }

}
