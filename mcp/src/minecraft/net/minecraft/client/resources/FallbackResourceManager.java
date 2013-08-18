package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class FallbackResourceManager implements ResourceManager
{
    protected final List field_110540_a = new ArrayList();
    private final MetadataSerializer field_110539_b;

    public FallbackResourceManager(MetadataSerializer par1MetadataSerializer)
    {
        this.field_110539_b = par1MetadataSerializer;
    }

    public void func_110538_a(ResourcePack par1ResourcePack)
    {
        this.field_110540_a.add(par1ResourcePack);
    }

    public Set func_135055_a()
    {
        return null;
    }

    public Resource func_110536_a(ResourceLocation par1ResourceLocation) throws IOException
    {
        ResourcePack resourcepack = null;
        ResourceLocation resourcelocation1 = func_110537_b(par1ResourceLocation);

        for (int i = this.field_110540_a.size() - 1; i >= 0; --i)
        {
            ResourcePack resourcepack1 = (ResourcePack)this.field_110540_a.get(i);

            if (resourcepack == null && resourcepack1.func_110589_b(resourcelocation1))
            {
                resourcepack = resourcepack1;
            }

            if (resourcepack1.func_110589_b(par1ResourceLocation))
            {
                InputStream inputstream = null;

                if (resourcepack != null)
                {
                    inputstream = resourcepack.func_110590_a(resourcelocation1);
                }

                return new SimpleResource(par1ResourceLocation, resourcepack1.func_110590_a(par1ResourceLocation), inputstream, this.field_110539_b);
            }
        }

        throw new FileNotFoundException(par1ResourceLocation.toString());
    }

    public List func_135056_b(ResourceLocation par1ResourceLocation) throws IOException
    {
        ArrayList arraylist = Lists.newArrayList();
        ResourceLocation resourcelocation1 = func_110537_b(par1ResourceLocation);
        Iterator iterator = this.field_110540_a.iterator();

        while (iterator.hasNext())
        {
            ResourcePack resourcepack = (ResourcePack)iterator.next();

            if (resourcepack.func_110589_b(par1ResourceLocation))
            {
                InputStream inputstream = resourcepack.func_110589_b(resourcelocation1) ? resourcepack.func_110590_a(resourcelocation1) : null;
                arraylist.add(new SimpleResource(par1ResourceLocation, resourcepack.func_110590_a(par1ResourceLocation), inputstream, this.field_110539_b));
            }
        }

        if (arraylist.isEmpty())
        {
            throw new FileNotFoundException(par1ResourceLocation.toString());
        }
        else
        {
            return arraylist;
        }
    }

    static ResourceLocation func_110537_b(ResourceLocation par0ResourceLocation)
    {
        return new ResourceLocation(par0ResourceLocation.func_110624_b(), par0ResourceLocation.func_110623_a() + ".mcmeta");
    }
}
