package femtocraft.managers.research;

import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;

@Cancelable
public class EventTechnology extends Event {
	public final ResearchTechnology researchTechnology;

	public EventTechnology(ResearchTechnology tech) {
		this.researchTechnology = tech;
	}

	@Cancelable
	public static class TechnologyAddedEvent extends EventTechnology {
		public TechnologyAddedEvent(ResearchTechnology tech) {
			super(tech);
		}
	}

	@Cancelable
	public static class TechnologyDiscoveredEvent extends EventTechnology {
		public final String username;

		public TechnologyDiscoveredEvent(String username, ResearchTechnology tech) {
			super(tech);
			this.username = username;
		}
	}

	@Cancelable
	public static class TechnologyResearchedEvent extends EventTechnology {
		public final String username;

		public TechnologyResearchedEvent(String username, ResearchTechnology tech) {
			super(tech);
			this.username = username;
		}
	}

}
