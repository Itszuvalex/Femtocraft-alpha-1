package net.minecraft.server.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import net.minecraft.server.dedicated.DedicatedServer;

@SideOnly(Side.SERVER)
final class MinecraftServerGuiINNER1 extends WindowAdapter {

   // $FF: synthetic field
   final DedicatedServer field_120023_a;


   MinecraftServerGuiINNER1(DedicatedServer p_i2363_1_) {
      this.field_120023_a = p_i2363_1_;
   }

   public void windowClosing(WindowEvent p_windowClosing_1_) {
      this.field_120023_a.func_71263_m();

      while(!this.field_120023_a.func_71241_aa()) {
         try {
            Thread.sleep(100L);
         } catch (InterruptedException var3) {
            var3.printStackTrace();
         }
      }

      System.exit(0);
   }
}
