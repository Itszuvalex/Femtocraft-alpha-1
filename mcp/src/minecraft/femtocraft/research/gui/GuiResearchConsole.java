package femtocraft.research.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import femtocraft.Femtocraft;
import femtocraft.managers.research.ResearchTechnology;
import femtocraft.research.containers.ContainerResearchConsole;
import femtocraft.research.tiles.TileEntityResearchConsole;
import femtocraft.utils.FemtocraftUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class GuiResearchConsole extends GuiContainer {
    private static final ResourceLocation texture = new ResourceLocation(
            Femtocraft.ID.toLowerCase(), "textures/guis/ResearchConsole.png");
    private static final int xSize = 176;
    private static final int ySize = 166;

    private final int researchButtonX = 63;
    private final int researchButtonWidth = 106 - researchButtonX;
    private final int researchButtonY = 35;
    private final int researchButtonHeight = 53 - researchButtonY;

    private final TileEntityResearchConsole console;

    public GuiResearchConsole(InventoryPlayer par1InventoryPlayer,
                              TileEntityResearchConsole console) {
        super(new ContainerResearchConsole(par1InventoryPlayer, console));
        this.console = console;
    }

    private void onResearchClick() {
        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = TileEntityResearchConsole.PACKET_CHANNEL;

        ByteArrayOutputStream bos = new ByteArrayOutputStream(14);
        DataOutputStream outputStream = new DataOutputStream(bos);
        try {
            outputStream.writeInt(console.xCoord);
            outputStream.writeInt(console.yCoord);
            outputStream.writeInt(console.zCoord);
            outputStream.writeInt(console.worldObj.provider.dimensionId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        packet.data = bos.toByteArray();
        packet.length = bos.size();

        PacketDispatcher.sendPacketToServer(packet);
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

        if ((par1 >= (k + researchButtonX))
                && (par1 <= (k + researchButtonX + researchButtonWidth))
                && (par2 >= (l + researchButtonY))
                && (par2 <= (l + researchButtonY + researchButtonHeight))) {
            this.drawGradientRect(k + researchButtonX, l + researchButtonY, k
                                          + researchButtonWidth + researchButtonX, l
                                          + researchButtonY + researchButtonHeight,
                                  FemtocraftUtils.colorFromARGB(60, 45, 0, 110),
                                  FemtocraftUtils.colorFromARGB(60, 45, 0, 110)
            );
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        if (par3 == 0) {
            int k = (this.width - this.xSize) / 2;
            int l = (this.height - this.ySize) / 2;

            if ((par1 >= (k + researchButtonX))
                    && (par1 <= (k + researchButtonX + researchButtonWidth))
                    && (par2 >= (l + researchButtonY))
                    && (par2 <= (l + researchButtonY + researchButtonHeight))) {
                Minecraft.getMinecraft().sndManager.playSoundFX("random.click",
                                                                1.0F, 1.0F);
                onResearchClick();
            }
        }
        super.mouseClicked(par1, par2, par3);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String s = "Research Console";
        this.fontRenderer.drawString(s,
                                     this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6,
                                     FemtocraftUtils.colorFromARGB(0, 255, 255, 255));
        this.fontRenderer.drawString(
                StatCollector.translateToLocal("container.inventory"), 8,
                this.ySize - 96 + 2,
                FemtocraftUtils.colorFromARGB(0, 255, 255, 255));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        int progress = console.getResearchProgressScaled(78);
        this.drawTexturedModalRect(k + 64, l + 65, 0, 166, progress, 6);

        if (console.displayTech != null || console.isResearching()) {

            String name = null;

            if (console.isResearching()) {
                name = console.getResearchingName();
            }
            else {
                name = console.displayTech;
            }

            ResearchTechnology tech = Femtocraft.researchManager
                    .getTechnology(name);
            if (tech != null) {

                String s = tech.name;
                this.fontRenderer.drawString(s, k + 71
                                                     + (165 - 71 - this.fontRenderer.getStringWidth(s)) / 2,
                                             l + 20,
                                             FemtocraftUtils.colorFromARGB(255, 255, 255, 255)
                );

                if (console.isResearching()) {
                    s = String.format("%d%s",
                                      console.getResearchProgressScaled(100), "%");
                    this.fontRenderer.drawString(s,
                                                 k + 168 - this.fontRenderer.getStringWidth(s),
                                                 l + 40,
                                                 FemtocraftUtils.colorFromARGB(255, 255, 255, 255));
                }
                else {
                    s = "Begin";
                    this.fontRenderer.drawString(s,
                                                 k + 85 - this.fontRenderer.getStringWidth(s) / 2, l
                                    + 36
                                    + (52 - 36 - this.fontRenderer.FONT_HEIGHT)
                                    / 2,
                                                 FemtocraftUtils.colorFromARGB(255, 255, 255, 255)
                    );
                }

                RenderItem render = new RenderItem();
                RenderHelper.enableGUIStandardItemLighting();
                render.renderItemAndEffectIntoGUI(fontRenderer, Minecraft
                                                          .getMinecraft().getTextureManager(), tech.displayItem,
                                                  k + 110, l + 33
                );
            }

        }
    }

}
