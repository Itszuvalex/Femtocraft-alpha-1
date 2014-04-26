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

package femtocraft.industry.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

import java.util.Random;

public class BlockDepletedStone extends Block {
    public Icon activeIcon;

    public BlockDepletedStone(int par1) {
        super(par1, Material.rock);
        setCreativeTab(Femtocraft.femtocraftTab);
        setUnlocalizedName("BlockDepletedStone");
    }

    /*
     * (non-Javadoc)
     *
     * @see net.minecraft.block.Block#getIcon(int, int)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2) {
        return par2 == 0 ? blockIcon : activeIcon;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.minecraft.block.Block#quantityDropped(java.util.Random)
     */
    @Override
    public int quantityDropped(Random par1Random) {
        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * net.minecraft.block.Block#registerIcons(net.minecraft.client.renderer
     * .texture.IconRegister)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID
                                                               .toLowerCase() + ":" + "BlockDepletedStone");
        this.activeIcon = par1IconRegister.registerIcon(Femtocraft.ID
                                                                .toLowerCase() + ":" + "BlockDepletedStone_active");
    }
}
