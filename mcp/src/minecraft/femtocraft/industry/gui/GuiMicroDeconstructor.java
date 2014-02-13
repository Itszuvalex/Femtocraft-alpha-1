package femtocraft.industry.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.industry.TileEntity.MicroDeconstructorTile;
import femtocraft.industry.containers.ContainerMicroDeconstructor;
import femtocraft.render.FemtocraftRenderUtils;

@SideOnly(Side.CLIENT)
public class GuiMicroDeconstructor extends GuiContainer {
	public static final ResourceLocation texture = new ResourceLocation(
			Femtocraft.ID.toLowerCase(), "textures/guis/Disassembler.png");
	private MicroDeconstructorTile deconstructorInventory;

	public GuiMicroDeconstructor(InventoryPlayer par1InventoryPlayer,
			MicroDeconstructorTile tileEntity) {
		super(new ContainerMicroDeconstructor(par1InventoryPlayer, tileEntity));
		this.deconstructorInventory = tileEntity;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items)
	 */
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String s = "Microtech Deconstructor";
		this.fontRenderer.drawString(s,
				this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6,
				4210752);
		this.fontRenderer.drawString(
				StatCollector.translateToLocal("container.inventory"), 8,
				this.ySize - 96 + 2, 4210752);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the
	 * items)
	 */
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,
			int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;

		// if (this.furnaceInventory.isBurning())
		// {
		// i1 = this.furnaceInventory.getBurnTimeRemainingScaled(12);
		// this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1,
		// 14, i1 + 2);
		// }

		i1 = this.deconstructorInventory.getCookProgressScaled(24);
		this.drawTexturedModalRect(k + 61, l + 37, 176, 14, i1 + 1, 16);
		i1 = (this.deconstructorInventory.currentPower * 60)
				/ this.deconstructorInventory.getMaxPower();
		this.drawTexturedModalRect(k + 10, l + 8 + (60 - i1), 176,
				31 + (60 - i1), 16, i1);

		FluidStack fluid = this.deconstructorInventory
				.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid;
		if (fluid != null) {
			Icon image = fluid.getFluid().getStillIcon();
			// Icon image = BlockFluid.getFluidIcon("water_still");
			// image = Femtocraft.mass_block.stillIcon;

			i1 = (this.deconstructorInventory.getMassAmount() * 60)
					/ this.deconstructorInventory.getMassCapacity();
			FemtocraftRenderUtils.renderLiquidInGUI(this, this.zLevel, image,
					k + 150, l + 8 + (60 - i1), 16, i1);

			// Rebind texture
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		}

		// Draw Tank Lines
		this.drawTexturedModalRect(k + 150, l + 8, 176, 91, 16, 60);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.client.gui.inventory.GuiContainer#drawScreen(int, int,
	 * float)
	 */
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);

		if (this.isPointInRegion(10, 8, 16, 60, par1, par2)) {

			int furnaceCurrent = this.deconstructorInventory.currentPower;
			int furnaceMax = this.deconstructorInventory.getMaxPower();

			// String text = String.format("%i/%i", furnaceCurrent, furnaceMax);
			String text = String.valueOf(furnaceCurrent) + '/'
					+ String.valueOf(furnaceMax);
			this.drawCreativeTabHoveringText(text, par1, par2);
		} else if (this.isPointInRegion(150, 8, 16, 60, par1, par2)) {
			int massCurrent = this.deconstructorInventory.getMassAmount();
			int massMax = this.deconstructorInventory.getMassCapacity();

			FluidStack fluid = this.deconstructorInventory
					.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid;
			String name = fluid == null ? "" : (" " + FluidRegistry
					.getFluidName(fluid));
			String text = String.valueOf(massCurrent) + '/'
					+ String.valueOf(massMax) + " mB" + name;

			this.drawCreativeTabHoveringText(text, par1, par2);
		}
	}
}
