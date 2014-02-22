package femtocraft.research.technology;

import net.minecraft.item.ItemStack;
import femtocraft.Femtocraft;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.managers.research.ResearchTechnology;

public class TechnologyMetallurgy extends ResearchTechnology {

	public TechnologyMetallurgy(int xDisplay, int yDisplay) {
		super("Metallurgy", EnumTechLevel.MACRO, null);
		displayItem = new ItemStack(Femtocraft.ingotThorium);
		this.xDisplay = xDisplay;
		this.yDisplay = yDisplay;
	}
}
