package femtocraft.research.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.api.ITechnologyCarrier;
import femtocraft.managers.research.ResearchPlayer;
import femtocraft.managers.research.ResearchTechnology;

public abstract class ItemTechnologyCarrier extends Item implements
		ITechnologyCarrier {
	private final static String researchKey = "techName";

	public ItemTechnologyCarrier(int par1) {
		super(par1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.minecraft.item.Item#onItemRightClick(net.minecraft.item.ItemStack,
	 * net.minecraft.world.World, net.minecraft.entity.player.EntityPlayer)
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		ResearchPlayer pr = Femtocraft.researchManager
				.getPlayerResearch(par3EntityPlayer.username);
		if (pr != null) {
			String tech = getTechnology(par1ItemStack);
			if (!(tech == null || tech.isEmpty())) {
				if (!par2World.isRemote) {
					if (pr.researchTechnology(tech, false)) {
						ResearchTechnology rt = Femtocraft.researchManager
								.getTechnology(tech);
						if (rt != null && rt.discoverItem != null) {
							return rt.discoverItem.copy();
						}

						par1ItemStack.stackSize = 0;
					}
				}
			}
		}
		return super.onItemRightClick(par1ItemStack, par2World,
				par3EntityPlayer);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		NBTTagCompound compound = par1ItemStack.stackTagCompound;
		if (compound != null) {
			par3List.add(getTechnologyLevel(par1ItemStack).getTooltipEnum()
					+ getTechnology(par1ItemStack) + EnumChatFormatting.RESET);
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

		compound.setString(researchKey, name);
	}

	@Override
	public String getTechnology(ItemStack itemStack) {
		NBTTagCompound compound = itemStack.stackTagCompound;
		if (compound == null) {
			return null;
		}

		return compound.getString(researchKey);
	}

}
