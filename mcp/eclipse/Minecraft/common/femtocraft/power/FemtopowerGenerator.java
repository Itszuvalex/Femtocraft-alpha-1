package femtocraft.power;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import femtocraft.power.TileEntity.FemtopowerProducer;

public class FemtopowerGenerator extends BlockContainer {
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
		
		FemtopowerProducer container = (FemtopowerProducer)par1World.getBlockTileEntity(par2, par3, par4);
		par5EntityPlayer.addChatMessage(String.valueOf(container.getCurrentPower()));
		
		return true;
	}

	public FemtopowerGenerator(int par1, Material par2Material) {
		super(par1, par2Material);
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new FemtopowerProducer(1000, 10);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

}
