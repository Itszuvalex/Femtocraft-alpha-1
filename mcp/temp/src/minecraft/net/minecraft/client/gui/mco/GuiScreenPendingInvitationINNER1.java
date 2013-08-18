package net.minecraft.client.gui.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.mco.GuiScreenPendingInvitation;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;

@SideOnly(Side.CLIENT)
class GuiScreenPendingInvitationINNER1 extends Thread {

   // $FF: synthetic field
   final GuiScreenPendingInvitation field_130121_a;


   GuiScreenPendingInvitationINNER1(GuiScreenPendingInvitation p_i1125_1_) {
      this.field_130121_a = p_i1125_1_;
   }

   public void run() {
      McoClient var1 = new McoClient(GuiScreenPendingInvitation.func_130048_a(this.field_130121_a).func_110432_I());

      try {
         GuiScreenPendingInvitation.func_130043_a(this.field_130121_a, var1.func_130108_f().field_130096_a);
      } catch (ExceptionMcoService var3) {
         GuiScreenPendingInvitation.func_130044_b(this.field_130121_a).func_98033_al().func_98232_c(var3.toString());
      }

   }
}
