package femtocraft.render;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;

public class FemtocraftRenderUtils {

	public static void renderCube(float x, float y, float z, 
							float startx, float starty, float startz, 
							float endx, float endy, float endz, 
							Icon texture) {
		FemtocraftRenderUtils.drawTopFace(x, y, z, startx, endx, startz, endz, endy, texture, texture.getMinU(), texture.getMaxU(), texture.getMinV(), texture.getMaxV());
		FemtocraftRenderUtils.drawBottomFace(x, y, z, startx, endx, startz, endz, starty, texture,  texture.getMinU(), texture.getMaxU(), texture.getMinV(), texture.getMaxV());
		FemtocraftRenderUtils.drawNorthFace(x, y, z, startx, endx, starty, endy, startz, texture,  texture.getMinU(), texture.getMaxU(), texture.getMinV(), texture.getMaxV());
		FemtocraftRenderUtils.drawEastFace(x, y, z, starty, endy, startz, endz, endx, texture,  texture.getMinU(), texture.getMaxU(), texture.getMinV(), texture.getMaxV());
		FemtocraftRenderUtils.drawSouthFace(x, y, z, startx, endx, starty, endy, endz, texture,  texture.getMinU(), texture.getMaxU(), texture.getMinV(), texture.getMaxV());
		FemtocraftRenderUtils.drawWestFace(x, y, z, starty, endy, startz, endz, startx, texture,  texture.getMinU(), texture.getMaxU(), texture.getMinV(), texture.getMaxV());
	}
	
	public static void renderDoubleSidedCube(float x, float y, float z,
											float startx, float starty, float startz,
											float endx, float endy, float endz,
											Icon texture)
	{
		FemtocraftRenderUtils.drawTopFace(x, y, z, startx, endx, startz, endz, endy, texture, texture.getMinU(), texture.getMaxU(), texture.getMinV(), texture.getMaxV());
		FemtocraftRenderUtils.drawBottomFace(x, y, z, startx, endx, startz, endz, endy, texture, texture.getMinU(), texture.getMaxU(), texture.getMinV(), texture.getMaxV());
		
		FemtocraftRenderUtils.drawBottomFace(x, y, z, startx, endx, startz, endz, starty, texture,  texture.getMinU(), texture.getMaxU(), texture.getMinV(), texture.getMaxV());
		FemtocraftRenderUtils.drawTopFace(x, y, z, startx, endx, startz, endz, starty, texture,  texture.getMinU(), texture.getMaxU(), texture.getMinV(), texture.getMaxV());
		
		FemtocraftRenderUtils.drawNorthFace(x, y, z, startx, endx, starty, endy, startz, texture,  texture.getMinU(), texture.getMaxU(), texture.getMinV(), texture.getMaxV());
		FemtocraftRenderUtils.drawSouthFace(x, y, z, startx, endx, starty, endy, startz, texture,  texture.getMinU(), texture.getMaxU(), texture.getMinV(), texture.getMaxV());
		
		FemtocraftRenderUtils.drawEastFace(x, y, z, starty, endy, startz, endz, endx, texture,  texture.getMinU(), texture.getMaxU(), texture.getMinV(), texture.getMaxV());
		FemtocraftRenderUtils.drawWestFace(x, y, z, starty, endy, startz, endz, endx, texture,  texture.getMinU(), texture.getMaxU(), texture.getMinV(), texture.getMaxV());
		
		FemtocraftRenderUtils.drawSouthFace(x, y, z, startx, endx, starty, endy, endz, texture,  texture.getMinU(), texture.getMaxU(), texture.getMinV(), texture.getMaxV());
		FemtocraftRenderUtils.drawNorthFace(x, y, z, startx, endx, starty, endy, endz, texture,  texture.getMinU(), texture.getMaxU(), texture.getMinV(), texture.getMaxV());
		
		FemtocraftRenderUtils.drawWestFace(x, y, z, starty, endy, startz, endz, startx, texture,  texture.getMinU(), texture.getMaxU(), texture.getMinV(), texture.getMaxV());
		FemtocraftRenderUtils.drawEastFace(x, y, z, starty, endy, startz, endz, startx, texture,  texture.getMinU(), texture.getMaxU(), texture.getMinV(), texture.getMaxV());
		}
	
