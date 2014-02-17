package femtocraft.power.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import femtocraft.Femtocraft;
import femtocraft.core.blocks.TileContainer;

public class BlockNanoCubeFrame extends TileContainer {

	public BlockNanoCubeFrame(int par1) {
		super(par1, Material.iron);
		setCreativeTab(Femtocraft.femtocraftTab);
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
			EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		// TODO Auto-generated method stub
		super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase,
				par6ItemStack);
	}

	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		super.onBlockAdded(par1World, par2, par3, par4);
	}

	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4,
			int par5, float par6, float par7, float par8, int par9) {
		// TODO Auto-generated method stub
		return super.onBlockPlaced(par1World, par2, par3, par4, par5, par6, par7, par8,
				par9);
	}
}
