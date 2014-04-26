package femtocraft.power.gui;

import femtocraft.Femtocraft;
import femtocraft.power.containers.ContainerFemtoCube;
import femtocraft.power.tiles.TileEntityFemtoCubePort;
import femtocraft.utils.FemtocraftUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiFemtoCube extends GuiContainer {
    public static final ResourceLocation texture = new ResourceLocation(
            Femtocraft.ID.toLowerCase(), "textures/guis/FemtoCube.png");
    private final TileEntityFemtoCubePort controller;

    public GuiFemtoCube(TileEntityFemtoCubePort controller) {
        super(new ContainerFemtoCube(controller));
        this.controller = controller;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String s = "Femto-Cube";
        this.fontRenderer.drawString(s,
                                     this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6,
                                     FemtocraftUtils.colorFromARGB(0, 255, 255, 255));

        String power = controller.getCurrentPower() + "/"
                + controller.getMaxPower();
        this.fontRenderer.drawString(power,
                                     this.xSize / 2 - this.fontRenderer.getStringWidth(power) / 2,
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

        int power = controller.getCurrentPower() * 82;
        int max = controller.getMaxPower();
        int i1 = (int) (((power > 0) && (max > 0)) ? power / max : 0);
        this.drawTexturedModalRect(k + 52, l + 33 + (82 - i1), 176, 82 - i1,
                                   70, i1);
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);
    }

}