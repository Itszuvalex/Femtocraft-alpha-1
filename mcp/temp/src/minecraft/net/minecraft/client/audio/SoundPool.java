package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.client.audio.SoundPoolProtocolHandler;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class SoundPool {

   private final Random field_77464_c = new Random();
   private final Map field_77461_d = Maps.newHashMap();
   private final ResourceManager field_110657_c;
   private final String field_110656_d;
   private final boolean field_77463_b;


   public SoundPool(ResourceManager p_i1330_1_, String p_i1330_2_, boolean p_i1330_3_) {
      this.field_110657_c = p_i1330_1_;
      this.field_110656_d = p_i1330_2_;
      this.field_77463_b = p_i1330_3_;
   }

   public void func_77459_a(String p_77459_1_) {
      try {
         String var2 = p_77459_1_;
         p_77459_1_ = p_77459_1_.substring(0, p_77459_1_.indexOf("."));
         if(this.field_77463_b) {
            while(Character.isDigit(p_77459_1_.charAt(p_77459_1_.length() - 1))) {
               p_77459_1_ = p_77459_1_.substring(0, p_77459_1_.length() - 1);
            }
         }

         p_77459_1_ = p_77459_1_.replaceAll("/", ".");
         Object var3 = (List)this.field_77461_d.get(p_77459_1_);
         if(var3 == null) {
            var3 = Lists.newArrayList();
            this.field_77461_d.put(p_77459_1_, var3);
         }

         ((List)var3).add(new SoundPoolEntry(var2, this.func_110654_c(var2)));
      } catch (MalformedURLException var4) {
         var4.printStackTrace();
         throw new RuntimeException(var4);
      }
   }

   private URL func_110654_c(String p_110654_1_) throws MalformedURLException {
      ResourceLocation var2 = new ResourceLocation(p_110654_1_);
      String var3 = String.format("%s:%s:%s/%s", new Object[]{"mcsounddomain", var2.func_110624_b(), this.field_110656_d, var2.func_110623_a()});
      SoundPoolProtocolHandler var4 = new SoundPoolProtocolHandler(this);
      return new URL((URL)null, var3, var4);
   }

   public SoundPoolEntry func_77458_a(String p_77458_1_) {
      List var2 = (List)this.field_77461_d.get(p_77458_1_);
      return var2 == null?null:(SoundPoolEntry)var2.get(this.field_77464_c.nextInt(var2.size()));
   }

   public SoundPoolEntry func_77460_a() {
      if(this.field_77461_d.isEmpty()) {
         return null;
      } else {
         ArrayList var1 = Lists.newArrayList(this.field_77461_d.keySet());
         return this.func_77458_a((String)var1.get(this.field_77464_c.nextInt(var1.size())));
      }
   }

   // $FF: synthetic method
   static ResourceManager func_110655_a(SoundPool p_110655_0_) {
      return p_110655_0_.field_110657_c;
   }
}
