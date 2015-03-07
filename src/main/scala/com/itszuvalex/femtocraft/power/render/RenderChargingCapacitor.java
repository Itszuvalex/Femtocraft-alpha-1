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

import com.itszuvalex.femtocraft.RenderConstants;
import com.itszuvalex.femtocraft.power.blocks.BlockAtmosphericChargingCapacitor;
import com.itszuvalex.femtocraft.render.RenderModel;
import com.itszuvalex.femtocraft.render.RenderPoint;
import com.itszuvalex.femtocraft.render.RenderUtils;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/5/14.
 */
public class RenderChargingCapacitor implements ISimpleBlockRenderingHandler {
    private RenderModel model;

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        BlockAtmosphericChargingCapacitor capacitor =
                (BlockAtmosphericChargingCapacitor) block;
        if (capacitor == null) {
            return;
        }

        Tessellator tessellator = Tessellator.instance;

        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_F(1, 1, 1);
        renderCapacitor(capacitor, 0, 0, 0);
        tessellator.draw();

        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    private void renderCapacitor(BlockAtmosphericChargingCapacitor block, int x, int y, int z) {
        if (model == null) {
            makeCapacitor(block);
        }
        model.location = new RenderPoint(x, y, z);
        model.draw();
    }

    private void makeCapacitor(BlockAtmosphericChargingCapacitor block) {
        model = new RenderModel();
        float min = 2.f / 16.f;
        float max = 14.f / 16.f;
        model.addQuad(RenderUtils.makeTopFace(min, max, min, max, max,
                block.capacitorTop(),
                block.capacitorTop().getInterpolatedU(2.f),
                block.capacitorTop().getInterpolatedU(14.f),
                block.capacitorTop().getInterpolatedV(2.f),
                block.capacitorTop().getInterpolatedV(14.f)
        ));
        model.addQuad(RenderUtils.makeBottomFace(min, max, min, max, min,
                block.capacitorBot(),
                block.capacitorBot().getInterpolatedU(2.f),
                block.capacitorBot().getInterpolatedU(14.f),
                block.capacitorBot().getInterpolatedV(2.f),
                block.capacitorBot().getInterpolatedV(14.f)
        ));
        float minU = block.capacitorSide().getInterpolatedU(2.f);
        float maxU = block.capacitorSide().getInterpolatedU(14.f);
        float minV = block.capacitorSide().getInterpolatedV(2.f);
        float maxV = block.capacitorSide().getInterpolatedV(14.f);
        model.addQuad(RenderUtils.makeNorthFace(min, max, min, max,
                min,
                block.capacitorSide(), minU,
                maxU, minV, maxV).flipV());
        model.addQuad(RenderUtils.makeEastFace(min, max, min, max, max,
                block.capacitorSide(), minU,
                maxU, minV, maxV).flipV()
                .rotatePointsClockwise()
                .rotatePointsClockwise());
        model.addQuad(RenderUtils.makeWestFace(min, max, min, max, min,
                block.capacitorSide(), minU,
                maxU, minV,
                maxV).flipV().rotatePointsClockwise
                ());
        model.addQuad(RenderUtils.makeSouthFace(min, max, min, max, max,
                block.capacitorSide(), minU,
                maxU, minV, maxV).flipV());

        min = 4.f / 16.f;
        max = 12.f / 16.f;
        float minY = 0.f;
        float maxY = 2.f / 16.f;

        minU = block.capacitorConnector().getMinU();
        maxU = block.capacitorConnector().getMaxU();
        minV = block.capacitorConnector().getInterpolatedV(12.f);
        maxV = block.capacitorConnector().getMaxV();

        model.addQuad(RenderUtils.makeNorthFace(min, max, minY - .0001f, maxY,
                min,
                block.capacitorConnector(), minU, maxU, minV,
                maxV));
        model.addQuad(RenderUtils.makeEastFace(min, max, minY - .0001f, maxY,
                        max,
                        block.capacitorConnector(), minU, maxU, minV,
                        maxV).rotateOnXAxis(-Math.PI /
                                            2.f, .5f, .5f).rotatePointsClockwise
                        ()
        );
        model.addQuad(RenderUtils.makeWestFace(min, max, minY - .0001f, maxY,
                        min,
                        block.capacitorConnector(), minU, maxU, minV,
                        maxV).rotateOnXAxis(-Math.PI /
                                            2.f, .5f, .5f).rotatePointsClockwise
                        ().rotatePointsClockwise
                        ()
        );
        model.addQuad(RenderUtils.makeSouthFace(min, max, minY - .0001f, maxY,
                max,
                block.capacitorConnector(), minU, maxU, minV,
                maxV));

        minU = block.capacitorConnectorBot().getMinU();
        maxU = block.capacitorConnectorBot().getMaxU();
        minV = block.capacitorConnectorBot().getMinV();
        maxV = block.capacitorConnectorBot().getMaxV();

        model.addQuad(RenderUtils.makeBottomFace(min, max, min, max, minY,
                block.capacitorConnectorBot(),
                minU,
                maxU,
                minV,
                maxV));
    }


    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
                                    RenderBlocks renderer) {
        BlockAtmosphericChargingCapacitor capacitor =
                (BlockAtmosphericChargingCapacitor) block;
        if (capacitor == null) {
            return false;
        }
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(
                renderer.blockAccess, x, y, z));
        tessellator.setColorOpaque_F(1, 1, 1);

        renderCapacitor(capacitor, x, y, z);

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelID) {
        return true;
    }

    @Override
    public int getRenderId() {
        return RenderConstants.FemtocraftChargingCapacitorRenderID();
    }
}
