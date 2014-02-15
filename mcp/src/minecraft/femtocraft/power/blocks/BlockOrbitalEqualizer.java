package femtocraft.power.blocks;

import femtocraft.Femtocraft;
import femtocraft.power.tiles.TileEntityMicroCable;
import femtocraft.power.tiles.TileEntityOrbitalEqualizer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockOrbitalEqualizer extends BlockPowerContainer {
	public BlockOrbitalEqualizer(int par1) {
		super(par1, Material.iron);
		setCreativeTab(Femtocraft.femtocraftTab);
		setUnlocalizedName("blockOrbitalEqualizer");
		setHardness(1.0f);
		setStepSound(Block.soundStoneFootstep);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityOrbitalEqualizer();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
