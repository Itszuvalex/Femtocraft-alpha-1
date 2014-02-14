package femtocraft.transport.liquids.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import femtocraft.core.blocks.TileContainer;

public class BlockSuctionPipe extends TileContainer {

	public BlockSuctionPipe(int par1) {
		super(par1, Material.iron);
		setUnlocalizedName("BlockSuctionPipe");
	}

	@Override
	public boolean renderAsNormalBlock() {
		// TODO Auto-generated method stub
		return super.renderAsNormalBlock();
	}

	@Override
	public int getRenderType() {
		// TODO Auto-generated method stub
		return super.getRenderType();
	}

	@Override
	public boolean isOpaqueCube() {
		// TODO Auto-generated method stub
		return super.isOpaqueCube();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		// TODO Auto-generated method stub
		super.registerIcons(par1IconRegister);
	}

}
