package net.minecraft.client.stats;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.stats.StatBase;

@SideOnly(Side.CLIENT)
public class StatPlaceholder extends StatBase {

   public StatPlaceholder(int p_i1545_1_) {
      super(p_i1545_1_, "Unknown stat");
   }
}
