package net.minecraft.client.resources;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class SimpleReloadableResourceManager implements ReloadableResourceManager
{
    private static final Joiner field_130074_a = Joiner.on(", ");
    private final Map field_110548_a = Maps.newHashMap();
    private final List field_110546_b = Lists.newArrayList();
    private final Set field_135057_d = Sets.newLinkedHashSet();
    private final MetadataSerializer field_110547_c;

    public SimpleReloadableResourceManager(MetadataSerializer par1MetadataSerializer)
    {
        this.field_110547_c = par1MetadataSerializer;
    }

    public void func_110545_a(ResourcePack par1ResourcePack)
    {
        FallbackResourceManager fallbackresourcemanager;

        for (Iterator iterator = par1ResourcePack.func_110587_b().iterator(); iterator.hasNext(); fallbackresourcemanager.func_110538_a(par1ResourcePack))
        {
            String s = (String)iterator.next();
            this.field_135057_d.add(s);
            fallbackresourcemanager = (FallbackResourceManager)this.field_110548_a.get(s);

            if (fallbackresourcemanager == null)
            {
                fallbackresourcemanager = new FallbackResourceManager(this.field_110547_c);
                this.field_110548_a.put(s, fallbackresourcemanager);
            }
        }
    }

    public Set func_135055_a()
    {
        return this.field_135057_d;
    }

    public Resource func_110536_a(ResourceLocation par1ResourceLocation) throws IOException
    {
        ResourceManager resourcemanager = (ResourceManager)this.field_110548_a.get(par1ResourceLocation.func_110624_b());

        if (resourcemanager != null)
        {
            return resourcemanager.func_110536_a(par1ResourceLocation);
        }
        else
        {
            throw new FileNotFoundException(par1ResourceLocation.toString());
        }
    }

    public List func_135056_b(ResourceLocation par1ResourceLocation) throws IOException
    {
        ResourceManager resourcemanager = (ResourceManager)this.field_110548_a.get(par1ResourceLocation.func_110624_b());

        if (resourcemanager != null)
        {
            return resourcemanager.func_135056_b(par1ResourceLocation);
        }
        else
        {
            throw new FileNotFoundException(par1ResourceLocation.toString());
        }
    }

    private void func_110543_a()
    {
        this.field_110548_a.clear();
        this.field_135057_d.clear();
    }

    public void func_110541_a(List par1List)
    {
        this.func_110543_a();
        Minecraft.getMinecraft().getLogAgent().logInfo("Reloading ResourceManager: " + field_130074_a.join(Iterables.transform(par1List, new SimpleReloadableResourceManagerINNER1(this))));
        Iterator iterator = par1List.iterator();

        while (iterator.hasNext())
        {
            ResourcePack resourcepack = (ResourcePack)iterator.next();
            this.func_110545_a(resourcepack);
        }

        this.func_110544_b();
    }

    public void func_110542_a(ResourceManagerReloadListener par1ResourceManagerReloadListener)
    {
        this.field_110546_b.add(par1ResourceManagerReloadListener);
        par1ResourceManagerReloadListener.func_110549_a(this);
    }

    private void func_110544_b()
    {
        Iterator iterator = this.field_110546_b.iterator();

        while (iterator.hasNext())
        {
            ResourceManagerReloadListener resourcemanagerreloadlistener = (ResourceManagerReloadListener)iterator.next();
            resourcemanagerreloadlistener.func_110549_a(this);
        }
    }
}
