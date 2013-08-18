package net.minecraft.client.gui.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreenSelectLocation;
import net.minecraft.client.gui.mco.GuiScreenPendingInvitation;
import net.minecraft.client.mco.PendingInvite;
import net.minecraft.client.renderer.Tessellator;

@SideOnly(Side.CLIENT)
class GuiScreenPendingInvitationList extends GuiScreenSelectLocation {

   // $FF: synthetic field
   final GuiScreenPendingInvitation field_130120_a;


   public GuiScreenPendingInvitationList(GuiScreenPendingInvitation p_i1128_1_) {
      super(GuiScreenPendingInvitation.func_130054_j(p_i1128_1_), p_i1128_1_.field_73880_f, p_i1128_1_.field_73881_g, 32, p_i1128_1_.field_73881_g - 64, 36);
      this.field_130120_a = p_i1128_1_;
   }

   protected int func_77217_a() {
      return GuiScreenPendingInvitation.func_130042_e(this.field_130120_a).size() + 1;
   }

   protected void func_77213_a(int p_77213_1_, boolean p_77213_2_) {
      if(p_77213_1_ < GuiScreenPendingInvitation.func_130042_e(this.field_130120_a).size()) {
         GuiScreenPendingInvitation.func_130053_a(this.field_130120_a, p_77213_1_);
      }
   }

   protected boolean func_77218_a(int p_77218_1_) {
      return p_77218_1_ == GuiScreenPendingInvitation.func_130049_d(this.field_130120_a);
   }

   protected boolean func_104086_b(int p_104086_1_) {
      return false;
   }

   protected int func_130003_b() {
      return this.func_77217_a() * 36;
   }

   protected void func_130004_c() {
      this.field_130120_a.func_73873_v_();
   }

   protected void func_77214_a(int p_77214_1_, int p_77214_2_, int p_77214_3_, int p_77214_4_, Tessellator p_77214_5_) {
      if(p_77214_1_ < GuiScreenPendingInvitation.func_130042_e(this.field_130120_a).size()) {
         this.func_130119_b(p_77214_1_, p_77214_2_, p_77214_3_, p_77214_4_, p_77214_5_);
      }

   }

   private void func_130119_b(int p_130119_1_, int p_130119_2_, int p_130119_3_, int p_130119_4_, Tessellator p_130119_5_) {
      PendingInvite var6 = (PendingInvite)GuiScreenPendingInvitation.func_130042_e(this.field_130120_a).get(p_130119_1_);
      this.field_130120_a.func_73731_b(GuiScreenPendingInvitation.func_130045_k(this.field_130120_a), var6.field_130092_b, p_130119_2_ + 2, p_130119_3_ + 1, 16777215);
      this.field_130120_a.func_73731_b(GuiScreenPendingInvitation.func_130052_l(this.field_130120_a), var6.field_130093_c, p_130119_2_ + 2, p_130119_3_ + 12, 7105644);
   }
}
