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

package com.itszuvalex.femtocraft.managers;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

import java.util.*;

public class ManagerCrafting {
    public static final String DefaultMinecraftRecipeKey = "minecraft";
    /**
     * The static instance of this class
     */
    private static final ManagerCrafting instance = new ManagerCrafting();
    /**
     * A map containing all lists of all the recipes added
     */
    private Map recipeListMap = new HashMap<String, List>();

    private ManagerCrafting() {

        // this.addShapelessRecipe(new ItemStack(Item.book, 1), new Object[]
        // {Item.paper, Item.paper, Item.paper, Item.leather});
        // this.addShapelessRecipe(new ItemStack(Item.writableBook, 1), new
        // Object[] {Item.book, new ItemStack(Item.dyePowder, 1, 0),
        // Item.feather});
        ComparatorRecipeSorter defaultSorter = new ComparatorRecipeSorter(this);

        // Sort all recipe lists
        for (Object obj : recipeListMap.values()) {
            Collections.sort((List) obj, defaultSorter);
        }
    }

    /**
     * Returns the static instance of this class
     */
    public static ManagerCrafting getInstance() {
        return instance;
    }

    public ShapedRecipes addRecipe(ItemStack par1ItemStack,
                                   Object... par2ArrayOfObj) {
        return addRecipe(DefaultMinecraftRecipeKey, par1ItemStack,
                par2ArrayOfObj);
    }

    public ShapedRecipes addRecipe(String recipeListKey,
                                   ItemStack par1ItemStack, Object... par2ArrayOfObj) {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if (par2ArrayOfObj[i] instanceof String[]) {
            String[] astring = (String[]) par2ArrayOfObj[i++];

            for (String s1 : astring) {
                ++k;
                j = s1.length();
                s = s + s1;
            }
        }
        else {
            while (par2ArrayOfObj[i] instanceof String) {
                String s2 = (String) par2ArrayOfObj[i++];
                ++k;
                j = s2.length();
                s = s + s2;
            }
        }

        HashMap hashmap;

        for (hashmap = new HashMap(); i < par2ArrayOfObj.length; i += 2) {
            Character character = (Character) par2ArrayOfObj[i];
            ItemStack itemstack1 = null;

            if (par2ArrayOfObj[i + 1] instanceof Item) {
                itemstack1 = new ItemStack((Item) par2ArrayOfObj[i + 1]);
            }
            else if (par2ArrayOfObj[i + 1] instanceof Block) {
                itemstack1 = new ItemStack((Block) par2ArrayOfObj[i + 1], 1,
                        32767);
            }
            else if (par2ArrayOfObj[i + 1] instanceof ItemStack) {
                itemstack1 = (ItemStack) par2ArrayOfObj[i + 1];
            }

            hashmap.put(character, itemstack1);
        }

        ItemStack[] aitemstack = new ItemStack[j * k];

        for (int i1 = 0; i1 < j * k; ++i1) {
            char c0 = s.charAt(i1);

            if (hashmap.containsKey(Character.valueOf(c0))) {
                aitemstack[i1] = ((ItemStack) hashmap
                        .get(Character.valueOf(c0))).copy();
            }
            else {
                aitemstack[i1] = null;
            }
        }

        ShapedRecipes shapedrecipes = new ShapedRecipes(j, k, aitemstack,
                par1ItemStack);

        ArrayList list = (ArrayList) recipeListMap.get(recipeListKey);
        if (list == null) {
            list = (ArrayList) recipeListMap
                    .put(recipeListKey, new ArrayList());
        }

        list.add(shapedrecipes);

        return shapedrecipes;
    }

    public void addShapelessRecipe(ItemStack par1ItemStack,
                                   Object... par2ArrayOfObj) {
        addShapelessRecipe(DefaultMinecraftRecipeKey, par1ItemStack,
                par2ArrayOfObj);
    }

    public void addShapelessRecipe(String recipeListKey,
                                   ItemStack par1ItemStack, Object... par2ArrayOfObj) {
        ArrayList arraylist = new ArrayList();
        for (Object object1 : par2ArrayOfObj) {
            if (object1 instanceof ItemStack) {
                arraylist.add(((ItemStack) object1).copy());
            }
            else if (object1 instanceof Item) {
                arraylist.add(new ItemStack((Item) object1));
            }
            else {
                if (!(object1 instanceof Block)) {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }

                arraylist.add(new ItemStack((Block) object1));
            }
        }

        ShapelessRecipes recipe = new ShapelessRecipes(par1ItemStack, arraylist);

        ArrayList list = (ArrayList) recipeListMap.get(recipeListKey);
        if (list == null) {
            list = (ArrayList) recipeListMap
                    .put(recipeListKey, new ArrayList());
        }

        list.add(recipe);
    }

    public ItemStack findMatchingRecipe(
            InventoryCrafting par1InventoryCrafting, World par2World) {
        return findMatchingRecipe(DefaultMinecraftRecipeKey,
                par1InventoryCrafting, par2World);
    }

    public ItemStack findMatchingRecipe(String recipeListKey,
                                        InventoryCrafting par1InventoryCrafting, World par2World) {
        int i = 0;
        ItemStack itemstack = null;
        ItemStack itemstack1 = null;
        int j;

        for (j = 0; j < par1InventoryCrafting.getSizeInventory(); ++j) {
            ItemStack itemstack2 = par1InventoryCrafting.getStackInSlot(j);

            if (itemstack2 != null) {
                if (i == 0) {
                    itemstack = itemstack2;
                }

                if (i == 1) {
                    itemstack1 = itemstack2;
                }

                ++i;
            }
        }

        if (i == 2 && itemstack.itemID == itemstack1.itemID
                && itemstack.stackSize == 1 && itemstack1.stackSize == 1
                && Item.itemsList[itemstack.itemID].isRepairable()) {
            Item item = Item.itemsList[itemstack.itemID];
            int k = item.getMaxDamage() - itemstack.getItemDamageForDisplay();
            int l = item.getMaxDamage() - itemstack1.getItemDamageForDisplay();
            int i1 = k + l + item.getMaxDamage() * 5 / 100;
            int j1 = item.getMaxDamage() - i1;

            if (j1 < 0) {
                j1 = 0;
            }

            return new ItemStack(itemstack.itemID, 1, j1);
        }
        else {
            for (j = 0; j < ((ArrayList) this.recipeListMap.get(recipeListKey))
                    .size(); ++j) {
                IRecipe irecipe = (IRecipe) ((ArrayList) this.recipeListMap
                        .get(recipeListKey)).get(j);

                if (irecipe.matches(par1InventoryCrafting, par2World)) {
                    return irecipe.getCraftingResult(par1InventoryCrafting);
                }
            }

            return null;
        }
    }

    /**
     * returns the List<> of all recipes
     */
    public List getRecipeList() {
        return getRecipeList(DefaultMinecraftRecipeKey);
    }

    public List getRecipeList(String recipeListKey) {
        return (List) this.recipeListMap.get(recipeListKey);
    }
}
