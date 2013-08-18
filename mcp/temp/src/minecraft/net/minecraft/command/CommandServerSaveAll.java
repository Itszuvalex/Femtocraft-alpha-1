package net.minecraft.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldServer;

public class CommandServerSaveAll extends CommandBase {

   public String func_71517_b() {
      return "save-all";
   }

   public int func_82362_a() {
      return 4;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.save.usage";
   }

   public void func_71515_b(ICommandSender p_71515_1_, String[] p_71515_2_) {
      MinecraftServer var3 = MinecraftServer.func_71276_C();
      p_71515_1_.func_70006_a(ChatMessageComponent.func_111077_e("commands.save.start"));
      if(var3.func_71203_ab() != null) {
         var3.func_71203_ab().func_72389_g();
      }

      try {
         int var4;
         WorldServer var5;
         boolean var6;
         for(var4 = 0; var4 < var3.field_71305_c.length; ++var4) {
            if(var3.field_71305_c[var4] != null) {
               var5 = var3.field_71305_c[var4];
               var6 = var5.field_73058_d;
               var5.field_73058_d = false;
               var5.func_73044_a(true, (IProgressUpdate)null);
               var5.field_73058_d = var6;
            }
         }

         if(p_71515_2_.length > 0 && "flush".equals(p_71515_2_[0])) {
            p_71515_1_.func_70006_a(ChatMessageComponent.func_111077_e("commands.save.flushStart"));

            for(var4 = 0; var4 < var3.field_71305_c.length; ++var4) {
               if(var3.field_71305_c[var4] != null) {
                  var5 = var3.field_71305_c[var4];
                  var6 = var5.field_73058_d;
                  var5.field_73058_d = false;
                  var5.func_104140_m();
                  var5.field_73058_d = var6;
               }
            }

            p_71515_1_.func_70006_a(ChatMessageComponent.func_111077_e("commands.save.flushEnd"));
         }
      } catch (MinecraftException var7) {
         func_71522_a(p_71515_1_, "commands.save.failed", new Object[]{var7.getMessage()});
         return;
      }

      func_71522_a(p_71515_1_, "commands.save.success", new Object[0]);
   }
}
