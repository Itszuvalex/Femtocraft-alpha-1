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

package com.itszuvalex.femtocraft.consumables.items;

import com.itszuvalex.femtocraft.Femtocraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemTomato extends ItemFood {
    public ItemTomato(int id) {

        super(id, 2, 0.6f, false);
        setMaxStackSize(64);
        setCreativeTab(Femtocraft.femtocraftTab);
        setTextureName(Femtocraft.ID.toLowerCase() + ":" + "ItemTomato");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * net.minecraft.item.ItemFood#getMaxItemUseDuration(net.minecraft.item.
     * ItemStack)
     */
    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return super.getMaxItemUseDuration(par1ItemStack);
    }

    /*
     * (non-Javadoc)
     *
     * @see net.minecraft.item.ItemFood#getHealAmount()
     */
    @Override
    public int getHealAmount() {
        return super.getHealAmount();
    }

    /*
     * (non-Javadoc)
     *
     * @see net.minecraft.item.ItemFood#getSaturationModifier()
     */
    @Override
    public float getSaturationModifier() {
        return super.getSaturationModifier();
    }

    public void updateIcons(IconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                                                          + ":" + "ItemTomato");
    }

}
