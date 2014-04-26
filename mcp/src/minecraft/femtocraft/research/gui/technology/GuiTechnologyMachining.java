package femtocraft.research.gui.technology;

import femtocraft.Femtocraft;
import femtocraft.managers.research.ResearchTechnologyStatus;
import femtocraft.research.gui.GuiResearch;
import femtocraft.research.gui.GuiTechnology;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class GuiTechnologyMachining extends GuiTechnology {

    public GuiTechnologyMachining(GuiResearch guiResearch, ResearchTechnologyStatus status) {
        super(guiResearch, status);
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
                                    new ItemStack(Femtocraft.ingotTemperedTitanium),
                                    new ItemStack(Femtocraft.microCircuitBoard),
                                    new ItemStack(Femtocraft.ingotTemperedTitanium),
                                    new ItemStack(Femtocraft.microCircuitBoard),
                                    new ItemStack(Femtocraft.conductivePowder),
                                    new ItemStack(Femtocraft.microCircuitBoard),
                                    new ItemStack(Femtocraft.ingotTemperedTitanium),
                                    new ItemStack(Femtocraft.microCircuitBoard),
                                    new ItemStack(Femtocraft.ingotTemperedTitanium)},
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

    @Override
    protected int getNumPages(boolean researched) {
        return researched ? 1 : 1;
    }
}
