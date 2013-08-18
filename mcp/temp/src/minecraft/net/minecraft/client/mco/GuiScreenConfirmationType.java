package net.minecraft.client.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public enum GuiScreenConfirmationType {

   Warning("Warning", 0, "Warning!", 16711680),
   Info("Info", 1, "Info!", 8226750);
   public final int field_140075_c;
   public final String field_140072_d;
   // $FF: synthetic field
   private static final GuiScreenConfirmationType[] field_140073_e = new GuiScreenConfirmationType[]{Warning, Info};


   private GuiScreenConfirmationType(String p_i2324_1_, int p_i2324_2_, String p_i2324_3_, int p_i2324_4_) {
      this.field_140072_d = p_i2324_3_;
      this.field_140075_c = p_i2324_4_;
   }

}
