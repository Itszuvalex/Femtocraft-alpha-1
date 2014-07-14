package com.itszuvalex.femtocraft.power.blocks;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.core.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.power.multiblock.MultiBlockPhlegethonTunnel;
import com.itszuvalex.femtocraft.power.tiles.TileEntityPhlegethonTunnelFrame;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/12/14.
 */
public class BlockPhlegethonTunnelFrame extends Block {
    public BlockPhlegethonTunnelFrame(int id) {
        super(id, Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab);
        setUnlocalizedName("BlockPhlegethonTunnelFrame");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                + ":" + "BlockPhlegethonTunnelFrame");
    }

    /*
    * (non-Javadoc)
    *
    * @see
    * net.minecraft.block.Block#onPostBlockPlaced(net.minecraft.world.World,
    * int, int, int, int)
    */
    @Override
    public void onPostBlockPlaced(World par1World, int par2, int par3,
                                  int par4, int par5) {
        MultiBlockPhlegethonTunnel.instance.formMultiBlockWithBlock(par1World, par2,
                par3, par4);
        super.onPostBlockPlaced(par1World, par2, par3, par4, par5);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityPhlegethonTunnelFrame();
    }

    /*
         * (non-Javadoc)
         *
         * @see
         * TileContainer#breakBlock(net.minecraft.world.World
         * , int, int, int, int, int)
         */
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4,
                           int par5, int par6) {
        TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
        if (te instanceof TileEntityPhlegethonTunnelFrame) {
            MultiBlockInfo info = ((TileEntityPhlegethonTunnelFrame) te).getInfo();
            MultiBlockPhlegethonTunnel.instance.breakMultiBlock(par1World, info.x(),
                    info.y(), info.z());

        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

}