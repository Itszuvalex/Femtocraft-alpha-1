package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class GuiConfirmOpenLink extends GuiYesNo {

   private String field_73947_a;
   private String field_73946_b;
   private String field_92028_p;
   private boolean field_92027_q = true;


   public GuiConfirmOpenLink(GuiScreen p_i1179_1_, String p_i1179_2_, int p_i1179_3_, boolean p_i1179_4_) {
      super(p_i1179_1_, I18n.func_135053_a(p_i1179_4_?"chat.link.confirmTrusted":"chat.link.confirm"), p_i1179_2_, p_i1179_3_);
      this.field_73941_c = I18n.func_135053_a(p_i1179_4_?"chat.link.open":"gui.yes");
      this.field_73939_d = I18n.func_135053_a(p_i1179_4_?"gui.cancel":"gui.no");
      this.field_73946_b = I18n.func_135053_a("chat.copy");
      this.field_73947_a = I18n.func_135053_a("chat.link.warning");
      this.field_92028_p = p_i1179_2_;
   }

   public void func_73866_w_() {
      this.field_73887_h.add(new GuiButton(0, this.field_73880_f / 3 - 83 + 0, this.field_73881_g / 6 + 96, 100, 20, this.field_73941_c));
      this.field_73887_h.add(new GuiButton(2, this.field_73880_f / 3 - 83 + 105, this.field_73881_g / 6 + 96, 100, 20, this.field_73946_b));
      this.field_73887_h.add(new GuiButton(1, this.field_73880_f / 3 - 83 + 210, this.field_73881_g / 6 + 96, 100, 20, this.field_73939_d));
   }

   protected void func_73875_a(GuiButton p_73875_1_) {
      if(p_73875_1_.field_73741_f == 2) {
         this.func_73945_e();
      }

      this.field_73942_a.func_73878_a(p_73875_1_.field_73741_f == 0, this.field_73943_n);
   }

   public void func_73945_e() {
      func_73865_d(this.field_92028_p);
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
      if(this.field_92027_q) {
         this.func_73732_a(this.field_73886_k, this.field_73947_a, this.field_73880_f / 2, 110, 16764108);
      }

   }

   public void func_92026_h() {
      this.field_92027_q = false;
   }
}
