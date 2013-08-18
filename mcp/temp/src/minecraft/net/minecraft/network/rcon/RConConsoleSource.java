package net.minecraft.network.rcon;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class RConConsoleSource implements ICommandSender {

   public static final RConConsoleSource field_70010_a = new RConConsoleSource();
   private StringBuffer field_70009_b = new StringBuffer();


   public void func_70007_b() {
      this.field_70009_b.setLength(0);
   }

   public String func_70008_c() {
      return this.field_70009_b.toString();
   }

   public String func_70005_c_() {
      return "Rcon";
   }

   public void func_70006_a(ChatMessageComponent p_70006_1_) {
      this.field_70009_b.append(p_70006_1_.toString());
   }

   public boolean func_70003_b(int p_70003_1_, String p_70003_2_) {
      return true;
   }

   public ChunkCoordinates func_82114_b() {
      return new ChunkCoordinates(0, 0, 0);
   }

   public World func_130014_f_() {
      return MinecraftServer.func_71276_C().func_130014_f_();
   }

}
