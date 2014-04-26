/*******************************************************************************
 * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 * Itszuvalex@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/

package femtocraft.core.ore;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IconRegister;

import java.util.Random;

public class BlockOreFarenite extends BlockOre {

    public BlockOreFarenite(int id) {
        super(id);
        setCreativeTab(Femtocraft.femtocraftTab);
        setTextureName(Femtocraft.ID.toLowerCase() + ":" + "BlockOreFarenite");
        setUnlocalizedName("BlockOreFarenite");
        setHardness(3.0f);
        setStepSound(Block.soundStoneFootstep);
        setResistance(1f);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID
                                                               .toLowerCase() + ":" + "BlockOreFarenite");
    }

    public int idDropped(int par1, Random random, int par2) {
        return Femtocraft.ingotFarenite.itemID;
    }

    public int quantityDropped(Random random) {
        return 4;
    }
}
