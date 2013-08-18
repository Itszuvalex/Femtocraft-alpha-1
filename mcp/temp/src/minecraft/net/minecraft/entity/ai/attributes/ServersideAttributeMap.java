package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.server.management.LowerStringMap;

public class ServersideAttributeMap extends BaseAttributeMap {

   private final Set field_111162_d = Sets.newHashSet();
   protected final Map field_111163_c = new LowerStringMap();


   public ModifiableAttributeInstance func_111159_c(Attribute p_111159_1_) {
      return (ModifiableAttributeInstance)super.func_111151_a(p_111159_1_);
   }

   public ModifiableAttributeInstance func_111158_b(String p_111158_1_) {
      AttributeInstance var2 = super.func_111152_a(p_111158_1_);
      if(var2 == null) {
         var2 = (AttributeInstance)this.field_111163_c.get(p_111158_1_);
      }

      return (ModifiableAttributeInstance)var2;
   }

   public AttributeInstance func_111150_b(Attribute p_111150_1_) {
      if(this.field_111153_b.containsKey(p_111150_1_.func_111108_a())) {
         throw new IllegalArgumentException("Attribute is already registered!");
      } else {
         ModifiableAttributeInstance var2 = new ModifiableAttributeInstance(this, p_111150_1_);
         this.field_111153_b.put(p_111150_1_.func_111108_a(), var2);
         if(p_111150_1_ instanceof RangedAttribute && ((RangedAttribute)p_111150_1_).func_111116_f() != null) {
            this.field_111163_c.put(((RangedAttribute)p_111150_1_).func_111116_f(), var2);
         }

         this.field_111154_a.put(p_111150_1_, var2);
         return var2;
      }
   }

   public void func_111149_a(ModifiableAttributeInstance p_111149_1_) {
      if(p_111149_1_.func_111123_a().func_111111_c()) {
         this.field_111162_d.add(p_111149_1_);
      }

   }

   public Set func_111161_b() {
      return this.field_111162_d;
   }

   public Collection func_111160_c() {
      HashSet var1 = Sets.newHashSet();
      Iterator var2 = this.func_111146_a().iterator();

      while(var2.hasNext()) {
         AttributeInstance var3 = (AttributeInstance)var2.next();
         if(var3.func_111123_a().func_111111_c()) {
            var1.add(var3);
         }
      }

      return var1;
   }

   // $FF: synthetic method
   public AttributeInstance func_111152_a(String p_111152_1_) {
      return this.func_111158_b(p_111152_1_);
   }

   // $FF: synthetic method
   public AttributeInstance func_111151_a(Attribute p_111151_1_) {
      return this.func_111159_c(p_111151_1_);
   }
}
