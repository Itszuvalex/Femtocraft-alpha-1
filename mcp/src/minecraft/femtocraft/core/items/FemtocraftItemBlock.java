package femtocraft.core.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.FemtocraftUtils;
import femtocraft.api.FemtopowerContainer;
import femtocraft.power.TileEntity.FemtopowerMicroCubeTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class FemtocraftItemBlock extends ItemBlock {

	public FemtocraftItemBlock(int par1) {
		super(par1);
		setCreativeTab(Femtocraft.femtocraftTab);
	}

	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);

		NBTTagCompound nbt = par1ItemStack.getTagCompound();
		if(nbt == null)
		{
			nbt = par1ItemStack.stackTagCompound = new NBTTagCompound();
		}
		
		String owner = nbt.getString("owner");

		String ownerLabelString = EnumChatFormatting.GRAY + "Owner:" + EnumChatFormatting.RESET;
		String ownerString;
		if(owner.isEmpty())
		{
			ownerString = EnumChatFormatting.ITALIC + "unassigned" + EnumChatFormatting.RESET;
		}
		else
		{
			ownerString = owner;
		}
		
		par3List.add(new String().format("%s %s", ownerLabelString, ownerString));
	}


	@Override
	@SideOnly(Side.CLIENT)
	public boolean canPlaceItemBlockOnSide(World par1World, int par2, int par3,
			int par4, int par5, EntityPlayer par6EntityPlayer,
			ItemStack par7ItemStack) {
		if(!canPlayerPlace(par6EntityPlayer)) return false;
		return super.canPlaceItemBlockOnSide(par1World, par2, par3, par4, par5,
				par6EntityPlayer, par7ItemStack);
	}


	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ, int metadata) {
		if(!canPlayerPlace(player)) return false;
		return super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY,
				hitZ, metadata);
	}
	
	private boolean canPlayerPlace(EntityPlayer player)
	{
		if(player == null) return false;
		
		return true;
	}
}
