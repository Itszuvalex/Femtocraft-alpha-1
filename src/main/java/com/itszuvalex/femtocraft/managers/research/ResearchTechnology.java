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

package com.itszuvalex.femtocraft.managers.research;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.configuration.Configurable;
import com.itszuvalex.femtocraft.research.gui.GuiResearch;
import com.itszuvalex.femtocraft.research.gui.GuiTechnology;
import com.itszuvalex.femtocraft.research.gui.technology.GuiTechnologyDefault;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;

public class ResearchTechnology {
    @Configurable(comment = "Name of technology.")
    public String name;
    @Configurable(comment = "Displayed when moused over in Research Tree")
    public String description;
    @Configurable(comment = "Tech level of Research.  Changes color of lines rendered to this tech in Research Tree")
    public EnumTechLevel level;
    @Configurable(comment = "Names of all prerequisite technologies.")
    public ArrayList<String> prerequisites;

    @Configurable(comment = "What item stack is the icon for this technology.")
    public ItemStack displayItem;
    public int xDisplay;
    public int yDisplay;
    @Configurable(comment = "True if special background in Research Tree, false if normal")
    public boolean isKeystone;
    @Configurable(comment = "Null for free research, ItemStack[9] (can contain nulls) as required items to put into " +
                            "research console.")
    public ArrayList<ItemStack> researchMaterials;

    @Configurable(comment = "ItemStack that replaces technology item when used.  This will only ever have a stack " +
                            "size of 1.")
    public ItemStack discoverItem;

    public ResearchTechnology(String name, String description,
                              EnumTechLevel level, ArrayList<String> prerequisites,
                              ItemStack displayItem,
                              boolean isKeystone, ArrayList<ItemStack> researchMaterials) {
        this(name, description, level, prerequisites, displayItem,
                isKeystone, researchMaterials,
                null);
    }

    public ResearchTechnology(String name, String description,
                              EnumTechLevel level, ArrayList<String> prerequisites,
                              ItemStack displayItem,
                              boolean isKeystone, ArrayList<ItemStack> researchMaterials,
                              ItemStack discoverItem) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.prerequisites = prerequisites;
        this.displayItem = displayItem;
        this.xDisplay = 0;
        this.yDisplay = 0;
        this.isKeystone = isKeystone;
        this.researchMaterials = researchMaterials;
        this.discoverItem = discoverItem;
    }

    // --------------------------------------------------

    private ResearchTechnology() {
    }

    public boolean addPrerequisite(ResearchTechnology prereq) {
        return prerequisites.add(prereq.name);
    }

    public boolean addPrerequisite(String prereq) {
        return prerequisites.add(prereq);
    }

    public boolean removePrerequisite(ResearchTechnology prereq) {
        return prerequisites.remove(prereq);
    }

    // --------------------------------------------------

    public boolean hasPrerequisite(ResearchTechnology prereq) {
        return prerequisites.contains(prereq);
    }

    @SideOnly(value = Side.CLIENT)
    public GuiTechnology getGui(GuiResearch research,
                                ResearchTechnologyStatus status) {
        try {
            return getGuiClass().getConstructor(GuiResearch.class,
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
                            "Technologies must return a GuiTechnology class that supports the constructor" +
                            "(GuiResearch, ResearchTechnologyStatus)");
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SideOnly(value = Side.CLIENT)
    public Class<? extends GuiTechnology> getGuiClass() {
        return GuiTechnologyDefault.class;
    }
}
