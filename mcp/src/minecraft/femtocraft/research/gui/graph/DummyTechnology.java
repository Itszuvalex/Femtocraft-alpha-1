package femtocraft.research.gui.graph;

import java.util.ArrayList;

import femtocraft.managers.research.EnumTechLevel;
import femtocraft.managers.research.ResearchTechnology;

public class DummyTechnology extends ResearchTechnology {

	public DummyTechnology(EnumTechLevel level,
			ArrayList<ResearchTechnology> prerequisites) {
		super("Dummy", "I am a dummy and you shouldn't see me", level,
				prerequisites, null, 0, 0, false, null);
	}

}
