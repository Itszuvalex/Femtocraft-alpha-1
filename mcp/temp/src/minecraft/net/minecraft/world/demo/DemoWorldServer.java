package net.minecraft.world.demo;

import net.minecraft.logging.ILogAgent;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveHandler;

public class DemoWorldServer extends WorldServer {

   private static final long field_73072_L = (long)"North Carolina".hashCode();
   public static final WorldSettings field_73071_a = (new WorldSettings(field_73072_L, EnumGameType.SURVIVAL, true, false, WorldType.field_77137_b)).func_77159_a();


   public DemoWorldServer(MinecraftServer p_i1512_1_, ISaveHandler p_i1512_2_, String p_i1512_3_, int p_i1512_4_, Profiler p_i1512_5_, ILogAgent p_i1512_6_) {
      super(p_i1512_1_, p_i1512_2_, p_i1512_3_, p_i1512_4_, field_73071_a, p_i1512_5_, p_i1512_6_);
   }

}
