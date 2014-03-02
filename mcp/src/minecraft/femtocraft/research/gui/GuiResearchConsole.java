package femtocraft.research.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import femtocraft.Femtocraft;
import femtocraft.research.containers.ContainerResearchConsole;
import femtocraft.research.tiles.TileResearchConsole;

public class GuiResearchConsole extends GuiContainer {
	private static final ResourceLocation achievementTextures = new ResourceLocation(
			Femtocraft.ID.toLowerCase(), "textures/guis/GuiResearchConsole.png");
	private static final int width = 176;
	private static final int height = 166;

	private final TileResearchConsole console;

	public GuiResearchConsole(TileResearchConsole console) {
		super(new ContainerResearchConsole(console));
		this.console = console;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {

	}

}
