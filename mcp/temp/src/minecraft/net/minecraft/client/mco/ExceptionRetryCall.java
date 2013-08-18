package net.minecraft.client.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.mco.ExceptionMcoService;

@SideOnly(Side.CLIENT)
public class ExceptionRetryCall extends ExceptionMcoService {

   public final int field_96393_c;


   public ExceptionRetryCall(int p_i1146_1_) {
      super(503, "Retry operation", -1);
      this.field_96393_c = p_i1146_1_;
   }
}
