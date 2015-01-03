package com.itszuvalex.femtocraft.api.items

import com.itszuvalex.femtocraft.api.EnumTechLevel
import net.minecraft.item.Item

/**
 * Interface for Items that want to interact with blocks and tiles.
 */
trait IInterfaceDevice extends Item {
  def getInterfaceLevel: EnumTechLevel
}
