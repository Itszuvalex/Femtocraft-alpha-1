package net.minecraft.inventory;

import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

class ContainerHorseInventorySlotSaddle extends Slot {

   // $FF: synthetic field
   final ContainerHorseInventory field_111239_a;


   ContainerHorseInventorySlotSaddle(ContainerHorseInventory p_i1815_1_, IInventory p_i1815_2_, int p_i1815_3_, int p_i1815_4_, int p_i1815_5_) {
      super(p_i1815_2_, p_i1815_3_, p_i1815_4_, p_i1815_5_);
      this.field_111239_a = p_i1815_1_;
   }

   public boolean func_75214_a(ItemStack p_75214_1_) {
      return super.func_75214_a(p_75214_1_) && p_75214_1_.field_77993_c == Item.field_77765_aA.field_77779_bT && !this.func_75216_d();
   }
}
