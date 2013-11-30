package femtocraft.industry.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import femtocraft.industry.blocks.VacuumTube;
import femtocraft.power.FemtopowerCable;
import femtocraft.proxy.ClientProxyFemtocraft;
import femtocraft.render.FemtocraftRenderUtils;
import femtocraft.render.Model;
import femtocraft.render.Point;
import femtocraft.render.Quad;

public class VacuumTubeRenderer implements ISimpleBlockRenderingHandler {
	//Full Outside Models
	private Model outNorth, inNorth;
	private Model outSouth, inSouth;
	private Model outEast, inEast;
	private Model outWest, inWest;
	private Model outUp, inUp;
	private Model outDown, inDown;
	//Indicators
	private Model outNorthFarOn, outNorthFarOff, outNorthCloseOn, outNorthCloseOff;
	private Model inNorthFarOn, inNorthFarOff, inNorthFarOverflow, inNorthCloseOn, inNorthCloseOff;
	private Model outSouthFarOn, outSouthFarOff, outSouthCloseOn, outSouthCloseOff;
	private Model inSouthFarOn, inSouthFarOff, inSouthFarOverflow, inSouthCloseOn, inSouthCloseOff;
	private Model outUpFarOn, outUpFarOff, outUpCloseOn, outUpCloseOff;
	private Model inUpFarOn, inUpFarOff, inUpFarOverflow, inUpCloseOn, inUpCloseOff;
	private Model outDownFarOn, outDownFarOff, outDownCloseOn, outDownCloseOff;
	private Model inDownFarOn, inDownFarOff, inDownFarOverflow, inDownCloseOn, inDownCloseOff;
	private Model outEastFarOn, outEastFarOff, outEastCloseOn, outEastCloseOff;
	private Model inEastFarOn, inEastFarOff, inEastFarOverflow, inEastCloseOn, inEastCloseOff;
	private Model outWestFarOn, outWestFarOff, outWestCloseOn, outWestCloseOff;
	private Model inWestFarOn, inWestFarOff, inWestFarOverflow, inWestCloseOn, inWestCloseOff;
	
	//Center Models
	//Straight
	private Model centerStraightSouthToNorth, centerStraightNorthToSouth;
	private Model centerStraightEastToWest, centerStraightWestToEast;
	private Model centerStraightDownToUp, centerStraightUpToDown;
	//Ends
	private Model centerEndNorth, centerEndSouth, centerEndEast, centerEndWest, centerEndUp, centerEndDown;
	//Curved
	private Model centerCurvedNorthToUp, centerCurvedNorthToDown, centerCurvedNorthToEast, centerCurvedNorthToWest;
	private Model centerCurvedSouthToUp, centerCurvedSouthToDown, centerCurvedSouthToEast, centerCurvedSouthToWest;
	private Model centerCurvedUpToNorth, centerCurvedUpToSouth, centerCurvedUpToEast, centerCurvedUpToWest;
	private Model centerCurvedDownToNorth, centerCurvedDownToSouth, centerCurvedDownToEast, centerCurvedDownToWest;
	private Model centerCurvedEastToNorth, centerCurvedEastToSouth, centerCurvedEastToUp, centerCurvedEastToDown;
	private Model centerCurvedWestToNorth, centerCurvedWestToSouth, centerCurvedWestToUp, centerCurvedWestToDown;
	
	boolean initialized = false;

	public VacuumTubeRenderer() {
		
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		VacuumTube tube = (VacuumTube)block;
		if(tube == null) return;
		
		Tessellator tessellator = Tessellator.instance;
		
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		tessellator.startDrawingQuads();
		renderTube(tube, 0, 0, 0, new boolean[]{false, false, false, false}, false, ForgeDirection.EAST, ForgeDirection.WEST);
		tessellator.draw();
		
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return ClientProxyFemtocraft.FemtocraftVacuumTubeRenderID;
	}


	private void renderTube(VacuumTube tube, int x, int y, int z, boolean[] hasItem, boolean isOverflowing,
			ForgeDirection in, ForgeDirection out) {
		if(!initialized)
			initializeModels(tube);
		
		renderIn(x,y,z,in,hasItem,isOverflowing);
		renderOut(x,y,z,out,hasItem);
		renderCenter(x,y,z,in,out,hasItem);
	}

