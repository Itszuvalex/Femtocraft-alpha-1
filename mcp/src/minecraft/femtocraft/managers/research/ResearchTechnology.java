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
	public int widthDisplay;
	public int heightDisplay;
	public boolean isKeystone;
	public ArrayList<ItemStack> researchMaterials;

	public ResearchTechnology(String name, EnumTechLevel level,
			ArrayList<ResearchTechnology> prerequisites) {
		this.name = name;
		this.description = "";
		this.level = level;
		this.prerequisites = prerequisites;
		displayItem = null;
		xDisplay = 0;
		yDisplay = 0;
		widthDisplay = 0;
		heightDisplay = 0;
		isKeystone = false;
		researchMaterials = new ArrayList<ItemStack>();
	}

	public ResearchTechnology(String name, EnumTechLevel level) {
		this(name, level, null);
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
