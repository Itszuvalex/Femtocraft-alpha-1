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

package com.itszuvalex.femtocraft.research.gui;

import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.managers.assembler.AssemblerRecipe;
import com.itszuvalex.femtocraft.managers.research.ResearchTechnology;
import com.itszuvalex.femtocraft.managers.research.ResearchTechnologyStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GuiTechnology extends GuiScreen {
    private static final ResourceLocation texture = new ResourceLocation(
            Femtocraft.ID.toLowerCase(), "textures/guis/GuiTechnology.png");
    private final GuiResearch guiResearch;
    private final ResearchTechnologyStatus status;
    private final ResearchTechnology tech;
    private final int xSize = 256;
    private final int ySize = 202;

    private final int backButtonX = 8;
    private final int backButtonY = 11;
    private final int backButtonWidth = 52 - 8;
    private final int backButtonHeight = 26 - 8;

    private final int pageLeftButtonX = 8;
    private final int pageLeftButtonY = 54;
    private final int pageLeftButtonWidth = 25 - 8;
    private final int pageLeftButtonHeight = 71 - 54;

    private final int pageRightButtonX = 88;
    private final int pageRightButtonY = 54;
    private final int pageRightButtonWidth = 106 - 88;
    private final int pageRightButtonHeight = 71 - 54;

    private int displayPage = 1;

    public GuiTechnology(GuiResearch guiResearch,
                         ResearchTechnologyStatus status) {
        this.guiResearch = guiResearch;
        this.status = status;
        this.tech = Femtocraft.researchManager.getTechnology(status.tech);
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        if (par2 == this.mc.gameSettings.keyBindInventory.keyCode) {
            this.mc.displayGuiScreen(guiResearch);
        }
        else {
            super.keyTyped(par1, par2);
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        if (par3 == 0) {
            int k = (this.width - this.xSize) / 2;
            int l = (this.height - this.ySize) / 2;

            if ((par1 >= (k + backButtonX))
                    && (par1 <= (k + backButtonX + backButtonWidth))
                    && (par2 >= (l + backButtonY))
                    && (par2 <= (l + backButtonY + backButtonHeight))) {
                Minecraft.getMinecraft().sndManager.playSoundFX("random.click",
                                                                1.0F, 1.0F);
                this.mc.displayGuiScreen(guiResearch);
            }

            if ((displayPage > 1) && (par1 >= (k + pageLeftButtonX))
                    && (par1 <= (k + pageLeftButtonX + pageLeftButtonWidth))
                    && (par2 >= (l + pageLeftButtonY))
                    && (par2 <= (l + pageLeftButtonY + pageLeftButtonHeight))) {
                Minecraft.getMinecraft().sndManager.playSoundFX("random.click",
                                                                1.0F, 1.0F);
                displayPage--;
            }

            if ((displayPage < getNumPages(status.researched))
                    && (par1 >= (k + pageRightButtonX))
                    && (par1 <= (k + pageRightButtonX + pageRightButtonWidth))
                    && (par2 >= (l + pageRightButtonY))
                    && (par2 <= (l + pageRightButtonY + pageRightButtonHeight))) {
                Minecraft.getMinecraft().sndManager.playSoundFX("random.click",
                                                                1.0F, 1.0F);
                displayPage++;
            }
        }
        super.mouseClicked(par1, par2, par3);
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();

        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        super.drawScreen(par1, par2, par3);

        List tooltip = new ArrayList();
        renderInformation(k + 9, l + 76, 237, 116, displayPage, par1, par2,
                          tooltip, status.researched);

        String s = status.tech;
        this.fontRenderer.drawString(s,
                                     k + this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2,
                                     l + 17, FemtocraftUtils.colorFromARGB(255, 255, 255, 255));

        if ((par1 >= (k + backButtonX))
                && (par1 <= (k + backButtonX + backButtonWidth))
                && (par2 >= (l + backButtonY))
                && (par2 <= (l + backButtonY + backButtonHeight))) {
            this.drawGradientRect(k + backButtonX, l + backButtonY, k
                                          + backButtonWidth + backButtonX, l + backButtonY
                                          + backButtonHeight,
                                  FemtocraftUtils.colorFromARGB(60, 45, 0, 110),
                                  FemtocraftUtils.colorFromARGB(60, 45, 0, 110)
            );
        }

        this.fontRenderer.drawString(
                "Back",
                k + backButtonX + (backButtonWidth) / 2
                        - this.fontRenderer.getStringWidth("Back") / 2, l
                        + backButtonY
                        + (backButtonHeight - this.fontRenderer.FONT_HEIGHT)
                        / 2, FemtocraftUtils.colorFromARGB(255, 255, 255, 255)
        );

        int color = FemtocraftUtils.colorFromARGB(60, 45, 0, 110);
        if (displayPage > 1) {
            if ((par1 >= (k + pageLeftButtonX))
                    && (par1 <= (k + pageLeftButtonX + pageLeftButtonWidth))
                    && (par2 >= (l + pageLeftButtonY))
                    && (par2 <= (l + pageLeftButtonY + pageLeftButtonHeight))) {
                this.drawGradientRect(k + pageLeftButtonX, l + pageLeftButtonY,
                                      k + pageLeftButtonX + pageLeftButtonWidth, l
                                + pageLeftButtonY + pageLeftButtonHeight,
                                      color, color
                );
            }

            this.fontRenderer
                    .drawString(
                            "<-",
                            k + pageLeftButtonX + (pageLeftButtonWidth) / 2
                                    - this.fontRenderer.getStringWidth("<-")
                                    / 2,
                            l
                                    + pageLeftButtonY
                                    + (pageLeftButtonHeight - this.fontRenderer.FONT_HEIGHT)
                                    / 2 + 1,
                            FemtocraftUtils.colorFromARGB(255, 255, 255, 255)
                    );
        }
        else {
            color = FemtocraftUtils.colorFromARGB(60, 0, 0, 0);
            this.drawGradientRect(k + pageLeftButtonX, l + pageLeftButtonY, k
                    + pageLeftButtonX + pageLeftButtonWidth, l
                                          + pageLeftButtonY + pageLeftButtonHeight, color, color);
        }

        color = FemtocraftUtils.colorFromARGB(60, 45, 0, 110);
        if (displayPage < getNumPages(status.researched)) {
            if ((par1 >= (k + pageRightButtonX))
                    && (par1 <= (k + pageRightButtonX + pageRightButtonWidth))
                    && (par2 >= (l + pageRightButtonY))
                    && (par2 <= (l + pageRightButtonY + pageRightButtonHeight))) {
                this.drawGradientRect(k + pageRightButtonX, l
                        + pageRightButtonY, k + pageRightButtonX
                                              + pageRightButtonWidth, l + pageRightButtonY
                                              + pageRightButtonHeight, color, color);
            }

            this.fontRenderer
                    .drawString(
                            "->",
                            k + pageRightButtonX + (pageRightButtonWidth) / 2
                                    - this.fontRenderer.getStringWidth("->")
                                    / 2,
                            l
                                    + pageRightButtonY
                                    + (pageRightButtonHeight - this.fontRenderer.FONT_HEIGHT)
                                    / 2 + 1,
                            FemtocraftUtils.colorFromARGB(255, 255, 255, 255)
                    );
        }
        else {
            color = FemtocraftUtils.colorFromARGB(60, 0, 0, 0);
            this.drawGradientRect(k + pageRightButtonX, l + pageRightButtonY, k
                    + pageRightButtonX + pageRightButtonWidth, l
                                          + pageRightButtonY + pageRightButtonHeight, color, color);
        }

        String pageString = String.format("Page %s/%s", displayPage,
                                          getNumPages(status.researched));
        this.fontRenderer.drawString(pageString, k + pageLeftButtonX
                + (pageLeftButtonWidth + pageRightButtonX - pageLeftButtonX)
                / 2 - this.fontRenderer.getStringWidth(pageString) / 2, l
                                             + pageLeftButtonY
                                             + (pageLeftButtonHeight - this.fontRenderer.FONT_HEIGHT) / 2
                                             + 1, FemtocraftUtils.colorFromARGB(255, 255, 255, 255));

        GL11.glPushMatrix();
        RenderItem renderitem = new RenderItem();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        renderitem.renderItemAndEffectIntoGUI(
                Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft()
                                                                .getTextureManager(), tech.displayItem, k + 66, l + 12
        );

        ItemStack[] materials = null;
        if (tech.researchMaterials != null) {
            materials = new ItemStack[9];
            tech.researchMaterials.toArray(materials);
        }

        this.renderCraftingGrid(k + 194, l + 11, materials, par1, par2, tooltip);

        // if (tech.researchMaterials != null) {
        // int i = 0;
        // for (ItemStack item : tech.researchMaterials) {
        // if (i >= 9)
        // break;
        // renderitem.renderItemAndEffectIntoGUI(
        // Minecraft.getMinecraft().fontRenderer, Minecraft
        // .getMinecraft().getTextureManager(), item, k
        // + 195 + 18 * (i % 3), l + 12 + 18 * (i / 3));
        // i++;
        // }
        // ;
        // }

        this.drawHoveringText(tooltip, par1, par2, this.fontRenderer);

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    protected int getNumPages(boolean researched) {
        return 1;
    }

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     * @param pageNum
     * @param mouseX
     * @param mouseY
     * @param isResearched
     */
    protected void renderInformation(int x, int y, int width, int height,
                                     int pageNum, int mouseX, int mouseY, List tooltip,
                                     boolean isResearched) {

    }

    /**
     * Height must be greater than 54. Width must be MUCH greater than 54, to
     * allow room for the info string to be drawn.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param items
     * @param info
     */
    protected void renderCraftingGridWithInfo(int x, int y, int width,
                                              int height, ItemStack[] items, int mouseX, int mouseY,
                                              List tooltip, String info) {
        int padding = 2;
        renderCraftingGrid(x, y, items, mouseX, mouseY, tooltip);
        info = String.format("%s%s%s", EnumChatFormatting.WHITE, info,
                             EnumChatFormatting.RESET);
        this.fontRenderer.drawSplitString(info, x + 54 + padding, y + padding,
                                          width - 54 - 2 * padding, height - 2 * padding);
    }

    protected void renderAssemblerRecipeWithInfo(int x, int y, int width,
                                                 int height,
                                                 AssemblerRecipe recipe,
                                                 int mouseX, int mouseY,
                                                 List tooltip, String info) {
        int padding = 2;
        renderAssemblerRecipe(x, y, width, height, recipe, mouseX, mouseY, tooltip);
        info = String.format("%s%s%s", EnumChatFormatting.WHITE, info,
                             EnumChatFormatting.RESET);
        fontRenderer.drawSplitString(info, x + 54 + fontRenderer
                                             .getStringWidth("<->") + 18 + 2 * padding, y + 2 * padding,
                                     width - 2 * padding - fontRenderer
                                             .getStringWidth("<->") - 54 - 18,
                                     height - 2 * padding
        );
    }

    protected void renderCraftingRecipe(int x, int y, int width, int height, ItemStack[] input, ItemStack output, int mouseX, int mouseY, List tooltip) {
        String recipeDir = EnumChatFormatting.WHITE + "->" +
                EnumChatFormatting.RESET;
        renderCraftingGrid(x, y, input, mouseX,
                           mouseY, tooltip);
        GL11.glColor4f(1.f, 1.f, 1.f, 1.f);
        fontRenderer.drawSplitString(recipeDir, x + 54 + 1,
                                     y + (54 - fontRenderer
                                             .FONT_HEIGHT)
                                             / 2, fontRenderer.getStringWidth
                        (recipeDir) + 2,
                                     fontRenderer.FONT_HEIGHT
        );
        renderItemSlot(x + 54 + fontRenderer.getStringWidth(recipeDir) + 2,
                       y + 18, output, new RenderItem(), mouseX, mouseY,
                       tooltip);
    }

    protected void renderCraftingRecipeWithInfo(int x, int y, int width, int height, ItemStack[] input, ItemStack output, int mouseX, int mouseY, List tooltip, String info) {
        int padding = 2;
        renderCraftingRecipe(x, y, width, height, input, output, mouseX, mouseY, tooltip);
        info = String.format("%s%s%s", EnumChatFormatting.WHITE, info,
                             EnumChatFormatting.RESET);
        fontRenderer.drawSplitString(info, x + 54 + fontRenderer
                                             .getStringWidth("->") + 18 + 2 * padding, y + 2 * padding,
                                     width - 2 * padding - fontRenderer
                                             .getStringWidth("->") - 54 - 18,
                                     height - 2 * padding
        );
    }


    protected void renderAssemblerRecipe(int x, int y, int width, int height,
                                         AssemblerRecipe recipe, int mouseX,
                                         int mouseY, List tooltip)

    {
        String recipeDir = EnumChatFormatting.WHITE + "<->" +
                EnumChatFormatting.RESET;
        renderCraftingGrid(x, y, recipe.input, mouseX,
                           mouseY, tooltip);
        GL11.glColor4f(1.f, 1.f, 1.f, 1.f);
        fontRenderer.drawSplitString(recipeDir, x + 54 + 1,
                                     y + (54 - fontRenderer
                                             .FONT_HEIGHT)
                                             / 2, fontRenderer.getStringWidth
                        (recipeDir) + 2,
                                     fontRenderer.FONT_HEIGHT
        );
        renderItemSlot(x + 54 + fontRenderer.getStringWidth(recipeDir) + 2,
                       y + 18, recipe.output, new RenderItem(), mouseX, mouseY,
                       tooltip);

    }

    /**
     * 54 width and height
     *
     * @param x     X of Top Left
     * @param y     Y of Top Left
     * @param items Up to 9 items to render into the crafting grid
     */
    protected void renderCraftingGrid(int x, int y, ItemStack[] items,
                                      int mouseX, int mouseY, List tooltip) {
        ItemStack[] ir = items != null ? items : new ItemStack[9];
        RenderItem renderitem = new RenderItem();
        for (int i = 0; (i < 9); ++i) {
            ItemStack item = i >= ir.length ? null : ir[i];
            int xr = x
                    + 18 * (i % 3);
            int yr = y + 18 * (i / 3);
            renderItemSlot(xr, yr, item, renderitem, mouseX, mouseY,
                           tooltip);
        }
    }

    protected void renderItemSlot(int x, int y, ItemStack item,
                                  RenderItem renderitem, int mouseX,
                                  int mouseY, List tooltip) {

        GL11.glColor4f(1.f, 1.f, 1.f, 1.f);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        drawTexturedModalRect(x, y, 194, 11, 18, 18);

        RenderHelper.enableGUIStandardItemLighting();

        if (item == null) {
            RenderHelper.disableStandardItemLighting();
            return;
        }

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);


        int xr = x
                + 1;
        int yr = y + 1;
        renderitem.renderItemIntoGUI(
                Minecraft.getMinecraft().fontRenderer, Minecraft
                        .getMinecraft().getTextureManager(), item, xr,
                yr, true
        );
        renderitem.renderItemOverlayIntoGUI(Minecraft.getMinecraft()
                                                    .fontRenderer,
                                            Minecraft.getMinecraft()
                                                     .getTextureManager()
                , item, xr, yr
        );


        RenderHelper.disableStandardItemLighting();

        if (mouseX > x && mouseX < x + 17 && mouseY > y && mouseY <
                y + 17)

        {
            tooltip.add(item.getDisplayName());
        }

        GL11.glDisable(GL11.GL_LIGHTING);
    }


    protected void drawHoveringText(List par1List, int par2, int par3,
                                    FontRenderer font) {
        if (!par1List.isEmpty()) {
            int k = 0;
            Iterator iterator = par1List.iterator();

            while (iterator.hasNext()) {
                String s = (String) iterator.next();
                int l = font.getStringWidth(s);

                if (l > k) {
                    k = l;
                }
            }

            int i1 = par2 + 12;
            int j1 = par3 - 12;
            int k1 = 8;

            if (par1List.size() > 1) {
                k1 += 2 + (par1List.size() - 1) * 10;
            }

            if (i1 + k > this.width) {
                i1 -= 28 + k;
            }

            if (j1 + k1 + 6 > this.height) {
                j1 = this.height - k1 - 6;
            }

            int l1 = -267386864;
            this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4,
                                  l1, l1);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1,
                                  l1);
            this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3,
                                  l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3
                    - 1, i2, j2);
            this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1
                    + 3 - 1, i2, j2);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2,
                                  i2);
            this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3,
                                  j2, j2);

            for (int k2 = 0; k2 < par1List.size(); ++k2) {
                String s1 = (String) par1List.get(k2);
                font.drawStringWithShadow(s1, i1, j1, -1);
                if (k2 == 0) {
                    j1 += 2;
                }

                j1 += 10;
            }
        }
    }

}
