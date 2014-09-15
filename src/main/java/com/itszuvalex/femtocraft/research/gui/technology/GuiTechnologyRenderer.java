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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Christopher Harris (Itszuvalex) on 9/15/14.
 */
public class GuiTechnologyRenderer {
    private ArrayList<TechnologyPageRenderer> pages = new ArrayList<TechnologyPageRenderer>();
    protected final FontRenderer frender = Minecraft.getMinecraft().fontRenderer;

    /**
     * Matches anything beginning with [ and ending with ]
     */
    public static final Pattern recipePattern = Pattern.compile("\\[*\\]");

    public GuiTechnologyRenderer(String description) {
        parse(description);
    }

    private void parse(String description) {
        Matcher specialMatcher = recipePattern.matcher(description);
        String[] textRegions = recipePattern.split(description);
        int currentY = 0;
        for (int i = 0; i < textRegions.length; ++i) {
            String region = textRegions[i];
            List lines = frender.listFormattedStringToWidth(region, GuiTechnology.descriptionWidth);
        }
    }
}
