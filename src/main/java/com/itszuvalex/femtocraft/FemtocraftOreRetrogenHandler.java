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

package com.itszuvalex.femtocraft;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.ChunkDataEvent;

import java.util.Random;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/26/14.
 */
public class FemtocraftOreRetrogenHandler {
    Random random = new Random();

    public FemtocraftOreRetrogenHandler() {

    }

    @ForgeSubscribe
    public void onChunkLoad(ChunkDataEvent.Load event) {
        if (event.world.isRemote) return;
        if (event.isCanceled()) return;
        if (!FemtocraftConfigs.worldGen) return;
        if (!FemtocraftConfigs.retroGen) return;
        FemtocraftOreGenerator.retroGenerate(random, event.getChunk(), event.getData());
        event.getChunk().setChunkModified();
    }


    @ForgeSubscribe
    public void onChunkSave(ChunkDataEvent.Save event)
    {

    }
}
