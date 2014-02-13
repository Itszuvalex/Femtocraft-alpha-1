package femtocraft.core.ore;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;

public class oreMalenite extends BlockOre {

	public oreMalenite(int id) {
		super(id);
		setCreativeTab(Femtocraft.femtocraftTab);
		setTextureName(Femtocraft.ID.toLowerCase() + ":" + "oreMalenite");
		setUnlocalizedName("oreMalenite");
		setHardness(3.0f);
		setStepSound(Block.soundStoneFootstep);
		setResistance(1f);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID
				.toLowerCase() + ":" + "oreMalenite");
	}

	public int idDropped(int par1, Random random, int par2) {
		return Femtocraft.ingotMalenite.itemID;
	}

	public int quantityDropped(Random random) {
		// 2-4
		return random.nextInt(3) + 2;
	}
}
