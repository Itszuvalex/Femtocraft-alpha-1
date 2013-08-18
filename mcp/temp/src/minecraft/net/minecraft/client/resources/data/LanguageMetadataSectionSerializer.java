package net.minecraft.client.resources.data;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.data.BaseMetadataSectionSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;

@SideOnly(Side.CLIENT)
public class LanguageMetadataSectionSerializer extends BaseMetadataSectionSerializer {

   public LanguageMetadataSection func_135020_a(JsonElement p_135020_1_, Type p_135020_2_, JsonDeserializationContext p_135020_3_) {
      JsonObject var4 = p_135020_1_.getAsJsonObject();
      HashSet var5 = Sets.newHashSet();
      Iterator var6 = var4.entrySet().iterator();

      String var8;
      String var11;
      String var12;
      boolean var13;
      do {
         if(!var6.hasNext()) {
            return new LanguageMetadataSection(var5);
         }

         Entry var7 = (Entry)var6.next();
         var8 = (String)var7.getKey();
         JsonElement var9 = (JsonElement)var7.getValue();
         if(!var9.isJsonObject()) {
            throw new JsonParseException("Invalid language->\'" + var8 + "\': expected object, was " + var9);
         }

         JsonObject var10 = var9.getAsJsonObject();
         var11 = this.func_110486_a(var10.get("region"), "region", "", 0, Integer.MAX_VALUE);
         var12 = this.func_110486_a(var10.get("name"), "name", "", 0, Integer.MAX_VALUE);
         var13 = this.func_110484_a(var10.get("bidirectional"), "bidirectional", Boolean.valueOf(false));
         if(var11.isEmpty()) {
            throw new JsonParseException("Invalid language->\'" + var8 + "\'->region: empty value");
         }

         if(var12.isEmpty()) {
            throw new JsonParseException("Invalid language->\'" + var8 + "\'->name: empty value");
         }
      } while(var5.add(new Language(var8, var11, var12, var13)));

      throw new JsonParseException("Duplicate language->\'" + var8 + "\' defined");
   }

   public String func_110483_a() {
      return "language";
   }

   // $FF: synthetic method
   public Object deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) {
      return this.func_135020_a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
   }
}
