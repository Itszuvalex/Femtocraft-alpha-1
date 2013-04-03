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
    	//DrawCuttingBoard(block, x, y, z);
    	
    	//Draw cutting board top/bottom
    	renderer.setRenderBounds(0, 0, 0, 1, 1.0D/16.0D, 1);
    	renderer.renderStandardBlock(block, x, y, z);
    	
    	//Draw sides
    	
    	//Draw North side
    	renderer.setRenderBounds(1.0D/16.0D, 0, 0, 1.0D/16.0D, 1, 1);
    	renderer.renderNorthFace(block, x, y, z, cuttingBoard.cuttingBoardSide);
    	
    	//Draw East side
    	renderer.setRenderBounds(0,0,1.0D/16.0D, 1, 1, 1.0D/16.0D);
    	renderer.renderEastFace(block, x, y, z, cuttingBoard.cuttingBoardSide);
    	
    	//Draw South side
    	renderer.setRenderBounds(15.0D/16.0D,0,0, 15.0D/16.0D, 1, 1);
    	renderer.renderSouthFace(block, x, y, z, cuttingBoard.cuttingBoardSide);
    	
    	//Draw West side
    	renderer.setRenderBounds(0,0,15.0D/16.0D, 1, 1, 15.0D/16.0D);
    	renderer.renderWestFace(block, x, y, z, cuttingBoard.cuttingBoardSide);
    	
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
    	//change the passed integer coordinates into double precision floats, and set the height on the y axis
        double par3 = (double)x;
        double par5 = (double)y + 0.25F;
        double par7 = (double)z;
        
      //this is the scale of the squares, in blocks
        float par9 = 0.5F;
       
        //get the tessellator instance
        Tessellator tessellator = Tessellator.instance;
       
        //set the texture
        Icon icon = Block.blockDiamond.getBlockTextureFromSideAndMetadata(0, 0);
        
        double d0 = (double)icon.func_94206_g();
        double d1 = (double)icon.func_94210_h();

        for (int j1 = 0; j1 < 3; ++j1)
        {
            double d2 = (double)j1 * Math.PI * 2.0D / 3.0D + (Math.PI / 2D);
            double d3 = (double)icon.func_94214_a(8.0D);
            double d4 = (double)icon.func_94212_f();

            double d5 = (double)x + 0.5D;
            double d6 = (double)x + 0.5D + Math.sin(d2) * 8.0D / 16.0D;
            double d7 = (double)z + 0.5D;
            double d8 = (double)z + 0.5D + Math.cos(d2) * 8.0D / 16.0D;
            tessellator.addVertexWithUV(d5, (double)(par3 + 1), d7, d3, d0);
            tessellator.addVertexWithUV(d5, (double)(par3 + 0), d7, d3, d1);
            tessellator.addVertexWithUV(d6, (double)(par3 + 0), d8, d4, d1);
            tessellator.addVertexWithUV(d6, (double)(par3 + 1), d8, d4, d0);
            tessellator.addVertexWithUV(d6, (double)(par3 + 1), d8, d4, d0);
            tessellator.addVertexWithUV(d6, (double)(par3 + 0), d8, d4, d1);
            tessellator.addVertexWithUV(d5, (double)(par3 + 0), d7, d3, d1);
            tessellator.addVertexWithUV(d5, (double)(par3 + 1), d7, d3, d0);
        }
    }
}
