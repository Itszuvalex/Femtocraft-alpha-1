package net.minecraft.client.gui.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreenSelectLocation;
import net.minecraft.client.gui.mco.GuiScreenMcoWorldTemplate;
import net.minecraft.client.mco.WorldTemplate;
import net.minecraft.client.renderer.Tessellator;

@SideOnly(Side.CLIENT)
class GuiScreenMcoWorldTemplateSelectionList extends GuiScreenSelectLocation {

   // $FF: synthetic field
   final GuiScreenMcoWorldTemplate field_111245_a;


   public GuiScreenMcoWorldTemplateSelectionList(GuiScreenMcoWorldTemplate p_i1114_1_) {
      super(GuiScreenMcoWorldTemplate.func_130066_c(p_i1114_1_), p_i1114_1_.field_73880_f, p_i1114_1_.field_73881_g, 32, p_i1114_1_.field_73881_g - 64, 36);
      this.field_111245_a = p_i1114_1_;
   }

   protected int func_77217_a() {
      return GuiScreenMcoWorldTemplate.func_110395_c(this.field_111245_a).size() + 1;
   }

   protected void func_77213_a(int p_77213_1_, boolean p_77213_2_) {
      if(p_77213_1_ < GuiScreenMcoWorldTemplate.func_110395_c(this.field_111245_a).size()) {
         GuiScreenMcoWorldTemplate.func_130064_a(this.field_111245_a, p_77213_1_);
         GuiScreenMcoWorldTemplate.func_130065_a(this.field_111245_a, (WorldTemplate)null);
      }
   }

   protected boolean func_77218_a(int p_77218_1_) {
      return GuiScreenMcoWorldTemplate.func_110395_c(this.field_111245_a).size() == 0?false:(p_77218_1_ >= GuiScreenMcoWorldTemplate.func_110395_c(this.field_111245_a).size()?false:(GuiScreenMcoWorldTemplate.func_130067_e(this.field_111245_a) != null?GuiScreenMcoWorldTemplate.func_130067_e(this.field_111245_a).field_110732_b.equals(((WorldTemplate)GuiScreenMcoWorldTemplate.func_110395_c(this.field_111245_a).get(p_77218_1_)).field_110732_b):p_77218_1_ == GuiScreenMcoWorldTemplate.func_130062_f(this.field_111245_a)));
   }

   protected boolean func_104086_b(int p_104086_1_) {
      return false;
   }

   protected int func_130003_b() {
      return this.func_77217_a() * 36;
   }

   protected void func_130004_c() {
      this.field_111245_a.func_73873_v_();
   }

   protected void func_77214_a(int p_77214_1_, int p_77214_2_, int p_77214_3_, int p_77214_4_, Tessellator p_77214_5_) {
      if(p_77214_1_ < GuiScreenMcoWorldTemplate.func_110395_c(this.field_111245_a).size()) {
         this.func_111244_b(p_77214_1_, p_77214_2_, p_77214_3_, p_77214_4_, p_77214_5_);
      }

   }

   private void func_111244_b(int p_111244_1_, int p_111244_2_, int p_111244_3_, int p_111244_4_, Tessellator p_111244_5_) {
      WorldTemplate var6 = (WorldTemplate)GuiScreenMcoWorldTemplate.func_110395_c(this.field_111245_a).get(p_111244_1_);
      this.field_111245_a.func_73731_b(GuiScreenMcoWorldTemplate.func_110389_g(this.field_111245_a), var6.field_110732_b, p_111244_2_ + 2, p_111244_3_ + 1, 16777215);
      this.field_111245_a.func_73731_b(GuiScreenMcoWorldTemplate.func_110387_h(this.field_111245_a), var6.field_110731_d, p_111244_2_ + 2, p_111244_3_ + 12, 7105644);
      this.field_111245_a.func_73731_b(GuiScreenMcoWorldTemplate.func_110384_i(this.field_111245_a), var6.field_110733_c, p_111244_2_ + 2 + 207 - GuiScreenMcoWorldTemplate.func_130063_j(this.field_111245_a).func_78256_a(var6.field_110733_c), p_111244_3_ + 1, 5000268);
   }
}
