package net.minecraft.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityLeashKnot extends EntityHanging {

   public EntityLeashKnot(World p_i1592_1_) {
      super(p_i1592_1_);
   }

   public EntityLeashKnot(World p_i1593_1_, int p_i1593_2_, int p_i1593_3_, int p_i1593_4_) {
      super(p_i1593_1_, p_i1593_2_, p_i1593_3_, p_i1593_4_, 0);
      this.func_70107_b((double)p_i1593_2_ + 0.5D, (double)p_i1593_3_ + 0.5D, (double)p_i1593_4_ + 0.5D);
   }

   protected void func_70088_a() {
      super.func_70088_a();
   }

   public void func_82328_a(int p_82328_1_) {}

   public int func_82329_d() {
      return 9;
   }

   public int func_82330_g() {
      return 9;
   }

   @SideOnly(Side.CLIENT)
   public boolean func_70112_a(double p_70112_1_) {
      return p_70112_1_ < 1024.0D;
   }

   public void func_110128_b(Entity p_110128_1_) {}

   public boolean func_70039_c(NBTTagCompound p_70039_1_) {
      return false;
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {}

   public void func_70037_a(NBTTagCompound p_70037_1_) {}

   public boolean func_130002_c(EntityPlayer p_130002_1_) {
      ItemStack var2 = p_130002_1_.func_70694_bm();
      boolean var3 = false;
      double var4;
      List var6;
      Iterator var7;
      EntityLiving var8;
      if(var2 != null && var2.field_77993_c == Item.field_111214_ch.field_77779_bT && !this.field_70170_p.field_72995_K) {
         var4 = 7.0D;
         var6 = this.field_70170_p.func_72872_a(EntityLiving.class, AxisAlignedBB.func_72332_a().func_72299_a(this.field_70165_t - var4, this.field_70163_u - var4, this.field_70161_v - var4, this.field_70165_t + var4, this.field_70163_u + var4, this.field_70161_v + var4));
         if(var6 != null) {
            var7 = var6.iterator();

            while(var7.hasNext()) {
               var8 = (EntityLiving)var7.next();
               if(var8.func_110167_bD() && var8.func_110166_bE() == p_130002_1_) {
                  var8.func_110162_b(this, true);
                  var3 = true;
               }
            }
         }
      }

      if(!this.field_70170_p.field_72995_K && !var3) {
         this.func_70106_y();
         if(p_130002_1_.field_71075_bZ.field_75098_d) {
            var4 = 7.0D;
            var6 = this.field_70170_p.func_72872_a(EntityLiving.class, AxisAlignedBB.func_72332_a().func_72299_a(this.field_70165_t - var4, this.field_70163_u - var4, this.field_70161_v - var4, this.field_70165_t + var4, this.field_70163_u + var4, this.field_70161_v + var4));
            if(var6 != null) {
               var7 = var6.iterator();

               while(var7.hasNext()) {
                  var8 = (EntityLiving)var7.next();
                  if(var8.func_110167_bD() && var8.func_110166_bE() == this) {
                     var8.func_110160_i(true, false);
                  }
               }
            }
         }
      }

      return true;
   }

   public boolean func_70518_d() {
      int var1 = this.field_70170_p.func_72798_a(this.field_70523_b, this.field_70524_c, this.field_70521_d);
      return Block.field_71973_m[var1] != null && Block.field_71973_m[var1].func_71857_b() == 11;
   }

   public static EntityLeashKnot func_110129_a(World p_110129_0_, int p_110129_1_, int p_110129_2_, int p_110129_3_) {
      EntityLeashKnot var4 = new EntityLeashKnot(p_110129_0_, p_110129_1_, p_110129_2_, p_110129_3_);
      var4.field_98038_p = true;
      p_110129_0_.func_72838_d(var4);
      return var4;
   }

   public static EntityLeashKnot func_110130_b(World p_110130_0_, int p_110130_1_, int p_110130_2_, int p_110130_3_) {
      List var4 = p_110130_0_.func_72872_a(EntityLeashKnot.class, AxisAlignedBB.func_72332_a().func_72299_a((double)p_110130_1_ - 1.0D, (double)p_110130_2_ - 1.0D, (double)p_110130_3_ - 1.0D, (double)p_110130_1_ + 1.0D, (double)p_110130_2_ + 1.0D, (double)p_110130_3_ + 1.0D));
      Object var5 = null;
      if(var4 != null) {
         Iterator var6 = var4.iterator();

         while(var6.hasNext()) {
            EntityLeashKnot var7 = (EntityLeashKnot)var6.next();
            if(var7.field_70523_b == p_110130_1_ && var7.field_70524_c == p_110130_2_ && var7.field_70521_d == p_110130_3_) {
               return var7;
            }
         }
      }

      return null;
   }
}
