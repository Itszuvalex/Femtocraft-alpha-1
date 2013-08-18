package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlotLanguage;
import net.minecraft.client.gui.GuiSmallButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;

@SideOnly(Side.CLIENT)
public class GuiLanguage extends GuiScreen {

   protected GuiScreen field_74047_a;
   private GuiSlotLanguage field_74046_c;
   private final GameSettings field_74044_d;
   private final LanguageManager field_135014_d;
   private GuiSmallButton field_74048_m;


   public GuiLanguage(GuiScreen p_i1043_1_, GameSettings p_i1043_2_, LanguageManager p_i1043_3_) {
      this.field_74047_a = p_i1043_1_;
      this.field_74044_d = p_i1043_2_;
      this.field_135014_d = p_i1043_3_;
   }

   public void func_73866_w_() {
      this.field_73887_h.add(this.field_74048_m = new GuiSmallButton(6, this.field_73880_f / 2 - 75, this.field_73881_g - 38, I18n.func_135053_a("gui.done")));
      this.field_74046_c = new GuiSlotLanguage(this);
      this.field_74046_c.func_77220_a(7, 8);
   }

   protected void func_73875_a(GuiButton p_73875_1_) {
      if(p_73875_1_.field_73742_g) {
         switch(p_73875_1_.field_73741_f) {
         case 5:
            break;
         case 6:
            this.field_73882_e.func_71373_a(this.field_74047_a);
            break;
         default:
            this.field_74046_c.func_77219_a(p_73875_1_);
         }

      }
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.field_74046_c.func_77211_a(p_73863_1_, p_73863_2_, p_73863_3_);
      this.func_73732_a(this.field_73886_k, I18n.func_135053_a("options.language"), this.field_73880_f / 2, 16, 16777215);
      this.func_73732_a(this.field_73886_k, "(" + I18n.func_135053_a("options.languageWarning") + ")", this.field_73880_f / 2, this.field_73881_g - 56, 8421504);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   // $FF: synthetic method
   static LanguageManager func_135011_a(GuiLanguage p_135011_0_) {
      return p_135011_0_.field_135014_d;
   }

   // $FF: synthetic method
   static GameSettings func_74043_a(GuiLanguage p_74043_0_) {
      return p_74043_0_.field_74044_d;
   }

   // $FF: synthetic method
   static GuiSmallButton func_74042_b(GuiLanguage p_74042_0_) {
      return p_74042_0_.field_74048_m;
   }
}
