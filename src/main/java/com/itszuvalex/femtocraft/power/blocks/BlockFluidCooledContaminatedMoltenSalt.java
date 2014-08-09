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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.BlockFluidClassic;

/**
 * Created by Christopher Harris (Itszuvalex) on 8/2/14.
 */
public class BlockFluidCooledContaminatedMoltenSalt extends BlockFluidClassic {
    @SideOnly(Side.CLIENT)
    public Icon stillIcon;
    @SideOnly(Side.CLIENT)
    public Icon flowingIcon;

    public BlockFluidCooledContaminatedMoltenSalt(int id) {
        super(id, Femtocraft.fluidCooledContaminatedMoltenSalt, Material.water);
        setUnlocalizedName("FluidCooledContamiantedMoltenSalt");
        setCreativeTab(Femtocraft.femtocraftTab);
        Femtocraft.fluidCooledContaminatedMoltenSalt.setBlockID(id);
    }

    @Override
    public Icon getIcon(int side, int meta) {
        return (side == 0 || side == 1) ? stillIcon : flowingIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        blockIcon = stillIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                + ":" + "BlockCooledContaminatedMoltenSalt_still");
        flowingIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                + ":" + "BlockCooledContaminatedMoltenSalt_flow");
    }
}