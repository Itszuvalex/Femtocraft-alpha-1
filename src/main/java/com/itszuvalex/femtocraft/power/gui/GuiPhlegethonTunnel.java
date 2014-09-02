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

package com.itszuvalex.femtocraft.power.gui;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.power.containers.ContainerPhlegethonTunnel;
import com.itszuvalex.femtocraft.power.tiles.TileEntityPhlegethonTunnelCore;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GuiPhlegethonTunnel extends GuiContainer {

    public static final ResourceLocation texture = new ResourceLocation(
            Femtocraft.ID.toLowerCase(), "textures/guis/PhlegethonTunnel.png");
    private static final int activateButtonX = 14;
    private static final int activateButtonWidth = 44 - activateButtonX;
    private static final int activateButtonY = 56;
    private static final int activateButtonHeight = 68 - activateButtonY;
    private static final int deactivateButtonX = 133;
    private static final int deactivateButtonWidth = 162 - deactivateButtonX;
    private static final int deactivateButtonY = 56;
    private static final int deactivateButtonHeight = 68 - deactivateButtonY;
    private TileEntityPhlegethonTunnelCore tunnel;

    public GuiPhlegethonTunnel(EntityPlayer player, InventoryPlayer par1InventoryPlayer,
                               TileEntityPhlegethonTunnelCore tunnel) {
        super(new ContainerPhlegethonTunnel(player, par1InventoryPlayer, tunnel));
        this.tunnel = tunnel;
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        if (par3 == 0) {
            int k = (this.width - this.xSize) / 2;
            int l = (this.height - this.ySize) / 2;
            //incrementButton
            if (!tunnel.isActive() && (par1 >= (k + activateButtonX))
                && (par1 <= (k + activateButtonX + activateButtonWidth))
                && (par2 >= (l + activateButtonY))
                && (par2 <= (l + activateButtonY + activateButtonHeight))) {
                Minecraft.getMinecraft().sndManager.playSoundFX("random.click",
                        1.0F, 1.0F);
                tunnel.onActivateClick();
            }

            //decrementButton
            if (tunnel.isActive() && (par1 >= (k + deactivateButtonX))
                && (par1 <= (k + deactivateButtonX + deactivateButtonWidth))
                && (par2 >= (l + deactivateButtonY))
                && (par2 <= (l + deactivateButtonY + deactivateButtonHeight))) {
                Minecraft.getMinecraft().sndManager.playSoundFX("random.click",
                        1.0F, 1.0F);
                tunnel.onDeactivateClick();
            }

            super.mouseClicked(par1, par2, par3);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see net.minecraft.client.gui.inventory.GuiContainer#drawScreen(int, int,
     * float)
     */
    @Override
    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;

        //activateButton
        if (!tunnel.isActive() && (par1 >= (k + activateButtonX))
            && (par1 <= (k + activateButtonX + activateButtonWidth))
            && (par2 >= (l + activateButtonY))
            && (par2 <= (l + activateButtonY + activateButtonHeight))) {
            this.drawGradientRect(
                    k + activateButtonX, l + activateButtonY, k + activateButtonWidth + activateButtonX,
                    l + activateButtonY + activateButtonHeight, FemtocraftUtils.colorFromARGB(60, 45, 0, 110),
                    FemtocraftUtils.colorFromARGB(60, 45, 0, 110)
            );
        }

        //deactivateButton
        if (tunnel.isActive() && (par1 >= (k + deactivateButtonX))
            && (par1 <= (k + deactivateButtonX + deactivateButtonWidth))
            && (par2 >= (l + deactivateButtonY))
            && (par2 <= (l + deactivateButtonY + deactivateButtonHeight))) {
            this.drawGradientRect(
                    k + deactivateButtonX,
                    l + deactivateButtonY,
                    k + deactivateButtonWidth + deactivateButtonX, l + deactivateButtonY + deactivateButtonHeight,
                    FemtocraftUtils.colorFromARGB(60, 45, 0, 110),
                    FemtocraftUtils.colorFromARGB(60, 45, 0, 110)
            );
        }
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String s = "Phlegethon               Tunnel";
        this.fontRenderer.drawString(s,
                (xSize - this.fontRenderer.getStringWidth(s)) / 2, 6,
                FemtocraftUtils.colorFromARGB(0, 255, 255, 255));
        this.fontRenderer.drawString(
                StatCollector.translateToLocal("container.inventory"), 8,
                76,
                FemtocraftUtils.colorFromARGB(0, 255, 255, 255));

        if (!tunnel.isActive()) {
            String activate = "Open";
            this.fontRenderer.drawString(activate,
                    (activateButtonX + (activateButtonWidth) / 2) - ((this.fontRenderer.getStringWidth(activate) /
                                                                      2)), activateButtonY + (activateButtonHeight -
                                                                                              fontRenderer
                                                                                                      .FONT_HEIGHT) /

                                                                                             2,
                    FemtocraftUtils.colorFromARGB(0, 255, 255,
                            255));
        } else {
            String deactivate = "Close";
            this.fontRenderer.drawString(deactivate,
                    (deactivateButtonX + (deactivateButtonWidth) / 2) - ((this.fontRenderer.getStringWidth(deactivate) /
                                                                          2)),
                    deactivateButtonY + (deactivateButtonHeight -
                                         fontRenderer.FONT_HEIGHT) /

                                        2,
                    FemtocraftUtils.colorFromARGB(0, 255, 255,
                            255));
        }

        fontRenderer.drawSplitString(EnumChatFormatting.WHITE +
                                     FemtocraftUtils.formatIntegerString(
                                             String.valueOf(tunnel.getCurrentPower())) + "/\n" +
                                     FemtocraftUtils.formatIntegerString(String.valueOf(tunnel.getMaxPower())), 126,
                16, 40, 40);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2,
                                                   int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        int power = tunnel.getCurrentPower() * 80;
        int max = tunnel.getMaxPower();
        int i1 = ((power > 0) && (max > 0)) ? power / max : 0;
        this.drawTexturedModalRect(k + 52, l + 3 + (80 - i1), 176, 80 - i1,
                70, i1);
    }
}

