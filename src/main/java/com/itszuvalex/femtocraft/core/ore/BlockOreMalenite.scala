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
package com.itszuvalex.femtocraft.core.ore

import java.util.Random

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.configuration.Configurable
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.client.renderer.texture.IIconRegister

object BlockOreMalenite {
  @Configurable(comment = "Maximum amount of Malenite dust to drop.") var DROP_AMOUNT_MAX = 4
  @Configurable(comment = "Minimum amount of Malenite dust to drop.") var DROP_AMOUNT_MIN = 2
}

@Configurable class BlockOreMalenite extends BlockOreBase {
  setCreativeTab(Femtocraft.femtocraftTab)
  setBlockTextureName(Femtocraft.ID.toLowerCase + ":" + "BlockOreMalenite")
  setBlockName("BlockOreMalenite")
  setHardness(3.0f)
  setStepSound(Block.soundTypeStone)
  setResistance(1f)

  @SideOnly(Side.CLIENT) override def registerBlockIcons(par1IconRegister: IIconRegister) {
    this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockOreMalenite")
  }

  override def getItemDropped(p_149650_1_ : Int, p_149650_2_ : Random, p_149650_3_ : Int) = Femtocraft.itemIngotMalenite

  override def quantityDropped(random: Random) = random.nextInt(BlockOreMalenite.DROP_AMOUNT_MAX + 1 - BlockOreMalenite.DROP_AMOUNT_MIN) + BlockOreMalenite.DROP_AMOUNT_MIN
}
