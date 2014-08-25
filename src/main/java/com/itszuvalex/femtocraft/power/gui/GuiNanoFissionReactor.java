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

package com.itszuvalex.femtocraft.power.gui;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.power.containers.ContainerNanoFissionReactor;
import com.itszuvalex.femtocraft.power.tiles.TileEntityNanoFissionReactorCore;
import com.itszuvalex.femtocraft.render.RenderUtils;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import org.lwjgl.opengl.GL11;

public class GuiNanoFissionReactor extends GuiContainer {

    public static final ResourceLocation texture = new ResourceLocation(
            Femtocraft.ID.toLowerCase(), "textures/guis/NanoFissionReactor.png");
    private static final int decrementButtonX = 111;
    private static final int decrementButtonWidth = 117 - decrementButtonX;
    private static final int decrementButtonY = 61;
    private static final int decrementButtonHeight = 67 - decrementButtonY;
    private static final int incrementButtonX = 122;
    private static final int incrementButtonWidth = 128 - incrementButtonX;
    private static final int incrementButtonY = 61;
    private static final int incrementButtonHeight = 67 - incrementButtonY;
    private static final int abortButtonX = 37;
    private static final int abortButtonWidth = 108 - abortButtonX;
    private static final int abortButtonY = 67;
    private static final int abortButtonHeight = 75 - abortButtonY;
    private TileEntityNanoFissionReactorCore reactor;

