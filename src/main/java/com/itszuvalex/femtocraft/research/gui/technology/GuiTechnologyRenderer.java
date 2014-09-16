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
public class GuiTechnologyRenderer implements ITechnologyElementRenderer {
    private ArrayList<TechnologyPageRenderer> pages = new ArrayList<TechnologyPageRenderer>();
    protected final FontRenderer frender = Minecraft.getMinecraft().fontRenderer;

    /**
     * Matches anything beginning with [ and ending with ]
     */
    public static final Pattern recipePattern = Pattern.compile("\\Q__\\E(.)\\Q__\\E");

    public GuiTechnologyRenderer(String description) {
        parse(description);
    }

    private void parse(String description) {
        Matcher specialMatcher = recipePattern.matcher(description);
        String[] textRegions = recipePattern.split(description);
        int currentY = 0;
        TechnologyPageRenderer currentPage = new TechnologyPageRenderer();
        pages.add(currentPage);
        for (String region : textRegions) {
            List lines = frender.listFormattedStringToWidth(region, GuiTechnology.descriptionWidth);
            for (Object oline : lines) {
                String line = (String) oline;
                if ((currentY + frender.FONT_HEIGHT) > GuiTechnology.descriptionHeight) {
                    currentPage = new TechnologyPageRenderer();
                    pages.add(currentPage);
                    currentY = 0;
                }
                currentPage.addElement(new TechnologyLineRenderer(line, currentY));
                currentY += frender.FONT_HEIGHT;
            }
            /**
             * String region ended - check for associated regex splitter section, parse, and insert.
             */
            if (specialMatcher.matches()) {
                String special = specialMatcher.group();
                ITechnologyElementRenderer renderer = parseSpecial(special);
                //If fail parsing, replace with string representation to show fiddlers the string.
                if (renderer == null) {
                    renderer = new TechnologyLineRenderer(special);
                }
                if ((currentY + renderer.getHeight()) > GuiTechnology.descriptionHeight) {
                    currentPage = new TechnologyPageRenderer();
                    pages.add(currentPage);
                    currentY = 0;
                }
                renderer.setY(currentY);
                currentPage.addElement(renderer);
                currentY += renderer.getHeight();
            }
        }
    }

    private ITechnologyElementRenderer parseSpecial(String special) {
        return null;
    }

    public int getPageCount() {
        return pages.size();
    }

    @Override
    public void render(int x, int y, int width, int height, int displayPage, int mouseX, int mouseY, List tooltip,
                       boolean isResearched) {
        pages.get(displayPage - 1).render(x, y, width, height, displayPage, mouseX, mouseY, tooltip, isResearched);
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void setY(int y) {

    }
}
