package net.minecraft.entity;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EntitySelectorArmoredMob implements IEntitySelector {

   private final ItemStack field_96567_c;


   public EntitySelectorArmoredMob(ItemStack p_i1584_1_) {
      this.field_96567_c = p_i1584_1_;
   }

   public boolean func_82704_a(Entity p_82704_1_) {
      if(!p_82704_1_.func_70089_S()) {
         return false;
      } else if(!(p_82704_1_ instanceof EntityLivingBase)) {
         return false;
      } else {
         EntityLivingBase var2 = (EntityLivingBase)p_82704_1_;
         return var2.func_71124_b(EntityLiving.func_82159_b(this.field_96567_c)) != null?false:(var2 instanceof EntityLiving?((EntityLiving)var2).func_98052_bS():var2 instanceof EntityPlayer);
      }
   }
}
