package femtocraft.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockUnidentifiedAlloy extends Block {
	public BlockUnidentifiedAlloy(int id) {
		super(id, Material.iron);
		setCreativeTab(Femtocraft.femtocraftTab);
		setTextureName(Femtocraft.ID.toLowerCase() + ":" + "BlockUnidentifiedAlloy");
		setUnlocalizedName("BlockUnidentifiedAlloy");
		setBlockUnbreakable();
		setStepSound(Block.soundMetalFootstep);
		setResistance(20f);
	}

	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID
				.toLowerCase() + ":" + "BlockUnidentifiedAlloy");
	}
}