package net.minecraft.client.gui.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.mco.GuiScreenMcoWorldTemplate;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;

@SideOnly(Side.CLIENT)
class GuiScreenMcoWorldTemplateDownloadThread extends Thread {

   // $FF: synthetic field
   final GuiScreenMcoWorldTemplate field_111256_a;


   GuiScreenMcoWorldTemplateDownloadThread(GuiScreenMcoWorldTemplate p_i1113_1_) {
      this.field_111256_a = p_i1113_1_;
   }

   public void run() {
      McoClient var1 = new McoClient(GuiScreenMcoWorldTemplate.func_110382_a(this.field_111256_a).func_110432_I());

      try {
         GuiScreenMcoWorldTemplate.func_110388_a(this.field_111256_a, var1.func_111231_d().field_110736_a);
      } catch (ExceptionMcoService var3) {
         GuiScreenMcoWorldTemplate.func_110392_b(this.field_111256_a).func_98033_al().func_98232_c(var3.toString());
      }

   }
}
