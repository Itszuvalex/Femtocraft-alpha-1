package femtocraft.ore;

import net.minecraft.block.BlockOre;
import net.minecraft.creativetab.CreativeTabs;

public class oreTitanium extends BlockOre{

	public oreTitanium(int id, int texture) {
		super(id);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public String getTextureFile() {
		return "/femtocraft/femtocraft_terrain.png";
	}
}
