package com.itszuvalex.femtocraft.utils;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.research.gui.technology.GuiTechnologyRenderer;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Level;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Chris on 9/15/2014.
 */
public class FemtocraftStringUtils {
    public static final String itemModIDGroup = "modID";
    public static final String itemNameGroup = "itemName";
    public static final String itemIDGroup = "itemID";
    public static final String itemDamageGroup = "itemDamage";
    public static final String itemStackSizeGroup = "itemStackSize";
    public static final String itemIDRegex = "(?<" + itemIDGroup + ">\\d+)";
    public static final String modIDRegex = "(?<" + itemModIDGroup + ">[^:-]+)";
    public static final String itemNameRegex = "(?<" + itemNameGroup + ">[^:-]+)";
    public static final String itemStackRegex = "(?:-(?<" + itemStackSizeGroup + ">\\d+?))?";
    public static final String itemDamageRegex = "(?::(?<" + itemDamageGroup + ">\\d+?))?";
    public static final Pattern itemStackPattern = Pattern.compile(
            "(?:" + itemIDRegex + "|(?:" + modIDRegex + ":" + itemNameRegex + "))" + itemDamageRegex +
            itemStackRegex);

    public static ItemStack itemStackFromString(String s) {
        if (s == null || s.isEmpty()) return null;
        Matcher itemMatcher = itemStackPattern.matcher(s);
        if (itemMatcher.matches()) {
            try {
                String itemID = itemMatcher.group(itemIDGroup);
                String modID = itemMatcher.group(itemModIDGroup);
                String name = itemMatcher.group(itemNameGroup);
                String sdam = itemMatcher.group(itemDamageGroup);
                String ssize = itemMatcher.group(itemStackSizeGroup);
                int damage = sdam == null ? 0 : Integer.parseInt(sdam);
                int stackSize = ssize == null ? 1 : Integer.parseInt(ssize);
                if (itemID != null) {
                    int id = Integer.parseInt(itemID);
                    return new ItemStack(Item.getItemById(id), stackSize, damage);
                }
                String[] typeName = name.split("\\.");
                name = typeName[typeName.length - 1];
                Item item = GameRegistry.findItem(modID, name);
                if (item != null) {
                    return new ItemStack(item, stackSize, damage);
                }
                Block block = GameRegistry.findBlock(modID, name);
                if (block != null) {
                    return new ItemStack(block, stackSize, damage);
                }
            } catch (Exception e) {
                Femtocraft.log(Level.ERROR, "Error parsing ItemStack string \"" + s + "\"");
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }


    public static String itemStackToString(ItemStack s) {
//        if (modID == null && s != null) {
//            if (s.getItem() instanceof ItemBase) {
//                modID = Femtocraft.ID;
//            } else if (s.getItem() instanceof ItemBlock) {
//                Block bl = Block.blocksList[((ItemBlock) s.getItem()).getBlockID()];
//                //SO hacky
//                if (bl instanceof BlockBase || bl instanceof TileContainer || bl instanceof BlockOreBase) {
//                    modID = Femtocraft.ID;
//                }
//            } else {
//                modID = "Minecraft";
//            }
//        }
        GameRegistry.UniqueIdentifier id = null;
        if (s != null) {
            if (s.getItem() instanceof ItemBlock) {
                id = GameRegistry.findUniqueIdentifierFor(Block.getBlockFromItem(s.getItem()));
            } else {
                id = GameRegistry.findUniqueIdentifierFor(s.getItem());
            }
        }
        if (s == null) {
            return "";
        } else {
            String result;
            if (id == null) {
                result = String.valueOf(Item.getIdFromItem(s.getItem()));
            } else {
                result = id.modId + ":" + id.name;
            }
            return result + ":" + s.getItemDamage() + "-" + s.stackSize;
        }
    }


    public static String formatItemStackForTechnologyDisplay(RecipeType r, ItemStack s, String info) {
        return "__Recipe." +
               (r == RecipeType.CRAFTING ? GuiTechnologyRenderer.recipeCraftingParam :
                       r == RecipeType.ASSEMBLER ? GuiTechnologyRenderer.recipeAssemblerParam :
                               r == RecipeType.TEMPORAL ? GuiTechnologyRenderer.recipeTemporalParam :
                                       r == RecipeType.DIMENSIONAL ? GuiTechnologyRenderer.recipeDimensionalParam :
                                               "UNKNOWN") + ":" + FemtocraftStringUtils.itemStackToString(s) + "--" +
               info + "__";
    }

    public enum RecipeType {
        CRAFTING,
        ASSEMBLER,
        TEMPORAL,
        DIMENSIONAL
    }

}
