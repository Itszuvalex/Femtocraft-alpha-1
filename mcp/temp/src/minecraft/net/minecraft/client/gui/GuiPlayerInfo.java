package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPlayerInfo {

   public final String field_78831_a;
   private final String field_78830_c;
   public int field_78829_b;


   public GuiPlayerInfo(String p_i1190_1_) {
      this.field_78831_a = p_i1190_1_;
      this.field_78830_c = p_i1190_1_.toLowerCase();
   }
}
