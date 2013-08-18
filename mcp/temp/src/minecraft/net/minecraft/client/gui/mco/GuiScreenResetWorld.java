package net.minecraft.client.gui.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenConfirmation;
import net.minecraft.client.gui.GuiScreenLongRunningTask;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.mco.GuiScreenMcoWorldTemplate;
import net.minecraft.client.gui.mco.ScreenWithCallback;
import net.minecraft.client.gui.mco.TaskResetWorld;
import net.minecraft.client.mco.GuiScreenConfirmationType;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.mco.WorldTemplate;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class GuiScreenResetWorld extends ScreenWithCallback {

   private GuiScreen field_96152_a;
   private McoServer field_96150_b;
   private GuiTextField field_96151_c;
   private final int field_96149_d = 1;
   private final int field_96153_n = 2;
   private static int field_110360_p = 3;
   private WorldTemplate field_110359_q;
   private GuiButton field_96154_o;


   public GuiScreenResetWorld(GuiScreen p_i1135_1_, McoServer p_i1135_2_) {
      this.field_96152_a = p_i1135_1_;
      this.field_96150_b = p_i1135_2_;
   }

   public void func_73876_c() {
      this.field_96151_c.func_73780_a();
   }

   public void func_73866_w_() {
      Keyboard.enableRepeatEvents(true);
      this.field_73887_h.clear();
      this.field_73887_h.add(this.field_96154_o = new GuiButton(1, this.field_73880_f / 2 - 100, this.field_73881_g / 4 + 120 + 12, 97, 20, I18n.func_135053_a("mco.configure.world.buttons.reset")));
      this.field_73887_h.add(new GuiButton(2, this.field_73880_f / 2 + 5, this.field_73881_g / 4 + 120 + 12, 97, 20, I18n.func_135053_a("gui.cancel")));
      this.field_96151_c = new GuiTextField(this.field_73886_k, this.field_73880_f / 2 - 100, 99, 200, 20);
      this.field_96151_c.func_73796_b(true);
      this.field_96151_c.func_73804_f(32);
      this.field_96151_c.func_73782_a("");
      if(this.field_110359_q == null) {
         this.field_73887_h.add(new GuiButton(field_110360_p, this.field_73880_f / 2 - 100, 125, 200, 20, I18n.func_135053_a("mco.template.default.name")));
      } else {
         this.field_96151_c.func_73782_a("");
         this.field_96151_c.func_82265_c(false);
         this.field_96151_c.func_73796_b(false);
         this.field_73887_h.add(new GuiButton(field_110360_p, this.field_73880_f / 2 - 100, 125, 200, 20, I18n.func_135053_a("mco.template.name") + ": " + this.field_110359_q.field_110732_b));
      }

   }

   public void func_73874_b() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) {
      this.field_96151_c.func_73802_a(p_73869_1_, p_73869_2_);
      if(p_73869_2_ == 28 || p_73869_2_ == 156) {
         this.func_73875_a(this.field_96154_o);
      }

   }

   protected void func_73875_a(GuiButton p_73875_1_) {
      if(p_73875_1_.field_73742_g) {
         if(p_73875_1_.field_73741_f == 2) {
            this.field_73882_e.func_71373_a(this.field_96152_a);
         } else if(p_73875_1_.field_73741_f == 1) {
            String var2 = I18n.func_135053_a("mco.configure.world.reset.question.line1");
            String var3 = I18n.func_135053_a("mco.configure.world.reset.question.line2");
            this.field_73882_e.func_71373_a(new GuiScreenConfirmation(this, GuiScreenConfirmationType.Warning, var2, var3, 1));
         } else if(p_73875_1_.field_73741_f == field_110360_p) {
            this.field_73882_e.func_71373_a(new GuiScreenMcoWorldTemplate(this, this.field_110359_q));
         }

      }
   }

   public void func_73878_a(boolean p_73878_1_, int p_73878_2_) {
      if(p_73878_1_ && p_73878_2_ == 1) {
         this.func_140006_g();
      } else {
         this.field_73882_e.func_71373_a(this);
      }

   }

   private void func_140006_g() {
      TaskResetWorld var1 = new TaskResetWorld(this, this.field_96150_b.field_96408_a, this.field_96151_c.func_73781_b(), this.field_110359_q);
      GuiScreenLongRunningTask var2 = new GuiScreenLongRunningTask(this.field_73882_e, this.field_96152_a, var1);
      var2.func_98117_g();
      this.field_73882_e.func_71373_a(var2);
   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
      this.field_96151_c.func_73793_a(p_73864_1_, p_73864_2_, p_73864_3_);
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_73873_v_();
      this.func_73732_a(this.field_73886_k, I18n.func_135053_a("mco.reset.world.title"), this.field_73880_f / 2, 17, 16777215);
      this.func_73732_a(this.field_73886_k, I18n.func_135053_a("mco.reset.world.warning"), this.field_73880_f / 2, 56, 16711680);
      this.func_73731_b(this.field_73886_k, I18n.func_135053_a("mco.reset.world.seed"), this.field_73880_f / 2 - 100, 86, 10526880);
      this.field_96151_c.func_73795_f();
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   void func_110358_a(WorldTemplate p_110358_1_) {
      this.field_110359_q = p_110358_1_;
   }

   // $FF: synthetic method
   // $FF: bridge method
   void func_110354_a(Object p_110354_1_) {
      this.func_110358_a((WorldTemplate)p_110354_1_);
   }

   // $FF: synthetic method
   static GuiScreen func_96148_a(GuiScreenResetWorld p_96148_0_) {
      return p_96148_0_.field_96152_a;
   }

   // $FF: synthetic method
   static Minecraft func_96147_b(GuiScreenResetWorld p_96147_0_) {
      return p_96147_0_.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft func_130025_c(GuiScreenResetWorld p_130025_0_) {
      return p_130025_0_.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft func_130024_d(GuiScreenResetWorld p_130024_0_) {
      return p_130024_0_.field_73882_e;
   }

}
