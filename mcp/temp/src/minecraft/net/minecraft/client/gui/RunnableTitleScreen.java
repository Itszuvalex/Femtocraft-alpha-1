package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.ExceptionRetryCall;
import net.minecraft.client.mco.McoClient;

@SideOnly(Side.CLIENT)
class RunnableTitleScreen extends Thread {

   // $FF: synthetic field
   final GuiMainMenu field_104058_d;


   RunnableTitleScreen(GuiMainMenu p_i1331_1_) {
      this.field_104058_d = p_i1331_1_;
   }

   public void run() {
      McoClient var1 = new McoClient(GuiMainMenu.func_110348_a(this.field_104058_d).func_110432_I());
      boolean var2 = false;

      for(int var3 = 0; var3 < 3; ++var3) {
         try {
            Boolean var4 = var1.func_96375_b();
            if(var4.booleanValue()) {
               GuiMainMenu.func_130021_b(this.field_104058_d);
            }

            GuiMainMenu.func_110349_a(var4.booleanValue());
         } catch (ExceptionRetryCall var6) {
            var2 = true;
         } catch (ExceptionMcoService var7) {
            GuiMainMenu.func_130018_c(this.field_104058_d).func_98033_al().func_98232_c(var7.toString());
         } catch (IOException var8) {
            GuiMainMenu.func_130019_d(this.field_104058_d).func_98033_al().func_98236_b("Realms: could not parse response");
         }

         if(!var2) {
            break;
         }

         try {
            Thread.sleep(10000L);
         } catch (InterruptedException var5) {
            Thread.currentThread().interrupt();
         }
      }

   }
}
