package net.minecraft.server.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.MinecraftServerGui;

@SideOnly(Side.SERVER)
class MinecraftServerGuiINNER2 implements ActionListener {

   // $FF: synthetic field
   final JTextField field_120025_a;
   // $FF: synthetic field
   final MinecraftServerGui field_120024_b;


   MinecraftServerGuiINNER2(MinecraftServerGui p_i2364_1_, JTextField p_i2364_2_) {
      this.field_120024_b = p_i2364_1_;
      this.field_120025_a = p_i2364_2_;
   }

   public void actionPerformed(ActionEvent p_actionPerformed_1_) {
      String var2 = this.field_120025_a.getText().trim();
      if(var2.length() > 0) {
         MinecraftServerGui.func_120017_a(this.field_120024_b).func_71331_a(var2, MinecraftServer.func_71276_C());
      }

      this.field_120025_a.setText("");
   }
}
