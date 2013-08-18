package net.minecraft.client.multiplayer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;

@SideOnly(Side.CLIENT)
public class ServerData {

   public String field_78847_a;
   public String field_78845_b;
   public String field_78846_c;
   public String field_78843_d;
   public long field_78844_e;
   public int field_82821_f = 74;
   public String field_82822_g = "1.6.2";
   public boolean field_78841_f;
   private boolean field_78842_g = true;
   private boolean field_78848_h;
   private boolean field_82823_k;


   public ServerData(String p_i1193_1_, String p_i1193_2_) {
      this.field_78847_a = p_i1193_1_;
      this.field_78845_b = p_i1193_2_;
   }

   public NBTTagCompound func_78836_a() {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.func_74778_a("name", this.field_78847_a);
      var1.func_74778_a("ip", this.field_78845_b);
      var1.func_74757_a("hideAddress", this.field_82823_k);
      if(!this.field_78842_g) {
         var1.func_74757_a("acceptTextures", this.field_78848_h);
      }

      return var1;
   }

   public void func_78838_a(boolean p_78838_1_) {
      this.field_78848_h = p_78838_1_;
      this.field_78842_g = false;
   }

   public boolean func_82820_d() {
      return this.field_82823_k;
   }

   public void func_82819_b(boolean p_82819_1_) {
      this.field_82823_k = p_82819_1_;
   }

   public static ServerData func_78837_a(NBTTagCompound p_78837_0_) {
      ServerData var1 = new ServerData(p_78837_0_.func_74779_i("name"), p_78837_0_.func_74779_i("ip"));
      var1.field_82823_k = p_78837_0_.func_74767_n("hideAddress");
      if(p_78837_0_.func_74764_b("acceptTextures")) {
         var1.func_78838_a(p_78837_0_.func_74767_n("acceptTextures"));
      }

      return var1;
   }
}
