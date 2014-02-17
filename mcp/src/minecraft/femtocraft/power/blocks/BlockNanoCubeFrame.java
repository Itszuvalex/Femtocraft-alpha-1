package femtocraft.power.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import femtocraft.Femtocraft;
import femtocraft.core.blocks.TileContainer;
import femtocraft.core.multiblock.MultiBlockInfo;
import femtocraft.power.multiblock.MultiBlockNanoCube;
import femtocraft.power.tiles.TileEntityNanoCubeFrame;

public class BlockNanoCubeFrame extends TileContainer {

	public BlockNanoCubeFrame(int par1) {
		super(par1, Material.iron);
		setCreativeTab(Femtocraft.femtocraftTab);
		setUnlocalizedName("BlockNanoCubeFrame");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityNanoCubeFrame();
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
			EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		MultiBlockNanoCube.instance.formMultiBlockWithBlock(par1World, par2,
				par3, par4);
		super.onBlockPlacedBy(par1World, par2, par3, par4,
				par5EntityLivingBase, par6ItemStack);
	}

	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		MultiBlockNanoCube.instance.formMultiBlockWithBlock(par1World, par2,
				par3, par4);
		super.onBlockAdded(par1World, par2, par3, par4);
	}

	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4,
			int par5, float par6, float par7, float par8, int par9) {
		MultiBlockNanoCube.instance.formMultiBlockWithBlock(par1World, par2,
				par3, par4);
		return super.onBlockPlaced(par1World, par2, par3, par4, par5, par6,
				par7, par8, par9);
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4,
			int par5, int par6) {
		TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
		if (te != null && te instanceof TileEntityNanoCubeFrame) {
			MultiBlockInfo info = ((TileEntityNanoCubeFrame) te).getInfo();
			MultiBlockNanoCube.instance.breakMultiBlock(par1World, info.x(),
					info.y(), info.z());
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
}
