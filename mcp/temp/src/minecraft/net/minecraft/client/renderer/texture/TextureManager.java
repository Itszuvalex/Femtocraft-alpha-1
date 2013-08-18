package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManagerINNER1;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.client.renderer.texture.TickableTextureObject;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.client.resources.ResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class TextureManager implements Tickable, ResourceManagerReloadListener {

   private final Map field_110585_a = Maps.newHashMap();
   private final Map field_130089_b = Maps.newHashMap();
   private final List field_110583_b = Lists.newArrayList();
   private final Map field_110584_c = Maps.newHashMap();
   private ResourceManager field_110582_d;


   public TextureManager(ResourceManager p_i1284_1_) {
      this.field_110582_d = p_i1284_1_;
   }

   public void func_110577_a(ResourceLocation p_110577_1_) {
      Object var2 = (TextureObject)this.field_110585_a.get(p_110577_1_);
      if(var2 == null) {
         var2 = new SimpleTexture(p_110577_1_);
         this.func_110579_a(p_110577_1_, (TextureObject)var2);
      }

      TextureUtil.func_94277_a(((TextureObject)var2).func_110552_b());
   }

   public ResourceLocation func_130087_a(int p_130087_1_) {
      return (ResourceLocation)this.field_130089_b.get(Integer.valueOf(p_130087_1_));
   }

   public boolean func_130088_a(ResourceLocation p_130088_1_, TextureMap p_130088_2_) {
      if(this.func_110580_a(p_130088_1_, p_130088_2_)) {
         this.field_130089_b.put(Integer.valueOf(p_130088_2_.func_130086_a()), p_130088_1_);
         return true;
      } else {
         return false;
      }
   }

   public boolean func_110580_a(ResourceLocation p_110580_1_, TickableTextureObject p_110580_2_) {
      if(this.func_110579_a(p_110580_1_, p_110580_2_)) {
         this.field_110583_b.add(p_110580_2_);
         return true;
      } else {
         return false;
      }
   }

   public boolean func_110579_a(ResourceLocation p_110579_1_, TextureObject p_110579_2_) {
      boolean var3 = true;

      try {
         ((TextureObject)p_110579_2_).func_110551_a(this.field_110582_d);
      } catch (IOException var8) {
         Minecraft.func_71410_x().func_98033_al().func_98235_b("Failed to load texture: " + p_110579_1_, var8);
         p_110579_2_ = TextureUtil.field_111001_a;
         this.field_110585_a.put(p_110579_1_, p_110579_2_);
         var3 = false;
      } catch (Throwable var9) {
         CrashReport var5 = CrashReport.func_85055_a(var9, "Registering texture");
         CrashReportCategory var6 = var5.func_85058_a("Resource location being registered");
         var6.func_71507_a("Resource location", p_110579_1_);
         var6.func_71500_a("Texture object class", new TextureManagerINNER1(this, (TextureObject)p_110579_2_));
         throw new ReportedException(var5);
      }

      this.field_110585_a.put(p_110579_1_, p_110579_2_);
      return var3;
   }

   public TextureObject func_110581_b(ResourceLocation p_110581_1_) {
      return (TextureObject)this.field_110585_a.get(p_110581_1_);
   }

   public ResourceLocation func_110578_a(String p_110578_1_, DynamicTexture p_110578_2_) {
      Integer var3 = (Integer)this.field_110584_c.get(p_110578_1_);
      if(var3 == null) {
         var3 = Integer.valueOf(1);
      } else {
         var3 = Integer.valueOf(var3.intValue() + 1);
      }

      this.field_110584_c.put(p_110578_1_, var3);
      ResourceLocation var4 = new ResourceLocation(String.format("dynamic/%s_%d", new Object[]{p_110578_1_, var3}));
      this.func_110579_a(var4, p_110578_2_);
      return var4;
   }

   public void func_110550_d() {
      Iterator var1 = this.field_110583_b.iterator();

      while(var1.hasNext()) {
         Tickable var2 = (Tickable)var1.next();
         var2.func_110550_d();
      }

   }

   public void func_110549_a(ResourceManager p_110549_1_) {
      Iterator var2 = this.field_110585_a.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         this.func_110579_a((ResourceLocation)var3.getKey(), (TextureObject)var3.getValue());
      }

   }
}
