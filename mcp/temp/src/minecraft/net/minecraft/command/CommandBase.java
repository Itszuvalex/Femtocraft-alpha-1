package net.minecraft.command;

import com.google.common.primitives.Doubles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.minecraft.command.CommandException;
import net.minecraft.command.IAdminCommand;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public abstract class CommandBase implements ICommand {

   private static IAdminCommand field_71533_a;


   public int func_82362_a() {
      return 4;
   }

   public List func_71514_a() {
      return null;
   }

   public boolean func_71519_b(ICommandSender p_71519_1_) {
      return p_71519_1_.func_70003_b(this.func_82362_a(), this.func_71517_b());
   }

   public List func_71516_a(ICommandSender p_71516_1_, String[] p_71516_2_) {
      return null;
   }

   public static int func_71526_a(ICommandSender p_71526_0_, String p_71526_1_) {
      try {
         return Integer.parseInt(p_71526_1_);
      } catch (NumberFormatException var3) {
         throw new NumberInvalidException("commands.generic.num.invalid", new Object[]{p_71526_1_});
      }
   }

   public static int func_71528_a(ICommandSender p_71528_0_, String p_71528_1_, int p_71528_2_) {
      return func_71532_a(p_71528_0_, p_71528_1_, p_71528_2_, Integer.MAX_VALUE);
   }

   public static int func_71532_a(ICommandSender p_71532_0_, String p_71532_1_, int p_71532_2_, int p_71532_3_) {
      int var4 = func_71526_a(p_71532_0_, p_71532_1_);
      if(var4 < p_71532_2_) {
         throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[]{Integer.valueOf(var4), Integer.valueOf(p_71532_2_)});
      } else if(var4 > p_71532_3_) {
         throw new NumberInvalidException("commands.generic.num.tooBig", new Object[]{Integer.valueOf(var4), Integer.valueOf(p_71532_3_)});
      } else {
         return var4;
      }
   }

   public static double func_82363_b(ICommandSender p_82363_0_, String p_82363_1_) {
      try {
         double var2 = Double.parseDouble(p_82363_1_);
         if(!Doubles.isFinite(var2)) {
            throw new NumberInvalidException("commands.generic.double.invalid", new Object[]{p_82363_1_});
         } else {
            return var2;
         }
      } catch (NumberFormatException var4) {
         throw new NumberInvalidException("commands.generic.double.invalid", new Object[]{p_82363_1_});
      }
   }

   public static double func_110664_a(ICommandSender p_110664_0_, String p_110664_1_, double p_110664_2_) {
      return func_110661_a(p_110664_0_, p_110664_1_, p_110664_2_, Double.MAX_VALUE);
   }

   public static double func_110661_a(ICommandSender p_110661_0_, String p_110661_1_, double p_110661_2_, double p_110661_4_) {
      double var6 = func_82363_b(p_110661_0_, p_110661_1_);
      if(var6 < p_110661_2_) {
         throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[]{Double.valueOf(var6), Double.valueOf(p_110661_2_)});
      } else if(var6 > p_110661_4_) {
         throw new NumberInvalidException("commands.generic.double.tooBig", new Object[]{Double.valueOf(var6), Double.valueOf(p_110661_4_)});
      } else {
         return var6;
      }
   }

   public static boolean func_110662_c(ICommandSender p_110662_0_, String p_110662_1_) {
      if(!p_110662_1_.equals("true") && !p_110662_1_.equals("1")) {
         if(!p_110662_1_.equals("false") && !p_110662_1_.equals("0")) {
            throw new CommandException("commands.generic.boolean.invalid", new Object[]{p_110662_1_});
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   public static EntityPlayerMP func_71521_c(ICommandSender p_71521_0_) {
      if(p_71521_0_ instanceof EntityPlayerMP) {
         return (EntityPlayerMP)p_71521_0_;
      } else {
         throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
      }
   }

   public static EntityPlayerMP func_82359_c(ICommandSender p_82359_0_, String p_82359_1_) {
      EntityPlayerMP var2 = PlayerSelector.func_82386_a(p_82359_0_, p_82359_1_);
      if(var2 != null) {
         return var2;
      } else {
         var2 = MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(p_82359_1_);
         if(var2 == null) {
            throw new PlayerNotFoundException();
         } else {
            return var2;
         }
      }
   }

   public static String func_96332_d(ICommandSender p_96332_0_, String p_96332_1_) {
      EntityPlayerMP var2 = PlayerSelector.func_82386_a(p_96332_0_, p_96332_1_);
      if(var2 != null) {
         return var2.func_70023_ak();
      } else if(PlayerSelector.func_82378_b(p_96332_1_)) {
         throw new PlayerNotFoundException();
      } else {
         return p_96332_1_;
      }
   }

   public static String func_82360_a(ICommandSender p_82360_0_, String[] p_82360_1_, int p_82360_2_) {
      return func_82361_a(p_82360_0_, p_82360_1_, p_82360_2_, false);
   }

   public static String func_82361_a(ICommandSender p_82361_0_, String[] p_82361_1_, int p_82361_2_, boolean p_82361_3_) {
      StringBuilder var4 = new StringBuilder();

      for(int var5 = p_82361_2_; var5 < p_82361_1_.length; ++var5) {
         if(var5 > p_82361_2_) {
            var4.append(" ");
         }

         String var6 = p_82361_1_[var5];
         if(p_82361_3_) {
            String var7 = PlayerSelector.func_82385_b(p_82361_0_, var6);
            if(var7 != null) {
               var6 = var7;
            } else if(PlayerSelector.func_82378_b(var6)) {
               throw new PlayerNotFoundException();
            }
         }

         var4.append(var6);
      }

      return var4.toString();
   }

   public static double func_110666_a(ICommandSender p_110666_0_, double p_110666_1_, String p_110666_3_) {
      return func_110665_a(p_110666_0_, p_110666_1_, p_110666_3_, -30000000, 30000000);
   }

   public static double func_110665_a(ICommandSender p_110665_0_, double p_110665_1_, String p_110665_3_, int p_110665_4_, int p_110665_5_) {
      boolean var6 = p_110665_3_.startsWith("~");
      if(var6 && Double.isNaN(p_110665_1_)) {
         throw new NumberInvalidException("commands.generic.num.invalid", new Object[]{Double.valueOf(p_110665_1_)});
      } else {
         double var7 = var6?p_110665_1_:0.0D;
         if(!var6 || p_110665_3_.length() > 1) {
            boolean var9 = p_110665_3_.contains(".");
            if(var6) {
               p_110665_3_ = p_110665_3_.substring(1);
            }

            var7 += func_82363_b(p_110665_0_, p_110665_3_);
            if(!var9 && !var6) {
               var7 += 0.5D;
            }
         }

         if(p_110665_4_ != 0 || p_110665_5_ != 0) {
            if(var7 < (double)p_110665_4_) {
               throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[]{Double.valueOf(var7), Integer.valueOf(p_110665_4_)});
            }

            if(var7 > (double)p_110665_5_) {
               throw new NumberInvalidException("commands.generic.double.tooBig", new Object[]{Double.valueOf(var7), Integer.valueOf(p_110665_5_)});
            }
         }

         return var7;
      }
   }

   public static String func_71527_a(Object[] p_71527_0_) {
      StringBuilder var1 = new StringBuilder();

      for(int var2 = 0; var2 < p_71527_0_.length; ++var2) {
         String var3 = p_71527_0_[var2].toString();
         if(var2 > 0) {
            if(var2 == p_71527_0_.length - 1) {
               var1.append(" and ");
            } else {
               var1.append(", ");
            }
         }

         var1.append(var3);
      }

      return var1.toString();
   }

   public static String func_96333_a(Collection p_96333_0_) {
      return func_71527_a(p_96333_0_.toArray(new String[p_96333_0_.size()]));
   }

   public static String func_110663_b(Collection p_110663_0_) {
      String[] var1 = new String[p_110663_0_.size()];
      int var2 = 0;

      EntityLivingBase var4;
      for(Iterator var3 = p_110663_0_.iterator(); var3.hasNext(); var1[var2++] = var4.func_96090_ax()) {
         var4 = (EntityLivingBase)var3.next();
      }

      return func_71527_a(var1);
   }

   public static boolean func_71523_a(String p_71523_0_, String p_71523_1_) {
      return p_71523_1_.regionMatches(true, 0, p_71523_0_, 0, p_71523_0_.length());
   }

   public static List func_71530_a(String[] p_71530_0_, String ... p_71530_1_) {
      String var2 = p_71530_0_[p_71530_0_.length - 1];
      ArrayList var3 = new ArrayList();
      String[] var4 = p_71530_1_;
      int var5 = p_71530_1_.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String var7 = var4[var6];
         if(func_71523_a(var2, var7)) {
            var3.add(var7);
         }
      }

      return var3;
   }

   public static List func_71531_a(String[] p_71531_0_, Iterable p_71531_1_) {
      String var2 = p_71531_0_[p_71531_0_.length - 1];
      ArrayList var3 = new ArrayList();
      Iterator var4 = p_71531_1_.iterator();

      while(var4.hasNext()) {
         String var5 = (String)var4.next();
         if(func_71523_a(var2, var5)) {
            var3.add(var5);
         }
      }

      return var3;
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return false;
   }

   public static void func_71522_a(ICommandSender p_71522_0_, String p_71522_1_, Object ... p_71522_2_) {
      func_71524_a(p_71522_0_, 0, p_71522_1_, p_71522_2_);
   }

   public static void func_71524_a(ICommandSender p_71524_0_, int p_71524_1_, String p_71524_2_, Object ... p_71524_3_) {
      if(field_71533_a != null) {
         field_71533_a.func_71563_a(p_71524_0_, p_71524_1_, p_71524_2_, p_71524_3_);
      }

   }

   public static void func_71529_a(IAdminCommand p_71529_0_) {
      field_71533_a = p_71529_0_;
   }

   public int func_71525_a(ICommand p_71525_1_) {
      return this.func_71517_b().compareTo(p_71525_1_.func_71517_b());
   }

   // $FF: synthetic method
   public int compareTo(Object p_compareTo_1_) {
      return this.func_71525_a((ICommand)p_compareTo_1_);
   }
}
