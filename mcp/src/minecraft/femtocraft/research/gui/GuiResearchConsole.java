package femtocraft.research.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import femtocraft.Femtocraft;
import femtocraft.FemtocraftUtils;
import femtocraft.managers.research.ResearchTechnology;
import femtocraft.research.containers.ContainerResearchConsole;
import femtocraft.research.tiles.TileResearchConsole;

public class GuiResearchConsole extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation(
			Femtocraft.ID.toLowerCase(), "textures/guis/ResearchConsole.png");
	private static final int xSize = 176;
	private static final int ySize = 166;

	private final TileResearchConsole console;

	public GuiResearchConsole(InventoryPlayer par1InventoryPlayer,
			TileResearchConsole console) {
		super(new ContainerResearchConsole(par1InventoryPlayer, console));
		this.console = console;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items)
	 */
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String s = "Research Console";
		this.fontRenderer.drawString(s,
				this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6,
				FemtocraftUtils.colorFromARGB(0, 255, 255, 255));
		this.fontRenderer.drawString(
				StatCollector.translateToLocal("container.inventory"), 8,
				this.ySize - 96 + 2,
				FemtocraftUtils.colorFromARGB(0, 255, 255, 255));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		int progress = console.getResearchProgressScaled(78);
		this.drawTexturedModalRect(k + 64, l + 65, 0, 166, progress, 6);

		if (console.displayTech != null) {
			ResearchTechnology tech = Femtocraft.researchManager
					.getTechnology(console.displayTech);
			if (tech != null) {

				String s = tech.name;
				this.fontRenderer.drawString(s, k + 71
						+ (165 - 71 - this.fontRenderer.getStringWidth(s)) / 2,
						l + 20,
						FemtocraftUtils.colorFromARGB(255, 255, 255, 255));

				if (console.isResearching()) {
					s = String.format("%d%s",
							console.getResearchProgressScaled(100), "%");
					this.fontRenderer.drawString(s,
							k + 168 - this.fontRenderer.getStringWidth(s),
							l + 40,
							FemtocraftUtils.colorFromARGB(255, 255, 255, 255));
				} else {
					s = "Begin";
					this.fontRenderer.drawString(s,
							k + 85 - this.fontRenderer.getStringWidth(s) / 2, l
									+ 36
									+ (52 - 36 - this.fontRenderer.FONT_HEIGHT)
									/ 2,
							FemtocraftUtils.colorFromARGB(255, 255, 255, 255));
				}

				RenderItem render = new RenderItem();
				RenderHelper.enableGUIStandardItemLighting();
				render.renderItemAndEffectIntoGUI(fontRenderer, Minecraft
						.getMinecraft().getTextureManager(), tech.displayItem,
						k + 110, l + 33);
			}

		}
	}

}
