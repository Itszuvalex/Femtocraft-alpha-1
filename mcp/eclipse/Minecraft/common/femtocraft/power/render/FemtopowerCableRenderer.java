/**
 * 
 */
package femtocraft.power.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import femtocraft.FemtocraftRenderUtils;
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
		FemtocraftRenderUtils.renderCube(x, y, z, 5.0f/16.0f, 5.0f/16.0f, 5.0f/16.0f, 11.0f/16.0f, 11.0f/16.0f, 11.0f/16.0f, cable.coil);
		FemtocraftRenderUtils.renderCube(x, y, z, 4.0f/16.0f, 4.0f/16.0f, 4.0f/16.0f, 12.0f/16.0f, 12.0f/16.0f, 12.0f/16.0f, cable.coreBorder);
		
		//Draw Northern connector 'cap'
		drawCoreConnectors(cable, x, y, z, renderer, ForgeDirection.UP);
		drawCoreConnectors(cable, x, y, z, renderer, ForgeDirection.DOWN);
		drawCoreConnectors(cable, x, y, z, renderer, ForgeDirection.NORTH);
		drawCoreConnectors(cable, x, y, z, renderer, ForgeDirection.EAST);
		drawCoreConnectors(cable, x, y, z, renderer, ForgeDirection.SOUTH);
		drawCoreConnectors(cable, x, y, z, renderer, ForgeDirection.WEST);
	}
	
	private void drawCoreConnectors(FemtopowerCable cable, int x, int y, int z, RenderBlocks renderer, ForgeDirection direction) {
			switch(direction) {
				case UP:
					FemtocraftRenderUtils.drawTopFace(x, y, z, 7.0F/16.0F, 9.0F/16.0F, 7.0F/16.0F, 9.0F/16.0F, 12.0F/16.0F, 
							cable.connector, cable.connector.getMinU(), cable.connector.getMaxU(), cable.connector.getMinV(), cable.connector.getMaxV());
					FemtocraftRenderUtils.drawNorthFace(x, y, z, 7.0F/16.0F, 9.0F/16.0F, 11.0F/16.0F, 12.0F/16.0F, 7.0F/16.0F, 
							cable.connector, cable.connector.getMinU(), cable.connector.getMaxU() - (cable.connector.getMaxU() - cable.connector.getMinU())/2, cable.connector.getMinV(), cable.connector.getMaxV() - (cable.connector.getMaxV() - cable.connector.getMinV())/2);
					FemtocraftRenderUtils.drawEastFace(x, y, z, 11.0F/16.0F, 12.0F/16.0F, 7.0F/16.0F, 9.0F/16.0F, 9.0F/16.0F, 
							cable.connector, cable.connector.getMinU(), cable.connector.getMaxU() - (cable.connector.getMaxU() - cable.connector.getMinU())/2, cable.connector.getMinV(), cable.connector.getMaxV() - (cable.connector.getMaxV() - cable.connector.getMinV())/2);
					FemtocraftRenderUtils.drawSouthFace(x, y, z, 7.0F/16.0F, 9.0F/16.0F, 11.0F/16.0F, 12.0F/16.0F, 9.0F/16.0F, 
							cable.connector, cable.connector.getMinU(), cable.connector.getMaxU() - (cable.connector.getMaxU() - cable.connector.getMinU())/2, cable.connector.getMinV(), cable.connector.getMaxV() - (cable.connector.getMaxV() - cable.connector.getMinV())/2);
					FemtocraftRenderUtils.drawWestFace(x, y, z, 11.0F/16.0F, 12.0F/16.0F, 7.0F/16.0F, 9.0F/16.0F, 7.0F/16.0F, 
							cable.connector, cable.connector.getMinU(), cable.connector.getMaxU() - (cable.connector.getMaxU() - cable.connector.getMinU())/2, cable.connector.getMinV(), cable.connector.getMaxV() - (cable.connector.getMaxV() - cable.connector.getMinV())/2);
					break;
				case DOWN:
					FemtocraftRenderUtils.drawBottomFace(x, y, z, 7.0F/16.0F, 9.0F/16.0F, 7.0F/16.0F, 9.0F/16.0F, 4.0F/16.0F, 
							cable.connector, cable.connector.getMinU(), cable.connector.getMaxU(), cable.connector.getMinV(), cable.connector.getMaxV());
					FemtocraftRenderUtils.drawNorthFace(x, y, z, 7.0F/16.0F, 9.0F/16.0F, 4.0F/16.0F, 5.0F/16.0F, 7.0F/16.0F, 
							cable.connector, cable.connector.getMinU(), cable.connector.getMaxU() - (cable.connector.getMaxU() - cable.connector.getMinU())/2, cable.connector.getMinV(), cable.connector.getMaxV() - (cable.connector.getMaxV() - cable.connector.getMinV())/2);
					FemtocraftRenderUtils.drawEastFace(x, y, z, 4.0F/16.0F, 5.0F/16.0F, 7.0F/16.0F, 9.0F/16.0F, 9.0F/16.0F, 
							cable.connector, cable.connector.getMinU(), cable.connector.getMaxU() - (cable.connector.getMaxU() - cable.connector.getMinU())/2, cable.connector.getMinV(), cable.connector.getMaxV() - (cable.connector.getMaxV() - cable.connector.getMinV())/2);
					FemtocraftRenderUtils.drawSouthFace(x, y, z, 7.0F/16.0F, 9.0F/16.0F, 4.0F/16.0F, 5.0F/16.0F, 9.0F/16.0F, 
							cable.connector, cable.connector.getMinU(), cable.connector.getMaxU() - (cable.connector.getMaxU() - cable.connector.getMinU())/2, cable.connector.getMinV(), cable.connector.getMaxV() - (cable.connector.getMaxV() - cable.connector.getMinV())/2);
					FemtocraftRenderUtils.drawWestFace(x, y, z, 4.0F/16.0F, 5.0F/16.0F, 7.0F/16.0F, 9.0F/16.0F, 7.0F/16.0F, 
							cable.connector, cable.connector.getMinU(), cable.connector.getMaxU() - (cable.connector.getMaxU() - cable.connector.getMinU())/2, cable.connector.getMinV(), cable.connector.getMaxV() - (cable.connector.getMaxV() - cable.connector.getMinV())/2);
					break;
				case NORTH:
					break;
				case EAST:
					break;
				case SOUTH:
					break;
				case WEST:
					break;
			default:
				break;
			}
	}

}
