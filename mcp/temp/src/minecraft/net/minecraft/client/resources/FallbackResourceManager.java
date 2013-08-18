package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.Resource;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.client.resources.ResourcePack;
import net.minecraft.client.resources.SimpleResource;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class FallbackResourceManager implements ResourceManager {

   protected final List field_110540_a = new ArrayList();
   private final MetadataSerializer field_110539_b;


   public FallbackResourceManager(MetadataSerializer p_i1289_1_) {
      this.field_110539_b = p_i1289_1_;
   }

   public void func_110538_a(ResourcePack p_110538_1_) {
      this.field_110540_a.add(p_110538_1_);
   }

   public Set func_135055_a() {
      return null;
   }

   public Resource func_110536_a(ResourceLocation p_110536_1_) throws IOException {
      ResourcePack var2 = null;
      ResourceLocation var3 = func_110537_b(p_110536_1_);

      for(int var4 = this.field_110540_a.size() - 1; var4 >= 0; --var4) {
         ResourcePack var5 = (ResourcePack)this.field_110540_a.get(var4);
         if(var2 == null && var5.func_110589_b(var3)) {
            var2 = var5;
         }

         if(var5.func_110589_b(p_110536_1_)) {
            InputStream var6 = null;
            if(var2 != null) {
               var6 = var2.func_110590_a(var3);
            }

            return new SimpleResource(p_110536_1_, var5.func_110590_a(p_110536_1_), var6, this.field_110539_b);
         }
      }

      throw new FileNotFoundException(p_110536_1_.toString());
   }

   public List func_135056_b(ResourceLocation p_135056_1_) throws IOException {
      ArrayList var2 = Lists.newArrayList();
      ResourceLocation var3 = func_110537_b(p_135056_1_);
      Iterator var4 = this.field_110540_a.iterator();

      while(var4.hasNext()) {
         ResourcePack var5 = (ResourcePack)var4.next();
         if(var5.func_110589_b(p_135056_1_)) {
            InputStream var6 = var5.func_110589_b(var3)?var5.func_110590_a(var3):null;
            var2.add(new SimpleResource(p_135056_1_, var5.func_110590_a(p_135056_1_), var6, this.field_110539_b));
         }
      }

      if(var2.isEmpty()) {
         throw new FileNotFoundException(p_135056_1_.toString());
      } else {
         return var2;
      }
   }

   static ResourceLocation func_110537_b(ResourceLocation p_110537_0_) {
      return new ResourceLocation(p_110537_0_.func_110624_b(), p_110537_0_.func_110623_a() + ".mcmeta");
   }
}
