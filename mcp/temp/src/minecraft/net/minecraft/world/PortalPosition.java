package net.minecraft.world;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.Teleporter;

public class PortalPosition extends ChunkCoordinates {

   public long field_85087_d;
   // $FF: synthetic field
   final Teleporter field_85088_e;


   public PortalPosition(Teleporter p_i1962_1_, int p_i1962_2_, int p_i1962_3_, int p_i1962_4_, long p_i1962_5_) {
      super(p_i1962_2_, p_i1962_3_, p_i1962_4_);
      this.field_85088_e = p_i1962_1_;
      this.field_85087_d = p_i1962_5_;
   }
}
