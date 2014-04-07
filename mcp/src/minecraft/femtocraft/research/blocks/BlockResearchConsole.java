package femtocraft.research.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.core.blocks.TileContainer;
import femtocraft.research.tiles.TileEntityResearchConsole;

public class BlockResearchConsole extends TileContainer {
	public Icon side;
	public Icon top;
	public Icon bot;

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4,
			int par5, int par6) {

		TileEntityResearchConsole console = (TileEntityResearchConsole) par1World
				.getBlockTileEntity(par2, par3, par4);

		Random random = new Random();

		if (console != null) {
			for (int j1 = 0; j1 < console.getSizeInventory(); ++j1) {
				ItemStack itemstack = console.getStackInSlot(j1);

				if (itemstack != null) {
					float f = random.nextFloat() * 0.8F + 0.1F;
					float f1 = random.nextFloat() * 0.8F + 0.1F;
					float f2 = random.nextFloat() * 0.8F + 0.1F;

					while (itemstack.stackSize > 0) {
						int k1 = random.nextInt(21) + 10;

						if (k1 > itemstack.stackSize) {
							k1 = itemstack.stackSize;
						}

						itemstack.stackSize -= k1;
						EntityItem entityitem = new EntityItem(par1World,
								(double) ((float) par2 + f),
								(double) ((float) par3 + f1),
								(double) ((float) par4 + f2), new ItemStack(
										itemstack.itemID, k1,
										itemstack.getItemDamage()));

						if (itemstack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound(
									(NBTTagCompound) itemstack.getTagCompound()
											.copy());
						}

						float f3 = 0.05F;
						entityitem.motionX = (double) ((float) random
								.nextGaussian() * f3);
						entityitem.motionY = (double) ((float) random
								.nextGaussian() * f3 + 0.2F);
						entityitem.motionZ = (double) ((float) random
								.nextGaussian() * f3);
						par1World.spawnEntityInWorld(entityitem);
					}
				}
			}

			par1World.func_96440_m(par2, par3, par4, par5);
		}

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	public BlockResearchConsole(int par1) {
		super(par1, Material.iron);
		setCreativeTab(Femtocraft.femtocraftTab);
		setUnlocalizedName("BlockResearchConsole");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		switch (ForgeDirection.getOrientation(par1)) {
		case UP:
			return top;
		case DOWN:
			return bot;
		default:
			return side;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityResearchConsole();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		blockIcon = side = par1IconRegister.registerIcon(Femtocraft.ID
				.toLowerCase() + ":" + "BlockResearchConsole_side");
		top = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":"
				+ "BlockResearchConsole_top");
		bot = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":"
				+ "BlockResearchConsole_bot");
	}

}
