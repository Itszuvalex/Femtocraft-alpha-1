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
	Model segment;

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
		if(segment == null)
		{
			createSegment(coil);
		}
		
		segment.location = new Point(x, y, z);
		segment.draw();
		segment.location = new Point(x, y + 8.f/16.f, z);
		segment.draw();
	}
	
	private void createSegment(MicroChargingCoil coil)
	{
		segment = new Model();

		float minY = 0;
		float maxY = 8.f/16.f;
		float min = 4.f/16.f;
		float max = 12.f/16.f;
		
		Quad connectorNorth = new Quad(new Point(min,minY,min), new Point(min,maxY,min), new Point(max,maxY,min), new Point(max,minY,min), coil.coilConnector);
		Quad connectorSouth = new Quad(new Point(max,minY,max), new Point(max,maxY,max), new Point(min,maxY,max), new Point(min,minY,max), coil.coilConnector);
		Quad connectorEast = new Quad(new Point(max,minY,min), new Point(max,maxY,min), new Point(max,maxY,max), new Point(max,minY,max), coil.coilConnector);
		Quad connectorWest = new Quad(new Point(min,minY,max), new Point(min,maxY,max), new Point(min,maxY,min), new Point(min,minY,min), coil.coilConnector);
		
		segment.addQuad(connectorNorth);
		segment.addQuad(connectorSouth);
		segment.addQuad(connectorEast);
		segment.addQuad(connectorWest);
		
		
		Quad top = new Quad(new Point(0,maxY,0), new Point(0,maxY,1), new Point(1,maxY,1), new Point(1,maxY,0), coil.coilConnectorTop);
		Quad bot = new Quad(new Point(1,minY,0), new Point(1,minY,1), new Point(0,minY,1), new Point(0,minY,0), coil.coilConnectorTop);
		
		segment.addQuad(top);
		segment.addQuad(bot);
	}
}
