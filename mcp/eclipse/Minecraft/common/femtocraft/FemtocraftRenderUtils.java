package femtocraft;


import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;

public class FemtocraftRenderUtils {

	public static void renderCube(float x, float y, float z, 
							float startx, float starty, float startz, 
							float endx, float endy, float endz, 
							Icon texture) {
//		Tesselator tes = Tesselator.instance;
		
//		tes.addTranslation(x, y, z)
		
		//Draw top face
		FemtocraftRenderUtils.drawTopFace(x, y, z, startx, endx, startz, endz, endy, texture);
//		tes.addVertexWithUV(startx, endy, startz, startU, endV);
//		tes.addVertexWithUV(startx, endy, endz, startU, startV);
//		tes.addVertexWithUV(endx, endy, endz, endU, startV);
//		tes.addVertexWithUV(endx, endy, startz, endU, endV);
		
		//Draw bottom face
		FemtocraftRenderUtils.drawBottomFace(x, y, z, startx, endx, startz, endz, starty, texture);
//		tes.addVertexWithUV(startx, starty, startz, startU, endV);
//		tes.addVertexWithUV(endx, starty, startz, startU, startV);
//		tes.addVertexWithUV(endx, starty, endz, endU, startV);
//		tes.addVertexWithUV(startx, starty, endz, endU, endV);
	
		//Draw north face
		FemtocraftRenderUtils.drawNorthFace(x, y, z, startx, endx, starty, endy, startz, texture);
//		tes.addVertexWithUV(startx, starty, startz, startU, endV);
//		tes.addVertexWithUV(startx, endy, startz, startU, startV);
//		tes.addVertexWithUV(endx, endy, startz, endU, startV);
//		tes.addVertexWithUV(endx, starty, startz, endU, endV);
		
		//Draw east face
		FemtocraftRenderUtils.drawEastFace(x, y, z, starty, endy, startz, endz, endx, texture);
//		tes.addVertexWithUV(endx, starty, startz, startU, endV);
//		tes.addVertexWithUV(endx, endy, startz, startU, startV);
//		tes.addVertexWithUV(endx, endy, endz, endU, startV);
//		tes.addVertexWithUV(endx, starty, endz, endU, endV);
		
		//Draw south face
		FemtocraftRenderUtils.drawSouthFace(x, y, z, startx, endx, starty, endy, endz, texture);
//		tes.addVertexWithUV(startx, starty, endz, startU, endV);
//		tes.addVertexWithUV(endx, starty, endz, startU, startV);
//		tes.addVertexWithUV(endx, endy, endz, endU, startV);
//		tes.addVertexWithUV(startx, endy, endz, endU, endV);
		
		//Draw west face
		FemtocraftRenderUtils.drawWestFace(x, y, z, starty, endy, startz, endz, startx, texture);
//		tes.addVertexWithUV(startx, starty, startz, startU, endV);
//		tes.addVertexWithUV(startx, starty, endz, startU, startV);
//		tes.addVertexWithUV(startx, endy, endz, endU, startV);
//		tes.addVertexWithUV(startx, endy, startz, endU, endV);
		
//		tes.addTranslation(-x, -y, -z);
	}
	
	public static void drawTopFace(float x, float y, float z, 
								float xmin, float xmax, float zmin, float zmax, 
								float yoffset, Icon texture) {
		Tessellator tes = Tessellator.instance;
		
		float startU = texture.getMinU();
		float startV = texture.getMinV();
		float endU = texture.getMaxU();
		float endV = texture.getMaxV();
		
		tes.addTranslation(x, y, z);
		
		tes.addVertexWithUV(xmin, yoffset, zmin, startU, endV);
		tes.addVertexWithUV(xmin, yoffset, zmax, startU, startV);
		tes.addVertexWithUV(xmax, yoffset, zmax, endU, startV);
		tes.addVertexWithUV(xmax, yoffset, zmin, endU, endV);
		
		tes.addTranslation(-x, -y, -z);
	}
	
