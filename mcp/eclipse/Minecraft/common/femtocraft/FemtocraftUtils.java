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
	
	public static float rotateXonZAxis(float x, float y, float rot) {
		return (float) (x*Math.cos(rot) - y*Math.sin(rot));
	}
	
	public static float rotateYonZAxis(float x, float y, float rot) {
		return (float) (x*Math.sin(rot) + y*Math.cos(rot));
	}
	
	public static float rotateXonYAxis(float x, float z, float rot) {
		return (float) (z*Math.cos(rot) - x*Math.sin(rot));
	}
	
	public static float rotateZonYAxis(float x, float z, float rot) {
		return (float) (z*Math.sin(rot) + x*Math.cos(rot));
	}
	
	public static float rotateYonXAxis(float y, float z, float rot) {
		return (float) (y*Math.cos(rot) - z*Math.sin(rot));
	}
	
	public static float rotateZonXAxis(float y, float z, float rot) {
		return (float) (y*Math.sin(rot) + z*Math.cos(rot));
	}
}
