package femtocraft.power.blocks;

import femtocraft.Femtocraft;
import femtocraft.power.tiles.TileEntityNullEqualizer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockNullEqualizer extends BlockPowerContainer {
    public BlockNullEqualizer(int par1) {
        super(par1, Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab);
        setUnlocalizedName("blockNullEqualizer");
        setHardness(1.0f);
        setStepSound(Block.soundStoneFootstep);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityNullEqualizer();
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }
}