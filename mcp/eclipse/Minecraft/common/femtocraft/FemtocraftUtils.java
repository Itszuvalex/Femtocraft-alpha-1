package femtocraft;


import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;

public class FemtocraftUtils {

	public static void renderCube(float x, float y, float z, 
							float startx, float starty, float startz, 
							float endx, float endy, float endz, 
							Icon texture) {
		Tessellator tes = Tessellator.instance;
		
		tes.addTranslation(x, y, z);
		
		//Draw core
		
		float startU = texture.getMinU();
		float startV = texture.getMinV();
		float endU = texture.getMaxU();
		float endV = texture.getMaxV();
		
		//Draw top face
		tes.addVertexWithUV(startx, endy, startz, startU, endV);
		tes.addVertexWithUV(startx, endy, endz, startU, startV);
		tes.addVertexWithUV(endx, endy, endz, endU, startV);
		tes.addVertexWithUV(endx, endy, startz, endU, endV);
		
		//Draw bottom face
		tes.addVertexWithUV(startx, starty, startz, startU, endV);
		tes.addVertexWithUV(endx, starty, startz, startU, startV);
		tes.addVertexWithUV(endx, starty, endz, endU, startV);
		tes.addVertexWithUV(startx, starty, endz, endU, endV);
		
		//Draw north face
		tes.addVertexWithUV(startx, starty, startz, startU, endV);
		tes.addVertexWithUV(startx, endy, startz, startU, startV);
		tes.addVertexWithUV(endx, endy, startz, endU, startV);
		tes.addVertexWithUV(endx, starty, startz, endU, endV);
		
		//Draw east face
		tes.addVertexWithUV(endx, starty, startz, startU, endV);
		tes.addVertexWithUV(endx, endy, startz, startU, startV);
		tes.addVertexWithUV(endx, endy, endz, endU, startV);
		tes.addVertexWithUV(endx, starty, endz, endU, endV);
		
		//Draw south face
		tes.addVertexWithUV(startx, starty, endz, startU, endV);
		tes.addVertexWithUV(endx, starty, endz, startU, startV);
		tes.addVertexWithUV(endx, endy, endz, endU, startV);
		tes.addVertexWithUV(startx, endy, endz, endU, endV);
		
		//Draw west face
		tes.addVertexWithUV(startx, starty, startz, startU, endV);
		tes.addVertexWithUV(startx, starty, endz, startU, startV);
		tes.addVertexWithUV(startx, endy, endz, endU, startV);
		tes.addVertexWithUV(startx, endy, startz, endU, endV);
		
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
