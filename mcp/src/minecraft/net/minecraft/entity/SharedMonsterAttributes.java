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

public class SharedMonsterAttributes
{
    public static final Attribute field_111267_a = (new RangedAttribute("generic.maxHealth", 20.0D, 0.0D, Double.MAX_VALUE)).func_111117_a("Max Health").func_111112_a(true);
    public static final Attribute field_111265_b = (new RangedAttribute("generic.followRange", 32.0D, 0.0D, 2048.0D)).func_111117_a("Follow Range");
    public static final Attribute field_111266_c = (new RangedAttribute("generic.knockbackResistance", 0.0D, 0.0D, 1.0D)).func_111117_a("Knockback Resistance");
    public static final Attribute field_111263_d = (new RangedAttribute("generic.movementSpeed", 0.699999988079071D, 0.0D, Double.MAX_VALUE)).func_111117_a("Movement Speed").func_111112_a(true);
    public static final Attribute field_111264_e = new RangedAttribute("generic.attackDamage", 2.0D, 0.0D, Double.MAX_VALUE);

    public static NBTTagList func_111257_a(BaseAttributeMap par0BaseAttributeMap)
    {
        NBTTagList nbttaglist = new NBTTagList();
        Iterator iterator = par0BaseAttributeMap.func_111146_a().iterator();

        while (iterator.hasNext())
        {
            AttributeInstance attributeinstance = (AttributeInstance)iterator.next();
            nbttaglist.appendTag(func_111261_a(attributeinstance));
        }

        return nbttaglist;
    }

    private static NBTTagCompound func_111261_a(AttributeInstance par0AttributeInstance)
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        Attribute attribute = par0AttributeInstance.func_111123_a();
        nbttagcompound.setString("Name", attribute.func_111108_a());
        nbttagcompound.setDouble("Base", par0AttributeInstance.func_111125_b());
        Collection collection = par0AttributeInstance.func_111122_c();

        if (collection != null && !collection.isEmpty())
        {
            NBTTagList nbttaglist = new NBTTagList();
            Iterator iterator = collection.iterator();

            while (iterator.hasNext())
            {
                AttributeModifier attributemodifier = (AttributeModifier)iterator.next();

                if (attributemodifier.func_111165_e())
                {
                    nbttaglist.appendTag(func_111262_a(attributemodifier));
                }
            }

            nbttagcompound.setTag("Modifiers", nbttaglist);
        }

        return nbttagcompound;
    }

    private static NBTTagCompound func_111262_a(AttributeModifier par0AttributeModifier)
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setString("Name", par0AttributeModifier.func_111166_b());
        nbttagcompound.setDouble("Amount", par0AttributeModifier.func_111164_d());
        nbttagcompound.setInteger("Operation", par0AttributeModifier.func_111169_c());
        nbttagcompound.setLong("UUIDMost", par0AttributeModifier.func_111167_a().getMostSignificantBits());
        nbttagcompound.setLong("UUIDLeast", par0AttributeModifier.func_111167_a().getLeastSignificantBits());
        return nbttagcompound;
    }

    public static void func_111260_a(BaseAttributeMap par0BaseAttributeMap, NBTTagList par1NBTTagList, ILogAgent par2ILogAgent)
    {
        for (int i = 0; i < par1NBTTagList.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound)par1NBTTagList.tagAt(i);
            AttributeInstance attributeinstance = par0BaseAttributeMap.func_111152_a(nbttagcompound.getString("Name"));

            if (attributeinstance != null)
            {
                func_111258_a(attributeinstance, nbttagcompound);
            }
            else if (par2ILogAgent != null)
            {
                par2ILogAgent.logWarning("Ignoring unknown attribute \'" + nbttagcompound.getString("Name") + "\'");
            }
        }
    }

    private static void func_111258_a(AttributeInstance par0AttributeInstance, NBTTagCompound par1NBTTagCompound)
    {
        par0AttributeInstance.func_111128_a(par1NBTTagCompound.getDouble("Base"));

        if (par1NBTTagCompound.hasKey("Modifiers"))
        {
            NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Modifiers");

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                AttributeModifier attributemodifier = func_111259_a((NBTTagCompound)nbttaglist.tagAt(i));
                AttributeModifier attributemodifier1 = par0AttributeInstance.func_111127_a(attributemodifier.func_111167_a());

                if (attributemodifier1 != null)
                {
                    par0AttributeInstance.func_111124_b(attributemodifier1);
                }

                par0AttributeInstance.func_111121_a(attributemodifier);
            }
        }
    }

    public static AttributeModifier func_111259_a(NBTTagCompound par0NBTTagCompound)
    {
        UUID uuid = new UUID(par0NBTTagCompound.getLong("UUIDMost"), par0NBTTagCompound.getLong("UUIDLeast"));
        return new AttributeModifier(uuid, par0NBTTagCompound.getString("Name"), par0NBTTagCompound.getDouble("Amount"), par0NBTTagCompound.getInteger("Operation"));
    }
}
