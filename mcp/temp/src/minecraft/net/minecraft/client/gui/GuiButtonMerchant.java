package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMerchant;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiButtonMerchant extends GuiButton {

   private final boolean field_73749_j;


   public GuiButtonMerchant(int p_i1095_1_, int p_i1095_2_, int p_i1095_3_, boolean p_i1095_4_) {
      super(p_i1095_1_, p_i1095_2_, p_i1095_3_, 12, 19, "");
      this.field_73749_j = p_i1095_4_;
   }

   public void func_73737_a(Minecraft p_73737_1_, int p_73737_2_, int p_73737_3_) {
      if(this.field_73748_h) {
         p_73737_1_.func_110434_K().func_110577_a(GuiMerchant.func_110417_h());
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         boolean var4 = p_73737_2_ >= this.field_73746_c && p_73737_3_ >= this.field_73743_d && p_73737_2_ < this.field_73746_c + this.field_73747_a && p_73737_3_ < this.field_73743_d + this.field_73745_b;
         int var5 = 0;
         int var6 = 176;
         if(!this.field_73742_g) {
            var6 += this.field_73747_a * 2;
         } else if(var4) {
            var6 += this.field_73747_a;
         }

         if(!this.field_73749_j) {
            var5 += this.field_73745_b;
         }

         this.func_73729_b(this.field_73746_c, this.field_73743_d, var6, var5, this.field_73747_a, this.field_73745_b);
      }
   }
}
