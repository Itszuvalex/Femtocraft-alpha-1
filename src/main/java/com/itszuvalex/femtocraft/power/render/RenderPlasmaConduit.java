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

package com.itszuvalex.femtocraft.power.render;

import com.itszuvalex.femtocraft.power.blocks.BlockPlasmaConduit;
import com.itszuvalex.femtocraft.power.tiles.TileEntityPlasmaConduit;
import com.itszuvalex.femtocraft.proxy.ProxyClient;
import com.itszuvalex.femtocraft.render.RenderModel;
import com.itszuvalex.femtocraft.render.RenderPoint;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import org.lwjgl.opengl.GL11;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/19/14.
 */
public class RenderPlasmaConduit implements ISimpleBlockRenderingHandler {
    private RenderModel center;
    private RenderModel center_filled;
    private RenderModel connector_in_north, connector_in_south, connector_in_east, connector_in_west, connector_in_up, connector_in_down;
    private RenderModel connector_in_north_filled, connector_in_south_filled, connector_in_east_filled, connector_in_west_filled, connector_in_up_filled, connector_in_down_filled;
    private RenderModel connector_out_north, connector_out_uth, connector_out_east, connector_out_west, connector_out_up, connector_out_down;
    private RenderModel connector_out_north_filled, connector_out_south_filled, connector_out_east_filled, connector_out_west_filled, connector_out_up_filled, connector_out_down_filled;
    private boolean initialized = false;

    public RenderPlasmaConduit() {

    }

    private void createConduit(BlockPlasmaConduit conduit) {
        center = new RenderModel(new RenderPoint(0, 0, 0), new RenderPoint(.5f, .5f, .5f));
        center_filled = new RenderModel(new RenderPoint(0, 0, 0), new RenderPoint(.5f, .5f, .5f));


        connector_in_up = new RenderModel(new RenderPoint(0, 0, 0), new RenderPoint(.5f, .5f, .5f));
        connector_in_up_filled = new RenderModel(new RenderPoint(0, 0, 0), new RenderPoint(.5f, .5f, .5f));

        connector_out_up = new RenderModel(new RenderPoint(0, 0, 0), new RenderPoint(.5f, .5f, .5f));
        connector_out_up_filled = new RenderModel(new RenderPoint(0, 0, 0), new RenderPoint(.5f, .5f, .5f));

        initialized = true;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        BlockPlasmaConduit conduit = (BlockPlasmaConduit) block;
        if (conduit == null) {
            return;
        }


        Tessellator tessellator = Tessellator.instance;

        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_F(1, 1, 1);
        renderConduit(conduit, 0, 0, 0, ForgeDirection.UNKNOWN, ForgeDirection.UNKNOWN);
        tessellator.draw();

        GL11.glTranslatef(0.5F, 0.5F, 0.5F);

    }

    private boolean renderConduit(BlockPlasmaConduit conduit, int x, int y, int z, ForgeDirection input, ForgeDirection output) {
        if (initialized) {
            createConduit(conduit);
        }


        return true;
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y,
                                    int z, Block block, int modelId, RenderBlocks renderer) {
        BlockPlasmaConduit conduit = (BlockPlasmaConduit) block;
        if (conduit == null) {
            return false;
        }
        TileEntity tile = renderer.blockAccess.getBlockTileEntity(x, y, z);
        if (tile == null) {
            return false;
        }
        if (!(tile instanceof TileEntityPlasmaConduit)) {
            return false;
        }
        TileEntityPlasmaConduit conduitTile = (TileEntityPlasmaConduit) tile;
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(
                renderer.blockAccess, x, y, z));
        tessellator.setColorOpaque_F(1, 1, 1);
        return renderConduit(conduit, x, y, z, conduitTile.getRenderInputDir(), conduitTile.getRenderOutputDir());
    }

    @Override
    public boolean shouldRender3DInInventory() {
        return true;
    }

    @Override
    public int getRenderId() {
        return ProxyClient.FemtocraftPlasmaConduitID;
    }
}
