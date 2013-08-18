package net.minecraft.client.resources.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.lang.reflect.Type;
import net.minecraft.client.resources.data.BaseMetadataSectionSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;

@SideOnly(Side.CLIENT)
public class PackMetadataSectionSerializer extends BaseMetadataSectionSerializer implements JsonSerializer {

   public PackMetadataSection func_110489_a(JsonElement p_110489_1_, Type p_110489_2_, JsonDeserializationContext p_110489_3_) {
      JsonObject var4 = p_110489_1_.getAsJsonObject();
      String var5 = this.func_110486_a(var4.get("description"), "description", (String)null, 1, Integer.MAX_VALUE);
      int var6 = this.func_110485_a(var4.get("pack_format"), "pack_format", (Integer)null, 1, Integer.MAX_VALUE);
      return new PackMetadataSection(var5, var6);
   }

   public JsonElement func_110488_a(PackMetadataSection p_110488_1_, Type p_110488_2_, JsonSerializationContext p_110488_3_) {
      JsonObject var4 = new JsonObject();
      var4.addProperty("pack_format", Integer.valueOf(p_110488_1_.func_110462_b()));
      var4.addProperty("description", p_110488_1_.func_110461_a());
      return var4;
   }

   public String func_110483_a() {
      return "pack";
   }

   // $FF: synthetic method
   public Object deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) {
      return this.func_110489_a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
   }

   // $FF: synthetic method
   public JsonElement serialize(Object p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
      return this.func_110488_a((PackMetadataSection)p_serialize_1_, p_serialize_2_, p_serialize_3_);
   }
}
