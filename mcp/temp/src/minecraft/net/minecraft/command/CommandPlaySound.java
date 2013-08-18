package net.minecraft.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet62LevelSound;

public class CommandPlaySound extends CommandBase {

   public String func_71517_b() {
      return "playsound";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.playsound.usage";
   }

   public void func_71515_b(ICommandSender p_71515_1_, String[] p_71515_2_) {
      if(p_71515_2_.length < 2) {
         throw new WrongUsageException(this.func_71518_a(p_71515_1_), new Object[0]);
      } else {
         byte var3 = 0;
         int var36 = var3 + 1;
         String var4 = p_71515_2_[var3];
         EntityPlayerMP var5 = func_82359_c(p_71515_1_, p_71515_2_[var36++]);
         double var6 = (double)var5.func_82114_b().field_71574_a;
         double var8 = (double)var5.func_82114_b().field_71572_b;
         double var10 = (double)var5.func_82114_b().field_71573_c;
         double var12 = 1.0D;
         double var14 = 1.0D;
         double var16 = 0.0D;
         if(p_71515_2_.length > var36) {
            var6 = func_110666_a(p_71515_1_, var6, p_71515_2_[var36++]);
         }

         if(p_71515_2_.length > var36) {
            var8 = func_110665_a(p_71515_1_, var8, p_71515_2_[var36++], 0, 0);
         }

         if(p_71515_2_.length > var36) {
            var10 = func_110666_a(p_71515_1_, var10, p_71515_2_[var36++]);
         }

         if(p_71515_2_.length > var36) {
            var12 = func_110661_a(p_71515_1_, p_71515_2_[var36++], 0.0D, 3.4028234663852886E38D);
         }

         if(p_71515_2_.length > var36) {
            var14 = func_110661_a(p_71515_1_, p_71515_2_[var36++], 0.0D, 2.0D);
         }

         if(p_71515_2_.length > var36) {
            var16 = func_110661_a(p_71515_1_, p_71515_2_[var36++], 0.0D, 1.0D);
         }

         double var18 = var12 > 1.0D?var12 * 16.0D:16.0D;
         double var20 = var5.func_70011_f(var6, var8, var10);
         if(var20 > var18) {
            if(var16 <= 0.0D) {
               throw new CommandException("commands.playsound.playerTooFar", new Object[]{var5.func_70023_ak()});
            }

            double var22 = var6 - var5.field_70165_t;
            double var24 = var8 - var5.field_70163_u;
            double var26 = var10 - var5.field_70161_v;
            double var28 = Math.sqrt(var22 * var22 + var24 * var24 + var26 * var26);
            double var30 = var5.field_70165_t;
            double var32 = var5.field_70163_u;
            double var34 = var5.field_70161_v;
            if(var28 > 0.0D) {
               var30 += var22 / var28 * 2.0D;
               var32 += var24 / var28 * 2.0D;
               var34 += var26 / var28 * 2.0D;
            }

            var5.field_71135_a.func_72567_b(new Packet62LevelSound(var4, var30, var32, var34, (float)var16, (float)var14));
         } else {
            var5.field_71135_a.func_72567_b(new Packet62LevelSound(var4, var6, var8, var10, (float)var12, (float)var14));
         }

         func_71522_a(p_71515_1_, "commands.playsound.success", new Object[]{var4, var5.func_70023_ak()});
      }
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return p_82358_2_ == 1;
   }
}
