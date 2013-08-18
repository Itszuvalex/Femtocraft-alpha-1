package net.minecraft.client.gui.mco;

import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.mco.GuiScreenPendingInvitationINNER1;
import net.minecraft.client.gui.mco.GuiScreenPendingInvitationINNER2;
import net.minecraft.client.gui.mco.GuiScreenPendingInvitationINNER3;
import net.minecraft.client.gui.mco.GuiScreenPendingInvitationList;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class GuiScreenPendingInvitation extends GuiScreen {

   private final GuiScreen field_130061_a;
   private GuiScreenPendingInvitationList field_130059_b;
   private List field_130060_c = Lists.newArrayList();
   private int field_130058_d = -1;


   public GuiScreenPendingInvitation(GuiScreen p_i1129_1_) {
      this.field_130061_a = p_i1129_1_;
   }

   public void func_73866_w_() {
      Keyboard.enableRepeatEvents(true);
      this.field_73887_h.clear();
      this.field_130059_b = new GuiScreenPendingInvitationList(this);
      (new GuiScreenPendingInvitationINNER1(this)).start();
      this.func_130050_g();
   }

   private void func_130050_g() {
      this.field_73887_h.add(new GuiButton(1, this.field_73880_f / 2 - 154, this.field_73881_g - 52, 153, 20, I18n.func_135053_a("mco.invites.button.accept")));
      this.field_73887_h.add(new GuiButton(2, this.field_73880_f / 2 + 6, this.field_73881_g - 52, 153, 20, I18n.func_135053_a("mco.invites.button.reject")));
      this.field_73887_h.add(new GuiButton(0, this.field_73880_f / 2 - 75, this.field_73881_g - 28, 153, 20, I18n.func_135053_a("gui.back")));
   }

   public void func_73876_c() {
      super.func_73876_c();
   }

   protected void func_73875_a(GuiButton p_73875_1_) {
      if(p_73875_1_.field_73742_g) {
         if(p_73875_1_.field_73741_f == 1) {
            this.func_130051_i();
         } else if(p_73875_1_.field_73741_f == 0) {
            this.field_73882_e.func_71373_a(this.field_130061_a);
         } else if(p_73875_1_.field_73741_f == 2) {
            this.func_130057_h();
         } else {
            this.field_130059_b.func_73875_a(p_73875_1_);
         }

      }
   }

   private void func_130057_h() {
      if(this.field_130058_d >= 0 && this.field_130058_d < this.field_130060_c.size()) {
         (new GuiScreenPendingInvitationINNER2(this)).start();
      }

   }

   private void func_130051_i() {
      if(this.field_130058_d >= 0 && this.field_130058_d < this.field_130060_c.size()) {
         (new GuiScreenPendingInvitationINNER3(this)).start();
      }

   }

   private void func_130047_j() {
      int var1 = this.field_130058_d;
      if(this.field_130060_c.size() - 1 == this.field_130058_d) {
         --this.field_130058_d;
      }

      this.field_130060_c.remove(var1);
      if(this.field_130060_c.size() == 0) {
         this.field_130058_d = -1;
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_73873_v_();
      this.field_130059_b.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
      this.func_73732_a(this.field_73886_k, I18n.func_135053_a("mco.invites.title"), this.field_73880_f / 2, 20, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   // $FF: synthetic method
   static Minecraft func_130048_a(GuiScreenPendingInvitation p_130048_0_) {
      return p_130048_0_.field_73882_e;
   }

   // $FF: synthetic method
   static List func_130043_a(GuiScreenPendingInvitation p_130043_0_, List p_130043_1_) {
      return p_130043_0_.field_130060_c = p_130043_1_;
   }

   // $FF: synthetic method
   static Minecraft func_130044_b(GuiScreenPendingInvitation p_130044_0_) {
      return p_130044_0_.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft func_130041_c(GuiScreenPendingInvitation p_130041_0_) {
      return p_130041_0_.field_73882_e;
   }

   // $FF: synthetic method
   static int func_130049_d(GuiScreenPendingInvitation p_130049_0_) {
      return p_130049_0_.field_130058_d;
   }

   // $FF: synthetic method
   static List func_130042_e(GuiScreenPendingInvitation p_130042_0_) {
      return p_130042_0_.field_130060_c;
   }

   // $FF: synthetic method
   static void func_130040_f(GuiScreenPendingInvitation p_130040_0_) {
      p_130040_0_.func_130047_j();
   }

   // $FF: synthetic method
   static Minecraft func_130056_g(GuiScreenPendingInvitation p_130056_0_) {
      return p_130056_0_.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft func_130046_h(GuiScreenPendingInvitation p_130046_0_) {
      return p_130046_0_.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft func_130055_i(GuiScreenPendingInvitation p_130055_0_) {
      return p_130055_0_.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft func_130054_j(GuiScreenPendingInvitation p_130054_0_) {
      return p_130054_0_.field_73882_e;
   }

   // $FF: synthetic method
   static int func_130053_a(GuiScreenPendingInvitation p_130053_0_, int p_130053_1_) {
      return p_130053_0_.field_130058_d = p_130053_1_;
   }

   // $FF: synthetic method
   static FontRenderer func_130045_k(GuiScreenPendingInvitation p_130045_0_) {
      return p_130045_0_.field_73886_k;
   }

   // $FF: synthetic method
   static FontRenderer func_130052_l(GuiScreenPendingInvitation p_130052_0_) {
      return p_130052_0_.field_73886_k;
   }
}
