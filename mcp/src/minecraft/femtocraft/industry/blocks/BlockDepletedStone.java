package femtocraft.industry.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

import java.util.Random;

public class BlockDepletedStone extends Block {
    public Icon activeIcon;

    public BlockDepletedStone(int par1) {
        super(par1, Material.rock);
        setCreativeTab(Femtocraft.femtocraftTab);
        setUnlocalizedName("BlockDepletedStone");
    }

    /*
     * (non-Javadoc)
     *
     * @see net.minecraft.block.Block#getIcon(int, int)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2) {
        return par2 == 0 ? blockIcon : activeIcon;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.minecraft.block.Block#quantityDropped(java.util.Random)
     */
    @Override
    public int quantityDropped(Random par1Random) {
        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * net.minecraft.block.Block#registerIcons(net.minecraft.client.renderer
     * .texture.IconRegister)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID
                                                               .toLowerCase() + ":" + "BlockDepletedStone");
        this.activeIcon = par1IconRegister.registerIcon(Femtocraft.ID
                                                                .toLowerCase() + ":" + "BlockDepletedStone_active");
    }
}
