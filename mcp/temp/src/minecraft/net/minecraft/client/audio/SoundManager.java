package net.minecraft.client.audio;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.client.audio.ScheduledSound;
import net.minecraft.client.audio.SoundManagerINNER1;
import net.minecraft.client.audio.SoundPool;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.client.resources.ResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import org.apache.commons.io.FileUtils;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

@SideOnly(Side.CLIENT)
public class SoundManager implements ResourceManagerReloadListener {

   private static final String[] field_130084_a = new String[]{"ogg"};
   public SoundSystem field_77381_a;
   private boolean field_77376_g;
   public final SoundPool field_77379_b;
   public final SoundPool field_77380_c;
   public final SoundPool field_77377_d;
   private int field_77378_e;
   private final GameSettings field_77375_f;
   private final File field_130085_i;
   private final Set field_82470_g = new HashSet();
   private final List field_92072_h = new ArrayList();
   private Random field_77382_h = new Random();
   private int field_77383_i;


   public SoundManager(ResourceManager p_i1326_1_, GameSettings p_i1326_2_, File p_i1326_3_) {
      this.field_77383_i = this.field_77382_h.nextInt(12000);
      this.field_77375_f = p_i1326_2_;
      this.field_130085_i = p_i1326_3_;
      this.field_77379_b = new SoundPool(p_i1326_1_, "sound", true);
      this.field_77380_c = new SoundPool(p_i1326_1_, "records", false);
      this.field_77377_d = new SoundPool(p_i1326_1_, "music", true);

      try {
         SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
         SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
         SoundSystemConfig.setCodec("wav", CodecWav.class);
      } catch (SoundSystemException var5) {
         var5.printStackTrace();
         System.err.println("error linking with the LibraryJavaSound plug-in");
      }

      this.func_130083_h();
   }

   public void func_110549_a(ResourceManager p_110549_1_) {
      this.func_82464_d();
      this.func_77370_b();
      this.func_77363_d();
   }

