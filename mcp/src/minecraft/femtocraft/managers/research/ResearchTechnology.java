package femtocraft.managers.research;

import java.util.ArrayList;

public class ResearchTechnology {
	public String name;
	public EnumTechLevel level;
	public ArrayList<ResearchTechnology> prerequisites;

	public ResearchTechnology(String name, EnumTechLevel level,
                              ArrayList<ResearchTechnology> prerequisites) {
		this.name = name;
		this.level = level;
		this.prerequisites = prerequisites;
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
