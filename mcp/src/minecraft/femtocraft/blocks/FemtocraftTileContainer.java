package femtocraft.blocks;

import java.util.Random;

import femtocraft.Femtocraft;
import femtocraft.api.FemtopowerContainer;
import femtocraft.core.tiles.FemtocraftTile;
import femtocraft.power.TileEntity.FemtopowerMicroCubeTile;
import femtocraft.power.TileEntity.FemtopowerTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class FemtocraftTileContainer extends BlockContainer {

	public FemtocraftTileContainer(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new FemtocraftTile();
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
		TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
		if(te instanceof FemtocraftTile)
		{
			FemtocraftTile tile = (FemtocraftTile) te;
			
			if(hasGui() && tile.isUseableByPlayer(par5EntityPlayer))
			{
				par5EntityPlayer.openGui(getMod(), getGuiID(), par1World, par2, par3, par4);
			}
		}
		return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer,
				par6, par7, par8, par9);
	}
	
	public boolean hasGui()
	{
		return false;
	}
	
	public int getGuiID()
	{
		return -1;
	}
	
	public Object getMod()
	{
		return Femtocraft.instance;
	}
	
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
			EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		
		if(!par1World.isRemote)
		{
			TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
			if(te instanceof FemtocraftTile)
			{
				FemtocraftTile tile = (FemtocraftTile) te;
				if(par5EntityLivingBase == null) return;
				if(par5EntityLivingBase instanceof EntityPlayerMP)
				{
					((FemtocraftTile) te).loadInfoFromItemNBT(par6ItemStack.stackTagCompound);
					if(((FemtocraftTile) te).getOwner().isEmpty())
					{
						((FemtocraftTile) te).setOwner(((EntityPlayerMP) par5EntityLivingBase).username);
					}
				}
			}
		}
		super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase,
				par6ItemStack);
	}
	
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4,
			int par5, int par6) {
		TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
		if(te != null && te instanceof FemtopowerTile)
		{
			FemtopowerTile tile = (FemtopowerTile) te;
			
			ItemStack stack = new ItemStack(Block.blocksList[par5]);
			if(!stack.hasTagCompound())
			{
				stack.stackTagCompound = new NBTTagCompound();
			}
			
			tile.saveInfoToItemNBT(stack.stackTagCompound);
			
			par1World.spawnEntityInWorld(new EntityItem(par1World, par2 + .5d, par3 + .5d, par4 + .5d, stack));
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x,
			int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(!(te instanceof FemtocraftTile)) return false;
		if(!canPlayerRemove(player, (FemtocraftTile) te)) return false;
		return super.removeBlockByPlayer(world, player, x, y, z);
	}

	private boolean canPlayerRemove(EntityPlayer player, FemtocraftTile tile)
	{
		if(player == null) return false;
		return tile.getOwner().equals(player.username);
	}
	
	@Override
	public boolean canEntityDestroy(World world, int x, int y, int z,
			Entity entity) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(!(te instanceof FemtocraftTile)) return false;
		if(!(entity instanceof EntityPlayer)) return false;
		if(!canPlayerRemove((EntityPlayer) entity,  (FemtocraftTile)te)) return false;
		return super.canEntityDestroy(world, x, y, z, entity);
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}
}
