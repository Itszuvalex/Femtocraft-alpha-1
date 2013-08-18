package net.minecraft.client.gui.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Collections;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenLongRunningTask;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.mco.GuiScreenMcoWorldTemplate;
import net.minecraft.client.gui.mco.ScreenWithCallback;
import net.minecraft.client.gui.mco.TaskWorldCreation;
import net.minecraft.client.mco.WorldTemplate;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class GuiScreenCreateOnlineWorld extends ScreenWithCallback {

   private GuiScreen field_96260_a;
   private GuiTextField field_96257_c;
   private GuiTextField field_96255_b;
   private String field_98108_c;
   private String field_98109_n;
   private static int field_96253_d;
   private static int field_96261_n = 1;
   private static int field_110357_r = 2;
   private boolean field_96256_r;
   private String field_96254_s = "You must enter a name!";
   private WorldTemplate field_110356_u;


   public GuiScreenCreateOnlineWorld(GuiScreen p_i1107_1_) {
      super.field_73887_h = Collections.synchronizedList(new ArrayList());
      this.field_96260_a = p_i1107_1_;
   }

   public void func_73876_c() {
      this.field_96257_c.func_73780_a();
      this.field_98108_c = this.field_96257_c.func_73781_b();
      this.field_96255_b.func_73780_a();
      this.field_98109_n = this.field_96255_b.func_73781_b();
   }

   public void func_73866_w_() {
      Keyboard.enableRepeatEvents(true);
      this.field_73887_h.clear();
      this.field_73887_h.add(new GuiButton(field_96253_d, this.field_73880_f / 2 - 100, this.field_73881_g / 4 + 120 + 17, 97, 20, I18n.func_135053_a("mco.create.world")));
      this.field_73887_h.add(new GuiButton(field_96261_n, this.field_73880_f / 2 + 5, this.field_73881_g / 4 + 120 + 17, 95, 20, I18n.func_135053_a("gui.cancel")));
      this.field_96257_c = new GuiTextField(this.field_73886_k, this.field_73880_f / 2 - 100, 65, 200, 20);
      this.field_96257_c.func_73796_b(true);
      if(this.field_98108_c != null) {
         this.field_96257_c.func_73782_a(this.field_98108_c);
      }

      this.field_96255_b = new GuiTextField(this.field_73886_k, this.field_73880_f / 2 - 100, 111, 200, 20);
      if(this.field_98109_n != null) {
         this.field_96255_b.func_73782_a(this.field_98109_n);
      }

      if(this.field_110356_u == null) {
         this.field_73887_h.add(new GuiButton(field_110357_r, this.field_73880_f / 2 - 100, 147, 200, 20, I18n.func_135053_a("mco.template.default.name")));
      } else {
         this.field_96255_b.func_73782_a("");
         this.field_96255_b.func_82265_c(false);
         this.field_96255_b.func_73796_b(false);
         this.field_73887_h.add(new GuiButton(field_110357_r, this.field_73880_f / 2 - 100, 147, 200, 20, I18n.func_135053_a("mco.template.name") + ": " + this.field_110356_u.field_110732_b));
      }

   }

   public void func_73874_b() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void func_73875_a(GuiButton p_73875_1_) {
      if(p_73875_1_.field_73742_g) {
         if(p_73875_1_.field_73741_f == field_96261_n) {
            this.field_73882_e.func_71373_a(this.field_96260_a);
         } else if(p_73875_1_.field_73741_f == field_96253_d) {
            this.func_96252_h();
         } else if(p_73875_1_.field_73741_f == field_110357_r) {
            this.field_73882_e.func_71373_a(new GuiScreenMcoWorldTemplate(this, this.field_110356_u));
         }

      }
   }

   private void func_96252_h() {
      if(this.func_96249_i()) {
         TaskWorldCreation var1 = new TaskWorldCreation(this, this.field_96257_c.func_73781_b(), "Minecraft Realms Server", this.field_98109_n, this.field_110356_u);
         GuiScreenLongRunningTask var2 = new GuiScreenLongRunningTask(this.field_73882_e, this.field_96260_a, var1);
         var2.func_98117_g();
         this.field_73882_e.func_71373_a(var2);
      }

   }

   private boolean func_96249_i() {
      this.field_96256_r = this.field_96257_c.func_73781_b() == null || this.field_96257_c.func_73781_b().trim().equals("");
      return !this.field_96256_r;
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) {
      this.field_96257_c.func_73802_a(p_73869_1_, p_73869_2_);
      this.field_96255_b.func_73802_a(p_73869_1_, p_73869_2_);
      if(p_73869_2_ == 15) {
         this.field_96257_c.func_73796_b(!this.field_96257_c.func_73806_l());
         this.field_96255_b.func_73796_b(!this.field_96255_b.func_73806_l());
      }

      if(p_73869_2_ == 28 || p_73869_2_ == 156) {
         this.func_73875_a((GuiButton)this.field_73887_h.get(0));
      }

   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
      this.field_96257_c.func_73793_a(p_73864_1_, p_73864_2_, p_73864_3_);
      this.field_96255_b.func_73793_a(p_73864_1_, p_73864_2_, p_73864_3_);
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_73873_v_();
      this.func_73732_a(this.field_73886_k, I18n.func_135053_a("mco.selectServer.create"), this.field_73880_f / 2, 11, 16777215);
      this.func_73731_b(this.field_73886_k, I18n.func_135053_a("mco.configure.world.name"), this.field_73880_f / 2 - 100, 52, 10526880);
      this.func_73731_b(this.field_73886_k, I18n.func_135053_a("mco.create.world.seed"), this.field_73880_f / 2 - 100, 98, 10526880);
      if(this.field_96256_r) {
         this.func_73732_a(this.field_73886_k, this.field_96254_s, this.field_73880_f / 2, 167, 16711680);
      }

      this.field_96257_c.func_73795_f();
      this.field_96255_b.func_73795_f();
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   public void func_110355_a(WorldTemplate p_110355_1_) {
      this.field_110356_u = p_110355_1_;
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_110354_a(Object p_110354_1_) {
      this.func_110355_a((WorldTemplate)p_110354_1_);
   }

   // $FF: synthetic method
   static Minecraft func_96248_a(GuiScreenCreateOnlineWorld p_96248_0_) {
      return p_96248_0_.field_73882_e;
   }

   // $FF: synthetic method
   static GuiScreen func_96247_b(GuiScreenCreateOnlineWorld p_96247_0_) {
      return p_96247_0_.field_96260_a;
   }

   // $FF: synthetic method
   static Minecraft func_96246_c(GuiScreenCreateOnlineWorld p_96246_0_) {
      return p_96246_0_.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft func_130026_d(GuiScreenCreateOnlineWorld p_130026_0_) {
      return p_130026_0_.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft func_130027_e(GuiScreenCreateOnlineWorld p_130027_0_) {
      return p_130027_0_.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft func_130028_f(GuiScreenCreateOnlineWorld p_130028_0_) {
      return p_130028_0_.field_73882_e;
   }

}
