package femtocraft.research.containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.research.tiles.TileResearchConsole;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public class ContainerResearchConsole extends Container {
	private final TileResearchConsole console;

	public ContainerResearchConsole(TileResearchConsole console) {
		this.console = console;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return console.isUseableByPlayer(entityplayer);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		super.updateProgressBar(par1, par2);

		if (par1 == 0) {
			console.setResearchProgress(par2);
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting par1iCrafting) {
		super.addCraftingToCrafters(par1iCrafting);
		par1iCrafting.sendProgressBarUpdate(this, 0,
				console.getResearchProgress());
	}
}
