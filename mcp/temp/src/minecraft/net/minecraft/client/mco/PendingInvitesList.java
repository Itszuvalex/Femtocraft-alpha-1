package net.minecraft.client.mco;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.saj.InvalidSyntaxException;
import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.mco.PendingInvite;
import net.minecraft.util.ValueObject;

@SideOnly(Side.CLIENT)
public class PendingInvitesList extends ValueObject {

   public List field_130096_a = Lists.newArrayList();


   public static PendingInvitesList func_130095_a(String p_130095_0_) {
      PendingInvitesList var1 = new PendingInvitesList();

      try {
         JsonRootNode var2 = (new JdomParser()).parse(p_130095_0_);
         if(var2.isArrayNode(new Object[]{"invites"})) {
            Iterator var3 = var2.getArrayNode(new Object[]{"invites"}).iterator();

            while(var3.hasNext()) {
               JsonNode var4 = (JsonNode)var3.next();
               var1.field_130096_a.add(PendingInvite.func_130091_a(var4));
            }
         }
      } catch (InvalidSyntaxException var5) {
         ;
      }

      return var1;
   }
}
