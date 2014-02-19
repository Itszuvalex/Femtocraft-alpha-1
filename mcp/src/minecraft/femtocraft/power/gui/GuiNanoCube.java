package femtocraft.power.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import femtocraft.Femtocraft;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroDeconstructor;
import femtocraft.power.containers.ContainerNanoCube;
import femtocraft.power.tiles.TileEntityNanoCubePort;
import femtocraft.render.RenderUtils;

public class GuiNanoCube extends GuiContainer {
	public static final ResourceLocation texture = new ResourceLocation(
			Femtocraft.ID.toLowerCase(), "textures/guis/NanoCube.png");
	private final TileEntityNanoCubePort controller;

	public GuiNanoCube(TileEntityNanoCubePort controller) {
		super(new ContainerNanoCube(controller));
		this.controller = controller;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String s = "Nano-Cube";
		this.fontRenderer.drawString(s,
				this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6,
				4210752);

		String power = controller.getCurrentPower() + "/"
				+ controller.getMaxPower();
		this.fontRenderer.drawString(power,
				this.xSize / 2 - this.fontRenderer.getStringWidth(power) / 2,
				this.ySize * 4 / 5, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		int i1 = (int) ((controller.getCurrentPower() * 81) / controller
				.getMaxPower());
		this.drawTexturedModalRect(k + 48, l + 33 + (81 - i1), 176, 81 - i1,
				70, 81 - i1);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
	}

}
