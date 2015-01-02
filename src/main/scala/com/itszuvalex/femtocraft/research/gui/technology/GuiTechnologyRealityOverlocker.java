package com.itszuvalex.femtocraft.research.gui.technology;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.managers.research.ResearchStatus;
import com.itszuvalex.femtocraft.research.gui.GuiResearch;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by Chris on 9/10/2014.
 */
public class GuiTechnologyRealityOverlocker extends GuiTechnology {
    public GuiTechnologyRealityOverlocker(GuiResearch guiResearch, ResearchStatus status) {
        super(guiResearch, status);
    }

    @Override
    public void renderInformation(int x, int y, int width, int height, int pageNum, int mouseX, int mouseY, List
            tooltip, boolean isResearched) {
        if (isResearched) {
            switch (pageNum) {
                case 1:
                    renderTemporalRecipeWithInfo(x, y, width, height /
                                                              2, Femtocraft.recipeManager().temporalRecipes().getRecipe
                                    (new ItemStack(Femtocraft.itemInfallibleEstimator())), mouseX, mouseY, tooltip,
                            "Infallible Estimator");
                    renderDimensionalRecipeWithInfo(x,
                            y + height / 2, width, height /
                                                   2, Femtocraft.recipeManager().dimensionalRecipes().getRecipe(new
                                    ItemStack(Femtocraft.itemPandoraCube())), mouseX, mouseY, tooltip, "Pandora Cube");
                    break;
                case 2:
                    renderTemporalRecipeWithInfo(x, y, width, height /
                                                              2, Femtocraft.recipeManager().temporalRecipes().getRecipe
                            (new ItemStack(Femtocraft.itemInfinitelyRecursiveALU())), mouseX, mouseY, tooltip,
                            "Infinite ALU");
                    renderDimensionalRecipeWithInfo(x,
                            y + height / 2, width, height /
                                                   2, Femtocraft.recipeManager().dimensionalRecipes().getRecipe(new
                                    ItemStack(Femtocraft.itemInfiniteVolumePolychora())), mouseX, mouseY, tooltip,
                            "Infinite Polychora");
                    break;
            }
        } else {

        }
    }

    @Override
    public int getNumPages(boolean researched) {
        return researched ? 2 : 1;
    }
}
