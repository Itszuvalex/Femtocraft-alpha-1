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

import com.itszuvalex.femtocraft.utils.FemtocraftStringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

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
    protected final GuiTechnology gui;

    /**
     * Matches anything beginning with [ and ending with ]
     */
    public static final String specialPattern = "__([^_]*)__";
    public static final Pattern splitPattern = Pattern.compile(specialPattern);
    public static final Pattern groupingPattern = Pattern.compile(".*?" + specialPattern + ".*?");

    public static final String specialTypeGroup = "specialType";
    public static final String specialTypeRegex = "(?<" + specialTypeGroup + ">[^\\._]*)";
    public static final String specialParamGroup = "specialParam";
    public static final String specialParamRegex = "(?<" + specialParamGroup + ">[^:_]*)?";
    public static final String specialTextGroup = "specialText";
    public static final String specialTextRegex = "(?<" + specialTextGroup + ">[^_]*)";
    public static final Pattern specialDecompPattern = Pattern.compile(specialTypeRegex + "\\." + specialParamRegex + ":" + specialTextRegex);

    public static final String recipeType = "Recipe";
    public static final String recipeAssemblerParam = "Assembler";
    public static final String recipeTemporalParam = "Temporal";
    public static final String recipeDimensionalParam = "Dimensional";

    public static final String recipeParamTypeGroup = "recipeType";
    public static final String recipeParamTypeRegex = "(?<" + recipeParamTypeGroup + ">[^:]*)";
    public static final String recipeParamItemGroup = "recipeItem";
    public static final String recipeParamItemRegex = "(?<" + recipeParamItemGroup + ">[^:]*)";
    public static final Pattern recipeParamPattern = Pattern.compile(recipeParamTypeRegex + ":" + recipeParamItemRegex);

    public GuiTechnologyRenderer(GuiTechnology gui, String description) {
        this.gui = gui;
        parse(description);
    }

    private void parse(String description) {
        Matcher groupingMatcher = groupingPattern.matcher(description);
        String[] textRegions = splitPattern.split(description);
        int currentY = 0;
        TechnologyPageRenderer currentPage = new TechnologyPageRenderer();
        groupingMatcher.matches();
        int groupIndex = 1;
        pages.add(currentPage);
        for (int i = 0; i < textRegions.length; i++) {
            String region = textRegions[i];
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
            try {
                String special;
                if (((i + 1) < textRegions.length) && (special = groupingMatcher.group(i + 1)) != null) {
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
                    groupIndex = i + 1;
                }
            } catch (Exception ignored) {
            }
        }
        try {
            while (true) {
                String special = groupingMatcher.group(groupIndex);
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
                groupIndex++;
            }
        } catch (Exception ignored) {
        }
    }

    private ITechnologyElementRenderer parseSpecial(String special) {
        Matcher specialMatcher = specialDecompPattern.matcher(special);
        if (specialMatcher.matches()) {
            String type = specialMatcher.group(specialTypeGroup);
            String param = specialMatcher.group(specialParamGroup);
            String text = specialMatcher.group(specialTextGroup);
            if (type.equals(recipeType)) {
                return parseRecipe(param, text);
            }
        }
        return null;
    }

    private ITechnologyElementRenderer parseRecipe(String param, String text) {
        Matcher paramMatcher = recipeParamPattern.matcher(param);
        String rtype = paramMatcher.group(recipeParamTypeGroup);
        String sitem = paramMatcher.group(recipeParamItemGroup);
        ItemStack item = FemtocraftStringUtils.itemStackFromString(sitem);
        if (item != null) {
            if (rtype.equals(recipeAssemblerParam)) {
                return new GuiTechnologyAssemblerRenderer(gui, item, text);
            }
            else if (rtype.equals(recipeDimensionalParam)) {

            }
            else if (rtype.equals(recipeTemporalParam)) {

            }
        }
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
