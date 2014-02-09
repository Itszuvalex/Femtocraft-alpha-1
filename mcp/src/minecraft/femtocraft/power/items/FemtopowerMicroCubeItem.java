package femtocraft.power.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.api.FemtopowerContainer;

public class FemtopowerMicroCubeItem extends ItemBlock {

	public FemtopowerMicroCubeItem(int par1) {
		super(par1);
	}

//	@Override
//	public boolean placeBlockAt(ItemStack stack, EntityPlayer player,
//			World world, int x, int y, int z, int side, float hitX, float hitY,
//			float hitZ, int metadata) {
//		// TODO Auto-generated method stub
//		return super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY,
//				hitZ, metadata);
//	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);

		FemtopowerContainer container = getContainer(par1ItemStack.getTagCompound());
		if(container == null) return;
		
		String value =  EnumChatFormatting.BLUE + "Power: " + EnumChatFormatting.RESET + container.getCurrentPower() + "/" + container.getMaxPower() +".";
		par3List.add(value);
	}
	
	private FemtopowerContainer getContainer(NBTTagCompound nbt)
	{
		if(nbt == null) return null;
		
		NBTTagCompound power = nbt.getCompoundTag("power");
		if(power == null) return null;
		
		return FemtopowerContainer.createFromNBT(power);
	}

}
