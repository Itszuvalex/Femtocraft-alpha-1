package com.itszuvalex.femtocraft.core;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.configuration.Configurable;
import com.itszuvalex.femtocraft.managers.assembler.AssemblerRecipe;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

/**
 * Created by Chris on 9/20/2014.
 */
@Configurable
public class MagnetRegistry {
    @Configurable(comment = "Maximum depth to search the magnet tree.")
    public static int MAXIMUM_DEPTH = 3000;

    @Configurable(comment = "Set true to show magnetism value in tooltip at all.")
    public static boolean showMagnetismTooltip = true;

    @Configurable(comment = "Set this to true to only show magnetism value if advanced tooltips are on.")
    public static boolean magnetismTooltipIsAdvanced = false;

    @Configurable
    public static int ORE_LODESTONE = 50;
    @Configurable
    public static int NUGGET_LODESTONE = 10;
    @Configurable
    public static int CHUNK_LODESTONE = 40;
    @Configurable
    public static int ORE_IRON = 5;
    @Configurable
    public static int INGOT_IRON = 5;


    private static Map<Integer, Integer> idToStrengthMap = new TreeMap<>();

    public static void init() {
        registerMagnetDefaults();
        registerMagnetTree();
    }

    private static void registerMagnetTree() {
        Femtocraft.log(Level.INFO, "Registering all magnets.");
        ArrayList<AssemblerRecipe> recipes = Femtocraft.recipeManager().assemblyRecipes.getAllRecipes();
        boolean changed = true;
        int depth = 0;
        while (changed && depth < MAXIMUM_DEPTH) {
            changed = false;
            for (AssemblerRecipe recipe : recipes) {
                int prevStrength = isMagnet(recipe.output) ? getMagnetStrength(recipe.output) : 0;
                int strength = 0;
                for (ItemStack input : recipe.input) {
                    strength += isMagnet(input) ? getMagnetStrength(input) : 0;
                }
                if (strength > 0) {
                    if ((strength / recipe.output.stackSize) != prevStrength) {
                        registerMagnet(recipe.output, strength / recipe.output.stackSize);
                        changed = true;
                    }
                }
            }
            depth++;
        }
        Femtocraft.log(Level.INFO, "Finished registering magnets.  Total depth was " + depth + ".");
    }

    private static void registerMagnetDefaults() {
        registerMagnet(Blocks.iron_ore, ORE_IRON);
        registerMagnet(Items.iron_ingot, INGOT_IRON);
        registerMagnet(Femtocraft.blockOreLodestone(), ORE_LODESTONE);
        registerMagnet(Femtocraft.itemNuggetLodestone(), NUGGET_LODESTONE);
        registerMagnet(Femtocraft.itemChunkLodestone(), CHUNK_LODESTONE);
    }

    public static boolean registerMagnet(Block block, int strength) {
        return block != null && registerMagnet(new ItemStack(block), strength);
    }

    public static boolean registerMagnet(int id, int strength) {
        return idToStrengthMap.put(id, strength) != null;
    }

    public static boolean registerMagnet(Item item, int strength) {
        return item != null && registerMagnet(Item.getIdFromItem(item), strength);
    }

    public static boolean registerMagnet(ItemStack item, int strength) {
        return item != null && registerMagnet(Item.getIdFromItem(item.getItem()), strength);
    }

    public static int getMagnetStrength(Block block) {
        return block == null ? 0 : getMagnetStrength(new ItemStack(block));
    }

    public static int getMagnetStrength(Item item) {
        return item == null ? 0 : getMagnetStrength(Item.getIdFromItem(item));
    }

    public static int getMagnetStrength(int id) {
        return idToStrengthMap.get(id);
    }

    public static int getMagnetStrength(ItemStack item) {
        return item == null ? 0 : getMagnetStrength(Item.getIdFromItem(item.getItem()));
    }

    public static boolean isMagnet(Block block) {
        return block != null && isMagnet(new ItemStack(block));
    }

    public static boolean isMagnet(int id) {
        return idToStrengthMap.containsKey(id);
    }

    public static boolean isMagnet(Item item) {
        return item != null && isMagnet(Item.getIdFromItem(item));
    }

    public static boolean isMagnet(ItemStack item) {
        return item != null && isMagnet(Item.getIdFromItem(item.getItem()));
    }
}
