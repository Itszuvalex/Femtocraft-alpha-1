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
import com.itszuvalex.femtocraft.core.gui.GuiBase;
import com.itszuvalex.femtocraft.power.containers.ContainerMagnetoHydrodynamicGenerator;
import com.itszuvalex.femtocraft.power.tiles.TileEntityMagnetohydrodynamicGenerator;
import com.itszuvalex.femtocraft.render.RenderUtils;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import org.lwjgl.opengl.GL11;

/**
 * Created by Christopher Harris (Itszuvalex) on 8/27/14.
 */
public class GuiMagnetohydrodynamicGenerator extends GuiBase {
    public static final ResourceLocation texture = new ResourceLocation(
            Femtocraft.ID().toLowerCase(), "textures/guis/MagnetohydrodynamicGenerator.png");
    private final TileEntityMagnetohydrodynamicGenerator generator;

    public GuiMagnetohydrodynamicGenerator(TileEntityMagnetohydrodynamicGenerator generator) {
        super(new ContainerMagnetoHydrodynamicGenerator(generator));
        this.generator = generator;
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);

        //Molten Salt Tank
        if (this.isPointInRegion(30, 24, 16, 120, par1, par2)) {
            int moltenSaltAmount = this.generator.getMoltenSaltTank().getFluidAmount();
            int moltenSaltMax = this.generator.getMoltenSaltTank().getCapacity();

            FluidStack fluid = this.generator.getMoltenSaltTank().getFluid();
            String name = fluid == null ? "" : (" " + FluidRegistry
                    .getFluidName(fluid));
            String text = FemtocraftUtils.formatIntegerToString(moltenSaltAmount) + '/'
                          + FemtocraftUtils.formatIntegerToString(moltenSaltMax) + " mB" + name;

            this.drawCreativeTabHoveringText(text, par1, par2);

            //Contaminated Salt Tank
        } else if (this.isPointInRegion(130, 24, 16, 120, par1, par2)) {
            int contaminatedSaltAmount = this.generator.getContaminatedSaltTank().getFluidAmount();
            int contamiantedSaltMax = this.generator.getContaminatedSaltTank().getCapacity();

            FluidStack fluid = this.generator.getContaminatedSaltTank().getFluid();
            String name = fluid == null ? "" : (" " + FluidRegistry
                    .getFluidName(fluid));
            String text = FemtocraftUtils.formatIntegerToString(contaminatedSaltAmount) + '/'
                          + FemtocraftUtils.formatIntegerToString(contamiantedSaltMax) + " mB" + name;

            this.drawCreativeTabHoveringText(text, par1, par2);

        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String s = "Magnetohydrodynamic Generator";
        this.fontRendererObj.drawString(s,
                this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6,
                FemtocraftUtils.colorFromARGB(0, 255, 255, 255));

        String power = FemtocraftUtils.formatIntegerToString(generator
                .getCurrentPower()) + "/"
                       + FemtocraftUtils.formatIntegerToString(generator
                .getMaxPower()) + " OP";
        this.fontRendererObj.drawString(power,
                this.xSize / 2 - this.fontRendererObj.getStringWidth(power) / 2,
                this.ySize * 4 / 5,
                FemtocraftUtils.colorFromARGB(0, 255, 255, 255));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        int power = generator.getCurrentPower() * 72;
        int max = generator.getMaxPower();
        int i1 = (int) (((power > 0) && (max > 0)) ? power / max : 0);
        this.drawTexturedModalRect(k + 52, l + 47 + (72 - i1), 176, 72 - i1,
                72, i1);

        renderTank(generator.getMoltenSaltTank(), 30, 23, k, l);
        renderTank(generator.getContaminatedSaltTank(), 130, 23, k, l);

        this.drawTexturedModalRect(k + 30, l + 23, 176, 72, 16, 60);
        this.drawTexturedModalRect(k + 30, l + 23 + 60, 176, 72, 16, 60);
        this.drawTexturedModalRect(k + 130, l + 23, 176, 72, 16, 60);
        this.drawTexturedModalRect(k + 130, l + 23 + 60, 176, 72, 16, 60);
    }

    private void renderTank(IFluidTank tank, int x, int y, int k, int l) {
        FluidStack fluid = tank.getFluid();
        if (fluid != null) {
            IIcon image = fluid.getFluid().getStillIcon();

            int i1 = (fluid.amount * 120)
                     / tank.getCapacity();
            RenderUtils.renderLiquidInGUI(this, this.zLevel, image, k + x, l
                                                                           + y + (120 - i1), 16, i1);

            // Rebind texture
            Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        }
    }

}
