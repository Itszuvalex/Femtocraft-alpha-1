package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandServerTp extends CommandBase {

   public String func_71517_b() {
      return "tp";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.tp.usage";
   }

   public void func_71515_b(ICommandSender p_71515_1_, String[] p_71515_2_) {
      if(p_71515_2_.length < 1) {
         throw new WrongUsageException("commands.tp.usage", new Object[0]);
      } else {
         EntityPlayerMP var3;
         if(p_71515_2_.length != 2 && p_71515_2_.length != 4) {
            var3 = func_71521_c(p_71515_1_);
         } else {
            var3 = func_82359_c(p_71515_1_, p_71515_2_[0]);
            if(var3 == null) {
               throw new PlayerNotFoundException();
            }
         }

         if(p_71515_2_.length != 3 && p_71515_2_.length != 4) {
            if(p_71515_2_.length == 1 || p_71515_2_.length == 2) {
               EntityPlayerMP var11 = func_82359_c(p_71515_1_, p_71515_2_[p_71515_2_.length - 1]);
               if(var11 == null) {
                  throw new PlayerNotFoundException();
               }

               if(var11.field_70170_p != var3.field_70170_p) {
                  func_71522_a(p_71515_1_, "commands.tp.notSameDimension", new Object[0]);
                  return;
               }

               var3.func_70078_a((Entity)null);
               var3.field_71135_a.func_72569_a(var11.field_70165_t, var11.field_70163_u, var11.field_70161_v, var11.field_70177_z, var11.field_70125_A);
               func_71522_a(p_71515_1_, "commands.tp.success", new Object[]{var3.func_70023_ak(), var11.func_70023_ak()});
            }
         } else if(var3.field_70170_p != null) {
            int var4 = p_71515_2_.length - 3;
            double var5 = func_110666_a(p_71515_1_, var3.field_70165_t, p_71515_2_[var4++]);
            double var7 = func_110665_a(p_71515_1_, var3.field_70163_u, p_71515_2_[var4++], 0, 0);
            double var9 = func_110666_a(p_71515_1_, var3.field_70161_v, p_71515_2_[var4++]);
            var3.func_70078_a((Entity)null);
            var3.func_70634_a(var5, var7, var9);
            func_71522_a(p_71515_1_, "commands.tp.success.coordinates", new Object[]{var3.func_70023_ak(), Double.valueOf(var5), Double.valueOf(var7), Double.valueOf(var9)});
         }

      }
   }

   public List func_71516_a(ICommandSender p_71516_1_, String[] p_71516_2_) {
      return p_71516_2_.length != 1 && p_71516_2_.length != 2?null:func_71530_a(p_71516_2_, MinecraftServer.func_71276_C().func_71213_z());
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_2_ == 0;
   }
}
