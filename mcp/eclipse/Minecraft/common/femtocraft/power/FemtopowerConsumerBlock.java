package femtocraft.power;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import femtocraft.Femtocraft;
import femtocraft.power.TileEntity.FemtopowerConsumer;

public class FemtopowerConsumerBlock extends FemtopowerContainer {

	public FemtopowerConsumerBlock(int par1, Material par2Material) {
		super(par1, par2Material);
		setCreativeTab(Femtocraft.femtocraftTab);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new FemtopowerConsumer();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

}
