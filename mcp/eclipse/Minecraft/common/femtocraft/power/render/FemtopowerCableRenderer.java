/**
 * 
 */
package femtocraft.power.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import femtocraft.cooking.blocks.cuttingBoard;
import femtocraft.power.FemtopowerCable;
import femtocraft.proxy.ClientProxyFemtocraft;

/**
 * @author Chris
 *
 */
public class FemtopowerCableRenderer implements ISimpleBlockRenderingHandler {
	Tessellator tes = Tessellator.instance;
	
	public FemtopowerCableRenderer() {
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
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
		float minU = cable.coil.getMinU();
		float minV = cable.coil.getMinV();
		float maxU = cable.coil.getMaxU();
		float maxV = cable.coil.getMaxV();
		
		double min = 5.0D/16.0D;
		double max = 11.0D/16.0D;
		
		//Draw top face
		tes.addVertexWithUV(x + min, y + max, z + min, minU, maxV);
		tes.addVertexWithUV(x + min, y + max, z + max, minU, minV);
		tes.addVertexWithUV(x + max, y + max, z + max, maxU, minV);
		tes.addVertexWithUV(x + max, y + max, z + min, maxU, maxV);
		
		//Draw bottom face
		tes.addVertexWithUV(x + min, y + min, z + min, minU, maxV);
		tes.addVertexWithUV(x + max, y + min, z + min, minU, minV);
		tes.addVertexWithUV(x + max, y + min, z + max, maxU, minV);
		tes.addVertexWithUV(x + min, y + min, z + max, maxU, maxV);
		
		//Draw north face
		tes.addVertexWithUV(x + min, y + min, z + min, minU, maxV);
		tes.addVertexWithUV(x + min, y + max, z + min, minU, minV);
		tes.addVertexWithUV(x + max, y + max, z + min, maxU, minV);
		tes.addVertexWithUV(x + max, y + min, z + min, maxU, maxV);
		
		//Draw east face
		tes.addVertexWithUV(x + max, y + min, z + min, minU, maxV);
		tes.addVertexWithUV(x + max, y + max, z + min, minU, minV);
		tes.addVertexWithUV(x + max, y + max, z + max, maxU, minV);
		tes.addVertexWithUV(x + max, y + min, z + max, maxU, maxV);
		
		//Draw south face
		tes.addVertexWithUV(x + min, y + min, z + max, minU, maxV);
		tes.addVertexWithUV(x + max, y + min, z + max, minU, minV);
		tes.addVertexWithUV(x + max, y + max, z + max, maxU, minV);
		tes.addVertexWithUV(x + min, y + max, z + max, maxU, maxV);
		
		//Draw west face
		tes.addVertexWithUV(x + min, y + min, z + min, minU, maxV);
		tes.addVertexWithUV(x + min, y + min, z + max, minU, minV);
		tes.addVertexWithUV(x + min, y + max, z + max, maxU, minV);
		tes.addVertexWithUV(x + min, y + max, z + min, maxU, maxV);
	}

}
