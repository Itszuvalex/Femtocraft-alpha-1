package femtocraft.power;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import femtocraft.power.TileEntity.FemtopowerProducer;

public class FemtopowerGenerator extends FemtopowerContainer {
	
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
