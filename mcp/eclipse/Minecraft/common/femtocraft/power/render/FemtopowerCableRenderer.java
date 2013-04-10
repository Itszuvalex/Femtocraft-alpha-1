/**
 * 
 */
package femtocraft.power.render;


import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
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
		FemtopowerCable cable = (FemtopowerCable)block;
		if(block == null) return;
		
		renderCable(cable, -.5F, -.5F, -.5F, renderer, new boolean[]{false, false, true, false, false, true});
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		FemtopowerCable cable = (FemtopowerCable)block;
		if(block == null) return false;
		
		FemtopowerCableTile cableTile = (FemtopowerCableTile)renderer.blockAccess.getBlockTileEntity(x, y, z);
		if(cableTile == null) return false;
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(cable.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
		
		return renderCable(cable, x, y, z, renderer, cableTile.connections);
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return ClientProxyFemtocraft.FemtopowerCableRenderID;
	}
	
	private boolean renderCable(FemtopowerCable cable, float x, float y, float z, RenderBlocks renderer, boolean[] connections) {
		
//		//Render border
//		renderer.setOverrideBlockTexture(cable.coreBorder);
//		block.setBlockBounds(4.F/16.0F, 4.F/16.0F, 4.F/16.0F, 12.F/16.0F, 12.F/16.0F, 12.F/16.F);
//		renderer.setRenderBoundsFromBlock(block);
//		renderer.renderStandardBlock(block, x, y, z);
//		renderer.clearOverrideBlockTexture();
//		
//		block.setBlockBounds(5.F/16.0F, 5.F/16.0F, 5.F/16.0F, 11.F/16.0F, 11.F/16.0F, 11.F/16.F);
//		renderer.setRenderBoundsFromBlock(block);
//		renderer.renderStandardBlock(block, x, y, z);
//		
//		renderer.clearOverrideBlockTexture();
//		
//		renderer.
//		
//		cable.setBlockBounds();
		

		//tessellator.setBrightness((int) (renderer.blockAccess.getLightBrightness(x, y, z) * 100));
		
		drawCore(cable, x, y, z, renderer, connections);
		
		return true;
	}
	
	private void drawCore(FemtopowerCable cable, float x, float y, float z, RenderBlocks renderer, boolean[] connections) {
		if(!connectedAcross(connections))
			drawCoreBlock(cable, x, y, z, renderer, connections);
		else {
			if(connections[0]) {
				drawCoil(cable, x, y, z, 2.0F/16.0F, renderer, ForgeDirection.getOrientation(0));
				drawCoil(cable, x, y, z, 2.0F/16.0F, renderer, ForgeDirection.getOrientation(0).getOpposite());
			}
			else if (connections [2]) {
				drawCoil(cable, x, y, z, 2.0F/16.0F, renderer, ForgeDirection.getOrientation(2));
				drawCoil(cable, x, y, z, 2.0F/16.0F, renderer, ForgeDirection.getOrientation(2).getOpposite());
			}
			else if (connections [4]) {
				drawCoil(cable, x, y, z, 2.0F/16.0F, renderer, ForgeDirection.getOrientation(4));
				drawCoil(cable, x, y, z, 2.0F/16.0F, renderer, ForgeDirection.getOrientation(4).getOpposite());
			}
		}
		
		//Draw connecting tubes
		for(int i = 0; i < 6; i++) {
			if(connections[i]) {
				//Draw coil and connectors
				drawCoil(cable, x, y, z, 6.0F/16.0F, renderer, ForgeDirection.getOrientation(i));
			}
		}
	}

	private void drawCoreBlock(FemtopowerCable cable, float x, float y,
			float z, RenderBlocks renderer, boolean[] connections) {
		FemtocraftRenderUtils.renderCube(x, y, z, 5.0f/16.0f, 5.0f/16.0f, 5.0f/16.0f, 11.0f/16.0f, 11.0f/16.0f, 11.0f/16.0f, cable.coil);
		FemtocraftRenderUtils.renderCube(x, y, z, 4.0f/16.0f, 4.0f/16.0f, 4.0f/16.0f, 12.0f/16.0f, 12.0f/16.0f, 12.0f/16.0f, cable.coreBorder);

		//Draw connector caps
		drawConnector(cable, x, y, z, 3.0F/16.0F, renderer, ForgeDirection.UP, !connections[1]);
		drawConnector(cable, x, y, z, 3.0F/16.0F, renderer, ForgeDirection.DOWN, !connections[0]);
		drawConnector(cable, x, y, z, 3.0F/16.0F, renderer, ForgeDirection.NORTH, !connections[2]);
		drawConnector(cable, x, y, z, 3.0F/16.0F, renderer, ForgeDirection.EAST, !connections[5]);
		drawConnector(cable, x, y, z, 3.0F/16.0F, renderer, ForgeDirection.SOUTH, !connections[3]);
		drawConnector(cable, x, y, z, 3.0F/16.0F, renderer, ForgeDirection.WEST, !connections[4]);
	}
	
	private void drawConnector(FemtopowerCable cable, float x, float y, float z, float offset, RenderBlocks renderer, ForgeDirection direction, boolean drawCap) {
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
	
	private void drawCoil(FemtopowerCable cable, float x, float y, float z, float offset, RenderBlocks renderer, ForgeDirection direction) {
		ForgeDirection rotaxi = ForgeDirection.UNKNOWN;
		double yrot = Math.PI/2.0D;
		double xrot = 0;
		
		switch(direction) {
			case UP:
				rotaxi = ForgeDirection.EAST;
				xrot += Math.PI/2.0D;
				break;
			case DOWN:
				rotaxi = ForgeDirection.WEST;
				xrot -= Math.PI/2.0D;
				break;
			case NORTH:
				rotaxi = ForgeDirection.UP;
				break;
			case EAST:
				rotaxi = ForgeDirection.DOWN;
				yrot += Math.PI/2.0D;
				break;
			case SOUTH:
				rotaxi = ForgeDirection.DOWN;
				yrot += Math.PI;
				break;
			case WEST:
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

		drawConnector(cable, x, y, z, 1.0F/16.0F + offset, renderer, direction, true);
		drawConnector(cable, x, y, z, -1.0F/16.0F + offset, renderer, direction, false);
		
		offset = .5F - offset;
		
		//West-face, AA - top left, AB - bot left, AC - bot right, AD - top right
		Point AA = new Point(4.0F/16.0F, 12.0F/16.0F, -2.0F/16.0F + offset);
		Point AB = new Point(4.0F/16.0F, 4.0F/16.0F, -2.0F/16.0F + offset);
		Point AC = new Point(4.0F/16.0F, 4.0F/16.0F, 2.0F/16.0F + offset);
		Point AD = new Point(4.0F/16.0F, 12.0F/16.0F, 2.0F/16.0F + offset);
		//East-face, BA - top left, BB - bottom left, BC - bottom right, BD - top right
		Point BA = new Point(12.0F/16.0F, 12.0F/16.0F, 2.0F/16.0F + offset);
		Point BB = new Point(12.0F/16.0F, 4.0F/16.0F, 2.0F/16.0F + offset);
		Point BC = new Point(12.0F/16.0F, 4.0F/16.0F, -2.0F/16.0F + offset);
		Point BD = new Point(12.0F/16.0F, 12.0F/16.0F, -2.0F/16.0F + offset);
		
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
		
		//Draw borders
		
		//West
		FemtocraftRenderUtils.drawFaceByPoints(x, y, z, rotAD, rotAC, rotAB, rotAA, cable.border, cable.border.getMinU(), cable.border.getMaxU() - 2.0F *(cable.border.getMaxU() - cable.border.getMinU())/3.0F, cable.border.getMinV(), cable.border.getMaxV());
		//East
		FemtocraftRenderUtils.drawFaceByPoints(x, y, z, rotBD, rotBC, rotBB, rotBA, cable.border, cable.border.getMinU(), cable.border.getMaxU() - 2.0F *(cable.border.getMaxU() - cable.border.getMinU())/3.0F, cable.border.getMinV(), cable.border.getMaxV());
		//Top
		FemtocraftRenderUtils.drawFaceByPoints(x, y, z, rotBA, rotAD, rotAA, rotBD, cable.border, cable.border.getMinU(), cable.border.getMaxU() - 2.0F *(cable.border.getMaxU() - cable.border.getMinU())/3.0F, cable.border.getMinV(), cable.border.getMaxV());
		//Bottom
		FemtocraftRenderUtils.drawFaceByPoints(x, y, z, rotBC, rotAB, rotAC, rotBB, cable.border, cable.border.getMinU(), cable.border.getMaxU() - 2.0F *(cable.border.getMaxU() - cable.border.getMinU())/3.0F, cable.border.getMinV(), cable.border.getMaxV());
		
		//Draw coil
		
		//West-face, AA - top left, AB - bot left, AC - bot right, AD - top right
		AA = new Point(5.0F/16.0F, 11.0F/16.0F, -1.0F/16.0F + offset);
		AB = new Point(5.0F/16.0F, 5.0F/16.0F, -1.0F/16.0F + offset);
		AC = new Point(5.0F/16.0F, 5.0F/16.0F, 1.0F/16.0F + offset);
		AD = new Point(5.0F/16.0F, 11.0F/16.0F, 1.0F/16.0F + offset);
		//East-face, BA - top left, BB - bottom left, BC - bottom right, BD - top right
		BA = new Point(11.0F/16.0F, 11.0F/16.0F, 1.0F/16.0F + offset);
		BB = new Point(11.0F/16.0F, 5.0F/16.0F, 1.0F/16.0F + offset);
		BC = new Point(11.0F/16.0F, 5.0F/16.0F, -1.0F/16.0F + offset);
		BD = new Point(11.0F/16.0F, 11.0F/16.0F, -1.0F/16.0F + offset);
		
		//West-face, AA - top left, AB - bot left, AC - bot right, AD - top right
		rotAA = AA.rotateOnYAxis(yrot, .5F, .5F).rotateOnXAxis(xrot, .5F, .5F);
		rotAB = AB.rotateOnYAxis(yrot, .5F, .5F).rotateOnXAxis(xrot, .5F, .5F);
		rotAC = AC.rotateOnYAxis(yrot, .5F, .5F).rotateOnXAxis(xrot, .5F, .5F);
		rotAD = AD.rotateOnYAxis(yrot, .5F, .5F).rotateOnXAxis(xrot, .5F, .5F);
		//East-face, BA - top left, BB - bottom left, BC - bottom right, BD - top right
		rotBA = BA.rotateOnYAxis(yrot, .5F, .5F).rotateOnXAxis(xrot, .5F, .5F);
		rotBB = BB.rotateOnYAxis(yrot, .5F, .5F).rotateOnXAxis(xrot, .5F, .5F);
		rotBC = BC.rotateOnYAxis(yrot, .5F, .5F).rotateOnXAxis(xrot, .5F, .5F);
		rotBD = BD.rotateOnYAxis(yrot, .5F, .5F).rotateOnXAxis(xrot, .5F, .5F);
		
		//West
		FemtocraftRenderUtils.drawFaceByPoints(x, y, z, rotAD, rotAC, rotAB, rotAA, cable.coilEdge, cable.coilEdge.getMinU(), cable.coilEdge.getMaxU() - 2.0F *(cable.coilEdge.getMaxU() - cable.coilEdge.getMinU())/3.0F, cable.coilEdge.getMinV(), cable.coilEdge.getMaxV());
		//East
		FemtocraftRenderUtils.drawFaceByPoints(x, y, z, rotBD, rotBC, rotBB, rotBA, cable.coilEdge, cable.coilEdge.getMinU(), cable.coilEdge.getMaxU() - 2.0F *(cable.coilEdge.getMaxU() - cable.coilEdge.getMinU())/3.0F, cable.coilEdge.getMinV(), cable.coilEdge.getMaxV());
		//Top
		FemtocraftRenderUtils.drawFaceByPoints(x, y, z, rotBA, rotAD, rotAA, rotBD, cable.coilEdge, cable.coilEdge.getMinU(), cable.coilEdge.getMaxU() - 2.0F *(cable.coilEdge.getMaxU() - cable.coilEdge.getMinU())/3.0F, cable.coilEdge.getMinV(), cable.coilEdge.getMaxV());
		//Bottom
		FemtocraftRenderUtils.drawFaceByPoints(x, y, z, rotBC, rotAB, rotAC, rotBB, cable.coilEdge, cable.coilEdge.getMinU(), cable.coilEdge.getMaxU() - 2.0F *(cable.coilEdge.getMaxU() - cable.coilEdge.getMinU())/3.0F, cable.coilEdge.getMinV(), cable.coilEdge.getMaxV());
		
		//North
		FemtocraftRenderUtils.drawFaceByPoints(x, y, z, rotBB, rotAC, rotAD, rotBA, cable.coil, cable.coil.getMinU(), cable.coil.getMaxU(), cable.coil.getMinV(), cable.coil.getMaxV());
		//South
		FemtocraftRenderUtils.drawFaceByPoints(x, y, z, rotAA, rotAB, rotBC, rotBD, cable.coil, cable.coil.getMinU(), cable.coil.getMaxU(), cable.coil.getMinV(), cable.coil.getMaxV());
	}

	
	private boolean connectedAcross(boolean[] connections) {
		if(numConnections(connections) == 2) {
			if(connections[0] == true && connections[1] == true) return true;
			if(connections[2] == true && connections[3] == true) return true;
			if(connections[4] == true && connections[5] == true) return true;
		}
		return false;
	}
	
	private int numConnections(boolean[] connections) {
		int count = 0;
		for(int i =0; i < 6; i++) if(connections[i]) ++count;
		return count;
	}
}
