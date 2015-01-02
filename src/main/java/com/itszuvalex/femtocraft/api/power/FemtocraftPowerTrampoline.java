package com.itszuvalex.femtocraft.api.power;

import net.minecraft.world.World;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Chris on 11/14/2014.
 */
public class FemtocraftPowerTrampoline {
    /**
     * {@link com.itszuvalex.femtocraft.power.FemtocraftPowerUtils#distributePower(IPowerTileContainer, boolean[],
     * net.minecraft.world.World, int, int, int)}
     */
    public static void distributePower(IPowerTileContainer container, boolean[] connections, World world, int x,
                                       int y, int z) {
        try {
            Class<?> clazz = Class.forName("com.itszuvalex.femtocraft.power.FemtocraftPowerUtils");
            Method method = clazz.getMethod("distributePower", IPowerTileContainer.class, boolean[].class, World
                    .class, int.class, int.class, int.class);
            method.invoke(container, connections, world, x, y, z);
        } catch (ClassNotFoundException e) {
            System.out.print("Class com.itszuvalex.femtocraft.power.FemtocraftPowerUtils not found.  Is Femtocraft " +
                             "loaded?");
        } catch (NoSuchMethodException e) {
            System.out.print(
                    "Method distributePower(IPowerTileContainer, boolean[], World, int, int, int) not found.  Is" +
                    " your FemtocraftAPI up to date?");

        } catch (InvocationTargetException e) {
            System.out.print("Method distributePower(IPowerTileContainer, boolean[], World, int, int, int) threw an " +
                             "error.");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.print(
                    "Method distributePower(IPowerTileContainer, boolean[], World, int, int, int) is not accessible. " +
                    " Is  your Femtocraft API up to date?");
        }
    }
}
