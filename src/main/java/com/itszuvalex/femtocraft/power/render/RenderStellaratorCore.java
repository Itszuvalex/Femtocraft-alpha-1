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

import com.itszuvalex.femtocraft.power.blocks.BlockFemtoStellaratorCore;
import com.itszuvalex.femtocraft.proxy.ProxyClient;
import com.itszuvalex.femtocraft.render.RenderModel;
import com.itszuvalex.femtocraft.render.RenderPoint;
import com.itszuvalex.femtocraft.render.RenderUtils;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/10/14.
 */
public class RenderStellaratorCore implements ISimpleBlockRenderingHandler {
    private RenderModel coreModel;

    public RenderStellaratorCore() {

    }

    @java.lang.Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        BlockFemtoStellaratorCore core = (BlockFemtoStellaratorCore)
                block;
        if (core == null) {
            return;
        }

        Tessellator tessellator = Tessellator.instance;

        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_F(1, 1, 1);
        renderCore(core, 0, 0, 0);
        tessellator.draw();

        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    private void renderCore(BlockFemtoStellaratorCore core, int x, int y, int z) {
        if (coreModel == null) {
            createCore(core);
        }

        coreModel.location = new RenderPoint(x, y, z);
        coreModel.draw();
    }

    private void createCore(BlockFemtoStellaratorCore core) {
        coreModel = new RenderModel();

        IIcon icon = core.outsideIcon();
        float minU = icon.getMinU();
        float maxU = icon.getMaxU();
        float minV = icon.getMinV();
        float maxV = icon.getMaxV();

        float min = 0 + .0001f;
        float max = 1 - .0001f;

        float bmin = 0 - .0001f;
        float bmax = 1 + .0001f;
        coreModel.addQuad(RenderUtils.makeNorthFace(bmin, bmax, bmin, bmax, min, icon,
                minU, maxU, minV,
                maxV).flipV());
        coreModel.addQuad(RenderUtils.makeSouthFace(bmin, bmax, bmin, bmax, max, icon,
                minU, maxU, minV,
                maxV).flipV());
        coreModel.addQuad(RenderUtils.makeEastFace(bmin, bmax, bmin, bmax, max, icon,
                minU, maxU, minV,
                maxV).flipU());
        coreModel.addQuad(RenderUtils.makeWestFace(bmin, bmax, bmin, bmax, min, icon,
                minU, maxU, minV,
                maxV).flipV().flipU());
        coreModel.addQuad(RenderUtils.makeTopFace(bmin, bmax, bmin, bmax, max, icon,
                minU, maxU, minV,
                maxV).flipV());
        coreModel.addQuad(RenderUtils.makeBottomFace(bmin, bmax, bmin, bmax, min, icon,
                minU, maxU, minV,
                maxV).flipV());

        icon = core.insideIcon();
        minU = icon.getMinU();
        maxU = icon.getMaxU();
        minV = icon.getMinV();
        maxV = icon.getMaxV();

        min = 6.f / 16.f;
        max = 10.f / 16.f;

        coreModel.addQuad(RenderUtils.makeNorthFace(bmin, bmax, bmin, bmax, max, icon,
                minU, maxU, minV,
                maxV).flipV());
        coreModel.addQuad(RenderUtils.makeSouthFace(bmin, bmax, bmin, bmax, min, icon,
                minU, maxU, minV,
                maxV).flipV());
        coreModel.addQuad(RenderUtils.makeEastFace(bmin, bmax, bmin, bmax, min, icon,
                minU, maxU, minV,
                maxV).flipU());
        coreModel.addQuad(RenderUtils.makeWestFace(bmin, bmax, bmin, bmax, max, icon,
                minU, maxU, minV,
                maxV).flipV().flipU());
        coreModel.addQuad(RenderUtils.makeTopFace(bmin, bmax, bmin, bmax, min, icon,
                minU, maxU, minV,
                maxV).flipV());
        coreModel.addQuad(RenderUtils.makeBottomFace(bmin, bmax, bmin, bmax, max, icon,
                minU, maxU, minV,
                maxV).flipV());

        icon = core.coreIcon();
        minU = icon.getMinU();
        maxU = icon.getMaxU();
        minV = icon.getMinV();
        maxV = icon.getMaxV();

        min = 7.f / 16.f;
        max = 9.f / 16.f;

        coreModel.addQuad(RenderUtils.makeNorthFace(bmin, bmax, bmin, bmax, min, icon,
                minU, maxU, minV,
                maxV).flipV());
        coreModel.addQuad(RenderUtils.makeSouthFace(bmin, bmax, bmin, bmax, max, icon,
                minU, maxU, minV,
                maxV).flipV());
        coreModel.addQuad(RenderUtils.makeEastFace(bmin, bmax, bmin, bmax, max, icon,
                minU, maxU, minV,
                maxV).flipU());
        coreModel.addQuad(RenderUtils.makeWestFace(bmin, bmax, bmin, bmax, min, icon,
                minU, maxU, minV,
                maxV).flipV().flipU());
        coreModel.addQuad(RenderUtils.makeTopFace(bmin, bmax, bmin, bmax, max, icon,
                minU, maxU, minV,
                maxV).flipV());
        coreModel.addQuad(RenderUtils.makeBottomFace(bmin, bmax, bmin, bmax, min, icon,
                minU, maxU, minV,
                maxV).flipV());
    }

    @java.lang.Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
                                    RenderBlocks renderer) {
        BlockFemtoStellaratorCore core = (BlockFemtoStellaratorCore)
                block;
        if (core == null) {
            return false;
        }

        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(
                renderer.blockAccess, x, y, z));
        tessellator.setColorOpaque_F(1, 1, 1);
        renderCore(core, x, y, z);

        return true;
    }

    @java.lang.Override
    public boolean shouldRender3DInInventory(int modelID) {
        return true;
    }

    @java.lang.Override
    public int getRenderId() {
        return ProxyClient.FemtocraftStellaratorCoreRenderID();
    }
}
