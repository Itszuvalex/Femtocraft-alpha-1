package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.gui.GuiSmallButton;
import net.minecraft.client.gui.GuiSnooper;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.ScreenChatOptions;
import net.minecraft.client.resources.GuiScreenTemporaryResourcePackSelect;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.EnumOptions;
import net.minecraft.client.settings.GameSettings;

@SideOnly(Side.CLIENT)
public class GuiOptions extends GuiScreen {

   private static final EnumOptions[] field_74052_b = new EnumOptions[]{EnumOptions.MUSIC, EnumOptions.SOUND, EnumOptions.INVERT_MOUSE, EnumOptions.SENSITIVITY, EnumOptions.FOV, EnumOptions.DIFFICULTY, EnumOptions.TOUCHSCREEN};
   private final GuiScreen field_74053_c;
   private final GameSettings field_74051_d;
   protected String field_74054_a = "Options";


   public GuiOptions(GuiScreen p_i1046_1_, GameSettings p_i1046_2_) {
      this.field_74053_c = p_i1046_1_;
      this.field_74051_d = p_i1046_2_;
   }

   public void func_73866_w_() {
      int var1 = 0;
      this.field_74054_a = I18n.func_135053_a("options.title");
      EnumOptions[] var2 = field_74052_b;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         EnumOptions var5 = var2[var4];
         if(var5.func_74380_a()) {
            this.field_73887_h.add(new GuiSlider(var5.func_74381_c(), this.field_73880_f / 2 - 155 + var1 % 2 * 160, this.field_73881_g / 6 - 12 + 24 * (var1 >> 1), var5, this.field_74051_d.func_74297_c(var5), this.field_74051_d.func_74296_a(var5)));
         } else {
            GuiSmallButton var6 = new GuiSmallButton(var5.func_74381_c(), this.field_73880_f / 2 - 155 + var1 % 2 * 160, this.field_73881_g / 6 - 12 + 24 * (var1 >> 1), var5, this.field_74051_d.func_74297_c(var5));
            if(var5 == EnumOptions.DIFFICULTY && this.field_73882_e.field_71441_e != null && this.field_73882_e.field_71441_e.func_72912_H().func_76093_s()) {
               var6.field_73742_g = false;
               var6.field_73744_e = I18n.func_135053_a("options.difficulty") + ": " + I18n.func_135053_a("options.difficulty.hardcore");
            }

            this.field_73887_h.add(var6);
         }

         ++var1;
      }

      this.field_73887_h.add(new GuiButton(101, this.field_73880_f / 2 - 152, this.field_73881_g / 6 + 96 - 6, 150, 20, I18n.func_135053_a("options.video")));
      this.field_73887_h.add(new GuiButton(100, this.field_73880_f / 2 + 2, this.field_73881_g / 6 + 96 - 6, 150, 20, I18n.func_135053_a("options.controls")));
      this.field_73887_h.add(new GuiButton(102, this.field_73880_f / 2 - 152, this.field_73881_g / 6 + 120 - 6, 150, 20, I18n.func_135053_a("options.language")));
      this.field_73887_h.add(new GuiButton(103, this.field_73880_f / 2 + 2, this.field_73881_g / 6 + 120 - 6, 150, 20, I18n.func_135053_a("options.multiplayer.title")));
      this.field_73887_h.add(new GuiButton(105, this.field_73880_f / 2 - 152, this.field_73881_g / 6 + 144 - 6, 150, 20, I18n.func_135053_a("options.resourcepack")));
      this.field_73887_h.add(new GuiButton(104, this.field_73880_f / 2 + 2, this.field_73881_g / 6 + 144 - 6, 150, 20, I18n.func_135053_a("options.snooper.view")));
      this.field_73887_h.add(new GuiButton(200, this.field_73880_f / 2 - 100, this.field_73881_g / 6 + 168, I18n.func_135053_a("gui.done")));
   }

   protected void func_73875_a(GuiButton p_73875_1_) {
      if(p_73875_1_.field_73742_g) {
         if(p_73875_1_.field_73741_f < 100 && p_73875_1_ instanceof GuiSmallButton) {
            this.field_74051_d.func_74306_a(((GuiSmallButton)p_73875_1_).func_73753_a(), 1);
            p_73875_1_.field_73744_e = this.field_74051_d.func_74297_c(EnumOptions.func_74379_a(p_73875_1_.field_73741_f));
         }

         if(p_73875_1_.field_73741_f == 101) {
            this.field_73882_e.field_71474_y.func_74303_b();
            this.field_73882_e.func_71373_a(new GuiVideoSettings(this, this.field_74051_d));
         }

         if(p_73875_1_.field_73741_f == 100) {
            this.field_73882_e.field_71474_y.func_74303_b();
            this.field_73882_e.func_71373_a(new GuiControls(this, this.field_74051_d));
         }

         if(p_73875_1_.field_73741_f == 102) {
            this.field_73882_e.field_71474_y.func_74303_b();
            this.field_73882_e.func_71373_a(new GuiLanguage(this, this.field_74051_d, this.field_73882_e.func_135016_M()));
         }

         if(p_73875_1_.field_73741_f == 103) {
            this.field_73882_e.field_71474_y.func_74303_b();
            this.field_73882_e.func_71373_a(new ScreenChatOptions(this, this.field_74051_d));
         }

         if(p_73875_1_.field_73741_f == 104) {
            this.field_73882_e.field_71474_y.func_74303_b();
            this.field_73882_e.func_71373_a(new GuiSnooper(this, this.field_74051_d));
         }

         if(p_73875_1_.field_73741_f == 200) {
            this.field_73882_e.field_71474_y.func_74303_b();
            this.field_73882_e.func_71373_a(this.field_74053_c);
         }

         if(p_73875_1_.field_73741_f == 105) {
            this.field_73882_e.field_71474_y.func_74303_b();
            this.field_73882_e.func_71373_a(new GuiScreenTemporaryResourcePackSelect(this, this.field_74051_d));
         }

      }
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_73873_v_();
      this.func_73732_a(this.field_73886_k, this.field_74054_a, this.field_73880_f / 2, 15, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

}
