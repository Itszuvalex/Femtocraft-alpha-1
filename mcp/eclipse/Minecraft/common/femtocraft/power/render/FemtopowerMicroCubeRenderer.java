package femtocraft.power.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import femtocraft.FemtocraftRenderUtils;
import femtocraft.power.FemtopowerMicroCube;
import femtocraft.power.TileEntity.FemtopowerMicroCubeTile;
import femtocraft.proxy.ClientProxyFemtocraft;

public class FemtopowerMicroCubeRenderer implements ISimpleBlockRenderingHandler {

	public FemtopowerMicroCubeRenderer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		FemtopowerMicroCube cube = (FemtopowerMicroCube)block;
		if(cube == null) return;
		
		Tessellator tessellator = Tessellator.instance;
		
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		tessellator.startDrawingQuads();
		renderCube(cube, 0, 0, 0, renderer, new boolean[]{false, false, false, false, false, false});
		tessellator.draw();
		
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		FemtopowerMicroCube cube = (FemtopowerMicroCube)block;
		if(cube == null) return false;
		
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile == null) return false;
		if(!(tile instanceof FemtopowerMicroCubeTile)) return false;
		FemtopowerMicroCubeTile cubeTile = (FemtopowerMicroCubeTile)tile;
		
		Tessellator tessellator = Tessellator.instance;
		//TODO : Find some actually working light function
		tessellator.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);
		
		renderCube(cube, x, y, z, renderer, cubeTile.outputs);
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return ClientProxyFemtocraft.FemtopowerMicroCubeRenderID;
	}
	
	private void renderCube(FemtopowerMicroCube cube, int x, int y, int z, RenderBlocks renderer, boolean[] outputs)
	{
		for(int i = 0; i < 6; i++)
		{
			renderFace(cube, x, y, z, renderer, outputs[i], ForgeDirection.getOrientation(i));
		}
	}
	
	private void renderFace(FemtopowerMicroCube cube, int x, int y, int z, RenderBlocks renderer, boolean output, ForgeDirection dir)
	{
		Icon outputIcon = output ? cube.outputSide : cube.inputSide;
		Icon frame = cube.side;
		
		float minU = frame.getMinU();
		float maxU = frame.getMaxU();
		float minV = frame.getMinV();
		float maxV = frame.getMaxV();
		
		//Draw frame
		switch(dir)
		{
			case UP:
			{
				FemtocraftRenderUtils.drawTopFace(x, y, z, 0, 1, 0, 1, 1, frame, minU, maxU, minV, maxV);
				FemtocraftRenderUtils.drawBottomFace(x, y, z, 0, 1, 0, 1, (13.f/16.f), frame, minU, maxU, minV, maxV);
			}
			case DOWN:
			{
				FemtocraftRenderUtils.drawBottomFace(x, y, z, 0, 1, 0, 1, 0, frame, minU, maxU, minV, maxV);
				FemtocraftRenderUtils.drawTopFace(x, y, z, 0, 1, 0, 1, (3.f/16.f), frame, minU, maxU, minV, maxV);
			}
			case NORTH:
			{
				FemtocraftRenderUtils.drawNorthFace(x, y, z, 0, 1, 0, 1, 0, frame, minU, maxU, minV, maxV);
				FemtocraftRenderUtils.drawSouthFace(x, y, z, 0, 1, 0, 1, 3.f/16.f, frame, minU, maxU, minV, maxV);
			}
			case EAST:
			{
				FemtocraftRenderUtils.drawEastFace(x, y, z, 0, 1, 0, 1, 1, frame, minU, maxU, minV, maxV);
				FemtocraftRenderUtils.drawWestFace(x, y, z, 0, 1, 0, 1, 13.f/16.f, frame, minU, maxU, minV, maxV);
			}
			case SOUTH:
			{
				FemtocraftRenderUtils.drawSouthFace(x, y, z, 0, 1, 0, 1, 1, frame, minU, maxU, minV, maxV);
				FemtocraftRenderUtils.drawNorthFace(x, y, z, 0, 1, 0, 1, 13.f/16.f, frame, minU, maxU, minV, maxV);
			}
			case WEST:
			{
				FemtocraftRenderUtils.drawWestFace(x, y, z, 0, 1, 0, 1, 0, frame, minU, maxU, minV, maxV);
				FemtocraftRenderUtils.drawEastFace(x, y, z, 0, 1, 0, 1, 3.f/16.f, frame, minU, maxU, minV, maxV);
			}
		default:
			break;
		}
		
		renderPort(outputIcon, x, y, z, dir);
	}
	
	private void renderPort(Icon port, int x, int y, int z, ForgeDirection dir)
	{
		float min = 4.f/16.f;
		float max = 12.f/16.f;
		
		float minU = port.getMinU();
		float maxU = port.getMaxU();
		float minV = port.getMinV();
		float maxV = port.getMaxV();
		
		float bandU = minU + (maxU - minU)/8.f;
		float bandV = minV + (maxV - minV)/8.f;
		
		switch(dir)
		{
			case UP:
			{
				FemtocraftRenderUtils.drawTopFace(x, y, z, min, max, min, max, 1, port, minU, maxU, minV, maxV);
				FemtocraftRenderUtils.drawBottomFace(x, y, z, min, max, min, max, 15.f/16.f, port, minU, maxU, minV, maxV);
				FemtocraftRenderUtils.drawNorthFace(x, y, z, min, max, 15.f/16.f, 1, min, port, minU, maxU, minV, bandV);
				FemtocraftRenderUtils.drawSouthFace(x, y, z, min, max, 15.f/16.f, 1, max, port, minU, bandU, minV, maxV);
				FemtocraftRenderUtils.drawEastFace(x, y, z, 15.f/16.f, 1, min, max, max, port, minU, maxU, minV, bandV);
				FemtocraftRenderUtils.drawWestFace(x, y, z, 15.f/16.f, 1, min, max, min, port, minU, bandU, minV, maxV);
			}
			case DOWN:
			{
				FemtocraftRenderUtils.drawTopFace(x, y, z, min, max, min, max, 1.f/16.f, port, minU, maxU, minV, maxV);
				FemtocraftRenderUtils.drawBottomFace(x, y, z, min, max, min, max, 0, port, minU, maxU, minV, maxV);
				FemtocraftRenderUtils.drawNorthFace(x, y, z, min, max, 0, 1.f/16.f, min, port, minU, maxU, minV, bandV);
				FemtocraftRenderUtils.drawSouthFace(x, y, z, min, max, 0, 1.f/16.f, max, port, minU, bandU, minV, maxV);
				FemtocraftRenderUtils.drawEastFace(x, y, z, 0, 1.f/16.f, min, max, max, port, minU, maxU, minV, bandV);
				FemtocraftRenderUtils.drawWestFace(x, y, z, 0, 1.f/16.f, min, max, min, port, minU, bandU, minV, maxV);
			}
			case NORTH:
			{
				FemtocraftRenderUtils.drawNorthFace(x, y, z, min, max, min, max, 0, port, minU, maxU, minV, maxV);
				FemtocraftRenderUtils.drawSouthFace(x, y, z, min, max, min, max, 1.f/16.f, port, minU, maxU, minV, maxV);
			}
			case EAST:
			{
				FemtocraftRenderUtils.drawEastFace(x, y, z, min, max, min, max, 1, port, minU, maxU, minV, maxV);
				FemtocraftRenderUtils.drawWestFace(x, y, z, min, max, min, max, 15.f/16.f, port, minU, maxU, minV, maxV);
			}
			case SOUTH:
			{
				FemtocraftRenderUtils.drawSouthFace(x, y, z, min, max, min, max, 1, port, minU, maxU, minV, maxV);
				FemtocraftRenderUtils.drawNorthFace(x, y, z, min, max, min, max, 15.f/16.f, port, minU, maxU, minV, maxV);
			}
			case WEST:
			{
				FemtocraftRenderUtils.drawWestFace(x, y, z, min, max, min, max, 0, port, minU, maxU, minV, maxV);
				FemtocraftRenderUtils.drawEastFace(x, y, z, min, max, min, max, 1.f/16.f, port, minU, maxU, minV, maxV);
			}
		default:
			break;
		}
	}

}
