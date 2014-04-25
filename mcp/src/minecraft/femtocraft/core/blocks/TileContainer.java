package femtocraft.core.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import femtocraft.core.items.CoreItemBlock;
import femtocraft.core.tiles.TileEntityBase;

public class TileContainer extends BlockContainer {
	protected static boolean shouldDrop = true;

	public TileContainer(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBase();
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
		TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
		if (te instanceof TileEntityBase) {
			TileEntityBase tile = (TileEntityBase) te;

			return tile.onSideActivate(par5EntityPlayer, par6);
		}
		return super.onBlockActivated(par1World, par2, par3, par4,
				par5EntityPlayer, par6, par7, par8, par9);
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
			EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {

		if (!par1World.isRemote) {
			TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
			if (te instanceof TileEntityBase) {
				if (par5EntityLivingBase == null)
					return;
				if (par5EntityLivingBase instanceof EntityPlayerMP) {
					Item item = par6ItemStack.getItem();
					if ((item instanceof CoreItemBlock)
							&& (((CoreItemBlock) item).hasItemNBT())) {
						((TileEntityBase) te)
								.loadInfoFromItemNBT(par6ItemStack.stackTagCompound);
					}
					if (((TileEntityBase) te).getOwner() == null
							|| ((TileEntityBase) te).getOwner().isEmpty()) {
						((TileEntityBase) te)
								.setOwner(((EntityPlayerMP) par5EntityLivingBase).username);
					}
				}
			}
		}
		super.onBlockPlacedBy(par1World, par2, par3, par4,
				par5EntityLivingBase, par6ItemStack);
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4,
			int par5, int par6) {
		if (shouldDrop) {
			TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
			if (te instanceof TileEntityBase) {
				TileEntityBase tile = (TileEntityBase) te;

				ItemStack stack = new ItemStack(Block.blocksList[par5]);
				Item item = stack.getItem();
				if ((item instanceof CoreItemBlock)
						&& (((CoreItemBlock) item).hasItemNBT())) {
					stack.stackTagCompound = new NBTTagCompound();
					tile.saveInfoToItemNBT(stack.stackTagCompound);
				}

				EntityItem spawn = new EntityItem(par1World, par2 + .5d,
						par3 + .5d, par4 + .5d, stack);
				spawn.delayBeforeCanPickup = 10;
				par1World.spawnEntityInWorld(spawn);
			}
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x,
			int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (!(te instanceof TileEntityBase))
			return false;
		if (!canPlayerRemove(player, (TileEntityBase) te)) {
			player.addChatMessage("You do not own this block.");
			return false;
		}
		return super.removeBlockByPlayer(world, player, x, y, z);
	}

	private boolean canPlayerRemove(EntityPlayer player, TileEntityBase tile) {
		return player != null
				&& (tile.getOwner() == null
						|| tile.getOwner().equals(player.username)
						|| MinecraftServer.getServer()
								.getConfigurationManager()
								.isPlayerOpped(player.username) || player.capabilities.isCreativeMode);
	}

	@Override
	public boolean canEntityDestroy(World world, int x, int y, int z,
			Entity entity) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		return te instanceof TileEntityBase && entity instanceof EntityPlayer
				&& canPlayerRemove((EntityPlayer) entity, (TileEntityBase) te)
				&& super.canEntityDestroy(world, x, y, z, entity);
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}
}
