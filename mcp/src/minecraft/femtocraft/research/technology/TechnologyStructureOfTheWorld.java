package femtocraft.research.technology;

import net.minecraft.item.ItemStack;
import femtocraft.Femtocraft;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.managers.research.ResearchTechnology;

public class TechnologyStructureOfTheWorld extends ResearchTechnology {

	public TechnologyStructureOfTheWorld() {
		super("Structure of the World", EnumTechLevel.MACRO, null);
		displayItem = new ItemStack(Femtocraft.itemMineralLattice);
		xDisplay = 8;
		yDisplay = 0;
	}
}
