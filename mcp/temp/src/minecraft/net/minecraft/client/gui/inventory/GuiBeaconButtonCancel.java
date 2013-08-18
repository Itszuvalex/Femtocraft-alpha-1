package net.minecraft.client.gui.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiBeaconButton;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
class GuiBeaconButtonCancel extends GuiBeaconButton {

   // $FF: synthetic field
   final GuiBeacon field_82260_k;


   public GuiBeaconButtonCancel(GuiBeacon p_i1074_1_, int p_i1074_2_, int p_i1074_3_, int p_i1074_4_) {
      super(p_i1074_2_, p_i1074_3_, p_i1074_4_, GuiBeacon.func_110427_g(), 112, 220);
      this.field_82260_k = p_i1074_1_;
   }

   public void func_82251_b(int p_82251_1_, int p_82251_2_) {
      this.field_82260_k.func_74190_a(I18n.func_135053_a("gui.cancel"), p_82251_1_, p_82251_2_);
   }
}
