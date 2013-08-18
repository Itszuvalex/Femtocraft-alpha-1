package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class ItemNameTag extends Item
{
    public ItemNameTag(int par1)
    {
        super(par1);
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    public boolean func_111207_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase)
    {
        if (!par1ItemStack.hasDisplayName())
        {
            return false;
        }
        else if (par3EntityLivingBase instanceof EntityLiving)
        {
            EntityLiving entityliving = (EntityLiving)par3EntityLivingBase;
            entityliving.setCustomNameTag(par1ItemStack.getDisplayName());
            entityliving.func_110163_bv();
            --par1ItemStack.stackSize;
            return true;
        }
        else
        {
            return super.func_111207_a(par1ItemStack, par2EntityPlayer, par3EntityLivingBase);
        }
    }
}
