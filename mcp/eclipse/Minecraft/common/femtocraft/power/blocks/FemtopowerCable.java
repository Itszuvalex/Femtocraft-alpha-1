package femtocraft.power.blocks;

import java.util.List;

import net.minecraft.block.Block;
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
import femtocraft.power.TileEntity.FemtopowerCableTile;
import femtocraft.proxy.ClientProxyFemtocraft;

public class FemtopowerCable extends FemtopowerContainer {
	public Icon coreBorder;
	public Icon connector;
	public Icon coil;
	public Icon coilEdge;
	public Icon border;
	
	public FemtopowerCable(int par1, Material par2Material) {
		super(par1, par2Material);
		setCreativeTab(Femtocraft.femtocraftTab);
		setUnlocalizedName("FemtopowerCable");
		setHardness(1.0f);
		setStepSound(Block.soundStoneFootstep);
		setBlockBounds();
	}
	
	public void setBlockBounds() {
		this.minX = this.minY = this.minZ = 4.0D/16.0D;
		this.maxX = this.maxY = this.maxZ = 12.0D/16.0D;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new FemtopowerCableTile();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return ClientProxyFemtocraft.FemtopowerCableRenderID;
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
     //   this.field_94336_cN = par1IconRegister.func_94245_a("Femtocraft:FemtopowerCable");
	//	this.core = par1IconRegister.func_94245_a("Femtocraft:FemtopowerCableCore");
		this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "FemtopowerCableCoil");
		this.coreBorder = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "FemtopowerCableCoreBorder");
		this.connector = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "FemtopowerCableConnector");
		this.coil = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "FemtopowerCableCoil");
		this.coilEdge = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "FemtopowerCableCoilEdge");
		this.border = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "FemtopowerCableBorder");
    }
	
	@Override
	public void addCollisionBoxesToList(World par1World, int x, int y,
			int z, AxisAlignedBB par5AxisAlignedBB, List par6List,
			Entity par7Entity) {
		
		super.addCollisionBoxesToList(par1World, x, y, z, par5AxisAlignedBB,
				par6List, par7Entity);
		
		TileEntity tile = par1World.getBlockTileEntity(x, y, z);
		if(tile == null) return;
		if(!(tile instanceof FemtopowerCableTile)) return;
		FemtopowerCableTile cable = (FemtopowerCableTile)tile;
		
		for(int i = 0; i < 6; ++i)
		{
			if(!cable.connections[i]) continue;
			AxisAlignedBB bb = boundingBoxForDirection(ForgeDirection.getOrientation(i), x, y, z);
			if(par5AxisAlignedBB.intersectsWith(bb))
			{
				par6List.add(bb);
			}
		}
	}
	
	private AxisAlignedBB boundingBoxForDirection(ForgeDirection dir, int x, int y, int z)
	{
		double minX = 4.f/16.d;
		double minY = 4.f/16.d;
		double minZ = 4.f/16.d;
		double maxX = 12.f/16.d;
		double maxY = 12.f/16.d;
		double maxZ = 12.f/16.d;
		
		switch(dir)
		{
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
		
		return AxisAlignedBB.getAABBPool().getAABB((double)x + minX, (double)y + minY, (double)z + minZ, (double)x + maxX, (double)y + maxY, (double)z + maxZ);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int x, int y, int z) {
		AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB((double)x + 4.f/16.f, (double)y + 4.f/16.f, (double)z + 4.f/16.f, (double)x + 12.f/16.f, (double)y + 12.f/16.f, (double)z + 12.f/16.f);
		
//		
//		TileEntity tile = par1World.getBlockTileEntity(x, y, z);
//		if(tile == null) return box;
//		if(!(tile instanceof FemtopowerCableTile)) return box;
//		FemtopowerCableTile cable = (FemtopowerCableTile)tile;
//		
//		for(int i = 0; i < 6; ++i)
//		{
//			if(!cable.connections[i]) continue;
//			box = box.func_111270_a(boundingBoxForDirection(ForgeDirection.getOrientation(i), x, y, z));
//		}
//		
		return box;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World,
			int x, int y, int z) {
		AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB((double)x + 4.f/16.f, (double)y + 4.f/16.f, (double)z + 4.f/16.f, (double)x + 12.f/16.f, (double)y + 12.f/16.f, (double)z + 12.f/16.f);
		
		
		TileEntity tile = par1World.getBlockTileEntity(x, y, z);
		if(tile == null) return box;
		if(!(tile instanceof FemtopowerCableTile)) return box;
		FemtopowerCableTile cable = (FemtopowerCableTile)tile;
		
		for(int i = 0; i < 6; ++i)
		{
			if(!cable.connections[i]) continue;
			box = box.func_111270_a(boundingBoxForDirection(ForgeDirection.getOrientation(i), x, y, z));
		}
		
		return box;
	}

	//TODO:  Get this working.  This needs to be enabled in order to actually have mouse-over working
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1iBlockAccess,
			int x, int y, int z) {
		AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB((double)x + 4.d/16.d, (double)y + 4.d/16.d, (double)z + 4.d/16.d, (double)x + 12.d/16.d, (double)y + 12.d/16.d, (double)z + 12.d/16.d);
		
		
		TileEntity tile = par1iBlockAccess.getBlockTileEntity(x, y, z);
		if(tile == null)
		{
			setBlockBounds((float)box.minX, (float)box.minY, (float)box.minZ, (float)box.maxX, (float)box.maxY, (float)box.maxZ);
			return;
		}
		if(!(tile instanceof FemtopowerCableTile))
		{
			setBlockBounds((float)box.minX, (float)box.minY, (float)box.minZ, (float)box.maxX, (float)box.maxY, (float)box.maxZ);
			return;
		}
		
		double minX = 4.d/16.d;
		double minY = 4.d/16.d;
		double minZ = 4.d/16.d;
		double maxX = 12.d/16.d;
		double maxY = 12.d/16.d;
		double maxZ = 12.d/16.d;
		FemtopowerCableTile cable = (FemtopowerCableTile)tile;
		
		for(int i = 0; i < 6; ++i)
		{
			if(!cable.connections[i]) continue;
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			switch(dir)
			{
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
		
		setBlockBounds((float)minX, (float)minY, (float)minZ, (float)maxX, (float)maxY, (float)maxZ);
	}
	
}
