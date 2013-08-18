package net.minecraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;

public class BlockHay extends BlockRotatedPillar
{
    public BlockHay(int par1)
    {
        super(par1, Material.grass);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 31;
    }

    @SideOnly(Side.CLIENT)
    protected Icon func_111048_c(int par1)
    {
        return this.blockIcon;
    }

    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.field_111051_a = par1IconRegister.registerIcon(this.func_111023_E() + "_top");
        this.blockIcon = par1IconRegister.registerIcon(this.func_111023_E() + "_side");
    }
}
