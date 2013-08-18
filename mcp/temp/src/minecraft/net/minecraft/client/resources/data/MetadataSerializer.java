package net.minecraft.client.resources.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.data.MetadataSection;
import net.minecraft.client.resources.data.MetadataSectionSerializer;
import net.minecraft.client.resources.data.MetadataSerializerEmptyAnon;
import net.minecraft.client.resources.data.MetadataSerializerRegistration;
import net.minecraft.dispenser.IRegistry;
import net.minecraft.dispenser.RegistrySimple;

@SideOnly(Side.CLIENT)
public class MetadataSerializer {

   private final IRegistry field_110508_a = new RegistrySimple();
   private final GsonBuilder field_110506_b = new GsonBuilder();
   private Gson field_110507_c;


   public void func_110504_a(MetadataSectionSerializer p_110504_1_, Class p_110504_2_) {
      this.field_110508_a.func_82595_a(p_110504_1_.func_110483_a(), new MetadataSerializerRegistration(this, p_110504_1_, p_110504_2_, (MetadataSerializerEmptyAnon)null));
      this.field_110506_b.registerTypeAdapter(p_110504_2_, p_110504_1_);
      this.field_110507_c = null;
   }

   public MetadataSection func_110503_a(String p_110503_1_, JsonObject p_110503_2_) {
      if(p_110503_1_ == null) {
         throw new IllegalArgumentException("Metadata section name cannot be null");
      } else if(!p_110503_2_.has(p_110503_1_)) {
         return null;
      } else if(!p_110503_2_.get(p_110503_1_).isJsonObject()) {
         throw new IllegalArgumentException("Invalid metadata for \'" + p_110503_1_ + "\' - expected object, found " + p_110503_2_.get(p_110503_1_));
      } else {
         MetadataSerializerRegistration var3 = (MetadataSerializerRegistration)this.field_110508_a.func_82594_a(p_110503_1_);
         if(var3 == null) {
            throw new IllegalArgumentException("Don\'t know how to handle metadata section \'" + p_110503_1_ + "\'");
         } else {
            return (MetadataSection)this.func_110505_a().fromJson(p_110503_2_.getAsJsonObject(p_110503_1_), var3.field_110500_b);
         }
      }
   }

   private Gson func_110505_a() {
      if(this.field_110507_c == null) {
         this.field_110507_c = this.field_110506_b.create();
      }

      return this.field_110507_c;
   }
}
