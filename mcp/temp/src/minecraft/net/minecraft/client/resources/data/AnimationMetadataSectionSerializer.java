package net.minecraft.client.resources.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.lang.reflect.Type;
import java.util.ArrayList;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.BaseMetadataSectionSerializer;

@SideOnly(Side.CLIENT)
public class AnimationMetadataSectionSerializer extends BaseMetadataSectionSerializer implements JsonSerializer {

   public AnimationMetadataSection func_110493_a(JsonElement p_110493_1_, Type p_110493_2_, JsonDeserializationContext p_110493_3_) {
      ArrayList var4 = Lists.newArrayList();
      JsonObject var5 = (JsonObject)p_110493_1_;
      int var6 = this.func_110485_a(var5.get("frametime"), "frametime", Integer.valueOf(1), 1, Integer.MAX_VALUE);
      int var8;
      if(var5.has("frames")) {
         try {
            JsonArray var7 = var5.getAsJsonArray("frames");

            for(var8 = 0; var8 < var7.size(); ++var8) {
               JsonElement var9 = var7.get(var8);
               AnimationFrame var10 = this.func_110492_a(var8, var9);
               if(var10 != null) {
                  var4.add(var10);
               }
            }
         } catch (ClassCastException var11) {
            throw new JsonParseException("Invalid animation->frames: expected array, was " + var5.get("frames"), var11);
         }
      }

      int var12 = this.func_110485_a(var5.get("width"), "width", Integer.valueOf(-1), 1, Integer.MAX_VALUE);
      var8 = this.func_110485_a(var5.get("height"), "height", Integer.valueOf(-1), 1, Integer.MAX_VALUE);
      return new AnimationMetadataSection(var4, var12, var8, var6);
   }

   private AnimationFrame func_110492_a(int p_110492_1_, JsonElement p_110492_2_) {
      if(p_110492_2_.isJsonPrimitive()) {
         try {
            return new AnimationFrame(p_110492_2_.getAsInt());
         } catch (NumberFormatException var6) {
            throw new JsonParseException("Invalid animation->frames->" + p_110492_1_ + ": expected number, was " + p_110492_2_, var6);
         }
      } else if(p_110492_2_.isJsonObject()) {
         JsonObject var3 = p_110492_2_.getAsJsonObject();
         int var4 = this.func_110485_a(var3.get("time"), "frames->" + p_110492_1_ + "->time", Integer.valueOf(-1), 1, Integer.MAX_VALUE);
         int var5 = this.func_110485_a(var3.get("index"), "frames->" + p_110492_1_ + "->index", (Integer)null, 0, Integer.MAX_VALUE);
         return new AnimationFrame(var5, var4);
      } else {
         return null;
      }
   }

   public JsonElement func_110491_a(AnimationMetadataSection p_110491_1_, Type p_110491_2_, JsonSerializationContext p_110491_3_) {
      JsonObject var4 = new JsonObject();
      var4.addProperty("frametime", Integer.valueOf(p_110491_1_.func_110469_d()));
      if(p_110491_1_.func_110474_b() != -1) {
         var4.addProperty("width", Integer.valueOf(p_110491_1_.func_110474_b()));
      }

      if(p_110491_1_.func_110471_a() != -1) {
         var4.addProperty("height", Integer.valueOf(p_110491_1_.func_110471_a()));
      }

      if(p_110491_1_.func_110473_c() > 0) {
         JsonArray var5 = new JsonArray();

         for(int var6 = 0; var6 < p_110491_1_.func_110473_c(); ++var6) {
            if(p_110491_1_.func_110470_b(var6)) {
               JsonObject var7 = new JsonObject();
               var7.addProperty("index", Integer.valueOf(p_110491_1_.func_110468_c(var6)));
               var7.addProperty("time", Integer.valueOf(p_110491_1_.func_110472_a(var6)));
               var5.add(var7);
            } else {
               var5.add(new JsonPrimitive(Integer.valueOf(p_110491_1_.func_110468_c(var6))));
            }
         }

         var4.add("frames", var5);
      }

      return var4;
   }

   public String func_110483_a() {
      return "animation";
   }

   // $FF: synthetic method
   public Object deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) {
      return this.func_110493_a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
   }

   // $FF: synthetic method
   public JsonElement serialize(Object p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
      return this.func_110491_a((AnimationMetadataSection)p_serialize_1_, p_serialize_2_, p_serialize_3_);
   }
}
