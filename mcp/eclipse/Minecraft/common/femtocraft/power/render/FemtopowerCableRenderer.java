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
import femtocraft.Point;
import femtocraft.power.FemtopowerCable;
import femtocraft.power.TileEntity.FemtopowerCableTile;
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
	//	renderCable(block, 0, 0, 0, renderer);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
			return renderCable(block, x, y, z, renderer);

	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return ClientProxyFemtocraft.FemtopowerCableRenderID;
	}
	
	private boolean renderCable(Block block, int x, int y, int z, RenderBlocks renderer) {
		FemtopowerCable cable = (FemtopowerCable)block;
		if(block == null) return false;
		
		//Render border
		renderer.setOverrideBlockTexture(cable.coreBorder);
		block.setBlockBounds(4.F/16.0F, 4.F/16.0F, 4.F/16.0F, 12.F/16.0F, 12.F/16.0F, 12.F/16.F);
		renderer.setRenderBoundsFromBlock(block);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.clearOverrideBlockTexture();
		
		block.setBlockBounds(5.F/16.0F, 5.F/16.0F, 5.F/16.0F, 11.F/16.0F, 11.F/16.0F, 11.F/16.F);
		renderer.setRenderBoundsFromBlock(block);
		renderer.renderStandardBlock(block, x, y, z);
		
		renderer.clearOverrideBlockTexture();
		
		cable.setBlockBounds();
		
		//Render core
		
//		drawCore(cable, x, y, z, renderer);
		
		
		return true;
	}
	
	private void drawCore(FemtopowerCable cable, int x, int y, int z, RenderBlocks renderer) {
		FemtopowerCableTile cableTile = (FemtopowerCableTile)renderer.blockAccess.getBlockTileEntity(x, y, z);
		if(cableTile == null) return;
		
		FemtocraftRenderUtils.renderCube(x, y, z, 5.0f/16.0f, 5.0f/16.0f, 5.0f/16.0f, 11.0f/16.0f, 11.0f/16.0f, 11.0f/16.0f, cable.coil);
		FemtocraftRenderUtils.renderCube(x, y, z, 4.0f/16.0f, 4.0f/16.0f, 4.0f/16.0f, 12.0f/16.0f, 12.0f/16.0f, 12.0f/16.0f, cable.coreBorder);

		//Draw connector caps
		drawConnector(cable, x, y, z, 3.0F/16.0F, renderer, ForgeDirection.UP, !cableTile.connections[1]);
		drawConnector(cable, x, y, z, 3.0F/16.0F, renderer, ForgeDirection.DOWN, !cableTile.connections[0]);
		drawConnector(cable, x, y, z, 3.0F/16.0F, renderer, ForgeDirection.NORTH, !cableTile.connections[2]);
		drawConnector(cable, x, y, z, 3.0F/16.0F, renderer, ForgeDirection.EAST, !cableTile.connections[5]);
		drawConnector(cable, x, y, z, 3.0F/16.0F, renderer, ForgeDirection.SOUTH, !cableTile.connections[3]);
		drawConnector(cable, x, y, z, 3.0F/16.0F, renderer, ForgeDirection.WEST, !cableTile.connections[4]);
		
		for(int i = 0; i < 6; i++) {
			if(cableTile.connections[i]) {
				drawCoil(cable, x, y, z, 6.0F/16.0F, renderer, ForgeDirection.getOrientation(i));
			}
		}
	}
	
	private void drawConnector(FemtopowerCable cable, int x, int y, int z, float offset, RenderBlocks renderer, ForgeDirection direction, boolean drawCap) {
		ForgeDirection rotaxi = ForgeDirection.UNKNOWN;
		float xoffset = 0;
		float yoffset = 0;
		float zoffset = 0;
		
		switch(direction) {
				case UP:
					rotaxi = ForgeDirection.EAST;
					xoffset = .5F;
					yoffset = .5F + offset;
					zoffset = .5F;
					break;
				case DOWN:
					rotaxi = ForgeDirection.WEST;
					xoffset = .5F;
					yoffset = .5F - offset;
					zoffset = .5F;
					break;
				case NORTH:
					xoffset = .5F;
					yoffset = .5F;
					zoffset = .5F - offset;
					rotaxi = ForgeDirection.UP;
					break;
				case EAST:
					xoffset = .5F + offset;
					yoffset = .5F;
					zoffset = .5F;
					rotaxi = ForgeDirection.DOWN;
					break;
				case SOUTH:
					xoffset = .5F;
					yoffset = .5F;
					zoffset = .5F + offset;
					rotaxi = ForgeDirection.DOWN;
					break;
				case WEST:
					xoffset = .5F - offset;
					yoffset = .5F;
					zoffset = .5F;
					rotaxi = ForgeDirection.UP;
					break;
			default:
				break;
			}
		ForgeDirection face1, face2, face3, face4;
		face1 = direction.getRotation(rotaxi);
		face2 = face1.getRotation(direction);
		face3 = face2.getRotation(direction);
		face4 = face3.getRotation(direction);
		
		
		if(drawCap) {
			FemtocraftRenderUtils.drawArbitraryFace(x, y, z, -1.0F/16.0F + xoffset, 1.0F/16.0F + xoffset, -1.0F/16.0F + yoffset, 1.0F/16.0F + yoffset, -1.0F/16.0F + zoffset, 1.0F/16.0F + zoffset, 
				direction, cable.connector, cable.connector.getMinU(), cable.connector.getMaxU(), cable.connector.getMinV(), cable.connector.getMaxV());
		}
		
		FemtocraftRenderUtils.drawArbitraryFace(x, y, z, -1.0F/16.0F + xoffset, 1.0F/16.0F + xoffset, -1.0F/16.0F + yoffset, 1.0F/16.0F + yoffset, -1.0F/16.0F + zoffset, 1.0F/16.0F + zoffset, 
				face1, cable.connector, cable.connector.getMinU(), cable.connector.getMaxU(), cable.connector.getMinV(), cable.connector.getMaxV());
		FemtocraftRenderUtils.drawArbitraryFace(x, y, z, -1.0F/16.0F + xoffset, 1.0F/16.0F + xoffset, -1.0F/16.0F + yoffset, 1.0F/16.0F + yoffset, -1.0F/16.0F + zoffset, 1.0F/16.0F + zoffset, 
				face2, cable.connector, cable.connector.getMinU(), cable.connector.getMaxU(), cable.connector.getMinV(), cable.connector.getMaxV());
		FemtocraftRenderUtils.drawArbitraryFace(x, y, z, -1.0F/16.0F + xoffset, 1.0F/16.0F + xoffset, -1.0F/16.0F + yoffset, 1.0F/16.0F + yoffset, -1.0F/16.0F + zoffset, 1.0F/16.0F + zoffset, 
				face3, cable.connector, cable.connector.getMinU(), cable.connector.getMaxU(), cable.connector.getMinV(), cable.connector.getMaxV());
		FemtocraftRenderUtils.drawArbitraryFace(x, y, z, -1.0F/16.0F + xoffset, 1.0F/16.0F + xoffset, -1.0F/16.0F + yoffset, 1.0F/16.0F + yoffset, -1.0F/16.0F + zoffset, 1.0F/16.0F + zoffset, 
				face4, cable.connector, cable.connector.getMinU(), cable.connector.getMaxU(), cable.connector.getMinV(), cable.connector.getMaxV());	
	}
	
	private void drawCoil(FemtopowerCable cable, int x, int y, int z, float offset, RenderBlocks renderer, ForgeDirection direction) {
		ForgeDirection rotaxi = ForgeDirection.UNKNOWN;
		float xoffset = 0;
		float yoffset = 0;
		float zoffset = 0;
		double yrot = Math.PI/2.0D;
		double xrot = 0;
		
		switch(direction) {
			case UP:
				rotaxi = ForgeDirection.EAST;
				xoffset = .5F;
				yoffset = .5F + offset;
				zoffset = .5F;
				xrot += Math.PI/2.0D;
				break;
			case DOWN:
				rotaxi = ForgeDirection.WEST;
				xoffset = .5F;
				yoffset = .5F - offset;
				zoffset = .5F;
				xrot -= Math.PI/2.0D;
				break;
			case NORTH:
				xoffset = .5F;
				yoffset = .5F;
				zoffset = .5F - offset;
				rotaxi = ForgeDirection.UP;
				break;
			case EAST:
				xoffset = .5F + offset;
				yoffset = .5F;
				zoffset = .5F;
				rotaxi = ForgeDirection.DOWN;
				yrot += Math.PI/2.0D;
				break;
			case SOUTH:
				xoffset = .5F;
				yoffset = .5F;
				zoffset = .5F + offset;
				rotaxi = ForgeDirection.DOWN;
				yrot += Math.PI;
				break;
			case WEST:
				xoffset = .5F - offset;
				yoffset = .5F;
				zoffset = .5F;
				rotaxi = ForgeDirection.UP;
				yrot -= Math.PI/2.0D;
				break;
		default:
			break;
		}
		ForgeDirection face1, face2, face3, face4;
		face1 = direction.getRotation(rotaxi);
		face2 = face1.getRotation(direction);
		face3 = face2.getRotation(direction);
		face4 = face3.getRotation(direction);

		drawConnector(cable, x, y, z, 7.0F/16.0F, renderer, direction, true);
		drawConnector(cable, x, y, z, 5.0F/16.0F, renderer, direction, false);
		
		//West-face, AA - top left, AB - bot left, AC - bot right, AD - top right
		Point AA = new Point(4.0F/16.0F, 12.0F/16.0F, 0.0F/16.0F);
		Point AB = new Point(4.0F/16.0F, 4.0F/16.0F, 0.0F/16.0F);
		Point AC = new Point(4.0F/16.0F, 4.0F/16.0F, 4.0F/16.0F);
		Point AD = new Point(4.0F/16.0F, 12.0F/16.0F, 4.0F/16.0F);
		//East-face, BA - top left, BB - bottom left, BC - bottom right, BD - top right
		Point BA = new Point(12.0F/16.0F, 12.0F/16.0F, 4.0F/16.0F);
		Point BB = new Point(12.0F/16.0F, 4.0F/16.0F, 4.0F/16.0F);
		Point BC = new Point(12.0F/16.0F, 4.0F/16.0F, 0.0F/16.0F);
		Point BD = new Point(12.0F/16.0F, 12.0F/16.0F, 0.0F/16.0F);
		
		//West-face, AA - top left, AB - bot left, AC - bot right, AD - top right
		Point rotAA = AA.rotateOnYAxis(yrot, .5F, .5F).rotateOnXAxis(xrot, .5F, .5F);
		Point rotAB = AB.rotateOnYAxis(yrot, .5F, .5F).rotateOnXAxis(xrot, .5F, .5F);
		Point rotAC = AC.rotateOnYAxis(yrot, .5F, .5F).rotateOnXAxis(xrot, .5F, .5F);
		Point rotAD = AD.rotateOnYAxis(yrot, .5F, .5F).rotateOnXAxis(xrot, .5F, .5F);
		//East-face, BA - top left, BB - bottom left, BC - bottom right, BD - top right
		Point rotBA = BA.rotateOnYAxis(yrot, .5F, .5F).rotateOnXAxis(xrot, .5F, .5F);
		Point rotBB = BB.rotateOnYAxis(yrot, .5F, .5F).rotateOnXAxis(xrot, .5F, .5F);
		Point rotBC = BC.rotateOnYAxis(yrot, .5F, .5F).rotateOnXAxis(xrot, .5F, .5F);
		Point rotBD = BD.rotateOnYAxis(yrot, .5F, .5F).rotateOnXAxis(xrot, .5F, .5F);
		
		//West
		FemtocraftRenderUtils.drawFaceByPoints(x, y, z, rotAA, rotAB, rotAC, rotAD, cable.border, cable.border.getMinU(), cable.border.getMaxU() - 2.0F *(cable.border.getMaxU() - cable.border.getMinU())/3.0F, cable.border.getMinV(), cable.border.getMaxV());
		//East
		FemtocraftRenderUtils.drawFaceByPoints(x, y, z, rotBA, rotBB, rotBC, rotBD, cable.border, cable.border.getMinU(), cable.border.getMaxU() - 2.0F *(cable.border.getMaxU() - cable.border.getMinU())/3.0F, cable.border.getMinV(), cable.border.getMaxV());
		
		//Top
		FemtocraftRenderUtils.drawFaceByPoints(x, y, z, rotBD, rotAA, rotAD, rotBD, cable.border, cable.border.getMinU(), cable.border.getMaxU() - 2.0F *(cable.border.getMaxU() - cable.border.getMinU())/3.0F, cable.border.getMinV(), cable.border.getMaxV());
		//Bottom
		FemtocraftRenderUtils.drawFaceByPoints(x, y, z, rotBB, rotAC, rotAB, rotBC, cable.border, cable.border.getMinU(), cable.border.getMaxU() - 2.0F *(cable.border.getMaxU() - cable.border.getMinU())/3.0F, cable.border.getMinV(), cable.border.getMaxV());
	}

}
