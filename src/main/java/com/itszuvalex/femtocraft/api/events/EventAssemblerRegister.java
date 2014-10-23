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

import com.itszuvalex.femtocraft.managers.assembler.AssemblerRecipe;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

@Cancelable
public class EventAssemblerRegister extends Event {

    public final AssemblerRecipe recipe;

    public EventAssemblerRegister(AssemblerRecipe recipe) {
        this.recipe = recipe;
    }

    @Cancelable
    public static class AssemblerDecompositionRegisterEvent extends
                                                            EventAssemblerRegister {
        public AssemblerDecompositionRegisterEvent(
                AssemblerRecipe recipe) {
            super(recipe);
        }
    }

    @Cancelable
    public static class AssemblerRecompositionRegisterEvent extends
                                                            EventAssemblerRegister {
        public AssemblerRecompositionRegisterEvent(
                AssemblerRecipe recipe) {
            super(recipe);
        }
    }
}
