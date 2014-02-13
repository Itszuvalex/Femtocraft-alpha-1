package femtocraft.power.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.api.PowerContainer;
import femtocraft.core.items.CoreItemBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public abstract class ItemBlockPower extends CoreItemBlock {

	public ItemBlockPower(int par1) {
		super(par1);
	}

	// @Override
	// public boolean placeBlockAt(ItemStack stack, EntityPlayer player,
	// World world, int x, int y, int z, int side, float hitX, float hitY,
	// float hitZ, int metadata) {
	// // TODO Auto-generated method stub
	// return super.placeBlockAt(stack, player, world, x, y, z, side, hitX,
	// hitY,
	// hitZ, metadata);
	// }

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);

		NBTTagCompound nbt = par1ItemStack.getTagCompound();
		if (nbt == null) {
			nbt = par1ItemStack.stackTagCompound = new NBTTagCompound();
		}

		PowerContainer container;
		boolean init = nbt.hasKey("power");

		NBTTagCompound power = nbt.getCompoundTag("power");

		if (!init) {
			container = getDefaultContainer();
			container.saveToNBT(power);
			nbt.setTag("power", power);
		} else {
			container = PowerContainer.createFromNBT(power);
		}

		container.addInformationToTooltip(par3List);
	}

	public abstract PowerContainer getDefaultContainer();

	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		super.onCreated(par1ItemStack, par2World, par3EntityPlayer);

		NBTTagCompound nbt = par1ItemStack.getTagCompound();
		if (nbt == null) {
			nbt = par1ItemStack.stackTagCompound = new NBTTagCompound();
		}
		NBTTagCompound power = new NBTTagCompound();
		getDefaultContainer().saveToNBT(power);
		nbt.setTag("power", power);
	}
}
