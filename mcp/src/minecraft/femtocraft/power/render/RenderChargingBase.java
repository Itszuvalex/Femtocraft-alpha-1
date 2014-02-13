package femtocraft.power.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import femtocraft.power.blocks.BlockBaseMicroCharging;
import femtocraft.proxy.ProxyClient;
import femtocraft.render.RenderModel;
import femtocraft.render.RenderPoint;
import femtocraft.render.RenderQuad;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class RenderChargingBase implements
		ISimpleBlockRenderingHandler {
	RenderModel base_model;

	public RenderChargingBase() {
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		BlockBaseMicroCharging base = (BlockBaseMicroCharging) block;
		if (base == null)
			return;

		Tessellator tessellator = Tessellator.instance;

		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(1, 1, 1);
		renderBase(base, 0, 0, 0);
		tessellator.draw();

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		BlockBaseMicroCharging base = (BlockBaseMicroCharging) block;
		if (base == null)
			return false;

		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(
				renderer.blockAccess, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);
		renderBase(base, x, y, z);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return ProxyClient.FemtocraftChargingBaseRenderID;
	}

	void renderBase(BlockBaseMicroCharging base, int x, int y, int z) {
		if (base_model == null) {
			createBase(base);
		}

		base_model.location = new RenderPoint(x, y, z);
		base_model.draw();
	}

	void createBase(BlockBaseMicroCharging base) {
		base_model = new RenderModel();

		// Normal faces
		RenderQuad side_north = new RenderQuad(new RenderPoint(0, 0, 0), new RenderPoint(0, 1, 0),
				new RenderPoint(1, 1, 0), new RenderPoint(1, 0, 0), base.side);
		RenderQuad side_south = new RenderQuad(new RenderPoint(1, 0, 1), new RenderPoint(1, 1, 1),
				new RenderPoint(0, 1, 1), new RenderPoint(0, 0, 1), base.side);
		RenderQuad side_east = new RenderQuad(new RenderPoint(1, 0, 0), new RenderPoint(1, 1, 0),
				new RenderPoint(1, 1, 1), new RenderPoint(1, 0, 1), base.side);
		RenderQuad side_west = new RenderQuad(new RenderPoint(0, 0, 1), new RenderPoint(0, 1, 1),
				new RenderPoint(0, 1, 0), new RenderPoint(0, 0, 0), base.side);

		base_model.addQuad(side_north);
		base_model.addQuad(side_south);
		base_model.addQuad(side_east);
		base_model.addQuad(side_west);

		RenderQuad side_top = new RenderQuad(new RenderPoint(0, 14.f / 16.f, 0), new RenderPoint(0,
				14.f / 16.f, 1), new RenderPoint(1, 14.f / 16.f, 1), new RenderPoint(1,
				14.f / 16.f, 0), base.top);
		RenderQuad side_bot = new RenderQuad(new RenderPoint(1, 0, 0), new RenderPoint(1, 0, 1),
				new RenderPoint(0, 0, 1), new RenderPoint(0, 0, 0), base.bottom);

		base_model.addQuad(side_top);
		base_model.addQuad(side_bot);

		// Top Pillars

		RenderQuad pillar_top = new RenderQuad(new RenderPoint(0, 1, 0), new RenderPoint(0, 1, 1),
				new RenderPoint(1, 1, 1), new RenderPoint(1, 1, 0), base.top_pillar_top);

		RenderQuad pillar_north = new RenderQuad(new RenderPoint(12.f / 16.f, 13.f / 16.f,
				4.f / 16.f), new RenderPoint(4.f / 16.f, 13.f / 16.f, 4.f / 16.f),
				new RenderPoint(4.f / 16.f, 1, 4.f / 16.f), new RenderPoint(12.f / 16.f, 1,
						4.f / 16.f), base.top_pillar_side,
				base.top_pillar_side.getMinU(),
				base.top_pillar_side.getInterpolatedU(6),
				base.top_pillar_side.getMinV(), base.top_pillar_side.getMaxV());
		RenderQuad pillar_south = new RenderQuad(new RenderPoint(4.f / 16.f, 13.f / 16.f,
				12.f / 16.f), new RenderPoint(12.f / 16.f, 13.f / 16.f, 12.f / 16.f),
				new RenderPoint(12.f / 16.f, 1, 12.f / 16.f), new RenderPoint(4.f / 16.f,
						1, 12.f / 16.f), base.top_pillar_side,
				base.top_pillar_side.getMinU(),
				base.top_pillar_side.getInterpolatedU(6),
				base.top_pillar_side.getMinV(), base.top_pillar_side.getMaxV());
		RenderQuad pillar_east = new RenderQuad(new RenderPoint(12.f / 16.f, 13.f / 16.f,
				12.f / 16.f), new RenderPoint(12.f / 16.f, 13.f / 16.f, 4.f / 16.f),
				new RenderPoint(12.f / 16.f, 1, 4.f / 16.f), new RenderPoint(12.f / 16.f,
						1, 12.f / 16.f), base.top_pillar_side,
				base.top_pillar_side.getMinU(),
				base.top_pillar_side.getInterpolatedU(6),
				base.top_pillar_side.getMinV(), base.top_pillar_side.getMaxV());
		RenderQuad pillar_west = new RenderQuad(new RenderPoint(4.f / 16.f, 13.f / 16.f,
				4.f / 16.f), new RenderPoint(4.f / 16.f, 13.f / 16.f, 12.f / 16.f),
				new RenderPoint(4.f / 16.f, 1, 12.f / 16.f), new RenderPoint(4.f / 16.f, 1,
						4.f / 16.f), base.top_pillar_side,
				base.top_pillar_side.getMinU(),
				base.top_pillar_side.getInterpolatedU(6),
				base.top_pillar_side.getMinV(), base.top_pillar_side.getMaxV());

		base_model.addQuad(pillar_top);
		base_model.addQuad(pillar_north);
		base_model.addQuad(pillar_south);
		base_model.addQuad(pillar_east);
		base_model.addQuad(pillar_west);

		// Side insets
		RenderQuad side_inset_north = new RenderQuad(new RenderPoint(0, 0, 3.f / 16.f),
				new RenderPoint(0, 1, 3.f / 16.f), new RenderPoint(1, 1, 3.f / 16.f),
				new RenderPoint(1, 0, 3.f / 16f), base.side_inset);
		RenderQuad side_inset_south = new RenderQuad(new RenderPoint(1, 0, 13.f / 16.f),
				new RenderPoint(1, 1, 13.f / 16.f), new RenderPoint(0, 1, 13.f / 16.f),
				new RenderPoint(0, 0, 13.f / 16.f), base.side_inset);
		RenderQuad side_inset_east = new RenderQuad(new RenderPoint(13.f / 16.f, 0, 0),
				new RenderPoint(13.f / 16.f, 1, 0), new RenderPoint(13.f / 16.f, 1, 1),
				new RenderPoint(13.f / 16.f, 0, 1), base.side_inset);
		RenderQuad side_inset_west = new RenderQuad(new RenderPoint(3.f / 16.f, 0, 1), new RenderPoint(
				3.f / 16.f, 1, 1), new RenderPoint(3.f / 16.f, 1, 0), new RenderPoint(
				3.f / 16.f, 0, 0), base.side_inset);

		base_model.addQuad(side_inset_north);
		base_model.addQuad(side_inset_south);
		base_model.addQuad(side_inset_east);
		base_model.addQuad(side_inset_west);

		// Coil insets
		RenderQuad coil_inset_north = new RenderQuad(new RenderPoint(0, 0, 1.f / 16.f),
				new RenderPoint(0, 1, 1.f / 16.f), new RenderPoint(1, 1, 1.f / 16.f),
				new RenderPoint(1, 0, 1.f / 16f), base.coil_inset);
		RenderQuad coil_inset_south = new RenderQuad(new RenderPoint(1, 0, 15.f / 16.f),
				new RenderPoint(1, 1, 15.f / 16.f), new RenderPoint(0, 1, 15.f / 16.f),
				new RenderPoint(0, 0, 15.f / 16.f), base.coil_inset);
		RenderQuad coil_inset_east = new RenderQuad(new RenderPoint(15.f / 16.f, 0, 0),
				new RenderPoint(15.f / 16.f, 1, 0), new RenderPoint(15.f / 16.f, 1, 1),
				new RenderPoint(15.f / 16.f, 0, 1), base.coil_inset);
		RenderQuad coil_inset_west = new RenderQuad(new RenderPoint(1.f / 16.f, 0, 1), new RenderPoint(
				1.f / 16.f, 1, 1), new RenderPoint(1.f / 16.f, 1, 0), new RenderPoint(
				1.f / 16.f, 0, 0), base.coil_inset);

		base_model.addQuad(coil_inset_north);
		base_model.addQuad(coil_inset_south);
		base_model.addQuad(coil_inset_east);
		base_model.addQuad(coil_inset_west);

		// Coil column insets
		RenderQuad coil_column_inset_north = new RenderQuad(new RenderPoint(0, 0, 2.f / 16.f),
				new RenderPoint(0, 1, 2.f / 16.f), new RenderPoint(1, 1, 2.f / 16.f),
				new RenderPoint(1, 0, 2.f / 16f), base.coil_column_inset);
		RenderQuad coil_column_inset_south = new RenderQuad(new RenderPoint(1, 0, 14.f / 16.f),
				new RenderPoint(1, 1, 14.f / 16.f), new RenderPoint(0, 1, 14.f / 16.f),
				new RenderPoint(0, 0, 14.f / 16.f), base.coil_column_inset);
		RenderQuad coil_column_inset_east = new RenderQuad(new RenderPoint(14.f / 16.f, 0, 0),
				new RenderPoint(14.f / 16.f, 1, 0), new RenderPoint(14.f / 16.f, 1, 1),
				new RenderPoint(14.f / 16.f, 0, 1), base.coil_column_inset);
		RenderQuad coil_column_inset_west = new RenderQuad(new RenderPoint(1.f / 14.f, 0, 1),
				new RenderPoint(2.f / 16.f, 1, 1), new RenderPoint(2.f / 16.f, 1, 0),
				new RenderPoint(2.f / 16.f, 0, 0), base.coil_column_inset);

		base_model.addQuad(coil_column_inset_north);
		base_model.addQuad(coil_column_inset_south);
		base_model.addQuad(coil_column_inset_east);
		base_model.addQuad(coil_column_inset_west);

		// Side top insets
		RenderQuad side_top_inset_top = new RenderQuad(new RenderPoint(1, 12.f / 16.f, 0),
				new RenderPoint(1, 12.f / 16.f, 1), new RenderPoint(0, 12.f / 16.f, 1),
				new RenderPoint(0, 12.f / 16.f, 0), base.top_inset);
		RenderQuad side_top_inset_bot = new RenderQuad(new RenderPoint(0, 2.f / 16.f, 0),
				new RenderPoint(0, 2.f / 16.f, 1), new RenderPoint(1, 2.f / 16.f, 1),
				new RenderPoint(1, 2.f / 16.f, 0), base.top_inset);

		base_model.addQuad(side_top_inset_top);
		base_model.addQuad(side_top_inset_bot);

		// Coil top insets
		RenderQuad coil_top_inset_top_1 = new RenderQuad(new RenderPoint(1, 3.f / 16.f, 0),
				new RenderPoint(1, 3.f / 16.f, 1), new RenderPoint(0, 3.f / 16.f, 1),
				new RenderPoint(0, 3.f / 16.f, 0), base.coil_top_inset);
		RenderQuad coil_top_inset_top_2 = new RenderQuad(new RenderPoint(1, 6.f / 16.f, 0),
				new RenderPoint(1, 6.f / 16.f, 1), new RenderPoint(0, 6.f / 16.f, 1),
				new RenderPoint(0, 6.f / 16.f, 0), base.coil_top_inset);
		RenderQuad coil_top_inset_top_3 = new RenderQuad(new RenderPoint(1, 9.f / 16.f, 0),
				new RenderPoint(1, 9.f / 16.f, 1), new RenderPoint(0, 9.f / 16.f, 1),
				new RenderPoint(0, 9.f / 16.f, 0), base.coil_top_inset);

		RenderQuad coil_top_inset_bot_1 = new RenderQuad(new RenderPoint(0, 5.f / 16.f, 0),
				new RenderPoint(0, 5.f / 16.f, 1), new RenderPoint(1, 5.f / 16.f, 1),
				new RenderPoint(1, 5.f / 16.f, 0), base.coil_top_inset);
		RenderQuad coil_top_inset_bot_2 = new RenderQuad(new RenderPoint(0, 8.f / 16.f, 0),
				new RenderPoint(0, 8.f / 16.f, 1), new RenderPoint(1, 8.f / 16.f, 1),
				new RenderPoint(1, 8.f / 16.f, 0), base.coil_top_inset);
		RenderQuad coil_top_inset_bot_3 = new RenderQuad(new RenderPoint(0, 11.f / 16.f, 0),
				new RenderPoint(0, 11.f / 16.f, 1), new RenderPoint(1, 11.f / 16.f, 1),
				new RenderPoint(1, 11.f / 16.f, 0), base.coil_top_inset);

		base_model.addQuad(coil_top_inset_top_1);
		base_model.addQuad(coil_top_inset_top_2);
		base_model.addQuad(coil_top_inset_top_3);
		base_model.addQuad(coil_top_inset_bot_1);
		base_model.addQuad(coil_top_inset_bot_2);
		base_model.addQuad(coil_top_inset_bot_3);
	}

}
