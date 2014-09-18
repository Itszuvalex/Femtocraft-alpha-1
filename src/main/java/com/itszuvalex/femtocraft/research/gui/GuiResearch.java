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

package com.itszuvalex.femtocraft.research.gui;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.graph.IGraphNode;
import com.itszuvalex.femtocraft.managers.research.ManagerResearch;
import com.itszuvalex.femtocraft.managers.research.ResearchPlayer;
import com.itszuvalex.femtocraft.managers.research.ResearchTechnology;
import com.itszuvalex.femtocraft.managers.research.ResearchTechnologyStatus;
import com.itszuvalex.femtocraft.render.RenderUtils;
import com.itszuvalex.femtocraft.research.gui.graph.DummyTechNode;
import com.itszuvalex.femtocraft.research.gui.graph.TechNode;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSmallButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.Random;

@SideOnly(Side.CLIENT)
public class GuiResearch extends GuiScreen {
    private static final ResourceLocation achievementTextures = new ResourceLocation(
            Femtocraft.ID.toLowerCase(),
            "textures/guis/research_background.png");
    private static int minDisplayColumn = 0;
    /**
     * The top x coordinate of the achievement map
     */
    private static int guiMapTop = minDisplayColumn * 24 - 112;
    private static int maxDisplayColumn = 20;
    /**
     * The bottom x coordinate of the achievement map
     */
    private static int guiMapBottom = maxDisplayColumn * 24 + 112;
    private static int minDisplayRow = 0;
    /**
     * The left y coordinate of the achievement map
     */
    private static int guiMapLeft = minDisplayRow * 24 - 112;
    private static int maxDisplayRow = 40;
    /**
     * The right y coordinate of the achievement map
     */
    private static int guiMapRight = maxDisplayRow * 24 + 112;
    private final String username;
    private final ResearchPlayer researchStatus;
    protected int researchPaneWidth = 256;
    protected int researchPaneHeight = 202;
    /**
     * The current mouse x coordinate
     */
    protected int mouseX;

    /**
     * The current mouse y coordinate
     */
    protected int mouseY;
    protected double field_74117_m;
    protected double field_74115_n;

    /**
     * The x position of the achievement map
     */
    protected double guiMapX;

    /**
     * The y position of the achievement map
     */
    protected double guiMapY;
    protected double field_74124_q;
    protected double field_74123_r;

    /**
     * Whether the Mouse Button is down or not
     */
    private int isMouseButtonDown;

    private int currentPage = -1;
    private GuiSmallButton button;

    public GuiResearch(String username) {
        this.username = username;
        researchStatus = Femtocraft.researchManager.getPlayerResearch(username);
        short short1 = 141;
        short short2 = 141;
        this.field_74117_m = this.guiMapX = this.field_74124_q = (double) (
                Femtocraft.researchManager.getTechnology(ManagerResearch.MACROSCOPIC_STRUCTURES).xDisplay - 1)
                                                                 * 24 - short1 / 2 - 12;
        this.field_74115_n = this.guiMapY = this.field_74123_r = (double) (
                Femtocraft.researchManager.getTechnology(ManagerResearch.MACROSCOPIC_STRUCTURES).yDisplay * 24 -
                short2 / 2);
    }

