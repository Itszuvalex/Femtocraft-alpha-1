package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreenOnlineServers;
import net.minecraft.client.gui.GuiScreenSelectLocation;
import net.minecraft.client.gui.ThreadConnectToOnlineServer;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
class GuiSlotOnlineServerList extends GuiScreenSelectLocation {

   // $FF: synthetic field
   final GuiScreenOnlineServers field_96294_a;


   public GuiSlotOnlineServerList(GuiScreenOnlineServers p_i1122_1_) {
      super(GuiScreenOnlineServers.func_140037_f(p_i1122_1_), p_i1122_1_.field_73880_f, p_i1122_1_.field_73881_g, 32, p_i1122_1_.field_73881_g - 64, 36);
      this.field_96294_a = p_i1122_1_;
   }

   protected int func_77217_a() {
      return GuiScreenOnlineServers.func_140013_c(this.field_96294_a).size() + 1;
   }

   protected void func_77213_a(int p_77213_1_, boolean p_77213_2_) {
      if(p_77213_1_ < GuiScreenOnlineServers.func_140013_c(this.field_96294_a).size()) {
         McoServer var3 = (McoServer)GuiScreenOnlineServers.func_140013_c(this.field_96294_a).get(p_77213_1_);
         GuiScreenOnlineServers.func_140036_b(this.field_96294_a, var3.field_96408_a);
         if(!GuiScreenOnlineServers.func_140015_g(this.field_96294_a).func_110432_I().func_111285_a().equals(var3.field_96405_e)) {
            GuiScreenOnlineServers.func_140038_h(this.field_96294_a).field_73744_e = I18n.func_135053_a("mco.selectServer.leave");
         } else {
            GuiScreenOnlineServers.func_140038_h(this.field_96294_a).field_73744_e = I18n.func_135053_a("mco.selectServer.configure");
         }

         GuiScreenOnlineServers.func_140033_i(this.field_96294_a).field_73742_g = var3.field_96404_d.equals("OPEN") && !var3.field_98166_h;
         if(p_77213_2_ && GuiScreenOnlineServers.func_140033_i(this.field_96294_a).field_73742_g) {
            GuiScreenOnlineServers.func_140008_c(this.field_96294_a, GuiScreenOnlineServers.func_140041_a(this.field_96294_a));
         }

      }
   }

   protected boolean func_77218_a(int p_77218_1_) {
      return p_77218_1_ == GuiScreenOnlineServers.func_140027_d(this.field_96294_a, GuiScreenOnlineServers.func_140041_a(this.field_96294_a));
   }

   protected boolean func_104086_b(int p_104086_1_) {
      try {
         return p_104086_1_ >= 0 && p_104086_1_ < GuiScreenOnlineServers.func_140013_c(this.field_96294_a).size() && ((McoServer)GuiScreenOnlineServers.func_140013_c(this.field_96294_a).get(p_104086_1_)).field_96405_e.toLowerCase().equals(GuiScreenOnlineServers.func_104032_j(this.field_96294_a).func_110432_I().func_111285_a());
      } catch (Exception var3) {
         return false;
      }
   }

   protected int func_130003_b() {
      return this.func_77217_a() * 36;
   }

   protected void func_130004_c() {
      this.field_96294_a.func_73873_v_();
   }

   protected void func_77214_a(int p_77214_1_, int p_77214_2_, int p_77214_3_, int p_77214_4_, Tessellator p_77214_5_) {
      if(p_77214_1_ < GuiScreenOnlineServers.func_140013_c(this.field_96294_a).size()) {
         this.func_96292_b(p_77214_1_, p_77214_2_, p_77214_3_, p_77214_4_, p_77214_5_);
      }

   }

   private void func_96292_b(int p_96292_1_, int p_96292_2_, int p_96292_3_, int p_96292_4_, Tessellator p_96292_5_) {
      McoServer var6 = (McoServer)GuiScreenOnlineServers.func_140013_c(this.field_96294_a).get(p_96292_1_);
      this.field_96294_a.func_73731_b(GuiScreenOnlineServers.func_140023_k(this.field_96294_a), var6.func_96398_b(), p_96292_2_ + 2, p_96292_3_ + 1, 16777215);
      short var7 = 207;
      byte var8 = 1;
      if(var6.field_98166_h) {
         GuiScreenOnlineServers.func_104031_c(this.field_96294_a, p_96292_2_ + var7, p_96292_3_ + var8, this.field_104094_d, this.field_104095_e);
      } else if(var6.field_96404_d.equals("CLOSED")) {
         GuiScreenOnlineServers.func_140035_b(this.field_96294_a, p_96292_2_ + var7, p_96292_3_ + var8, this.field_104094_d, this.field_104095_e);
      } else if(var6.field_96405_e.equals(GuiScreenOnlineServers.func_140014_l(this.field_96294_a).func_110432_I().func_111285_a()) && var6.field_104063_i < 7) {
         this.func_96293_a(p_96292_1_, p_96292_2_ - 14, p_96292_3_, var6);
         GuiScreenOnlineServers.func_140031_a(this.field_96294_a, p_96292_2_ + var7, p_96292_3_ + var8, this.field_104094_d, this.field_104095_e, var6.field_104063_i);
      } else if(var6.field_96404_d.equals("OPEN")) {
         GuiScreenOnlineServers.func_140020_c(this.field_96294_a, p_96292_2_ + var7, p_96292_3_ + var8, this.field_104094_d, this.field_104095_e);
         this.func_96293_a(p_96292_1_, p_96292_2_ - 14, p_96292_3_, var6);
      }

      this.field_96294_a.func_73731_b(GuiScreenOnlineServers.func_140039_m(this.field_96294_a), var6.func_96397_a(), p_96292_2_ + 2, p_96292_3_ + 12, 7105644);
      this.field_96294_a.func_73731_b(GuiScreenOnlineServers.func_98079_k(this.field_96294_a), var6.field_96405_e, p_96292_2_ + 2, p_96292_3_ + 12 + 11, 5000268);
   }

   private void func_96293_a(int p_96293_1_, int p_96293_2_, int p_96293_3_, McoServer p_96293_4_) {
      if(p_96293_4_.field_96403_g != null) {
         synchronized(GuiScreenOnlineServers.func_140029_i()) {
            if(GuiScreenOnlineServers.func_140018_j() < 5 && (!p_96293_4_.field_96411_l || p_96293_4_.field_102022_m)) {
               (new ThreadConnectToOnlineServer(this, p_96293_4_)).start();
            }
         }

         if(p_96293_4_.field_96414_k != null) {
            this.field_96294_a.func_73731_b(GuiScreenOnlineServers.func_110402_q(this.field_96294_a), p_96293_4_.field_96414_k, p_96293_2_ + 215 - GuiScreenOnlineServers.func_140010_p(this.field_96294_a).func_78256_a(p_96293_4_.field_96414_k), p_96293_3_ + 1, 8421504);
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GuiScreenOnlineServers.func_142023_q(this.field_96294_a).func_110434_K().func_110577_a(Gui.field_110324_m);
      }
   }
}
