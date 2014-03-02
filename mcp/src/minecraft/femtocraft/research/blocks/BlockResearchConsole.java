package femtocraft.research.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import femtocraft.Femtocraft;
import femtocraft.core.blocks.TileContainer;

public class BlockResearchConsole extends TileContainer {

	public BlockResearchConsole(int par1, Material par2Material) {
		super(par1, Material.iron);
		setCreativeTab(Femtocraft.femtocraftTab);
		setUnlocalizedName("BlockResearchConsole");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return super.getIcon(par1, par2);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return super.createNewTileEntity(world);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
	}

}