    public static void setSize(int rows, int columns) {
        // Achievement screen grew from left to right, need to swap rows/columns
        maxDisplayColumn = rows;
        maxDisplayRow = columns;
        guiMapBottom = maxDisplayColumn * 24 + 112;
        guiMapRight = maxDisplayRow * 24 + 112;
    }

    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int par1, int par2, float par3) {
        if (Mouse.isButtonDown(0)) {
            int k = (this.width - this.researchPaneWidth) / 2;
            int l = (this.height - this.researchPaneHeight) / 2;
            int i1 = k + 8;
            int j1 = l + 17;

            if ((this.isMouseButtonDown == 0 || this.isMouseButtonDown == 1)
                && par1 >= i1 && par1 < i1 + 224 && par2 >= j1
                && par2 < j1 + 155) {
                if (this.isMouseButtonDown == 0) {
                    this.isMouseButtonDown = 1;
                } else {
                    this.guiMapX -= (double) (par1 - this.mouseX);
                    this.guiMapY -= (double) (par2 - this.mouseY);
                    this.field_74124_q = this.field_74117_m = this.guiMapX;
                    this.field_74123_r = this.field_74115_n = this.guiMapY;
                }

                this.mouseX = par1;
                this.mouseY = par2;
            }

            if (this.field_74124_q < (double) guiMapTop) {
                this.field_74124_q = (double) guiMapTop;
            }

            if (this.field_74123_r < (double) guiMapLeft) {
                this.field_74123_r = (double) guiMapLeft;
            }

            if (this.field_74124_q >= (double) guiMapBottom) {
                this.field_74124_q = (double) (guiMapBottom - 1);
            }

            if (this.field_74123_r >= (double) guiMapRight) {
                this.field_74123_r = (double) (guiMapRight - 1);
            }
        } else {
            this.isMouseButtonDown = 0;
        }

        this.drawDefaultBackground();
        this.genAchievementBackground(par1, par2, par3);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        this.drawTitle();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    @Override
    protected void keyTyped(char par1, int par2) {
        if (par2 == this.mc.gameSettings.keyBindInventory.keyCode) {
            this.mc.displayGuiScreen((GuiScreen) null);
            this.mc.setIngameFocus();
        } else {
            super.keyTyped(par1, par2);
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        if (par3 == 0) {
            for (ResearchTechnology rt : Femtocraft.researchManager
                    .getTechnologies()) {
                ResearchTechnologyStatus ts = researchStatus
                        .getTechnology(rt.name);
                if (ts != null) {
                    int k = MathHelper.floor_double(this.field_74117_m
                                                    + (this.guiMapX - this.field_74117_m)
                                                      * (double) par3);
                    int l = MathHelper.floor_double(this.field_74115_n
                                                    + (this.guiMapY - this.field_74115_n)
                                                      * (double) par3);

                    if (k < guiMapTop) {
                        k = guiMapTop;
                    }

                    if (l < guiMapLeft) {
                        l = guiMapLeft;
                    }

                    if (k >= guiMapBottom) {
                        k = guiMapBottom - 1;
                    }

                    if (l >= guiMapRight) {
                        l = guiMapRight - 1;
                    }

                    int i1 = (this.width - this.researchPaneWidth) / 2;
                    int j1 = (this.height - this.researchPaneHeight) / 2;
                    int k1 = i1 + 16;
                    int l1 = j1 + 17;

                    int j4 = rt.xDisplay * 24 - k;
                    int l3 = rt.yDisplay * 24 - l;

                    int i5 = k1 + j4;
                    int l4 = l1 + l3;

                    if (par1 >= k1 && par2 >= l1 && par1 < k1 + 224
                        && par2 < l1 + 155 && par1 >= i5 && par1 <= i5 + 22
                        && par2 >= l4 && par2 <= l4 + 22) {
                        // Open GUIS
                        Minecraft.getMinecraft().sndManager.playSoundFX(
                                "random.click", 1.0F, 1.0F);
                        Minecraft.getMinecraft().displayGuiScreen(
                                rt.getGui(this, ts));
                        this.isMouseButtonDown = 0;
                    }
                }
            }
        }

        super.mouseClicked(par1, par2, par3);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        // if (par1GuiButton.id == 1) {
        // this.mc.displayGuiScreen((GuiScreen) null);
        // this.mc.setIngameFocus();
        // }
        //
        // if (par1GuiButton.id == 2) {
        // currentPage++;
        // if (currentPage >= AchievementPage.getAchievementPages().size()) {
        // currentPage = -1;
        // }
        // button.displayString = AchievementPage.getTitle(currentPage);
        // }

        super.actionPerformed(par1GuiButton);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override
    public void initGui() {
        // this.buttonList.clear();
        // this.buttonList.addInput(new GuiSmallButton(1, this.width / 2 + 24,
        // this.height / 2 + 74, 80, 20, I18n.getString("gui.done")));
        // this.buttonList.addInput(button = new GuiSmallButton(2,
        // (width - researchPaneWidth) / 2 + 24, height / 2 + 74, 125,
        // 20, AchievementPage.getTitle(currentPage)));
    }

    /**
     * Called from the main game loop to update the screen.
     */
    @Override
    public void updateScreen() {
        this.field_74117_m = this.guiMapX;
        this.field_74115_n = this.guiMapY;
        double d0 = this.field_74124_q - this.guiMapX;
        double d1 = this.field_74123_r - this.guiMapY;

        if (d0 * d0 + d1 * d1 < 4.0D) {
            this.guiMapX += d0;
            this.guiMapY += d1;
        } else {
            this.guiMapX += d0 * 0.85D;
            this.guiMapY += d1 * 0.85D;
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    protected void genAchievementBackground(int par1, int par2, float par3) {
        int k = MathHelper.floor_double(this.field_74117_m
                                        + (this.guiMapX - this.field_74117_m) * (double) par3);
        int l = MathHelper.floor_double(this.field_74115_n
                                        + (this.guiMapY - this.field_74115_n) * (double) par3);

        if (k < guiMapTop) {
            k = guiMapTop;
        }

        if (l < guiMapLeft) {
            l = guiMapLeft;
        }

        if (k >= guiMapBottom) {
            k = guiMapBottom - 1;
        }

        if (l >= guiMapRight) {
            l = guiMapRight - 1;
        }

        int i1 = (this.width - this.researchPaneWidth) / 2;
        int j1 = (this.height - this.researchPaneHeight) / 2;
        int k1 = i1 + 16;
        int l1 = j1 + 17;
        this.zLevel = 0.0F;
        GL11.glDepthFunc(GL11.GL_GEQUAL);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.0F, -200.0F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        int i2 = k + 288 >> 4;
        int j2 = l + 288 >> 4;
        int k2 = (k + 288) % 16;
        int l2 = (l + 288) % 16;
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;
        boolean flag4 = true;
        Random random = new Random();
        int i3;
        int j3;
        int k3;

        // Make background

        for (i3 = 0; i3 * 16 - l2 < 155; ++i3) {
            float f1 = 0.6F - (float) (j2 + i3) / 25.0F * 0.3F;
            GL11.glColor4f(f1, f1, f1, 1.0F);

            for (k3 = 0; k3 * 16 - k2 < 224; ++k3) {
                random.setSeed((long) (1234 + i2 + k3));
                random.nextInt();
                j3 = random.nextInt(1 + j2 + i3) + (j2 + i3) / 2;
                Icon icon = Block.sand.getIcon(0, 0);

                if (j3 <= 37 && j2 + i3 != 35) {
                    if (j3 == 22) {
                        if (random.nextInt(2) == 0) {
                            icon = Femtocraft.blockOrePlatinum.getIcon(0, 0);
                        } else {
                            icon = Femtocraft.blockOreFarenite.getIcon(0, 0);
                        }
                    } else if (j3 == 10) {
                        icon = Femtocraft.blockOreTitanium.getIcon(0, 0);
                    } else if (j3 == 8) {
                        icon = Femtocraft.blockOreThorium.getIcon(0, 0);
                    } else if (j3 > 4) {
                        icon = Block.stone.getIcon(0, 0);
                    } else if (j3 > 0) {
                        icon = Block.dirt.getIcon(0, 0);
                    }
                } else {
                    icon = Block.bedrock.getIcon(0, 0);
                }

                Minecraft.getMinecraft().getTextureManager()
                        .bindTexture(TextureMap.locationBlocksTexture);
                this.drawTexturedModelRectFromIcon(k1 + k3 * 16 - k2, l1 + i3
                                                                           * 16 - l2, icon, 16, 16);
            }
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        int l3;
        int i4;
        int j4;

        for (ResearchTechnology tech : Femtocraft.researchManager
                .getTechnologies()) {
            ResearchTechnologyStatus rs = researchStatus
                    .getTechnology(tech.name);
            if (rs == null) {
                continue;
            }
            if (tech.prerequisites != null) {
                for (String cr : tech.prerequisites) {
                    TechNode node = Femtocraft.researchManager.getNode(tech);

                    for (IGraphNode parent : node.getParents()) {
                        IGraphNode next = parent;
                        IGraphNode prev = node;
                        while (next instanceof DummyTechNode) {
                            k3 = prev.getDisplayX() * 24 - k + 11 + k1;
                            j3 = prev.getDisplayY() * 24 - l + 11 + l1 - 11;
                            j4 = next.getDisplayX() * 24 - k + 11 + k1;
                            l3 = next.getDisplayY() * 24 - l + 11 + l1 + 11;
                            boolean flag5 = rs.researched;
                            boolean flag6 = !rs.researched;
                            i4 = Math
                                         .sin((double) (Minecraft.getSystemTime() % 600L)
                                              / 600.0D * Math.PI * 2.0D) > 0.6D ? 255
                                    : 130;
                            int color = tech.level.getColor();
                            if (flag6) {
                                color += (i4 << 24);
                            } else {
                                color += (255 << 24);
                            }

                            // this.drawHorizontalLine(k3, j4, j3, color);
                            // this.drawVerticalLine(j4, j3, l3, color);
                            RenderUtils.drawLine(k3, j4, j3, l3, 1, color);
                            RenderUtils.drawLine(j4, j4, l3 - 22, l3, 1, color);

                            // Dummy nodes should only have 1 parent
                            prev = next;
                            next = next.getParents().get(0);
                        }

                        k3 = prev.getDisplayX() * 24 - k + 11 + k1;
                        j3 = prev.getDisplayY() * 24 - l + 11 + l1 - 11;
                        j4 = next.getDisplayX() * 24 - k + 11 + k1;
                        l3 = next.getDisplayY() * 24 - l + 11 + l1 + 11;
                        boolean flag5 = rs.researched;
                        boolean flag6 = !rs.researched;
                        i4 = Math
                                     .sin((double) (Minecraft.getSystemTime() % 600L)
                                          / 600.0D * Math.PI * 2.0D) > 0.6D ? 255
                                : 130;
                        int color = tech.level.getColor();
                        if (flag6) {
                            color += (i4 << 24);
                        } else {
                            color += (255 << 24);
                        }

                        // this.drawHorizontalLine(k3, j4, j3, color);
                        // this.drawVerticalLine(j4, j3, l3, color);
                        RenderUtils.drawLine(k3, j4, j3, l3, 1, color);
                    }
                }
            }
        }

        ResearchTechnology tooltipTech = null;
        RenderItem renderitem = new RenderItem();
        int l4;
        int i5;

        for (ResearchTechnology tech : Femtocraft.researchManager
                .getTechnologies()) {
            ResearchTechnologyStatus ts = researchStatus
                    .getTechnology(tech.name);
            if (ts == null) {
                continue;
            }
            j4 = tech.xDisplay * 24 - k;
            l3 = tech.yDisplay * 24 - l;

            if (j4 >= -24 && l3 >= -24 && j4 <= 224 && l3 <= 155) {
                float f2;

                if (ts.researched) {
                    f2 = 1.0F;
                    GL11.glColor4f(f2, f2, f2, 1.0F);
                } else if (!ts.researched) {
                    f2 = Math.sin((double) (Minecraft.getSystemTime() % 600L)
                                  / 600.0D * Math.PI * 2.0D) < 0.6D ? 0.6F : 0.8F;
                    GL11.glColor4f(f2, f2, f2, 1.0F);
                }
                // else {
                // f2 = 0.3F;
                // GL11.glColor4f(f2, f2, f2, 1.0F);
                // }

                Minecraft.getMinecraft().getTextureManager()
                        .bindTexture(achievementTextures);
                i5 = k1 + j4;
                l4 = l1 + l3;

                if (tech.isKeystone) {
                    this.drawTexturedModalRect(i5 - 2, l4 - 2, 26, 202, 26, 26);
                } else {
                    this.drawTexturedModalRect(i5 - 2, l4 - 2, 0, 202, 26, 26);
                }
                //
                // if (!this.statFileWriter.canUnlockAchievement(achievement2))
                // {
                // float f3 = 0.1F;
                // GL11.glColor4f(f3, f3, f3, 1.0F);
                // renderitem.renderWithColor = false;
                // }

                RenderHelper.enableGUIStandardItemLighting();
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glBlendFunc(GL11.GL_DST_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                GL11.glEnable(GL11.GL_COLOR_MATERIAL);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_CULL_FACE);
                renderitem.renderItemAndEffectIntoGUI(
                        Minecraft.getMinecraft().fontRenderer, Minecraft
                                .getMinecraft().getTextureManager(),
                        tech.displayItem, i5 + 3, l4 + 3
                );
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL12.GL_RESCALE_NORMAL);

                // if (!this.statFileWriter.canUnlockAchievement(achievement2))
                // {
                // renderitem.renderWithColor = true;
                // }

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                if (par1 >= k1 && par2 >= l1 && par1 < k1 + 224
                    && par2 < l1 + 155 && par1 >= i5 && par1 <= i5 + 22
                    && par2 >= l4 && par2 <= l4 + 22) {
                    tooltipTech = tech;
                }
            }
        }

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager()
                .bindTexture(achievementTextures);
        this.drawTexturedModalRect(i1, j1, 0, 0, this.researchPaneWidth,
                this.researchPaneHeight);
        GL11.glPopMatrix();
        this.zLevel = 0.0F;
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        super.drawScreen(par1, par2, par3);

        if (tooltipTech != null) {

            ResearchTechnologyStatus status = researchStatus
                    .getTechnology(tooltipTech.name);
            String s = tooltipTech.name;
            String s1 = tooltipTech.shortDescription;
            j4 = par1 + 12;
            l3 = par2 - 4;

            if (true) {
                i5 = Math.max(this.fontRenderer.getStringWidth(s), 120);
                l4 = this.fontRenderer.splitStringWidth(s1, i5);

                if (status.researched) {
                    l4 += 12;
                }

                this.drawGradientRect(j4 - 3, l3 - 3, j4 + i5 + 3, l3 + l4 + 3
                                                                   + 12, -1073741824, -1073741824);
                this.fontRenderer
                        .drawSplitString(s1, j4, l3 + 12, i5, -6250336);

                if (status.researched) {
                    this.fontRenderer.drawStringWithShadow("Researched!", j4,
                            l3 + l4 + 4, -7302913);
                }
            }

            // Keep Commented

            // else {
            // i5 = Math.max(this.fontRenderer.getStringWidth(s), 120);
            // String s2 = I18n.getStringParams("achievement.requires",
            // new Object[] { I18n
            // .getString(tooltipTech.parentAchievement
            // .getName()) });
            // i4 = this.fontRenderer.splitStringWidth(s2, i5);
            // this.drawGradientRect(j4 - 3, l3 - 3, j4 + i5 + 3, l3 + i4 + 12
            // + 3, -1073741824, -1073741824);
            // this.fontRenderer
            // .drawSplitString(s2, j4, l3 + 12, i5, -9416624);
            // }

            this.fontRenderer.drawStringWithShadow(s, j4, l3,
                    status.researched ? (tooltipTech.isKeystone ? -128 : -1)
                            : (tooltipTech.isKeystone ? -8355776 : -8355712)
            );
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    /**
     * Draws the "Achievements" title at the top of the GUI.
     */
    protected void drawTitle() {
        int i = (this.width - this.researchPaneWidth) / 2;
        int j = (this.height - this.researchPaneHeight) / 2;
        this.fontRenderer.drawString("Research", i + 15, j + 5,
                FemtocraftUtils.colorFromARGB(0, 255, 255, 255));
    }
}
