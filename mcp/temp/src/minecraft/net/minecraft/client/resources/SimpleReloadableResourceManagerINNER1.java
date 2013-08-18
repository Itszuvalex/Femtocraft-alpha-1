package net.minecraft.client.resources;

import com.google.common.base.Function;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.ResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;

@SideOnly(Side.CLIENT)
class SimpleReloadableResourceManagerINNER1 implements Function {

   // $FF: synthetic field
   final SimpleReloadableResourceManager field_130076_a;


   SimpleReloadableResourceManagerINNER1(SimpleReloadableResourceManager p_i1298_1_) {
      this.field_130076_a = p_i1298_1_;
   }

   public String func_130075_a(ResourcePack p_130075_1_) {
      return p_130075_1_.func_130077_b();
   }

   // $FF: synthetic method
   public Object apply(Object p_apply_1_) {
      return this.func_130075_a((ResourcePack)p_apply_1_);
   }
}
