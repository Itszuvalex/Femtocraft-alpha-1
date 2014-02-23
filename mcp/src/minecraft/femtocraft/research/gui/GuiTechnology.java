package femtocraft.research.gui;

import net.minecraft.client.gui.GuiScreen;
import femtocraft.managers.research.ResearchTechnologyStatus;

public class GuiTechnology extends GuiScreen {
	private final GuiResearch guiResearch;
	private final ResearchTechnologyStatus status;

	public GuiTechnology(GuiResearch guiResearch,
			ResearchTechnologyStatus status) {
		this.guiResearch = guiResearch;
		this.status = status;
	}
	
	@Override
	protected void keyTyped(char par1, int par2) {
		if (par2 == this.mc.gameSettings.keyBindInventory.keyCode) {
			this.mc.displayGuiScreen(guiResearch);
		} else {
			super.keyTyped(par1, par2);
		}
	}

}
