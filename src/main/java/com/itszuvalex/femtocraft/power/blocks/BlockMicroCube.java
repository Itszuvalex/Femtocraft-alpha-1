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

package com.itszuvalex.femtocraft.power.blocks;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.power.tiles.TileEntityMicroCube;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMicroCube extends BlockPowerContainer {
    public Icon outputSide;
    public Icon inputSide;

    public BlockMicroCube(int par1) {
        super(par1, Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab);
        setUnlocalizedName("blockMicroCube");
        setHardness(3.f);
        setStepSound(Block.soundMetalFootstep);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityMicroCube();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess access, int x, int y, int z,
                                int side) {
        TileEntity te = access.getBlockTileEntity(x, y, z);

        if (te == null) {
            return this.blockIcon;
        }
        if (!(te instanceof TileEntityMicroCube)) {
            return this.blockIcon;
        }
        TileEntityMicroCube cube = (TileEntityMicroCube) te;
        return cube.outputs[side] ? outputSide : inputSide;
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
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = inputSide = par1IconRegister
                .registerIcon(Femtocraft.ID.toLowerCase() + ":"
                        + "MicroCube_input");
        outputSide = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                + ":" + "MicroCube_output");
        // side = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +
        // ":" + "MicroCube_side");
    }
}
