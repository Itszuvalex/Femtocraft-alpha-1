package net.minecraft.client.gui.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.text.DateFormat;
import java.util.Date;
import net.minecraft.client.gui.GuiScreenSelectLocation;
import net.minecraft.client.gui.mco.GuiScreenBackup;
import net.minecraft.client.mco.Backup;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.server.MinecraftServer;

@SideOnly(Side.CLIENT)
class GuiScreenBackupSelectionList extends GuiScreenSelectLocation {

   // $FF: synthetic field
   final GuiScreenBackup field_111249_a;


   public GuiScreenBackupSelectionList(GuiScreenBackup p_i1100_1_) {
      super(GuiScreenBackup.func_130036_f(p_i1100_1_), p_i1100_1_.field_73880_f, p_i1100_1_.field_73881_g, 32, p_i1100_1_.field_73881_g - 64, 36);
      this.field_111249_a = p_i1100_1_;
   }

   protected int func_77217_a() {
      return GuiScreenBackup.func_110370_e(this.field_111249_a).size() + 1;
   }

   protected void func_77213_a(int p_77213_1_, boolean p_77213_2_) {
      if(p_77213_1_ < GuiScreenBackup.func_110370_e(this.field_111249_a).size()) {
         GuiScreenBackup.func_130029_a(this.field_111249_a, p_77213_1_);
      }
   }

   protected boolean func_77218_a(int p_77218_1_) {
      return p_77218_1_ == GuiScreenBackup.func_130034_h(this.field_111249_a);
   }

   protected boolean func_104086_b(int p_104086_1_) {
      return false;
   }

   protected int func_130003_b() {
      return this.func_77217_a() * 36;
   }

   protected void func_130004_c() {
      this.field_111249_a.func_73873_v_();
   }

   protected void func_77214_a(int p_77214_1_, int p_77214_2_, int p_77214_3_, int p_77214_4_, Tessellator p_77214_5_) {
      if(p_77214_1_ < GuiScreenBackup.func_110370_e(this.field_111249_a).size()) {
         this.func_111246_b(p_77214_1_, p_77214_2_, p_77214_3_, p_77214_4_, p_77214_5_);
      }

   }

   private void func_111246_b(int p_111246_1_, int p_111246_2_, int p_111246_3_, int p_111246_4_, Tessellator p_111246_5_) {
      Backup var6 = (Backup)GuiScreenBackup.func_110370_e(this.field_111249_a).get(p_111246_1_);
      this.field_111249_a.func_73731_b(GuiScreenBackup.func_130032_i(this.field_111249_a), "Backup (" + this.func_111248_a(Long.valueOf(MinecraftServer.func_130071_aq() - var6.field_110725_b.getTime())) + ")", p_111246_2_ + 2, p_111246_3_ + 1, 16777215);
      this.field_111249_a.func_73731_b(GuiScreenBackup.func_130033_j(this.field_111249_a), this.func_111247_a(var6.field_110725_b), p_111246_2_ + 2, p_111246_3_ + 12, 7105644);
   }

   private String func_111247_a(Date p_111247_1_) {
      return DateFormat.getDateTimeInstance(3, 3).format(p_111247_1_);
   }

   private String func_111248_a(Long p_111248_1_) {
      if(p_111248_1_.longValue() < 0L) {
         return "right now";
      } else {
         long var2 = p_111248_1_.longValue() / 1000L;
         if(var2 < 60L) {
            return (var2 == 1L?"1 second":var2 + " seconds") + " ago";
         } else {
            long var4;
            if(var2 < 3600L) {
               var4 = var2 / 60L;
               return (var4 == 1L?"1 minute":var4 + " minutes") + " ago";
            } else if(var2 < 86400L) {
               var4 = var2 / 3600L;
               return (var4 == 1L?"1 hour":var4 + " hours") + " ago";
            } else {
               var4 = var2 / 86400L;
               return (var4 == 1L?"1 day":var4 + " days") + " ago";
            }
         }
      }
   }
}
