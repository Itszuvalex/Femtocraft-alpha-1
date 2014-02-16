package femtocraft.power.blocks;

import femtocraft.Femtocraft;
import femtocraft.power.tiles.TileEntityPowerConsumerTest;
import femtocraft.power.tiles.TileEntityPowerTest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTest extends BlockContainer {

	//Treat it like a normal block here. The Block Bounds are a good idea - the first three are X Y and Z of the botton-left corner,
	//And the second three are the top-right corner.
	public BlockTest(int id) {
		super(id, Material.rock);
		this.setCreativeTab(Femtocraft.femtocraftTab);
	}

	//Make sure you set this as your TileEntity class relevant for the block!
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityPowerTest();
	}

	//You don't want the normal render type, or it wont render properly.
	@Override
	public int getRenderType() {
		return -1;
	}

	//It's not an opaque cube, so you need this.
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	//It's not a normal block, so you need this too.
	public boolean renderAsNormalBlock() {
		return false;
	}
}
