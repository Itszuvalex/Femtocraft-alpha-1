package net.minecraft.client.resources;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import net.minecraft.client.resources.Resource;
import net.minecraft.client.resources.data.MetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

@SideOnly(Side.CLIENT)
public class SimpleResource implements Resource {

   private final Map field_110535_a = Maps.newHashMap();
   private final ResourceLocation field_110533_b;
   private final InputStream field_110534_c;
   private final InputStream field_110531_d;
   private final MetadataSerializer field_110532_e;
   private boolean field_110529_f;
   private JsonObject field_110530_g;


   public SimpleResource(ResourceLocation p_i1300_1_, InputStream p_i1300_2_, InputStream p_i1300_3_, MetadataSerializer p_i1300_4_) {
      this.field_110533_b = p_i1300_1_;
      this.field_110534_c = p_i1300_2_;
      this.field_110531_d = p_i1300_3_;
      this.field_110532_e = p_i1300_4_;
   }

   public InputStream func_110527_b() {
      return this.field_110534_c;
   }

   public boolean func_110528_c() {
      return this.field_110531_d != null;
   }

   public MetadataSection func_110526_a(String p_110526_1_) {
      if(!this.func_110528_c()) {
         return null;
      } else {
         if(this.field_110530_g == null && !this.field_110529_f) {
            this.field_110529_f = true;
            BufferedReader var2 = null;

            try {
               var2 = new BufferedReader(new InputStreamReader(this.field_110531_d));
               this.field_110530_g = (new JsonParser()).parse(var2).getAsJsonObject();
            } finally {
               IOUtils.closeQuietly(var2);
            }
         }

         MetadataSection var6 = (MetadataSection)this.field_110535_a.get(p_110526_1_);
         if(var6 == null) {
            var6 = this.field_110532_e.func_110503_a(p_110526_1_, this.field_110530_g);
         }

         return var6;
      }
   }

   public boolean equals(Object p_equals_1_) {
      if(this == p_equals_1_) {
         return true;
      } else if(p_equals_1_ instanceof SimpleResource) {
         SimpleResource var2 = (SimpleResource)p_equals_1_;
         return this.field_110533_b != null?this.field_110533_b.equals(var2.field_110533_b):var2.field_110533_b == null;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.field_110533_b == null?0:this.field_110533_b.hashCode();
   }
}
