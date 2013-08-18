package net.minecraft.command;

import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public interface ICommandSender {

   String func_70005_c_();

   void func_70006_a(ChatMessageComponent var1);

   boolean func_70003_b(int var1, String var2);

   ChunkCoordinates func_82114_b();

   World func_130014_f_();
}
