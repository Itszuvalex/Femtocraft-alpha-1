package femtocraft.research.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
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
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.managers.research.ResearchPlayer;

public class ItemMicroTechnology extends ItemTechnologyCarrier {

	public ItemMicroTechnology(int par1) {
		super(par1);
		setCreativeTab(Femtocraft.femtocraftTab);
		setUnlocalizedName("itemMicroTechnology");
	}

	@Override
	public EnumTechLevel getTechnologyLevel(ItemStack stack) {
		return EnumTechLevel.MICRO;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		itemIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
				+ ":" + "ItemMicroTechnology");
	}
}
