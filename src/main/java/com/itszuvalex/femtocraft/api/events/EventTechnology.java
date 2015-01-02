package com.itszuvalex.femtocraft.api.events;

import com.itszuvalex.femtocraft.api.research.ITechnology;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

@Cancelable
public class EventTechnology extends Event {
    public final ITechnology technology;

    public EventTechnology(ITechnology tech) {
        this.technology = tech;
    }

    @Cancelable
    public static class TechnologyAddedEvent extends EventTechnology {
        public TechnologyAddedEvent(ITechnology tech) {
            super(tech);
        }
    }

    @Cancelable
    public static class TechnologyDiscoveredEvent extends EventTechnology {
        public final String username;

        public TechnologyDiscoveredEvent(String username, ITechnology tech) {
            super(tech);
            this.username = username;
        }
    }

    @Cancelable
    public static class TechnologyResearchedEvent extends EventTechnology {
        public final String username;

        public TechnologyResearchedEvent(String username, ITechnology tech) {
            super(tech);
            this.username = username;
        }
    }

}
