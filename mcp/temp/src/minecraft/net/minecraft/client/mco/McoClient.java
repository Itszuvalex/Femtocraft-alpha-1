package net.minecraft.client.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.client.mco.BackupList;
import net.minecraft.client.mco.ExceptionMcoHttp;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.ExceptionRetryCall;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.mco.McoServerAddress;
import net.minecraft.client.mco.PendingInvitesList;
import net.minecraft.client.mco.Request;
import net.minecraft.client.mco.ValueObjectList;
import net.minecraft.client.mco.ValueObjectSubscription;
import net.minecraft.client.mco.WorldTemplateList;
import net.minecraft.util.Session;

@SideOnly(Side.CLIENT)
public class McoClient {

   private final String field_96390_a;
   private final String field_100007_c;
   private static String field_96388_b = "https://mcoapi.minecraft.net/";


   public McoClient(Session p_i1138_1_) {
      this.field_96390_a = p_i1138_1_.func_111286_b();
      this.field_100007_c = p_i1138_1_.func_111285_a();
   }

   public ValueObjectList func_96382_a() throws ExceptionMcoService, IOException {
      String var1 = this.func_96377_a(Request.func_96358_a(field_96388_b + "worlds"));
      return ValueObjectList.func_98161_a(var1);
   }

   public McoServer func_98176_a(long p_98176_1_) throws ExceptionMcoService, IOException {
      String var3 = this.func_96377_a(Request.func_96358_a(field_96388_b + "worlds" + "/$ID".replace("$ID", String.valueOf(p_98176_1_))));
      return McoServer.func_98165_c(var3);
   }

   public McoServerAddress func_96374_a(long p_96374_1_) throws ExceptionMcoService, IOException {
      String var3 = field_96388_b + "worlds" + "/$ID/join".replace("$ID", "" + p_96374_1_);
      String var4 = this.func_96377_a(Request.func_96358_a(var3));
      return McoServerAddress.func_98162_a(var4);
   }

   public void func_96386_a(String p_96386_1_, String p_96386_2_, String p_96386_3_, String p_96386_4_) throws ExceptionMcoService, UnsupportedEncodingException {
      StringBuilder var5 = new StringBuilder();
      var5.append(field_96388_b).append("worlds").append("/$NAME/$LOCATION_ID".replace("$NAME", this.func_96380_a(p_96386_1_)));
      HashMap var6 = new HashMap();
      if(p_96386_2_ != null && !p_96386_2_.trim().equals("")) {
         var6.put("motd", p_96386_2_);
      }

      if(p_96386_3_ != null && !p_96386_3_.equals("")) {
         var6.put("seed", p_96386_3_);
      }

      var6.put("template", p_96386_4_);
      if(!var6.isEmpty()) {
         boolean var7 = true;

         Entry var9;
         for(Iterator var8 = var6.entrySet().iterator(); var8.hasNext(); var5.append((String)var9.getKey()).append("=").append(this.func_96380_a((String)var9.getValue()))) {
            var9 = (Entry)var8.next();
            if(var7) {
               var5.append("?");
               var7 = false;
            } else {
               var5.append("&");
            }
         }
      }

      this.func_96377_a(Request.func_104064_a(var5.toString(), "", 5000, 30000));
   }

   public Boolean func_96375_b() throws ExceptionMcoService, IOException {
      String var1 = field_96388_b + "mco" + "/available";
      String var2 = this.func_96377_a(Request.func_96358_a(var1));
      return Boolean.valueOf(var2);
   }

   public Boolean func_140054_c() throws ExceptionMcoService, IOException {
      String var1 = field_96388_b + "mco" + "/client/outdated";
      String var2 = this.func_96377_a(Request.func_96358_a(var1));
      return Boolean.valueOf(var2);
   }

   public int func_96379_c() throws ExceptionMcoService {
      String var1 = field_96388_b + "payments" + "/unused";
      String var2 = this.func_96377_a(Request.func_96358_a(var1));
      return Integer.valueOf(var2).intValue();
   }

   public void func_96381_a(long p_96381_1_, String p_96381_3_) throws ExceptionMcoService {
      String var4 = field_96388_b + "invites" + "/$WORLD_ID/invite/$USER_NAME".replace("$WORLD_ID", String.valueOf(p_96381_1_)).replace("$USER_NAME", p_96381_3_);
      this.func_96377_a(Request.func_96355_b(var4));
   }

   public void func_140055_c(long p_140055_1_) throws ExceptionMcoService {
      String var3 = field_96388_b + "invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_140055_1_));
      this.func_96377_a(Request.func_96355_b(var3));
   }

   public McoServer func_96387_b(long p_96387_1_, String p_96387_3_) throws ExceptionMcoService, IOException {
      String var4 = field_96388_b + "invites" + "/$WORLD_ID/invite/$USER_NAME".replace("$WORLD_ID", String.valueOf(p_96387_1_)).replace("$USER_NAME", p_96387_3_);
      String var5 = this.func_96377_a(Request.func_96361_b(var4, ""));
      return McoServer.func_98165_c(var5);
   }

   public BackupList func_111232_c(long p_111232_1_) throws ExceptionMcoService {
      String var3 = field_96388_b + "worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(p_111232_1_));
      String var4 = this.func_96377_a(Request.func_96358_a(var3));
      return BackupList.func_111222_a(var4);
   }

