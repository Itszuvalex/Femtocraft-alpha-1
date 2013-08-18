package net.minecraft.client.resources;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import net.minecraft.client.resources.data.MetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

@SideOnly(Side.CLIENT)
public class SimpleResource implements Resource
{
    private final Map field_110535_a = Maps.newHashMap();
    private final ResourceLocation field_110533_b;
    private final InputStream field_110534_c;
    private final InputStream field_110531_d;
    private final MetadataSerializer field_110532_e;
    private boolean field_110529_f;
    private JsonObject field_110530_g;

    public SimpleResource(ResourceLocation par1ResourceLocation, InputStream par2InputStream, InputStream par3InputStream, MetadataSerializer par4MetadataSerializer)
    {
        this.field_110533_b = par1ResourceLocation;
        this.field_110534_c = par2InputStream;
        this.field_110531_d = par3InputStream;
        this.field_110532_e = par4MetadataSerializer;
    }

    public InputStream func_110527_b()
    {
        return this.field_110534_c;
    }

    public boolean func_110528_c()
    {
        return this.field_110531_d != null;
    }

    public MetadataSection func_110526_a(String par1Str)
    {
        if (!this.func_110528_c())
        {
            return null;
        }
        else
        {
            if (this.field_110530_g == null && !this.field_110529_f)
            {
                this.field_110529_f = true;
                BufferedReader bufferedreader = null;

                try
                {
                    bufferedreader = new BufferedReader(new InputStreamReader(this.field_110531_d));
                    this.field_110530_g = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
                }
                finally
                {
                    IOUtils.closeQuietly(bufferedreader);
                }
            }

            MetadataSection metadatasection = (MetadataSection)this.field_110535_a.get(par1Str);

            if (metadatasection == null)
            {
                metadatasection = this.field_110532_e.func_110503_a(par1Str, this.field_110530_g);
            }

            return metadatasection;
        }
    }

    public boolean equals(Object par1Obj)
    {
        if (this == par1Obj)
        {
            return true;
        }
        else if (par1Obj instanceof SimpleResource)
        {
            SimpleResource simpleresource = (SimpleResource)par1Obj;
            return this.field_110533_b != null ? this.field_110533_b.equals(simpleresource.field_110533_b) : simpleresource.field_110533_b == null;
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return this.field_110533_b == null ? 0 : this.field_110533_b.hashCode();
    }
}
