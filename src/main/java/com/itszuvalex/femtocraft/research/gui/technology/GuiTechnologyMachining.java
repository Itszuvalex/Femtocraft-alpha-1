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
import com.itszuvalex.femtocraft.managers.research.ResearchTechnologyStatus;
import com.itszuvalex.femtocraft.research.gui.GuiResearch;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class GuiTechnologyMachining extends GuiTechnology {

    public GuiTechnologyMachining(GuiResearch guiResearch, ResearchTechnologyStatus status) {
        super(guiResearch, status);
    }

    @Override
    protected int getNumPages(boolean researched) {
        return researched ? 1 : 1;
    }

    @Override
    protected void renderInformation(int x, int y, int width, int height,
                                     int displayPage, int mouseX, int mouseY, List tooltip,
                                     boolean isResearched) {
        if (isResearched) {
            switch (displayPage) {
                case 1:
                    this.renderCraftingGridWithInfo(
                            x,
                            y,
                            width,
                            height,
                            new ItemStack[]{
                                    new ItemStack(Femtocraft.itemIngotTemperedTitanium()),
                                    new ItemStack(Femtocraft.itemMicrochip()),
                                    new ItemStack(Femtocraft.itemIngotTemperedTitanium()),
                                    new ItemStack(Femtocraft.itemMicrochip()),
                                    new ItemStack(Femtocraft.itemConductivePowder()),
                                    new ItemStack(Femtocraft.itemMicrochip()),
                                    new ItemStack(Femtocraft.itemIngotTemperedTitanium()),
                                    new ItemStack(Femtocraft.itemMicrochip()),
                                    new ItemStack(Femtocraft.itemIngotTemperedTitanium())},
                            mouseX,
                            mouseY,
                            tooltip,
                            "The resiliency of tempered titanium makes for a solid structure, while the integrated circuits allow for interface with other devices."
                    );
                    break;
            }
        }
        else {
            switch (displayPage) {
                case 1:
                    this.fontRenderer
                            .drawSplitString(
                                    EnumChatFormatting.WHITE
                                            + "Electronic circuits can perform basic logic, but they are far too fragile for actual use, without something to mount them on.  You have a feeling tempered titanium would provide structural integrity."
                                            + EnumChatFormatting.RESET, x + 2,
                                    y + 2, width - 4, height - 4
                            );
                    break;
            }
        }
    }
}
