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

package com.itszuvalex.femtocraft.research.gui;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.managers.research.ResearchTechnology;
import com.itszuvalex.femtocraft.research.containers.ContainerResearchConsole;
import com.itszuvalex.femtocraft.research.tiles.TileEntityResearchConsole;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class GuiResearchConsole extends GuiContainer {
    private static final ResourceLocation texture = new ResourceLocation(
            Femtocraft.ID.toLowerCase(), "textures/guis/ResearchConsole.png");
    private static final int xSize = 176;
    private static final int ySize = 166;

    private static final int researchButtonX = 63;
    private static final int researchButtonWidth = 106 - researchButtonX;
    private static final int researchButtonY = 35;
    private static final int researchButtonHeight = 53 - researchButtonY;

    private final TileEntityResearchConsole console;

    public GuiResearchConsole(InventoryPlayer par1InventoryPlayer,
                              TileEntityResearchConsole console) {
        super(new ContainerResearchConsole(par1InventoryPlayer, console));
        this.console = console;
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

        if ((par1 >= (k + researchButtonX))
            && (par1 <= (k + researchButtonX + researchButtonWidth))
            && (par2 >= (l + researchButtonY))
            && (par2 <= (l + researchButtonY + researchButtonHeight))) {
            this.drawGradientRect(k + researchButtonX, l + researchButtonY, k
                                                                            + researchButtonWidth + researchButtonX, l
                                                                                                                     +
                                                                                                                     researchButtonY +
                                                                                                                     researchButtonHeight,
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
        String s = "Research Console";
        this.fontRenderer.drawString(s,
                this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6,
                FemtocraftUtils.colorFromARGB(0, 255, 255, 255));
        this.fontRenderer.drawString(
                StatCollector.translateToLocal("container.inventory"), 8,
                this.ySize - 96 + 2,
                FemtocraftUtils.colorFromARGB(0, 255, 255, 255));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        int progress = console.getResearchProgressScaled(78);
        this.drawTexturedModalRect(k + 64, l + 65, 0, 166, progress, 6);

        if (console.displayTech != null || console.isResearching()) {

            String name = null;

            if (console.isResearching()) {
                name = console.getResearchingName();
            } else {
                name = console.displayTech;
            }

            ResearchTechnology tech = Femtocraft.researchManager
                    .getTechnology(name);
            if (tech != null) {

                String s = tech.name;
                this.fontRenderer.drawString(s, k + 71
                                                + (165 - 71 - this.fontRenderer.getStringWidth(s)) / 2,
                        l + 20,
                        FemtocraftUtils.colorFromARGB(255, 255, 255, 255)
                );

                if (console.isResearching()) {
                    s = String.format("%d%s",
                            console.getResearchProgressScaled(100), "%");
                    this.fontRenderer.drawString(s,
                            k + 168 - this.fontRenderer.getStringWidth(s),
                            l + 40,
                            FemtocraftUtils.colorFromARGB(255, 255, 255, 255));
                } else {
                    s = "Begin";
                    this.fontRenderer.drawString(s,
                            k + 85 - this.fontRenderer.getStringWidth(s) / 2, l
                                                                              + 36
                                                                              +
                                                                              (52 - 36 - this.fontRenderer.FONT_HEIGHT)
                                                                              / 2,
                            FemtocraftUtils.colorFromARGB(255, 255, 255, 255)
                    );
                }

                RenderItem render = new RenderItem();
                RenderHelper.enableGUIStandardItemLighting();
                render.renderItemAndEffectIntoGUI(fontRenderer, Minecraft
                                .getMinecraft().getTextureManager(), tech.displayItem,
                        k + 110, l + 33
                );
            }

        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        if (par3 == 0) {
            int k = (this.width - this.xSize) / 2;
            int l = (this.height - this.ySize) / 2;

            if ((par1 >= (k + researchButtonX))
                && (par1 <= (k + researchButtonX + researchButtonWidth))
                && (par2 >= (l + researchButtonY))
                && (par2 <= (l + researchButtonY + researchButtonHeight))) {
                Minecraft.getMinecraft().sndManager.playSoundFX("random.click",
                        1.0F, 1.0F);
                onResearchClick();
            }
        }
        super.mouseClicked(par1, par2, par3);
    }

    private void onResearchClick() {
        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = TileEntityResearchConsole.PACKET_CHANNEL;

        ByteArrayOutputStream bos = new ByteArrayOutputStream(14);
        DataOutputStream outputStream = new DataOutputStream(bos);
        try {
            outputStream.writeInt(console.xCoord);
            outputStream.writeInt(console.yCoord);
            outputStream.writeInt(console.zCoord);
            outputStream.writeInt(console.worldObj.provider.dimensionId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        packet.data = bos.toByteArray();
        packet.length = bos.size();

        PacketDispatcher.sendPacketToServer(packet);
    }

}
