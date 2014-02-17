package femtocraft.power.blocks;

import net.minecraft.block.material.Material;
import femtocraft.Femtocraft;
import femtocraft.core.blocks.TileContainer;

public class BlockNanoCubePort extends TileContainer {

	public BlockNanoCubePort(int par1) {
		super(par1, Material.iron);
		setCreativeTab(Femtocraft.femtocraftTab);
		setUnlocalizedName("BlockNanoCubePort");
	}

}
