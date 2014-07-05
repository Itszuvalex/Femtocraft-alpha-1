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

package com.itszuvalex.femtocraft;

import com.itszuvalex.femtocraft.common.gui.DisplaySlot;
import com.itszuvalex.femtocraft.industry.items.ItemAssemblySchematic;
import com.itszuvalex.femtocraft.player.PropertiesNanite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;

public class FemtocraftEventHookContainer {
    @ForgeSubscribe
    public void load(WorldEvent.Load event) {
        if (event.world.isRemote) {
            return;
        }
        Femtocraft.researchManager.load(event.world);
    }

    @ForgeSubscribe
    public void save(WorldEvent.Save event) {
        if (event.world.isRemote) {
            return;
        }
        Femtocraft.researchManager.save(event.world);
    }

    @ForgeSubscribe
    public void preTextureStitch(TextureStitchEvent.Pre event) {
        // Skip out of blocks
        if (event.map.textureType == 0) {
            return;
        }
        // Want items
        ItemAssemblySchematic.placeholderIcon = event.map
                .registerIcon(Femtocraft.ID.toLowerCase() + ":"
                        + "ItemAssemblySchematic");
        DisplaySlot.noPlaceDisplayIcon = event.map.registerIcon(Femtocraft.ID
                .toLowerCase() + ":" + "NoPlaceSlot");
    }

    @ForgeSubscribe
    public void onEntityConstructing(EntityConstructing event) {
        if (event.entity instanceof EntityPlayer
                && event.entity
                .getExtendedProperties(PropertiesNanite.PROP_TAG) == null) {
            PropertiesNanite.register((EntityPlayer) event.entity);
        }
    }

    @ForgeSubscribe
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!event.entity.worldObj.isRemote
                && event.entity instanceof EntityPlayer) {
            PropertiesNanite.get((EntityPlayer) event.entity).sync();
        }
    }
}