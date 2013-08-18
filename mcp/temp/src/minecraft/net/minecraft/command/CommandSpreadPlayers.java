package net.minecraft.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSpreadPlayersPosition;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.PlayerSelector;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CommandSpreadPlayers extends CommandBase {

   public String func_71517_b() {
      return "spreadplayers";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "commands.spreadplayers.usage";
   }

   public void func_71515_b(ICommandSender p_71515_1_, String[] p_71515_2_) {
      if(p_71515_2_.length < 6) {
         throw new WrongUsageException("commands.spreadplayers.usage", new Object[0]);
      } else {
         byte var3 = 0;
         int var17 = var3 + 1;
         double var4 = func_110666_a(p_71515_1_, Double.NaN, p_71515_2_[var3]);
         double var6 = func_110666_a(p_71515_1_, Double.NaN, p_71515_2_[var17++]);
         double var8 = func_110664_a(p_71515_1_, p_71515_2_[var17++], 0.0D);
         double var10 = func_110664_a(p_71515_1_, p_71515_2_[var17++], var8 + 1.0D);
         boolean var12 = func_110662_c(p_71515_1_, p_71515_2_[var17++]);
         ArrayList var13 = Lists.newArrayList();

         while(true) {
            while(var17 < p_71515_2_.length) {
               String var14 = p_71515_2_[var17++];
               if(PlayerSelector.func_82378_b(var14)) {
                  EntityPlayerMP[] var18 = PlayerSelector.func_82380_c(p_71515_1_, var14);
                  if(var18 == null || var18.length == 0) {
                     throw new PlayerNotFoundException();
                  }

                  Collections.addAll(var13, var18);
               } else {
                  EntityPlayerMP var15 = MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(var14);
                  if(var15 == null) {
                     throw new PlayerNotFoundException();
                  }

                  var13.add(var15);
               }
            }

            if(var13.isEmpty()) {
               throw new PlayerNotFoundException();
            }

            p_71515_1_.func_70006_a(ChatMessageComponent.func_111082_b("commands.spreadplayers.spreading." + (var12?"teams":"players"), new Object[]{func_110663_b(var13), Double.valueOf(var4), Double.valueOf(var6), Double.valueOf(var8), Double.valueOf(var10)}));
            this.func_110669_a(p_71515_1_, var13, new CommandSpreadPlayersPosition(var4, var6), var8, var10, ((EntityLivingBase)var13.get(0)).field_70170_p, var12);
            return;
         }
      }
   }

   private void func_110669_a(ICommandSender p_110669_1_, List p_110669_2_, CommandSpreadPlayersPosition p_110669_3_, double p_110669_4_, double p_110669_6_, World p_110669_8_, boolean p_110669_9_) {
      Random var10 = new Random();
      double var11 = p_110669_3_.field_111101_a - p_110669_6_;
      double var13 = p_110669_3_.field_111100_b - p_110669_6_;
      double var15 = p_110669_3_.field_111101_a + p_110669_6_;
      double var17 = p_110669_3_.field_111100_b + p_110669_6_;
      CommandSpreadPlayersPosition[] var19 = this.func_110670_a(var10, p_110669_9_?this.func_110667_a(p_110669_2_):p_110669_2_.size(), var11, var13, var15, var17);
      int var20 = this.func_110668_a(p_110669_3_, p_110669_4_, p_110669_8_, var10, var11, var13, var15, var17, var19, p_110669_9_);
      double var21 = this.func_110671_a(p_110669_2_, p_110669_8_, var19, p_110669_9_);
      func_71522_a(p_110669_1_, "commands.spreadplayers.success." + (p_110669_9_?"teams":"players"), new Object[]{Integer.valueOf(var19.length), Double.valueOf(p_110669_3_.field_111101_a), Double.valueOf(p_110669_3_.field_111100_b)});
      if(var19.length > 1) {
         p_110669_1_.func_70006_a(ChatMessageComponent.func_111082_b("commands.spreadplayers.info." + (p_110669_9_?"teams":"players"), new Object[]{String.format("%.2f", new Object[]{Double.valueOf(var21)}), Integer.valueOf(var20)}));
      }

   }

   private int func_110667_a(List p_110667_1_) {
      HashSet var2 = Sets.newHashSet();
      Iterator var3 = p_110667_1_.iterator();

      while(var3.hasNext()) {
         EntityLivingBase var4 = (EntityLivingBase)var3.next();
         if(var4 instanceof EntityPlayer) {
            var2.add(((EntityPlayer)var4).func_96124_cp());
         } else {
            var2.add((Object)null);
         }
      }

      return var2.size();
   }

   private int func_110668_a(CommandSpreadPlayersPosition p_110668_1_, double p_110668_2_, World p_110668_4_, Random p_110668_5_, double p_110668_6_, double p_110668_8_, double p_110668_10_, double p_110668_12_, CommandSpreadPlayersPosition[] p_110668_14_, boolean p_110668_15_) {
      boolean var16 = true;
      double var18 = 3.4028234663852886E38D;

      int var17;
      for(var17 = 0; var17 < 10000 && var16; ++var17) {
         var16 = false;
         var18 = 3.4028234663852886E38D;

         CommandSpreadPlayersPosition var23;
         int var22;
         for(int var20 = 0; var20 < p_110668_14_.length; ++var20) {
            CommandSpreadPlayersPosition var21 = p_110668_14_[var20];
            var22 = 0;
            var23 = new CommandSpreadPlayersPosition();

            for(int var24 = 0; var24 < p_110668_14_.length; ++var24) {
               if(var20 != var24) {
                  CommandSpreadPlayersPosition var25 = p_110668_14_[var24];
                  double var26 = var21.func_111099_a(var25);
                  var18 = Math.min(var26, var18);
                  if(var26 < p_110668_2_) {
                     ++var22;
                     var23.field_111101_a += var25.field_111101_a - var21.field_111101_a;
                     var23.field_111100_b += var25.field_111100_b - var21.field_111100_b;
                  }
               }
            }

            if(var22 > 0) {
               var23.field_111101_a /= (double)var22;
               var23.field_111100_b /= (double)var22;
               double var30 = (double)var23.func_111096_b();
               if(var30 > 0.0D) {
                  var23.func_111095_a();
                  var21.func_111094_b(var23);
               } else {
                  var21.func_111097_a(p_110668_5_, p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_);
               }

               var16 = true;
            }

            if(var21.func_111093_a(p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_)) {
               var16 = true;
            }
         }

         if(!var16) {
            CommandSpreadPlayersPosition[] var28 = p_110668_14_;
            int var29 = p_110668_14_.length;

            for(var22 = 0; var22 < var29; ++var22) {
               var23 = var28[var22];
               if(!var23.func_111098_b(p_110668_4_)) {
                  var23.func_111097_a(p_110668_5_, p_110668_6_, p_110668_8_, p_110668_10_, p_110668_12_);
                  var16 = true;
               }
            }
         }
      }

      if(var17 >= 10000) {
         throw new CommandException("commands.spreadplayers.failure." + (p_110668_15_?"teams":"players"), new Object[]{Integer.valueOf(p_110668_14_.length), Double.valueOf(p_110668_1_.field_111101_a), Double.valueOf(p_110668_1_.field_111100_b), String.format("%.2f", new Object[]{Double.valueOf(var18)})});
      } else {
         return var17;
      }
   }

   private double func_110671_a(List p_110671_1_, World p_110671_2_, CommandSpreadPlayersPosition[] p_110671_3_, boolean p_110671_4_) {
      double var5 = 0.0D;
      int var7 = 0;
      HashMap var8 = Maps.newHashMap();

      for(int var9 = 0; var9 < p_110671_1_.size(); ++var9) {
         EntityLivingBase var10 = (EntityLivingBase)p_110671_1_.get(var9);
         CommandSpreadPlayersPosition var11;
         if(p_110671_4_) {
            Team var12 = var10 instanceof EntityPlayer?((EntityPlayer)var10).func_96124_cp():null;
            if(!var8.containsKey(var12)) {
               var8.put(var12, p_110671_3_[var7++]);
            }

            var11 = (CommandSpreadPlayersPosition)var8.get(var12);
         } else {
            var11 = p_110671_3_[var7++];
         }

         var10.func_70634_a((double)((float)MathHelper.func_76128_c(var11.field_111101_a) + 0.5F), (double)var11.func_111092_a(p_110671_2_), (double)MathHelper.func_76128_c(var11.field_111100_b) + 0.5D);
         double var17 = Double.MAX_VALUE;

         for(int var14 = 0; var14 < p_110671_3_.length; ++var14) {
            if(var11 != p_110671_3_[var14]) {
               double var15 = var11.func_111099_a(p_110671_3_[var14]);
               var17 = Math.min(var15, var17);
            }
         }

         var5 += var17;
      }

      var5 /= (double)p_110671_1_.size();
      return var5;
   }

   private CommandSpreadPlayersPosition[] func_110670_a(Random p_110670_1_, int p_110670_2_, double p_110670_3_, double p_110670_5_, double p_110670_7_, double p_110670_9_) {
      CommandSpreadPlayersPosition[] var11 = new CommandSpreadPlayersPosition[p_110670_2_];

      for(int var12 = 0; var12 < var11.length; ++var12) {
         CommandSpreadPlayersPosition var13 = new CommandSpreadPlayersPosition();
         var13.func_111097_a(p_110670_1_, p_110670_3_, p_110670_5_, p_110670_7_, p_110670_9_);
         var11[var12] = var13;
      }

      return var11;
   }
}
