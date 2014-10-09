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
import com.itszuvalex.femtocraft.api.power.IAtmosphericChargingBase;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.power.tiles.TileEntityAtmosphericChargingBase;
import com.itszuvalex.femtocraft.proxy.ProxyClient;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockAtmosphericChargingBase extends BlockPowerContainer implements
                                                                      IAtmosphericChargingBase {
    public Icon side;
    public Icon top;
    public Icon bottom;
    public Icon side_inset;
    public Icon coil_inset;
    public Icon coil_column_inset;
    public Icon top_inset;
    public Icon coil_top_inset;
    public Icon top_pillar_top;
    public Icon top_pillar_side;

    public BlockAtmosphericChargingBase(int par1) {
        super(par1, Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab());
        setUnlocalizedName("BlockAtmosphericChargingBase");
        setHardness(2.0f);
        setStepSound(Block.soundMetalFootstep);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityAtmosphericChargingBase();
    }

    @Override
    public int getRenderType() {
        return ProxyClient.FemtocraftChargingBaseRenderID;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        return canBlockStay(par1World, par2, par3, par4);
    }

    @Override
    public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
        Block block = Block.blocksList[par1World.getBlockId(par2 - 1, par3,
                par4)];
        if (block instanceof IAtmosphericChargingBase) {
            return false;
        }

        block = Block.blocksList[par1World.getBlockId(par2 + 1, par3, par4)];
        if (block instanceof IAtmosphericChargingBase) {
            return false;
        }

        block = Block.blocksList[par1World.getBlockId(par2, par3, par4 - 1)];
        if (block instanceof IAtmosphericChargingBase) {
            return false;
        }

        block = Block.blocksList[par1World.getBlockId(par2, par3, par4 + 1)];
        return !(block instanceof IAtmosphericChargingBase);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        side = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":"
                + "ChargingBase_side");
        top = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() + ":"
                + "ChargingBase_top");
        // bottom = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase() +
        // ":" + "ChargingBase_bottom");
        bottom = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                + ":" + "MicroMachineBlock_side");
        side_inset = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                + ":" + "ChargingBase_side_inset");
        coil_inset = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                + ":" + "ChargingBase_coil_inset");
        coil_column_inset = par1IconRegister.registerIcon(Femtocraft.ID()
                                                                    .toLowerCase() + ":" + "ChargingBase_coil_column_inset");
        top_inset = par1IconRegister.registerIcon(Femtocraft.ID().toLowerCase()
                + ":" + "ChargingBase_top_inset");
        coil_top_inset = par1IconRegister.registerIcon(Femtocraft.ID()
                                                                 .toLowerCase() + ":" + "ChargingBase_coil_top_inset");
        top_pillar_top = par1IconRegister.registerIcon(Femtocraft.ID()
                                                                 .toLowerCase() + ":" + "ChargingBase_top_pillar_top");
        top_pillar_side = par1IconRegister.registerIcon(Femtocraft.ID()
                                                                  .toLowerCase() + ":" + "ChargingBase_top_pillar_side");
    }

    @Override
    public int maxAddonsSupported(World world, int x, int y, int z) {
        return 10;
    }

    @Override
    public EnumTechLevel maxTechSupported(World world, int x, int y, int z) {
        return EnumTechLevel.MICRO;
    }

}
