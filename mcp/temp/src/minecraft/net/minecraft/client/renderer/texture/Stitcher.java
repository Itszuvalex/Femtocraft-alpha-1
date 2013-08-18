package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.client.renderer.texture.StitchHolder;
import net.minecraft.client.renderer.texture.StitchSlot;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

@SideOnly(Side.CLIENT)
public class Stitcher {

   private final Set field_94319_a;
   private final List field_94317_b;
   private int field_94318_c;
   private int field_94315_d;
   private final int field_94316_e;
   private final int field_94313_f;
   private final boolean field_94314_g;
   private final int field_94323_h;


   public Stitcher(int p_i1278_1_, int p_i1278_2_, boolean p_i1278_3_) {
      this(p_i1278_1_, p_i1278_2_, p_i1278_3_, 0);
   }

   public Stitcher(int p_i1279_1_, int p_i1279_2_, boolean p_i1279_3_, int p_i1279_4_) {
      this.field_94319_a = new HashSet(256);
      this.field_94317_b = new ArrayList(256);
      this.field_94316_e = p_i1279_1_;
      this.field_94313_f = p_i1279_2_;
      this.field_94314_g = p_i1279_3_;
      this.field_94323_h = p_i1279_4_;
   }

   public int func_110935_a() {
      return this.field_94318_c;
   }

   public int func_110936_b() {
      return this.field_94315_d;
   }

   public void func_110934_a(TextureAtlasSprite p_110934_1_) {
      StitchHolder var2 = new StitchHolder(p_110934_1_);
      if(this.field_94323_h > 0) {
         var2.func_94196_a(this.field_94323_h);
      }

      this.field_94319_a.add(var2);
   }

   public void func_94305_f() {
      StitchHolder[] var1 = (StitchHolder[])this.field_94319_a.toArray(new StitchHolder[this.field_94319_a.size()]);
      Arrays.sort(var1);
      StitchHolder[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         StitchHolder var5 = var2[var4];
         if(!this.func_94310_b(var5)) {
            String var6 = String.format("Unable to fit: %s - size: %dx%d - Maybe try a lowerresolution texturepack?", new Object[]{var5.func_98150_a().func_94215_i(), Integer.valueOf(var5.func_98150_a().func_94211_a()), Integer.valueOf(var5.func_98150_a().func_94216_b())});
            throw new StitcherException(var5, var6);
         }
      }

      if(this.field_94314_g) {
         this.field_94318_c = this.func_94308_a(this.field_94318_c);
         this.field_94315_d = this.func_94308_a(this.field_94315_d);
      }

   }

   public List func_94309_g() {
      ArrayList var1 = Lists.newArrayList();
      Iterator var2 = this.field_94317_b.iterator();

      while(var2.hasNext()) {
         StitchSlot var3 = (StitchSlot)var2.next();
         var3.func_94184_a(var1);
      }

      ArrayList var7 = Lists.newArrayList();
      Iterator var8 = var1.iterator();

      while(var8.hasNext()) {
         StitchSlot var4 = (StitchSlot)var8.next();
         StitchHolder var5 = var4.func_94183_a();
         TextureAtlasSprite var6 = var5.func_98150_a();
         var6.func_110971_a(this.field_94318_c, this.field_94315_d, var4.func_94186_b(), var4.func_94185_c(), var5.func_94195_e());
         var7.add(var6);
      }

      return var7;
   }

   private int func_94308_a(int p_94308_1_) {
      int var2 = p_94308_1_ - 1;
      var2 |= var2 >> 1;
      var2 |= var2 >> 2;
      var2 |= var2 >> 4;
      var2 |= var2 >> 8;
      var2 |= var2 >> 16;
      return var2 + 1;
   }

   private boolean func_94310_b(StitchHolder p_94310_1_) {
      for(int var2 = 0; var2 < this.field_94317_b.size(); ++var2) {
         if(((StitchSlot)this.field_94317_b.get(var2)).func_94182_a(p_94310_1_)) {
            return true;
         }

         p_94310_1_.func_94194_d();
         if(((StitchSlot)this.field_94317_b.get(var2)).func_94182_a(p_94310_1_)) {
            return true;
         }

         p_94310_1_.func_94194_d();
      }

      return this.func_94311_c(p_94310_1_);
   }

   private boolean func_94311_c(StitchHolder p_94311_1_) {
      int var2 = Math.min(p_94311_1_.func_94199_b(), p_94311_1_.func_94197_a());
      boolean var3 = this.field_94318_c == 0 && this.field_94315_d == 0;
      boolean var4;
      if(this.field_94314_g) {
         int var5 = this.func_94308_a(this.field_94318_c);
         int var6 = this.func_94308_a(this.field_94315_d);
         int var7 = this.func_94308_a(this.field_94318_c + var2);
         int var8 = this.func_94308_a(this.field_94315_d + var2);
         boolean var9 = var7 <= this.field_94316_e;
         boolean var10 = var8 <= this.field_94313_f;
         if(!var9 && !var10) {
            return false;
         }

         int var11 = Math.max(p_94311_1_.func_94199_b(), p_94311_1_.func_94197_a());
         if(var3 && !var9 && this.func_94308_a(this.field_94315_d + var11) > this.field_94313_f) {
            return false;
         }

         boolean var12 = var5 != var7;
         boolean var13 = var6 != var8;
         if(var12 ^ var13) {
            var4 = var12 && var9;
         } else {
            var4 = var9 && var5 <= var6;
         }
      } else {
         boolean var14 = this.field_94318_c + var2 <= this.field_94316_e;
         boolean var16 = this.field_94315_d + var2 <= this.field_94313_f;
         if(!var14 && !var16) {
            return false;
         }

         var4 = (var3 || this.field_94318_c <= this.field_94315_d) && var14;
      }

      StitchSlot var15;
      if(var4) {
         if(p_94311_1_.func_94197_a() > p_94311_1_.func_94199_b()) {
            p_94311_1_.func_94194_d();
         }

         if(this.field_94315_d == 0) {
            this.field_94315_d = p_94311_1_.func_94199_b();
         }

         var15 = new StitchSlot(this.field_94318_c, 0, p_94311_1_.func_94197_a(), this.field_94315_d);
         this.field_94318_c += p_94311_1_.func_94197_a();
      } else {
         var15 = new StitchSlot(0, this.field_94315_d, this.field_94318_c, p_94311_1_.func_94199_b());
         this.field_94315_d += p_94311_1_.func_94199_b();
      }

      var15.func_94182_a(p_94311_1_);
      this.field_94317_b.add(var15);
      return true;
   }
}
