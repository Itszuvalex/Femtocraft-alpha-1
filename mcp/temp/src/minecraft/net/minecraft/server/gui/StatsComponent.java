package net.minecraft.server.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import javax.swing.JComponent;
import javax.swing.Timer;
import net.minecraft.network.TcpConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.StatsComponentINNER1;

@SideOnly(Side.SERVER)
public class StatsComponent extends JComponent {

   private static final DecimalFormat field_120040_a = new DecimalFormat("########0.000");
   private int[] field_120038_b = new int[256];
   private int field_120039_c;
   private String[] field_120036_d = new String[11];
   private final MinecraftServer field_120037_e;


   public StatsComponent(MinecraftServer p_i2367_1_) {
      this.field_120037_e = p_i2367_1_;
      this.setPreferredSize(new Dimension(456, 246));
      this.setMinimumSize(new Dimension(456, 246));
      this.setMaximumSize(new Dimension(456, 246));
      (new Timer(500, new StatsComponentINNER1(this))).start();
      this.setBackground(Color.BLACK);
   }

   private void func_120034_a() {
      long var1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
      System.gc();
      this.field_120036_d[0] = "Memory use: " + var1 / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)";
      this.field_120036_d[1] = "Threads: " + TcpConnection.field_74471_a.get() + " + " + TcpConnection.field_74469_b.get();
      this.field_120036_d[2] = "Avg tick: " + field_120040_a.format(this.func_120035_a(this.field_120037_e.field_71311_j) * 1.0E-6D) + " ms";
      this.field_120036_d[3] = "Avg sent: " + (int)this.func_120035_a(this.field_120037_e.field_71300_f) + ", Avg size: " + (int)this.func_120035_a(this.field_120037_e.field_71301_g);
      this.field_120036_d[4] = "Avg rec: " + (int)this.func_120035_a(this.field_120037_e.field_71313_h) + ", Avg size: " + (int)this.func_120035_a(this.field_120037_e.field_71314_i);
      if(this.field_120037_e.field_71305_c != null) {
         for(int var3 = 0; var3 < this.field_120037_e.field_71305_c.length; ++var3) {
            this.field_120036_d[5 + var3] = "Lvl " + var3 + " tick: " + field_120040_a.format(this.func_120035_a(this.field_120037_e.field_71312_k[var3]) * 1.0E-6D) + " ms";
            if(this.field_120037_e.field_71305_c[var3] != null && this.field_120037_e.field_71305_c[var3].field_73059_b != null) {
               this.field_120036_d[5 + var3] = this.field_120036_d[5 + var3] + ", " + this.field_120037_e.field_71305_c[var3].field_73059_b.func_73148_d();
               this.field_120036_d[5 + var3] = this.field_120036_d[5 + var3] + ", Vec3: " + this.field_120037_e.field_71305_c[var3].func_82732_R().func_82590_d() + " / " + this.field_120037_e.field_71305_c[var3].func_82732_R().func_82591_c();
            }
         }
      }

      double var5 = 12500.0D;
      this.field_120038_b[this.field_120039_c++ & 255] = (int)(this.func_120035_a(this.field_120037_e.field_71301_g) * 100.0D / 12500.0D);
      this.repaint();
   }

   private double func_120035_a(long[] p_120035_1_) {
      long var2 = 0L;

      for(int var4 = 0; var4 < p_120035_1_.length; ++var4) {
         var2 += p_120035_1_[var4];
      }

      return (double)var2 / (double)p_120035_1_.length;
   }

   public void paint(Graphics p_paint_1_) {
      p_paint_1_.setColor(new Color(16777215));
      p_paint_1_.fillRect(0, 0, 456, 246);

      int var2;
      for(var2 = 0; var2 < 256; ++var2) {
         int var3 = this.field_120038_b[var2 + this.field_120039_c & 255];
         p_paint_1_.setColor(new Color(var3 + 28 << 16));
         p_paint_1_.fillRect(var2, 100 - var3, 1, var3);
      }

      p_paint_1_.setColor(Color.BLACK);

      for(var2 = 0; var2 < this.field_120036_d.length; ++var2) {
         String var4 = this.field_120036_d[var2];
         if(var4 != null) {
            p_paint_1_.drawString(var4, 32, 116 + var2 * 16);
         }
      }

   }

   // $FF: synthetic method
   static void func_120033_a(StatsComponent p_120033_0_) {
      p_120033_0_.func_120034_a();
   }

}
