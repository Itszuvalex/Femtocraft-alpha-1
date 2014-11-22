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

import com.itszuvalex.femtocraft.power.blocks.BlockCryoEndothermalChargingCoil;
import com.itszuvalex.femtocraft.proxy.ProxyClient;
import com.itszuvalex.femtocraft.render.RenderModel;
import com.itszuvalex.femtocraft.render.RenderPoint;
import com.itszuvalex.femtocraft.render.RenderQuad;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class RenderCryoEndothermalChargingCoil implements
        ISimpleBlockRenderingHandler {
    RenderModel segment;

    public RenderCryoEndothermalChargingCoil() {

    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID,
                                     RenderBlocks renderer) {
        BlockCryoEndothermalChargingCoil coil = (BlockCryoEndothermalChargingCoil) block;
        if (coil == null) {
            return;
        }

        Tessellator tessellator = Tessellator.instance;

        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_F(1, 1, 1);
        renderCoil(coil, 0, 0, 0);
        tessellator.draw();

        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
                                    Block block, int modelId, RenderBlocks renderer) {
        BlockCryoEndothermalChargingCoil coil = (BlockCryoEndothermalChargingCoil) block;
        if (coil == null) {
            return false;
        }

        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(
                renderer.blockAccess, x, y, z));
        tessellator.setColorOpaque_F(1, 1, 1);
        renderCoil(coil, x, y, z);

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelID) {
        return true;
    }

    @Override
    public int getRenderId() {
        return ProxyClient.FemtocraftCryoEndothermalChargingCoilRenderID();
    }

    private void renderCoil(BlockCryoEndothermalChargingCoil coil, int x, int y, int z) {
        if (segment == null) {
            createSegment(coil);
        }

        segment.location = new RenderPoint(x, y, z);
        segment.draw();
        segment.location = new RenderPoint(x, y + 8.f / 16.f, z);
        segment.draw();
    }

    private void createSegment(BlockCryoEndothermalChargingCoil coil) {
        segment = new RenderModel();

        float minY = 0;
        float maxY = 8.f / 16.f;
        float min = 4.f / 16.f;
        float max = 12.f / 16.f;

        RenderQuad connectorNorth = new RenderQuad(new RenderPoint(min, minY, min), new RenderPoint(
                min, maxY, min), new RenderPoint(max, maxY, min), new RenderPoint(max,
                minY, min), coil.coilConnector);
        RenderQuad connectorSouth = new RenderQuad(new RenderPoint(max, minY, max), new RenderPoint(
                max, maxY, max), new RenderPoint(min, maxY, max), new RenderPoint(min,
                minY, max), coil.coilConnector);
        RenderQuad connectorEast = new RenderQuad(new RenderPoint(max, minY, min), new RenderPoint(max,
                maxY, min), new RenderPoint(max, maxY, max),
                new RenderPoint(max, minY, max), coil.coilConnector
        );
        RenderQuad connectorWest = new RenderQuad(new RenderPoint(min, minY, max), new RenderPoint(min,
                maxY, max), new RenderPoint(min, maxY, min),
                new RenderPoint(min, minY, min), coil.coilConnector
        );

        segment.addQuad(connectorNorth);
        segment.addQuad(connectorSouth);
        segment.addQuad(connectorEast);
        segment.addQuad(connectorWest);

        RenderQuad top = new RenderQuad(new RenderPoint(0, maxY, 0), new RenderPoint(0, maxY, 1),
                new RenderPoint(1, maxY, 1), new RenderPoint(1, maxY, 0),
                coil.coilConnectorTop);
        RenderQuad bot = new RenderQuad(new RenderPoint(1, minY, 0), new RenderPoint(1, minY, 1),
                new RenderPoint(0, minY, 1), new RenderPoint(0, minY, 0),
                coil.coilConnectorTop);

        segment.addQuad(top);
        segment.addQuad(bot);
    }
}
