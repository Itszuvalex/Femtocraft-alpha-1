package femtocraft.cooking.blocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import femtocraft.proxy.ClientProxyFemtocraft;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class cuttingBoardRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		// Draw cutting board top/bottom
		renderer.setRenderBounds(0, 0, 0, 1, 1.0D / 16.0D, 1);
		renderer.renderStandardBlock(block, x, y, z);

		CuttingBoard cuttingBoard = (CuttingBoard) block;

		// Draw sides
		// Draw North side
		renderer.setRenderBounds(1.0D / 16.0D, 0, 0, 1.0D / 16.0D, 1, 1);
		renderer.renderFaceZNeg(block, x, y, z, cuttingBoard.cuttingBoardSideE);

		// Draw East side
		renderer.setRenderBounds(0, 0, 1.0D / 16.0D, 1, 1, 1.0D / 16.0D);
		renderer.renderFaceXPos(block, x, y, z, cuttingBoard.cuttingBoardSideNS);

		// Draw South side
		renderer.setRenderBounds(15.0D / 16.0D, 0, 0, 15.0D / 16.0D, 1, 1);
		renderer.renderFaceZPos(block, x, y, z, cuttingBoard.cuttingBoardSideW);

		// Draw West side
		renderer.setRenderBounds(0, 0, 15.0D / 16.0D, 1, 1, 15.0D / 16.0D);
		renderer.renderFaceXNeg(block, x, y, z, cuttingBoard.cuttingBoardSideNS);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return ClientProxyFemtocraft.cuttingBoardRenderType;
	}

}
