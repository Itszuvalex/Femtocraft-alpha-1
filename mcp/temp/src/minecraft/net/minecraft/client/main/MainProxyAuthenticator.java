package net.minecraft.client.main;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

@SideOnly(Side.CLIENT)
public final class MainProxyAuthenticator extends Authenticator {

   // $FF: synthetic field
   final String field_111237_a;
   // $FF: synthetic field
   final String field_111236_b;


   public MainProxyAuthenticator(String p_i1137_1_, String p_i1137_2_) {
      this.field_111237_a = p_i1137_1_;
      this.field_111236_b = p_i1137_2_;
   }

   protected PasswordAuthentication getPasswordAuthentication() {
      return new PasswordAuthentication(this.field_111237_a, this.field_111236_b.toCharArray());
   }
}
