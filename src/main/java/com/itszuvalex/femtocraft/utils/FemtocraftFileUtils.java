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

package com.itszuvalex.femtocraft.utils;

import com.itszuvalex.femtocraft.Femtocraft;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.io.File;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/26/14.
 */
public class FemtocraftFileUtils {
    public static String savePath(World world) {
        return !MinecraftServer.getServer().isDedicatedServer() ? Minecraft.getMinecraft().mcDataDir + "/saves/"
                +
                world.getSaveHandler().getWorldDirectoryName() : MinecraftServer.getServer().getFile
                (world.getSaveHandler().getWorldDirectoryName())
                                                                                .getPath();
    }

    public static String savePathFemtocraft(World world) {
        File dir = new File(savePath(world), Femtocraft.ID().toLowerCase());
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir.getPath();
    }
}


