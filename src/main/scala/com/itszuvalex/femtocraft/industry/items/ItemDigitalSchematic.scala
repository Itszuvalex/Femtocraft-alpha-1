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
package com.itszuvalex.femtocraft.industry.items

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.core.Configurable
import com.itszuvalex.femtocraft.api.items.AssemblerSchematic
import com.itszuvalex.femtocraft.core.items.ItemBase
import com.itszuvalex.femtocraft.industry.items.ItemDigitalSchematic.SCHEMATIC_USES
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon

object ItemDigitalSchematic {
  @Configurable(comment = "Number of uses good for.") private val SCHEMATIC_USES = 64
}

@Configurable class ItemDigitalSchematic(uName: String)
  extends ItemBase(uName) with AssemblerSchematic {
  setMaxDamage(SCHEMATIC_USES)

  var keyed: IIcon = null

  @SideOnly(Side.CLIENT)
  override def blankIcon = itemIcon

  @SideOnly(Side.CLIENT)
  override def keyedIcon = keyed

  @SideOnly(Side.CLIENT) override def registerIcons(par1IconRegister: IIconRegister) {
    itemIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "ItemDigitalSchematic")
    keyed = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "ItemDigitalSchematicKeyed")
  }
}
