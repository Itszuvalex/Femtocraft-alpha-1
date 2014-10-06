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
import net.minecraft.client.renderer.texture.IconRegister;

import java.util.Random;

@Configurable
public class BlockOreMalenite extends BlockOreBase {
    @Configurable(comment = "Maximum amount of Malenite dust to drop.")
    public static int DROP_AMOUNT_MAX = 4;
    @Configurable(comment = "Minimum amount of Malenite dust to drop.")
    public static int DROP_AMOUNT_MIN = 2;

    public BlockOreMalenite(int id) {
        super(id);
        setCreativeTab(Femtocraft.femtocraftTab());
        setTextureName(Femtocraft.ID().toLowerCase() + ":" + "BlockOreMalenite");
        setUnlocalizedName("BlockOreMalenite");
        setHardness(3.0f);
        setStepSound(Block.soundStoneFootstep);
        setResistance(1f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID()
                                                                 .toLowerCase() + ":" + "BlockOreMalenite");
    }

    @Override
    public int idDropped(int par1, Random random, int par2) {
        return Femtocraft.itemIngotMalenite().itemID;
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(DROP_AMOUNT_MAX + 1 - DROP_AMOUNT_MIN) + DROP_AMOUNT_MIN;
    }
}