   public void func_96384_a(long p_96384_1_, String p_96384_3_, String p_96384_4_, int p_96384_5_, int p_96384_6_) throws ExceptionMcoService, UnsupportedEncodingException {
      StringBuilder var7 = new StringBuilder();
      var7.append(field_96388_b).append("worlds").append("/$WORLD_ID/$NAME".replace("$WORLD_ID", String.valueOf(p_96384_1_)).replace("$NAME", this.func_96380_a(p_96384_3_)));
      if(p_96384_4_ != null && !p_96384_4_.trim().equals("")) {
         var7.append("?motd=").append(this.func_96380_a(p_96384_4_));
      }

      var7.append("&difficulty=").append(p_96384_5_).append("&gameMode=").append(p_96384_6_);
      this.func_96377_a(Request.func_96363_c(var7.toString(), ""));
   }

   public void func_111235_c(long p_111235_1_, String p_111235_3_) throws ExceptionMcoService {
      String var4 = field_96388_b + "worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(p_111235_1_)) + "?backupId=" + p_111235_3_;
      this.func_96377_a(Request.func_96363_c(var4, ""));
   }

   public WorldTemplateList func_111231_d() throws ExceptionMcoService {
      String var1 = field_96388_b + "worlds" + "/templates";
      String var2 = this.func_96377_a(Request.func_96358_a(var1));
      return WorldTemplateList.func_110735_a(var2);
   }

   public Boolean func_96383_b(long p_96383_1_) throws ExceptionMcoService, IOException {
      String var3 = field_96388_b + "worlds" + "/$WORLD_ID/open".replace("$WORLD_ID", String.valueOf(p_96383_1_));
      String var4 = this.func_96377_a(Request.func_96363_c(var3, ""));
      return Boolean.valueOf(var4);
   }

   public Boolean func_96378_c(long p_96378_1_) throws ExceptionMcoService, IOException {
      String var3 = field_96388_b + "worlds" + "/$WORLD_ID/close".replace("$WORLD_ID", String.valueOf(p_96378_1_));
      String var4 = this.func_96377_a(Request.func_96363_c(var3, ""));
      return Boolean.valueOf(var4);
   }

   public Boolean func_96376_d(long p_96376_1_, String p_96376_3_) throws ExceptionMcoService, UnsupportedEncodingException {
      StringBuilder var4 = new StringBuilder();
      var4.append(field_96388_b).append("worlds").append("/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(p_96376_1_)));
      if(p_96376_3_ != null && p_96376_3_.length() > 0) {
         var4.append("?seed=").append(this.func_96380_a(p_96376_3_));
      }

      String var5 = this.func_96377_a(Request.func_96353_a(var4.toString(), "", 30000, 80000));
      return Boolean.valueOf(var5);
   }

   public Boolean func_111233_e(long p_111233_1_, String p_111233_3_) throws ExceptionMcoService {
      StringBuilder var4 = new StringBuilder();
      var4.append(field_96388_b).append("worlds").append("/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(p_111233_1_)));
      if(p_111233_3_ != null) {
         var4.append("?template=").append(p_111233_3_);
      }

      String var5 = this.func_96377_a(Request.func_96353_a(var4.toString(), "", 30000, 80000));
      return Boolean.valueOf(var5);
   }

   public ValueObjectSubscription func_98177_f(long p_98177_1_) throws ExceptionMcoService, IOException {
      String var3 = this.func_96377_a(Request.func_96358_a(field_96388_b + "subscriptions" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_98177_1_))));
      return ValueObjectSubscription.func_98169_a(var3);
   }

   public int func_130106_e() throws ExceptionMcoService {
      String var1 = this.func_96377_a(Request.func_96358_a(field_96388_b + "invites" + "/count/pending"));
      return Integer.parseInt(var1);
   }

   public PendingInvitesList func_130108_f() throws ExceptionMcoService {
      String var1 = this.func_96377_a(Request.func_96358_a(field_96388_b + "invites" + "/pending"));
      return PendingInvitesList.func_130095_a(var1);
   }

   public void func_130107_a(String p_130107_1_) throws ExceptionMcoService {
      this.func_96377_a(Request.func_96363_c(field_96388_b + "invites" + "/accept/$INVITATION_ID".replace("$INVITATION_ID", p_130107_1_), ""));
   }

   public void func_130109_b(String p_130109_1_) throws ExceptionMcoService {
      this.func_96377_a(Request.func_96363_c(field_96388_b + "invites" + "/reject/$INVITATION_ID".replace("$INVITATION_ID", p_130109_1_), ""));
   }

   private String func_96380_a(String p_96380_1_) throws UnsupportedEncodingException {
      return URLEncoder.encode(p_96380_1_, "UTF-8");
   }

   private String func_96377_a(Request p_96377_1_) throws ExceptionMcoService {
      p_96377_1_.func_100006_a("sid", this.field_96390_a);
      p_96377_1_.func_100006_a("user", this.field_100007_c);
      p_96377_1_.func_100006_a("version", "1.6.2");

      try {
         int var2 = p_96377_1_.func_96362_a();
         if(var2 == 503) {
            int var3 = p_96377_1_.func_111221_b();
            throw new ExceptionRetryCall(var3);
         } else if(var2 >= 200 && var2 < 300) {
            return p_96377_1_.func_96364_c();
         } else {
            throw new ExceptionMcoService(p_96377_1_.func_96362_a(), p_96377_1_.func_96364_c(), p_96377_1_.func_130110_g());
         }
      } catch (ExceptionMcoHttp var4) {
         throw new ExceptionMcoService(500, "Server not available!", -1);
      }
   }

}
