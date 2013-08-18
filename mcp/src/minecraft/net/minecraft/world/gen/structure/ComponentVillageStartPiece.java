package net.minecraft.world.gen.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

public class ComponentVillageStartPiece extends ComponentVillageWell
{
    public final WorldChunkManager worldChunkMngr;

    /** Boolean that determines if the village is in a desert or not. */
    public final boolean inDesert;
    public final BiomeGenBase biome;

    /** World terrain type, 0 for normal, 1 for flap map */
    public final int terrainType;
    public StructureVillagePieceWeight structVillagePieceWeight;

    /**
     * Contains List of all spawnable Structure Piece Weights. If no more Pieces of a type can be spawned, they are
     * removed from this list
     */
    public List structureVillageWeightedPieceList;
    public List field_74932_i = new ArrayList();
    public List field_74930_j = new ArrayList();

    public ComponentVillageStartPiece(WorldChunkManager par1WorldChunkManager, int par2, Random par3Random, int par4, int par5, List par6List, int par7)
    {
        super((ComponentVillageStartPiece)null, 0, par3Random, par4, par5);
        this.worldChunkMngr = par1WorldChunkManager;
        this.structureVillageWeightedPieceList = par6List;
        this.terrainType = par7;
        BiomeGenBase biomegenbase = par1WorldChunkManager.getBiomeGenAt(par4, par5);
        this.inDesert = biomegenbase == BiomeGenBase.desert || biomegenbase == BiomeGenBase.desertHills;
        this.biome = biomegenbase;
        this.startPiece = this;
    }

    public WorldChunkManager getWorldChunkManager()
    {
        return this.worldChunkMngr;
    }
}
