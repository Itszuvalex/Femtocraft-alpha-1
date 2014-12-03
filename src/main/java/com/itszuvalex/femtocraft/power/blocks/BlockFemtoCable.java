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
import com.itszuvalex.femtocraft.power.tiles.TileEntityFemtoCable;
import com.itszuvalex.femtocraft.proxy.ProxyClient;
import com.itszuvalex.femtocraft.render.RenderUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFemtoCable extends BlockMicroCable {
    public BlockFemtoCable(Material par2Material) {
        super(par2Material);
        setCreativeTab(Femtocraft.femtocraftTab());
        setBlockName("blockFemtoCable");
        setHardness(1.0f);
        setStepSound(Block.soundTypeMetal);
        setBlockBounds();
        setTickRandomly(true);
    }

    @Override
    public void randomDisplayTick(World par1World, int x, int y, int z, Random par5Random) {
        double spawnX =
                x + getBlockBoundsMinX() + par5Random.nextFloat() * (getBlockBoundsMaxX() - getBlockBoundsMinX());
        double spawnY =
                y + getBlockBoundsMinY() + par5Random.nextFloat() * (getBlockBoundsMaxY() - getBlockBoundsMinY());
        double spawnZ =
                z + getBlockBoundsMinZ() + par5Random.nextFloat() * (getBlockBoundsMaxZ() - getBlockBoundsMinZ());

        RenderUtils.spawnParticle(par1World, RenderUtils.FEMTO_POWER_PARTICLE(), spawnX, spawnY, spawnZ);
    }

    @Override
    public int getRenderType() {
        return ProxyClient.femtoCableRenderID();
    }

    @Override
    public void setBlockBounds() {
        this.minX = this.minY = this.minZ = 4.0D / 16.0D;
        this.maxX = this.maxY = this.maxZ = 12.0D / 16.0D;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityFemtoCable();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":" + "femtoCableCoil");
        coreBorder_$eq(par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":" + "femtoCableCoreBorder"));
        connector_$eq(par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":" + "femtoCableConnector"));
        coil_$eq(par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":" + "femtoCableCoil"));
        coilEdge_$eq(par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":" + "femtoCableCoilEdge"));
        border_$eq(par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":" + "femtoCableBorder"));
    }
}
