package net.minecraft.client.resources.data;

import com.google.common.collect.Sets;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.MetadataSection;

@SideOnly(Side.CLIENT)
public class AnimationMetadataSection implements MetadataSection {

   private final List field_110478_a;
   private final int field_110476_b;
   private final int field_110477_c;
   private final int field_110475_d;


   public AnimationMetadataSection(List p_i1309_1_, int p_i1309_2_, int p_i1309_3_, int p_i1309_4_) {
      this.field_110478_a = p_i1309_1_;
      this.field_110476_b = p_i1309_2_;
      this.field_110477_c = p_i1309_3_;
      this.field_110475_d = p_i1309_4_;
   }

   public int func_110471_a() {
      return this.field_110477_c;
   }

   public int func_110474_b() {
      return this.field_110476_b;
   }

   public int func_110473_c() {
      return this.field_110478_a.size();
   }

   public int func_110469_d() {
      return this.field_110475_d;
   }

   private AnimationFrame func_130072_d(int p_130072_1_) {
      return (AnimationFrame)this.field_110478_a.get(p_130072_1_);
   }

   public int func_110472_a(int p_110472_1_) {
      AnimationFrame var2 = this.func_130072_d(p_110472_1_);
      return var2.func_110495_a()?this.field_110475_d:var2.func_110497_b();
   }

   public boolean func_110470_b(int p_110470_1_) {
      return !((AnimationFrame)this.field_110478_a.get(p_110470_1_)).func_110495_a();
   }

   public int func_110468_c(int p_110468_1_) {
      return ((AnimationFrame)this.field_110478_a.get(p_110468_1_)).func_110496_c();
   }

   public Set func_130073_e() {
      HashSet var1 = Sets.newHashSet();
      Iterator var2 = this.field_110478_a.iterator();

      while(var2.hasNext()) {
         AnimationFrame var3 = (AnimationFrame)var2.next();
         var1.add(Integer.valueOf(var3.func_110496_c()));
      }

      return var1;
   }
}
