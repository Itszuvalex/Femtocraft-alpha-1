package net.minecraft.client.gui.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
class GuiBeaconButton extends GuiButton {

   private final ResourceLocation field_82259_k;
   private final int field_82257_l;
   private final int field_82258_m;
   private boolean field_82256_n;


   protected GuiBeaconButton(int p_i1077_1_, int p_i1077_2_, int p_i1077_3_, ResourceLocation p_i1077_4_, int p_i1077_5_, int p_i1077_6_) {
      super(p_i1077_1_, p_i1077_2_, p_i1077_3_, 22, 22, "");
      this.field_82259_k = p_i1077_4_;
      this.field_82257_l = p_i1077_5_;
      this.field_82258_m = p_i1077_6_;
   }

   public void func_73737_a(Minecraft p_73737_1_, int p_73737_2_, int p_73737_3_) {
      if(this.field_73748_h) {
         p_73737_1_.func_110434_K().func_110577_a(GuiBeacon.func_110427_g());
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_82253_i = p_73737_2_ >= this.field_73746_c && p_73737_3_ >= this.field_73743_d && p_73737_2_ < this.field_73746_c + this.field_73747_a && p_73737_3_ < this.field_73743_d + this.field_73745_b;
         short var4 = 219;
         int var5 = 0;
         if(!this.field_73742_g) {
            var5 += this.field_73747_a * 2;
         } else if(this.field_82256_n) {
            var5 += this.field_73747_a * 1;
         } else if(this.field_82253_i) {
            var5 += this.field_73747_a * 3;
         }

         this.func_73729_b(this.field_73746_c, this.field_73743_d, var5, var4, this.field_73747_a, this.field_73745_b);
         if(!GuiBeacon.func_110427_g().equals(this.field_82259_k)) {
            p_73737_1_.func_110434_K().func_110577_a(this.field_82259_k);
         }

         this.func_73729_b(this.field_73746_c + 2, this.field_73743_d + 2, this.field_82257_l, this.field_82258_m, 18, 18);
      }
   }

   public boolean func_82255_b() {
      return this.field_82256_n;
   }

   public void func_82254_b(boolean p_82254_1_) {
      this.field_82256_n = p_82254_1_;
   }
}
