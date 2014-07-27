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

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.ChunkDataEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.logging.Level;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/26/14.
 */
public class FemtocraftOreRetrogenHandler {
    Random random = new Random();
    private static boolean RetroGenning = false;
    private static final String DIRECTORY = "Retrogen";

    public FemtocraftOreRetrogenHandler() {

    }

    @ForgeSubscribe
    public void onChunkLoad(ChunkDataEvent.Load event) {
        if (event.world.isRemote) return;
        if (event.isCanceled()) return;
        if (!FemtocraftConfigs.retroGen) return;
        if (RetroGenning) return;

        RetroGenning = true;

        int regionX = event.getChunk().xPosition >> 5;
        int regionZ = event.getChunk().zPosition >> 5;

        NBTTagCompound regionCompound = getRetroGenFile(event.world, regionX, regionZ);

        if (regionCompound == null) {
            if (FemtocraftConfigs.retrogenAlerts) {
                Femtocraft.logger.log(Level.WARNING, "No Retrogen File found for worldId:" + event.world.provider.dimensionId + " x:" + regionX + " z:" + regionZ + ".  Generating now.");
            }
        }

        regionCompound = regionCompound == null ? new NBTTagCompound() : regionCompound;

        if (FemtocraftOreGenerator.shouldRetroGenerate(event.world, regionCompound)) {
            int minRegionChunkX = regionX << 5;
            int maxRegionChunkX = minRegionChunkX + 32;
            int minRegionChunkZ = regionZ << 5;
            int maxRegionChunkZ = maxRegionChunkX + 32;

            for (int x = minRegionChunkX; x < maxRegionChunkX; ++x) {
                for (int z = minRegionChunkZ; z < maxRegionChunkZ; ++z) {
                    FemtocraftOreGenerator.retroGenerate(random, event.world.getChunkFromChunkCoords(x, z), regionCompound);
                }
            }

            saveRetroGenFile(event.world, regionX, regionZ, regionCompound);
        }
        RetroGenning = false;
    }

    private boolean saveRetroGenFile(World world, int regionX, int regionZ, NBTTagCompound regionCompound) {
        try {
            File folder = new File(FemtocraftFileUtils.savePathFemtocraft(world), DIRECTORY);
            if (!folder.exists()) {
                folder.mkdir();
            }
            File worldFolder = new File(folder.getPath(), String.valueOf(world.provider.dimensionId));
            if (!worldFolder.exists()) {
                worldFolder.mkdir();
            }
            String filename = getRegionFileName(regionX, regionZ);
            try {
                File file = new File(worldFolder, filename);
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileOutputStream fileoutputstream = new FileOutputStream(
                        file);
                CompressedStreamTools.writeCompressed(regionCompound,
                        fileoutputstream);
                fileoutputstream.close();
            } catch (Exception exception) {
                Femtocraft.logger.log(Level.SEVERE,
                        "Failed to save data for player " + filename
                                + " in world - " + FemtocraftFileUtils.savePathFemtocraft(world) + "."
                );
                exception.printStackTrace();
            }

        } catch (Exception e) {
            Femtocraft.logger.log(Level.SEVERE, "Failed to create folder "
                    + FemtocraftFileUtils.savePathFemtocraft(world) + File.pathSeparator + DIRECTORY + ".");
            e.printStackTrace();
            return false;
        }

        return false;
    }

    private NBTTagCompound getRetroGenFile(World world, int regionX, int regionZ) {
        try {
            File folder = new File(FemtocraftFileUtils.savePathFemtocraft(world), DIRECTORY);
            if (!folder.exists()) {
                if (FemtocraftConfigs.retrogenAlerts) {
                    Femtocraft.logger.log(Level.WARNING, "No " + DIRECTORY
                            + " folder found for world - " + FemtocraftFileUtils.savePathFemtocraft(world) + ".");
                }
                return null;
            }
            File worldFolder = new File(folder.getPath(), String.valueOf(world.provider.dimensionId));
            if (!worldFolder.exists()) {
                if (FemtocraftConfigs.retrogenAlerts) {
                    Femtocraft.logger.log(Level.WARNING, "No " + String.valueOf(world.provider.dimensionId)
                            + " folder found for world - " + FemtocraftFileUtils.savePathFemtocraft(world) + ".");
                }
                return null;
            }


            String filename = getRegionFileName(regionX, regionZ);
            try {
                File regionFile = new File(worldFolder.getPath(), filename);
                if (!regionFile.exists()) return null;
                FileInputStream fileinputstream = new FileInputStream(regionFile);
                NBTTagCompound data = CompressedStreamTools
                        .readCompressed(fileinputstream);
                fileinputstream.close();
                return data;
            } catch (Exception e) {
                Femtocraft.logger.log(Level.SEVERE,
                        "Failed to load data from file " + filename
                                + " in world - " + FemtocraftFileUtils.savePathFemtocraft(world) + "."
                );
                e.printStackTrace();
            }

        } catch (Exception exception) {
            Femtocraft.logger.log(Level.SEVERE,
                    "Failed to load data from folder " + DIRECTORY
                            + " in world - " + FemtocraftFileUtils.savePathFemtocraft(world) + "."
            );
            exception.printStackTrace();
            return null;
        }

        return null;
    }

    private String getRegionFileName(int regionX, int regionZ) {
        return "region." + regionX + "." + regionZ + ".dat";
    }


    @ForgeSubscribe
    public void onChunkSave(ChunkDataEvent.Save event) {

    }
}
