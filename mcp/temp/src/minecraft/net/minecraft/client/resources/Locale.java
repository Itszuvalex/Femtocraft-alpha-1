package net.minecraft.client.resources;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.client.resources.Resource;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

@SideOnly(Side.CLIENT)
public class Locale {

   private static final Splitter field_135030_b = Splitter.on('=').limit(2);
   private static final Pattern field_135031_c = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
   Map field_135032_a = Maps.newHashMap();
   private boolean field_135029_d;


   public synchronized void func_135022_a(ResourceManager p_135022_1_, List p_135022_2_) {
      this.field_135032_a.clear();
      Iterator var3 = p_135022_2_.iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         String var5 = String.format("lang/%s.lang", new Object[]{var4});
         Iterator var6 = p_135022_1_.func_135055_a().iterator();

         while(var6.hasNext()) {
            String var7 = (String)var6.next();

            try {
               this.func_135028_a(p_135022_1_.func_135056_b(new ResourceLocation(var7, var5)));
            } catch (IOException var9) {
               ;
            }
         }
      }

      this.func_135024_b();
   }

   public boolean func_135025_a() {
      return this.field_135029_d;
   }

   private void func_135024_b() {
      this.field_135029_d = false;
      Iterator var1 = this.field_135032_a.values().iterator();

      while(var1.hasNext()) {
         String var2 = (String)var1.next();

         for(int var3 = 0; var3 < var2.length(); ++var3) {
            if(var2.charAt(var3) >= 256) {
               this.field_135029_d = true;
               break;
            }
         }
      }

   }

   private void func_135028_a(List p_135028_1_) throws IOException {
      Iterator var2 = p_135028_1_.iterator();

      while(var2.hasNext()) {
         Resource var3 = (Resource)var2.next();
         this.func_135021_a(var3.func_110527_b());
      }

   }

   private void func_135021_a(InputStream p_135021_1_) throws IOException {
      Iterator var2 = IOUtils.readLines(p_135021_1_, Charsets.UTF_8).iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         if(!var3.isEmpty() && var3.charAt(0) != 35) {
            String[] var4 = (String[])Iterables.toArray(field_135030_b.split(var3), String.class);
            if(var4 != null && var4.length == 2) {
               String var5 = var4[0];
               String var6 = field_135031_c.matcher(var4[1]).replaceAll("%$1s");
               this.field_135032_a.put(var5, var6);
            }
         }
      }

   }

   private String func_135026_c(String p_135026_1_) {
      String var2 = (String)this.field_135032_a.get(p_135026_1_);
      return var2 == null?p_135026_1_:var2;
   }

   public String func_135027_a(String p_135027_1_) {
      return this.func_135026_c(p_135027_1_);
   }

   public String func_135023_a(String p_135023_1_, Object[] p_135023_2_) {
      String var3 = this.func_135026_c(p_135023_1_);

      try {
         return String.format(var3, p_135023_2_);
      } catch (IllegalFormatException var5) {
         return "Format error: " + var3;
      }
   }

}
