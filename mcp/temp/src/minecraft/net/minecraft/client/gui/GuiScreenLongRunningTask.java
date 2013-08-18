package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Collections;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.TaskLongRunning;

@SideOnly(Side.CLIENT)
public class GuiScreenLongRunningTask extends GuiScreen {

   private final int field_96213_b = 666;
   private final GuiScreen field_96215_c;
   private final Thread field_98118_d;
   private volatile String field_96212_d = "";
   private volatile boolean field_96219_n;
   private volatile String field_96220_o;
   private volatile boolean field_96218_p;
   private int field_96216_q;
   private TaskLongRunning field_96214_r;
   public static final String[] field_96217_a = new String[]{"\u201a\u00f1\u00c9 \u201a\u00f1\u00d1 \u201a\u00f1\u00d6 \u201a\u00f1\u00dc \u201a\u00f1\u00e1 \u201a\u00f1\u00e0 \u201a\u00f1\u00e1 \u201a\u00f1\u00dc \u201a\u00f1\u00d6 \u201a\u00f1\u00d1 \u201a\u00f1\u00c9", "_ \u201a\u00f1\u00c9 \u201a\u00f1\u00d1 \u201a\u00f1\u00d6 \u201a\u00f1\u00dc \u201a\u00f1\u00e1 \u201a\u00f1\u00e0 \u201a\u00f1\u00e1 \u201a\u00f1\u00dc \u201a\u00f1\u00d6 \u201a\u00f1\u00d1", "_ _ \u201a\u00f1\u00c9 \u201a\u00f1\u00d1 \u201a\u00f1\u00d6 \u201a\u00f1\u00dc \u201a\u00f1\u00e1 \u201a\u00f1\u00e0 \u201a\u00f1\u00e1 \u201a\u00f1\u00dc \u201a\u00f1\u00d6", "_ _ _ \u201a\u00f1\u00c9 \u201a\u00f1\u00d1 \u201a\u00f1\u00d6 \u201a\u00f1\u00dc \u201a\u00f1\u00e1 \u201a\u00f1\u00e0 \u201a\u00f1\u00e1 \u201a\u00f1\u00dc", "_ _ _ _ \u201a\u00f1\u00c9 \u201a\u00f1\u00d1 \u201a\u00f1\u00d6 \u201a\u00f1\u00dc \u201a\u00f1\u00e1 \u201a\u00f1\u00e0 \u201a\u00f1\u00e1", "_ _ _ _ _ \u201a\u00f1\u00c9 \u201a\u00f1\u00d1 \u201a\u00f1\u00d6 \u201a\u00f1\u00dc \u201a\u00f1\u00e1 \u201a\u00f1\u00e0", "_ _ _ _ \u201a\u00f1\u00c9 \u201a\u00f1\u00d1 \u201a\u00f1\u00d6 \u201a\u00f1\u00dc \u201a\u00f1\u00e1 \u201a\u00f1\u00e0 \u201a\u00f1\u00e1", "_ _ _ \u201a\u00f1\u00c9 \u201a\u00f1\u00d1 \u201a\u00f1\u00d6 \u201a\u00f1\u00dc \u201a\u00f1\u00e1 \u201a\u00f1\u00e0 \u201a\u00f1\u00e1 \u201a\u00f1\u00dc", "_ _ \u201a\u00f1\u00c9 \u201a\u00f1\u00d1 \u201a\u00f1\u00d6 \u201a\u00f1\u00dc \u201a\u00f1\u00e1 \u201a\u00f1\u00e0 \u201a\u00f1\u00e1 \u201a\u00f1\u00dc \u201a\u00f1\u00d6", "_ \u201a\u00f1\u00c9 \u201a\u00f1\u00d1 \u201a\u00f1\u00d6 \u201a\u00f1\u00dc \u201a\u00f1\u00e1 \u201a\u00f1\u00e0 \u201a\u00f1\u00e1 \u201a\u00f1\u00dc \u201a\u00f1\u00d6 \u201a\u00f1\u00d1", "\u201a\u00f1\u00c9 \u201a\u00f1\u00d1 \u201a\u00f1\u00d6 \u201a\u00f1\u00dc \u201a\u00f1\u00e1 \u201a\u00f1\u00e0 \u201a\u00f1\u00e1 \u201a\u00f1\u00dc \u201a\u00f1\u00d6 \u201a\u00f1\u00d1 \u201a\u00f1\u00c9", "\u201a\u00f1\u00d1 \u201a\u00f1\u00d6 \u201a\u00f1\u00dc \u201a\u00f1\u00e1 \u201a\u00f1\u00e0 \u201a\u00f1\u00e1 \u201a\u00f1\u00dc \u201a\u00f1\u00d6 \u201a\u00f1\u00d1 \u201a\u00f1\u00c9 _", "\u201a\u00f1\u00d6 \u201a\u00f1\u00dc \u201a\u00f1\u00e1 \u201a\u00f1\u00e0 \u201a\u00f1\u00e1 \u201a\u00f1\u00dc \u201a\u00f1\u00d6 \u201a\u00f1\u00d1 \u201a\u00f1\u00c9 _ _", "\u201a\u00f1\u00dc \u201a\u00f1\u00e1 \u201a\u00f1\u00e0 \u201a\u00f1\u00e1 \u201a\u00f1\u00dc \u201a\u00f1\u00d6 \u201a\u00f1\u00d1 \u201a\u00f1\u00c9 _ _ _", "\u201a\u00f1\u00e1 \u201a\u00f1\u00e0 \u201a\u00f1\u00e1 \u201a\u00f1\u00dc \u201a\u00f1\u00d6 \u201a\u00f1\u00d1 \u201a\u00f1\u00c9 _ _ _ _", "\u201a\u00f1\u00e0 \u201a\u00f1\u00e1 \u201a\u00f1\u00dc \u201a\u00f1\u00d6 \u201a\u00f1\u00d1 \u201a\u00f1\u00c9 _ _ _ _ _", "\u201a\u00f1\u00e1 \u201a\u00f1\u00e0 \u201a\u00f1\u00e1 \u201a\u00f1\u00dc \u201a\u00f1\u00d6 \u201a\u00f1\u00d1 \u201a\u00f1\u00c9 _ _ _ _", "\u201a\u00f1\u00dc \u201a\u00f1\u00e1 \u201a\u00f1\u00e0 \u201a\u00f1\u00e1 \u201a\u00f1\u00dc \u201a\u00f1\u00d6 \u201a\u00f1\u00d1 \u201a\u00f1\u00c9 _ _ _", "\u201a\u00f1\u00d6 \u201a\u00f1\u00dc \u201a\u00f1\u00e1 \u201a\u00f1\u00e0 \u201a\u00f1\u00e1 \u201a\u00f1\u00dc \u201a\u00f1\u00d6 \u201a\u00f1\u00d1 \u201a\u00f1\u00c9 _ _", "\u201a\u00f1\u00d1 \u201a\u00f1\u00d6 \u201a\u00f1\u00dc \u201a\u00f1\u00e1 \u201a\u00f1\u00e0 \u201a\u00f1\u00e1 \u201a\u00f1\u00dc \u201a\u00f1\u00d6 \u201a\u00f1\u00d1 \u201a\u00f1\u00c9 _"};


