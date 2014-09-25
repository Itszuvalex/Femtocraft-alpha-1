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

package com.itszuvalex.femtocraft.managers.assembler;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.managers.research.ResearchTechnology;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created by Christopher Harris (Itszuvalex) on 6/28/14.
 */
public class AssemblerRecipeDatabase {
    private static final String DB_ITEMS_DAMAGE = "DAMAGE";
    private static final String DB_ITEMS_NBT = "NBT";
    private static final String DB_ITEMS_STACKSIZE = "STACKSIZE";
    private static final String DB_RECIPES_INPUT = "INPUT";
    private static final String DB_RECIPES_INPUT_SIZE = "INPUTSIZE";
    private static final String DB_RECIPES_MASS = "MASS";
    private static final String DB_RECIPES_OUTPUT = "OUTPUT";
    private static final String DB_RECIPES_OUTPUT_SIZE = "OUTPUTSIZE";
    private static final String DB_RECIPES_OUTPUT_NBT = "OUTPUTNBT";
    private static final String DB_RECIPES_TECH_LEVEL = "TECHLEVEL";
    private static final String DB_RECIPES_TECHNOLOGY = "TECHNAME";
    private static final String DB_NULL_ITEM = "null";
    private static final String DB_FILENAME = "AssemblerRecipes.db";
    private static final String DB_TABLE_ITEMS = "ITEMS";
    private static final String DB_TABLE_RECIPES = "RECIPES";
    private static final String DB_ITEMS_ITEMID = "ITEMID";
    private Connection c;

    private boolean shouldRegister = true;

    public AssemblerRecipeDatabase() {
        try {
            InitializeDatabase();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Femtocraft.logger.log(Level.SEVERE,
                    "SQLite dependency missing.");
            System.exit(1);

        } catch (SQLException e) {
            e.printStackTrace();
            Femtocraft.logger.log(Level.SEVERE, "Error opening connection");
        }
    }

