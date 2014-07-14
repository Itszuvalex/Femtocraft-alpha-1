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

package com.itszuvalex.femtocraft.managers.assembler;

import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import net.minecraft.item.ItemStack;

import java.util.Comparator;

public class ComparatorItemStackArray implements Comparator<ItemStack[]> {

    @Override
    public int compare(ItemStack[] o1, ItemStack[] o2) {
        boolean no1 = o1 == null;
        boolean no2 = o2 == null;

        if (no1 && !no2) {
            return -1;
        }
        if (!no1 && no2) {
            return 1;
        }
        if (no1 && no2) {
            return 0;
        }

        for (int i = 0; i < o1.length; i++) {
            int comp = FemtocraftUtils.compareItem(o1[i], o2[i]);
            if (comp != 0) {
                return comp;
            }
        }

        return 0;
    }

}
