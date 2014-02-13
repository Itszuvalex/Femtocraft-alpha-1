package femtocraft.industry.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import femtocraft.industry.TileEntity.VacuumTubeTile;
import femtocraft.industry.blocks.VacuumTube;
import femtocraft.proxy.ClientProxyFemtocraft;
import femtocraft.render.FemtocraftRenderUtils;
import femtocraft.render.Model;
import femtocraft.render.Point;
import femtocraft.render.Quad;

public class VacuumTubeRenderer implements ISimpleBlockRenderingHandler {
	// Full Outside Models
	private Model outNorth, inNorth;
	private Model outSouth, inSouth;
	private Model outEast, inEast;
	private Model outWest, inWest;
	private Model outUp, inUp;
	private Model outDown, inDown;
	// Indicators
	private Model outNorthFarOn, outNorthFarOff, outNorthCloseOn,
			outNorthCloseOff;
	private Model inNorthFarOn, inNorthFarOff, inNorthFarOverflow,
			inNorthCloseOn, inNorthCloseOff;
	private Model outSouthFarOn, outSouthFarOff, outSouthCloseOn,
			outSouthCloseOff;
	private Model inSouthFarOn, inSouthFarOff, inSouthFarOverflow,
			inSouthCloseOn, inSouthCloseOff;
	private Model outUpFarOn, outUpFarOff, outUpCloseOn, outUpCloseOff;
	private Model inUpFarOn, inUpFarOff, inUpFarOverflow, inUpCloseOn,
			inUpCloseOff;
	private Model outDownFarOn, outDownFarOff, outDownCloseOn, outDownCloseOff;
	private Model inDownFarOn, inDownFarOff, inDownFarOverflow, inDownCloseOn,
			inDownCloseOff;
	private Model outEastFarOn, outEastFarOff, outEastCloseOn, outEastCloseOff;
	private Model inEastFarOn, inEastFarOff, inEastFarOverflow, inEastCloseOn,
			inEastCloseOff;
	private Model outWestFarOn, outWestFarOff, outWestCloseOn, outWestCloseOff;
	private Model inWestFarOn, inWestFarOff, inWestFarOverflow, inWestCloseOn,
			inWestCloseOff;

	// Center Models
	// Straight
	private Model centerStraightSouthToNorth, centerStraightNorthToSouth;
	private Model centerStraightEastToWest, centerStraightWestToEast;
	private Model centerStraightDownToUp, centerStraightUpToDown;
	// Ends
	private Model centerEndNorth, centerEndSouth, centerEndEast, centerEndWest,
			centerEndUp, centerEndDown;
	// Curved
	private Model centerCurvedNorthToUp, centerCurvedNorthToDown,
			centerCurvedNorthToEast, centerCurvedNorthToWest;
	private Model centerCurvedSouthToUp, centerCurvedSouthToDown,
			centerCurvedSouthToEast, centerCurvedSouthToWest;
	private Model centerCurvedUpToNorth, centerCurvedUpToSouth,
			centerCurvedUpToEast, centerCurvedUpToWest;
	private Model centerCurvedDownToNorth, centerCurvedDownToSouth,
			centerCurvedDownToEast, centerCurvedDownToWest;
	private Model centerCurvedEastToNorth, centerCurvedEastToSouth,
			centerCurvedEastToUp, centerCurvedEastToDown;
	private Model centerCurvedWestToNorth, centerCurvedWestToSouth,
			centerCurvedWestToUp, centerCurvedWestToDown;

	boolean initialized = false;

