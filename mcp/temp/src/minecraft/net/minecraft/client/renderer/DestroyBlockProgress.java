package net.minecraft.client.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DestroyBlockProgress {

   private final int field_73115_a;
   private final int field_73113_b;
   private final int field_73114_c;
   private final int field_73111_d;
   private int field_73112_e;
   private int field_82745_f;


   public DestroyBlockProgress(int p_i1511_1_, int p_i1511_2_, int p_i1511_3_, int p_i1511_4_) {
      this.field_73115_a = p_i1511_1_;
      this.field_73113_b = p_i1511_2_;
      this.field_73114_c = p_i1511_3_;
      this.field_73111_d = p_i1511_4_;
   }

   public int func_73110_b() {
      return this.field_73113_b;
   }

   public int func_73109_c() {
      return this.field_73114_c;
   }

   public int func_73108_d() {
      return this.field_73111_d;
   }

   public void func_73107_a(int p_73107_1_) {
      if(p_73107_1_ > 10) {
         p_73107_1_ = 10;
      }

      this.field_73112_e = p_73107_1_;
   }

   public int func_73106_e() {
      return this.field_73112_e;
   }

   public void func_82744_b(int p_82744_1_) {
      this.field_82745_f = p_82744_1_;
   }

   public int func_82743_f() {
      return this.field_82745_f;
   }
}
