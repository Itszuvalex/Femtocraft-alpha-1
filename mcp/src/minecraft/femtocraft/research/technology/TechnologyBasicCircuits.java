package femtocraft.research.technology;

import net.minecraft.item.ItemStack;
import femtocraft.Femtocraft;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.managers.research.ResearchTechnology;

public class TechnologyBasicCircuits extends ResearchTechnology {

	public TechnologyBasicCircuits() {
		super("Basic Circuits", EnumTechLevel.MACRO, null);
		displayItem = new ItemStack(Femtocraft.microCircuitBoard);
		xDisplay = 12;
		yDisplay = 0;
	}
}
