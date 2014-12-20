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

package com.itszuvalex.femtocraft.research.gui.technology;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.api.AssemblerRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Christopher Harris (Itszuvalex) on 9/17/14.
 */
public class GuiTechnologyAssemblerRenderer implements ITechnologyElementRenderer {
    public final AssemblerRecipe recipe;
    public final String text;
    private final GuiTechnology gui;
    private int y = 0;

    public GuiTechnologyAssemblerRenderer(GuiTechnology gui, ItemStack output, String text) {
        this.gui = gui;
        this.text = text;
        recipe = Femtocraft.recipeManager().assemblyRecipes.getRecipe(output);
    }

    @Override
    public void render(int x, int y, int width, int height, int displayPage, int mouseX, int mouseY, List tooltip,
                       boolean isResearched) {
        gui.renderAssemblerRecipeWithInfo(x, y + getY(), width, height, recipe, mouseX, mouseY, tooltip, text);
    }

    @Override
    public int getWidth() {
        return GuiTechnology.descriptionWidth();
    }

    @Override
    public int getHeight() {
        return Math.max(
                18 * 3 + 2, Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT *
                            Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(text, getWidth()).size());
    }

    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }
}
