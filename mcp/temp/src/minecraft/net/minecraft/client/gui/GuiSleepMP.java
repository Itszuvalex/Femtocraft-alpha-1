package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet19EntityAction;

@SideOnly(Side.CLIENT)
public class GuiSleepMP extends GuiChat {

   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_73887_h.add(new GuiButton(1, this.field_73880_f / 2 - 100, this.field_73881_g - 40, I18n.func_135053_a("multiplayer.stopSleeping")));
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) {
      if(p_73869_2_ == 1) {
         this.func_73906_g();
      } else if(p_73869_2_ != 28 && p_73869_2_ != 156) {
         super.func_73869_a(p_73869_1_, p_73869_2_);
      } else {
         String var3 = this.field_73901_a.func_73781_b().trim();
         if(var3.length() > 0) {
            this.field_73882_e.field_71439_g.func_71165_d(var3);
         }

         this.field_73901_a.func_73782_a("");
         this.field_73882_e.field_71456_v.func_73827_b().func_73764_c();
      }

   }

   protected void func_73875_a(GuiButton p_73875_1_) {
      if(p_73875_1_.field_73741_f == 1) {
         this.func_73906_g();
      } else {
         super.func_73875_a(p_73875_1_);
      }

   }

   private void func_73906_g() {
      NetClientHandler var1 = this.field_73882_e.field_71439_g.field_71174_a;
      var1.func_72552_c(new Packet19EntityAction(this.field_73882_e.field_71439_g, 3));
   }
}
