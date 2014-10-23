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

package com.itszuvalex.femtocraft.core.ore;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.configuration.Configurable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

import java.util.Random;

@Configurable
public class BlockOreFarenite extends BlockOreBase {
    @Configurable(comment = "Maximum amount of Farenite dust to drop.")
    public static int DROP_AMOUNT_MAX = 4;
    @Configurable(comment = "Minimum amount of Farenite dust to drop.")
    public static int DROP_AMOUNT_MIN = 4;

    public BlockOreFarenite() {
        super();
        setCreativeTab(Femtocraft.femtocraftTab());
        setBlockTextureName(Femtocraft.ID().toLowerCase() + ":" + "BlockOreFarenite");
        setBlockName("BlockOreFarenite");
        setHardness(3.0f);
        setStepSound(Block.soundTypeStone);
        setResistance(1f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID()
                                                               .toLowerCase() + ":" + "BlockOreFarenite");
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Femtocraft.itemIngotFarenite();
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(DROP_AMOUNT_MAX + 1 - DROP_AMOUNT_MIN) + DROP_AMOUNT_MIN;
    }
}
