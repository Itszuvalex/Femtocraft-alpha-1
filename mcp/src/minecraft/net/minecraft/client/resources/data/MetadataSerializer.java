package net.minecraft.client.resources.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.dispenser.IRegistry;
import net.minecraft.dispenser.RegistrySimple;

@SideOnly(Side.CLIENT)
public class MetadataSerializer
{
    private final IRegistry field_110508_a = new RegistrySimple();
    private final GsonBuilder field_110506_b = new GsonBuilder();
    private Gson field_110507_c;

    public void func_110504_a(MetadataSectionSerializer par1MetadataSectionSerializer, Class par2Class)
    {
        this.field_110508_a.putObject(par1MetadataSectionSerializer.func_110483_a(), new MetadataSerializerRegistration(this, par1MetadataSectionSerializer, par2Class, (MetadataSerializerEmptyAnon)null));
        this.field_110506_b.registerTypeAdapter(par2Class, par1MetadataSectionSerializer);
        this.field_110507_c = null;
    }

    public MetadataSection func_110503_a(String par1Str, JsonObject par2JsonObject)
    {
        if (par1Str == null)
        {
            throw new IllegalArgumentException("Metadata section name cannot be null");
        }
        else if (!par2JsonObject.has(par1Str))
        {
            return null;
        }
        else if (!par2JsonObject.get(par1Str).isJsonObject())
        {
            throw new IllegalArgumentException("Invalid metadata for \'" + par1Str + "\' - expected object, found " + par2JsonObject.get(par1Str));
        }
        else
        {
            MetadataSerializerRegistration metadataserializerregistration = (MetadataSerializerRegistration)this.field_110508_a.getObject(par1Str);

            if (metadataserializerregistration == null)
            {
                throw new IllegalArgumentException("Don\'t know how to handle metadata section \'" + par1Str + "\'");
            }
            else
            {
                return (MetadataSection)this.func_110505_a().fromJson(par2JsonObject.getAsJsonObject(par1Str), metadataserializerregistration.field_110500_b);
            }
        }
    }

    private Gson func_110505_a()
    {
        if (this.field_110507_c == null)
        {
            this.field_110507_c = this.field_110506_b.create();
        }

        return this.field_110507_c;
    }
}
