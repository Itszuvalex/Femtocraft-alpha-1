package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.management.LowerStringMap;

public abstract class BaseAttributeMap
{
    protected final Map field_111154_a = new HashMap();
    protected final Map field_111153_b = new LowerStringMap();

    public AttributeInstance func_111151_a(Attribute par1Attribute)
    {
        return (AttributeInstance)this.field_111154_a.get(par1Attribute);
    }

    public AttributeInstance func_111152_a(String par1Str)
    {
        return (AttributeInstance)this.field_111153_b.get(par1Str);
    }

    public abstract AttributeInstance func_111150_b(Attribute attribute);

    public Collection func_111146_a()
    {
        return this.field_111153_b.values();
    }

    public void func_111149_a(ModifiableAttributeInstance par1ModifiableAttributeInstance) {}

    public void func_111148_a(Multimap par1Multimap)
    {
        Iterator iterator = par1Multimap.entries().iterator();

        while (iterator.hasNext())
        {
            Entry entry = (Entry)iterator.next();
            AttributeInstance attributeinstance = this.func_111152_a((String)entry.getKey());

            if (attributeinstance != null)
            {
                attributeinstance.func_111124_b((AttributeModifier)entry.getValue());
            }
        }
    }

    public void func_111147_b(Multimap par1Multimap)
    {
        Iterator iterator = par1Multimap.entries().iterator();

        while (iterator.hasNext())
        {
            Entry entry = (Entry)iterator.next();
            AttributeInstance attributeinstance = this.func_111152_a((String)entry.getKey());

            if (attributeinstance != null)
            {
                attributeinstance.func_111124_b((AttributeModifier)entry.getValue());
                attributeinstance.func_111121_a((AttributeModifier)entry.getValue());
            }
        }
    }
}
