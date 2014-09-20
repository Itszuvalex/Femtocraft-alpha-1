package com.itszuvalex.femtocraft.power;

import net.minecraft.block.Block;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Chris on 9/20/2014.
 */
public class MagnetRegistry {
    private static Map<Integer, Integer> blockIDToStrengthMap = new TreeMap<Integer, Integer>();

    public static boolean registerMagnet(Block block, int strength) {
        return registerMagnet(block.blockID, strength);
    }

    public static boolean registerMagnet(int blockID, int strength) {
        return blockIDToStrengthMap.put(blockID, strength) != null;
    }

    public static Integer getMagnetStrength(Block block) {
        return getMagnetStrength(block.blockID);
    }

    public static Integer getMagnetStrength(int blockId) {
        return blockIDToStrengthMap.get(blockId);
    }

    public static boolean isMagnet(Block block) {
        return isMagnet(block.blockID);
    }

    public static boolean isMagnet(int blockID) {
        return blockIDToStrengthMap.containsKey(blockID);
    }
}
