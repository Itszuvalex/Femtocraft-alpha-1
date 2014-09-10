package com.itszuvalex.femtocraft.research.gui.technology;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.managers.research.ResearchTechnologyStatus;
import com.itszuvalex.femtocraft.research.gui.GuiResearch;
import com.itszuvalex.femtocraft.research.gui.GuiTechnology;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Chris on 9/10/2014.
 */
public class GuiTechnologyRealityOverlocker extends GuiTechnology {
    @Override
    protected void renderInformation(int x, int y, int width, int height, int pageNum, int mouseX, int mouseY, List
            tooltip, boolean isResearched) {
        if (isResearched) {
            switch (pageNum) {
                case 1:
                    renderTemporalRecipeWithInfo(x, y, width, height /
                                                              2, Femtocraft.recipeManager.temporalRecipes.getRecipe
                                    (new ItemStack(Femtocraft.itemInfallibleEstimator)), mouseX, mouseY, tooltip,
                            "Infallible Estimator");
                    break;
                case 2:
                    break;
            }
        } else {

        }
    }

    @Override
    protected int getNumPages(boolean researched) {
        return researched ? 2 : 1;
    }

    public GuiTechnologyRealityOverlocker(GuiResearch guiResearch, ResearchTechnologyStatus status) {
        super(guiResearch, status);
    }
}
