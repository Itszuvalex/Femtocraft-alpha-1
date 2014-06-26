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

package femtocraft.core.items;

import femtocraft.Femtocraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

/**
 * Created by Christopher Harris (Itszuvalex) on 6/22/14.
 */
public class ItemBase extends Item {

    public ItemBase(int par1, String unlocalizedName) {
        super(par1);
        setMaxStackSize(64);
        setCreativeTab(Femtocraft.femtocraftTab);
        setUnlocalizedName(unlocalizedName);
        setTextureName(Femtocraft.ID.toLowerCase() + ":" + unlocalizedName);
    }

    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(Femtocraft.ID
                .toLowerCase() + ":" + getUnlocalizedName());
    }
}
