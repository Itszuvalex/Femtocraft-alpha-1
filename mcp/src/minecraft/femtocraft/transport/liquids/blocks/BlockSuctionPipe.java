package femtocraft.transport.liquids.blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
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

	@Override
	public void addCollisionBoxesToList(World par1World, int x, int y, int z,
			AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {

		super.addCollisionBoxesToList(par1World, x, y, z, par5AxisAlignedBB,
				par6List, par7Entity);

		TileEntity tile = par1World.getBlockTileEntity(x, y, z);
		if (tile == null || !(tile instanceof TileEntitySuctionPipe))
			return;
		TileEntitySuctionPipe pipe = (TileEntitySuctionPipe) tile;

		for (int i = 0; i < 6; ++i) {
			if (pipe.neighbors[i] == null)
				continue;
			AxisAlignedBB bb = boundingBoxForDirection(
					ForgeDirection.getOrientation(i), x, y, z);
			if (par5AxisAlignedBB.intersectsWith(bb)) {
				par6List.add(bb);
			}
		}
	}

	protected AxisAlignedBB boundingBoxForDirection(ForgeDirection dir, int x,
			int y, int z) {
		double minX = 6.f / 16.d;
		double minY = 6.f / 16.d;
		double minZ = 6.f / 16.d;
		double maxX = 10.f / 16.d;
		double maxY = 10.f / 16.d;
		double maxZ = 10.f / 16.d;

		switch (dir) {
		case UP:
			maxY = 1;
			break;
		case DOWN:
			minY = 0;
			break;
		case NORTH:
			minZ = 0;
			break;
		case SOUTH:
			maxZ = 1;
			break;
		case EAST:
			maxX = 1;
			break;
		case WEST:
			minX = 0;
			break;
		default:
			break;
		}

		return AxisAlignedBB.getAABBPool().getAABB((double) x + minX,
				(double) y + minY, (double) z + minZ, (double) x + maxX,
				(double) y + maxY, (double) z + maxZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int x, int y, int z) {
		return AxisAlignedBB.getAABBPool().getAABB((double) x + 6.f / 16.f,
				(double) y + 6.f / 16.f, (double) z + 6.f / 16.f,
				(double) x + 10.f / 16.f, (double) y + 10.f / 16.f,
				(double) z + 10.f / 16.f);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int x,
			int y, int z) {
		AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB(
				(double) x + 6.f / 16.f, (double) y + 6.f / 16.f,
				(double) z + 6.f / 16.f, (double) x + 10.f / 16.f,
				(double) y + 10.f / 16.f, (double) z + 10.f / 16.f);

		TileEntity tile = par1World.getBlockTileEntity(x, y, z);
		if (tile == null)
			return box;
		if (!(tile instanceof TileEntitySuctionPipe))
			return box;
		TileEntitySuctionPipe pipe = (TileEntitySuctionPipe) tile;

		for (int i = 0; i < 6; ++i) {
			if (pipe.neighbors[i] == null)
				continue;
			box = box.func_111270_a(boundingBoxForDirection(
					ForgeDirection.getOrientation(i), x, y, z));
		}

		return box;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1iBlockAccess,
			int x, int y, int z) {
		AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB(
				(double) x + 6.d / 16.d, (double) y + 6.d / 16.d,
				(double) z + 6.d / 16.d, (double) x + 10.d / 16.d,
				(double) y + 10.d / 16.d, (double) z + 10.d / 16.d);

		TileEntity tile = par1iBlockAccess.getBlockTileEntity(x, y, z);
		if (tile == null) {
			setBlockBounds((float) box.minX, (float) box.minY,
					(float) box.minZ, (float) box.maxX, (float) box.maxY,
					(float) box.maxZ);
			return;
		}
		if (!(tile instanceof TileEntitySuctionPipe)) {
			setBlockBounds((float) box.minX, (float) box.minY,
					(float) box.minZ, (float) box.maxX, (float) box.maxY,
					(float) box.maxZ);
			return;
		}

		double minX = 6.d / 16.d;
		double minY = 6.d / 16.d;
		double minZ = 6.d / 16.d;
		double maxX = 10.d / 16.d;
		double maxY = 10.d / 16.d;
		double maxZ = 10.d / 16.d;
		TileEntitySuctionPipe pipe = (TileEntitySuctionPipe) tile;

		for (int i = 0; i < 6; ++i) {
			if (pipe.neighbors[i] == null)
				continue;
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			switch (dir) {
			case UP:
				maxY = 1;
				break;
			case DOWN:
				minY = 0;
				break;
			case NORTH:
				minZ = 0;
				break;
			case SOUTH:
				maxZ = 1;
				break;
			case EAST:
				maxX = 1;
				break;
			case WEST:
				minX = 0;
				break;
			default:
				break;
			}
		}

		setBlockBounds((float) minX, (float) minY, (float) minZ, (float) maxX,
				(float) maxY, (float) maxZ);
	}

}
