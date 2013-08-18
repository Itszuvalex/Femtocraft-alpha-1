package net.minecraft.client.gui.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiBeaconButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;

@SideOnly(Side.CLIENT)
class GuiBeaconButtonPower extends GuiBeaconButton {

   private final int field_82261_l;
   private final int field_82262_m;
   // $FF: synthetic field
   final GuiBeacon field_82263_k;


   public GuiBeaconButtonPower(GuiBeacon p_i1076_1_, int p_i1076_2_, int p_i1076_3_, int p_i1076_4_, int p_i1076_5_, int p_i1076_6_) {
      super(p_i1076_2_, p_i1076_3_, p_i1076_4_, GuiContainer.field_110408_a, 0 + Potion.field_76425_a[p_i1076_5_].func_76392_e() % 8 * 18, 198 + Potion.field_76425_a[p_i1076_5_].func_76392_e() / 8 * 18);
      this.field_82263_k = p_i1076_1_;
      this.field_82261_l = p_i1076_5_;
      this.field_82262_m = p_i1076_6_;
   }

   public void func_82251_b(int p_82251_1_, int p_82251_2_) {
      String var3 = I18n.func_135053_a(Potion.field_76425_a[this.field_82261_l].func_76393_a());
      if(this.field_82262_m >= 3 && this.field_82261_l != Potion.field_76428_l.field_76415_H) {
         var3 = var3 + " II";
      }

      this.field_82263_k.func_74190_a(var3, p_82251_1_, p_82251_2_);
   }
}
