package net.minecraft.client.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Comparator;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.mco.McoServerListEmptyAnon;
import net.minecraft.client.mco.McoServerListUpdateTask;

@SideOnly(Side.CLIENT)
class McoServerListUpdateTaskComparator implements Comparator {

   private final String field_140069_b;
   // $FF: synthetic field
   final McoServerListUpdateTask field_140070_a;


   private McoServerListUpdateTaskComparator(McoServerListUpdateTask p_i2326_1_, String p_i2326_2_) {
      this.field_140070_a = p_i2326_1_;
      this.field_140069_b = p_i2326_2_;
   }

   public int func_140068_a(McoServer p_140068_1_, McoServer p_140068_2_) {
      if(p_140068_1_.field_96405_e.equals(p_140068_2_.field_96405_e)) {
         return p_140068_1_.field_96408_a < p_140068_2_.field_96408_a?1:(p_140068_1_.field_96408_a > p_140068_2_.field_96408_a?-1:0);
      } else if(p_140068_1_.field_96405_e.equals(this.field_140069_b)) {
         return -1;
      } else if(p_140068_2_.field_96405_e.equals(this.field_140069_b)) {
         return 1;
      } else {
         if(p_140068_1_.field_96404_d.equals("CLOSED") || p_140068_2_.field_96404_d.equals("CLOSED")) {
            if(p_140068_1_.field_96404_d.equals("CLOSED")) {
               return 1;
            }

            if(p_140068_2_.field_96404_d.equals("CLOSED")) {
               return 0;
            }
         }

         return p_140068_1_.field_96408_a < p_140068_2_.field_96408_a?1:(p_140068_1_.field_96408_a > p_140068_2_.field_96408_a?-1:0);
      }
   }

   // $FF: synthetic method
   public int compare(Object p_compare_1_, Object p_compare_2_) {
      return this.func_140068_a((McoServer)p_compare_1_, (McoServer)p_compare_2_);
   }

   // $FF: synthetic method
   McoServerListUpdateTaskComparator(McoServerListUpdateTask p_i2327_1_, String p_i2327_2_, McoServerListEmptyAnon p_i2327_3_) {
      this(p_i2327_1_, p_i2327_2_);
   }
}
