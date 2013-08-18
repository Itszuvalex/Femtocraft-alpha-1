package net.minecraft.client.resources.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.data.MetadataSectionSerializer;

@SideOnly(Side.CLIENT)
public abstract class BaseMetadataSectionSerializer implements MetadataSectionSerializer {

   protected float func_110487_a(JsonElement p_110487_1_, String p_110487_2_, Float p_110487_3_, float p_110487_4_, float p_110487_5_) {
      p_110487_2_ = this.func_110483_a() + "->" + p_110487_2_;
      if(p_110487_1_ == null) {
         if(p_110487_3_ == null) {
            throw new JsonParseException("Missing " + p_110487_2_ + ": expected float");
         } else {
            return p_110487_3_.floatValue();
         }
      } else if(!p_110487_1_.isJsonPrimitive()) {
         throw new JsonParseException("Invalid " + p_110487_2_ + ": expected float, was " + p_110487_1_);
      } else {
         try {
            float var6 = p_110487_1_.getAsFloat();
            if(var6 < p_110487_4_) {
               throw new JsonParseException("Invalid " + p_110487_2_ + ": expected float >= " + p_110487_4_ + ", was " + var6);
            } else if(var6 > p_110487_5_) {
               throw new JsonParseException("Invalid " + p_110487_2_ + ": expected float <= " + p_110487_5_ + ", was " + var6);
            } else {
               return var6;
            }
         } catch (NumberFormatException var7) {
            throw new JsonParseException("Invalid " + p_110487_2_ + ": expected float, was " + p_110487_1_, var7);
         }
      }
   }

   protected int func_110485_a(JsonElement p_110485_1_, String p_110485_2_, Integer p_110485_3_, int p_110485_4_, int p_110485_5_) {
      p_110485_2_ = this.func_110483_a() + "->" + p_110485_2_;
      if(p_110485_1_ == null) {
         if(p_110485_3_ == null) {
            throw new JsonParseException("Missing " + p_110485_2_ + ": expected int");
         } else {
            return p_110485_3_.intValue();
         }
      } else if(!p_110485_1_.isJsonPrimitive()) {
         throw new JsonParseException("Invalid " + p_110485_2_ + ": expected int, was " + p_110485_1_);
      } else {
         try {
            int var6 = p_110485_1_.getAsInt();
            if(var6 < p_110485_4_) {
               throw new JsonParseException("Invalid " + p_110485_2_ + ": expected int >= " + p_110485_4_ + ", was " + var6);
            } else if(var6 > p_110485_5_) {
               throw new JsonParseException("Invalid " + p_110485_2_ + ": expected int <= " + p_110485_5_ + ", was " + var6);
            } else {
               return var6;
            }
         } catch (NumberFormatException var7) {
            throw new JsonParseException("Invalid " + p_110485_2_ + ": expected int, was " + p_110485_1_, var7);
         }
      }
   }

   protected String func_110486_a(JsonElement p_110486_1_, String p_110486_2_, String p_110486_3_, int p_110486_4_, int p_110486_5_) {
      p_110486_2_ = this.func_110483_a() + "->" + p_110486_2_;
      if(p_110486_1_ == null) {
         if(p_110486_3_ == null) {
            throw new JsonParseException("Missing " + p_110486_2_ + ": expected string");
         } else {
            return p_110486_3_;
         }
      } else if(!p_110486_1_.isJsonPrimitive()) {
         throw new JsonParseException("Invalid " + p_110486_2_ + ": expected string, was " + p_110486_1_);
      } else {
         String var6 = p_110486_1_.getAsString();
         if(var6.length() < p_110486_4_) {
            throw new JsonParseException("Invalid " + p_110486_2_ + ": expected string length >= " + p_110486_4_ + ", was " + var6);
         } else if(var6.length() > p_110486_5_) {
            throw new JsonParseException("Invalid " + p_110486_2_ + ": expected string length <= " + p_110486_5_ + ", was " + var6);
         } else {
            return var6;
         }
      }
   }

   protected boolean func_110484_a(JsonElement p_110484_1_, String p_110484_2_, Boolean p_110484_3_) {
      p_110484_2_ = this.func_110483_a() + "->" + p_110484_2_;
      if(p_110484_1_ == null) {
         if(p_110484_3_ == null) {
            throw new JsonParseException("Missing " + p_110484_2_ + ": expected boolean");
         } else {
            return p_110484_3_.booleanValue();
         }
      } else if(!p_110484_1_.isJsonPrimitive()) {
         throw new JsonParseException("Invalid " + p_110484_2_ + ": expected boolean, was " + p_110484_1_);
      } else {
         boolean var4 = p_110484_1_.getAsBoolean();
         return var4;
      }
   }
}
