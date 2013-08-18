package net.minecraft.client.gui.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.TaskLongRunning;
import net.minecraft.client.gui.mco.GuiScreenResetWorld;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.WorldTemplate;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
class TaskResetWorld extends TaskLongRunning {

   private final long field_96591_c;
   private final String field_104066_d;
   private final WorldTemplate field_111252_e;
   // $FF: synthetic field
   final GuiScreenResetWorld field_96592_a;


   public TaskResetWorld(GuiScreenResetWorld p_i1134_1_, long p_i1134_2_, String p_i1134_4_, WorldTemplate p_i1134_5_) {
      this.field_96592_a = p_i1134_1_;
      this.field_96591_c = p_i1134_2_;
      this.field_104066_d = p_i1134_4_;
      this.field_111252_e = p_i1134_5_;
   }

   public void run() {
      McoClient var1 = new McoClient(this.func_96578_b().func_110432_I());
      String var2 = I18n.func_135053_a("mco.reset.world.resetting.screen.title");
      this.func_96576_b(var2);

      try {
         if(this.field_111252_e != null) {
            var1.func_111233_e(this.field_96591_c, this.field_111252_e.field_110734_a);
         } else {
            var1.func_96376_d(this.field_96591_c, this.field_104066_d);
         }

         GuiScreenResetWorld.func_96147_b(this.field_96592_a).func_71373_a(GuiScreenResetWorld.func_96148_a(this.field_96592_a));
      } catch (ExceptionMcoService var4) {
         GuiScreenResetWorld.func_130025_c(this.field_96592_a).func_98033_al().func_98232_c(var4.toString());
         this.func_96575_a(var4.toString());
      } catch (Exception var5) {
         GuiScreenResetWorld.func_130024_d(this.field_96592_a).func_98033_al().func_98236_b("Realms: ");
         this.func_96575_a(var5.toString());
      }

   }
}
