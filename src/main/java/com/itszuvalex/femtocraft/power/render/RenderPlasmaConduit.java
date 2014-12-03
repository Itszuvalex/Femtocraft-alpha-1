/*
 * ******************************************************************************
 *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 *  * Itszuvalex@gmail.com
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *  *****************************************************************************
 */

package com.itszuvalex.femtocraft.power.render;

import com.itszuvalex.femtocraft.power.blocks.BlockPlasmaConduit;
import com.itszuvalex.femtocraft.power.tiles.TileEntityPlasmaConduit;
import com.itszuvalex.femtocraft.proxy.ProxyClient;
import com.itszuvalex.femtocraft.render.RenderModel;
import com.itszuvalex.femtocraft.render.RenderPoint;
import com.itszuvalex.femtocraft.render.RenderUtils;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/19/14.
 */
public class RenderPlasmaConduit implements ISimpleBlockRenderingHandler {
    private RenderModel center;
    private RenderModel center_filled;
    private RenderModel connector_in_north, connector_in_south, connector_in_east, connector_in_west,
            connector_in_up, connector_in_down;
    private RenderModel connector_in_north_filled, connector_in_south_filled, connector_in_east_filled,
            connector_in_west_filled, connector_in_up_filled, connector_in_down_filled;
    private RenderModel connector_out_north, connector_out_south, connector_out_east, connector_out_west,
            connector_out_up, connector_out_down;
    private RenderModel connector_out_north_filled, connector_out_south_filled, connector_out_east_filled,
            connector_out_west_filled, connector_out_up_filled, connector_out_down_filled;
    private boolean initialized = false;

    public RenderPlasmaConduit() {

    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        BlockPlasmaConduit conduit = (BlockPlasmaConduit) block;
        if (conduit == null) {
            return;
        }


        Tessellator tessellator = Tessellator.instance;

        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_F(1, 1, 1);
        renderConduit(conduit, 0, 0, 0, false, ForgeDirection.NORTH, ForgeDirection.UNKNOWN);
        tessellator.draw();

        GL11.glTranslatef(0.5F, 0.5F, 0.5F);

    }

    private boolean renderConduit(BlockPlasmaConduit conduit, int x, int y, int z, boolean filled,
                                  ForgeDirection input, ForgeDirection output) {
        if (!initialized) {
            createConduit(conduit);
        }

        RenderModel core_model = filled ? center_filled : center;


        RenderModel input_model = null;
        if (input != null) {
            switch (input) {
                case DOWN:
                    input_model = filled ? connector_in_down_filled : connector_in_down;
                    break;
                case UP:
                    input_model = filled ? connector_in_up_filled : connector_in_up;
                    break;
                case NORTH:
                    input_model = filled ? connector_in_north_filled : connector_in_north;
                    break;
                case SOUTH:
                    input_model = filled ? connector_in_south_filled : connector_in_south;
                    break;
                case WEST:
                    input_model = filled ? connector_in_west_filled : connector_in_west;
                    break;
                case EAST:
                    input_model = filled ? connector_in_east_filled : connector_in_east;
                    break;
            }
        }

        RenderModel output_model = null;
        if (output != null) {
            switch (output) {
                case DOWN:
                    output_model = filled ? connector_out_down_filled : connector_out_down;
                    break;
                case UP:
                    output_model = filled ? connector_out_up_filled : connector_out_up;
                    break;
                case NORTH:
                    output_model = filled ? connector_out_north_filled : connector_out_north;
                    break;
                case SOUTH:
                    output_model = filled ? connector_out_south_filled : connector_out_south;
                    break;
                case WEST:
                    output_model = filled ? connector_out_west_filled : connector_out_west;
                    break;
                case EAST:
                    output_model = filled ? connector_out_east_filled : connector_out_east;
                    break;
            }
        }

        core_model.location = new RenderPoint(x, y, z);
        core_model.draw();
        if (input_model != null) {
            input_model.location = new RenderPoint(x, y, z);
            input_model.draw();
        }
        if (output_model != null) {
            output_model.location = new RenderPoint(x, y, z);
            output_model.draw();
        }

        return true;
    }

