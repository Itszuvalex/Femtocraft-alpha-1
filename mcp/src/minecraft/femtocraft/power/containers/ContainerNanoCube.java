package femtocraft.power.containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import femtocraft.power.tiles.TileEntityNanoCubePort;

public class ContainerNanoCube extends Container {
	private final TileEntityNanoCubePort controller;
	private int lastPower = 0;

	public ContainerNanoCube(TileEntityNanoCubePort controller) {
		this.controller = controller;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.controller.isUseableByPlayer(entityplayer);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		int power = controller.getCurrentPower();
		for (Object crafter : this.crafters) {
			ICrafting icrafting = (ICrafting) crafter;

			if (lastPower != power) {
				icrafting.sendProgressBarUpdate(this, 0, power);
			}
		}
		lastPower = power;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		super.updateProgressBar(par1, par2);

		if (par1 == 0) {
			controller.setCurrentStorage(par2);
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting par1iCrafting) {
		super.addCraftingToCrafters(par1iCrafting);
		par1iCrafting.sendProgressBarUpdate(this, 0,
				controller.getCurrentPower());
	}
}
