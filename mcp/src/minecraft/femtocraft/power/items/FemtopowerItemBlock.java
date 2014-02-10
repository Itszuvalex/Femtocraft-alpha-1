package femtocraft.power.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.api.FemtopowerContainer;
import femtocraft.core.items.FemtocraftItemBlock;
import femtocraft.power.TileEntity.FemtopowerMicroCubeTile;

public abstract class FemtopowerItemBlock extends FemtocraftItemBlock {

	public FemtopowerItemBlock(int par1) {
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

		NBTTagCompound nbt = par1ItemStack.getTagCompound();
		if(nbt == null)
		{
			nbt = par1ItemStack.stackTagCompound = new NBTTagCompound();
		}
		
		FemtopowerContainer container;
		boolean init = nbt.hasKey("power");

		NBTTagCompound power = nbt.getCompoundTag("power");

		
		if(!init)
		{
			container = getDefaultContainer();
			container.saveToNBT(power);
			nbt.setTag("power", power);
		}
		else
		{
			container =  FemtopowerContainer.createFromNBT(power);
		}
		
		container.addInformationToTooltip(par3List);
	}
	
	public abstract FemtopowerContainer getDefaultContainer();
}
