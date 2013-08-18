package net.minecraft.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.logging.ILogAgent;
import net.minecraft.server.MinecraftServer;

public class HttpUtil {

   public static String func_76179_a(Map p_76179_0_) {
      StringBuilder var1 = new StringBuilder();
      Iterator var2 = p_76179_0_.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         if(var1.length() > 0) {
            var1.append('&');
         }

         try {
            var1.append(URLEncoder.encode((String)var3.getKey(), "UTF-8"));
         } catch (UnsupportedEncodingException var6) {
            var6.printStackTrace();
         }

         if(var3.getValue() != null) {
            var1.append('=');

            try {
               var1.append(URLEncoder.encode(var3.getValue().toString(), "UTF-8"));
            } catch (UnsupportedEncodingException var5) {
               var5.printStackTrace();
            }
         }
      }

      return var1.toString();
   }

   public static String func_76183_a(ILogAgent p_76183_0_, URL p_76183_1_, Map p_76183_2_, boolean p_76183_3_) {
      return func_76180_a(p_76183_0_, p_76183_1_, func_76179_a(p_76183_2_), p_76183_3_);
   }

   private static String func_76180_a(ILogAgent p_76180_0_, URL p_76180_1_, String p_76180_2_, boolean p_76180_3_) {
      try {
         Proxy var4 = MinecraftServer.func_71276_C() == null?null:MinecraftServer.func_71276_C().func_110454_ao();
         if(var4 == null) {
            var4 = Proxy.NO_PROXY;
         }

         HttpURLConnection var5 = (HttpURLConnection)p_76180_1_.openConnection(var4);
         var5.setRequestMethod("POST");
         var5.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
         var5.setRequestProperty("Content-Length", "" + p_76180_2_.getBytes().length);
         var5.setRequestProperty("Content-Language", "en-US");
         var5.setUseCaches(false);
         var5.setDoInput(true);
         var5.setDoOutput(true);
         DataOutputStream var6 = new DataOutputStream(var5.getOutputStream());
         var6.writeBytes(p_76180_2_);
         var6.flush();
         var6.close();
         BufferedReader var7 = new BufferedReader(new InputStreamReader(var5.getInputStream()));
         StringBuffer var9 = new StringBuffer();

         String var8;
         while((var8 = var7.readLine()) != null) {
            var9.append(var8);
            var9.append('\r');
         }

         var7.close();
         return var9.toString();
      } catch (Exception var10) {
         if(!p_76180_3_) {
            if(p_76180_0_ != null) {
               p_76180_0_.func_98234_c("Could not post to " + p_76180_1_, var10);
            } else {
               Logger.getAnonymousLogger().log(Level.SEVERE, "Could not post to " + p_76180_1_, var10);
            }
         }

         return "";
      }
   }

   @SideOnly(Side.CLIENT)
   public static int func_76181_a() throws IOException {
      ServerSocket var0 = null;
      boolean var1 = true;

      int var10;
      try {
         var0 = new ServerSocket(0);
         var10 = var0.getLocalPort();
      } finally {
         try {
            if(var0 != null) {
               var0.close();
            }
         } catch (IOException var8) {
            ;
         }

      }

      return var10;
   }
}
