package net.minecraft.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.DamageSource;

public class CommandKill extends CommandBase {

   public String func_71517_b() {
      return "kill";
   }

   public int func_82362_a() {
      return 0;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.kill.usage";
   }

   public void func_71515_b(ICommandSender p_71515_1_, String[] p_71515_2_) {
      EntityPlayerMP var3 = func_71521_c(p_71515_1_);
      var3.func_70097_a(DamageSource.field_76380_i, Float.MAX_VALUE);
      p_71515_1_.func_70006_a(ChatMessageComponent.func_111077_e("commands.kill.success"));
   }
}
