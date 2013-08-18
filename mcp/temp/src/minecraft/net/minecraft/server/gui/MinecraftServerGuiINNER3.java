package net.minecraft.server.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import net.minecraft.server.gui.MinecraftServerGui;

@SideOnly(Side.SERVER)
class MinecraftServerGuiINNER3 extends FocusAdapter {

   // $FF: synthetic field
   final MinecraftServerGui field_120032_a;


   MinecraftServerGuiINNER3(MinecraftServerGui p_i2365_1_) {
      this.field_120032_a = p_i2365_1_;
   }

   public void focusGained(FocusEvent p_focusGained_1_) {}
}
