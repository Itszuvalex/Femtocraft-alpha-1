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
import com.itszuvalex.femtocraft.power.tiles.TileEntityNanoCable;
import com.itszuvalex.femtocraft.proxy.ProxyClient;
import com.itszuvalex.femtocraft.render.RenderUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class BlockNanoCable extends BlockMicroCable {
    public BlockNanoCable(int par1, Material par2Material) {
        super(par1, par2Material);
        setCreativeTab(Femtocraft.femtocraftTab());
        setUnlocalizedName("blockNanoCable");
        setHardness(1.0f);
        setStepSound(Block.soundStoneFootstep);
        setBlockBounds();
        setTickRandomly(true);
    }

    @Override
    public void randomDisplayTick(World par1World, int x, int y, int z, Random par5Random) {
        double spawnX = x + getBlockBoundsMinX() + par5Random.nextFloat() * (getBlockBoundsMaxX() - getBlockBoundsMinX());
        double spawnY = y + getBlockBoundsMinY() + par5Random.nextFloat() * (getBlockBoundsMaxY() - getBlockBoundsMinY());
        double spawnZ = z + getBlockBoundsMinZ() + par5Random.nextFloat() * (getBlockBoundsMaxZ() - getBlockBoundsMinZ());

        RenderUtils.spawnParticle(par1World, RenderUtils.NANO_POWER_PARTICLE, spawnX, spawnY, spawnZ);
    }

    @Override
    public void setBlockBounds() {
        this.minX = this.minY = this.minZ = 4.0D / 16.0D;
        this.maxX = this.maxY = this.maxZ = 12.0D / 16.0D;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityNanoCable();
    }

    @Override
    public int getRenderType() {
        return ProxyClient.nanoCableRenderID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":" + "nanoCableCoil");
        this.coreBorder = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":" +
                "nanoCableCoreBorder");
        this.connector = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":" + "nanoCableConnector");
        this.coil = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":" + "nanoCableCoil");
        this.coilEdge = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":" + "nanoCableCoilEdge");
        this.border = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":" + "nanoCableBorder");
    }
}
