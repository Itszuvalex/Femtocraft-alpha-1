package femtocraft.research.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.api.ITechnologyCarrier;
import femtocraft.managers.research.EnumTechLevel;

public class ItemFemtoTechnology extends Item implements ITechnologyCarrier {

	public ItemFemtoTechnology(int par1) {
		super(par1);
		setCreativeTab(Femtocraft.femtocraftTab);
		setUnlocalizedName("itemFemtoTechnology");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		NBTTagCompound compound = par1ItemStack.stackTagCompound;
		if (compound != null) {
			par3List.add(EnumTechLevel.FEMTO.getColor()
					+ compound.getString("techName") + EnumChatFormatting.RESET);
		} else {
			par3List.add("This is only valid if made via");
			par3List.add("Femtocraft Research Console.");
		}
	}

	@Override
	public void setTechnology(ItemStack itemStack, String name) {
		NBTTagCompound compound = itemStack.stackTagCompound;
		if (compound == null) {
			compound = itemStack.stackTagCompound = new NBTTagCompound();
		}

		compound.setString("techName", name);
	}

	@Override
	public String getTechnology(ItemStack itemStack) {
		NBTTagCompound compound = itemStack.stackTagCompound;
		if (compound == null) {
			return null;
		}

		return compound.getString("techName");
	}

	@Override
	public EnumTechLevel getTechnologyLevel(ItemStack stack) {
		return EnumTechLevel.FEMTO;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		itemIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
				+ ":" + "ItemFemtoTechnology");
	}
}
