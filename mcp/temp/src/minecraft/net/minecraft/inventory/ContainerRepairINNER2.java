package net.minecraft.inventory;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

class ContainerRepairINNER2 extends Slot {

   // $FF: synthetic field
   final World field_135071_a;
   // $FF: synthetic field
   final int field_135069_b;
   // $FF: synthetic field
   final int field_135070_c;
   // $FF: synthetic field
   final int field_135067_d;
   // $FF: synthetic field
   final ContainerRepair field_135068_e;


   ContainerRepairINNER2(ContainerRepair p_i1799_1_, IInventory p_i1799_2_, int p_i1799_3_, int p_i1799_4_, int p_i1799_5_, World p_i1799_6_, int p_i1799_7_, int p_i1799_8_, int p_i1799_9_) {
      super(p_i1799_2_, p_i1799_3_, p_i1799_4_, p_i1799_5_);
      this.field_135068_e = p_i1799_1_;
      this.field_135071_a = p_i1799_6_;
      this.field_135069_b = p_i1799_7_;
      this.field_135070_c = p_i1799_8_;
      this.field_135067_d = p_i1799_9_;
   }

   public boolean func_75214_a(ItemStack p_75214_1_) {
      return false;
   }

   public boolean func_82869_a(EntityPlayer p_82869_1_) {
      return (p_82869_1_.field_71075_bZ.field_75098_d || p_82869_1_.field_71068_ca >= this.field_135068_e.field_82854_e) && this.field_135068_e.field_82854_e > 0 && this.func_75216_d();
   }

   public void func_82870_a(EntityPlayer p_82870_1_, ItemStack p_82870_2_) {
      if(!p_82870_1_.field_71075_bZ.field_75098_d) {
         p_82870_1_.func_82242_a(-this.field_135068_e.field_82854_e);
      }

      ContainerRepair.func_82851_a(this.field_135068_e).func_70299_a(0, (ItemStack)null);
      if(ContainerRepair.func_82849_b(this.field_135068_e) > 0) {
         ItemStack var3 = ContainerRepair.func_82851_a(this.field_135068_e).func_70301_a(1);
         if(var3 != null && var3.field_77994_a > ContainerRepair.func_82849_b(this.field_135068_e)) {
            var3.field_77994_a -= ContainerRepair.func_82849_b(this.field_135068_e);
            ContainerRepair.func_82851_a(this.field_135068_e).func_70299_a(1, var3);
         } else {
            ContainerRepair.func_82851_a(this.field_135068_e).func_70299_a(1, (ItemStack)null);
         }
      } else {
         ContainerRepair.func_82851_a(this.field_135068_e).func_70299_a(1, (ItemStack)null);
      }

      this.field_135068_e.field_82854_e = 0;
      if(!p_82870_1_.field_71075_bZ.field_75098_d && !this.field_135071_a.field_72995_K && this.field_135071_a.func_72798_a(this.field_135069_b, this.field_135070_c, this.field_135067_d) == Block.field_82510_ck.field_71990_ca && p_82870_1_.func_70681_au().nextFloat() < 0.12F) {
         int var6 = this.field_135071_a.func_72805_g(this.field_135069_b, this.field_135070_c, this.field_135067_d);
         int var4 = var6 & 3;
         int var5 = var6 >> 2;
         ++var5;
         if(var5 > 2) {
            this.field_135071_a.func_94571_i(this.field_135069_b, this.field_135070_c, this.field_135067_d);
            this.field_135071_a.func_72926_e(1020, this.field_135069_b, this.field_135070_c, this.field_135067_d, 0);
         } else {
            this.field_135071_a.func_72921_c(this.field_135069_b, this.field_135070_c, this.field_135067_d, var4 | var5 << 2, 2);
            this.field_135071_a.func_72926_e(1021, this.field_135069_b, this.field_135070_c, this.field_135067_d, 0);
         }
      } else if(!this.field_135071_a.field_72995_K) {
         this.field_135071_a.func_72926_e(1021, this.field_135069_b, this.field_135070_c, this.field_135067_d, 0);
      }

   }
}
