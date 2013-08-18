package net.minecraft.client.resources;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.client.settings.GameSettings;

@SideOnly(Side.CLIENT)
public class ResourcePackRepository
{
    protected static final FileFilter field_110622_a = new ResourcePackRepositoryFilter();
    private final File field_110618_d;
    public final ResourcePack field_110620_b;
    public final MetadataSerializer field_110621_c;
    private List field_110619_e = Lists.newArrayList();
    private List field_110617_f = Lists.newArrayList();

    public ResourcePackRepository(File par1File, ResourcePack par2ResourcePack, MetadataSerializer par3MetadataSerializer, GameSettings par4GameSettings)
    {
        this.field_110618_d = par1File;
        this.field_110620_b = par2ResourcePack;
        this.field_110621_c = par3MetadataSerializer;
        this.func_110616_f();
        this.func_110611_a();
        Iterator iterator = this.field_110619_e.iterator();

        while (iterator.hasNext())
        {
            ResourcePackRepositoryEntry resourcepackrepositoryentry = (ResourcePackRepositoryEntry)iterator.next();

            if (resourcepackrepositoryentry.func_110515_d().equals(par4GameSettings.skin))
            {
                this.field_110617_f.add(resourcepackrepositoryentry);
            }
        }
    }

    private void func_110616_f()
    {
        if (!this.field_110618_d.isDirectory())
        {
            this.field_110618_d.delete();
            this.field_110618_d.mkdirs();
        }
    }

    private List func_110614_g()
    {
        return this.field_110618_d.isDirectory() ? Arrays.asList(this.field_110618_d.listFiles(field_110622_a)) : Collections.emptyList();
    }

    public void func_110611_a()
    {
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = this.func_110614_g().iterator();

        while (iterator.hasNext())
        {
            File file1 = (File)iterator.next();
            ResourcePackRepositoryEntry resourcepackrepositoryentry = new ResourcePackRepositoryEntry(this, file1, (ResourcePackRepositoryFilter)null);

            if (!this.field_110619_e.contains(resourcepackrepositoryentry))
            {
                try
                {
                    resourcepackrepositoryentry.func_110516_a();
                    arraylist.add(resourcepackrepositoryentry);
                }
                catch (Exception exception)
                {
                    arraylist.remove(resourcepackrepositoryentry);
                }
            }
            else
            {
                arraylist.add(this.field_110619_e.get(this.field_110619_e.indexOf(resourcepackrepositoryentry)));
            }
        }

        this.field_110619_e.removeAll(arraylist);
        iterator = this.field_110619_e.iterator();

        while (iterator.hasNext())
        {
            ResourcePackRepositoryEntry resourcepackrepositoryentry1 = (ResourcePackRepositoryEntry)iterator.next();
            resourcepackrepositoryentry1.func_110517_b();
        }

        this.field_110619_e = arraylist;
    }

    public List func_110609_b()
    {
        return ImmutableList.copyOf(this.field_110619_e);
    }

    public List func_110613_c()
    {
        return ImmutableList.copyOf(this.field_110617_f);
    }

    public String func_110610_d()
    {
        return this.field_110617_f.isEmpty() ? "Default" : ((ResourcePackRepositoryEntry)this.field_110617_f.get(0)).func_110515_d();
    }

    public void func_110615_a(ResourcePackRepositoryEntry ... par1ArrayOfResourcePackRepositoryEntry)
    {
        this.field_110617_f.clear();
        Collections.addAll(this.field_110617_f, par1ArrayOfResourcePackRepositoryEntry);
    }

    public File func_110612_e()
    {
        return this.field_110618_d;
    }
}
