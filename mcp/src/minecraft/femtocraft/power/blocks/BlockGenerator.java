package femtocraft.power.blocks;

import femtocraft.Femtocraft;
import femtocraft.power.tiles.TileEntityPowerProducerTest;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGenerator extends BlockPowerContainer {

    public BlockGenerator(int par1, Material par2Material) {
        super(par1, par2Material);
        setCreativeTab(Femtocraft.femtocraftTab);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityPowerProducerTest();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

}
