package femtocraft.power.blocks;

import femtocraft.Femtocraft;
import femtocraft.power.TileEntity.FemtopowerProducerTest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class FemtopowerGenerator extends FemtopowerContainer {
	
	public FemtopowerGenerator(int par1, Material par2Material) {
		super(par1, par2Material);
		setCreativeTab(Femtocraft.femtocraftTab);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new FemtopowerProducerTest();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

}
