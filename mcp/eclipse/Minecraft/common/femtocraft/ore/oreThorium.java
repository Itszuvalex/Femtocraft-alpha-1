package femtocraft.ore;

import net.minecraft.block.BlockOre;
import net.minecraft.creativetab.CreativeTabs;

public class oreThorium extends BlockOre{

	public oreThorium(int id, int texture) {
		super(id);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public String getTextureFile() {
		return "/femtocraft/femtocraft_terrain.png";
	}
}
