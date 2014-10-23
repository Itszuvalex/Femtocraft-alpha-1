/*
 * ******************************************************************************
 *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 *  * Itszuvalex@gmail.com
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *  *****************************************************************************
 */

package com.itszuvalex.femtocraft.industry.gui;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.core.gui.GuiBase;
import com.itszuvalex.femtocraft.industry.containers.ContainerNanoFabricator;
import com.itszuvalex.femtocraft.industry.tiles.TileEntityNanoFabricator;
import com.itszuvalex.femtocraft.render.RenderUtils;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class GuiNanoFabricator extends GuiBase {
    public static final ResourceLocation texture = new ResourceLocation(
            Femtocraft.ID().toLowerCase(), "textures/guis/NanoFabricator.png");
    private TileEntityNanoFabricator fabricatorInventory;

    public GuiNanoFabricator(EntityPlayer player, InventoryPlayer par1InventoryPlayer,
                             TileEntityNanoFabricator tileEntity) {
        super(new ContainerNanoFabricator(player, par1InventoryPlayer, tileEntity));
        this.ySize = 204;
        this.fabricatorInventory = tileEntity;
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

        if (this.isPointInRegion(10, 8, 16, 60, par1, par2)) {

            int furnaceCurrent = this.fabricatorInventory.currentPower;
            int furnaceMax = this.fabricatorInventory.getMaxPower();

            // String text = String.format("%i/%i", furnaceCurrent, furnaceMax);
            String text = FemtocraftUtils.formatIntegerToString(furnaceCurrent) + '/'
                          + FemtocraftUtils.formatIntegerToString(furnaceMax);
            this.drawCreativeTabHoveringText(text, par1, par2);
        } else if (this.isPointInRegion(150, 8, 16, 60, par1, par2)) {
            int massCurrent = this.fabricatorInventory.getMassAmount();
            int massMax = this.fabricatorInventory.getMassCapacity();

            FluidStack fluid = this.fabricatorInventory
                    .getTankInfo(ForgeDirection.UNKNOWN)[0].fluid;
            String name = fluid == null ? "" : (" " + FemtocraftUtils
                    .capitalize(FluidRegistry.getFluidName(fluid)));
            String text = FemtocraftUtils.formatIntegerToString(massCurrent) + '/'
                          + FemtocraftUtils.formatIntegerToString(massMax) + " mB" + name;

            this.drawCreativeTabHoveringText(text, par1, par2);
        } else if (this.isPointInRegion(94, 54, 16, 16, par1, par2)) {
            if (fabricatorInventory.getStackInSlot(10) == null) {
                this.drawCreativeTabHoveringText("Assembly Schematic", par1,
                        par2);
            }
        }
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String s = "Nano Fabricator";
        this.fontRendererObj.drawString(s,
                this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 5,
                FemtocraftUtils.colorFromARGB(0, 255, 255, 255));
        this.fontRendererObj.drawString(
                StatCollector.translateToLocal("container.inventory"), 8,
                this.ySize - 96 + 4,
                FemtocraftUtils.colorFromARGB(0, 255, 255, 255));
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
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

        i1 = this.fabricatorInventory.getCookProgressScaled(30);
        this.drawTexturedModalRect(k + 86, l + 21, 176, 0, i1 + 1, 31);
        i1 = (this.fabricatorInventory.currentPower * 60)
             / this.fabricatorInventory.getMaxPower();
        this.drawTexturedModalRect(k + 10, l + 8 + (60 - i1), 176,
                31 + (60 - i1), 16, i1);

        FluidStack fluid = this.fabricatorInventory
                .getTankInfo(ForgeDirection.UNKNOWN)[0].fluid;
        if (fluid != null) {
            IIcon image = fluid.getFluid().getStillIcon();
            // Icon image = BlockFluid.getFluidIcon("water_still");
            // image = Femtocraft.blockFluidMass.stillIcon;

            i1 = (this.fabricatorInventory.getMassAmount() * 60)
                 / this.fabricatorInventory.getMassCapacity();
            RenderUtils.renderLiquidInGUI(this, this.zLevel, image, k + 150, l
                                                                             + 8 + (60 - i1), 16, i1);

            // Rebind texture
            Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        }

        // Draw Tank Lines
        this.drawTexturedModalRect(k + 150, l + 8, 176, 91, 16, 60);
    }
}