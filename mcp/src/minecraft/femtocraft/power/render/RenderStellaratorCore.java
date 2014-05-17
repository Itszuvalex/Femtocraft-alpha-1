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

package femtocraft.power.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import femtocraft.power.blocks.BlockFemtoStellaratorCore;
import femtocraft.proxy.ProxyClient;
import femtocraft.render.RenderModel;
import femtocraft.render.RenderPoint;
import femtocraft.render.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
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
        tessellator.setBrightness(0xfffff);
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

        Icon icon = core.outsideIcon;
        float minU = icon.getMinU();
        float maxU = icon.getMaxU();
        float minV = icon.getMinV();
        float maxV = icon.getMaxV();

        float min = 0;
        float max = 1;

        coreModel.addQuad(RenderUtils.makeNorthFace(0, 1, 0, 1, min, icon,
                                                    minU, maxU, minV, maxV));
        coreModel.addQuad(RenderUtils.makeSouthFace(0, 1, 0, 1, max, icon,
                                                    minU, maxU, minV, maxV));
        coreModel.addQuad(RenderUtils.makeEastFace(0, 1, 0, 1, max, icon,
                                                   minU, maxU, minV, maxV));
        coreModel.addQuad(RenderUtils.makeWestFace(0, 1, 0, 1, min, icon,
                                                   minU, maxU, minV, maxV));
        coreModel.addQuad(RenderUtils.makeTopFace(0, 1, 0, 1, max, icon,
                                                  minU, maxU, minV, maxV));
        coreModel.addQuad(RenderUtils.makeBottomFace(0, 1, 0, 1, min, icon,
                                                     minU, maxU, minV, maxV));

        icon = core.insideIcon;
        minU = icon.getMinU();
        maxU = icon.getMaxU();
        minV = icon.getMinV();
        maxV = icon.getMaxV();

        min = 6.f / 16.f;
        max = 10.f / 16.f;

        coreModel.addQuad(RenderUtils.makeNorthFace(0, 1, 0, 1, max, icon,
                                                    minU, maxU, minV, maxV));
        coreModel.addQuad(RenderUtils.makeSouthFace(0, 1, 0, 1, min, icon,
                                                    minU, maxU, minV, maxV));
        coreModel.addQuad(RenderUtils.makeEastFace(0, 1, 0, 1, min, icon,
                                                   minU, maxU, minV, maxV));
        coreModel.addQuad(RenderUtils.makeWestFace(0, 1, 0, 1, max, icon,
                                                   minU, maxU, minV, maxV));
        coreModel.addQuad(RenderUtils.makeTopFace(0, 1, 0, 1, min, icon,
                                                  minU, maxU, minV, maxV));
        coreModel.addQuad(RenderUtils.makeBottomFace(0, 1, 0, 1, max, icon,
                                                     minU, maxU, minV, maxV));

        icon = core.coreIcon;
        minU = icon.getMinU();
        maxU = icon.getMaxU();
        minV = icon.getMinV();
        maxV = icon.getMaxV();

        min = 7.f / 16.f;
        max = 9.f / 16.f;

        coreModel.addQuad(RenderUtils.makeNorthFace(0, 1, 0, 1, max, icon,
                                                    minU, maxU, minV, maxV));
        coreModel.addQuad(RenderUtils.makeSouthFace(0, 1, 0, 1, min, icon,
                                                    minU, maxU, minV, maxV));
        coreModel.addQuad(RenderUtils.makeEastFace(0, 1, 0, 1, min, icon,
                                                   minU, maxU, minV, maxV));
        coreModel.addQuad(RenderUtils.makeWestFace(0, 1, 0, 1, max, icon,
                                                   minU, maxU, minV, maxV));
        coreModel.addQuad(RenderUtils.makeTopFace(0, 1, 0, 1, min, icon,
                                                  minU, maxU, minV, maxV));
        coreModel.addQuad(RenderUtils.makeBottomFace(0, 1, 0, 1, max, icon,
                                                     minU, maxU, minV, maxV));
    }

    @java.lang.Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
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
    public boolean shouldRender3DInInventory() {
        return true;
    }

    @java.lang.Override
    public int getRenderId() {
        return ProxyClient.FemtocraftStellaratorCoreRenderID;
    }
}
