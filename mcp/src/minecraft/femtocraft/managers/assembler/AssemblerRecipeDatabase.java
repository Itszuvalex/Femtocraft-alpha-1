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

package femtocraft.managers.assembler;

import femtocraft.Femtocraft;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.managers.research.ResearchTechnology;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created by Christopher Harris (Itszuvalex) on 6/28/14.
 */
public class AssemblerRecipeDatabase {
    public static final String DB_ITEMS_DAMAGE = "DAMAGE";
    public static final String DB_ITEMS_NBT = "NBT";
    public static final String DB_ITEMS_STACKSIZE = "STACKSIZE";
    public static final String DB_RECIPES_INPUT = "INPUT";
    public static final String DB_RECIPES_MASS = "MASS";
    public static final String DB_RECIPES_OUTPUT = "OUTPUT";
    public static final String DB_RECIPES_TECH_LEVEL = "TECHLEVEL";
    public static final String DB_RECIPES_TECHNOLOGY = "TECHNAME";
    public static final String DB_NULL_ITEM = "null";
    private static final String DB_FILENAME = "AssemblerRecipes.db";
    private static final String DB_TABLE_ITEMS = "ITEMS";
    private static final String DB_TABLE_RECIPES = "RECIPES";
    private static final String DB_ITEMS_ITEMID = "ITEMID";
    private Connection c;

