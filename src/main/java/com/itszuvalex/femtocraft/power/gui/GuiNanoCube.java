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

package com.itszuvalex.femtocraft.power.gui;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.power.containers.ContainerNanoCube;
import com.itszuvalex.femtocraft.power.tiles.TileEntityNanoCubePort;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiNanoCube extends GuiContainer {
    public static final ResourceLocation texture = new ResourceLocation(
            Femtocraft.ID.toLowerCase(), "textures/guis/NanoCube.png");
    private final TileEntityNanoCubePort controller;

    public GuiNanoCube(TileEntityNanoCubePort controller) {
        super(new ContainerNanoCube(controller));
        this.controller = controller;
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String s = "Nano-Cube";
        this.fontRenderer.drawString(s,
                this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6,
                FemtocraftUtils.colorFromARGB(0, 255, 255, 255));

        String power = FemtocraftUtils.formatIntegerToString(controller
                .getCurrentPower()) + "/"
                + FemtocraftUtils.formatIntegerToString(controller
                .getMaxPower());
        this.fontRenderer.drawString(power,
                this.xSize / 2 - this.fontRenderer.getStringWidth(power) / 2,
                this.ySize * 4 / 5,
                FemtocraftUtils.colorFromARGB(0, 255, 255, 255));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        int power = controller.getCurrentPower() * 82;
        int max = controller.getMaxPower();
        int i1 = (int) (((power > 0) && (max > 0)) ? power / max : 0);
        this.drawTexturedModalRect(k + 52, l + 33 + (82 - i1), 176, 82 - i1,
                70, i1);
    }

}
