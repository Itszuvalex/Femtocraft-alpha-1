package femtocraft.transport.liquids.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import femtocraft.proxy.ProxyClient;
import femtocraft.render.RenderModel;
import femtocraft.render.RenderPoint;
import femtocraft.render.RenderQuad;
import femtocraft.render.RenderUtils;
import femtocraft.transport.liquids.blocks.BlockSuctionPipe;
import femtocraft.transport.liquids.tiles.TileEntitySuctionPipe;

public class RenderSuctionPipe implements ISimpleBlockRenderingHandler {
	boolean initialized;
	RenderModel center;
	RenderModel center_liquid;

	RenderModel pipe_North, pipe_South, pipe_East, pipe_West, pipe_Up,
			pipe_Down;
	RenderModel tank_North, tank_South, tank_East, tank_West, tank_Up,
			tank_Down;

	public RenderSuctionPipe() {
		initialized = false;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		BlockSuctionPipe pipe = (BlockSuctionPipe) block;
		if (pipe == null)
			return;

		Tessellator tessellator = Tessellator.instance;

		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(1, 1, 1);
		renderPipe(pipe, 0, 0, 0, new boolean[] { false, false, false, false,
				false, false }, new boolean[] { false, false, false, false,
				false, false });
		tessellator.draw();

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		if (!(block instanceof BlockSuctionPipe))
			return false;
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile == null)
			return false;
		if (!(tile instanceof TileEntitySuctionPipe))
			return false;

		TileEntitySuctionPipe pipe = (TileEntitySuctionPipe) tile;
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(
				renderer.blockAccess, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		renderPipe((BlockSuctionPipe) block, x, y, z, pipe.pipeconnections,
				pipe.tankconnections);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return ProxyClient.FemtocraftSuctionPipeRenderID;
	}

	private void createPipe(BlockSuctionPipe pipe) {
		center = new RenderModel();

		float min = 6.f / 16.f;
		float max = 10.f / 16.f;

		RenderQuad top = RenderUtils.makeTopFace(min, max, min, max, max,
				pipe.center, pipe.center.getMinU(), pipe.center.getMaxU(),
				pipe.center.getMinV(), pipe.center.getMaxV());
		RenderQuad bot = RenderUtils.makeBottomFace(min, max, min, max, min,
				pipe.center, pipe.center.getMinU(), pipe.center.getMaxU(),
				pipe.center.getMinV(), pipe.center.getMaxV());
		RenderQuad north = RenderUtils.makeNorthFace(min, max, min, max, min,
				pipe.center, pipe.center.getMinU(), pipe.center.getMaxU(),
				pipe.center.getMinV(), pipe.center.getMaxV());
		RenderQuad south = RenderUtils.makeSouthFace(min, max, min, max, max,
				pipe.center, pipe.center.getMinU(), pipe.center.getMaxU(),
				pipe.center.getMinV(), pipe.center.getMaxV());
		RenderQuad east = RenderUtils.makeEastFace(min, max, min, max, max,
				pipe.center, pipe.center.getMinU(), pipe.center.getMaxU(),
				pipe.center.getMinV(), pipe.center.getMaxV());
		RenderQuad west = RenderUtils.makeWestFace(min, max, min, max, min,
				pipe.center, pipe.center.getMinU(), pipe.center.getMaxU(),
				pipe.center.getMinV(), pipe.center.getMaxV());

		center.addQuad(top);
		center.addQuad(bot);
		center.addQuad(north);
		center.addQuad(south);
		center.addQuad(east);
		center.addQuad(west);
	}

	private void renderPipe(BlockSuctionPipe pipe, int x, int y, int z,
			boolean[] pipes, boolean[] tanks) {
		if (!initialized)
			createPipe(pipe);

		center.location = new RenderPoint(x, y, z);
		center.draw();
	}

}
