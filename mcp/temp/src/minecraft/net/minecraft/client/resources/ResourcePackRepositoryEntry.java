package net.minecraft.client.resources;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.FileResourcePack;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.ResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.ResourcePackRepositoryFilter;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

@SideOnly(Side.CLIENT)
public class ResourcePackRepositoryEntry {

   private final File field_110523_b;
   private ResourcePack field_110524_c;
   private PackMetadataSection field_110521_d;
   private BufferedImage field_110522_e;
   private ResourceLocation field_110520_f;
   // $FF: synthetic field
   final ResourcePackRepository field_110525_a;


   private ResourcePackRepositoryEntry(ResourcePackRepository p_i1295_1_, File p_i1295_2_) {
      this.field_110525_a = p_i1295_1_;
      this.field_110523_b = p_i1295_2_;
   }

   public void func_110516_a() throws IOException {
      this.field_110524_c = (ResourcePack)(this.field_110523_b.isDirectory()?new FolderResourcePack(this.field_110523_b):new FileResourcePack(this.field_110523_b));
      this.field_110521_d = (PackMetadataSection)this.field_110524_c.func_135058_a(this.field_110525_a.field_110621_c, "pack");

      try {
         this.field_110522_e = this.field_110524_c.func_110586_a();
      } catch (IOException var2) {
         ;
      }

      if(this.field_110522_e == null) {
         this.field_110522_e = this.field_110525_a.field_110620_b.func_110586_a();
      }

      this.func_110517_b();
   }

   public void func_110518_a(TextureManager p_110518_1_) {
      if(this.field_110520_f == null) {
         this.field_110520_f = p_110518_1_.func_110578_a("texturepackicon", new DynamicTexture(this.field_110522_e));
      }

      p_110518_1_.func_110577_a(this.field_110520_f);
   }

   public void func_110517_b() {
      if(this.field_110524_c instanceof Closeable) {
         IOUtils.closeQuietly((Closeable)this.field_110524_c);
      }

   }

   public ResourcePack func_110514_c() {
      return this.field_110524_c;
   }

   public String func_110515_d() {
      return this.field_110524_c.func_130077_b();
   }

   public String func_110519_e() {
      return this.field_110521_d == null?EnumChatFormatting.RED + "Invalid pack.mcmeta (or missing \'pack\' section)":this.field_110521_d.func_110461_a();
   }

   public boolean equals(Object p_equals_1_) {
      return this == p_equals_1_?true:(p_equals_1_ instanceof ResourcePackRepositoryEntry?this.toString().equals(p_equals_1_.toString()):false);
   }

   public int hashCode() {
      return this.toString().hashCode();
   }

   public String toString() {
      return String.format("%s:%s:%d", new Object[]{this.field_110523_b.getName(), this.field_110523_b.isDirectory()?"folder":"zip", Long.valueOf(this.field_110523_b.lastModified())});
   }

   // $FF: synthetic method
   ResourcePackRepositoryEntry(ResourcePackRepository p_i1296_1_, File p_i1296_2_, ResourcePackRepositoryFilter p_i1296_3_) {
      this(p_i1296_1_, p_i1296_2_);
   }
}
