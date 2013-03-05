package femtocraft.ore;

import java.util.Random;

import femtocraft.Femtocraft;

import net.minecraft.block.BlockOre;
import net.minecraft.creativetab.CreativeTabs;

public class oreFarenite extends BlockOre{

	public oreFarenite(int id, int texture) {
		super(id, texture);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public String getTextureFile() {
		return "/femtocraft/femtocraft_terrain.png";
	}
	
	public int idDropped(int par1, Random random, int par2) {
		return Femtocraft.ingotFarenite.itemID;
	}
	
	public int quantityDropped(Random random) {
		return 4;
	}
}
