package net.minecraft.client.mco;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.saj.InvalidSyntaxException;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.mco.Backup;

@SideOnly(Side.CLIENT)
public class BackupList {

   public List field_111223_a;


   public static BackupList func_111222_a(String p_111222_0_) {
      BackupList var1 = new BackupList();
      var1.field_111223_a = new ArrayList();

      try {
         JsonRootNode var2 = (new JdomParser()).parse(p_111222_0_);
         if(var2.isArrayNode(new Object[]{"backups"})) {
            Iterator var3 = var2.getArrayNode(new Object[]{"backups"}).iterator();

            while(var3.hasNext()) {
               JsonNode var4 = (JsonNode)var3.next();
               var1.field_111223_a.add(Backup.func_110724_a(var4));
            }
         }
      } catch (InvalidSyntaxException var5) {
         ;
      } catch (IllegalArgumentException var6) {
         ;
      }

      return var1;
   }
}
