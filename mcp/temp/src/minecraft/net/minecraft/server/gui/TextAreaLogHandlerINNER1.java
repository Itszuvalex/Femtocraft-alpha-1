package net.minecraft.server.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import net.minecraft.server.gui.TextAreaLogHandler;

@SideOnly(Side.SERVER)
class TextAreaLogHandlerINNER1 extends Formatter {

   // $FF: synthetic field
   final TextAreaLogHandler field_120031_a;


   TextAreaLogHandlerINNER1(TextAreaLogHandler p_i2370_1_) {
      this.field_120031_a = p_i2370_1_;
   }

   public String format(LogRecord p_format_1_) {
      StringBuilder var2 = new StringBuilder();
      var2.append(" [").append(p_format_1_.getLevel().getName()).append("] ");
      var2.append(this.formatMessage(p_format_1_));
      var2.append('\n');
      Throwable var3 = p_format_1_.getThrown();
      if(var3 != null) {
         StringWriter var4 = new StringWriter();
         var3.printStackTrace(new PrintWriter(var4));
         var2.append(var4.toString());
      }

      return var2.toString();
   }
}
