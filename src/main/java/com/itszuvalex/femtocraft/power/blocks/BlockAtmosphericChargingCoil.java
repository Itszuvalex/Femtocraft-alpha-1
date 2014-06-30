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

import com.itszuvalex.femtocraft.api.IAtmosphericChargingAddon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.api.IAtmosphericChargingBase;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.proxy.ProxyClient;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAtmosphericChargingCoil extends Block implements IAtmosphericChargingAddon {
    public Icon coilConnector;
    public Icon coilConnectorTop;

    public BlockAtmosphericChargingCoil(int par1) {
        super(par1, Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab);
        setUnlocalizedName("BlockAtmosphericChargingCoil");
        setHardness(1.0f);
        setStepSound(Block.soundMetalFootstep);
        setBlockBounds(4.f / 16.f, 0, 4.f / 16.f, 12.f / 16.f, 1, 12.f / 16.f);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return ProxyClient.FemtocraftChargingCoilRenderID;
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
        coilConnector = par1IconRegister.registerIcon(Femtocraft.ID
                                                              .toLowerCase() + ":" + "MicroChargingCoil_connector");
        coilConnectorTop = par1IconRegister.registerIcon(Femtocraft.ID
                                                                 .toLowerCase() + ":" + "MicroChargingCoil_coilConnectorTop");
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
    public float powerPerTick(World world, int x, int y, int z) {
        return .1f;
    }

    @Override
    public EnumTechLevel techLevel(World world, int x, int y, int z) {
        return EnumTechLevel.MICRO;
    }

    @Override
    public boolean canSupportAddon(IAtmosphericChargingAddon addon, World world, int x, int y, int z) {
        return true;
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

}
