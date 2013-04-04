package femtocraft.cooking.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import femtocraft.proxy.ClientProxyFemtocraft;

public class cuttingBoardRenderer implements ISimpleBlockRenderingHandler{

	@Override
    public void renderInventoryBlock(Block block, int metadata, int modelID,
                    RenderBlocks renderer) {
           
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
                    Block block, int modelId, RenderBlocks renderer)
    {
    	DrawCuttingBoard(block, x, y, z);

//    	//Draw cutting board top/bottom
//    	renderer.setRenderBounds(0, 0, 0, 1, 1.0D/16.0D, 1);
//    	renderer.renderStandardBlock(block, x, y, z);
//    	
//    	//Draw sides
//    	
//    	//Draw North side
//    	renderer.setRenderBounds(1.0D/16.0D, 0, 0, 1.0D/16.0D, 1, 1);
//    	renderer.renderNorthFace(block, x, y, z, cuttingBoard.cuttingBoardSideE);
//    	
//    	//Draw East side
//    	renderer.setRenderBounds(0,0,1.0D/16.0D, 1, 1, 1.0D/16.0D);
//    	renderer.renderEastFace(block, x, y, z, cuttingBoard.cuttingBoardSideNS);
//    	
//    	//Draw South side
//    	renderer.setRenderBounds(15.0D/16.0D,0,0, 15.0D/16.0D, 1, 1);
//    	renderer.renderSouthFace(block, x, y, z, cuttingBoard.cuttingBoardSideW);
//    	
//    	//Draw West side
//    	renderer.setRenderBounds(0,0,15.0D/16.0D, 1, 1, 15.0D/16.0D);
//    	renderer.renderWestFace(block, x, y, z, cuttingBoard.cuttingBoardSideNS);
    	
    	return true;
    }
   
    @Override
    public boolean shouldRender3DInInventory() {
           
            return false;
    }

    @Override
    public int getRenderId()
    {       
    	return ClientProxyFemtocraft.cuttingBoardRenderType;
    }
    
    private void DrawCuttingBoard(Block block, int x, int y, int z)
    {
    	float minU = ((cuttingBoard)block).getCuttingBoardTop().getMinU();
		float minV = ((cuttingBoard)block).getCuttingBoardTop().getMinV();
		float maxU = ((cuttingBoard)block).getCuttingBoardTop().getMaxU();
		float maxV = ((cuttingBoard)block).getCuttingBoardTop().getMaxV();
		
		Tessellator tes = Tessellator.instance;
		
		double minHeight = 0D;
		double maxHeight = 1.0D / 16.0D;
		double minX = 1.0D / 16.0D;
		double maxX = 15.0D / 16.0D;
		double minZ = 1.0D / 16.0D;
		double maxZ = 15.0D / 16.0D;
		
		//Draw top face
		tes.addVertexWithUV(x + minX, y + maxHeight, z + minZ, minU, maxV);
		tes.addVertexWithUV(x + minX, y + maxHeight, z + maxZ, minU, minV);
		tes.addVertexWithUV(x + maxX, y + maxHeight, z + maxZ, maxU, minV);
		tes.addVertexWithUV(x + maxX, y + maxHeight, z + minZ, maxU, maxV);
		
		//Draw bottom face
		tes.addVertexWithUV(x + minX, y + minHeight, z + minZ, minU, maxV);
		tes.addVertexWithUV(x + maxX, y + minHeight, z + minZ, minU, minV);
		tes.addVertexWithUV(x + maxX, y + minHeight, z + maxZ, maxU, minV);
		tes.addVertexWithUV(x + minX, y + minHeight, z + maxZ, maxU, maxV);
		
		//Draw north face
		tes.addVertexWithUV(x + minX, y + minHeight, z + minZ, minU, maxV);
		tes.addVertexWithUV(x + minX, y + maxHeight, z + minZ, minU, minV);
		tes.addVertexWithUV(x + maxX, y + maxHeight, z + minZ, maxU, minV);
		tes.addVertexWithUV(x + maxX, y + minHeight, z + minZ, maxU, maxV);
		
		//Draw east face
		tes.addVertexWithUV(x + maxX, y + minHeight, z + minZ, minU, maxV);
		tes.addVertexWithUV(x + maxX, y + maxHeight, z + minZ, minU, minV);
		tes.addVertexWithUV(x + maxX, y + maxHeight, z + maxZ, maxU, minV);
		tes.addVertexWithUV(x + maxX, y + minHeight, z + maxZ, maxU, maxV);
		
		//Draw south face
		tes.addVertexWithUV(x + minX, y + minHeight, z + maxZ, minU, maxV);
		tes.addVertexWithUV(x + maxX, y + minHeight, z + maxZ, minU, minV);
		tes.addVertexWithUV(x + maxX, y + maxHeight, z + maxZ, maxU, minV);
		tes.addVertexWithUV(x + minX, y + maxHeight, z + maxZ, maxU, maxV);
		
		//Draw west face
		tes.addVertexWithUV(x + minX, y + minHeight, z + minZ, minU, maxV);
		tes.addVertexWithUV(x + minX, y + minHeight, z + maxZ, minU, minV);
		tes.addVertexWithUV(x + minX, y + maxHeight, z + maxZ, maxU, minV);
		tes.addVertexWithUV(x + minX, y + maxHeight, z + minZ, maxU, maxV);
    }
}
