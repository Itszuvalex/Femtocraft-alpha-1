package femtocraft.research;

import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;

@Cancelable
public class TechnologyEvent extends Event {
	public final Technology technology;

	public TechnologyEvent(Technology tech) {
		this.technology = tech;
	}
	
	@Cancelable
	public static class TechnologyAddedEvent extends TechnologyEvent
	{
		public TechnologyAddedEvent(Technology tech) {
			super(tech);
		}
	}
	
	@Cancelable
	public static class TechnologyDiscoveredEvent extends TechnologyEvent
	{
		public final String username;
		public TechnologyDiscoveredEvent(String username, Technology tech) {
			super(tech);
			this.username = username;
		}
	}
	
	@Cancelable
	public static class TechnologyResearchedEvent extends TechnologyEvent
	{
		public final String username;
		public TechnologyResearchedEvent(String username, Technology tech) {
			super(tech);
			this.username = username;
		}
	}

}
