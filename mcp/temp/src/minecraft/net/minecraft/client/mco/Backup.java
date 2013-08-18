package net.minecraft.client.mco;

import argo.jdom.JsonNode;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Date;
import net.minecraft.util.ValueObject;

@SideOnly(Side.CLIENT)
public class Backup extends ValueObject {

   public String field_110727_a;
   public Date field_110725_b;
   public long field_110726_c;


   public static Backup func_110724_a(JsonNode p_110724_0_) {
      Backup var1 = new Backup();

      try {
         var1.field_110727_a = p_110724_0_.getStringValue(new Object[]{"backupId"});
         var1.field_110725_b = new Date(Long.parseLong(p_110724_0_.getNumberValue(new Object[]{"lastModifiedDate"})));
         var1.field_110726_c = Long.parseLong(p_110724_0_.getNumberValue(new Object[]{"size"}));
      } catch (IllegalArgumentException var3) {
         ;
      }

      return var1;
   }
}
