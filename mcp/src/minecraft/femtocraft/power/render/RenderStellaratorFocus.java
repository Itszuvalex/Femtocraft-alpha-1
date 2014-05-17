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
import femtocraft.power.blocks.BlockFemtoStellaratorFocus;
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
public class RenderStellaratorFocus implements ISimpleBlockRenderingHandler {
    private RenderModel focusModel;

    public RenderStellaratorFocus() {

    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        BlockFemtoStellaratorFocus focus = (BlockFemtoStellaratorFocus)
                block;
        if (focus == null) {
            return;
        }

        Tessellator tessellator = Tessellator.instance;

        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        tessellator.startDrawingQuads();
        tessellator.setBrightness(983055);
        tessellator.setColorOpaque_F(1, 1, 1);
        renderFocus(focus, 0, 0, 0);
        tessellator.draw();

        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    private void renderFocus(BlockFemtoStellaratorFocus focus, int x, int y, int z) {
        if (focusModel == null) {
            createFocus(focus);
        }

        focusModel.location = new RenderPoint(x, y, z);
        focusModel.draw();
    }

    private void createFocus(BlockFemtoStellaratorFocus focus) {
        focusModel = new RenderModel();

        Icon icon = focus.outsideIcon;
        float minU = icon.getMinU();
        float maxU = icon.getMaxU();
        float minV = icon.getMinV();
        float maxV = icon.getMaxV();

        float min = 0;
        float max = 1;

        focusModel.addQuad(RenderUtils.makeNorthFace(0, 1, 0, 1, min, icon,
                                                     minU, maxU, minV, maxV));
        focusModel.addQuad(RenderUtils.makeSouthFace(0, 1, 0, 1, max, icon,
                                                     minU, maxU, minV, maxV));
        focusModel.addQuad(RenderUtils.makeEastFace(0, 1, 0, 1, max, icon,
                                                    minU, maxU, minV, maxV));
        focusModel.addQuad(RenderUtils.makeWestFace(0, 1, 0, 1, min, icon,
                                                    minU, maxU, minV, maxV));
        focusModel.addQuad(RenderUtils.makeTopFace(0, 1, 0, 1, max, icon,
                                                   minU, maxU, minV, maxV));
        focusModel.addQuad(RenderUtils.makeBottomFace(0, 1, 0, 1, min, icon,
                                                      minU, maxU, minV, maxV));

        icon = focus.insideIcon;
        minU = icon.getMinU();
        maxU = icon.getMaxU();
        minV = icon.getMinV();
        maxV = icon.getMaxV();

        min = 6.f / 16.f;
        max = 10.f / 16.f;

        focusModel.addQuad(RenderUtils.makeNorthFace(0, 1, 0, 1, max, icon,
                                                     minU, maxU, minV, maxV));
        focusModel.addQuad(RenderUtils.makeSouthFace(0, 1, 0, 1, min, icon,
                                                     minU, maxU, minV, maxV));
        focusModel.addQuad(RenderUtils.makeEastFace(0, 1, 0, 1, min, icon,
                                                    minU, maxU, minV, maxV));
        focusModel.addQuad(RenderUtils.makeWestFace(0, 1, 0, 1, max, icon,
                                                    minU, maxU, minV, maxV));
        focusModel.addQuad(RenderUtils.makeTopFace(0, 1, 0, 1, min, icon,
                                                   minU, maxU, minV, maxV));
        focusModel.addQuad(RenderUtils.makeBottomFace(0, 1, 0, 1, max, icon,
                                                      minU, maxU, minV, maxV));

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        BlockFemtoStellaratorFocus focus = (BlockFemtoStellaratorFocus)
                block;
        if (focus == null) {
            return false;
        }

        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(
                renderer.blockAccess, x, y, z));
        tessellator.setColorOpaque_F(1, 1, 1);
        renderFocus(focus, x, y, z);

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory() {
        return true;
    }

    @Override
    public int getRenderId() {
        return ProxyClient.FemtocraftStellaratorFocusRenderID;
    }
}
