/**
 * 
 */
package femtocraft.power.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import femtocraft.FemtocraftUtils;
import femtocraft.power.FemtopowerCable;
import femtocraft.proxy.ClientProxyFemtocraft;

/**
 * @author Chris
 *
 */
public class FemtopowerCableRenderer implements ISimpleBlockRenderingHandler {
	public FemtopowerCableRenderer() {
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		renderCable(block, 0, 0, 0, renderer);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
			return renderCable(block, x, y, z, renderer);

	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return ClientProxyFemtocraft.FemtopowerCableRenderID;
	}
	
	private boolean renderCable(Block block, int x, int y, int z, RenderBlocks renderer) {
		FemtopowerCable cable = (FemtopowerCable)block;
		if(block == null) return false;
		drawCore(cable, x, y, z, renderer);
		
		
		return true;
	}
	
	private void drawCore(FemtopowerCable cable, int x, int y, int z, RenderBlocks renderer) {
		FemtocraftUtils.renderCube(x, y, z, 5.0f/16.0f, 5.0f/16.0f, 5.0f/16.0f, 11.0f/16.0f, 11.0f/16.0f, 11.0f/16.0f, cable.coil);
		FemtocraftUtils.renderCube(x, y, z, 4.0f/16.0f, 4.0f/16.0f, 4.0f/16.0f, 12.0f/16.0f, 12.0f/16.0f, 12.0f/16.0f, cable.coreBorder);
//		
//		Tessellator tes = Tessellator.instance;
//		
//		tes.addTranslation(x, y, z);
//	
//		//Draw core
//		
//		float minU = cable.coil.getMinU();
//		float minV = cable.coil.getMinV();
//		float maxU = cable.coil.getMaxU();
//		float maxV = cable.coil.getMaxV();
//		
//		double min = 5.0D/16.0D;
//		double max = 11.0D/16.0D;
////		
////		//Draw top face
////		tes.addVertexWithUV(min, max, min, minU, maxV);
////		tes.addVertexWithUV(min, max, max, minU, minV);
////		tes.addVertexWithUV(max, max, max, maxU, minV);
////		tes.addVertexWithUV(max, max, min, maxU, maxV);
////		
////		//Draw bottom face
////		tes.addVertexWithUV(min, min, min, minU, maxV);
////		tes.addVertexWithUV(max, min, min, minU, minV);
////		tes.addVertexWithUV(max, min, max, maxU, minV);
////		tes.addVertexWithUV(min, min, max, maxU, maxV);
////		
////		//Draw north face
////		tes.addVertexWithUV(min, min, min, minU, maxV);
////		tes.addVertexWithUV(min, max, min, minU, minV);
////		tes.addVertexWithUV(max, max, min, maxU, minV);
////		tes.addVertexWithUV(max, min, min, maxU, maxV);
////		
////		//Draw east face
////		tes.addVertexWithUV(max, min, min, minU, maxV);
////		tes.addVertexWithUV(max, max, min, minU, minV);
////		tes.addVertexWithUV(max, max, max, maxU, minV);
////		tes.addVertexWithUV(max, min, max, maxU, maxV);
////		
////		//Draw south face
////		tes.addVertexWithUV(min, min, max, minU, maxV);
////		tes.addVertexWithUV(max, min, max, minU, minV);
////		tes.addVertexWithUV(max, max, max, maxU, minV);
////		tes.addVertexWithUV(min, max, max, maxU, maxV);
////		
////		//Draw west face
////		tes.addVertexWithUV(min, min, min, minU, maxV);
////		tes.addVertexWithUV(min, min, max, minU, minV);
////		tes.addVertexWithUV(min, max, max, maxU, minV);
////		tes.addVertexWithUV(min, max, min, maxU, maxV);
////		
//		//Draw core border
//		//Draw core
//	
//		minU = cable.coreBorder.getMinU();
//		minV = cable.coreBorder.getMinV();
//		maxU = cable.coreBorder.getMaxU();
//		maxV = cable.coreBorder.getMaxV();
//		
//		min = 4.0D/16.0D;
//		max = 12.0D/16.0D;
//		
//		//Draw top face
//		tes.addVertexWithUV(min, max, min, minU, maxV);
//		tes.addVertexWithUV(min, max, max, minU, minV);
//		tes.addVertexWithUV(max, max, max, maxU, minV);
//		tes.addVertexWithUV(max, max, min, maxU, maxV);
//		
//		//Draw bottom face
//		tes.addVertexWithUV(min, min, min, minU, maxV);
//		tes.addVertexWithUV(max, min, min, minU, minV);
//		tes.addVertexWithUV(max, min, max, maxU, minV);
//		tes.addVertexWithUV(min, min, max, maxU, maxV);
//		
//		//Draw north face
//		tes.addVertexWithUV(min, min, min, minU, maxV);
//		tes.addVertexWithUV(min, max, min, minU, minV);
//		tes.addVertexWithUV(max, max, min, maxU, minV);
//		tes.addVertexWithUV(max, min, min, maxU, maxV);
//		
//		//Draw east face
//		tes.addVertexWithUV(max, min, min, minU, maxV);
//		tes.addVertexWithUV(max, max, min, minU, minV);
//		tes.addVertexWithUV(max, max, max, maxU, minV);
//		tes.addVertexWithUV(max, min, max, maxU, maxV);
//		
//		//Draw south face
//		tes.addVertexWithUV(min, min, max, minU, maxV);
//		tes.addVertexWithUV(max, min, max, minU, minV);
//		tes.addVertexWithUV(max, max, max, maxU, minV);
//		tes.addVertexWithUV(min, max, max, maxU, maxV);
//		
//		//Draw west face
//		tes.addVertexWithUV(min, min, min, minU, maxV);
//		tes.addVertexWithUV(min, min, max, minU, minV);
//		tes.addVertexWithUV(min, max, max, maxU, minV);
//		tes.addVertexWithUV(min, max, min, maxU, maxV);
//		
//		tes.addTranslation(-x, -y, -z);
	}

}
