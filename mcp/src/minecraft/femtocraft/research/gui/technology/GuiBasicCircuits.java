package femtocraft.research.gui.technology;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import femtocraft.managers.research.ResearchTechnologyStatus;
import femtocraft.research.gui.GuiResearch;
import femtocraft.research.gui.GuiTechnology;

public class GuiBasicCircuits extends GuiTechnology {

	public GuiBasicCircuits(GuiResearch guiResearch,
			ResearchTechnologyStatus status) {
		super(guiResearch, status);
	}

	@Override
	protected void renderInformation(int x, int y, int width, int height,
			int displayPage, boolean isResearched) {
		switch (displayPage) {
		case 1:
			renderCraftingGridWithInfo(
					x,
					y,
					width,
					height,
					new ItemStack[] { new ItemStack(Item.stick),
							new ItemStack(Item.stick),
							new ItemStack(Item.stick) },
					"Combining small pieces of wood, it is possible to create a simple container to support the fragile requirements of a circuit board.");
			break;
		}
	}

	@Override
	protected int getNumPages() {
		return 3;
	}
}
