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

package femtocraft.industry.gui.graph;

import femtocraft.graph.GraphNode;
import net.minecraft.item.ItemStack;

/**
 * Created by Christopher Harris (Itszuvalex) on 4/26/14.
 */
public class AssemblerNode extends GraphNode {
    private final ItemStack item;

    public AssemblerNode(ItemStack item) {
        super();
        this.item = item;
    }

    public ItemStack getItemStack() {
        return item;
    }

    @Override
    public int getXPadding() {
        return 0;
    }

    @Override
    public int getYPadding() {
        return 0;
    }

}