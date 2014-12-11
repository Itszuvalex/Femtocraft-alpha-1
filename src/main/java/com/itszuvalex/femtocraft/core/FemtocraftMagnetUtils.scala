package com.itszuvalex.femtocraft.core

import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.core.Configurable
import net.minecraft.block.Block
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.{Entity, EntityLivingBase}
import net.minecraft.util.AxisAlignedBB
import net.minecraft.world.World
import org.apache.logging.log4j.Level

import scala.collection.JavaConversions._

/**
 * Created by Chris on 9/20/2014.
 */
@Configurable object FemtocraftMagnetUtils {
  @Configurable(comment = "Multiplier of magnetic strength to radius.")                                     val strengthToRadiusMultiplier   = .05f
  @Configurable(comment = "Ratio of strength/distance to item strength or greater to pull from inventory.") val inventoryPullStrengthRatio   = 2f
  @Configurable(comment = "Strength to velocity multiplier.")                                               val strengthToVelocityMultiplier = .3d

  /**
   * @param block
   * @param world
   * @param x
   * @param y
   * @param z
   * @param delta # of Updates/Tick    /    20 (Minecraft tick rate)
   */
  def applyMagnetismFromBlock(block: Block, world: World, x: Int, y: Int, z: Int, delta: Double) {
    if (MagnetRegistry.isMagnet(block)) applyMagnetismFromLocation(MagnetRegistry.getMagnetStrength(block), world, x + .5D, y + .5D, z + .5D, delta)
  }

  def applyMagnetismFromLocation(strength: Int, world: World, x: Double, y: Double, z: Double, delta: Double) {
    val radius = strength * strengthToRadiusMultiplier
    val bb = AxisAlignedBB.getBoundingBox(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius)
    world.getEntitiesWithinAABB(classOf[Entity], bb).asInstanceOf[util.List[Entity]].
    foreach { entity =>
      val distSqr = entity.getDistanceSq(x, y, z)
      if (distSqr <= (radius * radius)) {
        val distance = Math.sqrt(distSqr)
        entity match {
          case player: EntityPlayer   =>
            if (!player.capabilities.isCreativeMode) {
              if (!world.isRemote) {
                for (i <- 0 until player.inventory.mainInventory.length) {
                  val item = player.inventory.mainInventory(i)
                  if (MagnetRegistry.isMagnet(item)) {
                    val itemStr = MagnetRegistry.getMagnetStrength(item)
                    if (((strength.toDouble / Math.max(distance, 1d)) / itemStr.toDouble) >= inventoryPullStrengthRatio) {
                      val ei = player.entityDropItem(item, entity.height / 2f)
                      ei.delayBeforeCanPickup = 20
                      player.inventory.mainInventory(i) = null
                    }
                  }
                }
              }
              for (i <- 0 until player.inventory.armorInventory.length) {
                val item = player.inventory.armorInventory(i)
                if (MagnetRegistry.isMagnet(item)) {
                  val itemStrength = MagnetRegistry.getMagnetStrength(item)
                  val velocity = itemStrength * strengthToVelocityMultiplier
                  val velX = (Math.signum(x - player.posX) * velocity / (50 * Math.max(distance, 1))) * distance * delta
                  val velY = (Math.signum(y - player.posY) * velocity / (50 * Math.max(distance, 1))) * distance * delta
                  val velZ = (Math.signum(z - player.posZ) * velocity / (50 * Math.max(distance, 1))) * distance * delta
                  player.addVelocity(velX, velY, velZ)
                }
              }
            }
          case base: EntityLivingBase =>
            if (!world.isRemote) {
              val lastActiveItems = entity.getLastActiveItems
              for (i <- 0 until lastActiveItems.length) {
                val item = lastActiveItems(i)
                if (MagnetRegistry.isMagnet(item)) {
                  val itemStr = MagnetRegistry.getMagnetStrength(item)
                  if ((strength / itemStr) > inventoryPullStrengthRatio) {
                    val ei = base.entityDropItem(item, base.height / 2f)
                    ei.delayBeforeCanPickup = 20
                    base.setCurrentItemOrArmor(i, null)
                  }
                }
              }
            }
          case item: EntityItem       =>
            if (MagnetRegistry.isMagnet(item.getEntityItem)) {
              val velocity = strength * strengthToVelocityMultiplier
              val velX = (Math.signum(x - item.posX) * velocity / (Math.max(distance, 1) * 50)) * distance * delta
              val velY = (Math.signum(y - item.posY) * velocity / (Math.max(distance, 1) * 50)) * distance * delta
              val velZ = (Math.signum(z - item.posZ) * velocity / (Math.max(distance, 1) * 50)) * distance * delta
              item.addVelocity(velX, velY, velZ)
            }
          case _                      =>
        }
      }
            }

  }
}