	public static void drawTopFace(float x, float y, float z, 
								float xmin, float xmax, float zmin, float zmax, 
								float yoffset, Icon texture, 
								float minU,  float maxU, float minV, float maxV) {
		Tessellator tes = Tessellator.instance;
		
		tes.addTranslation(x, y, z);
		
		tes.addVertexWithUV(xmin, yoffset, zmin, minU, maxV);
		tes.addVertexWithUV(xmin, yoffset, zmax, minU, minV);
		tes.addVertexWithUV(xmax, yoffset, zmax, maxU, minV);
		tes.addVertexWithUV(xmax, yoffset, zmin, maxU, maxV);
		
		tes.addTranslation(-x, -y, -z);
	}
	
	public static Quad makeTopFace(float xmin, float xmax, float zmin, float zmax, float yoffset, Icon texture, float minU, float maxU, float minV, float maxV)
	{
		Point a = new Point();
		Point b = new Point();
		Point c = new Point();
		Point d = new Point();
		
		a.y = b.y = c.y = d.y = yoffset;
		a.x = b.x = xmin;
		c.x = d.x = xmax;
		a.z = d.z = zmin;
		b.z = c.z = zmax;
		
		return new Quad(a,b,c,d,texture,minU,maxU,minV,maxV);
	}
	
	public static void drawBottomFace(float x, float y, float z, 
								float xmin, float xmax, float zmin, float zmax, 
								float yoffset, Icon texture,
								float minU,  float maxU, float minV, float maxV) {
		Tessellator tes = Tessellator.instance;
		
		tes.addTranslation(x, y, z);
		
		tes.addVertexWithUV(xmin, yoffset, zmin, minU, maxV);
		tes.addVertexWithUV(xmax, yoffset, zmin, minU, minV);
		tes.addVertexWithUV(xmax, yoffset, zmax, maxU, minV);
		tes.addVertexWithUV(xmin, yoffset, zmax, maxU, maxV);
		
		tes.addTranslation(-x, -y, -z);
	}
	
	public static Quad makeBottomFace(float xmin, float xmax, float zmin, float zmax, float yoffset, Icon texture, float minU, float maxU, float minV, float maxV)
	{
		Point a = new Point();
		Point b = new Point();
		Point c = new Point();
		Point d = new Point();
		
		a.y = b.y = c.y = d.y = yoffset;
		a.x = d.x = xmin;
		b.x = c.x = xmax;
		a.z = b.z = zmin;
		c.z = d.z = zmax;
		
		return new Quad(a,b,c,d,texture,minU,maxU,minV,maxV);
	}
	
	public static void drawNorthFace(float x, float y, float z, 
			float xmin, float xmax, float ymin, float ymax, 
			float zoffset, Icon texture,
			float minU,  float maxU, float minV, float maxV) {
		Tessellator tes = Tessellator.instance;
		
		tes.addTranslation(x, y, z);
		
		tes.addVertexWithUV(xmin, ymin, zoffset, minU, maxV);
		tes.addVertexWithUV(xmin, ymax, zoffset, minU, minV);
		tes.addVertexWithUV(xmax, ymax, zoffset, maxU, minV);
		tes.addVertexWithUV(xmax, ymin, zoffset, maxU, maxV);
		
		tes.addTranslation(-x, -y, -z);
	}
	
	public static Quad makeNorthFace(float xmin, float xmax, float ymin, float ymax, float zoffset, Icon texture, float minU, float maxU, float minV, float maxV)
	{
		Point a = new Point();
		Point b = new Point();
		Point c = new Point();
		Point d = new Point();
		
		a.z = b.z = c.z = d.z = zoffset;
		a.x = b.x = xmin;
		c.x = d.x = xmax;
		a.y = d.y = ymin;
		b.y = c.y = ymax;
		
		return new Quad(a,b,c,d,texture,minU,maxU,minV,maxV);
	}
	
