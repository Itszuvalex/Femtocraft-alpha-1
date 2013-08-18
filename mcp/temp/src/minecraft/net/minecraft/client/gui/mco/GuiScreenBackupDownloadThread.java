package net.minecraft.client.gui.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.mco.GuiScreenBackup;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;

@SideOnly(Side.CLIENT)
class GuiScreenBackupDownloadThread extends Thread {

   // $FF: synthetic field
   final GuiScreenBackup field_111250_a;


   GuiScreenBackupDownloadThread(GuiScreenBackup p_i1099_1_) {
      this.field_111250_a = p_i1099_1_;
   }

   public void run() {
      McoClient var1 = new McoClient(GuiScreenBackup.func_110366_a(this.field_111250_a).func_110432_I());

      try {
         GuiScreenBackup.func_110373_a(this.field_111250_a, var1.func_111232_c(GuiScreenBackup.func_110367_b(this.field_111250_a)).field_111223_a);
      } catch (ExceptionMcoService var3) {
         GuiScreenBackup.func_130030_c(this.field_111250_a).func_98033_al().func_98232_c(var3.toString());
      }

   }
}