	private void renderIn(int x, int y, int z, ForgeDirection in,
			boolean[] hasItem, boolean isOverflowing) {
		switch(in)
		{
		case NORTH:
			inNorth.location = new Point(x,y,z);
			inNorth.draw();
			
			if(hasItem[0])
			{
				inNorthCloseOn.location = new Point(x,y,z);
				inNorthCloseOn.draw();
			}
			else {
				inNorthCloseOff.location = new Point(x,y,z);
				inNorthCloseOff.draw();
			}
			
			if(isOverflowing)
			{
				inNorthFarOverflow.location = new Point(x,y,z);
				inNorthFarOverflow.draw();
			}
			else if(hasItem[0])
			{
				inNorthFarOn.location = new Point(x,y,z);
				inNorthFarOn.draw();
			}
			else
			{
				inNorthFarOff.location = new Point(x,y,z);
				inNorthFarOff.draw();
			}
			
			break;
		case SOUTH:
			inSouth.location = new Point(x,y,z);
			inSouth.draw();
			
			if(hasItem[0])
			{
				inSouthCloseOn.location = new Point(x,y,z);
				inSouthCloseOn.draw();
			}
			else {
				inSouthCloseOff.location = new Point(x,y,z);
				inSouthCloseOff.draw();
			}
			
			if(isOverflowing)
			{
				inSouthFarOverflow.location = new Point(x,y,z);
				inSouthFarOverflow.draw();
			}
			else if(hasItem[0])
			{
				inSouthFarOn.location = new Point(x,y,z);
				inSouthFarOn.draw();
			}
			else
			{
				inSouthFarOff.location = new Point(x,y,z);
				inSouthFarOff.draw();
			}
			break;
		case EAST:
			inEast.location = new Point(x,y,z);
			inEast.draw();
			
			if(hasItem[0])
			{
				inEastCloseOn.location = new Point(x,y,z);
				inEastCloseOn.draw();
			}
			else {
				inEastCloseOff.location = new Point(x,y,z);
				inEastCloseOff.draw();
			}
			
			if(isOverflowing)
			{
				inEastFarOverflow.location = new Point(x,y,z);
				inEastFarOverflow.draw();
			}
			else if(hasItem[0])
			{
				inEastFarOn.location = new Point(x,y,z);
				inEastFarOn.draw();
			}
			else
			{
				inEastFarOff.location = new Point(x,y,z);
				inEastFarOff.draw();
			}
			break;
		case WEST:
			inWest.location = new Point(x,y,z);
			inWest.draw();
			
			if(hasItem[0])
			{
				inWestCloseOn.location = new Point(x,y,z);
				inWestCloseOn.draw();
			}
			else {
				inWestCloseOff.location = new Point(x,y,z);
				inWestCloseOff.draw();
			}
			
			if(isOverflowing)
			{
				inWestFarOverflow.location = new Point(x,y,z);
				inWestFarOverflow.draw();
			}
			else if(hasItem[0])
			{
				inWestFarOn.location = new Point(x,y,z);
				inWestFarOn.draw();
			}
			else
			{
				inWestFarOff.location = new Point(x,y,z);
				inWestFarOff.draw();
			}
			break;
		case UP:
			inUp.location = new Point(x,y,z);
			inUp.draw();
			
			if(hasItem[0])
			{
				inUpCloseOn.location = new Point(x,y,z);
				inUpCloseOn.draw();
			}
			else {
				inUpCloseOff.location = new Point(x,y,z);
				inUpCloseOff.draw();
			}
			
			if(isOverflowing)
			{
				inUpFarOverflow.location = new Point(x,y,z);
				inUpFarOverflow.draw();
			}
			else if(hasItem[0])
			{
				inUpFarOn.location = new Point(x,y,z);
				inUpFarOn.draw();
			}
			else
			{
				inUpFarOff.location = new Point(x,y,z);
				inUpFarOff.draw();
			}
			break;
		case DOWN:
			inDown.location = new Point(x,y,z);
			inDown.draw();
			
			if(hasItem[0])
			{
				inDownCloseOn.location = new Point(x,y,z);
				inDownCloseOn.draw();
			}
			else {
				inDownCloseOff.location = new Point(x,y,z);
				inDownCloseOff.draw();
			}
			
			if(isOverflowing)
			{
				inDownFarOverflow.location = new Point(x,y,z);
				inDownFarOverflow.draw();
			}
			else if(hasItem[0])
			{
				inDownFarOn.location = new Point(x,y,z);
				inDownFarOn.draw();
			}
			else
			{
				inDownFarOff.location = new Point(x,y,z);
				inDownFarOff.draw();
			}
			break;
		case UNKNOWN:
			break;
		default:
			break;
		}
	}

