package net.minecraft.entity;

import java.util.Random;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.potion.Potion;

public class SpiderEffectsGroupData implements EntityLivingData {

   public int field_111105_a;


   public void func_111104_a(Random p_111104_1_) {
      int var2 = p_111104_1_.nextInt(5);
      if(var2 <= 1) {
         this.field_111105_a = Potion.field_76424_c.field_76415_H;
      } else if(var2 <= 2) {
         this.field_111105_a = Potion.field_76420_g.field_76415_H;
      } else if(var2 <= 3) {
         this.field_111105_a = Potion.field_76428_l.field_76415_H;
      } else if(var2 <= 4) {
         this.field_111105_a = Potion.field_76441_p.field_76415_H;
      }

   }
}
