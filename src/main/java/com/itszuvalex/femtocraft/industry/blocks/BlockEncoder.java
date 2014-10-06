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

package com.itszuvalex.femtocraft.industry.blocks;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.core.blocks.TileContainer;
import com.itszuvalex.femtocraft.industry.tiles.TileEntityEncoder;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockEncoder extends TileContainer {
    public Icon top;
    public Icon side;

    public BlockEncoder(int par1) {
        super(par1, Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab());
        setHardness(3.5f);
        setStepSound(Block.soundMetalFootstep);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2) {
        switch (ForgeDirection.getOrientation(par1)) {
            case UP:
                return top;
            case DOWN:
                return blockIcon;
            default:
                return side;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        top = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":"
                + "BlockEncoder_top");
        side = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":"
                + "BlockEncoder_side");
        blockIcon = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                + ":" + "MicroMachineBlock_side");
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityEncoder();
    }
}
