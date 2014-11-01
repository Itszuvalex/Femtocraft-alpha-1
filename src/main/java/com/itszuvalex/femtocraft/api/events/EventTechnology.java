/*
 * ******************************************************************************
 *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 *  * Itszuvalex@gmail.com
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *  *****************************************************************************
 */

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
