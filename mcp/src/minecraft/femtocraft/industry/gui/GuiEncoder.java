package femtocraft.industry.gui;

import femtocraft.Femtocraft;
import femtocraft.industry.containers.ContainerEncoder;
import femtocraft.industry.tiles.TileEntityEncoder;
import femtocraft.render.RenderUtils;
import femtocraft.utils.FemtocraftUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class GuiEncoder extends GuiContainer {

    public static final ResourceLocation texture = new ResourceLocation(
            Femtocraft.ID.toLowerCase(), "textures/guis/Encoder.png");
    private TileEntityEncoder encoder;

    public GuiEncoder(InventoryPlayer par1InventoryPlayer,
                      TileEntityEncoder par2Encoder) {
        super(new ContainerEncoder(par1InventoryPlayer, par2Encoder));
        this.encoder = par2Encoder;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String s = "Encoder";
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

        i1 = this.encoder.getProgressScaled(20);
        this.drawTexturedModalRect(k + 119, l + 25, 176, 0, 18, i1);
        i1 = (int) (this.encoder.getFillPercentage() * 60);
        this.drawTexturedModalRect(k + 10, l + 68 - i1, 176, 100 - i1, 16, i1);

        FluidStack fluid = this.encoder.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid;
        if (fluid != null) {
            Icon image = fluid.getFluid().getStillIcon();
            // Icon image = BlockFluid.getFluidIcon("water_still");
            // image = Femtocraft.mass_block.stillIcon;

            i1 = (this.encoder.getMassAmount() * 60)
                    / this.encoder.getMassCapacity();
            RenderUtils.renderLiquidInGUI(this, this.zLevel, image, k + 150, l
                    + 8 + (60 - i1), 16, i1);

            // Rebind texture
            Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        }

        // Draw Tank Lines
        this.drawTexturedModalRect(k + 150, l + 8, 176, 100, 16, 60);
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

        if (this.isPointInRegion(18, 12, 16, 60, par1, par2)) {
            int furnaceCurrent = this.encoder.getCurrentPower();
            int furnaceMax = this.encoder.getMaxPower();

            // String text = String.format("%i/%i", furnaceCurrent, furnaceMax);
            String text = String.valueOf(furnaceCurrent) + '/'
                    + String.valueOf(furnaceMax);

            this.drawCreativeTabHoveringText(text, par1, par2);

        }
        else if (this.isPointInRegion(150, 8, 16, 60, par1, par2)) {
            int massCurrent = this.encoder.getMassAmount();
            int massMax = this.encoder.getMassCapacity();

            FluidStack fluid = this.encoder.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid;
            String name = fluid == null ? "" : (" " + FluidRegistry
                    .getFluidName(fluid));
            String text = String.valueOf(massCurrent) + '/'
                    + String.valueOf(massMax) + " mB" + name;

            this.drawCreativeTabHoveringText(text, par1, par2);
        }
    }
}