package net.minecraft.client.gui.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiDispenser extends GuiContainer {

   private static final ResourceLocation field_110419_u = new ResourceLocation("textures/gui/container/dispenser.png");
   public TileEntityDispenser field_94078_r;


   public GuiDispenser(InventoryPlayer p_i1098_1_, TileEntityDispenser p_i1098_2_) {
      super(new ContainerDispenser(p_i1098_1_, p_i1098_2_));
      this.field_94078_r = p_i1098_2_;
   }

   protected void func_74189_g(int p_74189_1_, int p_74189_2_) {
      String var3 = this.field_94078_r.func_94042_c()?this.field_94078_r.func_70303_b():I18n.func_135053_a(this.field_94078_r.func_70303_b());
      this.field_73886_k.func_78276_b(var3, this.field_74194_b / 2 - this.field_73886_k.func_78256_a(var3) / 2, 6, 4210752);
      this.field_73886_k.func_78276_b(I18n.func_135053_a("container.inventory"), 8, this.field_74195_c - 96 + 2, 4210752);
   }

   protected void func_74185_a(float p_74185_1_, int p_74185_2_, int p_74185_3_) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.func_110434_K().func_110577_a(field_110419_u);
      int var4 = (this.field_73880_f - this.field_74194_b) / 2;
      int var5 = (this.field_73881_g - this.field_74195_c) / 2;
      this.func_73729_b(var4, var5, 0, 0, this.field_74194_b, this.field_74195_c);
   }

}
