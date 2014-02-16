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

	private RenderModel[] pipes;
	private RenderModel[] pipes_liquid;
	private RenderModel[] tanks;
	private RenderModel[] tanks_liquid;

	public RenderSuctionPipe() {
		initialized = false;
		pipes = new RenderModel[6];
		pipes_liquid = new RenderModel[6];
		tanks = new RenderModel[6];
		tanks_liquid = new RenderModel[6];
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
				pipe.tankconnections, pipe.isOutput(),
				pipe.getRenderFluid());

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

		for (int i = 0; i < 6; ++i) {
			pipes[i] = new RenderModel();
			pipes_liquid[i] = new RenderModel();
			tanks[i] = new RenderModel();
			tanks_liquid[i] = new RenderModel();

			ForgeDirection dir = ForgeDirection.getOrientation(i);

			createPipePipe(pipe, pipes[i], pipes_liquid[i], dir);

		}
	}

	@SuppressWarnings("incomplete-switch")
	private void createPipePipe(BlockSuctionPipe pipe, RenderModel pipe_m,
			RenderModel pipe_liquid_m, ForgeDirection dir) {
		float min = 6.f / 16.f;
		float max = 10.f / 16.f;

		switch (dir) {
		case UP: {
			pipe_m.addQuad(RenderUtils.makeNorthFace(min, max, max, 1.f, min,
					pipe.connector, pipe.connector.getMinU(),
					pipe.connector.getInterpolatedU(8.f),
					pipe.connector.getInterpolatedV(4.f),
					pipe.connector.getMaxV()));
			pipe_m.addQuad(RenderUtils.makeSouthFace(min, max, max, 1.f, max,
					pipe.connector, pipe.connector.getMinU(),
					pipe.connector.getInterpolatedU(8.f),
					pipe.connector.getInterpolatedV(4.f),
					pipe.connector.getMaxV()));
			pipe_m.addQuad(RenderUtils.makeEastFace(max, 1.f, min, max, max,
					pipe.connector, pipe.connector.getMinU(),
					pipe.connector.getInterpolatedU(8.f),
					pipe.connector.getInterpolatedV(4.f),
					pipe.connector.getMaxV()));
			pipe_m.addQuad(RenderUtils.makeWestFace(max, 1.f, min, max, min,
					pipe.connector, pipe.connector.getMinU(),
					pipe.connector.getInterpolatedU(8.f),
					pipe.connector.getInterpolatedV(4.f),
					pipe.connector.getMaxV()));

			pipe_liquid_m.addQuad(RenderUtils.makeNorthFace(min + .01f,
					max - .01f, max, 1.f, min, pipe.connector, 0, 0, 0, 0));
			pipe_liquid_m.addQuad(RenderUtils.makeSouthFace(min + .01f,
					max - .01f, max, 1.f, max, pipe.connector, 0, 0, 0, 0));
			pipe_liquid_m.addQuad(RenderUtils.makeEastFace(max, 1.f,
					min + .01f, max - .01f, max, pipe.connector, 0, 0, 0, 0));
			pipe_liquid_m.addQuad(RenderUtils.makeWestFace(max, 1.f,
					min + .01f, max - .01f, min, pipe.connector, 0, 0, 0, 0));

			break;
		}
		case DOWN: {
			pipe_m.addQuad(RenderUtils.makeNorthFace(min, max, 0.f, min, min,
					pipe.connector, pipe.connector.getMinU(),
					pipe.connector.getInterpolatedU(8.f),
					pipe.connector.getInterpolatedV(4.f),
					pipe.connector.getMaxV()));
			pipe_m.addQuad(RenderUtils.makeSouthFace(min, max, 0.f, min, max,
					pipe.connector, pipe.connector.getMinU(),
					pipe.connector.getInterpolatedU(8.f),
					pipe.connector.getInterpolatedV(4.f),
					pipe.connector.getMaxV()));
			pipe_m.addQuad(RenderUtils.makeEastFace(0.f, min, min, max, max,
					pipe.connector, pipe.connector.getMinU(),
					pipe.connector.getInterpolatedU(8.f),
					pipe.connector.getInterpolatedV(4.f),
					pipe.connector.getMaxV()));
			pipe_m.addQuad(RenderUtils.makeWestFace(0.f, min, min, max, min,
					pipe.connector, pipe.connector.getMinU(),
					pipe.connector.getInterpolatedU(8.f),
					pipe.connector.getInterpolatedV(4.f),
					pipe.connector.getMaxV()));

			pipe_liquid_m.addQuad(RenderUtils.makeNorthFace(min + .01f,
					max - .01f, 0.f, min, min, pipe.connector, 0, 0, 0, 0));
			pipe_liquid_m.addQuad(RenderUtils.makeSouthFace(min + .01f,
					max - .01f, 0.f, min, max, pipe.connector, 0, 0, 0, 0));
			pipe_liquid_m.addQuad(RenderUtils.makeEastFace(0.f, min,
					min + .01f, max - .01f, max, pipe.connector, 0, 0, 0, 0));
			pipe_liquid_m.addQuad(RenderUtils.makeWestFace(0.f, min,
					min + .01f, max - .01f, min, pipe.connector, 0, 0, 0, 0));

			break;
		}
		case NORTH: {
			pipe_m.addQuad(RenderUtils.makeTopFace(min, max, 0.f, min, max,
					pipe.connector, pipe.connector.getMinU(),
					pipe.connector.getInterpolatedU(8.f),
					pipe.connector.getInterpolatedV(4.f),
					pipe.connector.getMaxV()));
			pipe_m.addQuad(RenderUtils.makeBottomFace(min, max, 0.f, min, min,
					pipe.connector, pipe.connector.getMinU(),
					pipe.connector.getInterpolatedU(8.f),
					pipe.connector.getInterpolatedV(4.f),
					pipe.connector.getMaxV()));
			pipe_m.addQuad(RenderUtils.makeEastFace(min, max, 0.f, min, max,
					pipe.connector, pipe.connector.getMinU(),
					pipe.connector.getInterpolatedU(8.f),
					pipe.connector.getInterpolatedV(4.f),
					pipe.connector.getMaxV()));
			pipe_m.addQuad(RenderUtils.makeWestFace(min, max, 0.f, min, min,
					pipe.connector, pipe.connector.getMinU(),
					pipe.connector.getInterpolatedU(8.f),
					pipe.connector.getInterpolatedV(4.f),
					pipe.connector.getMaxV()));

			pipe_liquid_m.addQuad(RenderUtils.makeTopFace(min, max, 0.f, min,
					max - .01f, pipe.connector, 0, 0, 0, 0));
			pipe_liquid_m.addQuad(RenderUtils.makeBottomFace(min, max, 0.f,
					min, min + .01f, pipe.connector, 0, 0, 0, 0));
			pipe_liquid_m.addQuad(RenderUtils.makeEastFace(min, max, 0.f, min,
					max - .01f, pipe.connector, 0, 0, 0, 0));
			pipe_liquid_m.addQuad(RenderUtils.makeWestFace(min, max, 0.f, min,
					min + .01f, pipe.connector, 0, 0, 0, 0));

			break;
		}
		case SOUTH: {
			pipe_m.addQuad(RenderUtils.makeTopFace(min, max, max, 1.f, max,
					pipe.connector, pipe.connector.getMinU(),
					pipe.connector.getInterpolatedU(8.f),
					pipe.connector.getInterpolatedV(4.f),
					pipe.connector.getMaxV()));
			pipe_m.addQuad(RenderUtils.makeBottomFace(min, max, max, 1.f, min,
					pipe.connector, pipe.connector.getMinU(),
					pipe.connector.getInterpolatedU(8.f),
					pipe.connector.getInterpolatedV(4.f),
					pipe.connector.getMaxV()));
			pipe_m.addQuad(RenderUtils.makeEastFace(min, max, max, 1.f, max,
					pipe.connector, pipe.connector.getMinU(),
					pipe.connector.getInterpolatedU(8.f),
					pipe.connector.getInterpolatedV(4.f),
					pipe.connector.getMaxV()));
			pipe_m.addQuad(RenderUtils.makeWestFace(min, max, max, 1.f, min,
					pipe.connector, pipe.connector.getMinU(),
					pipe.connector.getInterpolatedU(8.f),
					pipe.connector.getInterpolatedV(4.f),
					pipe.connector.getMaxV()));

			pipe_liquid_m.addQuad(RenderUtils.makeTopFace(min, max, max, 1.f,
					max - .01f, pipe.connector, 0, 0, 0, 0));
			pipe_liquid_m.addQuad(RenderUtils.makeBottomFace(min, max, max,
					1.f, min + .01f, pipe.connector, 0, 0, 0, 0));
			pipe_liquid_m.addQuad(RenderUtils.makeEastFace(min, max, max, 1.f,
					max - .01f, pipe.connector, 0, 0, 0, 0));
			pipe_liquid_m.addQuad(RenderUtils.makeWestFace(min, max, max, 1.f,
					min + .01f, pipe.connector, 0, 0, 0, 0));

			break;
		}
		case EAST: {
			break;
		}
		case WEST: {
			break;
		}
		}
	}

	private void renderPipe(BlockSuctionPipe pipe, int x, int y, int z,
			boolean[] con_pipes, boolean[] con_tanks, boolean output,
			FluidStack fluid) {
		if (!initialized)
			createPipe(pipe);

		RenderUtils.renderDoubleSidedCube(x, y, z, 6.f/16.f, 10.f/16.f, 6.f/16.f, 10.f/16.f, 6.f/16.f, 10.f/16.f, pipe.center);

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
				pipes[i].location = new RenderPoint(x, y, z);
				pipes[i].draw();

				if (fluid != null) {
					pipes_liquid[i].location = new RenderPoint(x, y, z);
					for (RenderQuad quad : pipes_liquid[i].faces) {
						Icon icon_f = fluid.getFluid().getIcon();
						quad.icon = icon_f;
						quad.minU = icon_f.getInterpolatedU(10.f);
						quad.maxU = icon_f.getMaxU();
						quad.minV = icon_f.getInterpolatedV(7.f);
						quad.maxV = icon_f.getInterpolatedV(9.f);
					}
					pipes_liquid[i].draw();
				}
			}

			if (con_tanks[i]) {
				tanks[i].location = new RenderPoint(x, y, z);
				tanks[i].draw();

				if (fluid != null) {
					tanks_liquid[i].location = new RenderPoint(x, y, z);
					for (RenderQuad quad : tanks_liquid[i].faces) {
						Icon icon_f = fluid.getFluid().getIcon();
						quad.icon = icon_f;
						quad.minU = icon_f.getInterpolatedU(10.f);
						quad.maxU = icon_f.getInterpolatedU(13.f);
						quad.minV = icon_f.getInterpolatedV(7.f);
						quad.maxV = icon_f.getInterpolatedV(9.f);
					}
					tanks_liquid[i].draw();
				}
			}
		}
	}
}
