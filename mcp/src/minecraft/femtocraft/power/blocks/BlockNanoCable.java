package femtocraft.power.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.power.tiles.TileEntityNanoCable;
import femtocraft.proxy.ProxyClient;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockNanoCable extends BlockMicroCable {
    public BlockNanoCable(int par1, Material par2Material) {
        super(par1, par2Material);
        setCreativeTab(Femtocraft.femtocraftTab);
        setUnlocalizedName("blockNanoCable");
        setHardness(1.0f);
        setStepSound(Block.soundStoneFootstep);
        setBlockBounds();
    }

    public void setBlockBounds() {
        this.minX = this.minY = this.minZ = 4.0D / 16.0D;
        this.maxX = this.maxY = this.maxZ = 12.0D / 16.0D;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityNanoCable();
    }

    @Override
    public int getRenderType() {
        return ProxyClient.nanoCableRenderID;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "nanoCableCoil");
        this.coreBorder = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" +
                                                                "nanoCableCoreBorder");
        this.connector = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "nanoCableConnector");
        this.coil = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "nanoCableCoil");
        this.coilEdge = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "nanoCableCoilEdge");
        this.border = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "nanoCableBorder");
    }
}
