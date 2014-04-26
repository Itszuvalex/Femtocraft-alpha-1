package femtocraft.power.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.api.IChargingBase;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.power.tiles.TileEntityPowerMicroChargingBase;
import femtocraft.proxy.ProxyClient;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockMicroChargingBase extends BlockPowerContainer implements
                                                                IChargingBase {
    public Icon side;
    public Icon top;
    public Icon bottom;
    public Icon side_inset;
    public Icon coil_inset;
    public Icon coil_column_inset;
    public Icon top_inset;
    public Icon coil_top_inset;
    public Icon top_pillar_top;
    public Icon top_pillar_side;

    public BlockMicroChargingBase(int par1) {
        super(par1, Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab);
        setUnlocalizedName("BlockBaseMicroCharging");
        setHardness(2.0f);
        setStepSound(Block.soundMetalFootstep);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityPowerMicroChargingBase();
    }

    @Override
    public int getRenderType() {
        return ProxyClient.FemtocraftChargingBaseRenderID;
    }

    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        return canBlockStay(par1World, par2, par3, par4);
    }

    @Override
    public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
        Block block = Block.blocksList[par1World.getBlockId(par2 - 1, par3,
                                                            par4)];
        if (block instanceof IChargingBase) {
            return false;
        }

        block = Block.blocksList[par1World.getBlockId(par2 + 1, par3, par4)];
        if (block instanceof IChargingBase) {
            return false;
        }

        block = Block.blocksList[par1World.getBlockId(par2, par3, par4 - 1)];
        if (block instanceof IChargingBase) {
            return false;
        }

        block = Block.blocksList[par1World.getBlockId(par2, par3, par4 + 1)];
        return !(block instanceof IChargingBase);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        side = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":"
                                                     + "ChargingBase_side");
        top = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":"
                                                    + "ChargingBase_top");
        // bottom = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +
        // ":" + "ChargingBase_bottom");
        bottom = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                                                       + ":" + "MicroMachineBlock_side");
        side_inset = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                                                           + ":" + "ChargingBase_side_inset");
        coil_inset = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                                                           + ":" + "ChargingBase_coil_inset");
        coil_column_inset = par1IconRegister.registerIcon(Femtocraft.ID
                                                                  .toLowerCase() + ":" + "ChargingBase_coil_column_inset");
        top_inset = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                                                          + ":" + "ChargingBase_top_inset");
        coil_top_inset = par1IconRegister.registerIcon(Femtocraft.ID
                                                               .toLowerCase() + ":" + "ChargingBase_coil_top_inset");
        top_pillar_top = par1IconRegister.registerIcon(Femtocraft.ID
                                                               .toLowerCase() + ":" + "ChargingBase_top_pillar_top");
        top_pillar_side = par1IconRegister.registerIcon(Femtocraft.ID
                                                                .toLowerCase() + ":" + "ChargingBase_top_pillar_side");
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int maxCoilsSupported(World world, int x, int y, int z) {
        return 10;
    }

    @Override
    public EnumTechLevel maxTechSupported(World world, int x, int y, int z) {
        return EnumTechLevel.MICRO;
    }

}