	public VacuumTubeRenderer() {

	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		VacuumTube tube = (VacuumTube) block;
		if (tube == null)
			return;

		Tessellator tessellator = Tessellator.instance;

		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(1, 1, 1);
		renderTube(tube, 0, 0, 0, new boolean[] { false, false, false, false },
				false, ForgeDirection.NORTH, ForgeDirection.SOUTH, false, false);
		tessellator.draw();

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {

		if (!(block instanceof VacuumTube))
			return false;
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile == null)
			return false;
		if (!(tile instanceof VacuumTubeTile))
			return false;
		VacuumTubeTile tube = (VacuumTubeTile) tile;
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(
				renderer.blockAccess, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		renderTube((VacuumTube) block, x, y, z, tube.hasItem,
				tube.isOverflowing(), tube.getInput(), tube.getOutput(),
				!tube.missingInput(), !tube.missingOutput());

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

	private void renderTube(VacuumTube tube, int x, int y, int z,
			boolean[] hasItem, boolean isOverflowing, ForgeDirection in,
			ForgeDirection out, boolean hasInput, boolean hasOutput) {
		if (!initialized)
			initializeModels(tube);

		renderIn(x, y, z, in, hasItem, hasInput, isOverflowing);
		renderOut(x, y, z, out, hasItem, hasOutput);
		renderCenter(x, y, z, in, out, hasItem, hasInput, hasOutput);
	}

	private void renderIn(int x, int y, int z, ForgeDirection in,
			boolean[] hasItem, boolean hasInput, boolean isOverflowing) {
		Model end = null;
		Model indicator = null;
		switch (in) {
		case NORTH:
			end = inNorth;

			if (isOverflowing) {
				indicator = inNorthFarOverflow;
			} else if (hasItem[0]) {
				indicator = inNorthFarOn;
			} else {
				indicator = inNorthFarOff;
			}

			break;
		case SOUTH:
			end = inSouth;

			if (isOverflowing) {
				indicator = inSouthFarOverflow;
			} else if (hasItem[0]) {
				indicator = inSouthFarOn;
			} else {
				indicator = inSouthFarOff;
			}
			break;
		case EAST:
			end = inEast;

			if (isOverflowing) {
				indicator = inEastFarOverflow;
			} else if (hasItem[0]) {
				indicator = inEastFarOn;
			} else {
				indicator = inEastFarOff;
			}
			break;
		case WEST:
			end = inWest;

			if (isOverflowing) {
				indicator = inWestFarOverflow;
			} else if (hasItem[0]) {
				indicator = inWestFarOn;
			} else {
				indicator = inWestFarOff;
			}
			break;
		case UP:
			end = inUp;

			if (isOverflowing) {
				indicator = inUpFarOverflow;
			} else if (hasItem[0]) {
				indicator = inUpFarOn;
			} else {
				indicator = inUpFarOff;
			}
			break;
		case DOWN:
			end = inDown;

			if (isOverflowing) {
				indicator = inDownFarOverflow;
			} else if (hasItem[0]) {
				indicator = inDownFarOn;
			} else {
				indicator = inDownFarOff;
			}
			break;
		case UNKNOWN:
			break;
		default:
			break;
		}

		if (hasInput) {
			if (end != null) {
				end.location = new Point(x, y, z);
				end.draw();
			}
			if (indicator != null) {
				indicator.location = new Point(x, y, z);
				indicator.draw();
			}
		}
	}

	private void renderOut(int x, int y, int z, ForgeDirection out,
			boolean[] hasItem, boolean hasOutput) {
		Model end = null;
		Model indicator = null;
		switch (out) {
		case NORTH:
			end = outNorth;

			if (hasItem[3]) {
				indicator = outNorthFarOn;
			} else {
				indicator = outNorthFarOff;
			}

			break;
		case SOUTH:
			end = outSouth;

			if (hasItem[3]) {
				indicator = outSouthFarOn;
			} else {
				indicator = outSouthFarOff;
			}
			break;
		case EAST:
			end = outEast;

			if (hasItem[3]) {
				indicator = outEastFarOn;
			} else {
				indicator = outEastFarOff;
			}
			break;
		case WEST:
			end = outWest;

			if (hasItem[3]) {
				indicator = outWestFarOn;
			} else {
				indicator = outWestFarOff;
			}
			break;
		case UP:
			end = outUp;

			if (hasItem[3]) {
				indicator = outUpFarOn;
			} else {
				indicator = outUpFarOff;
			}
			break;
		case DOWN:
			end = outDown;

			if (hasItem[3]) {
				indicator = outDownFarOn;
			} else {
				indicator = outDownFarOff;
			}
			break;
		case UNKNOWN:
			break;
		default:
			break;
		}

		if (hasOutput) {
			if (end != null) {
				end.location = new Point(x, y, z);
				end.draw();
			}
			if (indicator != null) {
				indicator.location = new Point(x, y, z);
				indicator.draw();
			}
		}
	}

	private void renderCenter(int x, int y, int z, ForgeDirection in,
			ForgeDirection out, boolean[] hasItem, boolean hasInput,
			boolean hasOutput) {
		Model c;

		switch (in) {
		case NORTH:
			if (!hasInput) {
				centerEndNorth.location = new Point(x, y, z);
				centerEndNorth.draw();
			}

			if (hasItem[1]) {
				inNorthCloseOn.location = new Point(x, y, z);
				inNorthCloseOn.draw();
			} else {
				inNorthCloseOff.location = new Point(x, y, z);
				inNorthCloseOff.draw();
			}

			switch (out) {
			case EAST:
				c = centerCurvedNorthToEast;
				break;
			case WEST:
				c = centerCurvedNorthToWest;
				break;
			case UP:
				c = centerCurvedNorthToUp;
				break;
			case DOWN:
				c = centerCurvedNorthToDown;
				break;
			case UNKNOWN:
				centerEndSouth.location = new Point(x, y, z);
				centerEndSouth.draw();

				if (hasItem[2]) {
					outSouthCloseOn.location = new Point(x, y, z);
					outSouthCloseOn.draw();
				} else {
					outSouthCloseOff.location = new Point(x, y, z);
					outSouthCloseOff.draw();
				}
			default:
				c = centerStraightNorthToSouth;
				break;
			}
			break;
		case SOUTH:
			if (!hasInput) {
				centerEndSouth.location = new Point(x, y, z);
				centerEndSouth.draw();
			}

			if (hasItem[1]) {
				inSouthCloseOn.location = new Point(x, y, z);
				inSouthCloseOn.draw();
			} else {
				inSouthCloseOff.location = new Point(x, y, z);
				inSouthCloseOff.draw();
			}

			switch (out) {
			case EAST:
				c = centerCurvedSouthToEast;
				break;
			case WEST:
				c = centerCurvedSouthToWest;
				break;
			case UP:
				c = centerCurvedSouthToUp;
				break;
			case DOWN:
				c = centerCurvedSouthToDown;
				break;
			case UNKNOWN:
				centerEndNorth.location = new Point(x, y, z);
				centerEndNorth.draw();

				if (hasItem[2]) {
					outNorthCloseOn.location = new Point(x, y, z);
					outNorthCloseOn.draw();
				} else {
					outNorthCloseOff.location = new Point(x, y, z);
					outNorthCloseOff.draw();
				}

			default:
				c = centerStraightSouthToNorth;
				break;
			}
			break;
		case EAST:
			if (!hasInput) {
				centerEndEast.location = new Point(x, y, z);
				centerEndEast.draw();
			}

			if (hasItem[1]) {
				inEastCloseOn.location = new Point(x, y, z);
				inEastCloseOn.draw();
			} else {
				inEastCloseOff.location = new Point(x, y, z);
				inEastCloseOff.draw();
			}

			switch (out) {
			case NORTH:
				c = centerCurvedEastToNorth;
				break;
			case SOUTH:
				c = centerCurvedEastToSouth;
				break;
			case UP:
				c = centerCurvedEastToUp;
				break;
			case DOWN:
				c = centerCurvedEastToDown;
				break;
			case UNKNOWN:
				centerEndWest.location = new Point(x, y, z);
				centerEndWest.draw();

				if (hasItem[2]) {
					outWestCloseOn.location = new Point(x, y, z);
					outWestCloseOn.draw();
				} else {
					outWestCloseOff.location = new Point(x, y, z);
					outWestCloseOff.draw();
				}
			default:
				c = centerStraightEastToWest;
				break;
			}
			break;
		case WEST:
			if (!hasInput) {
				centerEndWest.location = new Point(x, y, z);
				centerEndWest.draw();
			}

			if (hasItem[1]) {
				inWestCloseOn.location = new Point(x, y, z);
				inWestCloseOn.draw();
			} else {
				inWestCloseOff.location = new Point(x, y, z);
				inWestCloseOff.draw();
			}

			switch (out) {
			case NORTH:
				c = centerCurvedWestToNorth;
				break;
			case SOUTH:
				c = centerCurvedWestToSouth;
				break;
			case UP:
				c = centerCurvedWestToUp;
				break;
			case DOWN:
				c = centerCurvedWestToDown;
				break;
			case UNKNOWN:
				centerEndEast.location = new Point(x, y, z);
				centerEndEast.draw();

				if (hasItem[2]) {
					outEastCloseOn.location = new Point(x, y, z);
					outEastCloseOn.draw();
				} else {
					outEastCloseOff.location = new Point(x, y, z);
					outEastCloseOff.draw();
				}
			default:
				c = centerStraightWestToEast;
				break;
			}
			break;
		case UP:
			if (!hasInput) {
				centerEndUp.location = new Point(x, y, z);
				centerEndUp.draw();
			}

			if (hasItem[1]) {
				inUpCloseOn.location = new Point(x, y, z);
				inUpCloseOn.draw();
			} else {
				inUpCloseOff.location = new Point(x, y, z);
				inUpCloseOff.draw();
			}

			switch (out) {
			case NORTH:
				c = centerCurvedUpToNorth;
				break;
			case SOUTH:
				c = centerCurvedUpToSouth;
				break;
			case EAST:
				c = centerCurvedUpToEast;
				break;
			case WEST:
				c = centerCurvedUpToWest;
				break;
			case UNKNOWN:
				centerEndDown.location = new Point(x, y, z);
				centerEndDown.draw();

				if (hasItem[2]) {
					outDownCloseOn.location = new Point(x, y, z);
					outDownCloseOn.draw();
				} else {
					outDownCloseOff.location = new Point(x, y, z);
					outDownCloseOff.draw();
				}
			default:
				c = centerStraightUpToDown;
				break;
			}
			break;
		case DOWN:
			if (!hasInput) {
				centerEndDown.location = new Point(x, y, z);
				centerEndDown.draw();
			}

			if (hasItem[1]) {
				inDownCloseOn.location = new Point(x, y, z);
				inDownCloseOn.draw();
			} else {
				inDownCloseOff.location = new Point(x, y, z);
				inDownCloseOff.draw();
			}

			switch (out) {
			case NORTH:
				c = centerCurvedDownToNorth;
				break;
			case SOUTH:
				c = centerCurvedDownToSouth;
				break;
			case EAST:
				c = centerCurvedDownToEast;
				break;
			case WEST:
				c = centerCurvedDownToWest;
				break;
			case UNKNOWN:
				centerEndUp.location = new Point(x, y, z);
				centerEndUp.draw();

				if (hasItem[2]) {
					outUpCloseOn.location = new Point(x, y, z);
					outUpCloseOn.draw();
				} else {
					outUpCloseOff.location = new Point(x, y, z);
					outUpCloseOff.draw();
				}
			default:
				c = centerStraightDownToUp;
				break;
			}
			break;
		default:
			switch (out) {
			case NORTH:
				if (hasItem[1]) {
					inSouthCloseOn.location = new Point(x, y, z);
					inSouthCloseOn.draw();
				} else {
					inSouthCloseOff.location = new Point(x, y, z);
					inSouthCloseOff.draw();
				}

				c = centerStraightSouthToNorth;
				centerEndSouth.location = new Point(x, y, z);
				centerEndSouth.draw();
				break;
			case SOUTH:

				if (hasItem[1]) {
					inNorthCloseOn.location = new Point(x, y, z);
					inNorthCloseOn.draw();
				} else {
					inNorthCloseOff.location = new Point(x, y, z);
					inNorthCloseOff.draw();
				}

				c = centerStraightNorthToSouth;
				centerEndNorth.location = new Point(x, y, z);
				centerEndNorth.draw();
				break;
			case EAST:
				if (hasItem[1]) {
					inWestCloseOn.location = new Point(x, y, z);
					inWestCloseOn.draw();
				} else {
					inWestCloseOff.location = new Point(x, y, z);
					inWestCloseOff.draw();
				}
				c = centerStraightWestToEast;
				centerEndWest.location = new Point(x, y, z);
				centerEndWest.draw();
				break;
			case WEST:
				if (hasItem[1]) {
					inEastCloseOn.location = new Point(x, y, z);
					inEastCloseOn.draw();
				} else {
					inEastCloseOff.location = new Point(x, y, z);
					inEastCloseOff.draw();
				}

				c = centerStraightEastToWest;
				centerEndEast.location = new Point(x, y, z);
				centerEndEast.draw();
				break;
			case UP:
				if (hasItem[1]) {
					inDownCloseOn.location = new Point(x, y, z);
					inDownCloseOn.draw();
				} else {
					inDownCloseOff.location = new Point(x, y, z);
					inDownCloseOff.draw();
				}

				c = centerStraightDownToUp;
				centerEndUp.location = new Point(x, y, z);
				centerEndUp.draw();
				break;
			case DOWN:
				if (hasItem[1]) {
					inUpCloseOn.location = new Point(x, y, z);
					inUpCloseOn.draw();
				} else {
					inUpCloseOff.location = new Point(x, y, z);
					inUpCloseOff.draw();
				}

				c = centerStraightUpToDown;
				centerEndDown.location = new Point(x, y, z);
				centerEndDown.draw();
				break;
			case UNKNOWN:
				if (hasItem[1]) {
					inSouthCloseOn.location = new Point(x, y, z);
					inSouthCloseOn.draw();
				} else {
					inSouthCloseOff.location = new Point(x, y, z);
					inSouthCloseOff.draw();
				}

				centerEndNorth.location = new Point(x, y, z);
				centerEndNorth.draw();
			default:
				c = centerStraightSouthToNorth;
				centerEndSouth.location = new Point(x, y, z);
				centerEndSouth.draw();

				if (hasItem[2]) {
					outNorthCloseOn.location = new Point(x, y, z);
					outNorthCloseOn.draw();
				} else {
					outNorthCloseOff.location = new Point(x, y, z);
					outNorthCloseOff.draw();
				}
				break;
			}
			break;
		}

		renderCenterHelper(c, x, y, z, out, hasItem[2], hasOutput);
	}

	private void renderCenterHelper(Model centermodel, int x, int y, int z,
			ForgeDirection out, boolean hasItem, boolean hasOutput) {
		centermodel.location = new Point(x, y, z);
		centermodel.draw();

		switch (out) {
		case NORTH:
			if (hasItem) {
				outNorthCloseOn.location = new Point(x, y, z);
				outNorthCloseOn.draw();
			} else {
				outNorthCloseOff.location = new Point(x, y, z);
				outNorthCloseOff.draw();
			}

			if (!hasOutput) {
				centerEndNorth.location = new Point(x, y, z);
				centerEndNorth.draw();
			}

			break;
		case SOUTH:
			if (hasItem) {
				outSouthCloseOn.location = new Point(x, y, z);
				outSouthCloseOn.draw();
			} else {
				outSouthCloseOff.location = new Point(x, y, z);
				outSouthCloseOff.draw();
			}

			if (!hasOutput) {
				centerEndSouth.location = new Point(x, y, z);
				centerEndSouth.draw();
			}

			break;
		case EAST:
			if (hasItem) {
				outEastCloseOn.location = new Point(x, y, z);
				outEastCloseOn.draw();
			} else {
				outEastCloseOff.location = new Point(x, y, z);
				outEastCloseOff.draw();
			}

			if (!hasOutput) {
				centerEndEast.location = new Point(x, y, z);
				centerEndEast.draw();
			}

			break;
		case WEST:
			if (hasItem) {
				outWestCloseOn.location = new Point(x, y, z);
				outWestCloseOn.draw();
			} else {
				outWestCloseOff.location = new Point(x, y, z);
				outWestCloseOff.draw();
			}

			if (!hasOutput) {
				centerEndWest.location = new Point(x, y, z);
				centerEndWest.draw();
			}

			break;
		case UP:
			if (hasItem) {
				outUpCloseOn.location = new Point(x, y, z);
				outUpCloseOn.draw();
			} else {
				outUpCloseOff.location = new Point(x, y, z);
				outUpCloseOff.draw();
			}

			if (!hasOutput) {
				centerEndUp.location = new Point(x, y, z);
				centerEndUp.draw();
			}

			break;
		case DOWN:
			if (hasItem) {
				outDownCloseOn.location = new Point(x, y, z);
				outDownCloseOn.draw();
			} else {
				outDownCloseOff.location = new Point(x, y, z);
				outDownCloseOff.draw();
			}

			if (!hasOutput) {
				centerEndDown.location = new Point(x, y, z);
				centerEndDown.draw();
			}

			break;
		default:
			break;
		}
	}

	private void initializeModels(VacuumTube tube) {
		createCenters(tube);
		createIndicators(tube);
		createEnds(tube);
		createCenterEnds(tube);
		initialized = true;
	}

	private void createCenters(VacuumTube tube) {
		// Straight
		centerStraightSouthToNorth = createStraightCenter(tube);
		centerStraightNorthToSouth = centerStraightSouthToNorth
				.rotatedToDirection(ForgeDirection.SOUTH);
		centerStraightEastToWest = centerStraightSouthToNorth
				.rotatedToDirection(ForgeDirection.WEST);
		centerStraightWestToEast = centerStraightSouthToNorth
				.rotatedToDirection(ForgeDirection.EAST);
		centerStraightUpToDown = centerStraightSouthToNorth
				.rotatedToDirection(ForgeDirection.DOWN);
		centerStraightDownToUp = centerStraightSouthToNorth
				.rotatedToDirection(ForgeDirection.UP);

		// Curved
		centerCurvedWestToNorth = createCurvedCenter(tube);
		centerCurvedWestToSouth = centerCurvedWestToNorth
				.rotatedOnXAxis(Math.PI);
		centerCurvedWestToUp = centerCurvedWestToNorth
				.rotatedOnXAxis(Math.PI / 2.d);
		centerCurvedWestToDown = centerCurvedWestToNorth
				.rotatedOnXAxis(-Math.PI / 2.d);

		centerCurvedEastToNorth = centerCurvedWestToNorth
				.rotatedOnZAxis(Math.PI);
		centerCurvedEastToSouth = centerCurvedWestToNorth.rotatedOnZAxis(
				Math.PI).rotatedOnXAxis(Math.PI);
		centerCurvedEastToUp = centerCurvedWestToNorth.rotatedOnZAxis(Math.PI)
				.rotatedOnXAxis(Math.PI / 2.d);
		centerCurvedEastToDown = centerCurvedWestToNorth
				.rotatedOnZAxis(Math.PI).rotatedOnXAxis(-Math.PI / 2.d);

		centerCurvedNorthToEast = centerCurvedWestToNorth
				.rotatedOnYAxis(-Math.PI / 2.d);
		centerCurvedNorthToWest = centerCurvedWestToNorth.rotatedOnYAxis(
				Math.PI / 2.d).rotateOnXAxis(Math.PI);
		centerCurvedNorthToUp = centerCurvedWestToNorth.rotatedOnYAxis(
				-Math.PI / 2.d).rotatedOnZAxis(Math.PI / 2.d);
		centerCurvedNorthToDown = centerCurvedWestToNorth.rotatedOnYAxis(
				-Math.PI / 2.d).rotatedOnZAxis(-Math.PI / 2.d);

		centerCurvedSouthToEast = centerCurvedWestToNorth.rotatedOnZAxis(
				Math.PI).rotatedOnYAxis(-Math.PI / 2.d);
		centerCurvedSouthToWest = centerCurvedWestToNorth
				.rotatedOnYAxis(Math.PI / 2.d);
		centerCurvedSouthToUp = centerCurvedWestToNorth.rotatedOnYAxis(
				Math.PI / 2.d).rotatedOnZAxis(-Math.PI / 2.d);
		centerCurvedSouthToDown = centerCurvedWestToNorth.rotatedOnYAxis(
				Math.PI / 2.d).rotatedOnZAxis(Math.PI / 2.d);

		centerCurvedUpToNorth = centerCurvedWestToNorth
				.rotatedOnZAxis(-Math.PI / 2.d);
		centerCurvedUpToSouth = centerCurvedWestToNorth.rotatedOnZAxis(
				Math.PI / 2.d).rotatedOnXAxis(Math.PI);
		centerCurvedUpToEast = centerCurvedWestToNorth.rotatedOnZAxis(
				-Math.PI / 2.d).rotatedOnYAxis(-Math.PI / 2.d);
		centerCurvedUpToWest = centerCurvedWestToNorth.rotatedOnZAxis(
				-Math.PI / 2.d).rotatedOnYAxis(Math.PI / 2.d);

		centerCurvedDownToNorth = centerCurvedWestToNorth
				.rotatedOnZAxis(Math.PI / 2.d);
		centerCurvedDownToSouth = centerCurvedWestToNorth.rotatedOnZAxis(
				Math.PI / 2.d).rotatedOnYAxis(Math.PI);
		centerCurvedDownToEast = centerCurvedWestToNorth.rotatedOnZAxis(
				Math.PI / 2.d).rotatedOnYAxis(-Math.PI / 2.d);
		centerCurvedDownToWest = centerCurvedWestToNorth.rotatedOnZAxis(
				Math.PI / 2.d).rotatedOnYAxis(Math.PI / 2.d);
	}

	private Model createCurvedCenter(VacuumTube tube) {
		// This will return a WestToNorth Curve
		Model ret = new Model(new Point(0, 0, 0), new Point(.5f, .5f, .5f));

		float min = 4.f / 16.f;
		float max = 12.f / 16.f;

		float minU = tube.turnIcon.getInterpolatedU(4.f);
		float maxU = tube.turnIcon.getInterpolatedU(12.f);
		float minV = tube.turnIcon.getInterpolatedV(4.f);
		float maxV = tube.turnIcon.getInterpolatedV(12.f);

		float sideMinU = tube.straightIcon.getInterpolatedU(4.f);
		float sideMaxU = tube.straightIcon.getInterpolatedU(12.f);
		float sideMinV = tube.straightIcon.getInterpolatedV(4.f);
		float sideMaxV = tube.straightIcon.getInterpolatedV(12.f);

		Quad top = FemtocraftRenderUtils.makeTopFace(min, max, min, max, max,
				tube.turnIcon, minU, maxU, minV, maxV).rotateOnYAxis(
				-Math.PI / 2.d, .5f, .5f);
		Quad top_r = FemtocraftRenderUtils.makeBottomFace(min, max, min, max,
				max - 3.f / 16.f, tube.turnIcon, minU, maxU, minV, maxV)
				.rotateOnYAxis(Math.PI / 2.d, .5f, .5f);

		Quad bot = FemtocraftRenderUtils.makeBottomFace(min, max, min, max,
				min, tube.turnIcon, minU, maxU, minV, maxV).rotateOnYAxis(
				Math.PI / 2.d, .5f, .5f);
		Quad bot_r = FemtocraftRenderUtils.makeTopFace(min, max, min, max,
				min + 3.f / 16.f, tube.turnIcon, minU, maxU, minV, maxV)
				.rotateOnYAxis(-Math.PI / 2.d, .5f, .5f);

		Quad east = FemtocraftRenderUtils.makeEastFace(min, max, min, max, max,
				tube.straightIcon, sideMinU, sideMaxU, sideMinV, sideMaxV);
		Quad east_r = FemtocraftRenderUtils.makeWestFace(min, max, min, max,
				max - 3.f / 16.f, tube.straightIcon, sideMinU, sideMaxU,
				sideMinV, sideMaxV).rotateOnXAxis(Math.PI / 2.d, .5f, .5f);

		Quad south = FemtocraftRenderUtils.makeSouthFace(min, max, min, max,
				max, tube.straightIcon, sideMinU, sideMaxU, sideMinV, sideMaxV)
				.rotateOnZAxis(Math.PI / 2.d, .5f, .5f);
		Quad south_r = FemtocraftRenderUtils.makeNorthFace(min, max, min, max,
				max - 3.f / 16.f, tube.straightIcon, sideMinU, sideMaxU,
				sideMinV, sideMaxV);

		minU = tube.turnInsetIcon.getInterpolatedU(4.f);
		maxU = tube.turnInsetIcon.getInterpolatedU(12.f);
		minV = tube.turnInsetIcon.getInterpolatedV(4.f);
		maxV = tube.turnInsetIcon.getInterpolatedV(12.f);

		sideMinU = tube.straightInsetIcon.getInterpolatedU(4.f);
		sideMaxU = tube.straightInsetIcon.getInterpolatedU(12.f);
		sideMinV = tube.straightInsetIcon.getInterpolatedV(4.f);
		sideMaxV = tube.straightInsetIcon.getInterpolatedV(12.f);

		Quad top_inset = FemtocraftRenderUtils.makeTopFace(min, max, min, max,
				max - 1.f / 16.f, tube.turnInsetIcon, minU, maxU, maxV, minV)
				.rotateOnYAxis(0, .5f, .5f);
		Quad bot_inset = FemtocraftRenderUtils.makeBottomFace(min, max, min,
				max, min + 1.f / 16.f, tube.turnInsetIcon, minU, maxU, minV,
				maxV).rotatedOnYAxis(Math.PI / 2.d, .5f, .5f);
		Quad east_inset = FemtocraftRenderUtils.makeEastFace(min, max, min,
				max, max - 1.f / 16.f, tube.straightInsetIcon, sideMaxU,
				sideMinU, sideMinV, sideMaxV);
		Quad south_inset = FemtocraftRenderUtils.makeSouthFace(min, max, min,
				max, max - 1.f / 16.f, tube.straightInsetIcon, sideMaxU,
				sideMinU, sideMinV, sideMaxV).rotateOnZAxis(Math.PI / 2.d, .5f,
				.5f);

		minU = tube.straightIcon.getInterpolatedU(4.f);
		maxU = tube.straightIcon.getInterpolatedU(7.f);
		minV = tube.straightIcon.getInterpolatedV(4.f);
		maxV = tube.straightIcon.getInterpolatedV(12.f);

		Quad west_inset = FemtocraftRenderUtils.makeSouthFace(min, max, min,
				7.f / 16.f, min + 3.f / 16.f, tube.straightIcon, minU, maxU,
				minV, maxV).rotateOnZAxis(-Math.PI / 2.d, .5f, .5f);

		minU = tube.straightIcon.getInterpolatedU(9.f);
		maxU = tube.straightIcon.getInterpolatedU(12.f);

		Quad north_inset = FemtocraftRenderUtils.makeEastFace(min, max, min,
				min + 3.f / 16.f, 7.f / 16.f, tube.straightIcon, minU, maxU,
				minV, maxV);

		ret.addQuad(top);
		ret.addQuad(top_r);
		ret.addQuad(bot);
		ret.addQuad(bot_r);
		ret.addQuad(east);
		ret.addQuad(east_r);
		ret.addQuad(south);
		ret.addQuad(south_r);

		ret.addQuad(top_inset);
		ret.addQuad(bot_inset);
		ret.addQuad(east_inset);
		ret.addQuad(south_inset);

		ret.addQuad(west_inset);
		ret.addQuad(north_inset);

		return ret;
	}

	private Model createStraightCenter(VacuumTube tube) {
		Model ret = new Model(new Point(0, 0, 0), new Point(.5f, .5f, .5f));

		float min = 4.f / 16.f;
		float max = 12.0f / 16.f;

		float minU = tube.straightIcon.getInterpolatedU(4.f);
		float maxU = tube.straightIcon.getInterpolatedU(12.f);
		float minV = tube.straightIcon.getInterpolatedV(4.f);
		float maxV = tube.straightIcon.getInterpolatedV(12.f);

		Quad top = FemtocraftRenderUtils.makeTopFace(min, max, min, max, max,
				tube.straightIcon, minU, maxU, minV, maxV).rotateOnYAxis(
				Math.PI / 2.d, .5f, .5f);
		Quad top_r = FemtocraftRenderUtils.makeBottomFace(min, max, min, max,
				max - 3.f / 16.f, tube.straightIcon, minU, maxU, minV, maxV);

		Quad bot = FemtocraftRenderUtils.makeBottomFace(min, max, min, max,
				min, tube.straightIcon, minU, maxU, minV, maxV);
		Quad bot_r = FemtocraftRenderUtils.makeTopFace(min, max, min, max,
				min + 3.f / 16.f, tube.straightIcon, minU, maxU, minV, maxV)
				.rotateOnYAxis(Math.PI / 2.d, .5f, .5f);

		Quad east = FemtocraftRenderUtils.makeEastFace(min, max, min, max, max,
				tube.straightIcon, minU, maxU, minV, maxV);
		Quad east_r = FemtocraftRenderUtils.makeWestFace(min, max, min, max,
				max - 3.f / 16.f, tube.straightIcon, minU, maxU, minV, maxV)
				.rotateOnXAxis(Math.PI / 2.d, .5f, .5f);

		Quad west = FemtocraftRenderUtils.makeWestFace(min, max, min, max, min,
				tube.straightIcon, minU, maxU, minV, maxV).rotateOnXAxis(
				Math.PI / 2.d, .5f, .5f);
		Quad west_r = FemtocraftRenderUtils.makeEastFace(min, max, min, max,
				min + 3.f / 16.f, tube.straightIcon, minU, maxU, minV, maxV);

		minU = tube.straightInsetIcon.getInterpolatedU(12.f);
		maxU = tube.straightInsetIcon.getInterpolatedU(4.f);
		minV = tube.straightInsetIcon.getInterpolatedV(4.f);
		maxV = tube.straightInsetIcon.getInterpolatedV(12.f);

		Quad top_inset = FemtocraftRenderUtils.makeTopFace(min, max, min, max,
				max - 1.f / 16.f, tube.straightInsetIcon, maxU, minU, minV,
				maxV).rotateOnYAxis(Math.PI / 2.d, .5f, .5f);
		Quad bot_inset = FemtocraftRenderUtils.makeBottomFace(min, max, min,
				max, min + 1.f / 16.f, tube.straightInsetIcon, minU, maxU,
				minV, maxV);
		Quad east_inset = FemtocraftRenderUtils.makeEastFace(min, max, min,
				max, max - 1.f / 16.f, tube.straightInsetIcon, minU, maxU,
				minV, maxV);
		Quad west_inset = FemtocraftRenderUtils.makeWestFace(min, max, min,
				max, min + 1.f / 16.f, tube.straightInsetIcon, minU, maxU,
				minV, maxV).rotateOnXAxis(Math.PI / 2.d, .5f, .5f);

		ret.addQuad(top);
		ret.addQuad(top_r);
		ret.addQuad(bot);
		ret.addQuad(bot_r);
		ret.addQuad(east);
		ret.addQuad(east_r);
		ret.addQuad(west);
		ret.addQuad(west_r);

		ret.addQuad(top_inset);
		ret.addQuad(bot_inset);
		ret.addQuad(east_inset);
		ret.addQuad(west_inset);

		return ret;
	}

	private void createIndicators(VacuumTube tube) {
		// North
		inNorthFarOn = createIndicator(tube, 6.f / 16.f, true, false, true);
		inNorthFarOff = createIndicator(tube, 6.f / 16.f, false, false, true);
		inNorthFarOverflow = createIndicator(tube, 6.f / 16.f, true, true, true);
		outNorthFarOn = createIndicator(tube, 6.f / 16.f, true, false, false);
		outNorthFarOff = createIndicator(tube, 6.f / 16.f, false, false, false);
		inNorthCloseOn = createIndicator(tube, 2.f / 16.f, true, false, true);
		inNorthCloseOff = createIndicator(tube, 2.f / 16.f, false, false, true);
		outNorthCloseOn = createIndicator(tube, 2.f / 16.f, true, false, false);
		outNorthCloseOff = createIndicator(tube, 2.f / 16.f, false, false,
				false);

		// South
		inSouthFarOn = inNorthFarOn.rotatedToDirection(ForgeDirection.SOUTH);
		inSouthFarOff = inNorthFarOff.rotatedToDirection(ForgeDirection.SOUTH);
		inSouthFarOverflow = inNorthFarOverflow
				.rotatedToDirection(ForgeDirection.SOUTH);
		outSouthFarOn = outNorthFarOn.rotatedToDirection(ForgeDirection.SOUTH);
		outSouthFarOff = outNorthFarOff
				.rotatedToDirection(ForgeDirection.SOUTH);
		inSouthCloseOn = inNorthCloseOn
				.rotatedToDirection(ForgeDirection.SOUTH);
		inSouthCloseOff = inNorthCloseOff
				.rotatedToDirection(ForgeDirection.SOUTH);
		outSouthCloseOn = outNorthCloseOn
				.rotatedToDirection(ForgeDirection.SOUTH);
		outSouthCloseOff = outNorthCloseOff
				.rotatedToDirection(ForgeDirection.SOUTH);

		// East
		inEastFarOn = inNorthFarOn.rotatedToDirection(ForgeDirection.EAST);
		inEastFarOff = inNorthFarOff.rotatedToDirection(ForgeDirection.EAST);
		inEastFarOverflow = inNorthFarOverflow
				.rotatedToDirection(ForgeDirection.EAST);
		outEastFarOn = outNorthFarOn.rotatedToDirection(ForgeDirection.EAST);
		outEastFarOff = outNorthFarOff.rotatedToDirection(ForgeDirection.EAST);
		inEastCloseOn = inNorthCloseOn.rotatedToDirection(ForgeDirection.EAST);
		inEastCloseOff = inNorthCloseOff
				.rotatedToDirection(ForgeDirection.EAST);
		outEastCloseOn = outNorthCloseOn
				.rotatedToDirection(ForgeDirection.EAST);
		outEastCloseOff = outNorthCloseOff
				.rotatedToDirection(ForgeDirection.EAST);

		// West
		inWestFarOn = inNorthFarOn.rotatedToDirection(ForgeDirection.WEST);
		inWestFarOff = inNorthFarOff.rotatedToDirection(ForgeDirection.WEST);
		inWestFarOverflow = inNorthFarOverflow
				.rotatedToDirection(ForgeDirection.WEST);
		outWestFarOn = outNorthFarOn.rotatedToDirection(ForgeDirection.WEST);
		outWestFarOff = outNorthFarOff.rotatedToDirection(ForgeDirection.WEST);
		inWestCloseOn = inNorthCloseOn.rotatedToDirection(ForgeDirection.WEST);
		inWestCloseOff = inNorthCloseOff
				.rotatedToDirection(ForgeDirection.WEST);
		outWestCloseOn = outNorthCloseOn
				.rotatedToDirection(ForgeDirection.WEST);
		outWestCloseOff = outNorthCloseOff
				.rotatedToDirection(ForgeDirection.WEST);

		// Up
		inUpFarOn = inNorthFarOn.rotatedToDirection(ForgeDirection.UP);
		inUpFarOff = inNorthFarOff.rotatedToDirection(ForgeDirection.UP);
		inUpFarOverflow = inNorthFarOverflow
				.rotatedToDirection(ForgeDirection.UP);
		outUpFarOn = outNorthFarOn.rotatedToDirection(ForgeDirection.UP);
		outUpFarOff = outNorthFarOff.rotatedToDirection(ForgeDirection.UP);
		inUpCloseOn = inNorthCloseOn.rotatedToDirection(ForgeDirection.UP);
		inUpCloseOff = inNorthCloseOff.rotatedToDirection(ForgeDirection.UP);
		outUpCloseOn = outNorthCloseOn.rotatedToDirection(ForgeDirection.UP);
		outUpCloseOff = outNorthCloseOff.rotatedToDirection(ForgeDirection.UP);

		// Down
		inDownFarOn = inNorthFarOn.rotatedToDirection(ForgeDirection.DOWN);
		inDownFarOff = inNorthFarOff.rotatedToDirection(ForgeDirection.DOWN);
		inDownFarOverflow = inNorthFarOverflow
				.rotatedToDirection(ForgeDirection.DOWN);
		outDownFarOn = outNorthFarOn.rotatedToDirection(ForgeDirection.DOWN);
		outDownFarOff = outNorthFarOff.rotatedToDirection(ForgeDirection.DOWN);
		inDownCloseOn = inNorthCloseOn.rotatedToDirection(ForgeDirection.DOWN);
		inDownCloseOff = inNorthCloseOff
				.rotatedToDirection(ForgeDirection.DOWN);
		outDownCloseOn = outNorthCloseOn
				.rotatedToDirection(ForgeDirection.DOWN);
		outDownCloseOff = outNorthCloseOff
				.rotatedToDirection(ForgeDirection.DOWN);
	}

	private Model createIndicator(VacuumTube tube, float offset,
			boolean hasItem, boolean overflow, boolean in) {
		Model ret = new Model(new Point(0, 0, 0), new Point(.5f, .5f, .5f));

		float min = 7.f / 16.f;
		float max = 9.f / 16.f;

		float minU = 0;
		float maxU = 0;
		float minV = 0;
		float maxV = 0;

		if (overflow) {
			minU = tube.indicatorIcon.getInterpolatedU(8.f);
			maxU = tube.indicatorIcon.getMaxU();
			minV = tube.indicatorIcon.getInterpolatedV(8.f);
			maxV = tube.indicatorIcon.getMaxV();
		} else if (hasItem) {
			minU = tube.indicatorIcon.getInterpolatedU(8.f);
			maxU = tube.indicatorIcon.getMaxU();
			minV = tube.indicatorIcon.getMinV();
			maxV = tube.indicatorIcon.getInterpolatedV(8.f);
		} else {
			minU = tube.indicatorIcon.getMinU();
			maxU = tube.indicatorIcon.getInterpolatedU(8.f);
			minV = tube.indicatorIcon.getMinV();
			maxV = tube.indicatorIcon.getInterpolatedV(8.f);
		}

		if (!in) {
			float temp = minU;
			minU = maxU;
			maxU = temp;
		}

		Quad top = FemtocraftRenderUtils.makeTopFace(min - offset,
				max - offset, min, max, 11.f / 16.f, tube.indicatorIcon, minU,
				maxU, minV, maxV).rotateOnYAxis(-Math.PI / 2.d, .5f, .5f);
		Quad bot = FemtocraftRenderUtils.makeBottomFace(min, max, min - offset,
				max - offset, 5.f / 16.f, tube.indicatorIcon, minU, maxU, minV,
				maxV);
		Quad east = FemtocraftRenderUtils.makeEastFace(min, max, min - offset,
				max - offset, 11.f / 16.f, tube.indicatorIcon, minU, maxU,
				minV, maxV);
		Quad west = FemtocraftRenderUtils.makeWestFace(min - offset,
				max - offset, min, max, 5.f / 16.f, tube.indicatorIcon, minU,
				maxU, minV, maxV).rotateOnXAxis(Math.PI / 2.d, .5f, .5f);

		ret.addQuad(top);
		ret.addQuad(bot);
		ret.addQuad(east);
		ret.addQuad(west);

		return ret;
	}

	private void createEnds(VacuumTube tube) {
		inNorth = createEnd(tube, true);
		outNorth = createEnd(tube, false);
		inSouth = inNorth.rotatedToDirection(ForgeDirection.SOUTH);
		outSouth = outNorth.rotatedToDirection(ForgeDirection.SOUTH);
		inEast = inNorth.rotatedToDirection(ForgeDirection.EAST);
		outEast = outNorth.rotatedToDirection(ForgeDirection.EAST);
		inWest = inNorth.rotatedToDirection(ForgeDirection.WEST);
		outWest = outNorth.rotatedToDirection(ForgeDirection.WEST);
		inUp = inNorth.rotatedToDirection(ForgeDirection.UP);
		outUp = outNorth.rotatedToDirection(ForgeDirection.UP);
		inDown = inNorth.rotatedToDirection(ForgeDirection.DOWN);
		outDown = outNorth.rotatedToDirection(ForgeDirection.DOWN);
	}

	private Model createEnd(VacuumTube tube, boolean in) {
		Model ret = new Model(new Point(0, 0, 0), new Point(0.5f, 0.5f, 0.5f));

		float min = 4.f / 16.f;
		float max = 12.f / 16.f;
		float minDist = 0.f / 16.f;
		float maxDist = 4.f / 16.f;
		float minU = tube.straightIcon.getMinU();
		float maxU = tube.straightIcon.getInterpolatedU(4.f);
		float minV = tube.straightIcon.getInterpolatedV(4.f);
		float maxV = tube.straightIcon.getInterpolatedV(12.f);

		// Outsides
		Quad top = FemtocraftRenderUtils.makeTopFace(minDist, maxDist, min,
				max, max, tube.straightIcon, minU, maxU, minV, maxV)
				.rotateOnYAxis(-Math.PI / 2.d, .5f, .5f);
		Quad top_r = FemtocraftRenderUtils.makeBottomFace(min, max, minDist,
				maxDist, max - 3.f / 16.f, tube.straightIcon, minU, maxU, minV,
				maxV);

		Quad bot = FemtocraftRenderUtils.makeBottomFace(min, max, minDist,
				maxDist, min, tube.straightIcon, minU, maxU, minV, maxV);
		Quad bot_r = FemtocraftRenderUtils.makeTopFace(minDist, maxDist, min,
				max, min + 3.f / 16.f, tube.straightIcon, minU, maxU, minV,
				maxV).rotateOnYAxis(-Math.PI / 2.d, .5f, .5f);

		Quad east = FemtocraftRenderUtils.makeEastFace(min, max, minDist,
				maxDist, max, tube.straightIcon, minU, maxU, minV, maxV);
		Quad east_r = FemtocraftRenderUtils.makeWestFace(minDist, maxDist, min,
				max, max - 3.f / 16.f, tube.straightIcon, minU, maxU, minV,
				maxV).rotateOnXAxis(Math.PI / 2.d, .5f, .5f);

		Quad west = FemtocraftRenderUtils.makeWestFace(minDist, maxDist, min,
				max, min, tube.straightIcon, minU, maxU, minV, maxV)
				.rotateOnXAxis(Math.PI / 2.d, .5f, .5f);
		Quad west_r = FemtocraftRenderUtils.makeEastFace(min, max, minDist,
				maxDist, min + 3.f / 16.f, tube.straightIcon, minU, maxU, minV,
				maxV);

		minU = tube.straightInsetIcon.getMinU();
		maxU = tube.straightInsetIcon.getInterpolatedU(4.f);
		minV = tube.straightInsetIcon.getInterpolatedV(4.f);
		maxV = tube.straightInsetIcon.getInterpolatedV(12.f);

		if (!in) {
			float temp = minU;
			minU = maxU;
			maxU = temp;
		}

		// Insets
		Quad top_inset = FemtocraftRenderUtils.makeTopFace(minDist, maxDist,
				min, max, max - 1.f / 16.f, tube.straightInsetIcon, minU, maxU,
				minV, maxV).rotateOnYAxis(-Math.PI / 2.d, .5f, .5f);
		Quad bot_inset = FemtocraftRenderUtils.makeBottomFace(min, max,
				minDist, maxDist, min + 1.f / 16.f, tube.straightInsetIcon,
				minU, maxU, minV, maxV);
		Quad east_inset = FemtocraftRenderUtils.makeEastFace(min, max, minDist,
				maxDist, max - 1.f / 16.f, tube.straightInsetIcon, minU, maxU,
				minV, maxV);
		Quad west_inset = FemtocraftRenderUtils.makeWestFace(minDist, maxDist,
				min, max, min + 1.f / 16.f, tube.straightInsetIcon, minU, maxU,
				minV, maxV).rotateOnXAxis(Math.PI / 2.d, .5f, .5f);

		ret.addQuad(top);
		ret.addQuad(top_r);
		ret.addQuad(bot);
		ret.addQuad(bot_r);
		ret.addQuad(east);
		ret.addQuad(east_r);
		ret.addQuad(west);
		ret.addQuad(west_r);

		ret.addQuad(top_inset);
		ret.addQuad(bot_inset);
		ret.addQuad(east_inset);
		ret.addQuad(west_inset);

		return ret;
	}

	private void createCenterEnds(VacuumTube tube) {
		centerEndNorth = createCenterEnd(tube);
		centerEndSouth = centerEndNorth
				.rotatedToDirection(ForgeDirection.SOUTH);
		centerEndUp = centerEndNorth.rotatedToDirection(ForgeDirection.UP);
		centerEndDown = centerEndNorth.rotatedToDirection(ForgeDirection.DOWN);
		centerEndWest = centerEndNorth.rotatedToDirection(ForgeDirection.WEST);
		centerEndEast = centerEndNorth.rotatedToDirection(ForgeDirection.EAST);
	}

	private Model createCenterEnd(VacuumTube tube) {
		Model ret = new Model(new Point(0, 0, 0), new Point(0.5f, 0.5f, 0.5f));

		float min = 4.f / 16.f;
		float max = 12.f / 16.f;

		float minU = tube.endIcon.getInterpolatedU(4.f);
		float maxU = tube.endIcon.getInterpolatedU(12.f);
		float minV = tube.endIcon.getInterpolatedV(4.f);
		float maxV = tube.endIcon.getInterpolatedV(12.f);

		float insetMinU = tube.endInsetIcon.getInterpolatedU(4.f);
		float insetMaxU = tube.endInsetIcon.getInterpolatedU(12.f);
		float insetMinV = tube.endInsetIcon.getInterpolatedV(4.f);
		float insetMaxV = tube.endInsetIcon.getInterpolatedV(12.f);

		Quad end = FemtocraftRenderUtils.makeNorthFace(min, max, min, max,
				4.f / 16.f, tube.endIcon, minU, maxU, minV, maxV);
		Quad inset = FemtocraftRenderUtils.makeNorthFace(min, max, min, max,
				4.f / 16.f, tube.endInsetIcon, insetMinU, insetMaxU, insetMinV,
				insetMaxV);

		ret.addQuad(end);
		ret.addQuad(inset);

		return ret;
	}

}
