package net.minecraft.client.gui.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiScreenHorseInventory extends GuiContainer {

   private static final ResourceLocation field_110414_t = new ResourceLocation("textures/gui/container/horse.png");
   private IInventory field_110413_u;
   private IInventory field_110412_v;
   private EntityHorse field_110411_w;
   private float field_110416_x;
   private float field_110415_y;


   public GuiScreenHorseInventory(IInventory p_i1093_1_, IInventory p_i1093_2_, EntityHorse p_i1093_3_) {
      super(new ContainerHorseInventory(p_i1093_1_, p_i1093_2_, p_i1093_3_));
      this.field_110413_u = p_i1093_1_;
      this.field_110412_v = p_i1093_2_;
      this.field_110411_w = p_i1093_3_;
      this.field_73885_j = false;
   }

   protected void func_74189_g(int p_74189_1_, int p_74189_2_) {
      this.field_73886_k.func_78276_b(this.field_110412_v.func_94042_c()?this.field_110412_v.func_70303_b():I18n.func_135053_a(this.field_110412_v.func_70303_b()), 8, 6, 4210752);
      this.field_73886_k.func_78276_b(this.field_110413_u.func_94042_c()?this.field_110413_u.func_70303_b():I18n.func_135053_a(this.field_110413_u.func_70303_b()), 8, this.field_74195_c - 96 + 2, 4210752);
   }

   protected void func_74185_a(float p_74185_1_, int p_74185_2_, int p_74185_3_) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.func_110434_K().func_110577_a(field_110414_t);
      int var4 = (this.field_73880_f - this.field_74194_b) / 2;
      int var5 = (this.field_73881_g - this.field_74195_c) / 2;
      this.func_73729_b(var4, var5, 0, 0, this.field_74194_b, this.field_74195_c);
      if(this.field_110411_w.func_110261_ca()) {
         this.func_73729_b(var4 + 79, var5 + 17, 0, this.field_74195_c, 90, 54);
      }

      if(this.field_110411_w.func_110259_cr()) {
         this.func_73729_b(var4 + 7, var5 + 35, 0, this.field_74195_c + 54, 18, 18);
      }

      GuiInventory.func_110423_a(var4 + 51, var5 + 60, 17, (float)(var4 + 51) - this.field_110416_x, (float)(var5 + 75 - 50) - this.field_110415_y, this.field_110411_w);
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.field_110416_x = (float)p_73863_1_;
      this.field_110415_y = (float)p_73863_2_;
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

}
