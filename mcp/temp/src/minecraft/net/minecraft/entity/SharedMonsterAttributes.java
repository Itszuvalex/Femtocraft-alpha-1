package net.minecraft.entity;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.logging.ILogAgent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class SharedMonsterAttributes {

   public static final Attribute field_111267_a = (new RangedAttribute("generic.maxHealth", 20.0D, 0.0D, Double.MAX_VALUE)).func_111117_a("Max Health").func_111112_a(true);
   public static final Attribute field_111265_b = (new RangedAttribute("generic.followRange", 32.0D, 0.0D, 2048.0D)).func_111117_a("Follow Range");
   public static final Attribute field_111266_c = (new RangedAttribute("generic.knockbackResistance", 0.0D, 0.0D, 1.0D)).func_111117_a("Knockback Resistance");
   public static final Attribute field_111263_d = (new RangedAttribute("generic.movementSpeed", 0.699999988079071D, 0.0D, Double.MAX_VALUE)).func_111117_a("Movement Speed").func_111112_a(true);
   public static final Attribute field_111264_e = new RangedAttribute("generic.attackDamage", 2.0D, 0.0D, Double.MAX_VALUE);


   public static NBTTagList func_111257_a(BaseAttributeMap p_111257_0_) {
      NBTTagList var1 = new NBTTagList();
      Iterator var2 = p_111257_0_.func_111146_a().iterator();

      while(var2.hasNext()) {
         AttributeInstance var3 = (AttributeInstance)var2.next();
         var1.func_74742_a(func_111261_a(var3));
      }

      return var1;
   }

   private static NBTTagCompound func_111261_a(AttributeInstance p_111261_0_) {
      NBTTagCompound var1 = new NBTTagCompound();
      Attribute var2 = p_111261_0_.func_111123_a();
      var1.func_74778_a("Name", var2.func_111108_a());
      var1.func_74780_a("Base", p_111261_0_.func_111125_b());
      Collection var3 = p_111261_0_.func_111122_c();
      if(var3 != null && !var3.isEmpty()) {
         NBTTagList var4 = new NBTTagList();
         Iterator var5 = var3.iterator();

         while(var5.hasNext()) {
            AttributeModifier var6 = (AttributeModifier)var5.next();
            if(var6.func_111165_e()) {
               var4.func_74742_a(func_111262_a(var6));
            }
         }

         var1.func_74782_a("Modifiers", var4);
      }

      return var1;
   }

   private static NBTTagCompound func_111262_a(AttributeModifier p_111262_0_) {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.func_74778_a("Name", p_111262_0_.func_111166_b());
      var1.func_74780_a("Amount", p_111262_0_.func_111164_d());
      var1.func_74768_a("Operation", p_111262_0_.func_111169_c());
      var1.func_74772_a("UUIDMost", p_111262_0_.func_111167_a().getMostSignificantBits());
      var1.func_74772_a("UUIDLeast", p_111262_0_.func_111167_a().getLeastSignificantBits());
      return var1;
   }

   public static void func_111260_a(BaseAttributeMap p_111260_0_, NBTTagList p_111260_1_, ILogAgent p_111260_2_) {
      for(int var3 = 0; var3 < p_111260_1_.func_74745_c(); ++var3) {
         NBTTagCompound var4 = (NBTTagCompound)p_111260_1_.func_74743_b(var3);
         AttributeInstance var5 = p_111260_0_.func_111152_a(var4.func_74779_i("Name"));
         if(var5 != null) {
            func_111258_a(var5, var4);
         } else if(p_111260_2_ != null) {
            p_111260_2_.func_98236_b("Ignoring unknown attribute \'" + var4.func_74779_i("Name") + "\'");
         }
      }

   }

   private static void func_111258_a(AttributeInstance p_111258_0_, NBTTagCompound p_111258_1_) {
      p_111258_0_.func_111128_a(p_111258_1_.func_74769_h("Base"));
      if(p_111258_1_.func_74764_b("Modifiers")) {
         NBTTagList var2 = p_111258_1_.func_74761_m("Modifiers");

         for(int var3 = 0; var3 < var2.func_74745_c(); ++var3) {
            AttributeModifier var4 = func_111259_a((NBTTagCompound)var2.func_74743_b(var3));
            AttributeModifier var5 = p_111258_0_.func_111127_a(var4.func_111167_a());
            if(var5 != null) {
               p_111258_0_.func_111124_b(var5);
            }

            p_111258_0_.func_111121_a(var4);
         }
      }

   }

   public static AttributeModifier func_111259_a(NBTTagCompound p_111259_0_) {
      UUID var1 = new UUID(p_111259_0_.func_74763_f("UUIDMost"), p_111259_0_.func_74763_f("UUIDLeast"));
      return new AttributeModifier(var1, p_111259_0_.func_74779_i("Name"), p_111259_0_.func_74769_h("Amount"), p_111259_0_.func_74762_e("Operation"));
   }

}