    private void InitializeDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        refreshConnection();
        shouldRegister = createRecipeTable();
    }

    private void refreshConnection() throws SQLException {
        if (c == null) {
            c = DriverManager.getConnection("jdbc:sqlite:" + "config/" +
                    DB_FILENAME);
        }
    }

    private boolean createRecipeTable() {
        Statement s;
        try {
            refreshConnection();
            s = c.createStatement();
            String sql = "CREATE TABLE " + DB_TABLE_RECIPES +
                    "(ID INTEGER PRIMARY KEY, " +
                    DB_RECIPES_INPUT + " STRING NOT NULL," +
                    DB_RECIPES_INPUT_SIZE + " STRING NOT NULL," +
                    DB_RECIPES_MASS + " INT CHECK(" + DB_RECIPES_MASS + " >= " +
                    "0) NOT NULL," +
                    DB_RECIPES_OUTPUT + " STRING NOT NULL," +
                    DB_RECIPES_OUTPUT_SIZE + " STRING NOT NULL," +
                    DB_RECIPES_OUTPUT_NBT + " BYTES," +
                    DB_RECIPES_TECH_LEVEL + " STRING NOT NULL," +
                    DB_RECIPES_TECHNOLOGY + " STRING," +
                    "UNIQUE(" + DB_RECIPES_INPUT + ", " + DB_RECIPES_MASS + "," +
                    DB_RECIPES_OUTPUT + "), " +
                    "FOREIGN KEY(" + DB_RECIPES_OUTPUT + ") REFERENCES ITEMS(ID))";
            s.executeUpdate(sql);
            s.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean shouldRegister() {
        return shouldRegister;
    }

    private boolean createItemTable() {
        Statement s;
        try {
            refreshConnection();
            s = c.createStatement();
            String sql = "CREATE TABLE " + DB_TABLE_ITEMS +
                    "(ID INTEGER PRIMARY KEY NOT NULL," +
                    DB_ITEMS_ITEMID + " INT NOT NULL," +
                    DB_ITEMS_DAMAGE + " INT NOT NULL," +
                    DB_ITEMS_STACKSIZE + " INT CHECK(" + DB_ITEMS_STACKSIZE +
                    ">0) NOT NULL," +
                    DB_ITEMS_NBT + " BYTES," +
                    "UNIQUE(" + DB_ITEMS_ITEMID + ", " + DB_ITEMS_DAMAGE + ", " +
                    /*DB_ITEMS_NBT + "," +*/ DB_ITEMS_STACKSIZE + "))";
            s.executeUpdate(sql);
            s.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            Femtocraft.logger.log(Level.SEVERE, "SQLite item table creation" +
                    " failed");
            return false;
        }

    }

    public AssemblerRecipe getRecipe(ItemStack[] inputs) {
        AssemblerRecipe ac = null;
        try {
            refreshConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM " +
                    DB_TABLE_RECIPES + " WHERE " + DB_RECIPES_INPUT + " = ? " +
                    "LIMIT 1");
            ps.setString(1, formatItems(inputs));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ac = getRecipe(rs);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Femtocraft.logger.log(Level.SEVERE, "SQLite select recipe from " +
                    "inputs failed");
        }
        return Femtocraft.assemblerConfigs.isEnabled(ac) ? ac : null;
    }

    private String formatItems(ItemStack[] items) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.length; ++i) {
            if (items[i] == null) {
                sb.append(DB_NULL_ITEM);
            }
            else {
                sb.append(items[i].itemID);
                sb.append(":");
                sb.append(items[i].getItemDamage());
            }
            if (i < items.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    private AssemblerRecipe getRecipe(ResultSet rs) throws SQLException {
        AssemblerRecipe ac = new AssemblerRecipe();
        ac.input = getItems(rs.getString(DB_RECIPES_INPUT),
                rs.getString(DB_RECIPES_INPUT_SIZE));
        ac.mass = rs.getInt(DB_RECIPES_MASS);
        ac.output = getItem(rs.getString(DB_RECIPES_OUTPUT),
                rs.getString(DB_RECIPES_OUTPUT_SIZE),
                rs.getBytes(DB_RECIPES_OUTPUT_NBT));
        ac.enumTechLevel = EnumTechLevel.getTech(rs.getString
                (DB_RECIPES_TECH_LEVEL));
        ac.tech = rs.getString
                (DB_RECIPES_TECHNOLOGY);
        return Femtocraft.assemblerConfigs.isEnabled(ac) ? ac : null;
    }

    private ItemStack[] getItems(String items, String stackSizes) {
        ItemStack[] itemStacks = getItems(items);
        String[] sizes = stackSizes.split(",");
        for (int i = 0; i < itemStacks.length; ++i) {
            if (itemStacks[i] != null) {
                itemStacks[i].stackSize = Integer.parseInt(sizes[i]);
            }
        }
        return itemStacks;
    }

    private ItemStack getItem(String item, String stackSize, byte[] nbt) {
        String[] id_damage = item.split(":");
        ItemStack result = new ItemStack(Integer.parseInt(id_damage[0]),
                Integer.parseInt(stackSize), Integer.parseInt(id_damage[1]));
        if (nbt != null) {
            try {
                result.setTagCompound(CompressedStreamTools.decompress(nbt));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private ItemStack[] getItems(String items) {
        String[] ids = items.split(",");
        ItemStack[] itemArray = new ItemStack[ids.length];
        for (int i = 0; i < itemArray.length; ++i) {
            if (ids[i].matches(DB_NULL_ITEM)) {
                itemArray[i] = null;
            }
            else {
                String[] id_damage = ids[i].split(":");
                itemArray[i] = new ItemStack(Integer.parseInt(id_damage[0]), 1,
                        Integer.parseInt(id_damage[1]));
            }
        }
        return itemArray;
    }

    public AssemblerRecipe getRecipe(ItemStack output) {
        AssemblerRecipe ac = null;
        try {
            refreshConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM " +
                    DB_TABLE_RECIPES + " WHERE " + DB_RECIPES_OUTPUT + " = ? " +
                    "LIMIT 1");
            ps.setString(1, formatItem(output));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ac = getRecipe(rs);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Femtocraft.logger.log(Level.SEVERE, "SQLite select recipe from " +
                    "inputs failed");
        }
        return Femtocraft.assemblerConfigs.isEnabled(ac) ? ac : null;
    }

    private String formatItem(ItemStack item) {
        return item == null ? null : item.itemID + ":" + item.getItemDamage();
    }

    public boolean insertRecipe(AssemblerRecipe recipe) {
        try {
            refreshConnection();
            PreparedStatement ps = c.prepareStatement("INSERT OR IGNORE INTO " +
                    DB_TABLE_RECIPES + "(ID, " + DB_RECIPES_INPUT + ", " +
                    DB_RECIPES_INPUT_SIZE + ", " +
                    DB_RECIPES_MASS + "," + DB_RECIPES_OUTPUT + ", " +
                    DB_RECIPES_OUTPUT_SIZE + ", " +
                    DB_RECIPES_OUTPUT_NBT + ", " +
                    DB_RECIPES_TECH_LEVEL + ", " + DB_RECIPES_TECHNOLOGY + ")" +
                    "VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, formatItems(recipe.input));
            ps.setString(2, formatItemSizes(recipe.input));
            ps.setInt(3, recipe.mass);
            ps.setString(4, formatItem(recipe.output));
            ps.setString(5, String.valueOf(recipe.output.stackSize));
            byte[] nbt = null;
            if (recipe.output.hasTagCompound()) {
                try {
                    nbt = CompressedStreamTools.compress(recipe.output
                            .getTagCompound());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ps.setBytes(6, nbt);
            ps.setString(7, recipe.enumTechLevel.key);
            ps.setString(8, recipe.tech);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            Femtocraft.logger.log(Level.SEVERE, "SQLite insert recipe failed");
            return false;
        }
    }

    private String formatItemSizes(ItemStack[] items) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.length; ++i) {
            if (items[i] == null) {
                sb.append(DB_NULL_ITEM);
            }
            else {
                sb.append(items[i].stackSize);
            }
            if (i < items.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public ArrayList<AssemblerRecipe> getRecipesForLevel(EnumTechLevel level) {
        ArrayList<AssemblerRecipe> arrayList = new ArrayList<AssemblerRecipe>();
        try {
            refreshConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM " +
                    DB_TABLE_RECIPES + " WHERE " + DB_RECIPES_TECH_LEVEL + " " +
                    "=?", ResultSet.HOLD_CURSORS_OVER_COMMIT);
            ps.setString(1, level.key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AssemblerRecipe ac = getRecipe(rs);
                if (ac != null) {
                    arrayList.add(ac);
                }
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Femtocraft.logger.log(Level.SEVERE, "SQLite select recipe from " +
                    "inputs failed");
        }
        return arrayList;
    }


    public ArrayList<AssemblerRecipe> getRecipesForTech(ResearchTechnology tech) {
        return getRecipesForTech(tech.name);
    }

    public ArrayList<AssemblerRecipe> getRecipesForTech(String techName) {
        ArrayList<AssemblerRecipe> arrayList = new ArrayList<AssemblerRecipe>();
        try {
            refreshConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM " +
                    DB_TABLE_RECIPES + " WHERE " + DB_RECIPES_TECHNOLOGY + " " +
                    "= ?", ResultSet.HOLD_CURSORS_OVER_COMMIT);
            ps.setString(1, techName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AssemblerRecipe ac = getRecipe(rs);
                if (ac != null) {
                    arrayList.add(ac);
                }
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Femtocraft.logger.log(Level.SEVERE, "SQLite select recipe from " +
                    "inputs failed");
        }
        return arrayList;
    }

    /**
     * DO NOT CALL THIS unless you have VERY good reason to.  This is a HUGE database and will take a long time to
     * load.
     *
     * @return
     */
    public ArrayList<AssemblerRecipe> getAllRecipes() {
        ArrayList<AssemblerRecipe> arrayList = new ArrayList<AssemblerRecipe>();
        try {
            refreshConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM " +
                    DB_TABLE_RECIPES, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AssemblerRecipe ac = getRecipe(rs);
                if (ac != null) {
                    arrayList.add(ac);
                }
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Femtocraft.logger.log(Level.SEVERE, "SQLite select recipe from " +
                    "inputs failed");
        }
        return arrayList;
    }
}
