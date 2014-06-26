/*******************************************************************************
 * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 * Itszuvalex@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/

package femtocraft.consumables.processing.blocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import femtocraft.proxy.ProxyClient;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RenderCuttingBoard implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID,
                                     RenderBlocks renderer) {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
                                    Block block, int modelId, RenderBlocks renderer) {
        // Draw cutting itemBoard top/bottom
        renderer.setRenderBounds(0, 0, 0, 1, 1.0D / 16.0D, 1);
        renderer.renderStandardBlock(block, x, y, z);

        BlockCuttingBoard blockCuttingBoard = (BlockCuttingBoard) block;

        // Draw sides
        // Draw North side
        renderer.setRenderBounds(1.0D / 16.0D, 0, 0, 1.0D / 16.0D, 1, 1);
        renderer.renderFaceZNeg(block, x, y, z, blockCuttingBoard.cuttingBoardSideE);

        // Draw East side
        renderer.setRenderBounds(0, 0, 1.0D / 16.0D, 1, 1, 1.0D / 16.0D);
        renderer.renderFaceXPos(block, x, y, z, blockCuttingBoard.cuttingBoardSideNS);

        // Draw South side
        renderer.setRenderBounds(15.0D / 16.0D, 0, 0, 15.0D / 16.0D, 1, 1);
        renderer.renderFaceZPos(block, x, y, z, blockCuttingBoard.cuttingBoardSideW);

        // Draw West side
        renderer.setRenderBounds(0, 0, 15.0D / 16.0D, 1, 1, 15.0D / 16.0D);
        renderer.renderFaceXNeg(block, x, y, z, blockCuttingBoard.cuttingBoardSideNS);

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory() {
        return false;
    }

    @Override
    public int getRenderId() {
        return ProxyClient.cuttingBoardRenderType;
    }

}
