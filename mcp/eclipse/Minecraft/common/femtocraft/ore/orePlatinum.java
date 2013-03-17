package femtocraft.ore;

import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class orePlatinum extends BlockOre{

	public orePlatinum(int id, int texture) {
		super(id);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public String getTextureFile() {
		return "/femtocraft/femtocraft_terrain.png";
	}
}