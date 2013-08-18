package net.minecraft.client.resources.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.lang.reflect.Type;
import net.minecraft.client.resources.data.BaseMetadataSectionSerializer;
import net.minecraft.client.resources.data.TextureMetadataSection;

@SideOnly(Side.CLIENT)
public class TextureMetadataSectionSerializer extends BaseMetadataSectionSerializer {

   public TextureMetadataSection func_110494_a(JsonElement p_110494_1_, Type p_110494_2_, JsonDeserializationContext p_110494_3_) {
      JsonObject var4 = p_110494_1_.getAsJsonObject();
      boolean var5 = this.func_110484_a(var4.get("blur"), "blur", Boolean.valueOf(false));
      boolean var6 = this.func_110484_a(var4.get("clamp"), "clamp", Boolean.valueOf(false));
      return new TextureMetadataSection(var5, var6);
   }

   public String func_110483_a() {
      return "texture";
   }

   // $FF: synthetic method
   public Object deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) {
      return this.func_110494_a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
   }
}
