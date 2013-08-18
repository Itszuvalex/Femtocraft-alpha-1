package net.minecraft.client.resources;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.GuiScreenTemporaryResourcePackSelect;
import net.minecraft.client.resources.ResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.ResourcePackRepositoryEntry;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
class GuiScreenTemporaryResourcePackSelectSelectionList extends GuiSlot {

   private final ResourcePackRepository field_110511_b;
   private ResourceLocation field_110513_h;
   // $FF: synthetic field
   final GuiScreenTemporaryResourcePackSelect field_110512_a;


   public GuiScreenTemporaryResourcePackSelectSelectionList(GuiScreenTemporaryResourcePackSelect p_i1301_1_, ResourcePackRepository p_i1301_2_) {
      super(GuiScreenTemporaryResourcePackSelect.func_110344_a(p_i1301_1_), p_i1301_1_.field_73880_f, p_i1301_1_.field_73881_g, 32, p_i1301_1_.field_73881_g - 55 + 4, 36);
      this.field_110512_a = p_i1301_1_;
      this.field_110511_b = p_i1301_2_;
      p_i1301_2_.func_110611_a();
   }

   protected int func_77217_a() {
      return 1 + this.field_110511_b.func_110609_b().size();
   }

   protected void func_77213_a(int p_77213_1_, boolean p_77213_2_) {
      List var3 = this.field_110511_b.func_110609_b();

      try {
         if(p_77213_1_ == 0) {
            throw new RuntimeException("This is so horrible ;D");
         }

         this.field_110511_b.func_110615_a(new ResourcePackRepositoryEntry[]{(ResourcePackRepositoryEntry)var3.get(p_77213_1_ - 1)});
         GuiScreenTemporaryResourcePackSelect.func_110341_b(this.field_110512_a).func_110436_a();
      } catch (Exception var5) {
         this.field_110511_b.func_110615_a(new ResourcePackRepositoryEntry[0]);
         GuiScreenTemporaryResourcePackSelect.func_110339_c(this.field_110512_a).func_110436_a();
      }

      GuiScreenTemporaryResourcePackSelect.func_110345_d(this.field_110512_a).field_71474_y.field_74346_m = this.field_110511_b.func_110610_d();
      GuiScreenTemporaryResourcePackSelect.func_110334_e(this.field_110512_a).field_71474_y.func_74303_b();
   }

   protected boolean func_77218_a(int p_77218_1_) {
      List var2 = this.field_110511_b.func_110613_c();
      return p_77218_1_ == 0?var2.isEmpty():var2.contains(this.field_110511_b.func_110609_b().get(p_77218_1_ - 1));
   }

   protected int func_77212_b() {
      return this.func_77217_a() * 36;
   }

   protected void func_77221_c() {
      this.field_110512_a.func_73873_v_();
   }

   protected void func_77214_a(int p_77214_1_, int p_77214_2_, int p_77214_3_, int p_77214_4_, Tessellator p_77214_5_) {
      TextureManager var6 = GuiScreenTemporaryResourcePackSelect.func_110340_f(this.field_110512_a).func_110434_K();
      if(p_77214_1_ == 0) {
         try {
            ResourcePack var12 = this.field_110511_b.field_110620_b;
            PackMetadataSection var13 = (PackMetadataSection)var12.func_135058_a(this.field_110511_b.field_110621_c, "pack");
            if(this.field_110513_h == null) {
               this.field_110513_h = var6.func_110578_a("texturepackicon", new DynamicTexture(var12.func_110586_a()));
            }

            var6.func_110577_a(this.field_110513_h);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            p_77214_5_.func_78382_b();
            p_77214_5_.func_78378_d(16777215);
            p_77214_5_.func_78374_a((double)p_77214_2_, (double)(p_77214_3_ + p_77214_4_), 0.0D, 0.0D, 1.0D);
            p_77214_5_.func_78374_a((double)(p_77214_2_ + 32), (double)(p_77214_3_ + p_77214_4_), 0.0D, 1.0D, 1.0D);
            p_77214_5_.func_78374_a((double)(p_77214_2_ + 32), (double)p_77214_3_, 0.0D, 1.0D, 0.0D);
            p_77214_5_.func_78374_a((double)p_77214_2_, (double)p_77214_3_, 0.0D, 0.0D, 0.0D);
            p_77214_5_.func_78381_a();
            this.field_110512_a.func_73731_b(GuiScreenTemporaryResourcePackSelect.func_130017_g(this.field_110512_a), "Default", p_77214_2_ + 32 + 2, p_77214_3_ + 1, 16777215);
            this.field_110512_a.func_73731_b(GuiScreenTemporaryResourcePackSelect.func_130016_h(this.field_110512_a), var13.func_110461_a(), p_77214_2_ + 32 + 2, p_77214_3_ + 12 + 10, 8421504);
         } catch (IOException var11) {
            ;
         }

      } else {
         ResourcePackRepositoryEntry var7 = (ResourcePackRepositoryEntry)this.field_110511_b.func_110609_b().get(p_77214_1_ - 1);
         var7.func_110518_a(var6);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         p_77214_5_.func_78382_b();
         p_77214_5_.func_78378_d(16777215);
         p_77214_5_.func_78374_a((double)p_77214_2_, (double)(p_77214_3_ + p_77214_4_), 0.0D, 0.0D, 1.0D);
         p_77214_5_.func_78374_a((double)(p_77214_2_ + 32), (double)(p_77214_3_ + p_77214_4_), 0.0D, 1.0D, 1.0D);
         p_77214_5_.func_78374_a((double)(p_77214_2_ + 32), (double)p_77214_3_, 0.0D, 1.0D, 0.0D);
         p_77214_5_.func_78374_a((double)p_77214_2_, (double)p_77214_3_, 0.0D, 0.0D, 0.0D);
         p_77214_5_.func_78381_a();
         String var8 = var7.func_110515_d();
         if(var8.length() > 32) {
            var8 = var8.substring(0, 32).trim() + "...";
         }

         this.field_110512_a.func_73731_b(GuiScreenTemporaryResourcePackSelect.func_110337_i(this.field_110512_a), var8, p_77214_2_ + 32 + 2, p_77214_3_ + 1, 16777215);
         List var9 = GuiScreenTemporaryResourcePackSelect.func_110335_j(this.field_110512_a).func_78271_c(var7.func_110519_e(), 183);

         for(int var10 = 0; var10 < 2 && var10 < var9.size(); ++var10) {
            this.field_110512_a.func_73731_b(GuiScreenTemporaryResourcePackSelect.func_110338_k(this.field_110512_a), (String)var9.get(var10), p_77214_2_ + 32 + 2, p_77214_3_ + 12 + 10 * var10, 8421504);
         }

      }
   }

   // $FF: synthetic method
   static ResourcePackRepository func_110510_a(GuiScreenTemporaryResourcePackSelectSelectionList p_110510_0_) {
      return p_110510_0_.field_110511_b;
   }
}
