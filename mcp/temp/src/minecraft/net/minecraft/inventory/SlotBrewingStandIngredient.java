package net.minecraft.inventory;

import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

class SlotBrewingStandIngredient extends Slot {

   // $FF: synthetic field
   final ContainerBrewingStand field_75226_a;


   public SlotBrewingStandIngredient(ContainerBrewingStand p_i1803_1_, IInventory p_i1803_2_, int p_i1803_3_, int p_i1803_4_, int p_i1803_5_) {
      super(p_i1803_2_, p_i1803_3_, p_i1803_4_, p_i1803_5_);
      this.field_75226_a = p_i1803_1_;
   }

   public boolean func_75214_a(ItemStack p_75214_1_) {
      return p_75214_1_ != null?Item.field_77698_e[p_75214_1_.field_77993_c].func_77632_u():false;
   }

   public int func_75219_a() {
      return 64;
   }
}
