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

package com.itszuvalex.femtocraft.managers.research;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.research.gui.GuiResearch;
import com.itszuvalex.femtocraft.research.gui.GuiTechnology;
import net.minecraft.item.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;

public class ResearchTechnology {
    public String name;
    public String description;
    public EnumTechLevel level;
    public ArrayList<ResearchTechnology> prerequisites;

    public ItemStack displayItem;
    public int xDisplay;
    public int yDisplay;
    public boolean isKeystone;
    public ArrayList<ItemStack> researchMaterials;

    public ItemStack discoverItem;

    public Class<? extends GuiTechnology> guiClass;

    public ResearchTechnology(String name, String description,
                              EnumTechLevel level, ArrayList<ResearchTechnology> prerequisites,
                              ItemStack displayItem,
                              boolean isKeystone, ArrayList<ItemStack> researchMaterials) {
        this(name, description, level, prerequisites, displayItem,
                isKeystone, researchMaterials, GuiTechnology.class,
                null);
    }

    public ResearchTechnology(String name, String description,
                              EnumTechLevel level, ArrayList<ResearchTechnology> prerequisites,
                              ItemStack displayItem,
                              boolean isKeystone, ArrayList<ItemStack> researchMaterials,
                              Class<? extends GuiTechnology> guiClass, ItemStack discoverItem) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.prerequisites = prerequisites;
        this.displayItem = displayItem;
        this.xDisplay = 0;
        this.yDisplay = 0;
        this.isKeystone = isKeystone;
        this.researchMaterials = researchMaterials;
        this.guiClass = guiClass;
        this.discoverItem = discoverItem;
    }

    // --------------------------------------------------

    private ResearchTechnology() {
    }

    public boolean addPrerequisite(ResearchTechnology prereq) {
        return prerequisites.add(prereq);
    }

    public boolean removePrerequisite(ResearchTechnology prereq) {
        return prerequisites.remove(prereq);
    }

    // --------------------------------------------------

    public boolean hasPrerequisite(ResearchTechnology prereq) {
        return prerequisites.contains(prereq);
    }

    public GuiTechnology getGui(GuiResearch research,
                                ResearchTechnologyStatus status) {
        try {
            return guiClass.getConstructor(GuiResearch.class,
                    ResearchTechnologyStatus.class).newInstance(research,
                    status);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            Femtocraft.logger
                    .log(Level.SEVERE,
                            "Technologies must return a GuiTechnology class that supports the constructor(GuiResearch, ResearchTechnologyStatus)");
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return null;
    }
}
