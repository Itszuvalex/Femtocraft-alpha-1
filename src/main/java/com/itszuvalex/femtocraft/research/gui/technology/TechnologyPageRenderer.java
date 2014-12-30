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

package com.itszuvalex.femtocraft.research.gui.technology;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher Harris (Itszuvalex) on 9/15/14.
 */
public class TechnologyPageRenderer implements ITechnologyElementRenderer {
    private ArrayList<ITechnologyElementRenderer> elements;

    public TechnologyPageRenderer() {
        elements = new ArrayList<ITechnologyElementRenderer>();
    }

    public boolean addElement(ITechnologyElementRenderer renderer) {
        return elements.add(renderer);
    }

    @Override
    public void render(int x, int y, int width, int height, int displayPage, int mouseX, int mouseY, List tooltip,
                       boolean isResearched) {
        for (ITechnologyElementRenderer element : elements) {
            try {
                element.render(x, y, width, height, displayPage, mouseX, mouseY, tooltip, isResearched);
            }catch(Exception ignored){}
        }
    }

    @Override
    public int getWidth() {
        return GuiTechnology.descriptionWidth();
    }

    @Override
    public int getHeight() {
        return GuiTechnology.descriptionHeight();
    }

    @Override
    public void setY(int y) {

    }
}
