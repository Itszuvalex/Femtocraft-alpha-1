package femtocraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class unidentifiedAlloy extends Block {
	public unidentifiedAlloy(int id, int texture) {
		super(id, Material.iron);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public String getTextureFile() {
		return "/femtocraft/femtocraft_terrain.png";
	}
	
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		return false;	
	}
}