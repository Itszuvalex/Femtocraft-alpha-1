package net.minecraft.inventory;

import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

class ContainerRepairINNER1 extends InventoryBasic {

   // $FF: synthetic field
   final ContainerRepair field_135010_a;


   ContainerRepairINNER1(ContainerRepair p_i1798_1_, String p_i1798_2_, boolean p_i1798_3_, int p_i1798_4_) {
      super(p_i1798_2_, p_i1798_3_, p_i1798_4_);
      this.field_135010_a = p_i1798_1_;
   }

   public void func_70296_d() {
      super.func_70296_d();
      this.field_135010_a.func_75130_a(this);
   }

   public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_) {
      return true;
   }
}
