package femtocraft.power.blocks;

import net.minecraft.block.material.Material;
import femtocraft.Femtocraft;
import femtocraft.core.blocks.TileContainer;

public class BlackNanoCubePort extends TileContainer {

	public BlackNanoCubePort(int par1) {
		super(par1, Material.iron);
		setCreativeTab(Femtocraft.femtocraftTab);
	}

}
