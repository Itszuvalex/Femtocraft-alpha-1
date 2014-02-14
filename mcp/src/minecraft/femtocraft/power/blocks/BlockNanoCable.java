package femtocraft.power.blocks;

import femtocraft.Femtocraft;
import femtocraft.power.tiles.TileEntityNanoCable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockNanoCable extends BlockMicroCable {
    public BlockNanoCable(int par1, Material par2Material) {
        super(par1, par2Material);
        setCreativeTab(Femtocraft.femtocraftTab);
        setUnlocalizedName("blockNanoCable");
        setHardness(1.0f);
        setStepSound(Block.soundStoneFootstep);
        setBlockBounds();
    }

    public void setBlockBounds() {
        this.minX = this.minY = this.minZ = 4.0D / 16.0D;
        this.maxX = this.maxY = this.maxZ = 12.0D / 16.0D;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityNanoCable();
    }
}
