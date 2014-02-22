package femtocraft.research.technology;

import net.minecraft.item.ItemStack;
import femtocraft.Femtocraft;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.managers.research.ResearchTechnology;

public class TechnologyBasicCircuits extends ResearchTechnology {

	public TechnologyBasicCircuits(int xDisplay, int yDisplay) {
		super("Basic Circuits", EnumTechLevel.MACRO, null);
		displayItem = new ItemStack(Femtocraft.microCircuitBoard);
		this.xDisplay = xDisplay;
		this.yDisplay = yDisplay;
	}
}