	private void renderOut(int x, int y, int z, ForgeDirection out,
			boolean[] hasItem) {
		switch(out)
		{
		case NORTH:
			outNorth.location = new Point(x,y,z);
			outNorth.draw();
			
			if(hasItem[2])
			{
				outNorthCloseOn.location = new Point(x,y,z);
				outNorthCloseOn.draw();
			}
			else {
				outNorthCloseOff.location = new Point(x,y,z);
				outNorthCloseOff.draw();
			}
			
			if(hasItem[3])
			{
				outNorthFarOn.location = new Point(x,y,z);
				outNorthFarOn.draw();
			}
			else {
				outNorthFarOff.location = new Point(x,y,z);
				outNorthFarOff.draw();
			}
			
			break;
		case SOUTH:
			outSouth.location = new Point(x,y,z);
			outSouth.draw();
			
			if(hasItem[2])
			{
				outSouthCloseOn.location = new Point(x,y,z);
				outSouthCloseOn.draw();
			}
			else {
				outSouthCloseOff.location = new Point(x,y,z);
				outSouthCloseOff.draw();
			}
			
			if(hasItem[3])
			{
				outSouthFarOn.location = new Point(x,y,z);
				outSouthFarOn.draw();
			}
			else {
				outSouthFarOff.location = new Point(x,y,z);
				outSouthFarOff.draw();
			}
			break;
		case EAST:
			outEast.location = new Point(x,y,z);
			outEast.draw();
			
			if(hasItem[2])
			{
				outEastCloseOn.location = new Point(x,y,z);
				outEastCloseOn.draw();
			}
			else {
				outEastCloseOff.location = new Point(x,y,z);
				outEastCloseOff.draw();
			}
			
			if(hasItem[3])
			{
				outEastFarOn.location = new Point(x,y,z);
				outEastFarOn.draw();
			}
			else {
				outEastFarOff.location = new Point(x,y,z);
				outEastFarOff.draw();
			}
			break;
		case WEST:
			outWest.location = new Point(x,y,z);
			outWest.draw();
			
			if(hasItem[2])
			{
				outWestCloseOn.location = new Point(x,y,z);
				outWestCloseOn.draw();
			}
			else {
				outWestCloseOff.location = new Point(x,y,z);
				outWestCloseOff.draw();
			}
			
			if(hasItem[3])
			{
				outWestFarOn.location = new Point(x,y,z);
				outWestFarOn.draw();
			}
			else {
				outWestFarOff.location = new Point(x,y,z);
				outWestFarOff.draw();
			}
			break;
		case UP:
			outUp.location = new Point(x,y,z);
			outUp.draw();
			
			if(hasItem[2])
			{
				outUpCloseOn.location = new Point(x,y,z);
				outUpCloseOn.draw();
			}
			else {
				outUpCloseOff.location = new Point(x,y,z);
				outUpCloseOff.draw();
			}
			
			if(hasItem[3])
			{
				outUpFarOn.location = new Point(x,y,z);
				outUpFarOn.draw();
			}
			else {
				outUpFarOff.location = new Point(x,y,z);
				outUpFarOff.draw();
			}
			break;
		case DOWN:
			outDown.location = new Point(x,y,z);
			outDown.draw();
			
			if(hasItem[2])
			{
				outDownCloseOn.location = new Point(x,y,z);
				outDownCloseOn.draw();
			}
			else {
				outDownCloseOff.location = new Point(x,y,z);
				outDownCloseOff.draw();
			}
			
			if(hasItem[3])
			{
				outDownFarOn.location = new Point(x,y,z);
				outDownFarOn.draw();
			}
			else {
				outDownFarOff.location = new Point(x,y,z);
				outDownFarOff.draw();
			}
			break;
		case UNKNOWN:
			break;
		default:
			break;
		}
	}
	
	private void renderCenter(int x, int y, int z, ForgeDirection in,
			ForgeDirection out, boolean[] hasItem) {	
		
	}

	private void initializeModels(VacuumTube tube) {
		
		
		
		initialized = true;
	}
	
	private void createEnds(VacuumTube tube)
	{
		
	}
	
	private Model createEnd(VacuumTube tube)
	{
		Model ret = new Model(new Point(0,0,0), new Point(0.5f,0.5f,0.5f));
		
		float minV = 4.f/16.f;
		float maxV = 12.f/16.f;
		
		
		
		return ret;
	}
	
	private void createCenterEnds(VacuumTube tube)
	{
		centerEndNorth = createCenterEnd(tube);
		centerEndSouth = centerEndNorth.rotatedOnYAxis(Math.PI);
		centerEndUp = centerEndNorth.rotatedOnXAxis(Math.PI/2.d);
		centerEndDown = centerEndNorth.rotatedOnXAxis(-Math.PI/2.d);
		centerEndWest = centerEndNorth.rotatedOnYAxis(-Math.PI/2.d);
		centerEndEast = centerEndNorth.rotatedOnYAxis(Math.PI/2.d);
	}
	
	private Model createCenterEnd(VacuumTube tube)
	{
		Model ret = new Model(new Point(0,0,0), new Point(0.5f, 0.5f, 0.5f));
		
		float min = 4.f/16.f;
		float max = 12.f/16.f;
		
		float minU = tube.endIcon.getMinU() + (tube.endIcon.getMinU() + tube.endIcon.getMaxU())* min;
		float maxU = tube.endIcon.getMinU() + (tube.endIcon.getMinU() + tube.endIcon.getMaxU())* max;
		float minV = tube.endIcon.getMinV() + (tube.endIcon.getMinV() + tube.endIcon.getMaxV())* min;
		float maxV = tube.endIcon.getMinV() + (tube.endIcon.getMinV() + tube.endIcon.getMaxV())* max;
		
		Quad end = FemtocraftRenderUtils.makeNorthFace(min, max, min, max, 4.f/16.f, tube.endIcon, minU, maxU, minV, maxV);
		Quad inset = FemtocraftRenderUtils.makeNorthFace(min, max, min, max, 5.f/16.f, tube.endInsetIcon, minU, maxU, minV, maxV);
		
		ret.addQuad(end);
		ret.addQuad(inset);
		
		return ret;
	}
	
}
