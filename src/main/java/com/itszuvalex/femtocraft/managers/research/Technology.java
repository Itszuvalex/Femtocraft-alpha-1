///*
// * ******************************************************************************
// *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
// *  * Itszuvalex@gmail.com
// *  *
// *  * This program is free software; you can redistribute it and/or
// *  * modify it under the terms of the GNU General Public License
// *  * as published by the Free Software Foundation; either version 2
// *  * of the License, or (at your option) any later version.
// *  *
// *  * This program is distributed in the hope that it will be useful,
// *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
// *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *  * GNU General Public License for more details.
// *  *
// *  * You should have received a copy of the GNU General Public License
// *  * along with this program; if not, write to the Free Software
// *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
// *  *****************************************************************************
// */
//
//package com.itszuvalex.femtocraft.managers.research;
//
//import com.itszuvalex.femtocraft.Femtocraft;
//import com.itszuvalex.femtocraft.api.EnumTechLevel;
//import com.itszuvalex.femtocraft.api.core.Configurable;
//import com.itszuvalex.femtocraft.research.gui.GuiResearch;
//import com.itszuvalex.femtocraft.research.gui.technology.GuiTechnology;
//import com.itszuvalex.femtocraft.research.gui.technology.GuiTechnologyDefault;
//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
//import net.minecraft.item.ItemStack;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//import java.util.logging.Level;
//
//public class Technology implements com.itszuvalex.femtocraft.api.research.ITechnology {
//    @Override
//    public String getName() {
//        return name;
//    }
//
//    @Override
//    public String getShortDescription() {
//        return shortDescription;
//    }
//
//    @Override
//    public EnumTechLevel getLevel() {
//        return level;
//    }
//
//    @Override
//    public Collection<String> getPrerequisites() {
//        return Arrays.asList(prerequisites);
//    }
//
//    @Override
//    public ItemStack getDisplayItem() {
//        return displayItem;
//    }
//
//    public int getxDisplay() {
//        return xDisplay;
//    }
//
//    public int getyDisplay() {
//        return yDisplay;
//    }
//
//    @Override
//    public boolean isKeystone() {
//        return isKeystone;
//    }
//
//    @Override
//    public ItemStack[] getResearchMaterials() {
//        return Arrays.asList(researchMaterials);
//    }
//
//    @Override
//    public ItemStack getDiscoverItem() {
//        return discoverItem;
//    }
//
//    @Override
//    public String getResearchedDescription() {
//        return researchedDescription;
//    }
//
//    @Override
//    public String getDiscoveredDescription() {
//        return discoveredDescription;
//    }
//
//    @Override
//    public boolean isDiscoveredByDefault() {
//        return discoveredByDefault;
//    }
//
//    @Override
//    public boolean isResearchedByDefault() {
//        return researchedByDefault;
//    }
//
//    @Configurable(comment = "Name of technology.")
//    protected String name;
//    @Configurable(comment = "Displayed when moused over in Research Tree")
//    protected String shortDescription;
//    @Configurable(comment = "Tech level of Research.  Changes color of lines rendered to this tech in Research Tree")
//    protected EnumTechLevel level;
//    @Configurable(comment = "Names of all prerequisite technologies.")
//    protected String[] prerequisites;
//
//    @Configurable(comment = "What item stack is the icon for this technology.")
//    protected ItemStack displayItem;
//    protected int xDisplay;
//    protected int yDisplay;
//    @Configurable(comment = "True if special background in Research Tree, false if normal")
//    protected boolean isKeystone;
//    @Configurable(comment = "Null for free research, ItemStack[9] (can contain nulls) as required items to put into " +
//                            "research console.")
//    protected ItemStack[] researchMaterials;
//
//    @Configurable(comment = "ItemStack that replaces technology item when used.  This will only ever have a stack " +
//                            "size of 1.")
//    protected ItemStack discoverItem;
//
//    @Configurable(comment =
//            "Description string displayed when Technology is clicked in the research tree.  This is displayed when " +
//            "the Technology has been researched.  This is " +
//            "parsed for recipes and automatically layed out across as many pages as needed.")
//    protected String researchedDescription;
//
//    @Configurable(comment =
//            "Description string displayed when Technology is clicked in the research tree.  This is displayed when " +
//            "the Technology has been discovered but not researched.  This is " +
//            "parsed for recipes and automatically layed out across as many pages as needed.")
//    protected String discoveredDescription;
//
//    @Configurable(comment = "Set this to true to force this to be discovered off the bat.")
//    protected boolean discoveredByDefault;
//
//    @Configurable(comment = "Set this to true to force this to be researched off the bat.")
//    protected boolean researchedByDefault;
//
//    public Technology(String name, String shortDescription,
//                      EnumTechLevel level, String[] prerequisites,
//                      ItemStack displayItem,
//                      boolean isKeystone, ItemStack[] researchMaterials) {
//        this(name, shortDescription, level, prerequisites, displayItem,
//                isKeystone, researchMaterials,
//                null);
//    }
//
//    public Technology(String name, String shortDescription,
//                      EnumTechLevel level, String[] prereq,
//                      ItemStack displayItem,
//                      boolean isKeystone, ItemStack[] resMats,
//                      ItemStack discoverItem) {
//        this(name, shortDescription, level, prereq, displayItem, isKeystone, resMats, discoverItem, "", "", false,
//                false);
//    }
//
//    public Technology(String name, String shortDescription,
//                      EnumTechLevel level, String[] prereq,
//                      ItemStack displayItem,
//                      boolean isKeystone, ItemStack[] resMats,
//                      ItemStack discoverItem, String discoverDescription, String researchDescription,
//                      boolean discoveredByDefault, boolean researchedByDefault) {
//        this.name = name;
//        this.shortDescription = shortDescription;
//        this.level = level;
//        this.prerequisites = prereq == null ? new String[0] : prereq;
//        this.displayItem = displayItem;
//        this.xDisplay = 0;
//        this.yDisplay = 0;
//        this.isKeystone = isKeystone;
//        this.researchMaterials = resMats == null ? new ItemStack[0] : resMats;
//        this.discoverItem = discoverItem;
//        this.discoveredDescription = discoverDescription == null ? "" : discoverDescription;
//        this.researchedDescription = researchDescription == null ? "" : researchDescription;
//        this.discoveredByDefault = discoveredByDefault;
//        this.researchedByDefault = researchedByDefault;
//    }
//
//    // --------------------------------------------------
//
//    private Technology() {
//    }
//
//    public boolean addPrerequisite(Technology prereq) {
//        return addPrerequisite(prereq.name);
//    }
//
//    public boolean addPrerequisite(String prereq) {
//        prerequisites = Arrays.copyOf(prerequisites, prerequisites.length + 1);
//        prerequisites[prerequisites.length - 1] = prereq;
//        return true;
//    }
//
//    public boolean removePrerequisite(Technology prereq) {
//        return removePrerequisite(prereq.name);
//    }
//
//    public boolean removePrerequisite(String name) {
//        if (!hasPrerequisite(name)) return false;
//        List prereqs = Arrays.asList(prerequisites);
//        boolean ret = prereqs.remove(name);
//        if (ret) {
//            prerequisites = (String[]) prereqs.toArray(new String[prereqs.size()]);
//        }
//        return ret;
//    }
//
//    // --------------------------------------------------
//
//    public boolean hasPrerequisite(Technology prereq) {
//        return hasPrerequisite(prereq.name);
//    }
//
//    public boolean hasPrerequisite(String prereq) {
//        return Arrays.asList(prerequisites).contains(prereq);
//    }
//
//    @SideOnly(value = Side.CLIENT)
//    public GuiTechnology getGui(GuiResearch research,
//                                ResearchStatus status) {
//        try {
//            return getGuiClass().getConstructor(GuiResearch.class,
//                    ResearchStatus.class).newInstance(research,
//                    status);
//        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
//                InvocationTargetException | SecurityException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            Femtocraft.logger()
//                    .log(Level.SEVERE,
//                            "Technologies must return a GuiTechnology class that supports the constructor" +
//                            "(GuiResearch, ResearchTechnologyStatus)");
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    @SideOnly(value = Side.CLIENT)
//    public Class<? extends GuiTechnology> getGuiClass() {
//        return (researchedDescription == null || researchedDescription.isEmpty()) && (discoveredDescription == null ||
//                                                                                      discoveredDescription.isEmpty()
//        ) ? GuiTechnologyDefault.class : GuiTechnology.class;
//    }
//}
