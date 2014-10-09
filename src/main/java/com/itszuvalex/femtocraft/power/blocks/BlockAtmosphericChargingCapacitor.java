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
import com.itszuvalex.femtocraft.api.power.IAtmosphericChargingAddon;
import com.itszuvalex.femtocraft.api.power.IAtmosphericChargingBase;
import com.itszuvalex.femtocraft.core.blocks.BlockBase;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.proxy.ProxyClient;
import com.itszuvalex.femtocraft.render.RenderUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockAtmosphericChargingCapacitor extends BlockBase implements IAtmosphericChargingAddon {
    public static float powerMultiplierBase = .2f;
    public static float powerMultiplierRain = .4f;
    public static float powerMultiplierStorm = .8f;
    public Icon capacitorConnector;
    public Icon capacitorTop;
    public Icon capacitorSide;
    public Icon capacitorBot;
    public Icon capacitorConnectorBot;

    public BlockAtmosphericChargingCapacitor(int par1) {
        super(par1, Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab());
        setUnlocalizedName("BlockAtmosphericChargingCapacitor");
        setHardness(1.0f);
        setStepSound(Block.soundMetalFootstep);
        setBlockBounds(2.f / 16.f, 0, 2.f / 16.f, 14.f / 16.f, 14.f / 16.f, 14.f / 16.f);
        setTickRandomly(true);
    }

    @Override
    public void randomDisplayTick(World par1World, int x, int y, int z, Random par5Random) {
        double spawnX = x + getBlockBoundsMinX() + par5Random.nextFloat() * (getBlockBoundsMaxX() - getBlockBoundsMinX());
        double spawnY = y + getBlockBoundsMinY() + par5Random.nextFloat() * (getBlockBoundsMaxY() - getBlockBoundsMinY());
        double spawnZ = z + getBlockBoundsMinZ() + par5Random.nextFloat() * (getBlockBoundsMaxZ() - getBlockBoundsMinZ());

        RenderUtils.spawnParticle(par1World, RenderUtils.MICRO_POWER_PARTICLE, spawnX, spawnY, spawnZ);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return ProxyClient.FemtocraftChargingCapacitorRenderID;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess par1iBlockAccess, int par2,
                                int par3, int par4, int par5) {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3,
                                      int par4, int par5) {
        if (!canBlockStay(par1World, par2, par3, par4)) {
            dropBlockAsItem(par1World, par2, par3, par4,
                    par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        return canBlockStay(par1World, par2, par3, par4);
    }

    @Override
    public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
        Block block = Block.blocksList[par1World.getBlockId(par2, par3 - 1,
                par4)];
        return block != null
                && (block instanceof IAtmosphericChargingAddon &&
                ((IAtmosphericChargingAddon) block).canSupportAddon(this,
                        par1World,
                        par2,
                        par3,
                        par4) ||
                (block
                        instanceof IAtmosphericChargingBase))
                && par1World.isAirBlock(par2 - 1, par3, par4)
                && par1World.isAirBlock(par2 + 1, par3, par4)
                && par1World.isAirBlock(par2, par3, par4 - 1)
                && par1World.isAirBlock(par2, par3, par4 + 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        capacitorConnector = par1IconRegister.registerIcon(Femtocraft.ID()
                                                                     .toLowerCase()
                + ":" +
                "AtmosphericChargingCapacitor_connector");
        capacitorConnectorBot = par1IconRegister.registerIcon(Femtocraft.ID()
                                                                        .toLowerCase
                                                                                () +
                ":" +
                "AtmosphericChargingCapacitor_connector_bot");
        blockIcon = capacitorSide = par1IconRegister.registerIcon(Femtocraft.ID()
                                                                            .toLowerCase()
                + ":" +
                "AtmosphericChargingCapacitor_side");
        capacitorTop = par1IconRegister.registerIcon(Femtocraft.ID()
                                                               .toLowerCase()
                + ":" +
                "AtmosphericChargingCapacitor_top");
        capacitorBot = par1IconRegister.registerIcon(Femtocraft.ID()
                                                               .toLowerCase()
                + ":" +
                "AtmosphericChargingCapacitor_bot");
    }

    @Override
    public float powerPerTick(World world, int x, int y, int z) {
        float power = 0;
        int offset = 0;
        boolean searchEnded = false;
        Block b;
        do {
            --offset;
            b = Block.blocksList[world.getBlockId(x, y - offset, z)];
            if (b instanceof IAtmosphericChargingBase) {
                searchEnded = true;
            }
            else if (b instanceof IAtmosphericChargingAddon) {
                IAtmosphericChargingAddon addon = (IAtmosphericChargingAddon) b;
                power += addon.powerPerTick(world, x, y - offset, z);
            }
            else {
                searchEnded = true;
            }
        }
        while (!searchEnded);

        if (world.isThundering()) {
            power *= powerMultiplierStorm;
        }
        else if (world.isRaining()) {
            power *= powerMultiplierRain;
        }
        else {
            power *= powerMultiplierBase;
        }

        return power;
    }

    @Override
    public EnumTechLevel techLevel(World world, int x, int y, int z) {
        return EnumTechLevel.MICRO;
    }

    @Override
    public boolean canSupportAddon(IAtmosphericChargingAddon addon, World world, int x, int y, int z) {
        return false;
    }

}