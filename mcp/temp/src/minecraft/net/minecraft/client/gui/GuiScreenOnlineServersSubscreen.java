package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class GuiScreenOnlineServersSubscreen {

   private final int field_104074_g;
   private final int field_104081_h;
   private final int field_104082_i;
   private final int field_104080_j;
   List field_104079_a = new ArrayList();
   String[] field_104077_b;
   String[] field_104078_c;
   String[][] field_104075_d;
   int field_104076_e;
   int field_104073_f;


   public GuiScreenOnlineServersSubscreen(int p_i1124_1_, int p_i1124_2_, int p_i1124_3_, int p_i1124_4_, int p_i1124_5_, int p_i1124_6_) {
      this.field_104074_g = p_i1124_1_;
      this.field_104081_h = p_i1124_2_;
      this.field_104082_i = p_i1124_3_;
      this.field_104080_j = p_i1124_4_;
      this.field_104076_e = p_i1124_5_;
      this.field_104073_f = p_i1124_6_;
      this.func_104068_a();
   }

   private void func_104068_a() {
      this.func_104070_b();
      this.field_104079_a.add(new GuiButton(5005, this.field_104082_i, this.field_104080_j + 1, 212, 20, this.func_104072_c()));
      this.field_104079_a.add(new GuiButton(5006, this.field_104082_i, this.field_104080_j + 25, 212, 20, this.func_104067_d()));
   }

   private void func_104070_b() {
      this.field_104077_b = new String[]{I18n.func_135053_a("options.difficulty.peaceful"), I18n.func_135053_a("options.difficulty.easy"), I18n.func_135053_a("options.difficulty.normal"), I18n.func_135053_a("options.difficulty.hard")};
      this.field_104078_c = new String[]{I18n.func_135053_a("selectWorld.gameMode.survival"), I18n.func_135053_a("selectWorld.gameMode.creative"), I18n.func_135053_a("selectWorld.gameMode.adventure")};
      this.field_104075_d = new String[][]{{I18n.func_135053_a("selectWorld.gameMode.survival.line1"), I18n.func_135053_a("selectWorld.gameMode.survival.line2")}, {I18n.func_135053_a("selectWorld.gameMode.creative.line1"), I18n.func_135053_a("selectWorld.gameMode.creative.line2")}, {I18n.func_135053_a("selectWorld.gameMode.adventure.line1"), I18n.func_135053_a("selectWorld.gameMode.adventure.line2")}};
   }

   private String func_104072_c() {
      String var1 = I18n.func_135053_a("options.difficulty");
      return var1 + ": " + this.field_104077_b[this.field_104076_e];
   }

   private String func_104067_d() {
      String var1 = I18n.func_135053_a("selectWorld.gameMode");
      return var1 + ": " + this.field_104078_c[this.field_104073_f];
   }

   void func_104069_a(GuiButton p_104069_1_) {
      if(p_104069_1_.field_73742_g) {
         if(p_104069_1_.field_73741_f == 5005) {
            this.field_104076_e = (this.field_104076_e + 1) % this.field_104077_b.length;
            p_104069_1_.field_73744_e = this.func_104072_c();
         } else if(p_104069_1_.field_73741_f == 5006) {
            this.field_104073_f = (this.field_104073_f + 1) % this.field_104078_c.length;
            p_104069_1_.field_73744_e = this.func_104067_d();
         }

      }
   }

   public void func_104071_a(GuiScreen p_104071_1_, FontRenderer p_104071_2_) {
      p_104071_1_.func_73731_b(p_104071_2_, this.field_104075_d[this.field_104073_f][0], this.field_104082_i, this.field_104080_j + 50, 10526880);
      p_104071_1_.func_73731_b(p_104071_2_, this.field_104075_d[this.field_104073_f][1], this.field_104082_i, this.field_104080_j + 60, 10526880);
   }
}
