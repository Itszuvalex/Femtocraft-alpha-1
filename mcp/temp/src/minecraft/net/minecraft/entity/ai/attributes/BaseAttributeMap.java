package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.server.management.LowerStringMap;

public abstract class BaseAttributeMap {

   protected final Map field_111154_a = new HashMap();
   protected final Map field_111153_b = new LowerStringMap();


   public AttributeInstance func_111151_a(Attribute p_111151_1_) {
      return (AttributeInstance)this.field_111154_a.get(p_111151_1_);
   }

   public AttributeInstance func_111152_a(String p_111152_1_) {
      return (AttributeInstance)this.field_111153_b.get(p_111152_1_);
   }

   public abstract AttributeInstance func_111150_b(Attribute var1);

   public Collection func_111146_a() {
      return this.field_111153_b.values();
   }

   public void func_111149_a(ModifiableAttributeInstance p_111149_1_) {}

   public void func_111148_a(Multimap p_111148_1_) {
      Iterator var2 = p_111148_1_.entries().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         AttributeInstance var4 = this.func_111152_a((String)var3.getKey());
         if(var4 != null) {
            var4.func_111124_b((AttributeModifier)var3.getValue());
         }
      }

   }

   public void func_111147_b(Multimap p_111147_1_) {
      Iterator var2 = p_111147_1_.entries().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         AttributeInstance var4 = this.func_111152_a((String)var3.getKey());
         if(var4 != null) {
            var4.func_111124_b((AttributeModifier)var3.getValue());
            var4.func_111121_a((AttributeModifier)var3.getValue());
         }
      }

   }
}
