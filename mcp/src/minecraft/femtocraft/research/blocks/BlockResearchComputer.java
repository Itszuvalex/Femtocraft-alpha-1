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

package femtocraft.research.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.core.blocks.TileContainer;
import femtocraft.research.tiles.TileEntityResearchComputer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockResearchComputer extends TileContainer {
    public Icon top;

    public BlockResearchComputer(int par1) {
        super(par1, Material.iron);
        setUnlocalizedName("researchComputer");
        setCreativeTab(Femtocraft.femtocraftTab);
    }

    /*
     * (non-Javadoc)
     *
     * @see net.minecraft.block.Block#getIcon(int, int)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2) {
        switch (ForgeDirection.getOrientation(par1)) {
            case UP:
                return top;
            default:
                return blockIcon;
        }
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
        blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                                                          + ":" + "BasicMachineBlockSide");
        top = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":"
                                                    + "BlockResearchComputer_top");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * net.minecraft.block.Block#createTileEntity(net.minecraft.world.World,
     * int)
     */
    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityResearchComputer();
    }

}
