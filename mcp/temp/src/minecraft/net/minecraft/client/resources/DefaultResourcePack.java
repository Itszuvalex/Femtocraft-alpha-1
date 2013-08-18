package net.minecraft.client.resources;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.ResourcePack;
import net.minecraft.client.resources.data.MetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class DefaultResourcePack implements ResourcePack {

   public static final Set field_110608_a = ImmutableSet.of("minecraft");
   private final Map field_110606_b = Maps.newHashMap();
   private final File field_110607_c;


   public DefaultResourcePack(File p_i1288_1_) {
      this.field_110607_c = p_i1288_1_;
      this.func_110603_a(this.field_110607_c);
   }

   public InputStream func_110590_a(ResourceLocation p_110590_1_) throws IOException {
      InputStream var2 = this.func_110605_c(p_110590_1_);
      if(var2 != null) {
         return var2;
      } else {
         File var3 = (File)this.field_110606_b.get(p_110590_1_.toString());
         if(var3 != null) {
            return new FileInputStream(var3);
         } else {
            throw new FileNotFoundException(p_110590_1_.func_110623_a());
         }
      }
   }

   private InputStream func_110605_c(ResourceLocation p_110605_1_) {
      return DefaultResourcePack.class.getResourceAsStream("/assets/minecraft/" + p_110605_1_.func_110623_a());
   }

   public void func_110604_a(String p_110604_1_, File p_110604_2_) {
      this.field_110606_b.put((new ResourceLocation(p_110604_1_)).toString(), p_110604_2_);
   }

   public boolean func_110589_b(ResourceLocation p_110589_1_) {
      return this.func_110605_c(p_110589_1_) != null || this.field_110606_b.containsKey(p_110589_1_.toString());
   }

   public Set func_110587_b() {
      return field_110608_a;
   }

   public void func_110603_a(File p_110603_1_) {
      if(p_110603_1_.isDirectory()) {
         File[] var2 = p_110603_1_.listFiles();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            File var5 = var2[var4];
            this.func_110603_a(var5);
         }
      } else {
         this.func_110604_a(AbstractResourcePack.func_110595_a(this.field_110607_c, p_110603_1_), p_110603_1_);
      }

   }

   public MetadataSection func_135058_a(MetadataSerializer p_135058_1_, String p_135058_2_) throws IOException {
      return AbstractResourcePack.func_110596_a(p_135058_1_, DefaultResourcePack.class.getResourceAsStream("/" + (new ResourceLocation("pack.mcmeta")).func_110623_a()), p_135058_2_);
   }

   public BufferedImage func_110586_a() throws IOException {
      return ImageIO.read(DefaultResourcePack.class.getResourceAsStream("/" + (new ResourceLocation("pack.png")).func_110623_a()));
   }

   public String func_130077_b() {
      return "Default";
   }

}