    public GuiNanoFissionReactor(EntityPlayer player, InventoryPlayer par1InventoryPlayer,
                                 TileEntityNanoFissionReactorCore reactor) {
        super(new ContainerNanoFissionReactor(player, par1InventoryPlayer, reactor));
        this.reactor = reactor;
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        if (par3 == 0) {
            int k = (this.width - this.xSize) / 2;
            int l = (this.height - this.ySize) / 2;
            //incrementButton
            if ((par1 >= (k + incrementButtonX))
                    && (par1 <= (k + incrementButtonX + incrementButtonWidth))
                    && (par2 >= (l + incrementButtonY))
                    && (par2 <= (l + incrementButtonY + incrementButtonHeight))) {
                Minecraft.getMinecraft().sndManager.playSoundFX("random.click",
                        1.0F, 1.0F);
                reactor.onIncrementClick();
            }

            //decrementButton
            if ((par1 >= (k + decrementButtonX))
                    && (par1 <= (k + decrementButtonX + decrementButtonWidth))
                    && (par2 >= (l + decrementButtonY))
                    && (par2 <= (l + decrementButtonY + decrementButtonHeight))) {
                Minecraft.getMinecraft().sndManager.playSoundFX("random.click",
                        1.0F, 1.0F);
                reactor.onDecrementClick();
            }

            //abortButton
            if ((par1 >= (k + abortButtonX))
                    && (par1 <= (k + abortButtonX + abortButtonWidth))
                    && (par2 >= (l + abortButtonY))
                    && (par2 <= (l + abortButtonY + abortButtonHeight))) {
                Minecraft.getMinecraft().sndManager.playSoundFX("random.click",
                        1.0F, 1.0F);
                reactor.onAbortClick();
            }
            super.mouseClicked(par1, par2, par3);
        }
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
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;

        //incrementButton
        if ((par1 >= (k + incrementButtonX))
                && (par1 <= (k + incrementButtonX + incrementButtonWidth))
                && (par2 >= (l + incrementButtonY))
                && (par2 <= (l + incrementButtonY + incrementButtonHeight))) {
            this.drawGradientRect(
                    k + incrementButtonX, l + incrementButtonY, k + incrementButtonWidth + incrementButtonX,
                    l + incrementButtonY +
                            incrementButtonHeight, FemtocraftUtils.colorFromARGB(60, 45, 0, 110),
                    FemtocraftUtils.colorFromARGB(60, 45, 0, 110)
            );
        }

        //decrementButton
        if ((par1 >= (k + decrementButtonX))
                && (par1 <= (k + decrementButtonX + decrementButtonWidth))
                && (par2 >= (l + decrementButtonY))
                && (par2 <= (l + decrementButtonY + decrementButtonHeight))) {
            this.drawGradientRect(
                    k + decrementButtonX,
                    l + decrementButtonY,
                    k + decrementButtonWidth + decrementButtonX, l + decrementButtonY + decrementButtonHeight,
                    FemtocraftUtils.colorFromARGB(60, 45, 0, 110),
                    FemtocraftUtils.colorFromARGB(60, 45, 0, 110)
            );
        }

        //abortButton
        if ((par1 >= (k + abortButtonX))
                && (par1 <= (k + abortButtonX + abortButtonWidth))
                && (par2 >= (l + abortButtonY))
                && (par2 <= (l + abortButtonY + abortButtonHeight))) {
            this.drawGradientRect(
                    k + abortButtonX,
                    l + abortButtonY, k + abortButtonWidth + abortButtonX, l + abortButtonY + abortButtonHeight,
                    FemtocraftUtils.colorFromARGB(60, 230, 230, 0),
                    FemtocraftUtils.colorFromARGB(60, 230, 230, 0)
            );
        }

        //Heat
        if (this.isPointInRegion(8, 28, 16, 46, par1, par2)) {
            int temperatureCurrent = (int) this.reactor.getTemperatureCurrent();
            int temperatureMax = this.reactor.getTemperatureMax();

            this.drawCreativeTabHoveringText("" + temperatureCurrent + '/' + temperatureMax + " TU", par1, par2);
        }
        //Cooled Salt Tank
        else if (this.isPointInRegion(134, 8, 16, 60, par1, par2)) {
            int cooledSaltAmount = this.reactor.getCooledSaltAmount();
            int cooledSaltMax = this.reactor.getCooledSaltTank().getCapacity();

            FluidStack fluid = this.reactor.getCooledSaltTank().getFluid();
            String name = fluid == null ? "" : (" " + FluidRegistry
                    .getFluidName(fluid));
            String text = FemtocraftUtils.formatIntegerToString(cooledSaltAmount) + '/'
                    + FemtocraftUtils.formatIntegerToString(cooledSaltMax) + " mB" + name;

            this.drawCreativeTabHoveringText(text, par1, par2);

            //Molten Salt Tank
        }
        else if (this.isPointInRegion(152, 8, 16, 60, par1, par2)) {
            int moltenSaltAmount = this.reactor.getMoltenSaltAmount();
            int moltenSaltMax = this.reactor.getMoltenSaltTank().getCapacity();

            FluidStack fluid = this.reactor.getMoltenSaltTank().getFluid();
            String name = fluid == null ? "" : (" " + FluidRegistry
                    .getFluidName(fluid));
            String text = FemtocraftUtils.formatIntegerToString(moltenSaltAmount) + '/'
                    + FemtocraftUtils.formatIntegerToString(moltenSaltMax) + " mB" + name;

            this.drawCreativeTabHoveringText(text, par1, par2);
        }
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String s = "Fission Reactor";
        this.fontRenderer.drawString(s,
                (110 + 25) / 2 - this.fontRenderer.getStringWidth(s) / 2, 6,
                FemtocraftUtils.colorFromARGB(0, 255, 255, 255));
        this.fontRenderer.drawString(
                StatCollector.translateToLocal("container.inventory"), 8,
                76,
                FemtocraftUtils.colorFromARGB(0, 255, 255, 255));

        this.fontRenderer.drawString("-", 112, 61, FemtocraftUtils.colorFromARGB(0, 255, 255, 255));
        this.fontRenderer.drawString("+", 123, 61, FemtocraftUtils.colorFromARGB(0, 255, 255, 255));

        String abort = "ABORT";
        this.fontRenderer.drawString(abort,
                (abortButtonX + (abortButtonWidth) / 2) - ((this.fontRenderer.getStringWidth(abort) /
                        2)), 68, FemtocraftUtils.colorFromARGB(0, 255, 255,
                        255));


        String concentrationTarget = String.format("%2.1f", reactor.getThoriumConcentrationTarget() * 100.f) + "%";
        this.fontRenderer.drawString(concentrationTarget,
                112 + (127 - 112) / 2 - this.fontRenderer.getStringWidth(concentrationTarget) / 2, 48 + (58 - 48) / 2 -
                        this.fontRenderer
                                .FONT_HEIGHT /
                                2,
                FemtocraftUtils.colorFromARGB(0, 255, 255, 255));

        String currentConcentration = String.format("%2.1f", reactor.getThoriumConcentration() * 100.f) + "%";
        this.fontRenderer.drawString(currentConcentration,
                90 + (104 - 90) / 2 - this.fontRenderer.getStringWidth(currentConcentration) / 2, 51 + (61 - 51) / 2 -
                        this.fontRenderer
                                .FONT_HEIGHT /
                                2,
                FemtocraftUtils.colorFromARGB(0, 255, 255, 255));

        this.fontRenderer.drawString("Thorium", 29, 51, FemtocraftUtils.colorFromARGB(0, 255, 255, 255));
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

        //ReactorState
        switch (reactor.getState()) {
            case ACTIVE:
                this.drawTexturedModalRect(k + 30, l + 70, 176, 6, 3, 3);
                break;
            case UNSTABLE:
                this.drawTexturedModalRect(k + 30, l + 70, 176, 3, 3, 3);
                break;
            case CRITICAL:
                this.drawTexturedModalRect(k + 30, l + 70, 176, 0, 3, 3);
                break;
            default:
        }

        //Heat
        int heatHeight = 46;
        i1 = (int) (Math.min(
                this.reactor.getTemperatureCurrent() / (float) this.reactor.getTemperatureMax(), 1.f) *
                heatHeight);
        this.drawTexturedModalRect(k + 7, l + 27 + (heatHeight - i1), 176, 69, 8, 3);

        //CooledMoltenSalt
        renderTank(this.reactor.getCooledSaltTank(), 134, 8, k, l);

        //Molten Salt
        renderTank(this.reactor.getMoltenSaltTank(), 152, 8, k, l);

        // Draw Tank Lines
        this.drawTexturedModalRect(k + 134, l + 8, 176, 9, 16, 60);
        this.drawTexturedModalRect(k + 152, l + 8, 176, 9, 16, 60);
    }

    private void renderTank(IFluidTank tank, int x, int y, int k, int l) {
        FluidStack fluid = tank.getFluid();
        if (fluid != null) {
            Icon image = fluid.getFluid().getStillIcon();

            int i1 = (fluid.amount * 59)
                    / tank.getCapacity();
            RenderUtils.renderLiquidInGUI(this, this.zLevel, image, k + x, l
                    + y + (59 - i1), 16, i1);

            // Rebind texture
            Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        }
    }
}

