package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLink;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenConfigureWorld;
import net.minecraft.client.gui.GuiScreenConfirmation;
import net.minecraft.client.gui.GuiScreenLongRunningTask;
import net.minecraft.client.gui.GuiSlotOnlineServerList;
import net.minecraft.client.gui.TaskOnlineConnect;
import net.minecraft.client.gui.ThreadOnlineScreen;
import net.minecraft.client.gui.mco.GuiScreenCreateOnlineWorld;
import net.minecraft.client.gui.mco.GuiScreenPendingInvitation;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.GuiScreenConfirmationType;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.mco.McoServerList;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiScreenOnlineServers extends GuiScreen {

   private static final ResourceLocation field_130039_a = new ResourceLocation("textures/gui/widgets.png");
   private GuiScreen field_96188_a;
   private GuiSlotOnlineServerList field_96186_b;
   private static int field_96187_c;
   private static final Object field_96185_d = new Object();
   private long field_96189_n = -1L;
   private GuiButton field_96190_o;
   private GuiButton field_96198_p;
   private GuiButtonLink field_96197_q;
   private GuiButton field_96196_r;
   private String field_96195_s;
   private static McoServerList field_96194_t = new McoServerList();
   private boolean field_96193_u;
   private List field_96192_v = Lists.newArrayList();
   private volatile int field_96199_x = 0;
   private Long field_102019_y;
   private int field_104044_y;


   public GuiScreenOnlineServers(GuiScreen p_i1123_1_) {
      this.field_96188_a = p_i1123_1_;
   }

   public void func_73866_w_() {
      Keyboard.enableRepeatEvents(true);
      this.field_73887_h.clear();
      field_96194_t.func_130129_a(this.field_73882_e.func_110432_I());
      if(!this.field_96193_u) {
         this.field_96193_u = true;
         this.field_96186_b = new GuiSlotOnlineServerList(this);
      } else {
         this.field_96186_b.func_104084_a(this.field_73880_f, this.field_73881_g, 32, this.field_73881_g - 64);
      }

      this.func_96178_g();
   }

   public void func_96178_g() {
      this.field_73887_h.add(this.field_96196_r = new GuiButton(1, this.field_73880_f / 2 - 154, this.field_73881_g - 52, 100, 20, I18n.func_135053_a("mco.selectServer.play")));
      this.field_73887_h.add(this.field_96198_p = new GuiButton(2, this.field_73880_f / 2 - 48, this.field_73881_g - 52, 100, 20, I18n.func_135053_a("mco.selectServer.create")));
      this.field_73887_h.add(this.field_96190_o = new GuiButton(3, this.field_73880_f / 2 + 58, this.field_73881_g - 52, 100, 20, I18n.func_135053_a("mco.selectServer.configure")));
      this.field_73887_h.add(this.field_96197_q = new GuiButtonLink(4, this.field_73880_f / 2 - 154, this.field_73881_g - 28, 154, 20, I18n.func_135053_a("mco.selectServer.moreinfo")));
      this.field_73887_h.add(new GuiButton(0, this.field_73880_f / 2 + 6, this.field_73881_g - 28, 153, 20, I18n.func_135053_a("gui.cancel")));
      McoServer var1 = this.func_140030_b(this.field_96189_n);
      this.field_96196_r.field_73742_g = var1 != null && var1.field_96404_d.equals("OPEN") && !var1.field_98166_h;
      this.field_96198_p.field_73742_g = this.field_96199_x > 0;
      if(var1 != null && !var1.field_96405_e.equals(this.field_73882_e.func_110432_I().func_111285_a())) {
         this.field_96190_o.field_73744_e = I18n.func_135053_a("mco.selectServer.leave");
      }

   }

   public void func_73876_c() {
      super.func_73876_c();
      ++this.field_104044_y;
      if(field_96194_t.func_130127_a()) {
         List var1 = field_96194_t.func_98252_c();
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            McoServer var3 = (McoServer)var2.next();
            Iterator var4 = this.field_96192_v.iterator();

            while(var4.hasNext()) {
               McoServer var5 = (McoServer)var4.next();
               if(var3.field_96408_a == var5.field_96408_a) {
                  var3.func_96401_a(var5);
                  if(this.field_102019_y != null && this.field_102019_y.longValue() == var3.field_96408_a) {
                     this.field_102019_y = null;
                     var3.field_96411_l = false;
                  }
                  break;
               }
            }
         }

         this.field_96199_x = field_96194_t.func_140056_e();
         this.field_96192_v = var1;
         field_96194_t.func_98250_b();
      }

      this.field_96198_p.field_73742_g = this.field_96199_x > 0;
   }

   public void func_73874_b() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void func_73875_a(GuiButton p_73875_1_) {
      if(p_73875_1_.field_73742_g) {
         if(p_73875_1_.field_73741_f == 1) {
            this.func_140032_e(this.field_96189_n);
         } else if(p_73875_1_.field_73741_f == 3) {
            this.func_140019_s();
         } else if(p_73875_1_.field_73741_f == 0) {
            field_96194_t.func_98248_d();
            this.field_73882_e.func_71373_a(this.field_96188_a);
         } else if(p_73875_1_.field_73741_f == 2) {
            field_96194_t.func_98248_d();
            this.field_73882_e.func_71373_a(new GuiScreenCreateOnlineWorld(this));
         } else if(p_73875_1_.field_73741_f == 4) {
            this.field_96197_q.func_96135_a("http://realms.minecraft.net/");
         } else {
            this.field_96186_b.func_73875_a(p_73875_1_);
         }

      }
   }

   private void func_140019_s() {
      McoServer var1 = this.func_140030_b(this.field_96189_n);
      if(var1 != null) {
         if(this.field_73882_e.func_110432_I().func_111285_a().equals(var1.field_96405_e)) {
            McoServer var2 = this.func_98086_a(var1.field_96408_a);
            if(var2 != null) {
               field_96194_t.func_98248_d();
               this.field_73882_e.func_71373_a(new GuiScreenConfigureWorld(this, var2));
            }
         } else {
            String var4 = I18n.func_135053_a("mco.configure.world.leave.question.line1");
            String var3 = I18n.func_135053_a("mco.configure.world.leave.question.line2");
            this.field_73882_e.func_71373_a(new GuiScreenConfirmation(this, GuiScreenConfirmationType.Info, var4, var3, 3));
         }
      }

   }

   private McoServer func_140030_b(long p_140030_1_) {
      Iterator var3 = this.field_96192_v.iterator();

      McoServer var4;
      do {
         if(!var3.hasNext()) {
            return null;
         }

         var4 = (McoServer)var3.next();
      } while(var4.field_96408_a != p_140030_1_);

      return var4;
   }

   private int func_140009_c(long p_140009_1_) {
      for(int var3 = 0; var3 < this.field_96192_v.size(); ++var3) {
         if(((McoServer)this.field_96192_v.get(var3)).field_96408_a == p_140009_1_) {
            return var3;
         }
      }

      return -1;
   }

   public void func_73878_a(boolean p_73878_1_, int p_73878_2_) {
      if(p_73878_2_ == 3 && p_73878_1_) {
         (new ThreadOnlineScreen(this)).start();
      }

      this.field_73882_e.func_71373_a(this);
   }

   private void func_140012_t() {
      int var1 = this.func_140009_c(this.field_96189_n);
      if(this.field_96192_v.size() - 1 == var1) {
         --var1;
      }

      if(this.field_96192_v.size() == 0) {
         var1 = -1;
      }

      if(var1 >= 0 && var1 < this.field_96192_v.size()) {
         this.field_96189_n = ((McoServer)this.field_96192_v.get(var1)).field_96408_a;
      }

   }

   public void func_102018_a(long p_102018_1_) {
      this.field_96189_n = -1L;
      this.field_102019_y = Long.valueOf(p_102018_1_);
   }

   private McoServer func_98086_a(long p_98086_1_) {
      McoClient var3 = new McoClient(this.field_73882_e.func_110432_I());

      try {
         return var3.func_98176_a(p_98086_1_);
      } catch (ExceptionMcoService var5) {
         this.field_73882_e.func_98033_al().func_98232_c(var5.toString());
      } catch (IOException var6) {
         this.field_73882_e.func_98033_al().func_98236_b("Realms: could not parse response");
      }

      return null;
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) {
      if(p_73869_2_ == 59) {
         this.field_73882_e.field_71474_y.field_80005_w = !this.field_73882_e.field_71474_y.field_80005_w;
         this.field_73882_e.field_71474_y.func_74303_b();
      } else {
         if(p_73869_2_ != 28 && p_73869_2_ != 156) {
            super.func_73869_a(p_73869_1_, p_73869_2_);
         } else {
            this.func_73875_a((GuiButton)this.field_73887_h.get(0));
         }

      }
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.field_96195_s = null;
      this.func_73873_v_();
      this.field_96186_b.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
      this.func_73732_a(this.field_73886_k, I18n.func_135053_a("mco.title"), this.field_73880_f / 2, 20, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
      if(this.field_96195_s != null) {
         this.func_96165_a(this.field_96195_s, p_73863_1_, p_73863_2_);
      }

      this.func_130038_b(p_73863_1_, p_73863_2_);
   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
      if(this.func_130037_c(p_73864_1_, p_73864_2_) && field_96194_t.func_130124_d() != 0) {
         GuiScreenPendingInvitation var4 = new GuiScreenPendingInvitation(this);
         this.field_73882_e.func_71373_a(var4);
      }

   }

   private void func_130038_b(int p_130038_1_, int p_130038_2_) {
      int var3 = field_96194_t.func_130124_d();
      boolean var4 = this.func_130037_c(p_130038_1_, p_130038_2_);
      this.field_73882_e.func_110434_K().func_110577_a(field_130039_a);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPushMatrix();
      this.func_73729_b(this.field_73880_f / 2 + 58, 15, var4?166:182, 22, 16, 16);
      GL11.glPopMatrix();
      int var5;
      int var6;
      if(var3 != 0) {
         var5 = 198 + (Math.min(var3, 6) - 1) * 8;
         var6 = (int)(Math.max(0.0F, Math.max(MathHelper.func_76126_a((float)(10 + this.field_104044_y) * 0.57F), MathHelper.func_76134_b((float)this.field_104044_y * 0.35F))) * -6.0F);
         this.field_73882_e.func_110434_K().func_110577_a(field_130039_a);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPushMatrix();
         this.func_73729_b(this.field_73880_f / 2 + 58 + 4, 19 + var6, var5, 22, 8, 8);
         GL11.glPopMatrix();
      }

      if(var4 && var3 != 0) {
         var5 = p_130038_1_ + 12;
         var6 = p_130038_2_ - 12;
         String var7 = I18n.func_135053_a("mco.invites.pending");
         int var8 = this.field_73886_k.func_78256_a(var7);
         this.func_73733_a(var5 - 3, var6 - 3, var5 + var8 + 3, var6 + 8 + 3, -1073741824, -1073741824);
         this.field_73886_k.func_78261_a(var7, var5, var6, -1);
      }

   }

   private boolean func_130037_c(int p_130037_1_, int p_130037_2_) {
      int var3 = this.field_73880_f / 2 + 56;
      int var4 = this.field_73880_f / 2 + 78;
      byte var5 = 13;
      byte var6 = 27;
      return var3 <= p_130037_1_ && p_130037_1_ <= var4 && var5 <= p_130037_2_ && p_130037_2_ <= var6;
   }

   private void func_140032_e(long p_140032_1_) {
      McoServer var3 = this.func_140030_b(p_140032_1_);
      if(var3 != null) {
         field_96194_t.func_98248_d();
         GuiScreenLongRunningTask var4 = new GuiScreenLongRunningTask(this.field_73882_e, this, new TaskOnlineConnect(this, var3));
         var4.func_98117_g();
         this.field_73882_e.func_71373_a(var4);
      }

   }

   private void func_101008_c(int p_101008_1_, int p_101008_2_, int p_101008_3_, int p_101008_4_) {
      this.field_73882_e.func_110434_K().func_110577_a(field_130039_a);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPushMatrix();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      this.func_73729_b(p_101008_1_ * 2, p_101008_2_ * 2, 191, 0, 16, 15);
      GL11.glPopMatrix();
      if(p_101008_3_ >= p_101008_1_ && p_101008_3_ <= p_101008_1_ + 9 && p_101008_4_ >= p_101008_2_ && p_101008_4_ <= p_101008_2_ + 9) {
         this.field_96195_s = I18n.func_135053_a("mco.selectServer.expired");
      }

   }

   private void func_104039_b(int p_104039_1_, int p_104039_2_, int p_104039_3_, int p_104039_4_, int p_104039_5_) {
      if(this.field_104044_y % 20 < 10) {
         this.field_73882_e.func_110434_K().func_110577_a(field_130039_a);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPushMatrix();
         GL11.glScalef(0.5F, 0.5F, 0.5F);
         this.func_73729_b(p_104039_1_ * 2, p_104039_2_ * 2, 207, 0, 16, 15);
         GL11.glPopMatrix();
      }

      if(p_104039_3_ >= p_104039_1_ && p_104039_3_ <= p_104039_1_ + 9 && p_104039_4_ >= p_104039_2_ && p_104039_4_ <= p_104039_2_ + 9) {
         if(p_104039_5_ == 0) {
            this.field_96195_s = I18n.func_135053_a("mco.selectServer.expires.soon");
         } else if(p_104039_5_ == 1) {
            this.field_96195_s = I18n.func_135053_a("mco.selectServer.expires.day");
         } else {
            this.field_96195_s = I18n.func_135052_a("mco.selectServer.expires.days", new Object[]{Integer.valueOf(p_104039_5_)});
         }
      }

   }

   private void func_101006_d(int p_101006_1_, int p_101006_2_, int p_101006_3_, int p_101006_4_) {
      this.field_73882_e.func_110434_K().func_110577_a(field_130039_a);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPushMatrix();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      this.func_73729_b(p_101006_1_ * 2, p_101006_2_ * 2, 207, 0, 16, 15);
      GL11.glPopMatrix();
      if(p_101006_3_ >= p_101006_1_ && p_101006_3_ <= p_101006_1_ + 9 && p_101006_4_ >= p_101006_2_ && p_101006_4_ <= p_101006_2_ + 9) {
         this.field_96195_s = I18n.func_135053_a("mco.selectServer.open");
      }

   }

   private void func_101001_e(int p_101001_1_, int p_101001_2_, int p_101001_3_, int p_101001_4_) {
      this.field_73882_e.func_110434_K().func_110577_a(field_130039_a);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPushMatrix();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      this.func_73729_b(p_101001_1_ * 2, p_101001_2_ * 2, 223, 0, 16, 15);
      GL11.glPopMatrix();
      if(p_101001_3_ >= p_101001_1_ && p_101001_3_ <= p_101001_1_ + 9 && p_101001_4_ >= p_101001_2_ && p_101001_4_ <= p_101001_2_ + 9) {
         this.field_96195_s = I18n.func_135053_a("mco.selectServer.closed");
      }

   }

   protected void func_96165_a(String p_96165_1_, int p_96165_2_, int p_96165_3_) {
      if(p_96165_1_ != null) {
         int var4 = p_96165_2_ + 12;
         int var5 = p_96165_3_ - 12;
         int var6 = this.field_73886_k.func_78256_a(p_96165_1_);
         this.func_73733_a(var4 - 3, var5 - 3, var4 + var6 + 3, var5 + 8 + 3, -1073741824, -1073741824);
         this.field_73886_k.func_78261_a(p_96165_1_, var4, var5, -1);
      }
   }

   private void func_96174_a(McoServer p_96174_1_) throws IOException {
      if(p_96174_1_.field_96414_k.equals("")) {
         p_96174_1_.field_96414_k = EnumChatFormatting.GRAY + "" + 0;
      }

      p_96174_1_.field_96415_h = 74;
      ServerAddress var2 = ServerAddress.func_78860_a(p_96174_1_.field_96403_g);
      Socket var3 = null;
      DataInputStream var4 = null;
      DataOutputStream var5 = null;

      try {
         var3 = new Socket();
         var3.setSoTimeout(3000);
         var3.setTcpNoDelay(true);
         var3.setTrafficClass(18);
         var3.connect(new InetSocketAddress(var2.func_78861_a(), var2.func_78864_b()), 3000);
         var4 = new DataInputStream(var3.getInputStream());
         var5 = new DataOutputStream(var3.getOutputStream());
         var5.write(254);
         var5.write(1);
         if(var4.read() != 255) {
            throw new IOException("Bad message");
         }

         String var6 = Packet.func_73282_a(var4, 256);
         char[] var7 = var6.toCharArray();

         for(int var8 = 0; var8 < var7.length; ++var8) {
            if(var7[var8] != 167 && var7[var8] != 0 && ChatAllowedCharacters.field_71568_a.indexOf(var7[var8]) < 0) {
               var7[var8] = 63;
            }
         }

         var6 = new String(var7);
         int var9;
         int var10;
         String[] var27;
         if(var6.startsWith("\u00a7") && var6.length() > 1) {
            var27 = var6.substring(1).split("\u0000");
            if(MathHelper.func_82715_a(var27[0], 0) == 1) {
               p_96174_1_.field_96415_h = MathHelper.func_82715_a(var27[1], p_96174_1_.field_96415_h);
               var9 = MathHelper.func_82715_a(var27[4], 0);
               var10 = MathHelper.func_82715_a(var27[5], 0);
               if(var9 >= 0 && var10 >= 0) {
                  p_96174_1_.field_96414_k = EnumChatFormatting.GRAY + "" + var9;
               } else {
                  p_96174_1_.field_96414_k = "" + EnumChatFormatting.DARK_GRAY + "???";
               }
            } else {
               p_96174_1_.field_96415_h = 75;
               p_96174_1_.field_96414_k = "" + EnumChatFormatting.DARK_GRAY + "???";
            }
         } else {
            var27 = var6.split("\u00a7");
            var6 = var27[0];
            var9 = -1;
            var10 = -1;

            try {
               var9 = Integer.parseInt(var27[1]);
               var10 = Integer.parseInt(var27[2]);
            } catch (Exception var25) {
               ;
            }

            p_96174_1_.field_96407_c = EnumChatFormatting.GRAY + var6;
            if(var9 >= 0 && var10 > 0) {
               p_96174_1_.field_96414_k = EnumChatFormatting.GRAY + "" + var9;
            } else {
               p_96174_1_.field_96414_k = "" + EnumChatFormatting.DARK_GRAY + "???";
            }

            p_96174_1_.field_96415_h = 73;
         }
      } finally {
         try {
            if(var4 != null) {
               var4.close();
            }
         } catch (Throwable var24) {
            ;
         }

         try {
            if(var5 != null) {
               var5.close();
            }
         } catch (Throwable var23) {
            ;
         }

         try {
            if(var3 != null) {
               var3.close();
            }
         } catch (Throwable var22) {
            ;
         }

      }

   }

   // $FF: synthetic method
   static long func_140041_a(GuiScreenOnlineServers p_140041_0_) {
      return p_140041_0_.field_96189_n;
   }

   // $FF: synthetic method
   static McoServer func_140011_a(GuiScreenOnlineServers p_140011_0_, long p_140011_1_) {
      return p_140011_0_.func_140030_b(p_140011_1_);
   }

   // $FF: synthetic method
   static Minecraft func_98075_b(GuiScreenOnlineServers p_98075_0_) {
      return p_98075_0_.field_73882_e;
   }

   // $FF: synthetic method
   static McoServerList func_140040_h() {
      return field_96194_t;
   }

   // $FF: synthetic method
   static List func_140013_c(GuiScreenOnlineServers p_140013_0_) {
      return p_140013_0_.field_96192_v;
   }

   // $FF: synthetic method
   static void func_140017_d(GuiScreenOnlineServers p_140017_0_) {
      p_140017_0_.func_140012_t();
   }

   // $FF: synthetic method
   static Minecraft func_98076_f(GuiScreenOnlineServers p_98076_0_) {
      return p_98076_0_.field_73882_e;
   }

   // $FF: synthetic method
   static Minecraft func_140037_f(GuiScreenOnlineServers p_140037_0_) {
      return p_140037_0_.field_73882_e;
   }

   // $FF: synthetic method
   static long func_140036_b(GuiScreenOnlineServers p_140036_0_, long p_140036_1_) {
      return p_140036_0_.field_96189_n = p_140036_1_;
   }

   // $FF: synthetic method
   static Minecraft func_140015_g(GuiScreenOnlineServers p_140015_0_) {
      return p_140015_0_.field_73882_e;
   }

   // $FF: synthetic method
   static GuiButton func_140038_h(GuiScreenOnlineServers p_140038_0_) {
      return p_140038_0_.field_96190_o;
   }

   // $FF: synthetic method
   static GuiButton func_140033_i(GuiScreenOnlineServers p_140033_0_) {
      return p_140033_0_.field_96196_r;
   }

   // $FF: synthetic method
   static void func_140008_c(GuiScreenOnlineServers p_140008_0_, long p_140008_1_) {
      p_140008_0_.func_140032_e(p_140008_1_);
   }

   // $FF: synthetic method
   static int func_140027_d(GuiScreenOnlineServers p_140027_0_, long p_140027_1_) {
      return p_140027_0_.func_140009_c(p_140027_1_);
   }

   // $FF: synthetic method
   static Minecraft func_104032_j(GuiScreenOnlineServers p_104032_0_) {
      return p_104032_0_.field_73882_e;
   }

   // $FF: synthetic method
   static FontRenderer func_140023_k(GuiScreenOnlineServers p_140023_0_) {
      return p_140023_0_.field_73886_k;
   }

   // $FF: synthetic method
   static void func_104031_c(GuiScreenOnlineServers p_104031_0_, int p_104031_1_, int p_104031_2_, int p_104031_3_, int p_104031_4_) {
      p_104031_0_.func_101008_c(p_104031_1_, p_104031_2_, p_104031_3_, p_104031_4_);
   }

   // $FF: synthetic method
   static void func_140035_b(GuiScreenOnlineServers p_140035_0_, int p_140035_1_, int p_140035_2_, int p_140035_3_, int p_140035_4_) {
      p_140035_0_.func_101001_e(p_140035_1_, p_140035_2_, p_140035_3_, p_140035_4_);
   }

   // $FF: synthetic method
   static Minecraft func_140014_l(GuiScreenOnlineServers p_140014_0_) {
      return p_140014_0_.field_73882_e;
   }

   // $FF: synthetic method
   static void func_140031_a(GuiScreenOnlineServers p_140031_0_, int p_140031_1_, int p_140031_2_, int p_140031_3_, int p_140031_4_, int p_140031_5_) {
      p_140031_0_.func_104039_b(p_140031_1_, p_140031_2_, p_140031_3_, p_140031_4_, p_140031_5_);
   }

   // $FF: synthetic method
   static void func_140020_c(GuiScreenOnlineServers p_140020_0_, int p_140020_1_, int p_140020_2_, int p_140020_3_, int p_140020_4_) {
      p_140020_0_.func_101006_d(p_140020_1_, p_140020_2_, p_140020_3_, p_140020_4_);
   }

   // $FF: synthetic method
   static FontRenderer func_140039_m(GuiScreenOnlineServers p_140039_0_) {
      return p_140039_0_.field_73886_k;
   }

   // $FF: synthetic method
   static FontRenderer func_98079_k(GuiScreenOnlineServers p_98079_0_) {
      return p_98079_0_.field_73886_k;
   }

   // $FF: synthetic method
   static Object func_140029_i() {
      return field_96185_d;
   }

   // $FF: synthetic method
   static int func_140018_j() {
      return field_96187_c;
   }

   // $FF: synthetic method
   static int func_140016_k() {
      return field_96187_c++;
   }

   // $FF: synthetic method
   static void func_140024_a(GuiScreenOnlineServers p_140024_0_, McoServer p_140024_1_) throws IOException {
      p_140024_0_.func_96174_a(p_140024_1_);
   }

   // $FF: synthetic method
   static int func_140021_r() {
      return field_96187_c--;
   }

   // $FF: synthetic method
   static FontRenderer func_110402_q(GuiScreenOnlineServers p_110402_0_) {
      return p_110402_0_.field_73886_k;
   }

   // $FF: synthetic method
   static FontRenderer func_140010_p(GuiScreenOnlineServers p_140010_0_) {
      return p_140010_0_.field_73886_k;
   }

   // $FF: synthetic method
   static Minecraft func_142023_q(GuiScreenOnlineServers p_142023_0_) {
      return p_142023_0_.field_73882_e;
   }

}