	public static void drawEastFace(float x, float y, float z, 
			float ymin, float ymax, float zmin, float zmax, 
			float xoffset, Icon texture,
			float minU,  float maxU, float minV, float maxV) {
		
		Tessellator tes = Tessellator.instance;
		
		tes.addTranslation(x, y, z);
		
		tes.addVertexWithUV(xoffset, ymin, zmin, minU, maxV);
		tes.addVertexWithUV(xoffset, ymax, zmin, minU, minV);
		tes.addVertexWithUV(xoffset, ymax, zmax, maxU, minV);
		tes.addVertexWithUV(xoffset, ymin, zmax, maxU, maxV);
		
		tes.addTranslation(-x, -y, -z);
	}
	
	public static Quad makeEastFace(float ymin, float ymax, float zmin, float zmax, float xoffset, Icon texture, float minU, float maxU, float minV, float maxV)
	{
		Point a = new Point();
		Point b = new Point();
		Point c = new Point();
		Point d = new Point();
		
		a.x = b.x = c.x = d.x = xoffset;
		a.y = d.y = ymin;
		b.y = c.y = ymax;
		a.z = b.z = zmin;
		c.z = d.z = zmax;
		
		return new Quad(a,b,c,d,texture,minU,maxU,minV,maxV);
	}
	
	public static void drawSouthFace(float x, float y, float z, 
			float xmin, float xmax, float ymin, float ymax, 
			float zoffset, Icon texture,
			float minU,  float maxU, float minV, float maxV) {
		Tessellator tes = Tessellator.instance;
		
		tes.addTranslation(x, y, z);
		
		tes.addVertexWithUV(xmin, ymin, zoffset, minU, maxV);
		tes.addVertexWithUV(xmax, ymin, zoffset, minU, minV);
		tes.addVertexWithUV(xmax, ymax, zoffset, maxU, minV);
		tes.addVertexWithUV(xmin, ymax, zoffset, maxU, maxV);
		
		tes.addTranslation(-x, -y, -z);
	}
	
	public static Quad makeSouthFace(float xmin, float xmax, float ymin, float ymax, float zoffset, Icon texture, float minU, float maxU, float minV, float maxV)
	{
		Point a = new Point();
		Point b = new Point();
		Point c = new Point();
		Point d = new Point();
		
		a.z = b.z = c.z = d.z = zoffset;
		a.x = d.x = xmin;
		b.x = c.x = xmax;
		a.y = b.y = ymin;
		c.y = d.y = ymax;
		
		return new Quad(a,b,c,d,texture,minU,maxU,minV,maxV);
	}
	
	public static void drawWestFace(float x, float y, float z, 
			float ymin, float ymax, float zmin, float zmax, 
			float xoffset, Icon texture,
			float minU,  float maxU, float minV, float maxV) {
		
		Tessellator tes = Tessellator.instance;
		
		tes.addTranslation(x, y, z);
		
		tes.addVertexWithUV(xoffset, ymin, zmin, minU, maxV);
		tes.addVertexWithUV(xoffset, ymin, zmax, minU, minV);
		tes.addVertexWithUV(xoffset, ymax, zmax, maxU, minV);
		tes.addVertexWithUV(xoffset, ymax, zmin, maxU, maxV);
		
		tes.addTranslation(-x, -y, -z);
	}
	
	public static Quad makeWestFace(float ymin, float ymax, float zmin, float zmax, float xoffset, Icon texture, float minU, float maxU, float minV, float maxV)
	{
		Point a = new Point();
		Point b = new Point();
		Point c = new Point();
		Point d = new Point();
		
		a.x = b.x = c.x = d.x = xoffset;
		a.y = b.y = ymin;
		c.y = d.y = ymax;
		a.z = d.z = zmin;
		b.z = c.z = zmax;
		
		return new Quad(a,b,c,d,texture,minU,maxU,minV,maxV);
	}
	