    private void createConduit(BlockPlasmaConduit conduit) {
        center = new RenderModel(new RenderPoint(0, 0, 0), new RenderPoint(.5f, .5f, .5f));
        center_filled = new RenderModel(new RenderPoint(0, 0, 0), new RenderPoint(.5f, .5f, .5f));

        float min = 2.f / 16.f;
        float max = 14.f / 16.f;
        float minU = conduit.center.getInterpolatedU(2f);
        float maxU = conduit.center.getInterpolatedU(14f);
        float minV = conduit.center.getInterpolatedV(2f);
        float maxV = conduit.center.getInterpolatedV(14f);
        center.addQuad(RenderUtils.makeNorthFace(min, max, min, max, min, conduit.center, minU, maxU, minV,
                maxV).flipV());
        center.addQuad(RenderUtils.makeSouthFace(min, max, min, max, max, conduit.center, minU, maxU, minV,
                maxV).flipV());
        center.addQuad(RenderUtils.makeWestFace(min, max, min, max, min, conduit.center, minU, maxU, minV,
                maxV).rotatePointsClockwise().flipV());
        center.addQuad(RenderUtils.makeEastFace(min, max, min, max, max, conduit.center, minU, maxU, minV,
                maxV).flipU());
        center.addQuad(RenderUtils.makeTopFace(min, max, min, max, max, conduit.center, minU, maxU, minV, maxV));
        center.addQuad(RenderUtils.makeBottomFace(min, max, min, max, min, conduit.center, minU, maxU, minV, maxV));

        minU = conduit.center_filled.getInterpolatedU(2f);
        maxU = conduit.center_filled.getInterpolatedU(14f);
        minV = conduit.center_filled.getInterpolatedV(2f);
        maxV = conduit.center_filled.getInterpolatedV(14f);

        center_filled.addQuad(RenderUtils.makeNorthFace(min, max, min, max, min, conduit.center_filled, minU, maxU,
                minV, maxV).flipV());
        center_filled.addQuad(RenderUtils.makeSouthFace(min, max, min, max, max, conduit.center_filled, minU, maxU,
                minV, maxV).flipV());
        center_filled.addQuad(RenderUtils.makeWestFace(min, max, min, max, min, conduit.center_filled, minU, maxU,
                minV, maxV).rotatePointsClockwise().flipV());
        center_filled.addQuad(RenderUtils.makeEastFace(min, max, min, max, max, conduit.center_filled, minU, maxU,
                minV, maxV).flipU());
        center_filled.addQuad(RenderUtils.makeTopFace(min, max, min, max, max, conduit.center_filled, minU, maxU,
                minV, maxV));
        center_filled.addQuad(RenderUtils.makeBottomFace(min, max, min, max, min, conduit.center_filled, minU, maxU,
                minV, maxV));

        //in

        connector_in_up = new RenderModel(new RenderPoint(0, 0, 0), new RenderPoint(.5f, .5f, .5f));
        connector_in_up_filled = new RenderModel(new RenderPoint(0, 0, 0), new RenderPoint(.5f, .5f, .5f));

        float minHeight = 14f / 16f;
        float maxHeight = 1f;
        minU = conduit.input.getInterpolatedU(0f);
        maxU = conduit.input.getInterpolatedU(2f);
        minV = conduit.input.getInterpolatedV(2f);
        maxV = conduit.input.getInterpolatedV(14f);

        connector_in_up.addQuad(RenderUtils.makeNorthFace(min, max, minHeight, maxHeight, min, conduit.input, minU,
                maxU, minV, maxV).rotatePointsClockwise());
        connector_in_up.addQuad(RenderUtils.makeSouthFace(min, max, minHeight, maxHeight, max, conduit.input, minU,
                maxU, minV, maxV).rotatePointsClockwise());
        connector_in_up.addQuad(RenderUtils.makeWestFace(minHeight, maxHeight, min, max, min, conduit.input, minU,
                maxU, minV, maxV).rotatePointsClockwise().rotatePointsClockwise());
        connector_in_up.addQuad(RenderUtils.makeEastFace(minHeight, maxHeight, min, max, max, conduit.input, minU,
                maxU, minV, maxV).rotatePointsCounterClockwise());

        minU = conduit.input_filled.getInterpolatedU(0f);
        maxU = conduit.input_filled.getInterpolatedU(2f);
        minV = conduit.input_filled.getInterpolatedV(2f);
        maxV = conduit.input_filled.getInterpolatedV(14f);

        connector_in_up_filled.addQuad(RenderUtils.makeNorthFace(min, max, minHeight, maxHeight, min,
                conduit.input_filled, minU, maxU, minV, maxV).rotatePointsClockwise());
        connector_in_up_filled.addQuad(RenderUtils.makeSouthFace(min, max, minHeight, maxHeight, max,
                conduit.input_filled, minU, maxU, minV, maxV).rotatePointsClockwise());
        connector_in_up_filled.addQuad(RenderUtils.makeWestFace(min, max, minHeight, maxHeight, min,
                conduit.input_filled, minU, maxU, minV, maxV).rotatePointsClockwise().rotatePointsClockwise());
        connector_in_up_filled.addQuad(RenderUtils.makeEastFace(min, max, minHeight, maxHeight, max,
                conduit.input_filled, minU, maxU, minV, maxV).rotatePointsCounterClockwise());

        //out

        connector_out_up = new RenderModel(new RenderPoint(0, 0, 0), new RenderPoint(.5f, .5f, .5f));
        connector_out_up_filled = new RenderModel(new RenderPoint(0, 0, 0), new RenderPoint(.5f, .5f, .5f));

        minU = conduit.output.getInterpolatedU(0f);
        maxU = conduit.output.getInterpolatedU(2f);
        minV = conduit.output.getInterpolatedV(2f);
        maxV = conduit.output.getInterpolatedV(14f);

        connector_out_up.addQuad(RenderUtils.makeNorthFace(min, max, minHeight, maxHeight, min, conduit.output, minU,
                maxU, minV, maxV).rotatePointsClockwise());
        connector_out_up.addQuad(RenderUtils.makeSouthFace(min, max, minHeight, maxHeight, max, conduit.output, minU,
                maxU, minV, maxV).rotatePointsClockwise());
        connector_out_up.addQuad(RenderUtils.makeWestFace(minHeight, maxHeight, min, max, min, conduit.output, minU,
                maxU, minV, maxV).rotatePointsClockwise().rotatePointsClockwise());
        connector_out_up.addQuad(RenderUtils.makeEastFace(minHeight, maxHeight, min, max, max, conduit.output, minU,
                maxU, minV, maxV).rotatePointsCounterClockwise());

        minU = conduit.output_filled.getInterpolatedU(0f);
        maxU = conduit.output_filled.getInterpolatedU(2f);
        minV = conduit.output_filled.getInterpolatedV(2f);
        maxV = conduit.output_filled.getInterpolatedV(14f);

        connector_out_up_filled.addQuad(RenderUtils.makeNorthFace(min, max, minHeight, maxHeight, min,
                conduit.output_filled, minU, maxU, minV, maxV).rotatePointsClockwise());
        connector_out_up_filled.addQuad(RenderUtils.makeSouthFace(min, max, minHeight, maxHeight, max,
                conduit.output_filled, minU, maxU, minV, maxV).rotatePointsClockwise());
        connector_out_up_filled.addQuad(RenderUtils.makeWestFace(min, max, minHeight, maxHeight, min,
                conduit.output_filled, minU, maxU, minV, maxV).rotatePointsClockwise().rotatePointsClockwise());
        connector_out_up_filled.addQuad(RenderUtils.makeEastFace(min, max, minHeight, maxHeight, max,
                conduit.output_filled, minU, maxU, minV, maxV).rotatePointsCounterClockwise());


        //rotations

        connector_in_down = connector_in_up.rotatedOnZAxis(Math.PI);
        connector_in_east = connector_in_up.rotatedOnZAxis(-Math.PI / 2.f);
        connector_in_west = connector_in_up.rotatedOnZAxis(Math.PI / 2.f);
        connector_in_north = connector_in_up.rotatedOnXAxis(-Math.PI / 2.f);
        connector_in_south = connector_in_up.rotatedOnXAxis(Math.PI / 2.f);

        connector_in_down_filled = connector_in_up_filled.rotatedOnZAxis(Math.PI);
        connector_in_east_filled = connector_in_up_filled.rotatedOnZAxis(-Math.PI / 2.f);
        connector_in_west_filled = connector_in_up_filled.rotatedOnZAxis(Math.PI / 2.f);
        connector_in_north_filled = connector_in_up_filled.rotatedOnXAxis(-Math.PI / 2.f);
        connector_in_south_filled = connector_in_up_filled.rotatedOnXAxis(Math.PI / 2.f);


        connector_out_down = connector_out_up.rotatedOnZAxis(Math.PI);
        connector_out_east = connector_out_up.rotatedOnZAxis(-Math.PI / 2.f);
        connector_out_west = connector_out_up.rotatedOnZAxis(Math.PI / 2.f);
        connector_out_north = connector_out_up.rotatedOnXAxis(-Math.PI / 2.f);
        connector_out_south = connector_out_up.rotatedOnXAxis(Math.PI / 2.f);

        connector_out_down_filled = connector_out_up_filled.rotatedOnZAxis(Math.PI);
        connector_out_east_filled = connector_out_up_filled.rotatedOnZAxis(-Math.PI / 2.f);
        connector_out_west_filled = connector_out_up_filled.rotatedOnZAxis(Math.PI / 2.f);
        connector_out_north_filled = connector_out_up_filled.rotatedOnXAxis(-Math.PI / 2.f);
        connector_out_south_filled = connector_out_up_filled.rotatedOnXAxis(Math.PI / 2.f);

        initialized = true;
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y,
                                    int z, Block block, int modelId, RenderBlocks renderer) {
        BlockPlasmaConduit conduit = (BlockPlasmaConduit) block;
        if (conduit == null) {
            return false;
        }
        TileEntity tile = renderer.blockAccess.getTileEntity(x, y, z);
        if (tile instanceof TileEntityPlasmaConduit) {
            TileEntityPlasmaConduit conduitTile = (TileEntityPlasmaConduit) tile;
            Tessellator tessellator = Tessellator.instance;
            tessellator.setBrightness(block.getMixedBrightnessForBlock(
                    renderer.blockAccess, x, y, z));
            tessellator.setColorOpaque_F(1, 1, 1);
            return renderConduit(conduit, x, y, z, conduitTile.hasFlows(), conduitTile.getInputDir(),
                    conduitTile.getOutputDir());
        }
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelID) {
        return true;
    }

    @Override
    public int getRenderId() {
        return ProxyClient.FemtocraftPlasmaConduitID();
    }
}
