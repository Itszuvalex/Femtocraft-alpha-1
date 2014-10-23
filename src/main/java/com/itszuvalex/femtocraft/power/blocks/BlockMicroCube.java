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

package com.itszuvalex.femtocraft.power.blocks;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.power.tiles.TileEntityMicroCube;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMicroCube extends BlockPowerContainer {
    public IIcon outputSide;
    public IIcon inputSide;

    public BlockMicroCube() {
        super(Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab());
        setBlockName("blockMicroCube");
        setHardness(3.f);
        setStepSound(Block.soundTypeMetal);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityMicroCube();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess access, int x, int y, int z,
                         int side) {
        TileEntity te = access.getTileEntity(x, y, z);

        if (te != null && te instanceof TileEntityMicroCube) {
            TileEntityMicroCube cube = (TileEntityMicroCube) te;
            return cube.outputs[side] ? outputSide : inputSide;
        }
        return this.blockIcon;
    }

    // @Override
    // public boolean isOpaqueCube() {
    // return false;
    // }

    // @Override
    // public boolean renderAsNormalBlock() {
    // return false;
    // }
    //
    // @Override
    // public int getRenderType() {
    // return ProxyClient.FemtopowerMicroCubeRenderID;
    // }
    //

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        this.blockIcon = inputSide = par1IconRegister
                .registerIcon(Femtocraft.ID().toLowerCase() + ":"
                              + "MicroCube_input");
        outputSide = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                                                   + ":" + "MicroCube_output");
        // side = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() +
        // ":" + "MicroCube_side");
    }
}
