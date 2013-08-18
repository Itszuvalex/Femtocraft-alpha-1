package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSmallButton;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class GuiYesNo extends GuiScreen {

   protected GuiScreen field_73942_a;
   protected String field_73940_b;
   private String field_73944_m;
   protected String field_73941_c;
   protected String field_73939_d;
   protected int field_73943_n;


   public GuiYesNo(GuiScreen p_i1025_1_, String p_i1025_2_, String p_i1025_3_, int p_i1025_4_) {
      this.field_73942_a = p_i1025_1_;
      this.field_73940_b = p_i1025_2_;
      this.field_73944_m = p_i1025_3_;
      this.field_73943_n = p_i1025_4_;
      this.field_73941_c = I18n.func_135053_a("gui.yes");
      this.field_73939_d = I18n.func_135053_a("gui.no");
   }

   public GuiYesNo(GuiScreen p_i1026_1_, String p_i1026_2_, String p_i1026_3_, String p_i1026_4_, String p_i1026_5_, int p_i1026_6_) {
      this.field_73942_a = p_i1026_1_;
      this.field_73940_b = p_i1026_2_;
      this.field_73944_m = p_i1026_3_;
      this.field_73941_c = p_i1026_4_;
      this.field_73939_d = p_i1026_5_;
      this.field_73943_n = p_i1026_6_;
   }

   public void func_73866_w_() {
      this.field_73887_h.add(new GuiSmallButton(0, this.field_73880_f / 2 - 155, this.field_73881_g / 6 + 96, this.field_73941_c));
      this.field_73887_h.add(new GuiSmallButton(1, this.field_73880_f / 2 - 155 + 160, this.field_73881_g / 6 + 96, this.field_73939_d));
   }

   protected void func_73875_a(GuiButton p_73875_1_) {
      this.field_73942_a.func_73878_a(p_73875_1_.field_73741_f == 0, this.field_73943_n);
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_73873_v_();
      this.func_73732_a(this.field_73886_k, this.field_73940_b, this.field_73880_f / 2, 70, 16777215);
      this.func_73732_a(this.field_73886_k, this.field_73944_m, this.field_73880_f / 2, 90, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }
}