   public GuiScreenLongRunningTask(Minecraft p_i1112_1_, GuiScreen p_i1112_2_, TaskLongRunning p_i1112_3_) {
      super.field_73887_h = Collections.synchronizedList(new ArrayList());
      this.field_73882_e = p_i1112_1_;
      this.field_96215_c = p_i1112_2_;
      this.field_96214_r = p_i1112_3_;
      p_i1112_3_.func_96574_a(this);
      this.field_98118_d = new Thread(p_i1112_3_);
   }

   public void func_98117_g() {
      this.field_98118_d.start();
   }

   public void func_73876_c() {
      super.func_73876_c();
      ++this.field_96216_q;
      this.field_96214_r.func_96573_a();
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) {}

   public void func_73866_w_() {
      this.field_96214_r.func_96571_d();
   }

   protected void func_73875_a(GuiButton p_73875_1_) {
      if(p_73875_1_.field_73741_f == 666) {
         this.field_96218_p = true;
         this.field_73882_e.func_71373_a(this.field_96215_c);
      }

      this.field_96214_r.func_96572_a(p_73875_1_);
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_73873_v_();
      this.func_73732_a(this.field_73886_k, this.field_96212_d, this.field_73880_f / 2, this.field_73881_g / 2 - 50, 16777215);
      this.func_73732_a(this.field_73886_k, "", this.field_73880_f / 2, this.field_73881_g / 2 - 10, 16777215);
      if(!this.field_96219_n) {
         this.func_73732_a(this.field_73886_k, field_96217_a[this.field_96216_q % field_96217_a.length], this.field_73880_f / 2, this.field_73881_g / 2 + 15, 8421504);
      }

      if(this.field_96219_n) {
         this.func_73732_a(this.field_73886_k, this.field_96220_o, this.field_73880_f / 2, this.field_73881_g / 2 + 15, 16711680);
      }

      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   public void func_96209_a(String p_96209_1_) {
      this.field_96219_n = true;
      this.field_96220_o = p_96209_1_;
      this.field_73887_h.clear();
      this.field_73887_h.add(new GuiButton(666, this.field_73880_f / 2 - 100, this.field_73881_g / 4 + 120 + 12, "Back"));
   }

   public Minecraft func_96208_g() {
      return this.field_73882_e;
   }

   public void func_96210_b(String p_96210_1_) {
      this.field_96212_d = p_96210_1_;
   }

   public boolean func_96207_h() {
      return this.field_96218_p;
   }

}
