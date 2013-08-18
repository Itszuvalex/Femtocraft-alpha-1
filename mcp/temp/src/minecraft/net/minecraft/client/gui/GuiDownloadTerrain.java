package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet0KeepAlive;

@SideOnly(Side.CLIENT)
public class GuiDownloadTerrain extends GuiScreen {

   private NetClientHandler field_74261_a;
   private int field_74260_b;


   public GuiDownloadTerrain(NetClientHandler p_i1191_1_) {
      this.field_74261_a = p_i1191_1_;
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) {}

   public void func_73866_w_() {
      this.field_73887_h.clear();
   }

   public void func_73876_c() {
      ++this.field_74260_b;
      if(this.field_74260_b % 20 == 0) {
         this.field_74261_a.func_72552_c(new Packet0KeepAlive());
      }

      if(this.field_74261_a != null) {
         this.field_74261_a.func_72551_d();
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_73871_c(0);
      this.func_73732_a(this.field_73886_k, I18n.func_135053_a("multiplayer.downloadingTerrain"), this.field_73880_f / 2, this.field_73881_g / 2 - 50, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }
}
