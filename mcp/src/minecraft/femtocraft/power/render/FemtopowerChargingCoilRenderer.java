package femtocraft.power.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import femtocraft.power.blocks.MicroChargingCoil;
import femtocraft.proxy.ClientProxyFemtocraft;
import femtocraft.render.Model;
import femtocraft.render.Point;
import femtocraft.render.Quad;

public class FemtopowerChargingCoilRenderer implements ISimpleBlockRenderingHandler{
	Model m_coil;
	Model ends;

	public FemtopowerChargingCoilRenderer() {
		
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		MicroChargingCoil coil = (MicroChargingCoil)block;
		if(coil == null) return;
		
		Tessellator tessellator = Tessellator.instance;
		
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(1, 1, 1);
		renderCoil(coil, 0,0,0);
		tessellator.draw();
		
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		MicroChargingCoil coil = (MicroChargingCoil)block;
		if(coil == null) return false;
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);
		renderCoil(coil, x,y,z);
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return ClientProxyFemtocraft.FemtocraftChargingCoilRenderID;
	}
	
	private void renderCoil(MicroChargingCoil coil, int x, int y, int z)
	{
		if(m_coil == null)
		{
			createCoil(coil);
		}
		if(ends == null)
		{
			createEnds(coil);
		}
		
		m_coil.location = new Point(x, y, z);
		m_coil.draw();
		m_coil.location = new Point(x, y + 8.f/16.f, z);
		m_coil.draw();
		
		ends.location = new Point(x, y, z);
		ends.draw();
	}
	
	private void createCoil(MicroChargingCoil coil)
	{
		m_coil = new Model();
		
		Quad coilTop = new Quad(new Point(0,6.f/16.f,0), new Point(0,6.f/16.f,1), new Point(1,6.f/16.f,1), new Point(1,6.f/16.f,0), coil.coilTop);
		Quad coilBot = new Quad(new Point(1,2.f/16.f,0), new Point(1,2.f/16.f,1), new Point(0,2.f/16.f,1), new Point(0,2.f/16.f,0), coil.coilTop);

		float minY = 2.f/16.f;
		float maxY = 6.f/16.f;
		float min = 0.f/16.f;
		float max = 16.f/16.f;
		
		Quad coilSideNorth = new Quad(new Point(max,minY,min), new Point(min,minY,min), new Point(min,maxY,min), new Point(max,maxY,min), coil.coilEdge, coil.coilEdge.getMinU(), coil.coilEdge.getInterpolatedU(4.f), coil.coilEdge.getInterpolatedV(0.f), coil.coilEdge.getInterpolatedV(16.f));
		Quad coilSideSouth = new Quad(new Point(min,minY,max), new Point(max,minY,max), new Point(max,maxY,max), new Point(min,maxY,max), coil.coilEdge, coil.coilEdge.getMinU(), coil.coilEdge.getInterpolatedU(4.f), coil.coilEdge.getInterpolatedV(0.f), coil.coilEdge.getInterpolatedV(16.f));
		Quad coilSideEast = new Quad(new Point(max,minY,max), new Point(max,minY,min), new Point(max,maxY,min), new Point(max,maxY,max), coil.coilEdge, coil.coilEdge.getMinU(), coil.coilEdge.getInterpolatedU(4.f), coil.coilEdge.getInterpolatedV(0.f), coil.coilEdge.getInterpolatedV(16.f));
		Quad coilSideWest = new Quad(new Point(min,minY,min), new Point(min,minY,max), new Point(min,maxY,max), new Point(min,maxY,min), coil.coilEdge, coil.coilEdge.getMinU(), coil.coilEdge.getInterpolatedU(4.f), coil.coilEdge.getInterpolatedV(0.f), coil.coilEdge.getInterpolatedV(16.f));
		
		m_coil.addQuad(coilTop);
		m_coil.addQuad(coilBot);
		
		m_coil.addQuad(coilSideNorth);
		m_coil.addQuad(coilSideSouth);
		m_coil.addQuad(coilSideEast);
		m_coil.addQuad(coilSideWest);
		
		minY = 0;
		maxY = 8.f/16.f;
		min = 4.f/16.f;
		max = 12.f/16.f;
		
		Quad connectorNorth = new Quad(new Point(max,minY,min), new Point(min,minY,min), new Point(min,maxY,min), new Point(max,maxY,min), coil.coilConnector);
		Quad connectorSouth = new Quad(new Point(min,minY,max), new Point(max,minY,max), new Point(max,maxY,max), new Point(min,maxY,max), coil.coilConnector);
		Quad connectorEast = new Quad(new Point(max,minY,max), new Point(max,minY,min), new Point(max,maxY,min), new Point(max,maxY,max), coil.coilConnector);
		Quad connectorWest = new Quad(new Point(min,minY,min), new Point(min,minY,max), new Point(min,maxY,max), new Point(min,maxY,min), coil.coilConnector);
		
		m_coil.addQuad(connectorNorth);
		m_coil.addQuad(connectorSouth);
		m_coil.addQuad(connectorEast);
		m_coil.addQuad(connectorWest);
	}
	
	private void createEnds(MicroChargingCoil coil)
	{
		ends = new Model();
		
		Quad top = new Quad(new Point(0,1,0), new Point(0,1,1), new Point(1,1,1), new Point(1,1,0), coil.coilConnectorTop);
		Quad bot = new Quad(new Point(1,0,0), new Point(1,0,1), new Point(0,0,1), new Point(0,0,0), coil.coilConnectorTop);
		
		ends.addQuad(top);
		ends.addQuad(bot);
	}

}
