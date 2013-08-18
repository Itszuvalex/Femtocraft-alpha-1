package net.minecraft.server;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IProgressUpdate;

public class ConvertingProgressUpdate implements IProgressUpdate {

   private long field_96245_b;
   // $FF: synthetic field
   final MinecraftServer field_74267_a;


   public ConvertingProgressUpdate(MinecraftServer p_i1493_1_) {
      this.field_74267_a = p_i1493_1_;
      this.field_96245_b = MinecraftServer.func_130071_aq();
   }

   public void func_73720_a(String p_73720_1_) {}

   public void func_73718_a(int p_73718_1_) {
      if(MinecraftServer.func_130071_aq() - this.field_96245_b >= 1000L) {
         this.field_96245_b = MinecraftServer.func_130071_aq();
         this.field_74267_a.func_98033_al().func_98233_a("Converting... " + p_73718_1_ + "%");
      }

   }

   public void func_73719_c(String p_73719_1_) {}
}
