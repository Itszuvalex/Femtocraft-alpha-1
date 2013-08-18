package net.minecraft.client.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class GuiScreenClientOutdated extends GuiScreen {

   private final GuiScreen field_140007_a;


   public GuiScreenClientOutdated(GuiScreen p_i2323_1_) {
      this.field_140007_a = p_i2323_1_;
   }

   public void func_73866_w_() {
      this.field_73887_h.clear();
      this.field_73887_h.add(new GuiButton(0, this.field_73880_f / 2 - 100, this.field_73881_g / 4 + 120 + 12, "Back"));
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_73873_v_();
      String var4 = I18n.func_135053_a("mco.client.outdated.title");
      String var5 = I18n.func_135053_a("mco.client.outdated.msg");
      this.func_73732_a(this.field_73886_k, var4, this.field_73880_f / 2, this.field_73881_g / 2 - 50, 16711680);
      this.func_73732_a(this.field_73886_k, var5, this.field_73880_f / 2, this.field_73881_g / 2 - 30, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   protected void func_73875_a(GuiButton p_73875_1_) {
      if(p_73875_1_.field_73741_f == 0) {
         this.field_73882_e.func_71373_a(this.field_140007_a);
      }

   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) {
      if(p_73869_2_ == 28 || p_73869_2_ == 156) {
         this.field_73882_e.func_71373_a(this.field_140007_a);
      }

   }
}
