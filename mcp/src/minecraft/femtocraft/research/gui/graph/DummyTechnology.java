/*******************************************************************************
 * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 * Itszuvalex@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/

package femtocraft.research.gui.graph;

import femtocraft.managers.research.EnumTechLevel;
import femtocraft.managers.research.ResearchTechnology;

import java.util.ArrayList;

public class DummyTechnology extends ResearchTechnology {

    public DummyTechnology(EnumTechLevel level,
                           ArrayList<ResearchTechnology> prerequisites) {
        super("Dummy", "I am a dummy and you shouldn't see me", level,
              prerequisites, null, 0, 0, false, null);
    }

}