	public static void drawArbitraryFace(float x, float y, float z, 
										float xmin, float xmax, float ymin, float ymax, float zmin, float zmax, ForgeDirection normal,
										Icon texture, float minU, float maxU, float minV, float maxV) {
		switch(normal) {
		case UP:
			FemtocraftRenderUtils.drawTopFace(x, y, z, xmin, xmax, zmin, zmax, ymax, texture, minU, maxU, minV, maxV);
			break;
		case DOWN:
			FemtocraftRenderUtils.drawBottomFace(x, y, z, xmin, xmax, zmin, zmax, ymin, texture, minU, maxU, minV, maxV);
			break;
		case NORTH:
			FemtocraftRenderUtils.drawNorthFace(x, y, z, xmin, xmax, ymin, ymax, zmin, texture, minU, maxU, minV, maxV);
			break;
		case EAST:
			FemtocraftRenderUtils.drawEastFace(x, y, z, ymin, ymax, zmin, zmax, xmax, texture, minU, maxU, minV, maxV);
			break;
		case SOUTH:
			FemtocraftRenderUtils.drawSouthFace(x, y, z, xmin, xmax, ymin, ymax, zmax, texture, minU, maxU, minV, maxV);
			break;
		case WEST:
			FemtocraftRenderUtils.drawWestFace(x, y, z, ymin, ymax, zmin, zmax, xmin, texture, minU, maxU, minV, maxV);
			break;
		default:
			break;
		}
	}	
	
	public static void drawFaceByPoints(float x, float y, float z, Point A, Point B, Point C, Point D, Icon texture, float minU, float maxU, float minV, float maxV) {
		Tessellator tes = Tessellator.instance;
		
		tes.addTranslation(x, y, z);
		
		tes.addVertexWithUV(A.x, A.y, A.z, minU, maxV);
		tes.addVertexWithUV(B.x, B.y, B.z, minU, minV);
		tes.addVertexWithUV(C.x, C.y, C.z, maxU, minV);
		tes.addVertexWithUV(D.x, D.y, D.z, maxU, maxV);
		
		tes.addTranslation(-x, -y, -z);
	}
	
	public static void renderLiquidInGUI(GuiContainer container, float zheight, Icon icon, int x, int y, int width, int height)
	{
		TextureManager man = Minecraft.getMinecraft().getTextureManager();
		//terrain.png
		man.bindTexture(man.getResourceLocation(0));
		renderLiquidInGUI_height(container, zheight, icon, x, y, width, height);
	}
	
	private static void renderLiquidInGUI_height(GuiContainer container, float zheight, Icon icon, int x, int y, int width, int height)
	{
		int i = 0;
		int remaining = height;
		if((height - width) > 0)
		{
			for(i = 0; width < remaining; i += width, remaining-= width)
			{
				drawTexturedModalSquareFromIcon(zheight, x, y+i, width, icon);
			}
		}
		
		drawTexturedModalRectFromIcon(zheight, x, y + i, width, remaining, icon.getMinU(), icon.getMaxU(), icon.getMinV(), icon.getInterpolatedV((remaining*16.f)/width));
	}
	
	private static void renderLiquidInGUI_width(GuiContainer container, float zheight, Icon icon, int x, int y, int width, int height)
	{
		int i = 0;
		if((width - height) > 0)
		{
			for(i = 0; i < (width-height); i += height)
			{
				drawTexturedModalSquareFromIcon(zheight, x+i, y, height, icon);
			}
		}
		
		int remaining = width - i*height;
		drawTexturedModalRectFromIcon(zheight, x + i, y, remaining, height, icon.getMinU(), icon.getInterpolatedV(((remaining*16.f)/height)), icon.getMinV(), icon.getMaxV());
	}
	
	private static void drawTexturedModalSquareFromIcon(float zheight, int x, int y, int size, Icon icon)
	{
		drawTexturedModalRectFromIcon(zheight, x, y, size, size, icon.getMinU(), icon.getMaxU(), icon.getMinV(), icon.getMaxV());
	}
	
	private static void drawTexturedModalRectFromIcon(float zheight, int x, int y, int width, int height, float minU, float maxU, float minV, float maxV)
	{
		Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + height), (double)zheight, (double)minU, (double)maxV);
        tessellator.addVertexWithUV((double)(x + width), (double)(y + height), (double)zheight, (double)maxU, (double)maxV);
        tessellator.addVertexWithUV((double)(x + width), (double)(y + 0), (double)zheight, (double)maxU, (double)minV);
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)zheight, (double)minU, (double)minV);
        tessellator.draw();
	}
}
