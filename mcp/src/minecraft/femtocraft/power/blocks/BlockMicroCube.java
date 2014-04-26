package femtocraft.power.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.power.tiles.TileEntityPowerMicroCube;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMicroCube extends BlockPowerContainer {
    public Icon outputSide;
    public Icon inputSide;

    public BlockMicroCube(int par1) {
        super(par1, Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab);
        setUnlocalizedName("microCube");
        setHardness(3.f);
        setStepSound(Block.soundMetalFootstep);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityPowerMicroCube();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess access, int x, int y, int z,
                                int side) {
        TileEntity te = access.getBlockTileEntity(x, y, z);

        if (te == null) {
            return this.blockIcon;
        }
        if (!(te instanceof TileEntityPowerMicroCube)) {
            return this.blockIcon;
        }
        TileEntityPowerMicroCube cube = (TileEntityPowerMicroCube) te;
        return cube.outputs[side] ? outputSide : inputSide;
    }

    // @Override
    // public boolean isOpaqueCube() {
    // return false;
    // }

    // @Override
    // public boolean renderAsNormalBlock() {
    // return false;
    // }
    //
    // @Override
    // public int getRenderType() {
    // return ProxyClient.FemtopowerMicroCubeRenderID;
    // }
    //

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = inputSide = par1IconRegister
                .registerIcon(Femtocraft.ID.toLowerCase() + ":"
                                      + "MicroCube_input");
        outputSide = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                                                           + ":" + "MicroCube_output");
        // side = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +
        // ":" + "MicroCube_side");
    }
}
