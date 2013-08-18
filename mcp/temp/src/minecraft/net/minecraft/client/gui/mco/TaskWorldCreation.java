package net.minecraft.client.gui.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import net.minecraft.client.gui.TaskLongRunning;
import net.minecraft.client.gui.mco.GuiScreenCreateOnlineWorld;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.WorldTemplate;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
class TaskWorldCreation extends TaskLongRunning {

   private final String field_96589_c;
   private final String field_96587_d;
   private final String field_104065_f;
   private final WorldTemplate field_111253_f;
   // $FF: synthetic field
   final GuiScreenCreateOnlineWorld field_96590_a;


   public TaskWorldCreation(GuiScreenCreateOnlineWorld p_i1106_1_, String p_i1106_2_, String p_i1106_3_, String p_i1106_4_, WorldTemplate p_i1106_5_) {
      this.field_96590_a = p_i1106_1_;
      this.field_96589_c = p_i1106_2_;
      this.field_96587_d = p_i1106_3_;
      this.field_104065_f = p_i1106_4_;
      this.field_111253_f = p_i1106_5_;
   }

   public void run() {
      String var1 = I18n.func_135053_a("mco.create.world.wait");
      this.func_96576_b(var1);
      McoClient var2 = new McoClient(GuiScreenCreateOnlineWorld.func_96248_a(this.field_96590_a).func_110432_I());

      try {
         if(this.field_111253_f != null) {
            var2.func_96386_a(this.field_96589_c, this.field_96587_d, this.field_104065_f, this.field_111253_f.field_110734_a);
         } else {
            var2.func_96386_a(this.field_96589_c, this.field_96587_d, this.field_104065_f, "-1");
         }

         GuiScreenCreateOnlineWorld.func_96246_c(this.field_96590_a).func_71373_a(GuiScreenCreateOnlineWorld.func_96247_b(this.field_96590_a));
      } catch (ExceptionMcoService var4) {
         GuiScreenCreateOnlineWorld.func_130026_d(this.field_96590_a).func_98033_al().func_98232_c(var4.toString());
         this.func_96575_a(var4.toString());
      } catch (UnsupportedEncodingException var5) {
         GuiScreenCreateOnlineWorld.func_130027_e(this.field_96590_a).func_98033_al().func_98236_b("Realms: " + var5.getLocalizedMessage());
         this.func_96575_a(var5.getLocalizedMessage());
      } catch (IOException var6) {
         GuiScreenCreateOnlineWorld.func_130028_f(this.field_96590_a).func_98033_al().func_98236_b("Realms: could not parse response");
         this.func_96575_a(var6.getLocalizedMessage());
      } catch (Exception var7) {
         this.func_96575_a(var7.getLocalizedMessage());
      }

   }
}
