package femtocraft;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;

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
	
	public static float rotateXonZAxis(float x, float y, float rot, float  xrotoffset, float yrotoffset) {
		return (float) ((x - xrotoffset)*Math.cos(rot) - (y - yrotoffset)*Math.sin(rot)) + xrotoffset;
	}
	
	public static float rotateYonZAxis(float x, float y, float rot, float xrotoffset, float yrotoffset) {
		return (float) ((x - xrotoffset)*Math.sin(rot) + (y - yrotoffset)*Math.cos(rot)) + yrotoffset;
	}
	
	public static float rotateXonYAxis(float x, float z, float rot, float xrotoffset, float zrotoffset) {
		return (float) ((z - zrotoffset)*Math.cos(rot) - (x - xrotoffset)*Math.sin(rot)) + xrotoffset;
	}
	
	public static float rotateZonYAxis(float x, float z, float rot, float xrotoffset, float zrotoffset) {
		return (float) ((z -zrotoffset)*Math.sin(rot) + (x - xrotoffset)*Math.cos(rot)) + zrotoffset;
	}
	
	public static float rotateYonXAxis(float y, float z, float rot, float yrotoffset, float zrotoffset) {
		return (float) ((y - yrotoffset)*Math.cos(rot) - (z - zrotoffset)*Math.sin(rot)) + yrotoffset;
	}
	
	public static float rotateZonXAxis(float y, float z, float rot, float yrotoffset, float zrotoffset) {
		return (float) ((y - yrotoffset)*Math.sin(rot) + (z - zrotoffset)*Math.cos(rot)) + zrotoffset;
	}
	
	
}
