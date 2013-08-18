package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSmallButton;
import net.minecraft.client.mco.GuiScreenConfirmationType;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class GuiScreenConfirmation extends GuiScreen {

   private final GuiScreenConfirmationType field_140045_e;
   private final String field_140049_p;
   private final String field_96288_n;
   protected final GuiScreen field_140048_a;
   protected final String field_140046_b;
   protected final String field_140047_c;
   protected final int field_140044_d;


   public GuiScreenConfirmation(GuiScreen p_i2325_1_, GuiScreenConfirmationType p_i2325_2_, String p_i2325_3_, String p_i2325_4_, int p_i2325_5_) {
      this.field_140048_a = p_i2325_1_;
      this.field_140044_d = p_i2325_5_;
      this.field_140045_e = p_i2325_2_;
      this.field_140049_p = p_i2325_3_;
      this.field_96288_n = p_i2325_4_;
      this.field_140046_b = I18n.func_135053_a("gui.yes");
      this.field_140047_c = I18n.func_135053_a("gui.no");
   }

   public void func_73866_w_() {
      this.field_73887_h.add(new GuiSmallButton(0, this.field_73880_f / 2 - 155, this.field_73881_g / 6 + 112, this.field_140046_b));
      this.field_73887_h.add(new GuiSmallButton(1, this.field_73880_f / 2 - 155 + 160, this.field_73881_g / 6 + 112, this.field_140047_c));
   }

   protected void func_73875_a(GuiButton p_73875_1_) {
      this.field_140048_a.func_73878_a(p_73875_1_.field_73741_f == 0, this.field_140044_d);
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_73873_v_();
      this.func_73732_a(this.field_73886_k, this.field_140045_e.field_140072_d, this.field_73880_f / 2, 70, this.field_140045_e.field_140075_c);
      this.func_73732_a(this.field_73886_k, this.field_140049_p, this.field_73880_f / 2, 90, 16777215);
      this.func_73732_a(this.field_73886_k, this.field_96288_n, this.field_73880_f / 2, 110, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }
}
