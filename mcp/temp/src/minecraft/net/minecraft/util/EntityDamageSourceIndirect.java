package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.StatCollector;

public class EntityDamageSourceIndirect extends EntityDamageSource {

   private Entity field_76387_p;


   public EntityDamageSourceIndirect(String p_i1568_1_, Entity p_i1568_2_, Entity p_i1568_3_) {
      super(p_i1568_1_, p_i1568_2_);
      this.field_76387_p = p_i1568_3_;
   }

   public Entity func_76364_f() {
      return this.field_76386_o;
   }

   public Entity func_76346_g() {
      return this.field_76387_p;
   }

   public ChatMessageComponent func_76360_b(EntityLivingBase p_76360_1_) {
      String var2 = this.field_76387_p == null?this.field_76386_o.func_96090_ax():this.field_76387_p.func_96090_ax();
      ItemStack var3 = this.field_76387_p instanceof EntityLivingBase?((EntityLivingBase)this.field_76387_p).func_70694_bm():null;
      String var4 = "death.attack." + this.field_76373_n;
      String var5 = var4 + ".item";
      return var3 != null && var3.func_82837_s() && StatCollector.func_94522_b(var5)?ChatMessageComponent.func_111082_b(var5, new Object[]{p_76360_1_.func_96090_ax(), var2, var3.func_82833_r()}):ChatMessageComponent.func_111082_b(var4, new Object[]{p_76360_1_.func_96090_ax(), var2});
   }
}
