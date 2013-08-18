package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.renderer.texture.Stitcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureClock;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.texture.TickableTextureObject;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class TextureMap extends AbstractTexture implements TickableTextureObject, IconRegister {

   public static final ResourceLocation field_110575_b = new ResourceLocation("textures/atlas/blocks.png");
   public static final ResourceLocation field_110576_c = new ResourceLocation("textures/atlas/items.png");
   private final List field_94258_i = Lists.newArrayList();
   private final Map field_110574_e = Maps.newHashMap();
   private final Map field_94252_e = Maps.newHashMap();
   public final int field_94255_a;
   public final String field_94254_c;
   private final TextureAtlasSprite field_94249_f = new TextureAtlasSprite("missingno");


   public TextureMap(int p_i1281_1_, String p_i1281_2_) {
      this.field_94255_a = p_i1281_1_;
      this.field_94254_c = p_i1281_2_;
      this.func_110573_f();
   }

   private void func_110569_e() {
      this.field_94249_f.func_110968_a(Lists.newArrayList(new int[][]{TextureUtil.field_110999_b}));
      this.field_94249_f.func_110966_b(16);
      this.field_94249_f.func_110969_c(16);
   }

   public void func_110551_a(ResourceManager p_110551_1_) throws IOException {
      this.func_110569_e();
      this.func_110571_b(p_110551_1_);
   }

   public void func_110571_b(ResourceManager p_110571_1_) {
      int var2 = Minecraft.func_71369_N();
      Stitcher var3 = new Stitcher(var2, var2, true);
      this.field_94252_e.clear();
      this.field_94258_i.clear();
      Iterator var4 = this.field_110574_e.entrySet().iterator();

      while(var4.hasNext()) {
         Entry var5 = (Entry)var4.next();
         ResourceLocation var6 = new ResourceLocation((String)var5.getKey());
         TextureAtlasSprite var7 = (TextureAtlasSprite)var5.getValue();
         ResourceLocation var8 = new ResourceLocation(var6.func_110624_b(), String.format("%s/%s%s", new Object[]{this.field_94254_c, var6.func_110623_a(), ".png"}));

         try {
            var7.func_130100_a(p_110571_1_.func_110536_a(var8));
         } catch (RuntimeException var13) {
            Minecraft.func_71410_x().func_98033_al().func_98232_c(String.format("Unable to parse animation metadata from %s: %s", new Object[]{var8, var13.getMessage()}));
            continue;
         } catch (IOException var14) {
            Minecraft.func_71410_x().func_98033_al().func_98232_c("Using missing texture, unable to load: " + var8);
            continue;
         }

         var3.func_110934_a(var7);
      }

      var3.func_110934_a(this.field_94249_f);

      try {
         var3.func_94305_f();
      } catch (StitcherException var12) {
         throw var12;
      }

      TextureUtil.func_110991_a(this.func_110552_b(), var3.func_110935_a(), var3.func_110936_b());
      HashMap var15 = Maps.newHashMap(this.field_110574_e);
      Iterator var16 = var3.func_94309_g().iterator();

      TextureAtlasSprite var17;
      while(var16.hasNext()) {
         var17 = (TextureAtlasSprite)var16.next();
         String var18 = var17.func_94215_i();
         var15.remove(var18);
         this.field_94252_e.put(var18, var17);

         try {
            TextureUtil.func_110998_a(var17.func_110965_a(0), var17.func_94211_a(), var17.func_94216_b(), var17.func_130010_a(), var17.func_110967_i(), false, false);
         } catch (Throwable var11) {
            CrashReport var9 = CrashReport.func_85055_a(var11, "Stitching texture atlas");
            CrashReportCategory var10 = var9.func_85058_a("Texture being stitched together");
            var10.func_71507_a("Atlas path", this.field_94254_c);
            var10.func_71507_a("Sprite", var17);
            throw new ReportedException(var9);
         }

         if(var17.func_130098_m()) {
            this.field_94258_i.add(var17);
         } else {
            var17.func_130103_l();
         }
      }

      var16 = var15.values().iterator();

      while(var16.hasNext()) {
         var17 = (TextureAtlasSprite)var16.next();
         var17.func_94217_a(this.field_94249_f);
      }

   }

   private void func_110573_f() {
      this.field_110574_e.clear();
      int var2;
      int var3;
      if(this.field_94255_a == 0) {
         Block[] var1 = Block.field_71973_m;
         var2 = var1.length;

         for(var3 = 0; var3 < var2; ++var3) {
            Block var4 = var1[var3];
            if(var4 != null) {
               var4.func_94332_a(this);
            }
         }

         Minecraft.func_71410_x().field_71438_f.func_94140_a(this);
         RenderManager.field_78727_a.func_94178_a(this);
      }

      Item[] var5 = Item.field_77698_e;
      var2 = var5.length;

      for(var3 = 0; var3 < var2; ++var3) {
         Item var6 = var5[var3];
         if(var6 != null && var6.func_94901_k() == this.field_94255_a) {
            var6.func_94581_a(this);
         }
      }

   }

   public TextureAtlasSprite func_110572_b(String p_110572_1_) {
      TextureAtlasSprite var2 = (TextureAtlasSprite)this.field_94252_e.get(p_110572_1_);
      if(var2 == null) {
         var2 = this.field_94249_f;
      }

      return var2;
   }

   public void func_94248_c() {
      TextureUtil.func_94277_a(this.func_110552_b());
      Iterator var1 = this.field_94258_i.iterator();

      while(var1.hasNext()) {
         TextureAtlasSprite var2 = (TextureAtlasSprite)var1.next();
         var2.func_94219_l();
      }

   }

   public Icon func_94245_a(String p_94245_1_) {
      if(p_94245_1_ == null) {
         (new RuntimeException("Don\'t register null!")).printStackTrace();
      }

      Object var2 = (TextureAtlasSprite)this.field_110574_e.get(p_94245_1_);
      if(var2 == null) {
         if(this.field_94255_a == 1) {
            if("clock".equals(p_94245_1_)) {
               var2 = new TextureClock(p_94245_1_);
            } else if("compass".equals(p_94245_1_)) {
               var2 = new TextureCompass(p_94245_1_);
            } else {
               var2 = new TextureAtlasSprite(p_94245_1_);
            }
         } else {
            var2 = new TextureAtlasSprite(p_94245_1_);
         }

         this.field_110574_e.put(p_94245_1_, var2);
      }

      return (Icon)var2;
   }

   public int func_130086_a() {
      return this.field_94255_a;
   }

   public void func_110550_d() {
      this.func_94248_c();
   }

}