    public AssemblerRecipeDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            refreshConnection();
            createItemTable();
            createRecipeTable();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Femtocraft.logger.log(Level.SEVERE, "Missing sqlite dependency.");
            System.exit(1);
        } catch (SQLException e) {
            e.printStackTrace();
            Femtocraft.logger.log(Level.SEVERE, "Error opening connection");
        }
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
                    DB_RECIPES_MASS + " INT CHECK(" + DB_RECIPES_MASS + " >= " +
                    "0) NOT NULL," +
                    DB_RECIPES_OUTPUT + " INT NOT NULL," +
                    DB_RECIPES_TECH_LEVEL + " STRING NOT NULL," +
                    DB_RECIPES_TECHNOLOGY + " STRING," +
                    "UNIQUE(" + DB_RECIPES_INPUT + ", " + DB_RECIPES_MASS + "," +
                    DB_RECIPES_OUTPUT + "), " +
                    "FOREIGN KEY(" + DB_RECIPES_OUTPUT + ") REFERENCES ITEMS(ID))";
            s.executeUpdate(sql);
            s.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            Femtocraft.logger.log(Level.SEVERE, "SQLite recipe table creation" +
                    " failed");
            return false;
        } finally {

        }
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
                    "LIMIT 1", ResultSet.HOLD_CURSORS_OVER_COMMIT);
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
        return ac;
    }

    private String formatItems(ItemStack[] items) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.length; ++i) {
            if (items[i] == null) {
                sb.append(DB_NULL_ITEM);
            }
            else {
                int id = getItemDB_ID(items[i]);
                sb.append(id);
            }
            if (i < items.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    private AssemblerRecipe getRecipe(ResultSet rs) throws SQLException {
        AssemblerRecipe ac = new AssemblerRecipe();
        ac.input = getItems(rs.getString(DB_RECIPES_INPUT));
        ac.mass = rs.getInt(DB_RECIPES_MASS);
        ac.output = getItem(rs.getInt(DB_RECIPES_OUTPUT));
        ac.enumTechLevel = EnumTechLevel.getTech(rs.getString
                (DB_RECIPES_TECH_LEVEL));
        String tech = rs.getString
                (DB_RECIPES_TECHNOLOGY);
        ac.tech = tech == null ? null : Femtocraft.researchManager.getTechnology
                (tech);
        return ac;
    }

    private int getItemDB_ID(ItemStack item) {
        int db_id = -1;
        if (item == null) return db_id;
        try {
            refreshConnection();
            PreparedStatement ps = c.prepareStatement("SELECT ID FROM " +
                    DB_TABLE_ITEMS +
                    " WHERE " + DB_ITEMS_ITEMID + "= ? AND " +
                    DB_ITEMS_DAMAGE + "= ? AND " +
                    DB_ITEMS_STACKSIZE + "= ? AND " +
                    DB_ITEMS_NBT + "= ? LIMIT 1");
            ps.setInt(1, item.itemID);
            ps.setInt(2, item.getItemDamage());
            ps.setInt(3, item.stackSize);

            byte[] bytes = null;
            if (item.hasTagCompound()) {
                try {
                    bytes = CompressedStreamTools.compress(item.getTagCompound());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ps.setBytes(4, bytes);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                db_id = rs.getInt(0);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Femtocraft.logger.log(Level.SEVERE, "SQLite select item" +
                    " failed");
        }
        return db_id;
    }

    private ItemStack[] getItems(String items) {
        String[] ids = items.split(",");
        ItemStack[] itemArray = new ItemStack[ids.length];
        for (int i = 0; i < itemArray.length; ++i) {
            if (ids[i].matches(DB_NULL_ITEM)) {
                itemArray[i] = null;
            }
            else {
                int db_id = Integer.parseInt(ids[i]);
                itemArray[i] = getItem(db_id);
            }
        }
        return itemArray;
    }

    private ItemStack getItem(int db_id) {
        if (db_id < 0) return null;
        try {
            refreshConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM " +
                    DB_TABLE_ITEMS +
                    " WHERE ID = ? LIMIT 1");
            ps.setInt(1, db_id);

            ResultSet rs = ps.executeQuery();
            ItemStack stack = null;
            if (rs.next()) {
                int itemID = rs.getInt(DB_ITEMS_ITEMID);
                int damage = rs.getInt(DB_ITEMS_DAMAGE);
                int stackSize = rs.getInt(DB_ITEMS_STACKSIZE);
                byte[] bytes = rs.getBytes(DB_ITEMS_NBT);
                stack = new ItemStack(itemID, stackSize, damage);

                if (bytes != null) {
                    try {
                        NBTTagCompound compound = CompressedStreamTools.decompress
                                (bytes);
                        stack.setTagCompound(compound);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            rs.close();
            ps.close();
            return stack;
        } catch (SQLException e) {
            e.printStackTrace();
            Femtocraft.logger.log(Level.SEVERE, "SQLite select item" +
                    " failed");
            return null;
        }
    }

    public AssemblerRecipe getRecipe(ItemStack output) {
        AssemblerRecipe ac = null;
        try {
            refreshConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM " +
                    DB_TABLE_RECIPES + " WHERE " + DB_RECIPES_OUTPUT + " = ? " +
                    "LIMIT 1", ResultSet.HOLD_CURSORS_OVER_COMMIT);
            ps.setInt(1, getItemDB_ID(output));
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
        return ac;
    }

    public boolean insertRecipe(AssemblerRecipe recipe) {
        for (ItemStack item : recipe.input) {
            insertItem(item);
        }
        insertItem(recipe.output);

        try {
            refreshConnection();
            PreparedStatement ps = c.prepareStatement("INSERT OR IGNORE INTO " +
                    DB_TABLE_RECIPES + "(ID, " + DB_RECIPES_INPUT + ", " +
                    DB_RECIPES_MASS + "," + DB_RECIPES_OUTPUT + ", " +
                    DB_RECIPES_TECH_LEVEL + ", " + DB_RECIPES_TECHNOLOGY + ")" +
                    "VALUES (null, ?, ?, ?, ?, ?)");
            ps.setString(1, formatItems(recipe.input));
            ps.setInt(2, recipe.mass);
            ps.setInt(3, getItemDB_ID(recipe.output));
            ps.setString(4, recipe.enumTechLevel.key);
            ps.setString(5, recipe.tech == null ? null : recipe.tech.name);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            Femtocraft.logger.log(Level.SEVERE, "SQLite insert recipe failed");
            return false;
        }
    }

    private boolean insertItem(ItemStack item) {
        if (item == null) return true;
        if (getItemDB_ID(item) != -1) return true;
        try {
            refreshConnection();
            PreparedStatement ps = c.prepareStatement("INSERT OR IGNORE INTO " +
                    DB_TABLE_ITEMS + "(" + DB_ITEMS_ITEMID + ", " +
                    DB_ITEMS_DAMAGE + "," +
                    DB_ITEMS_STACKSIZE + "," +
                    DB_ITEMS_NBT + ") VALUES (?, ?, ?, ?) ");
            ps.setInt(1, item.itemID);
            ps.setInt(2, item.getItemDamage());
            ps.setInt(3, item.stackSize);

            byte[] bytes = null;

            if (item.hasTagCompound()) {
                try {
                    bytes = CompressedStreamTools.compress(item.getTagCompound());
                } catch (IOException e) {
                    e.printStackTrace();
                    bytes = null;
                }
            }
            ps.setBytes(4, bytes);

            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            Femtocraft.logger.log(Level.SEVERE, "SQLite insert item" +
                    " failed");
            return false;
        }
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
                arrayList.add(getRecipe(rs));
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
        ArrayList<AssemblerRecipe> arrayList = new ArrayList<AssemblerRecipe>();
        try {
            refreshConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM " +
                    DB_TABLE_RECIPES + " WHERE " + DB_RECIPES_TECHNOLOGY + " " +
                    "= ?", ResultSet.HOLD_CURSORS_OVER_COMMIT);
            ps.setString(1, tech.name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arrayList.add(getRecipe(rs));
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
