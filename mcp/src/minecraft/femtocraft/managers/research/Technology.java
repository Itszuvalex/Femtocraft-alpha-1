package femtocraft.managers.research;

import java.util.ArrayList;

public class Technology {
	public String name;
	public TechLevel level;
	public ArrayList<Technology> prerequisites;

	public Technology(String name, TechLevel level,
			ArrayList<Technology> prerequisites) {
		this.name = name;
		this.level = level;
		this.prerequisites = prerequisites;
	}

	public Technology(String name, TechLevel level) {
		this(name, level, null);
	}

	// --------------------------------------------------

	public boolean addPrerequisite(Technology prereq) {
		return prerequisites.add(prereq);
	}

	public boolean removePrerequisite(Technology prereq) {
		return prerequisites.remove(prereq);
	}

	public boolean hasPrerequisite(Technology prereq) {
		return prerequisites.contains(prereq);
	}

	// --------------------------------------------------

	private Technology() {
	}
}
