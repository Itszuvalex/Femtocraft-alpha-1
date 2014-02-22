package femtocraft.managers.research;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

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

	public ResearchTechnology(String name, String description,
			EnumTechLevel level, ArrayList<ResearchTechnology> prerequisites,
			ItemStack displayItem, int xDisplay, int yDisplay,
			boolean isKeystone, ArrayList<ItemStack> researchMaterials) {
		this.name = name;
		this.description = description;
		this.level = level;
		this.prerequisites = prerequisites;
		this.displayItem = displayItem;
		this.xDisplay = xDisplay;
		this.yDisplay = yDisplay;
		this.isKeystone = isKeystone;
		this.researchMaterials = researchMaterials;
	}

	// --------------------------------------------------

	public boolean addPrerequisite(ResearchTechnology prereq) {
		return prerequisites.add(prereq);
	}

	public boolean removePrerequisite(ResearchTechnology prereq) {
		return prerequisites.remove(prereq);
	}

	public boolean hasPrerequisite(ResearchTechnology prereq) {
		return prerequisites.contains(prereq);
	}

	// --------------------------------------------------

	private ResearchTechnology() {
	}
}
