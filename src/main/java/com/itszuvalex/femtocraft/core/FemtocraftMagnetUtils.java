package com.itszuvalex.femtocraft.core;

import com.itszuvalex.femtocraft.configuration.Configurable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 9/20/2014.
 */
@Configurable
public class FemtocraftMagnetUtils {
    @Configurable(comment = "Multiplier of magnetic strength to radius.")
    public static float strengthToRadiusMultiplier = .05f;
    @Configurable(comment = "Ratio of strength/distance to item strength or greater to pull from inventory.")
    public static float inventoryPullStrengthRatio = 2f;
    @Configurable(comment = "Strength to velocity multiplier.")
    public static double strengthToVelocityMultiplier = .0000000001d;

    /**
     * @param block
     * @param world
     * @param x
     * @param y
     * @param z
     * @param delta # of Updates/Tick    /    20 (Minecraft tick rate)
     */
    public static void applyMagnetismFromBlock(Block block, World world, int x, int y, int z, double delta) {
        if (!MagnetRegistry.isMagnet(block)) {return;}
        applyMagnetismFromLocation(MagnetRegistry.getMagnetStrength(block), world, x + .5D, y + .5D, z + .5D, delta);
    }

    public static void applyMagnetismFromLocation(int strength, World world, double x, double y, double z,
                                                  double delta) {
        float radius = strength * strengthToRadiusMultiplier;
//
//        AxisAlignedBB bb = AxisAlignedBB.getAABBPool().getAABB(
//                x - radius, y - radius, z - radius, x + radius, y + radius, z + radius);
//        List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, bb);
        List<Entity> entities = new ArrayList<Entity>(world.getLoadedEntityList());
        for (Entity entity : entities) {
            double distSqr = entity.getDistanceSq(x, y, z);
            if (distSqr > (radius * radius)) continue;
            double distance = Math.sqrt(distSqr);
            if (entity instanceof EntityPlayer) {
                if (!((EntityPlayer) entity).capabilities.isCreativeMode) {
                    if (!world.isRemote) {
                        for (int i = 0; i < ((EntityPlayer) entity).inventory.mainInventory.length; ++i) {
                            ItemStack item = ((EntityPlayer) entity).inventory.mainInventory[i];
                            if (!MagnetRegistry.isMagnet(item)) continue;
                            int itemStr = MagnetRegistry.getMagnetStrength(item);
                            if ((((double) strength / Math.max(distance, 1d)) / (double) itemStr) >=
                                inventoryPullStrengthRatio) {
                                EntityItem ei = entity.entityDropItem(item, entity.height / 2f);
                                ei.delayBeforeCanPickup = 20;
                                ((EntityPlayer) entity).inventory.mainInventory[i] = null;
                            }
                        }
                    }
                    for (int i = 0; i < ((EntityPlayer) entity).inventory.armorInventory.length; ++i) {
                        ItemStack item = ((EntityPlayer) entity).inventory.armorInventory[i];
                        if (MagnetRegistry.isMagnet(item)) {
                            int itemStrength = MagnetRegistry.getMagnetStrength(item);
                            double velocity = itemStrength * strengthToVelocityMultiplier;
                            double velX =
                                    (Math.signum(x - entity.posX) * velocity / (50 * Math.max(distance, 1))) *
                                    distance *
                                    delta;
                            double velY =
                                    (Math.signum(y - entity.posY) * velocity / (50 * Math.max(distance, 1))) *
                                    distance *
                                    delta;
                            double velZ =
                                    (Math.signum(z - entity.posZ) * velocity / (50 * Math.max(distance, 1))) *
                                    distance *
                                    delta;
                            entity.addVelocity(velX, velY, velZ);
                        }
                    }
                }
            } else if (entity instanceof EntityLivingBase) {
                ItemStack[] lastActiveItems = entity.getLastActiveItems();
                if (!world.isRemote) {
                    for (int i = 0; i < lastActiveItems.length; i++) {
                        ItemStack item = lastActiveItems[i];
                        if (!MagnetRegistry.isMagnet(item)) continue;
                        int itemStr = MagnetRegistry.getMagnetStrength(item);
                        if ((strength / itemStr) > inventoryPullStrengthRatio) {
                            EntityItem ei = entity.entityDropItem(item, entity.height / 2f);
                            ei.delayBeforeCanPickup = 20;
                            entity.setCurrentItemOrArmor(i, null);
                        }
                    }
                }
            } else if (entity instanceof EntityItem) {
                if (MagnetRegistry.isMagnet(((EntityItem) entity).getEntityItem())) {
                    double velocity = strength * strengthToVelocityMultiplier;
                    double velX =
                            (Math.signum(x - entity.posX) * velocity / (Math.max(distance, 1) * 50)) * distance * delta;
                    double velY =
                            (Math.signum(y - entity.posY) * velocity / (Math.max(distance, 1) * 50)) * distance * delta;
                    double velZ =
                            (Math.signum(z - entity.posZ) * velocity / (Math.max(distance, 1) * 50)) * distance * delta;
                    entity.addVelocity(velX, velY, velZ);
                }
            }
        }
    }
}
