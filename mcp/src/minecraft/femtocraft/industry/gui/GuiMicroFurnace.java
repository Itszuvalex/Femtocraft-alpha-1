/*******************************************************************************
 * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 * Itszuvalex@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/

package femtocraft.industry.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.industry.containers.ContainerMicroFurnace;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroFurnace;
import femtocraft.utils.FemtocraftUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiMicroFurnace extends GuiContainer {
    public static final ResourceLocation texture = new ResourceLocation(
            Femtocraft.ID.toLowerCase(), "textures/guis/MicroFurnace.png");
    private TileEntityBaseEntityMicroFurnace furnaceInventory;

    public GuiMicroFurnace(InventoryPlayer par1InventoryPlayer,
                           TileEntityBaseEntityMicroFurnace par2TileEntityFurnace) {
        super(new ContainerMicroFurnace(par1InventoryPlayer,
                par2TileEntityFurnace));
        this.furnaceInventory = par2TileEntityFurnace;
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

        int furnaceCurrent = this.furnaceInventory.currentPower;
        int furnaceMax = this.furnaceInventory.getMaxPower();

        // String text = String.format("%i/%i", furnaceCurrent, furnaceMax);
        String text = FemtocraftUtils.formatIntegerToString(furnaceCurrent) +
                '/'
                + FemtocraftUtils.formatIntegerToString(furnaceMax);

        if (this.isPointInRegion(18, 12, 16, 60, par1, par2)) {
            this.drawCreativeTabHoveringText(text, par1, par2);
        }
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String s = "Micro-Furnace";
        this.fontRenderer.drawString(s,
                this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6,
                FemtocraftUtils.colorFromARGB(0, 255, 255, 255));
        this.fontRenderer.drawString(
                StatCollector.translateToLocal("container.inventory"), 8,
                this.ySize - 96 + 2,
                FemtocraftUtils.colorFromARGB(0, 255, 255, 255));
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    @Override
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

        i1 = this.furnaceInventory.getCookProgressScaled(38);
        this.drawTexturedModalRect(k + 73, l + 34, 176, 13, i1, 18);
        i1 = (this.furnaceInventory.currentPower * 60)
                / this.furnaceInventory.getMaxPower();
        this.drawTexturedModalRect(k + 18, l + 12 + (60 - i1), 176,
                32 + (60 - i1), 16 + (60 - i1), 60);
    }
}
