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
import femtocraft.power.containers.ContainerMicroCube;
import femtocraft.power.containers.ContainerNanoCube;
import femtocraft.power.tiles.TileEntityNanoCubePort;
import femtocraft.power.tiles.TileEntityPowerMicroCube;
import femtocraft.render.RenderUtils;
import femtocraft.utils.FemtocraftUtils;

public class GuiMicroCube extends GuiContainer {
	public static final ResourceLocation texture = new ResourceLocation(
			Femtocraft.ID.toLowerCase(), "textures/guis/MicroCube.png");
	private final TileEntityPowerMicroCube cube;

	public GuiMicroCube(TileEntityPowerMicroCube cube) {
		super(new ContainerMicroCube(cube));
		this.cube = cube;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String s = "Micro-Cube";
		this.fontRenderer.drawString(s,
				this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6,
				FemtocraftUtils.colorFromARGB(0, 255, 255, 255));

		String power = cube.getCurrentPower() + "/" + cube.getMaxPower();
		this.fontRenderer.drawString(power,
				this.xSize / 2 - this.fontRenderer.getStringWidth(power) / 2,
				this.ySize * 4 / 5,
				FemtocraftUtils.colorFromARGB(0, 255, 255, 255));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		int power = cube.getCurrentPower() * 82;
		int max = cube.getMaxPower();
		int i1 = (int) (((power > 0) && (max > 0)) ? power / max : 0);
		this.drawTexturedModalRect(k + 52, l + 33 + (82 - i1), 176, 82 - i1,
				70, i1);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
	}

}
