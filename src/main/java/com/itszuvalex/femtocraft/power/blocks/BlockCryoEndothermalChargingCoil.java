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
import com.itszuvalex.femtocraft.core.blocks.TileContainer;
import com.itszuvalex.femtocraft.power.tiles.TileEntityCryoEndothermalChargingCoil;
import com.itszuvalex.femtocraft.proxy.ProxyClient;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/6/14.
 */
public class BlockCryoEndothermalChargingCoil extends TileContainer {
    public IIcon coilConnector, coilConnectorTop;

    public BlockCryoEndothermalChargingCoil() {
        super(Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab());
        setBlockName("BlockCryoEndothermalChargingCoil");
        setBlockBounds(4.f / 16.f, 0, 4.f / 16.f, 12.f / 16.f, 1, 12.f / 16.f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        blockIcon = coilConnector = par1IconRegister.registerIcon(Femtocraft.ID()
                                                                          .toLowerCase() + ":" +
                                                                  "BlockCryoEndothermalChargingCoil_connector");
        coilConnectorTop = par1IconRegister.registerIcon(Femtocraft.ID()
                                                                 .toLowerCase() + ":" +
                                                         "BlockCryoEndothermalChargingCoil_connector_top");
    }


    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return ProxyClient.FemtocraftCryoEndothermalChargingCoilRenderID();
    }

    @Override
    public boolean isBlockSolid(IBlockAccess par1iBlockAccess, int par2,
                                int par3, int par4, int par5) {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityCryoEndothermalChargingCoil();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}