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

package femtocraft.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class RenderUtils {

    public static void renderCube(float x, float y, float z, float startx,
                                  float starty, float startz, float endx, float endy, float endz,
                                  Icon texture) {
        RenderUtils.drawTopFace(x, y, z, startx, endx, startz, endz, endy,
                                texture, texture.getMinU(), texture.getMaxU(),
                                texture.getMinV(), texture.getMaxV());
        RenderUtils.drawBottomFace(x, y, z, startx, endx, startz, endz, starty,
                                   texture, texture.getMinU(), texture.getMaxU(),
                                   texture.getMinV(), texture.getMaxV());
        RenderUtils.drawNorthFace(x, y, z, startx, endx, starty, endy, startz,
                                  texture, texture.getMinU(), texture.getMaxU(),
                                  texture.getMinV(), texture.getMaxV());
        RenderUtils.drawEastFace(x, y, z, starty, endy, startz, endz, endx,
                                 texture, texture.getMinU(), texture.getMaxU(),
                                 texture.getMinV(), texture.getMaxV());
        RenderUtils.drawSouthFace(x, y, z, startx, endx, starty, endy, endz,
                                  texture, texture.getMinU(), texture.getMaxU(),
                                  texture.getMinV(), texture.getMaxV());
        RenderUtils.drawWestFace(x, y, z, starty, endy, startz, endz, startx,
                                 texture, texture.getMinU(), texture.getMaxU(),
                                 texture.getMinV(), texture.getMaxV());
    }

    public static void renderDoubleSidedCube(float x, float y, float z,
                                             float startx, float starty, float startz, float endx, float endy,
                                             float endz, Icon texture) {
        RenderUtils.drawTopFace(x, y, z, startx, endx, startz, endz, endy,
                                texture, texture.getMinU(), texture.getMaxU(),
                                texture.getMinV(), texture.getMaxV());
        RenderUtils.drawBottomFace(x, y, z, startx, endx, startz, endz, endy,
                                   texture, texture.getMinU(), texture.getMaxU(),
                                   texture.getMinV(), texture.getMaxV());

        RenderUtils.drawBottomFace(x, y, z, startx, endx, startz, endz, starty,
                                   texture, texture.getMinU(), texture.getMaxU(),
                                   texture.getMinV(), texture.getMaxV());
        RenderUtils.drawTopFace(x, y, z, startx, endx, startz, endz, starty,
                                texture, texture.getMinU(), texture.getMaxU(),
                                texture.getMinV(), texture.getMaxV());

        RenderUtils.drawNorthFace(x, y, z, startx, endx, starty, endy, startz,
                                  texture, texture.getMinU(), texture.getMaxU(),
                                  texture.getMinV(), texture.getMaxV());
        RenderUtils.drawSouthFace(x, y, z, startx, endx, starty, endy, startz,
                                  texture, texture.getMinU(), texture.getMaxU(),
                                  texture.getMinV(), texture.getMaxV());

        RenderUtils.drawEastFace(x, y, z, starty, endy, startz, endz, endx,
                                 texture, texture.getMinU(), texture.getMaxU(),
                                 texture.getMinV(), texture.getMaxV());
        RenderUtils.drawWestFace(x, y, z, starty, endy, startz, endz, endx,
                                 texture, texture.getMinU(), texture.getMaxU(),
                                 texture.getMinV(), texture.getMaxV());

        RenderUtils.drawSouthFace(x, y, z, startx, endx, starty, endy, endz,
                                  texture, texture.getMinU(), texture.getMaxU(),
                                  texture.getMinV(), texture.getMaxV());
        RenderUtils.drawNorthFace(x, y, z, startx, endx, starty, endy, endz,
                                  texture, texture.getMinU(), texture.getMaxU(),
                                  texture.getMinV(), texture.getMaxV());

        RenderUtils.drawWestFace(x, y, z, starty, endy, startz, endz, startx,
                                 texture, texture.getMinU(), texture.getMaxU(),
                                 texture.getMinV(), texture.getMaxV());
        RenderUtils.drawEastFace(x, y, z, starty, endy, startz, endz, startx,
                                 texture, texture.getMinU(), texture.getMaxU(),
                                 texture.getMinV(), texture.getMaxV());
    }

    public static void drawTopFace(float x, float y, float z, float xmin,
                                   float xmax, float zmin, float zmax, float yoffset, Icon texture,
                                   float minU, float maxU, float minV, float maxV) {
        Tessellator tes = Tessellator.instance;

        tes.addTranslation(x, y, z);

        tes.addVertexWithUV(xmin, yoffset, zmin, minU, maxV);
        tes.addVertexWithUV(xmin, yoffset, zmax, minU, minV);
        tes.addVertexWithUV(xmax, yoffset, zmax, maxU, minV);
        tes.addVertexWithUV(xmax, yoffset, zmin, maxU, maxV);

        tes.addTranslation(-x, -y, -z);
    }

    public static RenderQuad makeTopFace(float xmin, float xmax, float zmin,
                                         float zmax, float yoffset, Icon texture, float minU, float maxU,
                                         float minV, float maxV) {
        RenderPoint a = new RenderPoint();
        RenderPoint b = new RenderPoint();
        RenderPoint c = new RenderPoint();
        RenderPoint d = new RenderPoint();

        a.y = b.y = c.y = d.y = yoffset;
        a.x = b.x = xmin;
        c.x = d.x = xmax;
        a.z = d.z = zmin;
        b.z = c.z = zmax;

        return new RenderQuad(a, b, c, d, texture, minU, maxU, minV, maxV);
    }

    public static void drawBottomFace(float x, float y, float z, float xmin,
                                      float xmax, float zmin, float zmax, float yoffset, Icon texture,
                                      float minU, float maxU, float minV, float maxV) {
        Tessellator tes = Tessellator.instance;

        tes.addTranslation(x, y, z);

        tes.addVertexWithUV(xmin, yoffset, zmin, minU, maxV);
        tes.addVertexWithUV(xmax, yoffset, zmin, minU, minV);
        tes.addVertexWithUV(xmax, yoffset, zmax, maxU, minV);
        tes.addVertexWithUV(xmin, yoffset, zmax, maxU, maxV);

        tes.addTranslation(-x, -y, -z);
    }

    public static RenderQuad makeBottomFace(float xmin, float xmax, float zmin,
                                            float zmax, float yoffset, Icon texture, float minU, float maxU,
                                            float minV, float maxV) {
        RenderPoint a = new RenderPoint();
        RenderPoint b = new RenderPoint();
        RenderPoint c = new RenderPoint();
        RenderPoint d = new RenderPoint();

        a.y = b.y = c.y = d.y = yoffset;
        a.x = d.x = xmin;
        b.x = c.x = xmax;
        a.z = b.z = zmin;
        c.z = d.z = zmax;

        return new RenderQuad(a, b, c, d, texture, minU, maxU, minV, maxV);
    }

    public static void drawNorthFace(float x, float y, float z, float xmin,
                                     float xmax, float ymin, float ymax, float zoffset, Icon texture,
                                     float minU, float maxU, float minV, float maxV) {
        Tessellator tes = Tessellator.instance;

        tes.addTranslation(x, y, z);

        tes.addVertexWithUV(xmin, ymin, zoffset, minU, maxV);
        tes.addVertexWithUV(xmin, ymax, zoffset, minU, minV);
        tes.addVertexWithUV(xmax, ymax, zoffset, maxU, minV);
        tes.addVertexWithUV(xmax, ymin, zoffset, maxU, maxV);

        tes.addTranslation(-x, -y, -z);
    }

    public static RenderQuad makeNorthFace(float xmin, float xmax, float ymin,
                                           float ymax, float zoffset, Icon texture, float minU, float maxU,
                                           float minV, float maxV) {
        return new RenderQuad(new RenderPoint(xmax, ymax, zoffset),
                              new RenderPoint(xmax, ymin, zoffset), new RenderPoint(xmin,
                                                                                    ymin, zoffset), new RenderPoint(xmin, ymax, zoffset),
                              texture, minU, maxU, minV, maxV
        );
    }

    public static void drawEastFace(float x, float y, float z, float ymin,
                                    float ymax, float zmin, float zmax, float xoffset, Icon texture,
                                    float minU, float maxU, float minV, float maxV) {

        Tessellator tes = Tessellator.instance;

        tes.addTranslation(x, y, z);

        tes.addVertexWithUV(xoffset, ymin, zmin, minU, maxV);
        tes.addVertexWithUV(xoffset, ymax, zmin, minU, minV);
        tes.addVertexWithUV(xoffset, ymax, zmax, maxU, minV);
        tes.addVertexWithUV(xoffset, ymin, zmax, maxU, maxV);

        tes.addTranslation(-x, -y, -z);
    }

    public static RenderQuad makeEastFace(float ymin, float ymax, float zmin,
                                          float zmax, float xoffset, Icon texture, float minU, float maxU,
                                          float minV, float maxV) {
        return new RenderQuad(new RenderPoint(xoffset, ymax, zmax),
                              new RenderPoint(xoffset, ymin, zmax), new RenderPoint(xoffset,
                                                                                    ymin, zmin), new RenderPoint(xoffset, ymax, zmin),
                              texture, minU, maxU, minV, maxV
        );
    }

    public static void drawSouthFace(float x, float y, float z, float xmin,
                                     float xmax, float ymin, float ymax, float zoffset, Icon texture,
                                     float minU, float maxU, float minV, float maxV) {
        Tessellator tes = Tessellator.instance;

        tes.addTranslation(x, y, z);

        tes.addVertexWithUV(xmin, ymin, zoffset, minU, maxV);
        tes.addVertexWithUV(xmax, ymin, zoffset, minU, minV);
        tes.addVertexWithUV(xmax, ymax, zoffset, maxU, minV);
        tes.addVertexWithUV(xmin, ymax, zoffset, maxU, maxV);

        tes.addTranslation(-x, -y, -z);
    }

    public static RenderQuad makeSouthFace(float xmin, float xmax, float ymin,
                                           float ymax, float zoffset, Icon texture, float minU, float maxU,
                                           float minV, float maxV) {
        return new RenderQuad(new RenderPoint(xmin, ymax, zoffset),
                              new RenderPoint(xmin, ymin, zoffset), new RenderPoint(xmax,
                                                                                    ymin, zoffset), new RenderPoint(xmax, ymax, zoffset),
                              texture, minU, maxU, minV, maxV
        );
    }

    public static void drawWestFace(float x, float y, float z, float ymin,
                                    float ymax, float zmin, float zmax, float xoffset, Icon texture,
                                    float minU, float maxU, float minV, float maxV) {

        Tessellator tes = Tessellator.instance;

        tes.addTranslation(x, y, z);

        tes.addVertexWithUV(xoffset, ymin, zmin, minU, maxV);
        tes.addVertexWithUV(xoffset, ymin, zmax, minU, minV);
        tes.addVertexWithUV(xoffset, ymax, zmax, maxU, minV);
        tes.addVertexWithUV(xoffset, ymax, zmin, maxU, maxV);

        tes.addTranslation(-x, -y, -z);
    }

    public static RenderQuad makeWestFace(float ymin, float ymax, float zmin,
                                          float zmax, float xoffset, Icon texture, float minU, float maxU,
                                          float minV, float maxV) {
        return new RenderQuad(new RenderPoint(xoffset, ymax, zmin),
                              new RenderPoint(xoffset, ymin, zmin), new RenderPoint(xoffset,
                                                                                    ymin, zmax), new RenderPoint(xoffset, ymax, zmax),
                              texture, minU, maxU, minV, maxV
        );
    }

    public static void drawArbitraryFace(float x, float y, float z, float xmin,
                                         float xmax, float ymin, float ymax, float zmin, float zmax,
                                         ForgeDirection normal, Icon texture, float minU, float maxU,
                                         float minV, float maxV) {
        switch (normal) {
            case UP:
                RenderUtils.drawTopFace(x, y, z, xmin, xmax, zmin, zmax, ymax,
                                        texture, minU, maxU, minV, maxV);
                break;
            case DOWN:
                RenderUtils.drawBottomFace(x, y, z, xmin, xmax, zmin, zmax, ymin,
                                           texture, minU, maxU, minV, maxV);
                break;
            case NORTH:
                RenderUtils.drawNorthFace(x, y, z, xmin, xmax, ymin, ymax, zmin,
                                          texture, minU, maxU, minV, maxV);
                break;
            case EAST:
                RenderUtils.drawEastFace(x, y, z, ymin, ymax, zmin, zmax, xmax,
                                         texture, minU, maxU, minV, maxV);
                break;
            case SOUTH:
                RenderUtils.drawSouthFace(x, y, z, xmin, xmax, ymin, ymax, zmax,
                                          texture, minU, maxU, minV, maxV);
                break;
            case WEST:
                RenderUtils.drawWestFace(x, y, z, ymin, ymax, zmin, zmax, xmin,
                                         texture, minU, maxU, minV, maxV);
                break;
            default:
                break;
        }
    }

    public static void drawFaceByPoints(float x, float y, float z,
                                        RenderPoint A, RenderPoint B, RenderPoint C, RenderPoint D,
                                        Icon texture, float minU, float maxU, float minV, float maxV) {
        Tessellator tes = Tessellator.instance;

        tes.addTranslation(x, y, z);

        tes.addVertexWithUV(A.x, A.y, A.z, minU, maxV);
        tes.addVertexWithUV(B.x, B.y, B.z, minU, minV);
        tes.addVertexWithUV(C.x, C.y, C.z, maxU, minV);
        tes.addVertexWithUV(D.x, D.y, D.z, maxU, maxV);

        tes.addTranslation(-x, -y, -z);
    }

    public static void renderLiquidInGUI(GuiContainer container, float zheight,
                                         Icon icon, int x, int y, int width, int height) {
        TextureManager man = Minecraft.getMinecraft().getTextureManager();
        // terrain.png
        man.bindTexture(man.getResourceLocation(0));
        renderLiquidInGUI_height(container, zheight, icon, x, y, width, height);
    }

    private static void renderLiquidInGUI_height(GuiContainer container,
                                                 float zheight, Icon icon, int x, int y, int width, int height) {
        int i = 0;
        int remaining = height;
        if ((height - width) > 0) {
            for (i = 0; width < remaining; i += width, remaining -= width) {
                drawTexturedModalSquareFromIcon(zheight, x, y + i, width, icon);
            }
        }

        drawTexturedModalRectFromIcon(zheight, x, y + i, width, remaining,
                                      icon.getMinU(), icon.getMaxU(), icon.getMinV(),
                                      icon.getInterpolatedV((remaining * 16.f) / width));
    }

    private static void renderLiquidInGUI_width(GuiContainer container,
                                                float zheight, Icon icon, int x, int y, int width, int height) {
        int i = 0;
        int remaining = width;
        if ((width - height) > 0) {
            for (i = 0; height < remaining; i += height, remaining -= height) {
                drawTexturedModalSquareFromIcon(zheight, x + i, y, height, icon);
            }
        }

        drawTexturedModalRectFromIcon(zheight, x + i, y, remaining, height,
                                      icon.getMinU(),
                                      icon.getInterpolatedV(((remaining * 16.f) / height)),
                                      icon.getMinV(), icon.getMaxV());
    }

    private static void drawTexturedModalSquareFromIcon(float zheight, int x,
                                                        int y, int size, Icon icon) {
        drawTexturedModalRectFromIcon(zheight, x, y, size, size,
                                      icon.getMinU(), icon.getMaxU(), icon.getMinV(), icon.getMaxV());
    }

    private static void drawTexturedModalRectFromIcon(float zheight, int x,
                                                      int y, int width, int height, float minU, float maxU, float minV,
                                                      float maxV) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) (x), (double) (y + height),
                                    (double) zheight, (double) minU, (double) maxV);
        tessellator.addVertexWithUV((double) (x + width),
                                    (double) (y + height), (double) zheight, (double) maxU,
                                    (double) maxV);
        tessellator.addVertexWithUV((double) (x + width), (double) (y),
                                    (double) zheight, (double) maxU, (double) minV);
        tessellator.addVertexWithUV((double) (x), (double) (y),
                                    (double) zheight, (double) minU, (double) minV);
        tessellator.draw();
    }

    public static void drawLine(int x1, int x2, int y1, int y2, int width,
                                int color) {

        float difX = x2 - x1;
        float difY = y2 - y1;
        float length = (float) Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2));
        float xS = (width * difY / length) / 2.f;
        float yS = (width * difX / length) / 2.f;

        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(red, green, blue, alpha);
        tessellator.startDrawingQuads();
        // Counterclockwise...WHICH IS APPARENTLY WRONG CAUSE SCREW YOU
        // MINECRAFT
        // tessellator.addVertex((double) x1 - xS, (double) y1 + yS, 0.0D);
        // tessellator.addVertex((double) x1 + xS, (double) y1 - yS, 0.0D);
        // tessellator.addVertex((double) x2 + xS, (double) y2 - yS, 0.0D);
        // tessellator.addVertex((double) x2 - xS, (double) y2 + yS, 0.0D);
        tessellator.addVertex((double) x2 - xS, (double) y2 + yS, 0.0D);
        tessellator.addVertex((double) x2 + xS, (double) y2 - yS, 0.0D);
        tessellator.addVertex((double) x1 + xS, (double) y1 - yS, 0.0D);
        tessellator.addVertex((double) x1 - xS, (double) y1 + yS, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
