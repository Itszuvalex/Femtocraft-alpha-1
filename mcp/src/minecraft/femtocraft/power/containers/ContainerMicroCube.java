package femtocraft.power.containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.power.tiles.TileEntityPowerMicroCube;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public class ContainerMicroCube extends Container {
    private final TileEntityPowerMicroCube cube;
    private int lastPower = 0;

    public ContainerMicroCube(TileEntityPowerMicroCube cube) {
        this.cube = cube;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return this.cube.isUseableByPlayer(entityplayer);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        int power = cube.getCurrentPower();
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
            cube.setCurrentStorage(par2);
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1iCrafting) {
        super.addCraftingToCrafters(par1iCrafting);
        par1iCrafting.sendProgressBarUpdate(this, 0, cube.getCurrentPower());
    }
}
