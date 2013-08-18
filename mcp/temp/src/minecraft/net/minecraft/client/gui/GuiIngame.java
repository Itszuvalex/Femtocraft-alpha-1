package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.FoodStats;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.chunk.Chunk;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiIngame extends Gui {

   protected static final ResourceLocation field_110329_b = new ResourceLocation("textures/misc/vignette.png");
   protected static final ResourceLocation field_110330_c = new ResourceLocation("textures/gui/widgets.png");
   protected static final ResourceLocation field_110328_d = new ResourceLocation("textures/misc/pumpkinblur.png");
   protected static final RenderItem field_73841_b = new RenderItem();
   protected final Random field_73842_c = new Random();
   protected final Minecraft field_73839_d;
   protected final GuiNewChat field_73840_e;
   protected int field_73837_f;
   protected String field_73838_g = "";
   protected int field_73845_h;
   protected boolean field_73844_j;
   public float field_73843_a = 1.0F;
   protected int field_92017_k;
   protected ItemStack field_92016_l;


   public GuiIngame(Minecraft p_i1036_1_) {
      this.field_73839_d = p_i1036_1_;
      this.field_73840_e = new GuiNewChat(p_i1036_1_);
   }

   public void func_73830_a(float p_73830_1_, boolean p_73830_2_, int p_73830_3_, int p_73830_4_) {
      ScaledResolution var5 = new ScaledResolution(this.field_73839_d.field_71474_y, this.field_73839_d.field_71443_c, this.field_73839_d.field_71440_d);
      int var6 = var5.func_78326_a();
      int var7 = var5.func_78328_b();
      FontRenderer var8 = this.field_73839_d.field_71466_p;
      this.field_73839_d.field_71460_t.func_78478_c();
      GL11.glEnable(3042);
      if(Minecraft.func_71375_t()) {
         this.func_73829_a(this.field_73839_d.field_71439_g.func_70013_c(p_73830_1_), var6, var7);
      } else {
         GL11.glBlendFunc(770, 771);
      }

      ItemStack var9 = this.field_73839_d.field_71439_g.field_71071_by.func_70440_f(3);
      if(this.field_73839_d.field_71474_y.field_74320_O == 0 && var9 != null && var9.field_77993_c == Block.field_72061_ba.field_71990_ca) {
         this.func_73836_a(var6, var7);
      }

      if(!this.field_73839_d.field_71439_g.func_70644_a(Potion.field_76431_k)) {
         float var10 = this.field_73839_d.field_71439_g.field_71080_cy + (this.field_73839_d.field_71439_g.field_71086_bY - this.field_73839_d.field_71439_g.field_71080_cy) * p_73830_1_;
         if(var10 > 0.0F) {
            this.func_130015_b(var10, var6, var7);
         }
      }

      int var11;
      int var12;
      int var13;
      if(!this.field_73839_d.field_71442_b.func_78747_a()) {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_73839_d.func_110434_K().func_110577_a(field_110330_c);
         InventoryPlayer var31 = this.field_73839_d.field_71439_g.field_71071_by;
         this.field_73735_i = -90.0F;
         this.func_73729_b(var6 / 2 - 91, var7 - 22, 0, 0, 182, 22);
         this.func_73729_b(var6 / 2 - 91 - 1 + var31.field_70461_c * 20, var7 - 22 - 1, 0, 22, 24, 22);
         this.field_73839_d.func_110434_K().func_110577_a(field_110324_m);
         GL11.glEnable(3042);
         GL11.glBlendFunc(775, 769);
         this.func_73729_b(var6 / 2 - 7, var7 / 2 - 7, 0, 0, 16, 16);
         GL11.glDisable(3042);
         this.field_73839_d.field_71424_I.func_76320_a("bossHealth");
         this.func_73828_d();
         this.field_73839_d.field_71424_I.func_76319_b();
         if(this.field_73839_d.field_71442_b.func_78755_b()) {
            this.func_110327_a(var6, var7);
         }

         GL11.glDisable(3042);
         this.field_73839_d.field_71424_I.func_76320_a("actionBar");
         GL11.glEnable('\u803a');
         RenderHelper.func_74520_c();

         for(var11 = 0; var11 < 9; ++var11) {
            var12 = var6 / 2 - 90 + var11 * 20 + 2;
            var13 = var7 - 16 - 3;
            this.func_73832_a(var11, var12, var13, p_73830_1_);
         }

         RenderHelper.func_74518_a();
         GL11.glDisable('\u803a');
         this.field_73839_d.field_71424_I.func_76319_b();
      }

      int var32;
      if(this.field_73839_d.field_71439_g.func_71060_bI() > 0) {
         this.field_73839_d.field_71424_I.func_76320_a("sleep");
         GL11.glDisable(2929);
         GL11.glDisable(3008);
         var32 = this.field_73839_d.field_71439_g.func_71060_bI();
         float var34 = (float)var32 / 100.0F;
         if(var34 > 1.0F) {
            var34 = 1.0F - (float)(var32 - 100) / 10.0F;
         }

         var12 = (int)(220.0F * var34) << 24 | 1052704;
         func_73734_a(0, 0, var6, var7, var12);
         GL11.glEnable(3008);
         GL11.glEnable(2929);
         this.field_73839_d.field_71424_I.func_76319_b();
      }

      var32 = 16777215;
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      var11 = var6 / 2 - 91;
      int var14;
      int var15;
      int var17;
      int var16;
      float var33;
      short var37;
      if(this.field_73839_d.field_71439_g.func_110317_t()) {
         this.field_73839_d.field_71424_I.func_76320_a("jumpBar");
         this.field_73839_d.func_110434_K().func_110577_a(Gui.field_110324_m);
         var33 = this.field_73839_d.field_71439_g.func_110319_bJ();
         var37 = 182;
         var14 = (int)(var33 * (float)(var37 + 1));
         var15 = var7 - 32 + 3;
         this.func_73729_b(var11, var15, 0, 84, var37, 5);
         if(var14 > 0) {
            this.func_73729_b(var11, var15, 0, 89, var14, 5);
         }

         this.field_73839_d.field_71424_I.func_76319_b();
      } else if(this.field_73839_d.field_71442_b.func_78763_f()) {
         this.field_73839_d.field_71424_I.func_76320_a("expBar");
         this.field_73839_d.func_110434_K().func_110577_a(Gui.field_110324_m);
         var12 = this.field_73839_d.field_71439_g.func_71050_bK();
         if(var12 > 0) {
            var37 = 182;
            var14 = (int)(this.field_73839_d.field_71439_g.field_71106_cc * (float)(var37 + 1));
            var15 = var7 - 32 + 3;
            this.func_73729_b(var11, var15, 0, 64, var37, 5);
            if(var14 > 0) {
               this.func_73729_b(var11, var15, 0, 69, var14, 5);
            }
         }

         this.field_73839_d.field_71424_I.func_76319_b();
         if(this.field_73839_d.field_71439_g.field_71068_ca > 0) {
            this.field_73839_d.field_71424_I.func_76320_a("expLevel");
            boolean var35 = false;
            var14 = var35?16777215:8453920;
            String var42 = "" + this.field_73839_d.field_71439_g.field_71068_ca;
            var16 = (var6 - var8.func_78256_a(var42)) / 2;
            var17 = var7 - 31 - 4;
            boolean var18 = false;
            var8.func_78276_b(var42, var16 + 1, var17, 0);
            var8.func_78276_b(var42, var16 - 1, var17, 0);
            var8.func_78276_b(var42, var16, var17 + 1, 0);
            var8.func_78276_b(var42, var16, var17 - 1, 0);
            var8.func_78276_b(var42, var16, var17, var14);
            this.field_73839_d.field_71424_I.func_76319_b();
         }
      }

      String var36;
      if(this.field_73839_d.field_71474_y.field_92117_D) {
         this.field_73839_d.field_71424_I.func_76320_a("toolHighlight");
         if(this.field_92017_k > 0 && this.field_92016_l != null) {
            var36 = this.field_92016_l.func_82833_r();
            var13 = (var6 - var8.func_78256_a(var36)) / 2;
            var14 = var7 - 59;
            if(!this.field_73839_d.field_71442_b.func_78755_b()) {
               var14 += 14;
            }

            var15 = (int)((float)this.field_92017_k * 256.0F / 10.0F);
            if(var15 > 255) {
               var15 = 255;
            }

            if(var15 > 0) {
               GL11.glPushMatrix();
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               var8.func_78261_a(var36, var13, var14, 16777215 + (var15 << 24));
               GL11.glDisable(3042);
               GL11.glPopMatrix();
            }
         }

         this.field_73839_d.field_71424_I.func_76319_b();
      }

      if(this.field_73839_d.func_71355_q()) {
         this.field_73839_d.field_71424_I.func_76320_a("demo");
         var36 = "";
         if(this.field_73839_d.field_71441_e.func_82737_E() >= 120500L) {
            var36 = I18n.func_135053_a("demo.demoExpired");
         } else {
            var36 = I18n.func_135052_a("demo.remainingTime", new Object[]{StringUtils.func_76337_a((int)(120500L - this.field_73839_d.field_71441_e.func_82737_E()))});
         }

         var13 = var8.func_78256_a(var36);
         var8.func_78261_a(var36, var6 - var13 - 10, 5, 16777215);
         this.field_73839_d.field_71424_I.func_76319_b();
      }

      int var21;
      int var23;
      int var22;
      if(this.field_73839_d.field_71474_y.field_74330_P) {
         this.field_73839_d.field_71424_I.func_76320_a("debug");
         GL11.glPushMatrix();
         var8.func_78261_a("Minecraft 1.6.2 (" + this.field_73839_d.field_71426_K + ")", 2, 2, 16777215);
         var8.func_78261_a(this.field_73839_d.func_71393_m(), 2, 12, 16777215);
         var8.func_78261_a(this.field_73839_d.func_71408_n(), 2, 22, 16777215);
         var8.func_78261_a(this.field_73839_d.func_71374_p(), 2, 32, 16777215);
         var8.func_78261_a(this.field_73839_d.func_71388_o(), 2, 42, 16777215);
         long var38 = Runtime.getRuntime().maxMemory();
         long var40 = Runtime.getRuntime().totalMemory();
         long var39 = Runtime.getRuntime().freeMemory();
         long var46 = var40 - var39;
         String var20 = "Used memory: " + var46 * 100L / var38 + "% (" + var46 / 1024L / 1024L + "MB) of " + var38 / 1024L / 1024L + "MB";
         var21 = 14737632;
         this.func_73731_b(var8, var20, var6 - var8.func_78256_a(var20) - 2, 2, 14737632);
         var20 = "Allocated memory: " + var40 * 100L / var38 + "% (" + var40 / 1024L / 1024L + "MB)";
         this.func_73731_b(var8, var20, var6 - var8.func_78256_a(var20) - 2, 12, 14737632);
         var22 = MathHelper.func_76128_c(this.field_73839_d.field_71439_g.field_70165_t);
         var23 = MathHelper.func_76128_c(this.field_73839_d.field_71439_g.field_70163_u);
         int var24 = MathHelper.func_76128_c(this.field_73839_d.field_71439_g.field_70161_v);
         this.func_73731_b(var8, String.format("x: %.5f (%d) // c: %d (%d)", new Object[]{Double.valueOf(this.field_73839_d.field_71439_g.field_70165_t), Integer.valueOf(var22), Integer.valueOf(var22 >> 4), Integer.valueOf(var22 & 15)}), 2, 64, 14737632);
         this.func_73731_b(var8, String.format("y: %.3f (feet pos, %.3f eyes pos)", new Object[]{Double.valueOf(this.field_73839_d.field_71439_g.field_70121_D.field_72338_b), Double.valueOf(this.field_73839_d.field_71439_g.field_70163_u)}), 2, 72, 14737632);
         this.func_73731_b(var8, String.format("z: %.5f (%d) // c: %d (%d)", new Object[]{Double.valueOf(this.field_73839_d.field_71439_g.field_70161_v), Integer.valueOf(var24), Integer.valueOf(var24 >> 4), Integer.valueOf(var24 & 15)}), 2, 80, 14737632);
         int var25 = MathHelper.func_76128_c((double)(this.field_73839_d.field_71439_g.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3;
         this.func_73731_b(var8, "f: " + var25 + " (" + Direction.field_82373_c[var25] + ") / " + MathHelper.func_76142_g(this.field_73839_d.field_71439_g.field_70177_z), 2, 88, 14737632);
         if(this.field_73839_d.field_71441_e != null && this.field_73839_d.field_71441_e.func_72899_e(var22, var23, var24)) {
            Chunk var26 = this.field_73839_d.field_71441_e.func_72938_d(var22, var24);
            this.func_73731_b(var8, "lc: " + (var26.func_76625_h() + 15) + " b: " + var26.func_76591_a(var22 & 15, var24 & 15, this.field_73839_d.field_71441_e.func_72959_q()).field_76791_y + " bl: " + var26.func_76614_a(EnumSkyBlock.Block, var22 & 15, var23, var24 & 15) + " sl: " + var26.func_76614_a(EnumSkyBlock.Sky, var22 & 15, var23, var24 & 15) + " rl: " + var26.func_76629_c(var22 & 15, var23, var24 & 15, 0), 2, 96, 14737632);
         }

         this.func_73731_b(var8, String.format("ws: %.3f, fs: %.3f, g: %b, fl: %d", new Object[]{Float.valueOf(this.field_73839_d.field_71439_g.field_71075_bZ.func_75094_b()), Float.valueOf(this.field_73839_d.field_71439_g.field_71075_bZ.func_75093_a()), Boolean.valueOf(this.field_73839_d.field_71439_g.field_70122_E), Integer.valueOf(this.field_73839_d.field_71441_e.func_72976_f(var22, var24))}), 2, 104, 14737632);
         GL11.glPopMatrix();
         this.field_73839_d.field_71424_I.func_76319_b();
      }

      if(this.field_73845_h > 0) {
         this.field_73839_d.field_71424_I.func_76320_a("overlayMessage");
         var33 = (float)this.field_73845_h - p_73830_1_;
         var13 = (int)(var33 * 255.0F / 20.0F);
         if(var13 > 255) {
            var13 = 255;
         }

         if(var13 > 8) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(var6 / 2), (float)(var7 - 68), 0.0F);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            var14 = 16777215;
            if(this.field_73844_j) {
               var14 = Color.HSBtoRGB(var33 / 50.0F, 0.7F, 0.6F) & 16777215;
            }

            var8.func_78276_b(this.field_73838_g, -var8.func_78256_a(this.field_73838_g) / 2, -4, var14 + (var13 << 24 & -16777216));
            GL11.glDisable(3042);
            GL11.glPopMatrix();
         }

         this.field_73839_d.field_71424_I.func_76319_b();
      }

      ScoreObjective var43 = this.field_73839_d.field_71441_e.func_96441_U().func_96539_a(1);
      if(var43 != null) {
         this.func_96136_a(var43, var7, var6, var8);
      }

      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3008);
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, (float)(var7 - 48), 0.0F);
      this.field_73839_d.field_71424_I.func_76320_a("chat");
      this.field_73840_e.func_73762_a(this.field_73837_f);
      this.field_73839_d.field_71424_I.func_76319_b();
      GL11.glPopMatrix();
      var43 = this.field_73839_d.field_71441_e.func_96441_U().func_96539_a(0);
      if(this.field_73839_d.field_71474_y.field_74321_H.field_74513_e && (!this.field_73839_d.func_71387_A() || this.field_73839_d.field_71439_g.field_71174_a.field_72559_c.size() > 1 || var43 != null)) {
         this.field_73839_d.field_71424_I.func_76320_a("playerList");
         NetClientHandler var41 = this.field_73839_d.field_71439_g.field_71174_a;
         List var44 = var41.field_72559_c;
         var15 = var41.field_72556_d;
         var16 = var15;

         for(var17 = 1; var16 > 20; var16 = (var15 + var17 - 1) / var17) {
            ++var17;
         }

         int var45 = 300 / var17;
         if(var45 > 150) {
            var45 = 150;
         }

         int var19 = (var6 - var17 * var45) / 2;
         byte var47 = 10;
         func_73734_a(var19 - 1, var47 - 1, var19 + var45 * var17, var47 + 9 * var16, Integer.MIN_VALUE);

         for(var21 = 0; var21 < var15; ++var21) {
            var22 = var19 + var21 % var17 * var45;
            var23 = var47 + var21 / var17 * 9;
            func_73734_a(var22, var23, var22 + var45 - 1, var23 + 8, 553648127);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(3008);
            if(var21 < var44.size()) {
               GuiPlayerInfo var49 = (GuiPlayerInfo)var44.get(var21);
               ScorePlayerTeam var48 = this.field_73839_d.field_71441_e.func_96441_U().func_96509_i(var49.field_78831_a);
               String var52 = ScorePlayerTeam.func_96667_a(var48, var49.field_78831_a);
               var8.func_78261_a(var52, var22, var23, 16777215);
               if(var43 != null) {
                  int var27 = var22 + var8.func_78256_a(var52) + 5;
                  int var28 = var22 + var45 - 12 - 5;
                  if(var28 - var27 > 5) {
                     Score var29 = var43.func_96682_a().func_96529_a(var49.field_78831_a, var43);
                     String var30 = EnumChatFormatting.YELLOW + "" + var29.func_96652_c();
                     var8.func_78261_a(var30, var28 - var8.func_78256_a(var30), var23, 16777215);
                  }
               }

               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               this.field_73839_d.func_110434_K().func_110577_a(field_110324_m);
               byte var53 = 0;
               boolean var51 = false;
               byte var50;
               if(var49.field_78829_b < 0) {
                  var50 = 5;
               } else if(var49.field_78829_b < 150) {
                  var50 = 0;
               } else if(var49.field_78829_b < 300) {
                  var50 = 1;
               } else if(var49.field_78829_b < 600) {
                  var50 = 2;
               } else if(var49.field_78829_b < 1000) {
                  var50 = 3;
               } else {
                  var50 = 4;
               }

               this.field_73735_i += 100.0F;
               this.func_73729_b(var22 + var45 - 12, var23, 0 + var53 * 10, 176 + var50 * 8, 10, 8);
               this.field_73735_i -= 100.0F;
            }
         }
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(2896);
      GL11.glEnable(3008);
   }

   protected void func_96136_a(ScoreObjective p_96136_1_, int p_96136_2_, int p_96136_3_, FontRenderer p_96136_4_) {
      Scoreboard var5 = p_96136_1_.func_96682_a();
      Collection var6 = var5.func_96534_i(p_96136_1_);
      if(var6.size() <= 15) {
         int var7 = p_96136_4_.func_78256_a(p_96136_1_.func_96678_d());

         String var11;
         for(Iterator var8 = var6.iterator(); var8.hasNext(); var7 = Math.max(var7, p_96136_4_.func_78256_a(var11))) {
            Score var9 = (Score)var8.next();
            ScorePlayerTeam var10 = var5.func_96509_i(var9.func_96653_e());
            var11 = ScorePlayerTeam.func_96667_a(var10, var9.func_96653_e()) + ": " + EnumChatFormatting.RED + var9.func_96652_c();
         }

         int var22 = var6.size() * p_96136_4_.field_78288_b;
         int var23 = p_96136_2_ / 2 + var22 / 3;
         byte var25 = 3;
         int var24 = p_96136_3_ - var7 - var25;
         int var12 = 0;
         Iterator var13 = var6.iterator();

         while(var13.hasNext()) {
            Score var14 = (Score)var13.next();
            ++var12;
            ScorePlayerTeam var15 = var5.func_96509_i(var14.func_96653_e());
            String var16 = ScorePlayerTeam.func_96667_a(var15, var14.func_96653_e());
            String var17 = EnumChatFormatting.RED + "" + var14.func_96652_c();
            int var19 = var23 - var12 * p_96136_4_.field_78288_b;
            int var20 = p_96136_3_ - var25 + 2;
            func_73734_a(var24 - 2, var19, var20, var19 + p_96136_4_.field_78288_b, 1342177280);
            p_96136_4_.func_78276_b(var16, var24, var19, 553648127);
            p_96136_4_.func_78276_b(var17, var20 - p_96136_4_.func_78256_a(var17), var19, 553648127);
            if(var12 == var6.size()) {
               String var21 = p_96136_1_.func_96678_d();
               func_73734_a(var24 - 2, var19 - p_96136_4_.field_78288_b - 1, var20, var19 - 1, 1610612736);
               func_73734_a(var24 - 2, var19 - 1, var20, var19, 1342177280);
               p_96136_4_.func_78276_b(var21, var24 + var7 / 2 - p_96136_4_.func_78256_a(var21) / 2, var19 - p_96136_4_.field_78288_b, 553648127);
            }
         }

      }
   }

   protected void func_110327_a(int p_110327_1_, int p_110327_2_) {
      boolean var3 = this.field_73839_d.field_71439_g.field_70172_ad / 3 % 2 == 1;
      if(this.field_73839_d.field_71439_g.field_70172_ad < 10) {
         var3 = false;
      }

      int var4 = MathHelper.func_76123_f(this.field_73839_d.field_71439_g.func_110143_aJ());
      int var5 = MathHelper.func_76123_f(this.field_73839_d.field_71439_g.field_70735_aL);
      this.field_73842_c.setSeed((long)(this.field_73837_f * 312871));
      boolean var6 = false;
      FoodStats var7 = this.field_73839_d.field_71439_g.func_71024_bL();
      int var8 = var7.func_75116_a();
      int var9 = var7.func_75120_b();
      AttributeInstance var10 = this.field_73839_d.field_71439_g.func_110148_a(SharedMonsterAttributes.field_111267_a);
      int var11 = p_110327_1_ / 2 - 91;
      int var12 = p_110327_1_ / 2 + 91;
      int var13 = p_110327_2_ - 39;
      float var14 = (float)var10.func_111126_e();
      float var15 = this.field_73839_d.field_71439_g.func_110139_bj();
      int var16 = MathHelper.func_76123_f((var14 + var15) / 2.0F / 10.0F);
      int var17 = Math.max(10 - (var16 - 2), 3);
      int var18 = var13 - (var16 - 1) * var17 - 10;
      float var19 = var15;
      int var20 = this.field_73839_d.field_71439_g.func_70658_aO();
      int var21 = -1;
      if(this.field_73839_d.field_71439_g.func_70644_a(Potion.field_76428_l)) {
         var21 = this.field_73837_f % MathHelper.func_76123_f(var14 + 5.0F);
      }

      this.field_73839_d.field_71424_I.func_76320_a("armor");

      int var23;
      int var22;
      for(var22 = 0; var22 < 10; ++var22) {
         if(var20 > 0) {
            var23 = var11 + var22 * 8;
            if(var22 * 2 + 1 < var20) {
               this.func_73729_b(var23, var18, 34, 9, 9, 9);
            }

            if(var22 * 2 + 1 == var20) {
               this.func_73729_b(var23, var18, 25, 9, 9, 9);
            }

            if(var22 * 2 + 1 > var20) {
               this.func_73729_b(var23, var18, 16, 9, 9, 9);
            }
         }
      }

      this.field_73839_d.field_71424_I.func_76318_c("health");

      int var25;
      int var27;
      int var26;
      for(var22 = MathHelper.func_76123_f((var14 + var15) / 2.0F) - 1; var22 >= 0; --var22) {
         var23 = 16;
         if(this.field_73839_d.field_71439_g.func_70644_a(Potion.field_76436_u)) {
            var23 += 36;
         } else if(this.field_73839_d.field_71439_g.func_70644_a(Potion.field_82731_v)) {
            var23 += 72;
         }

         byte var24 = 0;
         if(var3) {
            var24 = 1;
         }

         var25 = MathHelper.func_76123_f((float)(var22 + 1) / 10.0F) - 1;
         var26 = var11 + var22 % 10 * 8;
         var27 = var13 - var25 * var17;
         if(var4 <= 4) {
            var27 += this.field_73842_c.nextInt(2);
         }

         if(var22 == var21) {
            var27 -= 2;
         }

         byte var28 = 0;
         if(this.field_73839_d.field_71441_e.func_72912_H().func_76093_s()) {
            var28 = 5;
         }

         this.func_73729_b(var26, var27, 16 + var24 * 9, 9 * var28, 9, 9);
         if(var3) {
            if(var22 * 2 + 1 < var5) {
               this.func_73729_b(var26, var27, var23 + 54, 9 * var28, 9, 9);
            }

            if(var22 * 2 + 1 == var5) {
               this.func_73729_b(var26, var27, var23 + 63, 9 * var28, 9, 9);
            }
         }

         if(var19 > 0.0F) {
            if(var19 == var15 && var15 % 2.0F == 1.0F) {
               this.func_73729_b(var26, var27, var23 + 153, 9 * var28, 9, 9);
            } else {
               this.func_73729_b(var26, var27, var23 + 144, 9 * var28, 9, 9);
            }

            var19 -= 2.0F;
         } else {
            if(var22 * 2 + 1 < var4) {
               this.func_73729_b(var26, var27, var23 + 36, 9 * var28, 9, 9);
            }

            if(var22 * 2 + 1 == var4) {
               this.func_73729_b(var26, var27, var23 + 45, 9 * var28, 9, 9);
            }
         }
      }

      Entity var34 = this.field_73839_d.field_71439_g.field_70154_o;
      int var35;
      if(var34 == null) {
         this.field_73839_d.field_71424_I.func_76318_c("food");

         for(var23 = 0; var23 < 10; ++var23) {
            var35 = var13;
            var25 = 16;
            byte var36 = 0;
            if(this.field_73839_d.field_71439_g.func_70644_a(Potion.field_76438_s)) {
               var25 += 36;
               var36 = 13;
            }

            if(this.field_73839_d.field_71439_g.func_71024_bL().func_75115_e() <= 0.0F && this.field_73837_f % (var8 * 3 + 1) == 0) {
               var35 = var13 + (this.field_73842_c.nextInt(3) - 1);
            }

            if(var6) {
               var36 = 1;
            }

            var27 = var12 - var23 * 8 - 9;
            this.func_73729_b(var27, var35, 16 + var36 * 9, 27, 9, 9);
            if(var6) {
               if(var23 * 2 + 1 < var9) {
                  this.func_73729_b(var27, var35, var25 + 54, 27, 9, 9);
               }

               if(var23 * 2 + 1 == var9) {
                  this.func_73729_b(var27, var35, var25 + 63, 27, 9, 9);
               }
            }

            if(var23 * 2 + 1 < var8) {
               this.func_73729_b(var27, var35, var25 + 36, 27, 9, 9);
            }

            if(var23 * 2 + 1 == var8) {
               this.func_73729_b(var27, var35, var25 + 45, 27, 9, 9);
            }
         }
      } else if(var34 instanceof EntityLivingBase) {
         this.field_73839_d.field_71424_I.func_76318_c("mountHealth");
         EntityLivingBase var38 = (EntityLivingBase)var34;
         var35 = (int)Math.ceil((double)var38.func_110143_aJ());
         float var37 = var38.func_110138_aP();
         var26 = (int)(var37 + 0.5F) / 2;
         if(var26 > 30) {
            var26 = 30;
         }

         var27 = var13;

         for(int var39 = 0; var26 > 0; var39 += 20) {
            int var29 = Math.min(var26, 10);
            var26 -= var29;

            for(int var30 = 0; var30 < var29; ++var30) {
               byte var31 = 52;
               byte var32 = 0;
               if(var6) {
                  var32 = 1;
               }

               int var33 = var12 - var30 * 8 - 9;
               this.func_73729_b(var33, var27, var31 + var32 * 9, 9, 9, 9);
               if(var30 * 2 + 1 + var39 < var35) {
                  this.func_73729_b(var33, var27, var31 + 36, 9, 9, 9);
               }

               if(var30 * 2 + 1 + var39 == var35) {
                  this.func_73729_b(var33, var27, var31 + 45, 9, 9, 9);
               }
            }

            var27 -= 10;
         }
      }

      this.field_73839_d.field_71424_I.func_76318_c("air");
      if(this.field_73839_d.field_71439_g.func_70055_a(Material.field_76244_g)) {
         var23 = this.field_73839_d.field_71439_g.func_70086_ai();
         var35 = MathHelper.func_76143_f((double)(var23 - 2) * 10.0D / 300.0D);
         var25 = MathHelper.func_76143_f((double)var23 * 10.0D / 300.0D) - var35;

         for(var26 = 0; var26 < var35 + var25; ++var26) {
            if(var26 < var35) {
               this.func_73729_b(var12 - var26 * 8 - 9, var18, 16, 18, 9, 9);
            } else {
               this.func_73729_b(var12 - var26 * 8 - 9, var18, 25, 18, 9, 9);
            }
         }
      }

      this.field_73839_d.field_71424_I.func_76319_b();
   }

   protected void func_73828_d() {
      if(BossStatus.field_82827_c != null && BossStatus.field_82826_b > 0) {
         --BossStatus.field_82826_b;
         FontRenderer var1 = this.field_73839_d.field_71466_p;
         ScaledResolution var2 = new ScaledResolution(this.field_73839_d.field_71474_y, this.field_73839_d.field_71443_c, this.field_73839_d.field_71440_d);
         int var3 = var2.func_78326_a();
         short var4 = 182;
         int var5 = var3 / 2 - var4 / 2;
         int var6 = (int)(BossStatus.field_82828_a * (float)(var4 + 1));
         byte var7 = 12;
         this.func_73729_b(var5, var7, 0, 74, var4, 5);
         this.func_73729_b(var5, var7, 0, 74, var4, 5);
         if(var6 > 0) {
            this.func_73729_b(var5, var7, 0, 79, var6, 5);
         }

         String var8 = BossStatus.field_82827_c;
         var1.func_78261_a(var8, var3 / 2 - var1.func_78256_a(var8) / 2, var7 - 10, 16777215);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_73839_d.func_110434_K().func_110577_a(field_110324_m);
      }
   }

   protected void func_73836_a(int p_73836_1_, int p_73836_2_) {
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(3008);
      this.field_73839_d.func_110434_K().func_110577_a(field_110328_d);
      Tessellator var3 = Tessellator.field_78398_a;
      var3.func_78382_b();
      var3.func_78374_a(0.0D, (double)p_73836_2_, -90.0D, 0.0D, 1.0D);
      var3.func_78374_a((double)p_73836_1_, (double)p_73836_2_, -90.0D, 1.0D, 1.0D);
      var3.func_78374_a((double)p_73836_1_, 0.0D, -90.0D, 1.0D, 0.0D);
      var3.func_78374_a(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
      var3.func_78381_a();
      GL11.glDepthMask(true);
      GL11.glEnable(2929);
      GL11.glEnable(3008);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   protected void func_73829_a(float p_73829_1_, int p_73829_2_, int p_73829_3_) {
      p_73829_1_ = 1.0F - p_73829_1_;
      if(p_73829_1_ < 0.0F) {
         p_73829_1_ = 0.0F;
      }

      if(p_73829_1_ > 1.0F) {
         p_73829_1_ = 1.0F;
      }

      this.field_73843_a = (float)((double)this.field_73843_a + (double)(p_73829_1_ - this.field_73843_a) * 0.01D);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glBlendFunc(0, 769);
      GL11.glColor4f(this.field_73843_a, this.field_73843_a, this.field_73843_a, 1.0F);
      this.field_73839_d.func_110434_K().func_110577_a(field_110329_b);
      Tessellator var4 = Tessellator.field_78398_a;
      var4.func_78382_b();
      var4.func_78374_a(0.0D, (double)p_73829_3_, -90.0D, 0.0D, 1.0D);
      var4.func_78374_a((double)p_73829_2_, (double)p_73829_3_, -90.0D, 1.0D, 1.0D);
      var4.func_78374_a((double)p_73829_2_, 0.0D, -90.0D, 1.0D, 0.0D);
      var4.func_78374_a(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
      var4.func_78381_a();
      GL11.glDepthMask(true);
      GL11.glEnable(2929);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glBlendFunc(770, 771);
   }

   protected void func_130015_b(float p_130015_1_, int p_130015_2_, int p_130015_3_) {
      if(p_130015_1_ < 1.0F) {
         p_130015_1_ *= p_130015_1_;
         p_130015_1_ *= p_130015_1_;
         p_130015_1_ = p_130015_1_ * 0.8F + 0.2F;
      }

      GL11.glDisable(3008);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, p_130015_1_);
      Icon var4 = Block.field_72015_be.func_71851_a(1);
      this.field_73839_d.func_110434_K().func_110577_a(TextureMap.field_110575_b);
      float var5 = var4.func_94209_e();
      float var6 = var4.func_94206_g();
      float var7 = var4.func_94212_f();
      float var8 = var4.func_94210_h();
      Tessellator var9 = Tessellator.field_78398_a;
      var9.func_78382_b();
      var9.func_78374_a(0.0D, (double)p_130015_3_, -90.0D, (double)var5, (double)var8);
      var9.func_78374_a((double)p_130015_2_, (double)p_130015_3_, -90.0D, (double)var7, (double)var8);
      var9.func_78374_a((double)p_130015_2_, 0.0D, -90.0D, (double)var7, (double)var6);
      var9.func_78374_a(0.0D, 0.0D, -90.0D, (double)var5, (double)var6);
      var9.func_78381_a();
      GL11.glDepthMask(true);
      GL11.glEnable(2929);
      GL11.glEnable(3008);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   protected void func_73832_a(int p_73832_1_, int p_73832_2_, int p_73832_3_, float p_73832_4_) {
      ItemStack var5 = this.field_73839_d.field_71439_g.field_71071_by.field_70462_a[p_73832_1_];
      if(var5 != null) {
         float var6 = (float)var5.field_77992_b - p_73832_4_;
         if(var6 > 0.0F) {
            GL11.glPushMatrix();
            float var7 = 1.0F + var6 / 5.0F;
            GL11.glTranslatef((float)(p_73832_2_ + 8), (float)(p_73832_3_ + 12), 0.0F);
            GL11.glScalef(1.0F / var7, (var7 + 1.0F) / 2.0F, 1.0F);
            GL11.glTranslatef((float)(-(p_73832_2_ + 8)), (float)(-(p_73832_3_ + 12)), 0.0F);
         }

         field_73841_b.func_82406_b(this.field_73839_d.field_71466_p, this.field_73839_d.func_110434_K(), var5, p_73832_2_, p_73832_3_);
         if(var6 > 0.0F) {
            GL11.glPopMatrix();
         }

         field_73841_b.func_77021_b(this.field_73839_d.field_71466_p, this.field_73839_d.func_110434_K(), var5, p_73832_2_, p_73832_3_);
      }
   }

   public void func_73831_a() {
      if(this.field_73845_h > 0) {
         --this.field_73845_h;
      }

      ++this.field_73837_f;
      if(this.field_73839_d.field_71439_g != null) {
         ItemStack var1 = this.field_73839_d.field_71439_g.field_71071_by.func_70448_g();
         if(var1 == null) {
            this.field_92017_k = 0;
         } else if(this.field_92016_l != null && var1.field_77993_c == this.field_92016_l.field_77993_c && ItemStack.func_77970_a(var1, this.field_92016_l) && (var1.func_77984_f() || var1.func_77960_j() == this.field_92016_l.func_77960_j())) {
            if(this.field_92017_k > 0) {
               --this.field_92017_k;
            }
         } else {
            this.field_92017_k = 40;
         }

         this.field_92016_l = var1;
      }

   }

   public void func_73833_a(String p_73833_1_) {
      this.func_110326_a("Now playing: " + p_73833_1_, true);
   }

   public void func_110326_a(String p_110326_1_, boolean p_110326_2_) {
      this.field_73838_g = p_110326_1_;
      this.field_73845_h = 60;
      this.field_73844_j = p_110326_2_;
   }

   public GuiNewChat func_73827_b() {
      return this.field_73840_e;
   }

   public int func_73834_c() {
      return this.field_73837_f;
   }

}
