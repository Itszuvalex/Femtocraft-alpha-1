package femtocraft.industry.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.core.blocks.TileContainer;
import femtocraft.industry.tiles.TileEntityEncoder;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockEncoder extends TileContainer {
    public Icon top;
    public Icon side;

    public BlockEncoder(int par1) {
        super(par1, Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2) {
        switch (ForgeDirection.getOrientation(par1)) {
            case UP:
                return top;
            case DOWN:
                return blockIcon;
            default:
                return side;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        top = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":"
                                                    + "BlockEncoder_top");
        side = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":"
                                                     + "BlockEncoder_side");
        blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                                                          + ":" + "MicroMachineBlock_side");
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityEncoder();
    }
}
