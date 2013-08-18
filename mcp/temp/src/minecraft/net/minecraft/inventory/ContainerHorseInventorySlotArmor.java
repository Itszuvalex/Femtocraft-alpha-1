package net.minecraft.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class ContainerHorseInventorySlotArmor extends Slot {

   // $FF: synthetic field
   final EntityHorse field_111241_a;
   // $FF: synthetic field
   final ContainerHorseInventory field_111240_b;


   ContainerHorseInventorySlotArmor(ContainerHorseInventory p_i1816_1_, IInventory p_i1816_2_, int p_i1816_3_, int p_i1816_4_, int p_i1816_5_, EntityHorse p_i1816_6_) {
      super(p_i1816_2_, p_i1816_3_, p_i1816_4_, p_i1816_5_);
      this.field_111240_b = p_i1816_1_;
      this.field_111241_a = p_i1816_6_;
   }

   public boolean func_75214_a(ItemStack p_75214_1_) {
      return super.func_75214_a(p_75214_1_) && this.field_111241_a.func_110259_cr() && EntityHorse.func_110211_v(p_75214_1_.field_77993_c);
   }

   @SideOnly(Side.CLIENT)
   public boolean func_111238_b() {
      return this.field_111241_a.func_110259_cr();
   }
}
