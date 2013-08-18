package net.minecraft.inventory;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHorseInventorySlotArmor;
import net.minecraft.inventory.ContainerHorseInventorySlotSaddle;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHorseInventory extends Container {

   private IInventory field_111243_a;
   private EntityHorse field_111242_f;


   public ContainerHorseInventory(IInventory p_i1817_1_, IInventory p_i1817_2_, EntityHorse p_i1817_3_) {
      this.field_111243_a = p_i1817_2_;
      this.field_111242_f = p_i1817_3_;
      byte var4 = 3;
      p_i1817_2_.func_70295_k_();
      int var5 = (var4 - 4) * 18;
      this.func_75146_a(new ContainerHorseInventorySlotSaddle(this, p_i1817_2_, 0, 8, 18));
      this.func_75146_a(new ContainerHorseInventorySlotArmor(this, p_i1817_2_, 1, 8, 36, p_i1817_3_));
      int var6;
      int var7;
      if(p_i1817_3_.func_110261_ca()) {
         for(var6 = 0; var6 < var4; ++var6) {
            for(var7 = 0; var7 < 5; ++var7) {
               this.func_75146_a(new Slot(p_i1817_2_, 2 + var7 + var6 * 5, 80 + var7 * 18, 18 + var6 * 18));
            }
         }
      }

      for(var6 = 0; var6 < 3; ++var6) {
         for(var7 = 0; var7 < 9; ++var7) {
            this.func_75146_a(new Slot(p_i1817_1_, var7 + var6 * 9 + 9, 8 + var7 * 18, 102 + var6 * 18 + var5));
         }
      }

      for(var6 = 0; var6 < 9; ++var6) {
         this.func_75146_a(new Slot(p_i1817_1_, var6, 8 + var6 * 18, 160 + var5));
      }

   }

   public boolean func_75145_c(EntityPlayer p_75145_1_) {
      return this.field_111243_a.func_70300_a(p_75145_1_) && this.field_111242_f.func_70089_S() && this.field_111242_f.func_70032_d(p_75145_1_) < 8.0F;
   }

   public ItemStack func_82846_b(EntityPlayer p_82846_1_, int p_82846_2_) {
      ItemStack var3 = null;
      Slot var4 = (Slot)this.field_75151_b.get(p_82846_2_);
      if(var4 != null && var4.func_75216_d()) {
         ItemStack var5 = var4.func_75211_c();
         var3 = var5.func_77946_l();
         if(p_82846_2_ < this.field_111243_a.func_70302_i_()) {
            if(!this.func_75135_a(var5, this.field_111243_a.func_70302_i_(), this.field_75151_b.size(), true)) {
               return null;
            }
         } else if(this.func_75139_a(1).func_75214_a(var5) && !this.func_75139_a(1).func_75216_d()) {
            if(!this.func_75135_a(var5, 1, 2, false)) {
               return null;
            }
         } else if(this.func_75139_a(0).func_75214_a(var5)) {
            if(!this.func_75135_a(var5, 0, 1, false)) {
               return null;
            }
         } else if(this.field_111243_a.func_70302_i_() <= 2 || !this.func_75135_a(var5, 2, this.field_111243_a.func_70302_i_(), false)) {
            return null;
         }

         if(var5.field_77994_a == 0) {
            var4.func_75215_d((ItemStack)null);
         } else {
            var4.func_75218_e();
         }
      }

      return var3;
   }

   public void func_75134_a(EntityPlayer p_75134_1_) {
      super.func_75134_a(p_75134_1_);
      this.field_111243_a.func_70305_f();
   }
}
