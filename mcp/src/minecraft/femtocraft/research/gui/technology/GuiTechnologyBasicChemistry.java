package femtocraft.research.gui.technology;

import femtocraft.Femtocraft;
import femtocraft.managers.assembler.AssemblerRecipe;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.managers.research.ResearchTechnologyStatus;
import femtocraft.research.gui.GuiResearch;
import femtocraft.research.gui.GuiTechnology;

import java.util.ArrayList;
import java.util.List;

public class GuiTechnologyBasicChemistry extends GuiTechnology {
    private final ArrayList<AssemblerRecipe> recipes;

    public GuiTechnologyBasicChemistry(GuiResearch guiResearch,
                                       ResearchTechnologyStatus status) {
        super(guiResearch, status);
        recipes = Femtocraft.recipeManager.assemblyRecipes
                .getRecipesForTechLevel(EnumTechLevel.MICRO);
    }

    @Override
    protected void renderInformation(int x, int y, int width, int height,
                                     int displayPage, int mouseX, int mouseY, List tooltip,
                                     boolean isResearched) {
        int index = (displayPage - 1) * 2;
        AssemblerRecipe recipe = recipes.get(index);

        renderCraftingGridWithInfo(x, y, width, height / 2, recipe.input,
                                   mouseX, mouseY, tooltip, recipe.output.getDisplayName());
        if (++index < recipes.size()) {
            AssemblerRecipe recipe1 = recipes.get(index);
            renderCraftingGridWithInfo(x, y + (height / 2), width, height / 2,
                                       recipe1.input, mouseX, mouseY, tooltip,
                                       recipe1.output.getDisplayName());
        }
    }

    @Override
    protected int getNumPages(boolean researched) {
        return (int) Math.ceil(recipes.size() / 2.f);
    }
}
