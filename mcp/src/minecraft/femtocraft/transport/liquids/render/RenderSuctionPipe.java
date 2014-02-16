package femtocraft.transport.liquids.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

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
	private boolean initialized;
	private RenderModel center_liquid;

	private RenderModel pipes_North, pipes_South, pipes_East, pipes_West,
			pipes_Up, pipes_Down;
	private RenderModel pipes_liquid_North, pipes_liquid_South,
			pipes_liquid_East, pipes_liquid_West, pipes_liquid_Up,
			pipes_liquid_Down;
	private RenderModel pipes_liquid_cap_North, pipes_liquid_cap_South,
			pipes_liquid_cap_East, pipes_liquid_cap_West, pipes_liquid_cap_Up,
			pipes_liquid_cap_Down;
	private RenderModel tanks_North, tanks_South, tanks_East, tanks_West,
			tanks_Up, tanks_Down;
	private RenderModel tanks_liquid_North, tanks_liquid_South,
			tanks_liquid_East, tanks_liquid_West, tanks_liquid_Up,
			tanks_liquid_Down;

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
				false, false }, false, null);
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
				pipe.tankconnections, pipe.isOutput(), pipe.getRenderFluid());

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
		center_liquid = new RenderModel();

		float min = 6.f / 16.f;
		float max = 10.f / 16.f;

		center_liquid.addQuad(RenderUtils.makeTopFace(min, max, min, max,
				max - .01f, pipe.center, 0, 0, 0, 0));
		center_liquid.addQuad(RenderUtils.makeBottomFace(min, max, min, max,
				min + .01f, pipe.center, 0, 0, 0, 0));
		center_liquid.addQuad(RenderUtils.makeNorthFace(min, max, min, max,
				min + .01f, pipe.center, 0, 0, 0, 0));
		center_liquid.addQuad(RenderUtils.makeSouthFace(min, max, min, max,
				max - .01f, pipe.center, 0, 0, 0, 0));
		center_liquid.addQuad(RenderUtils.makeEastFace(min, max, min, max,
				max - .01f, pipe.center, 0, 0, 0, 0));
		center_liquid.addQuad(RenderUtils.makeWestFace(min, max, min, max,
				min + .01f, pipe.center, 0, 0, 0, 0));

		createPipePipe(pipe);
		createPipeTank(pipe);
	}

	private void createPipePipe(BlockSuctionPipe pipe) {
		float min = 6.f / 16.f;
		float max = 10.f / 16.f;

		float minU = pipe.connector.getMinU();
		float maxU = pipe.connector.getInterpolatedU(8.f);
		float minV = pipe.connector.getInterpolatedV(4.f);
		float maxV = pipe.connector.getMaxV();

		pipes_Up = new RenderModel();
		pipes_Up.center = new RenderPoint(.5f, .5f, .5f);
		pipes_liquid_Up = new RenderModel();
		pipes_liquid_Up.center = new RenderPoint(.5f, .5f, .5f);
		pipes_liquid_cap_Up = new RenderModel();
		pipes_liquid_cap_Up.center = new RenderPoint(.5f, .5f, .5f);

		{
			// North
			pipes_Up.addQuad(RenderUtils.makeNorthFace(min, max, max, 1.f, min,
					pipe.connector, minU, maxU, minV, maxV));
			pipes_Up.addQuad(RenderUtils.makeSouthFace(min, max, max, 1.f, min,
					pipe.connector, minU, maxU, minV, maxV));

			// South
			pipes_Up.addQuad(RenderUtils.makeSouthFace(min, max, max, 1.f, max,
					pipe.connector, minU, maxU, minV, maxV));
			pipes_Up.addQuad(RenderUtils.makeNorthFace(min, max, max, 1.f, max,
					pipe.connector, minU, maxU, minV, maxV));

			// East
			pipes_Up.addQuad(RenderUtils.makeEastFace(max, 1.f, min, max, max,
					pipe.connector, minU, maxU, minV, maxV));
			pipes_Up.addQuad(RenderUtils.makeWestFace(max, 1.f, min, max, max,
					pipe.connector, minU, maxU, minV, maxV));

			// West
			pipes_Up.addQuad(RenderUtils.makeWestFace(max, 1.f, min, max, min,
					pipe.connector, minU, maxU, minV, maxV));
			pipes_Up.addQuad(RenderUtils.makeEastFace(max, 1.f, min, max, min,
					pipe.connector, minU, maxU, minV, maxV));

			pipes_liquid_Up.addQuad(RenderUtils.makeNorthFace(min + .01f,
					max - .01f, max, 1.f, min + .01f, pipe.connector, 0, 0, 0,
					0));
			pipes_liquid_Up.addQuad(RenderUtils.makeSouthFace(min + .01f,
					max - .01f, max, 1.f, max - .01f, pipe.connector, 0, 0, 0,
					0));
			pipes_liquid_Up.addQuad(RenderUtils.makeEastFace(max, 1.f,
					min + .01f, max - .01f, max - .01f, pipe.connector, 0, 0,
					0, 0));
			pipes_liquid_Up.addQuad(RenderUtils.makeWestFace(max, 1.f,
					min + .01f, max - .01f, min + .01f, pipe.connector, 0, 0,
					0, 0));

			pipes_liquid_cap_Up.addQuad(RenderUtils.makeTopFace(min, max, min,
					max, 1.f, pipe.connector, 0, 0, 0, 0));
		}

		pipes_Down = pipes_Up.rotatedOnZAxis(Math.PI);
		pipes_liquid_Down = pipes_liquid_Up.rotatedOnZAxis(Math.PI);
		pipes_liquid_cap_Down = pipes_liquid_cap_Up.rotatedOnZAxis(Math.PI);

		pipes_East = pipes_Up.rotatedOnZAxis(-Math.PI / 2.f);
		pipes_liquid_East = pipes_liquid_Up.rotatedOnZAxis(-Math.PI / 2.f);
		pipes_liquid_cap_East = pipes_liquid_cap_Up
				.rotatedOnZAxis(-Math.PI / 2.f);

		pipes_West = pipes_Up.rotatedOnZAxis(Math.PI / 2.f);
		pipes_liquid_West = pipes_liquid_Up.rotatedOnZAxis(Math.PI / 2.f);
		pipes_liquid_cap_West = pipes_liquid_cap_Up
				.rotatedOnZAxis(Math.PI / 2.f);

		pipes_North = pipes_Up.rotatedOnXAxis(-Math.PI / 2.f);
		pipes_liquid_North = pipes_liquid_Up.rotatedOnXAxis(-Math.PI / 2.f);
		pipes_liquid_cap_North = pipes_liquid_cap_Up
				.rotatedOnXAxis(-Math.PI / 2.f);

		pipes_South = pipes_Up.rotatedOnXAxis(Math.PI / 2.f);
		pipes_liquid_South = pipes_liquid_Up.rotatedOnXAxis(Math.PI / 2.f);
		pipes_liquid_cap_South = pipes_liquid_cap_Up
				.rotatedOnXAxis(Math.PI / 2.f);
	}

	private void createPipeTank(BlockSuctionPipe pipe) {
		float min = 6.f / 16.f;
		float max = 10.f / 16.f;
		float height = 13.f / 16.f;

		float min_tip = 7.f / 16.f;
		float max_tip = 9.f / 16.f;

		float minU = pipe.connector.getMinU();
		float maxU = pipe.connector.getInterpolatedU(8.f);
		float minV = pipe.connector.getInterpolatedV(10.f);
		float maxV = pipe.connector.getMaxV();

		float minUtip = pipe.connector_tank.getMinU();
		float maxUtip = pipe.connector_tank.getInterpolatedU(4.f);
		float minVtip = pipe.connector_tank.getInterpolatedV(10.f);
		float maxVtip = pipe.connector_tank.getMaxV();

		tanks_Up = new RenderModel();
		tanks_Up.center = new RenderPoint(.5f, .5f, .5f);
		tanks_liquid_Up = new RenderModel();
		tanks_liquid_Up.center = new RenderPoint(.5f, .5f, .5f);

		{
			// North
			tanks_Up.addQuad(RenderUtils.makeNorthFace(min, max, max, height,
					min, pipe.connector, minU, maxU, minV, maxV));
			tanks_Up.addQuad(RenderUtils.makeSouthFace(min, max, max, height,
					min, pipe.connector, minU, maxU, minV, maxV));

			// South
			tanks_Up.addQuad(RenderUtils.makeSouthFace(min, max, max, height,
					max, pipe.connector, minU, maxU, minV, maxV));
			tanks_Up.addQuad(RenderUtils.makeNorthFace(min, max, max, height,
					max, pipe.connector, minU, maxU, minV, maxV));

			// East
			tanks_Up.addQuad(RenderUtils.makeEastFace(max, height, min, max,
					max, pipe.connector, minU, maxU, minV, maxV));
			tanks_Up.addQuad(RenderUtils.makeWestFace(max, height, min, max,
					max, pipe.connector, minU, maxU, minV, maxV));

			// West
			tanks_Up.addQuad(RenderUtils.makeWestFace(max, height, min, max,
					min, pipe.connector, minU, maxU, minV, maxV));
			tanks_Up.addQuad(RenderUtils.makeEastFace(max, height, min, max,
					min, pipe.connector, minU, maxU, minV, maxV));

			// Tank cap
			tanks_Up.addQuad(RenderUtils.makeTopFace(min, max, min, max,
					height, pipe.center, pipe.center.getMinU(),
					pipe.center.getMaxU(), pipe.center.getMinV(),
					pipe.center.getMaxV()));

			// Tip
			tanks_Up.addQuad(RenderUtils.makeNorthFace(min_tip, max_tip,
					height, 1.f, min_tip, pipe.connector_tank, minUtip,
					maxUtip, minVtip, maxVtip));
			tanks_Up.addQuad(RenderUtils.makeSouthFace(min_tip, max_tip,
					height, 1.f, max_tip, pipe.connector_tank, minUtip,
					maxUtip, minVtip, maxVtip));
			tanks_Up.addQuad(RenderUtils.makeEastFace(height, 1.f, min_tip,
					max_tip, max_tip, pipe.connector_tank, minUtip, maxUtip,
					minVtip, maxVtip));
			tanks_Up.addQuad(RenderUtils.makeWestFace(height, 1.f, min_tip,
					max_tip, min_tip, pipe.connector_tank, minUtip, maxUtip,
					minVtip, maxVtip));

			// Tip cap
			tanks_Up.addQuad(RenderUtils.makeTopFace(min_tip, max_tip, min_tip,
					max_tip, 1.f, pipe.connector_tank,
					pipe.connector_tank.getMinU(),
					pipe.connector_tank.getInterpolatedU(4.f),
					pipe.connector_tank.getInterpolatedV(12.f),
					pipe.connector_tank.getMaxV()));

			tanks_liquid_Up.addQuad(RenderUtils.makeNorthFace(min + .01f,
					max - .01f, max, height, min + .01f, pipe.connector, 0, 0,
					0, 0));
			tanks_liquid_Up.addQuad(RenderUtils.makeSouthFace(min + .01f,
					max - .01f, max, height, max - .01f, pipe.connector, 0, 0,
					0, 0));
			tanks_liquid_Up.addQuad(RenderUtils.makeEastFace(max, height,
					min + .01f, max - .01f, max - .01f, pipe.connector, 0, 0,
					0, 0));
			tanks_liquid_Up.addQuad(RenderUtils.makeWestFace(max, height,
					min + .01f, max - .01f, min + .01f, pipe.connector, 0, 0,
					0, 0));
		}

		tanks_Down = tanks_Up.rotatedOnZAxis(Math.PI);
		tanks_liquid_Down = tanks_liquid_Up.rotatedOnZAxis(Math.PI);

		tanks_East = tanks_Up.rotatedOnZAxis(-Math.PI / 2.f);
		tanks_liquid_East = tanks_liquid_Up.rotatedOnZAxis(-Math.PI / 2.f);

		tanks_West = tanks_Up.rotatedOnZAxis(Math.PI / 2.f);
		tanks_liquid_West = tanks_liquid_Up.rotatedOnZAxis(Math.PI / 2.f);

		tanks_North = tanks_Up.rotatedOnXAxis(-Math.PI / 2.f);
		tanks_liquid_North = tanks_liquid_Up.rotatedOnXAxis(-Math.PI / 2.f);

		tanks_South = tanks_Up.rotatedOnXAxis(Math.PI / 2.f);
		tanks_liquid_South = tanks_liquid_Up.rotatedOnXAxis(Math.PI / 2.f);
	}

	@SuppressWarnings("incomplete-switch")
	private void renderPipe(BlockSuctionPipe pipe, int x, int y, int z,
			boolean[] con_pipes, boolean[] con_tanks, boolean output,
			FluidStack fluid) {
		if (!initialized)
			createPipe(pipe);

		RenderUtils.renderDoubleSidedCube(x, y, z, 6.f / 16.f, 10.f / 16.f,
				6.f / 16.f, 10.f / 16.f, 6.f / 16.f, 10.f / 16.f, pipe.center);

		if (fluid != null) {
			center_liquid.location = new RenderPoint(x, y, z);
			for (RenderQuad quad : center_liquid.faces) {
				Icon icon_f = fluid.getFluid().getIcon();
				quad.icon = icon_f;
				quad.minU = icon_f.getInterpolatedU(7.f);
				quad.maxU = icon_f.getInterpolatedU(9.f);
				quad.minV = icon_f.getInterpolatedV(7.f);
				quad.maxV = icon_f.getInterpolatedV(9.f);
			}
			center_liquid.draw();
		}

		for (int i = 0; i < 6; ++i) {
			if (con_pipes[i]) {
				RenderModel pipe_m = null;
				RenderModel pipe_liquid_m = null;
				RenderModel pipe_liquid_cap_m = null;

				switch (ForgeDirection.getOrientation(i)) {
				case UP:
					pipe_m = pipes_Up;
					pipe_liquid_m = pipes_liquid_Up;
					pipe_liquid_cap_m = pipes_liquid_cap_Up;
					break;
				case DOWN:
					pipe_m = pipes_Down;
					pipe_liquid_m = pipes_liquid_Down;
					pipe_liquid_cap_m = pipes_liquid_cap_Down;
					break;
				case NORTH:
					pipe_m = pipes_North;
					pipe_liquid_m = pipes_liquid_North;
					pipe_liquid_cap_m = pipes_liquid_cap_North;
					break;
				case SOUTH:
					pipe_m = pipes_South;
					pipe_liquid_m = pipes_liquid_South;
					pipe_liquid_cap_m = pipes_liquid_cap_South;
					break;
				case EAST:
					pipe_m = pipes_East;
					pipe_liquid_m = pipes_liquid_East;
					pipe_liquid_cap_m = pipes_liquid_cap_East;
					break;
				case WEST:
					pipe_m = pipes_West;
					pipe_liquid_m = pipes_liquid_West;
					pipe_liquid_cap_m = pipes_liquid_cap_West;
					break;
				}

				pipe_m.location = new RenderPoint(x, y, z);
				pipe_m.draw();

				if (fluid != null) {
					pipe_liquid_m.location = new RenderPoint(x, y, z);
					for (RenderQuad quad : pipe_liquid_m.faces) {
						Icon icon_f = fluid.getFluid().getIcon();
						quad.icon = icon_f;
						quad.minU = icon_f.getInterpolatedU(7.f);
						quad.maxU = icon_f.getInterpolatedU(9.f);
						quad.minV = icon_f.getInterpolatedV(10.f);
						quad.maxV = icon_f.getInterpolatedV(16.f);
					}
					pipe_liquid_m.draw();

					pipe_liquid_cap_m.location = new RenderPoint(x, y, z);
					for (RenderQuad quad : pipe_liquid_m.faces) {
						Icon icon_f = fluid.getFluid().getIcon();
						quad.icon = icon_f;
						quad.minU = icon_f.getInterpolatedU(6.f);
						quad.maxU = icon_f.getInterpolatedU(10.f);
						quad.minV = icon_f.getInterpolatedV(6.f);
						quad.minV = icon_f.getInterpolatedV(10.f);
					}
					pipe_liquid_cap_m.draw();
				}
			}
			if (con_tanks[i]) {
				RenderModel tank_m = null;
				RenderModel tank_liquid_m = null;

				switch (ForgeDirection.getOrientation(i)) {
				case UP:
					tank_m = tanks_Up;
					tank_liquid_m = tanks_liquid_Up;
					break;
				case DOWN:
					tank_m = tanks_Down;
					tank_liquid_m = tanks_liquid_Down;
					break;
				case NORTH:
					tank_m = tanks_North;
					tank_liquid_m = tanks_liquid_North;
					break;
				case SOUTH:
					tank_m = tanks_South;
					tank_liquid_m = tanks_liquid_South;
					break;
				case EAST:
					tank_m = tanks_East;
					tank_liquid_m = tanks_liquid_East;
					break;
				case WEST:
					tank_m = tanks_West;
					tank_liquid_m = tanks_liquid_West;
					break;
				}

				tank_m.location = new RenderPoint(x, y, z);
				tank_m.draw();

				if (fluid != null) {
					tank_liquid_m.location = new RenderPoint(x, y, z);
					for (RenderQuad quad : tank_liquid_m.faces) {
						Icon icon_f = fluid.getFluid().getIcon();
						quad.icon = icon_f;
						quad.minU = icon_f.getInterpolatedU(7.f);
						quad.maxU = icon_f.getInterpolatedU(9.f);
						quad.minV = icon_f.getInterpolatedV(10.f);
						quad.maxV = icon_f.getInterpolatedV(13.f);
					}
					tank_liquid_m.draw();
				}
			}
		}
	}
}
