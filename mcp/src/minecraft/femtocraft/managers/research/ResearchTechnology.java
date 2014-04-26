package femtocraft.managers.research;

import femtocraft.Femtocraft;
import femtocraft.research.gui.GuiResearch;
import femtocraft.research.gui.GuiTechnology;
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
                              ItemStack displayItem, int xDisplay, int yDisplay,
                              boolean isKeystone, ArrayList<ItemStack> researchMaterials) {
        this(name, description, level, prerequisites, displayItem, xDisplay,
             yDisplay, isKeystone, researchMaterials, GuiTechnology.class,
             null);
    }

    public ResearchTechnology(String name, String description,
                              EnumTechLevel level, ArrayList<ResearchTechnology> prerequisites,
                              ItemStack displayItem, int xDisplay, int yDisplay,
                              boolean isKeystone, ArrayList<ItemStack> researchMaterials,
                              Class<? extends GuiTechnology> guiClass, ItemStack discoverItem) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.prerequisites = prerequisites;
        this.displayItem = displayItem;
        this.xDisplay = xDisplay;
        this.yDisplay = yDisplay;
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
