package femtocraft.transport.liquids.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.core.blocks.TileContainer;
import femtocraft.proxy.ProxyClient;
import femtocraft.transport.liquids.tiles.TileEntitySuctionPipe;

public class BlockSuctionPipe extends TileContainer {
	public Icon center;
	public Icon connector;
	public Icon connector_tank;

	public BlockSuctionPipe(int par1) {
		super(par1, Material.iron);
		setUnlocalizedName("BlockSuctionPipe");
		setCreativeTab(Femtocraft.femtocraftTab);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return ProxyClient.FemtocraftSuctionPipeRenderID;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		center = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
				+ ":" + "BlockSuctionPipe_center");
		connector = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
				+ ":" + "BlockSuctionPipe_connector");
		connector_tank = par1IconRegister.registerIcon(Femtocraft.ID
				.toLowerCase() + ":" + "BlockSuctionPipe_connector_tank");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitySuctionPipe();
	}

}
