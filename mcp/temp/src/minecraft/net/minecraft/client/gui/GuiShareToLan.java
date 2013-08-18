package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.EnumGameType;

@SideOnly(Side.CLIENT)
public class GuiShareToLan extends GuiScreen {

   private final GuiScreen field_74092_a;
   private GuiButton field_74090_b;
   private GuiButton field_74091_c;
   private String field_74089_d = "survival";
   private boolean field_74093_m;


   public GuiShareToLan(GuiScreen p_i1055_1_) {
      this.field_74092_a = p_i1055_1_;
   }

   public void func_73866_w_() {
      this.field_73887_h.clear();
      this.field_73887_h.add(new GuiButton(101, this.field_73880_f / 2 - 155, this.field_73881_g - 28, 150, 20, I18n.func_135053_a("lanServer.start")));
      this.field_73887_h.add(new GuiButton(102, this.field_73880_f / 2 + 5, this.field_73881_g - 28, 150, 20, I18n.func_135053_a("gui.cancel")));
      this.field_73887_h.add(this.field_74091_c = new GuiButton(104, this.field_73880_f / 2 - 155, 100, 150, 20, I18n.func_135053_a("selectWorld.gameMode")));
      this.field_73887_h.add(this.field_74090_b = new GuiButton(103, this.field_73880_f / 2 + 5, 100, 150, 20, I18n.func_135053_a("selectWorld.allowCommands")));
      this.func_74088_g();
   }

   private void func_74088_g() {
      this.field_74091_c.field_73744_e = I18n.func_135053_a("selectWorld.gameMode") + " " + I18n.func_135053_a("selectWorld.gameMode." + this.field_74089_d);
      this.field_74090_b.field_73744_e = I18n.func_135053_a("selectWorld.allowCommands") + " ";
      if(this.field_74093_m) {
         this.field_74090_b.field_73744_e = this.field_74090_b.field_73744_e + I18n.func_135053_a("options.on");
      } else {
         this.field_74090_b.field_73744_e = this.field_74090_b.field_73744_e + I18n.func_135053_a("options.off");
      }

   }

   protected void func_73875_a(GuiButton p_73875_1_) {
      if(p_73875_1_.field_73741_f == 102) {
         this.field_73882_e.func_71373_a(this.field_74092_a);
      } else if(p_73875_1_.field_73741_f == 104) {
         if(this.field_74089_d.equals("survival")) {
            this.field_74089_d = "creative";
         } else if(this.field_74089_d.equals("creative")) {
            this.field_74089_d = "adventure";
         } else {
            this.field_74089_d = "survival";
         }

         this.func_74088_g();
      } else if(p_73875_1_.field_73741_f == 103) {
         this.field_74093_m = !this.field_74093_m;
         this.func_74088_g();
      } else if(p_73875_1_.field_73741_f == 101) {
         this.field_73882_e.func_71373_a((GuiScreen)null);
         String var2 = this.field_73882_e.func_71401_C().func_71206_a(EnumGameType.func_77142_a(this.field_74089_d), this.field_74093_m);
         ChatMessageComponent var3;
         if(var2 != null) {
            var3 = ChatMessageComponent.func_111082_b("commands.publish.started", new Object[]{var2});
         } else {
            var3 = ChatMessageComponent.func_111066_d("commands.publish.failed");
         }

         this.field_73882_e.field_71456_v.func_73827_b().func_73765_a(var3.func_111068_a(true));
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_73873_v_();
      this.func_73732_a(this.field_73886_k, I18n.func_135053_a("lanServer.title"), this.field_73880_f / 2, 50, 16777215);
      this.func_73732_a(this.field_73886_k, I18n.func_135053_a("lanServer.otherPlayers"), this.field_73880_f / 2, 82, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }
}
