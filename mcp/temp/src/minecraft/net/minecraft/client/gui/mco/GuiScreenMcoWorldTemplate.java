package net.minecraft.client.gui.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.mco.GuiScreenMcoWorldTemplateDownloadThread;
import net.minecraft.client.gui.mco.GuiScreenMcoWorldTemplateSelectionList;
import net.minecraft.client.gui.mco.ScreenWithCallback;
import net.minecraft.client.mco.WorldTemplate;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class GuiScreenMcoWorldTemplate extends GuiScreen {

   private final ScreenWithCallback field_110401_a;
   private WorldTemplate field_110398_b;
   private List field_110399_c = Collections.emptyList();
   private GuiScreenMcoWorldTemplateSelectionList field_110396_d;
   private int field_110397_e = -1;
   private GuiButton field_110400_p;


   public GuiScreenMcoWorldTemplate(ScreenWithCallback p_i1115_1_, WorldTemplate p_i1115_2_) {
      this.field_110401_a = p_i1115_1_;
      this.field_110398_b = p_i1115_2_;
   }

   public void func_73866_w_() {
      Keyboard.enableRepeatEvents(true);
      this.field_73887_h.clear();
      this.field_110396_d = new GuiScreenMcoWorldTemplateSelectionList(this);
      (new GuiScreenMcoWorldTemplateDownloadThread(this)).start();
      this.func_110385_g();
   }

   private void func_110385_g() {
      this.field_73887_h.add(new GuiButton(0, this.field_73880_f / 2 + 6, this.field_73881_g - 52, 153, 20, I18n.func_135053_a("gui.cancel")));
      this.field_73887_h.add(this.field_110400_p = new GuiButton(1, this.field_73880_f / 2 - 154, this.field_73881_g - 52, 153, 20, I18n.func_135053_a("mco.template.button.select")));
   }

   public void func_73876_c() {
      super.func_73876_c();
   }

   protected void func_73875_a(GuiButton p_73875_1_) {
      if(p_73875_1_.field_73742_g) {
         if(p_73875_1_.field_73741_f == 1) {
            this.func_110394_h();
         } else if(p_73875_1_.field_73741_f == 0) {
            this.field_110401_a.func_110354_a((Object)null);
            this.field_73882_e.func_71373_a(this.field_110401_a);
         } else {
            this.field_110396_d.func_73875_a(p_73875_1_);
         }

      }
   }

   private void func_110394_h() {
      if(this.field_110397_e >= 0 && this.field_110397_e < this.field_110399_c.size()) {
         this.field_110401_a.func_110354_a(this.field_110399_c.get(this.field_110397_e));
         this.field_73882_e.func_71373_a(this.field_110401_a);
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_73873_v_();
      this.field_110396_d.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
      this.func_73732_a(this.field_73886_k, I18n.func_135053_a("mco.template.title"), this.field_73880_f / 2, 20, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   // $FF: synthetic method
   static Minecraft func_110382_a(GuiScreenMcoWorldTemplate p_110382_0_) {
      return p_110382_0_.field_73882_e;
   }

   // $FF: synthetic method
   static List func_110388_a(GuiScreenMcoWorldTemplate p_110388_0_, List p_110388_1_) {
      return p_110388_0_.field_110399_c = p_110388_1_;
   }

   // $FF: synthetic method
   static Minecraft func_110392_b(GuiScreenMcoWorldTemplate p_110392_0_) {
      return p_110392_0_.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft func_130066_c(GuiScreenMcoWorldTemplate p_130066_0_) {
      return p_130066_0_.field_73882_e;
   }

   // $FF: synthetic method
   static List func_110395_c(GuiScreenMcoWorldTemplate p_110395_0_) {
      return p_110395_0_.field_110399_c;
   }

   // $FF: synthetic method
   static int func_130064_a(GuiScreenMcoWorldTemplate p_130064_0_, int p_130064_1_) {
      return p_130064_0_.field_110397_e = p_130064_1_;
   }

   // $FF: synthetic method
   static WorldTemplate func_130065_a(GuiScreenMcoWorldTemplate p_130065_0_, WorldTemplate p_130065_1_) {
      return p_130065_0_.field_110398_b = p_130065_1_;
   }

   // $FF: synthetic method
   static WorldTemplate func_130067_e(GuiScreenMcoWorldTemplate p_130067_0_) {
      return p_130067_0_.field_110398_b;
   }

   // $FF: synthetic method
   static int func_130062_f(GuiScreenMcoWorldTemplate p_130062_0_) {
      return p_130062_0_.field_110397_e;
   }

   // $FF: synthetic method
   static FontRenderer func_110389_g(GuiScreenMcoWorldTemplate p_110389_0_) {
      return p_110389_0_.field_73886_k;
   }

   // $FF: synthetic method
   static FontRenderer func_110387_h(GuiScreenMcoWorldTemplate p_110387_0_) {
      return p_110387_0_.field_73886_k;
   }

   // $FF: synthetic method
   static FontRenderer func_110384_i(GuiScreenMcoWorldTemplate p_110384_0_) {
      return p_110384_0_.field_73886_k;
   }

   // $FF: synthetic method
   static FontRenderer func_130063_j(GuiScreenMcoWorldTemplate p_130063_0_) {
      return p_130063_0_.field_73886_k;
   }
}
