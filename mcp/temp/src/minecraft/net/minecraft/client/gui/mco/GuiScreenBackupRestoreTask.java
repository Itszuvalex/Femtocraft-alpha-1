package net.minecraft.client.gui.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.TaskLongRunning;
import net.minecraft.client.gui.mco.GuiScreenBackup;
import net.minecraft.client.gui.mco.GuiScreenBackupDownloadThread;
import net.minecraft.client.mco.Backup;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
class GuiScreenBackupRestoreTask extends TaskLongRunning {

   private final Backup field_111254_c;
   // $FF: synthetic field
   final GuiScreenBackup field_111255_a;


   private GuiScreenBackupRestoreTask(GuiScreenBackup p_i1101_1_, Backup p_i1101_2_) {
      this.field_111255_a = p_i1101_1_;
      this.field_111254_c = p_i1101_2_;
   }

   public void run() {
      this.func_96576_b(I18n.func_135053_a("mco.backup.restoring"));

      try {
         McoClient var1 = new McoClient(this.func_96578_b().func_110432_I());
         var1.func_111235_c(GuiScreenBackup.func_110367_b(this.field_111255_a), this.field_111254_c.field_110727_a);

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var3) {
            Thread.currentThread().interrupt();
         }

         this.func_96578_b().func_71373_a(GuiScreenBackup.func_130031_d(this.field_111255_a));
      } catch (ExceptionMcoService var4) {
         GuiScreenBackup.func_130035_e(this.field_111255_a).func_98033_al().func_98232_c(var4.toString());
         this.func_96575_a(var4.toString());
      } catch (Exception var5) {
         this.func_96575_a(var5.getLocalizedMessage());
      }

   }

   // $FF: synthetic method
   GuiScreenBackupRestoreTask(GuiScreenBackup p_i1102_1_, Backup p_i1102_2_, GuiScreenBackupDownloadThread p_i1102_3_) {
      this(p_i1102_1_, p_i1102_2_);
   }
}
