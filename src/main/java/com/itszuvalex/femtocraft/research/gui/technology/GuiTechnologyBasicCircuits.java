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

package com.itszuvalex.femtocraft.research.gui.technology;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.managers.research.ResearchTechnologyStatus;
import com.itszuvalex.femtocraft.research.gui.GuiResearch;
import com.itszuvalex.femtocraft.research.gui.GuiTechnology;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class GuiTechnologyBasicCircuits extends GuiTechnology {

    public GuiTechnologyBasicCircuits(GuiResearch guiResearch,
                                      ResearchTechnologyStatus status) {
        super(guiResearch, status);
    }

    @Override
    protected int getNumPages(boolean researched) {
        return 3;
    }

    @Override
    protected void renderInformation(int x, int y, int width, int height,
                                     int displayPage, int mouseX, int mouseY, List tooltip,
                                     boolean isResearched) {
        switch (displayPage) {
            case 1:
                renderCraftingRecipeWithInfo(
                        x,
                        y,
                        width,
                        height / 2,
                        new ItemStack[]{new ItemStack(Item.stick),
                                new ItemStack(Item.stick),
                                new ItemStack(Item.stick)},
                        new ItemStack(Femtocraft.itemBoard),
                        mouseX,
                        mouseY,
                        tooltip,
                        "Combining small pieces of wood, it is possible to create a simple container to support the fragile requirements of a circuit itemBoard."
                );
                renderCraftingRecipeWithInfo(
                        x,
                        y + height / 2,
                        width,
                        height / 2,
                        new ItemStack[]{new ItemStack(Femtocraft.itemIngotFarenite),
                                new ItemStack(Item.dyePowder, 1, 4)},
                        new ItemStack(Femtocraft.itemConductivePowder, 2),
                        mouseX,
                        mouseY,
                        tooltip,
                        "Farenite is an extremely conductive powder, orders of magnitude stronger than Redstone.  By diluting it with Lapis, you find it can both store and transmit power."
                );
                break;
            case 2:
                renderCraftingRecipeWithInfo(
                        x,
                        y,
                        width,
                        height / 2,
                        new ItemStack[]{new ItemStack(Block.planks), null,
                                new ItemStack(Block.planks),
                                new ItemStack(Block.planks),
                                new ItemStack(Item.stick),
                                new ItemStack(Block.planks),
                                new ItemStack(Block.planks), null,
                                new ItemStack(Block.planks)},
                        new ItemStack(Femtocraft.itemSpool),
                        mouseX,
                        mouseY,
                        tooltip,
                        "Spools allow for easy storage and retrieval of long threads of thin metal.  Perfect for holding wires."
                );
                renderCraftingRecipeWithInfo(
                        x,
                        y + height / 2,
                        width,
                        height / 2,
                        new ItemStack[]{new ItemStack(Item.ingotGold),
                                new ItemStack(Item.ingotGold),
                                new ItemStack(Item.ingotGold),
                                new ItemStack(Item.ingotGold),
                                new ItemStack(Femtocraft.itemSpool),
                                new ItemStack(Item.ingotGold),
                                new ItemStack(Item.ingotGold),
                                new ItemStack(Item.ingotGold),
                                new ItemStack(Item.ingotGold)},
                        new ItemStack(Femtocraft.itemSpoolGold),
                        mouseX,
                        mouseY,
                        tooltip,
                        "Gold makes for an excellent conductor, making it the perfect choice for wiring circuits."
                );
                break;
            case 3:
                renderCraftingRecipeWithInfo(
                        x,
                        y,
                        width,
                        height / 2,
                        new ItemStack[]{
                                new ItemStack(Femtocraft.itemConductivePowder), null,
                                null, new ItemStack(Femtocraft.itemBoard)},
                        new ItemStack(Femtocraft.itemPrimedBoard),
                        mouseX,
                        mouseY,
                        tooltip,
                        "Filling a itemBoard with conductive powder, then running it through an oven, produces a itemBoard suitable for wiring."
                );
                renderCraftingRecipeWithInfo(x, y + height / 2, width, height / 2,
                        new ItemStack[]{new ItemStack(Femtocraft.itemSpoolGold),
                                null, null, new ItemStack(Femtocraft.itemDopedBoard)},
                        new ItemStack(Femtocraft.itemMicrochip),
                        mouseX, mouseY, tooltip,
                        "Simply wire the connections using gold wiring to produce the needed circuit."
                );
                break;
        }
    }
}
