package femtocraft.research.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.core.blocks.TileContainer;
import femtocraft.research.tiles.TileEntityResearchComputer;

public class BlockResearchComputer extends TileContainer {
	public Icon top;

	public BlockResearchComputer(int par1) {
		super(par1, Material.iron);
		setUnlocalizedName("researchComputer");
		setCreativeTab(Femtocraft.femtocraftTab);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.block.Block#getIcon(int, int)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		switch (ForgeDirection.getOrientation(par1)) {
		case UP:
			return top;
		default:
			return blockIcon;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.minecraft.block.Block#registerIcons(net.minecraft.client.renderer
	 * .texture.IconRegister)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
				+ ":" + "BasicMachineBlockSide");
		top = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":"
				+ "BlockResearchComputer_top");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.minecraft.block.Block#createTileEntity(net.minecraft.world.World,
	 * int)
	 */
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityResearchComputer();
	}

}
