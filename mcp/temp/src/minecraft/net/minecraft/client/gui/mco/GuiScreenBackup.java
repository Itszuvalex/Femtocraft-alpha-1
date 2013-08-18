package net.minecraft.client.gui.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenConfigureWorld;
import net.minecraft.client.gui.GuiScreenConfirmation;
import net.minecraft.client.gui.GuiScreenLongRunningTask;
import net.minecraft.client.gui.mco.GuiScreenBackupDownloadThread;
import net.minecraft.client.gui.mco.GuiScreenBackupRestoreTask;
import net.minecraft.client.gui.mco.GuiScreenBackupSelectionList;
import net.minecraft.client.mco.Backup;
import net.minecraft.client.mco.GuiScreenConfirmationType;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class GuiScreenBackup extends GuiScreen {

   private final GuiScreenConfigureWorld field_110380_a;
   private final long field_110377_b;
   private List field_110378_c = Collections.emptyList();
   private GuiScreenBackupSelectionList field_110375_d;
   private int field_110376_e = -1;
   private GuiButton field_110379_p;


   public GuiScreenBackup(GuiScreenConfigureWorld p_i1103_1_, long p_i1103_2_) {
      this.field_110380_a = p_i1103_1_;
      this.field_110377_b = p_i1103_2_;
   }

   public void func_73866_w_() {
      Keyboard.enableRepeatEvents(true);
      this.field_73887_h.clear();
      this.field_110375_d = new GuiScreenBackupSelectionList(this);
      (new GuiScreenBackupDownloadThread(this)).start();
      this.func_110369_g();
   }

   private void func_110369_g() {
      this.field_73887_h.add(new GuiButton(0, this.field_73880_f / 2 + 6, this.field_73881_g - 52, 153, 20, I18n.func_135053_a("gui.back")));
      this.field_73887_h.add(this.field_110379_p = new GuiButton(1, this.field_73880_f / 2 - 154, this.field_73881_g - 52, 153, 20, I18n.func_135053_a("mco.backup.button.restore")));
   }

   public void func_73876_c() {
      super.func_73876_c();
   }

   protected void func_73875_a(GuiButton p_73875_1_) {
      if(p_73875_1_.field_73742_g) {
         if(p_73875_1_.field_73741_f == 1) {
            String var2 = I18n.func_135053_a("mco.configure.world.restore.question.line1");
            String var3 = I18n.func_135053_a("mco.configure.world.restore.question.line2");
            this.field_73882_e.func_71373_a(new GuiScreenConfirmation(this, GuiScreenConfirmationType.Warning, var2, var3, 1));
         } else if(p_73875_1_.field_73741_f == 0) {
            this.field_73882_e.func_71373_a(this.field_110380_a);
         } else {
            this.field_110375_d.func_73875_a(p_73875_1_);
         }

      }
   }

   public void func_73878_a(boolean p_73878_1_, int p_73878_2_) {
      if(p_73878_1_ && p_73878_2_ == 1) {
         this.func_110374_h();
      } else {
         this.field_73882_e.func_71373_a(this);
      }

   }

   private void func_110374_h() {
      if(this.field_110376_e >= 0 && this.field_110376_e < this.field_110378_c.size()) {
         Backup var1 = (Backup)this.field_110378_c.get(this.field_110376_e);
         GuiScreenBackupRestoreTask var2 = new GuiScreenBackupRestoreTask(this, var1, (GuiScreenBackupDownloadThread)null);
         GuiScreenLongRunningTask var3 = new GuiScreenLongRunningTask(this.field_73882_e, this.field_110380_a, var2);
         var3.func_98117_g();
         this.field_73882_e.func_71373_a(var3);
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_73873_v_();
      this.field_110375_d.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
      this.func_73732_a(this.field_73886_k, I18n.func_135053_a("mco.backup.title"), this.field_73880_f / 2, 20, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   // $FF: synthetic method
   static Minecraft func_110366_a(GuiScreenBackup p_110366_0_) {
      return p_110366_0_.field_73882_e;
   }

   // $FF: synthetic method
   static List func_110373_a(GuiScreenBackup p_110373_0_, List p_110373_1_) {
      return p_110373_0_.field_110378_c = p_110373_1_;
   }

   // $FF: synthetic method
   static long func_110367_b(GuiScreenBackup p_110367_0_) {
      return p_110367_0_.field_110377_b;
   }

   // $FF: synthetic method
   static Minecraft func_130030_c(GuiScreenBackup p_130030_0_) {
      return p_130030_0_.field_73882_e;
   }

   // $FF: synthetic method
   static GuiScreenConfigureWorld func_130031_d(GuiScreenBackup p_130031_0_) {
      return p_130031_0_.field_110380_a;
   }

   // $FF: synthetic method
   static Minecraft func_130035_e(GuiScreenBackup p_130035_0_) {
      return p_130035_0_.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft func_130036_f(GuiScreenBackup p_130036_0_) {
      return p_130036_0_.field_73882_e;
   }

   // $FF: synthetic method
   static List func_110370_e(GuiScreenBackup p_110370_0_) {
      return p_110370_0_.field_110378_c;
   }

   // $FF: synthetic method
   static int func_130029_a(GuiScreenBackup p_130029_0_, int p_130029_1_) {
      return p_130029_0_.field_110376_e = p_130029_1_;
   }

   // $FF: synthetic method
   static int func_130034_h(GuiScreenBackup p_130034_0_) {
      return p_130034_0_.field_110376_e;
   }

   // $FF: synthetic method
   static FontRenderer func_130032_i(GuiScreenBackup p_130032_0_) {
      return p_130032_0_.field_73886_k;
   }

   // $FF: synthetic method
   static FontRenderer func_130033_j(GuiScreenBackup p_130033_0_) {
      return p_130033_0_.field_73886_k;
   }
}
