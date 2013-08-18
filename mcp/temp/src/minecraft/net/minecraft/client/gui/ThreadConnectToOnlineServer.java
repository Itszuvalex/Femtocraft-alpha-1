package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import net.minecraft.client.gui.GuiScreenOnlineServers;
import net.minecraft.client.gui.GuiSlotOnlineServerList;
import net.minecraft.client.mco.McoServer;

@SideOnly(Side.CLIENT)
class ThreadConnectToOnlineServer extends Thread {

   // $FF: synthetic field
   final McoServer field_96597_a;
   // $FF: synthetic field
   final GuiSlotOnlineServerList field_96596_b;


   ThreadConnectToOnlineServer(GuiSlotOnlineServerList p_i1121_1_, McoServer p_i1121_2_) {
      this.field_96596_b = p_i1121_1_;
      this.field_96597_a = p_i1121_2_;
   }

   public void run() {
      boolean var27 = false;

      label194: {
         label195: {
            label196: {
               label197: {
                  label198: {
                     try {
                        var27 = true;
                        if(!this.field_96597_a.field_96411_l) {
                           this.field_96597_a.field_96411_l = true;
                           this.field_96597_a.field_96412_m = -2L;
                           this.field_96597_a.field_96414_k = "";
                           GuiScreenOnlineServers.func_140016_k();
                           long var1 = System.nanoTime();
                           GuiScreenOnlineServers.func_140024_a(this.field_96596_b.field_96294_a, this.field_96597_a);
                           long var3 = System.nanoTime();
                           this.field_96597_a.field_96412_m = (var3 - var1) / 1000000L;
                           var27 = false;
                        } else if(this.field_96597_a.field_102022_m) {
                           this.field_96597_a.field_102022_m = false;
                           GuiScreenOnlineServers.func_140024_a(this.field_96596_b.field_96294_a, this.field_96597_a);
                           var27 = false;
                        } else {
                           var27 = false;
                        }
                        break label194;
                     } catch (UnknownHostException var35) {
                        this.field_96597_a.field_96412_m = -1L;
                        var27 = false;
                        break label195;
                     } catch (SocketTimeoutException var36) {
                        this.field_96597_a.field_96412_m = -1L;
                        var27 = false;
                        break label196;
                     } catch (ConnectException var37) {
                        this.field_96597_a.field_96412_m = -1L;
                        var27 = false;
                        break label198;
                     } catch (IOException var38) {
                        this.field_96597_a.field_96412_m = -1L;
                        var27 = false;
                     } catch (Exception var39) {
                        this.field_96597_a.field_96412_m = -1L;
                        var27 = false;
                        break label197;
                     } finally {
                        if(var27) {
                           synchronized(GuiScreenOnlineServers.func_140029_i()) {
                              GuiScreenOnlineServers.func_140021_r();
                           }
                        }
                     }

                     synchronized(GuiScreenOnlineServers.func_140029_i()) {
                        GuiScreenOnlineServers.func_140021_r();
                        return;
                     }
                  }

                  synchronized(GuiScreenOnlineServers.func_140029_i()) {
                     GuiScreenOnlineServers.func_140021_r();
                     return;
                  }
               }

               synchronized(GuiScreenOnlineServers.func_140029_i()) {
                  GuiScreenOnlineServers.func_140021_r();
                  return;
               }
            }

            synchronized(GuiScreenOnlineServers.func_140029_i()) {
               GuiScreenOnlineServers.func_140021_r();
               return;
            }
         }

         synchronized(GuiScreenOnlineServers.func_140029_i()) {
            GuiScreenOnlineServers.func_140021_r();
            return;
         }
      }

      synchronized(GuiScreenOnlineServers.func_140029_i()) {
         GuiScreenOnlineServers.func_140021_r();
      }

   }
}
