package net.minecraft.inventory;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryBasic implements IInventory {

   private String field_70483_a;
   private int field_70481_b;
   private ItemStack[] field_70482_c;
   private List field_70480_d;
   private boolean field_94051_e;


   public InventoryBasic(String p_i1561_1_, boolean p_i1561_2_, int p_i1561_3_) {
      this.field_70483_a = p_i1561_1_;
      this.field_94051_e = p_i1561_2_;
      this.field_70481_b = p_i1561_3_;
      this.field_70482_c = new ItemStack[p_i1561_3_];
   }

   public void func_110134_a(IInvBasic p_110134_1_) {
      if(this.field_70480_d == null) {
         this.field_70480_d = new ArrayList();
      }

      this.field_70480_d.add(p_110134_1_);
   }

   public void func_110132_b(IInvBasic p_110132_1_) {
      this.field_70480_d.remove(p_110132_1_);
   }

   public ItemStack func_70301_a(int p_70301_1_) {
      return this.field_70482_c[p_70301_1_];
   }

   public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_) {
      if(this.field_70482_c[p_70298_1_] != null) {
         ItemStack var3;
         if(this.field_70482_c[p_70298_1_].field_77994_a <= p_70298_2_) {
            var3 = this.field_70482_c[p_70298_1_];
            this.field_70482_c[p_70298_1_] = null;
            this.func_70296_d();
            return var3;
         } else {
            var3 = this.field_70482_c[p_70298_1_].func_77979_a(p_70298_2_);
            if(this.field_70482_c[p_70298_1_].field_77994_a == 0) {
               this.field_70482_c[p_70298_1_] = null;
            }

            this.func_70296_d();
            return var3;
         }
      } else {
         return null;
      }
   }

   public ItemStack func_70304_b(int p_70304_1_) {
      if(this.field_70482_c[p_70304_1_] != null) {
         ItemStack var2 = this.field_70482_c[p_70304_1_];
         this.field_70482_c[p_70304_1_] = null;
         return var2;
      } else {
         return null;
      }
   }

   public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_) {
      this.field_70482_c[p_70299_1_] = p_70299_2_;
      if(p_70299_2_ != null && p_70299_2_.field_77994_a > this.func_70297_j_()) {
         p_70299_2_.field_77994_a = this.func_70297_j_();
      }

      this.func_70296_d();
   }

   public int func_70302_i_() {
      return this.field_70481_b;
   }

   public String func_70303_b() {
      return this.field_70483_a;
   }

   public boolean func_94042_c() {
      return this.field_94051_e;
   }

   public void func_110133_a(String p_110133_1_) {
      this.field_94051_e = true;
      this.field_70483_a = p_110133_1_;
   }

   public int func_70297_j_() {
      return 64;
   }

   public void func_70296_d() {
      if(this.field_70480_d != null) {
         for(int var1 = 0; var1 < this.field_70480_d.size(); ++var1) {
            ((IInvBasic)this.field_70480_d.get(var1)).func_76316_a(this);
         }
      }

   }

   public boolean func_70300_a(EntityPlayer p_70300_1_) {
      return true;
   }

   public void func_70295_k_() {}

   public void func_70305_f() {}

   public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_) {
      return true;
   }
}
