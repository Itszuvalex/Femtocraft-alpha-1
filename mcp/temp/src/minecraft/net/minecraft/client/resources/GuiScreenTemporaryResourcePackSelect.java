package net.minecraft.client.resources;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSmallButton;
import net.minecraft.client.resources.GuiScreenTemporaryResourcePackSelectSelectionList;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.EnumOS;
import net.minecraft.util.Util;
import org.lwjgl.Sys;

@SideOnly(Side.CLIENT)
public class GuiScreenTemporaryResourcePackSelect extends GuiScreen {

   protected GuiScreen field_110347_a;
   private int field_73965_b = -1;
   private GuiScreenTemporaryResourcePackSelectSelectionList field_110346_c;
   private GameSettings field_96146_n;


   public GuiScreenTemporaryResourcePackSelect(GuiScreen p_i1302_1_, GameSettings p_i1302_2_) {
      this.field_110347_a = p_i1302_1_;
      this.field_96146_n = p_i1302_2_;
   }

   public void func_73866_w_() {
      this.field_73887_h.add(new GuiSmallButton(5, this.field_73880_f / 2 - 154, this.field_73881_g - 48, I18n.func_135053_a("resourcePack.openFolder")));
      this.field_73887_h.add(new GuiSmallButton(6, this.field_73880_f / 2 + 4, this.field_73881_g - 48, I18n.func_135053_a("gui.done")));
      this.field_110346_c = new GuiScreenTemporaryResourcePackSelectSelectionList(this, this.field_73882_e.func_110438_M());
      this.field_110346_c.func_77220_a(7, 8);
   }

   protected void func_73875_a(GuiButton p_73875_1_) {
      if(p_73875_1_.field_73742_g) {
         if(p_73875_1_.field_73741_f == 5) {
            File var2 = GuiScreenTemporaryResourcePackSelectSelectionList.func_110510_a(this.field_110346_c).func_110612_e();
            String var3 = var2.getAbsolutePath();
            if(Util.func_110647_a() == EnumOS.MACOS) {
               try {
                  this.field_73882_e.func_98033_al().func_98233_a(var3);
                  Runtime.getRuntime().exec(new String[]{"/usr/bin/open", var3});
                  return;
               } catch (IOException var9) {
                  var9.printStackTrace();
               }
            } else if(Util.func_110647_a() == EnumOS.WINDOWS) {
               String var4 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[]{var3});

               try {
                  Runtime.getRuntime().exec(var4);
                  return;
               } catch (IOException var8) {
                  var8.printStackTrace();
               }
            }

            boolean var10 = false;

            try {
               Class var5 = Class.forName("java.awt.Desktop");
               Object var6 = var5.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
               var5.getMethod("browse", new Class[]{URI.class}).invoke(var6, new Object[]{var2.toURI()});
            } catch (Throwable var7) {
               var7.printStackTrace();
               var10 = true;
            }

            if(var10) {
               this.field_73882_e.func_98033_al().func_98233_a("Opening via system class!");
               Sys.openURL("file://" + var3);
            }
         } else if(p_73875_1_.field_73741_f == 6) {
            this.field_73882_e.func_71373_a(this.field_110347_a);
         } else {
            this.field_110346_c.func_77219_a(p_73875_1_);
         }

      }
   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
   }

   protected void func_73879_b(int p_73879_1_, int p_73879_2_, int p_73879_3_) {
      super.func_73879_b(p_73879_1_, p_73879_2_, p_73879_3_);
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.field_110346_c.func_77211_a(p_73863_1_, p_73863_2_, p_73863_3_);
      if(this.field_73965_b <= 0) {
         GuiScreenTemporaryResourcePackSelectSelectionList.func_110510_a(this.field_110346_c).func_110611_a();
         this.field_73965_b = 20;
      }

      this.func_73732_a(this.field_73886_k, I18n.func_135053_a("resourcePack.title"), this.field_73880_f / 2, 16, 16777215);
      this.func_73732_a(this.field_73886_k, I18n.func_135053_a("resourcePack.folderInfo"), this.field_73880_f / 2 - 77, this.field_73881_g - 26, 8421504);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   public void func_73876_c() {
      super.func_73876_c();
      --this.field_73965_b;
   }

   // $FF: synthetic method
   static Minecraft func_110344_a(GuiScreenTemporaryResourcePackSelect p_110344_0_) {
      return p_110344_0_.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft func_110341_b(GuiScreenTemporaryResourcePackSelect p_110341_0_) {
      return p_110341_0_.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft func_110339_c(GuiScreenTemporaryResourcePackSelect p_110339_0_) {
      return p_110339_0_.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft func_110345_d(GuiScreenTemporaryResourcePackSelect p_110345_0_) {
      return p_110345_0_.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft func_110334_e(GuiScreenTemporaryResourcePackSelect p_110334_0_) {
      return p_110334_0_.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft func_110340_f(GuiScreenTemporaryResourcePackSelect p_110340_0_) {
      return p_110340_0_.field_73882_e;
   }

   // $FF: synthetic method
   static FontRenderer func_130017_g(GuiScreenTemporaryResourcePackSelect p_130017_0_) {
      return p_130017_0_.field_73886_k;
   }

   // $FF: synthetic method
   static FontRenderer func_130016_h(GuiScreenTemporaryResourcePackSelect p_130016_0_) {
      return p_130016_0_.field_73886_k;
   }

   // $FF: synthetic method
   static FontRenderer func_110337_i(GuiScreenTemporaryResourcePackSelect p_110337_0_) {
      return p_110337_0_.field_73886_k;
   }

   // $FF: synthetic method
   static FontRenderer func_110335_j(GuiScreenTemporaryResourcePackSelect p_110335_0_) {
      return p_110335_0_.field_73886_k;
   }

   // $FF: synthetic method
   static FontRenderer func_110338_k(GuiScreenTemporaryResourcePackSelect p_110338_0_) {
      return p_110338_0_.field_73886_k;
   }
}
