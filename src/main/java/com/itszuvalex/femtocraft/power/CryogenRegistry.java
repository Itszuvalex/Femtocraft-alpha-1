package com.itszuvalex.femtocraft.power;

import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * Created by Chris on 8/19/2014.
 */
public class CryogenRegistry {
    private static ArrayList<ICryogenHandler> passiveHandlers = new ArrayList<ICryogenHandler>();
    private static ArrayList<ICryogenHandler> activeHandlers = new ArrayList<ICryogenHandler>();

    public static void init() {
        registerPassiveHandler(new CryogenPassiveHandler());
        registerActiveHandler(new CryogenActiveHandler());
    }

    public static boolean registerPassiveHandler(ICryogenHandler handler) {
        return passiveHandlers.add(handler);
    }

    public static boolean registerActiveHandler(ICryogenHandler handler) {
        return activeHandlers.add(handler);
    }

    public static boolean unregisterPassiveHandler(ICryogenHandler handler) {
        return passiveHandlers.remove(handler);
    }

    public static boolean unregisterActiveHandler(ICryogenHandler handler) {
        return activeHandlers.remove(handler);
    }

    public static float getPassivePower(World world, int x, int y, int z) {
        return getPower(world, x, y, z, passiveHandlers);
    }

    public static float getActivePower(World world, int x, int y, int z) {
        return getPower(world, x, y, z, activeHandlers);
    }

    private static float getPower(World world, int x, int y, int z, ArrayList<ICryogenHandler> handlers) {
        for (ICryogenHandler handler : handlers) {
            if (handler.canHandleBlock(world, x, y, z)) {
                float amount = handler.powerForBlock(world, x, y, z);
                handler.usedBlockForPower(world, x, y, z);
                return amount;
            }
        }
        return 0;
    }
}
