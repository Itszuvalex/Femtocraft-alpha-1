package net.minecraft.client.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Comparator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.EntityLivingBase;

@SideOnly(Side.CLIENT)
public class RenderSorter implements Comparator {

   private EntityLivingBase field_78945_a;


   public RenderSorter(EntityLivingBase p_i1241_1_) {
      this.field_78945_a = p_i1241_1_;
   }

   public int func_78944_a(WorldRenderer p_78944_1_, WorldRenderer p_78944_2_) {
      if(p_78944_1_.field_78927_l && !p_78944_2_.field_78927_l) {
         return 1;
      } else if(p_78944_2_.field_78927_l && !p_78944_1_.field_78927_l) {
         return -1;
      } else {
         double var3 = (double)p_78944_1_.func_78912_a(this.field_78945_a);
         double var5 = (double)p_78944_2_.func_78912_a(this.field_78945_a);
         return var3 < var5?1:(var3 > var5?-1:(p_78944_1_.field_78937_s < p_78944_2_.field_78937_s?1:-1));
      }
   }

   // $FF: synthetic method
   public int compare(Object p_compare_1_, Object p_compare_2_) {
      return this.func_78944_a((WorldRenderer)p_compare_1_, (WorldRenderer)p_compare_2_);
   }
}