	public static void drawBottomFace(float x, float y, float z, 
								float xmin, float xmax, float zmin, float zmax, 
								float yoffset, Icon texture) {
		Tessellator tes = Tessellator.instance;
		
		float startU = texture.getMinU();
		float startV = texture.getMinV();
		float endU = texture.getMaxU();
		float endV = texture.getMaxV();
		
		tes.addTranslation(x, y, z);
		
		tes.addVertexWithUV(xmin, yoffset, zmin, startU, endV);
		tes.addVertexWithUV(xmax, yoffset, zmin, startU, startV);
		tes.addVertexWithUV(xmax, yoffset, zmax, endU, startV);
		tes.addVertexWithUV(xmin, yoffset, zmax, endU, endV);
		
		tes.addTranslation(-x, -y, -z);
	}
	
	public static void drawNorthFace(float x, float y, float z, 
			float xmin, float xmax, float ymin, float ymax, 
			float zoffset, Icon texture) {
		Tessellator tes = Tessellator.instance;
		
		float startU = texture.getMinU();
		float startV = texture.getMinV();
		float endU = texture.getMaxU();
		float endV = texture.getMaxV();
		
		tes.addTranslation(x, y, z);
		
		tes.addVertexWithUV(xmin, ymin, zoffset, startU, endV);
		tes.addVertexWithUV(xmin, ymax, zoffset, startU, startV);
		tes.addVertexWithUV(xmax, ymax, zoffset, endU, startV);
		tes.addVertexWithUV(xmax, ymin, zoffset, endU, endV);
		
		tes.addTranslation(-x, -y, -z);
	}
	
	public static void drawEastFace(float x, float y, float z, 
			float ymin, float ymax, float zmin, float zmax, 
			float xoffset, Icon texture) {
		
		Tessellator tes = Tessellator.instance;
		
		float startU = texture.getMinU();
		float startV = texture.getMinV();
		float endU = texture.getMaxU();
		float endV = texture.getMaxV();
		
		tes.addTranslation(x, y, z);
		
		tes.addVertexWithUV(xoffset, ymin, zmin, startU, endV);
		tes.addVertexWithUV(xoffset, ymax, zmin, startU, startV);
		tes.addVertexWithUV(xoffset, ymax, zmax, endU, startV);
		tes.addVertexWithUV(xoffset, ymin, zmax, endU, endV);
		
		tes.addTranslation(-x, -y, -z);
	}
	
	public static void drawSouthFace(float x, float y, float z, 
			float xmin, float xmax, float ymin, float ymax, 
			float zoffset, Icon texture) {
		Tessellator tes = Tessellator.instance;
		
		float startU = texture.getMinU();
		float startV = texture.getMinV();
		float endU = texture.getMaxU();
		float endV = texture.getMaxV();
		
		tes.addTranslation(x, y, z);
		
		tes.addVertexWithUV(xmin, ymin, zoffset, startU, endV);
		tes.addVertexWithUV(xmax, ymin, zoffset, startU, startV);
		tes.addVertexWithUV(xmax, ymax, zoffset, endU, startV);
		tes.addVertexWithUV(xmin, ymax, zoffset, endU, endV);
		
		tes.addTranslation(-x, -y, -z);
	}
	
	public static void drawWestFace(float x, float y, float z, 
			float ymin, float ymax, float zmin, float zmax, 
			float xoffset, Icon texture) {
		
		Tessellator tes = Tessellator.instance;
		
		float startU = texture.getMinU();
		float startV = texture.getMinV();
		float endU = texture.getMaxU();
		float endV = texture.getMaxV();
		
		tes.addTranslation(x, y, z);
		
		tes.addVertexWithUV(xoffset, ymin, zmin, startU, endV);
		tes.addVertexWithUV(xoffset, ymin, zmax, startU, startV);
		tes.addVertexWithUV(xoffset, ymax, zmax, endU, startV);
		tes.addVertexWithUV(xoffset, ymax, zmin, endU, endV);
		
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
