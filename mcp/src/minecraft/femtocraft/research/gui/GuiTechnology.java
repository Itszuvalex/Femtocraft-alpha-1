package femtocraft.research.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import femtocraft.Femtocraft;
import femtocraft.FemtocraftUtils;
import femtocraft.managers.research.ResearchTechnology;
import femtocraft.managers.research.ResearchTechnologyStatus;

public class GuiTechnology extends GuiScreen {
	private final GuiResearch guiResearch;
	private final ResearchTechnologyStatus status;
	private final ResearchTechnology tech;

	private static final ResourceLocation texture = new ResourceLocation(
			Femtocraft.ID.toLowerCase(), "textures/guis/GuiTechnology.png");

	private final int xSize = 256;
	private final int ySize = 202;

	public GuiTechnology(GuiResearch guiResearch,
			ResearchTechnologyStatus status) {
		this.guiResearch = guiResearch;
		this.status = status;
		this.tech = Femtocraft.researchManager.getTechnology(status.tech);
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		if (par2 == this.mc.gameSettings.keyBindInventory.keyCode) {
			this.mc.displayGuiScreen(guiResearch);
		} else {
			super.keyTyped(par1, par2);
		}
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		if (par3 == 0) {
			int k = (this.width - this.xSize) / 2;
			int l = (this.height - this.ySize) / 2;

			if ((par1 >= (k + 8)) && (par1 <= (k + 51)) && (par2 >= (l + 11))
					&& (par2 <= (l + 28))) {
				Minecraft.getMinecraft().sndManager.playSoundFX("random.click",
						1.0F, 1.0F);
				this.mc.displayGuiScreen(guiResearch);
			}
		}
		super.mouseClicked(par1, par2, par3);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.drawDefaultBackground();

		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		super.drawScreen(par1, par2, par3);

		String s = status.tech;
		this.fontRenderer.drawString(s,
				k + this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2,
				l + 17, FemtocraftUtils.colorFromARGB(255, 255, 255, 255));

		if ((par1 >= (k + 8)) && (par1 <= (k + 51)) && (par2 >= (l + 11))
				&& (par2 <= (l + 28))) {
			this.drawGradientRect(k + 8, l + 11, k + 52, l + 29,
					FemtocraftUtils.colorFromARGB(60, 45, 0, 110),
					FemtocraftUtils.colorFromARGB(60, 45, 0, 110));
		}

		this.fontRenderer.drawString("Back",
				k + 29 - this.fontRenderer.getStringWidth("Back") / 2, l + 17,
				FemtocraftUtils.colorFromARGB(255, 255, 255, 255));

		GL11.glPushMatrix();
		RenderItem renderitem = new RenderItem();
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		renderitem.renderItemAndEffectIntoGUI(
				Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft()
						.getTextureManager(), tech.displayItem, k + 66, l + 12);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();

	}

}
