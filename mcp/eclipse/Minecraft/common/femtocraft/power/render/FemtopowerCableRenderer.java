/**
 * 
 */
package femtocraft.power.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import femtocraft.power.FemtopowerCable;
import femtocraft.proxy.ClientProxyFemtocraft;

/**
 * @author Chris
 *
 */
public class FemtopowerCableRenderer implements ISimpleBlockRenderingHandler {
	Tessellator tes = Tessellator.instance;
	protected int renderID;
	
	public FemtopowerCableRenderer() {
		renderID = ClientProxyFemtocraft.FemtopowerCableRenderID;
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
		return renderID;
	}
	
	private boolean renderCable(Block block, int x, int y, int z, RenderBlocks renderer) {
		FemtopowerCable cable = (FemtopowerCable)block;
		if(block == null) return false;
		drawCore(cable, x, y, z, renderer);
		
		
		return true;
	}
	
	private void drawCore(FemtopowerCable cable, int x, int y, int z, RenderBlocks renderer) {
		//Draw Bottom
		renderer.setRenderBounds(4.0F/16.0F, 4.0F/16.0F, 4.0F/16.0F, 12.0F/16.0F, 12.0F/16.0F, 12.0F/16.0F);
		
		tes.startDrawingQuads();
		tes.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderBottomFace(cable, 0.0, 0.0, 0.0, cable.coreBorder);
		tes.draw();
		//Draw Top
		tes.startDrawingQuads();
		tes.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderTopFace(cable, 0.0, 0.0, 0.0, cable.coreBorder);
		tes.draw();
		//Draw East
		tes.startDrawingQuads();
		tes.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderEastFace(cable, 0.0, 0.0, 0.0, cable.coreBorder);
		tes.draw();
		//Draw West
		tes.startDrawingQuads();
		tes.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderWestFace(cable, 0.0, 0.0, 0.0, cable.coreBorder);
		tes.draw();
		//Draw North
		tes.startDrawingQuads();
		tes.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderNorthFace(cable, 0.0, 0.0, 0.0, cable.coreBorder);
		tes.draw();
		//Draw South
		tes.startDrawingQuads();
		tes.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderSouthFace(cable, 0.0, 0.0, 0.0, cable.coreBorder);
		tes.draw();
	}

}
