package com.itszuvalex.femtocraft.power.blocks;

import com.itszuvalex.femtocraft.Femtocraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/6/14.
 */
public class BlockNanoFissionReactorCore extends Block {
    public BlockNanoFissionReactorCore(int id) {
        super(id, Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab);
        setUnlocalizedName("BlockBlockNanoFissionReactorCore");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                + ":" + "BlockBlockNanoFissionReactorCore");
    }
}