   private void func_130083_h() {
      if(this.field_130085_i.isDirectory()) {
         Collection var1 = FileUtils.listFiles(this.field_130085_i, field_130084_a, true);
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            File var3 = (File)var2.next();
            this.func_130081_a(var3);
         }
      }

   }

   private void func_130081_a(File p_130081_1_) {
      String var2 = this.field_130085_i.toURI().relativize(p_130081_1_.toURI()).getPath();
      int var3 = var2.indexOf("/");
      if(var3 != -1) {
         String var4 = var2.substring(0, var3);
         var2 = var2.substring(var3 + 1);
         if("sound".equalsIgnoreCase(var4)) {
            this.func_77372_a(var2);
         } else if("records".equalsIgnoreCase(var4)) {
            this.func_77374_b(var2);
         } else if("music".equalsIgnoreCase(var4)) {
            this.func_77365_c(var2);
         }

      }
   }

   private synchronized void func_77363_d() {
      if(!this.field_77376_g) {
         float var1 = this.field_77375_f.field_74340_b;
         float var2 = this.field_77375_f.field_74342_a;
         this.field_77375_f.field_74340_b = 0.0F;
         this.field_77375_f.field_74342_a = 0.0F;
         this.field_77375_f.func_74303_b();

         try {
            (new Thread(new SoundManagerINNER1(this))).start();
            this.field_77375_f.field_74340_b = var1;
            this.field_77375_f.field_74342_a = var2;
         } catch (RuntimeException var4) {
            var4.printStackTrace();
            System.err.println("error starting SoundSystem turning off sounds & music");
            this.field_77375_f.field_74340_b = 0.0F;
            this.field_77375_f.field_74342_a = 0.0F;
         }

         this.field_77375_f.func_74303_b();
      }
   }

   public void func_77367_a() {
      if(this.field_77376_g) {
         if(this.field_77375_f.field_74342_a == 0.0F) {
            this.field_77381_a.stop("BgMusic");
            this.field_77381_a.stop("streaming");
         } else {
            this.field_77381_a.setVolume("BgMusic", this.field_77375_f.field_74342_a);
            this.field_77381_a.setVolume("streaming", this.field_77375_f.field_74342_a);
         }

      }
   }

   public void func_77370_b() {
      if(this.field_77376_g) {
         this.field_77381_a.cleanup();
         this.field_77376_g = false;
      }

   }

   public void func_77372_a(String p_77372_1_) {
      this.field_77379_b.func_77459_a(p_77372_1_);
   }

   public void func_77374_b(String p_77374_1_) {
      this.field_77380_c.func_77459_a(p_77374_1_);
   }

   public void func_77365_c(String p_77365_1_) {
      this.field_77377_d.func_77459_a(p_77365_1_);
   }

   public void func_77371_c() {
      if(this.field_77376_g && this.field_77375_f.field_74342_a != 0.0F) {
         if(!this.field_77381_a.playing("BgMusic") && !this.field_77381_a.playing("streaming")) {
            if(this.field_77383_i > 0) {
               --this.field_77383_i;
            } else {
               SoundPoolEntry var1 = this.field_77377_d.func_77460_a();
               if(var1 != null) {
                  this.field_77383_i = this.field_77382_h.nextInt(12000) + 12000;
                  this.field_77381_a.backgroundMusic("BgMusic", var1.func_110457_b(), var1.func_110458_a(), false);
                  this.field_77381_a.setVolume("BgMusic", this.field_77375_f.field_74342_a);
                  this.field_77381_a.play("BgMusic");
               }

            }
         }
      }
   }

   public void func_77369_a(EntityLivingBase p_77369_1_, float p_77369_2_) {
      if(this.field_77376_g && this.field_77375_f.field_74340_b != 0.0F && p_77369_1_ != null) {
         float var3 = p_77369_1_.field_70127_C + (p_77369_1_.field_70125_A - p_77369_1_.field_70127_C) * p_77369_2_;
         float var4 = p_77369_1_.field_70126_B + (p_77369_1_.field_70177_z - p_77369_1_.field_70126_B) * p_77369_2_;
         double var5 = p_77369_1_.field_70169_q + (p_77369_1_.field_70165_t - p_77369_1_.field_70169_q) * (double)p_77369_2_;
         double var7 = p_77369_1_.field_70167_r + (p_77369_1_.field_70163_u - p_77369_1_.field_70167_r) * (double)p_77369_2_;
         double var9 = p_77369_1_.field_70166_s + (p_77369_1_.field_70161_v - p_77369_1_.field_70166_s) * (double)p_77369_2_;
         float var11 = MathHelper.func_76134_b(-var4 * 0.017453292F - 3.1415927F);
         float var12 = MathHelper.func_76126_a(-var4 * 0.017453292F - 3.1415927F);
         float var13 = -var12;
         float var14 = -MathHelper.func_76126_a(-var3 * 0.017453292F - 3.1415927F);
         float var15 = -var11;
         float var16 = 0.0F;
         float var17 = 1.0F;
         float var18 = 0.0F;
         this.field_77381_a.setListenerPosition((float)var5, (float)var7, (float)var9);
         this.field_77381_a.setListenerOrientation(var13, var14, var15, var16, var17, var18);
      }
   }

   public void func_82464_d() {
      if(this.field_77376_g) {
         Iterator var1 = this.field_82470_g.iterator();

         while(var1.hasNext()) {
            String var2 = (String)var1.next();
            this.field_77381_a.stop(var2);
         }

         this.field_82470_g.clear();
      }

   }

   public void func_77368_a(String p_77368_1_, float p_77368_2_, float p_77368_3_, float p_77368_4_) {
      if(this.field_77376_g && (this.field_77375_f.field_74340_b != 0.0F || p_77368_1_ == null)) {
         String var5 = "streaming";
         if(this.field_77381_a.playing(var5)) {
            this.field_77381_a.stop(var5);
         }

         if(p_77368_1_ != null) {
            SoundPoolEntry var6 = this.field_77380_c.func_77458_a(p_77368_1_);
            if(var6 != null) {
               if(this.field_77381_a.playing("BgMusic")) {
                  this.field_77381_a.stop("BgMusic");
               }

               this.field_77381_a.newStreamingSource(true, var5, var6.func_110457_b(), var6.func_110458_a(), false, p_77368_2_, p_77368_3_, p_77368_4_, 2, 64.0F);
               this.field_77381_a.setVolume(var5, 0.5F * this.field_77375_f.field_74340_b);
               this.field_77381_a.play(var5);
            }

         }
      }
   }

   public void func_82460_a(Entity p_82460_1_) {
      this.func_82462_a(p_82460_1_, p_82460_1_);
   }

   public void func_82462_a(Entity p_82462_1_, Entity p_82462_2_) {
      String var3 = "entity_" + p_82462_1_.field_70157_k;
      if(this.field_82470_g.contains(var3)) {
         if(this.field_77381_a.playing(var3)) {
            this.field_77381_a.setPosition(var3, (float)p_82462_2_.field_70165_t, (float)p_82462_2_.field_70163_u, (float)p_82462_2_.field_70161_v);
            this.field_77381_a.setVelocity(var3, (float)p_82462_2_.field_70159_w, (float)p_82462_2_.field_70181_x, (float)p_82462_2_.field_70179_y);
         } else {
            this.field_82470_g.remove(var3);
         }
      }

   }

   public boolean func_82465_b(Entity p_82465_1_) {
      if(p_82465_1_ != null && this.field_77376_g) {
         String var2 = "entity_" + p_82465_1_.field_70157_k;
         return this.field_77381_a.playing(var2);
      } else {
         return false;
      }
   }

   public void func_82469_c(Entity p_82469_1_) {
      if(p_82469_1_ != null && this.field_77376_g) {
         String var2 = "entity_" + p_82469_1_.field_70157_k;
         if(this.field_82470_g.contains(var2)) {
            if(this.field_77381_a.playing(var2)) {
               this.field_77381_a.stop(var2);
            }

            this.field_82470_g.remove(var2);
         }

      }
   }

   public void func_82468_a(Entity p_82468_1_, float p_82468_2_) {
      if(p_82468_1_ != null && this.field_77376_g && this.field_77375_f.field_74340_b != 0.0F) {
         String var3 = "entity_" + p_82468_1_.field_70157_k;
         if(this.field_77381_a.playing(var3)) {
            this.field_77381_a.setVolume(var3, p_82468_2_ * this.field_77375_f.field_74340_b);
         }
      }
   }

   public void func_82463_b(Entity p_82463_1_, float p_82463_2_) {
      if(p_82463_1_ != null && this.field_77376_g && this.field_77375_f.field_74340_b != 0.0F) {
         String var3 = "entity_" + p_82463_1_.field_70157_k;
         if(this.field_77381_a.playing(var3)) {
            this.field_77381_a.setPitch(var3, p_82463_2_);
         }
      }
   }

   public void func_82467_a(String p_82467_1_, Entity p_82467_2_, float p_82467_3_, float p_82467_4_, boolean p_82467_5_) {
      if(this.field_77376_g && (this.field_77375_f.field_74340_b != 0.0F || p_82467_1_ == null) && p_82467_2_ != null) {
         String var6 = "entity_" + p_82467_2_.field_70157_k;
         if(this.field_82470_g.contains(var6)) {
            this.func_82460_a(p_82467_2_);
         } else {
            if(this.field_77381_a.playing(var6)) {
               this.field_77381_a.stop(var6);
            }

            if(p_82467_1_ != null) {
               SoundPoolEntry var7 = this.field_77379_b.func_77458_a(p_82467_1_);
               if(var7 != null && p_82467_3_ > 0.0F) {
                  float var8 = 16.0F;
                  if(p_82467_3_ > 1.0F) {
                     var8 *= p_82467_3_;
                  }

                  this.field_77381_a.newSource(p_82467_5_, var6, var7.func_110457_b(), var7.func_110458_a(), false, (float)p_82467_2_.field_70165_t, (float)p_82467_2_.field_70163_u, (float)p_82467_2_.field_70161_v, 2, var8);
                  this.field_77381_a.setLooping(var6, true);
                  this.field_77381_a.setPitch(var6, p_82467_4_);
                  if(p_82467_3_ > 1.0F) {
                     p_82467_3_ = 1.0F;
                  }

                  this.field_77381_a.setVolume(var6, p_82467_3_ * this.field_77375_f.field_74340_b);
                  this.field_77381_a.setVelocity(var6, (float)p_82467_2_.field_70159_w, (float)p_82467_2_.field_70181_x, (float)p_82467_2_.field_70179_y);
                  this.field_77381_a.play(var6);
                  this.field_82470_g.add(var6);
               }

            }
         }
      }
   }

   public void func_77364_b(String p_77364_1_, float p_77364_2_, float p_77364_3_, float p_77364_4_, float p_77364_5_, float p_77364_6_) {
      if(this.field_77376_g && this.field_77375_f.field_74340_b != 0.0F) {
         SoundPoolEntry var7 = this.field_77379_b.func_77458_a(p_77364_1_);
         if(var7 != null && p_77364_5_ > 0.0F) {
            this.field_77378_e = (this.field_77378_e + 1) % 256;
            String var8 = "sound_" + this.field_77378_e;
            float var9 = 16.0F;
            if(p_77364_5_ > 1.0F) {
               var9 *= p_77364_5_;
            }

            this.field_77381_a.newSource(p_77364_5_ > 1.0F, var8, var7.func_110457_b(), var7.func_110458_a(), false, p_77364_2_, p_77364_3_, p_77364_4_, 2, var9);
            if(p_77364_5_ > 1.0F) {
               p_77364_5_ = 1.0F;
            }

            this.field_77381_a.setPitch(var8, p_77364_6_);
            this.field_77381_a.setVolume(var8, p_77364_5_ * this.field_77375_f.field_74340_b);
            this.field_77381_a.play(var8);
         }

      }
   }

   public void func_77366_a(String p_77366_1_, float p_77366_2_, float p_77366_3_) {
      if(this.field_77376_g && this.field_77375_f.field_74340_b != 0.0F) {
         SoundPoolEntry var4 = this.field_77379_b.func_77458_a(p_77366_1_);
         if(var4 != null && p_77366_2_ > 0.0F) {
            this.field_77378_e = (this.field_77378_e + 1) % 256;
            String var5 = "sound_" + this.field_77378_e;
            this.field_77381_a.newSource(false, var5, var4.func_110457_b(), var4.func_110458_a(), false, 0.0F, 0.0F, 0.0F, 0, 0.0F);
            if(p_77366_2_ > 1.0F) {
               p_77366_2_ = 1.0F;
            }

            p_77366_2_ *= 0.25F;
            this.field_77381_a.setPitch(var5, p_77366_3_);
            this.field_77381_a.setVolume(var5, p_77366_2_ * this.field_77375_f.field_74340_b);
            this.field_77381_a.play(var5);
         }

      }
   }

   public void func_82466_e() {
      Iterator var1 = this.field_82470_g.iterator();

      while(var1.hasNext()) {
         String var2 = (String)var1.next();
         this.field_77381_a.pause(var2);
      }

   }

   public void func_82461_f() {
      Iterator var1 = this.field_82470_g.iterator();

      while(var1.hasNext()) {
         String var2 = (String)var1.next();
         this.field_77381_a.play(var2);
      }

   }

   public void func_92071_g() {
      if(!this.field_92072_h.isEmpty()) {
         Iterator var1 = this.field_92072_h.iterator();

         while(var1.hasNext()) {
            ScheduledSound var2 = (ScheduledSound)var1.next();
            --var2.field_92064_g;
            if(var2.field_92064_g <= 0) {
               this.func_77364_b(var2.field_92069_a, var2.field_92067_b, var2.field_92068_c, var2.field_92065_d, var2.field_92066_e, var2.field_92063_f);
               var1.remove();
            }
         }

      }
   }

   public void func_92070_a(String p_92070_1_, float p_92070_2_, float p_92070_3_, float p_92070_4_, float p_92070_5_, float p_92070_6_, int p_92070_7_) {
      this.field_92072_h.add(new ScheduledSound(p_92070_1_, p_92070_2_, p_92070_3_, p_92070_4_, p_92070_5_, p_92070_6_, p_92070_7_));
   }

   // $FF: synthetic method
   static SoundSystem func_130080_a(SoundManager p_130080_0_, SoundSystem p_130080_1_) {
      return p_130080_0_.field_77381_a = p_130080_1_;
   }

   // $FF: synthetic method
   static boolean func_130082_a(SoundManager p_130082_0_, boolean p_130082_1_) {
      return p_130082_0_.field_77376_g = p_130082_1_;
   }

}
