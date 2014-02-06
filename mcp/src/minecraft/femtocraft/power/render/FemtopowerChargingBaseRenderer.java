package femtocraft.power.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import femtocraft.power.blocks.MicroChargingBase;
import femtocraft.proxy.ClientProxyFemtocraft;
import femtocraft.render.Model;
import femtocraft.render.Point;
import femtocraft.render.Quad;

public class FemtopowerChargingBaseRenderer implements ISimpleBlockRenderingHandler {
	Model base_model;

	public FemtopowerChargingBaseRenderer() {
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		MicroChargingBase base = (MicroChargingBase)block;
		if(base == null) return;
		
		Tessellator tessellator = Tessellator.instance;
		
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(1, 1, 1);
		renderBase(base, 0,0,0);
		tessellator.draw();
		
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		MicroChargingBase base = (MicroChargingBase)block;
		if(base == null) return false;
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);
		renderBase(base, x,y,z);
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return ClientProxyFemtocraft.FemtocraftChargingBaseRenderID;
	}
	
	void renderBase(MicroChargingBase base, int x, int y, int z)
	{
		if(base_model == null)
		{
			createBase(base);
		}
		
		base_model.location = new Point(x,y,z);
		base_model.draw();
	}
	
	void createBase(MicroChargingBase base)
	{
		base_model = new Model();
		
		//Normal faces
		Quad side_north = new Quad(new Point(0,0,0), new Point(0,1,0), new Point(1,1,0), new Point(1,0,0), base.side);
		Quad side_south = new Quad(new Point(1,0,1), new Point(1,1,1), new Point(0,1,1), new Point(0,0,1), base.side);
		Quad side_east = new Quad(new Point(1,0,0), new Point(1,1,0), new Point(1,1,1), new Point(1,0,1),  base.side);
		Quad side_west = new Quad(new Point(0,0,1), new Point(0,1,1), new Point(0,1,0), new Point(0,0,0),  base.side);
		
		base_model.addQuad(side_north);
		base_model.addQuad(side_south);
		base_model.addQuad(side_east);
		base_model.addQuad(side_west);
		
		Quad side_top = new Quad(new Point(0,14.f/16.f,0), new Point(0,14.f/16.f,1), new Point(1,14.f/16.f,1), new Point(1,14.f/16.f,0), base.top);
		Quad side_bot = new Quad(new Point(1,0,0), new Point(1,0,1), new Point(0,0,1), new Point(0,0,0), base.bottom);
		
		base_model.addQuad(side_top);
		base_model.addQuad(side_bot);
		
		//Top Pillars
		
		Quad pillar_top = new Quad(new Point(0,1,0), new Point(0,1,1), new Point(1,1,1), new Point(1,1,0), base.top_pillar_top);
		
		Quad pillar_north = new Quad(new Point(12.f/16.f,13.f/16.f,4.f/16.f), new Point(4.f/16.f,13.f/16.f,4.f/16.f), new Point(4.f/16.f,1,4.f/16.f), new Point(12.f/16.f,1,4.f/16.f), base.top_pillar_side, base.top_pillar_side.getMinU(), base.top_pillar_side.getInterpolatedU(6), base.top_pillar_side.getMinV(), base.top_pillar_side.getMaxV());
		Quad pillar_south = new Quad(new Point(4.f/16.f,13.f/16.f,12.f/16.f), new Point(12.f/16.f,13.f/16.f,12.f/16.f), new Point(12.f/16.f,1,12.f/16.f), new Point(4.f/16.f,1,12.f/16.f), base.top_pillar_side, base.top_pillar_side.getMinU(), base.top_pillar_side.getInterpolatedU(6), base.top_pillar_side.getMinV(), base.top_pillar_side.getMaxV());
		Quad pillar_east = new Quad(new Point(12.f/16.f,13.f/16.f,12.f/16.f), new Point(12.f/16.f,13.f/16.f,4.f/16.f), new Point(12.f/16.f,1,4.f/16.f), new Point(12.f/16.f,1,12.f/16.f), base.top_pillar_side, base.top_pillar_side.getMinU(), base.top_pillar_side.getInterpolatedU(6), base.top_pillar_side.getMinV(), base.top_pillar_side.getMaxV());
		Quad pillar_west = new Quad(new Point(4.f/16.f,13.f/16.f,4.f/16.f), new Point(4.f/16.f,13.f/16.f,12.f/16.f), new Point(4.f/16.f,1,12.f/16.f), new Point(4.f/16.f,1,4.f/16.f), base.top_pillar_side, base.top_pillar_side.getMinU(), base.top_pillar_side.getInterpolatedU(6), base.top_pillar_side.getMinV(), base.top_pillar_side.getMaxV());
		
		base_model.addQuad(pillar_top);
		base_model.addQuad(pillar_north);
		base_model.addQuad(pillar_south);
		base_model.addQuad(pillar_east);
		base_model.addQuad(pillar_west);
		
		//Side insets
		Quad side_inset_north = new Quad(new Point(0,0,3.f/16.f), new Point(0,1,3.f/16.f), new Point(1,1,3.f/16.f), new Point(1,0,3.f/16f), base.side_inset);
		Quad side_inset_south = new Quad(new Point(1,0,13.f/16.f), new Point(1,1,13.f/16.f), new Point(0,1,13.f/16.f), new Point(0,0,13.f/16.f), base.side_inset);
		Quad side_inset_east = new Quad(new Point(13.f/16.f,0,0), new Point(13.f/16.f,1,0), new Point(13.f/16.f,1,1), new Point(13.f/16.f,0,1),  base.side_inset);
		Quad side_inset_west = new Quad(new Point(3.f/16.f,0,1), new Point(3.f/16.f,1,1), new Point(3.f/16.f,1,0), new Point(3.f/16.f,0,0),  base.side_inset);
		
		base_model.addQuad(side_inset_north);
		base_model.addQuad(side_inset_south);
		base_model.addQuad(side_inset_east);
		base_model.addQuad(side_inset_west);
		
		//Coil insets
		Quad coil_inset_north = new Quad(new Point(0,0,1.f/16.f), new Point(0,1,1.f/16.f), new Point(1,1,1.f/16.f), new Point(1,0,1.f/16f), base.coil_inset);
		Quad coil_inset_south = new Quad(new Point(1,0,15.f/16.f), new Point(1,1,15.f/16.f), new Point(0,1,15.f/16.f), new Point(0,0,15.f/16.f), base.coil_inset);
		Quad coil_inset_east = new Quad(new Point(15.f/16.f,0,0), new Point(15.f/16.f,1,0), new Point(15.f/16.f,1,1), new Point(15.f/16.f,0,1),  base.coil_inset);
		Quad coil_inset_west = new Quad(new Point(1.f/16.f,0,1), new Point(1.f/16.f,1,1), new Point(1.f/16.f,1,0), new Point(1.f/16.f,0,0),  base.coil_inset);
		
		base_model.addQuad(coil_inset_north);
		base_model.addQuad(coil_inset_south);
		base_model.addQuad(coil_inset_east);
		base_model.addQuad(coil_inset_west);
		
		//Coil column insets
		Quad coil_column_inset_north = new Quad(new Point(0,0,2.f/16.f), new Point(0,1,2.f/16.f), new Point(1,1,2.f/16.f), new Point(1,0,2.f/16f), base.coil_column_inset);
		Quad coil_column_inset_south = new Quad(new Point(1,0,14.f/16.f), new Point(1,1,14.f/16.f), new Point(0,1,14.f/16.f), new Point(0,0,14.f/16.f), base.coil_column_inset);
		Quad coil_column_inset_east = new Quad(new Point(14.f/16.f,0,0), new Point(14.f/16.f,1,0), new Point(14.f/16.f,1,1), new Point(14.f/16.f,0,1),  base.coil_column_inset);
		Quad coil_column_inset_west = new Quad(new Point(1.f/14.f,0,1), new Point(2.f/16.f,1,1), new Point(2.f/16.f,1,0), new Point(2.f/16.f,0,0),  base.coil_column_inset);
		
		base_model.addQuad(coil_column_inset_north);
		base_model.addQuad(coil_column_inset_south);
		base_model.addQuad(coil_column_inset_east);
		base_model.addQuad(coil_column_inset_west);
		
		//Side top insets
		Quad side_top_inset_top = new Quad(new Point(1,12.f/16.f,0), new Point(1,12.f/16.f,1), new Point(0,12.f/16.f,1), new Point(0,12.f/16.f,0), base.top_inset);
		Quad side_top_inset_bot = new Quad(new Point(0,2.f/16.f,0), new Point(0,2.f/16.f,1), new Point(1,2.f/16.f,1), new Point(1,2.f/16.f,0), base.top_inset);
		
		base_model.addQuad(side_top_inset_top);
		base_model.addQuad(side_top_inset_bot);
		
		//Coil top insets
		Quad coil_top_inset_top_1 = new Quad(new Point(1,3.f/16.f,0), new Point(1,3.f/16.f,1), new Point(0,3.f/16.f,1), new Point(0,3.f/16.f,0), base.coil_top_inset);
		Quad coil_top_inset_top_2 = new Quad(new Point(1,6.f/16.f,0), new Point(1,6.f/16.f,1), new Point(0,6.f/16.f,1), new Point(0,6.f/16.f,0), base.coil_top_inset);
		Quad coil_top_inset_top_3 = new Quad(new Point(1,9.f/16.f,0), new Point(1,9.f/16.f,1), new Point(0,9.f/16.f,1), new Point(0,9.f/16.f,0), base.coil_top_inset);
		
		Quad coil_top_inset_bot_1 = new Quad(new Point(0,5.f/16.f,0), new Point(0,5.f/16.f,1), new Point(1,5.f/16.f,1), new Point(1,5.f/16.f,0), base.coil_top_inset);
		Quad coil_top_inset_bot_2 = new Quad(new Point(0,8.f/16.f,0), new Point(0,8.f/16.f,1), new Point(1,8.f/16.f,1), new Point(1,8.f/16.f,0), base.coil_top_inset);
		Quad coil_top_inset_bot_3 = new Quad(new Point(0,11.f/16.f,0), new Point(0,11.f/16.f,1), new Point(1,11.f/16.f,1), new Point(1,11.f/16.f,0), base.coil_top_inset);
		
		base_model.addQuad(coil_top_inset_top_1);
		base_model.addQuad(coil_top_inset_top_2);
		base_model.addQuad(coil_top_inset_top_3);
		base_model.addQuad(coil_top_inset_bot_1);
		base_model.addQuad(coil_top_inset_bot_2);
		base_model.addQuad(coil_top_inset_bot_3);
	}

}
