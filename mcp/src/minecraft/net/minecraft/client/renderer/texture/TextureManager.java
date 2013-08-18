package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.client.resources.ResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class TextureManager implements Tickable, ResourceManagerReloadListener
{
    private final Map field_110585_a = Maps.newHashMap();
    private final Map field_130089_b = Maps.newHashMap();
    private final List field_110583_b = Lists.newArrayList();
    private final Map field_110584_c = Maps.newHashMap();
    private ResourceManager field_110582_d;

    public TextureManager(ResourceManager par1ResourceManager)
    {
        this.field_110582_d = par1ResourceManager;
    }

    public void func_110577_a(ResourceLocation par1ResourceLocation)
    {
        Object object = (TextureObject)this.field_110585_a.get(par1ResourceLocation);

        if (object == null)
        {
            object = new SimpleTexture(par1ResourceLocation);
            this.func_110579_a(par1ResourceLocation, (TextureObject)object);
        }

        TextureUtil.bindTexture(((TextureObject)object).func_110552_b());
    }

    public ResourceLocation func_130087_a(int par1)
    {
        return (ResourceLocation)this.field_130089_b.get(Integer.valueOf(par1));
    }

    public boolean func_130088_a(ResourceLocation par1ResourceLocation, TextureMap par2TextureMap)
    {
        if (this.func_110580_a(par1ResourceLocation, par2TextureMap))
        {
            this.field_130089_b.put(Integer.valueOf(par2TextureMap.func_130086_a()), par1ResourceLocation);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean func_110580_a(ResourceLocation par1ResourceLocation, TickableTextureObject par2TickableTextureObject)
    {
        if (this.func_110579_a(par1ResourceLocation, par2TickableTextureObject))
        {
            this.field_110583_b.add(par2TickableTextureObject);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean func_110579_a(ResourceLocation par1ResourceLocation, TextureObject par2TextureObject)
    {
        boolean flag = true;

        try
        {
            ((TextureObject)par2TextureObject).func_110551_a(this.field_110582_d);
        }
        catch (IOException ioexception)
        {
            Minecraft.getMinecraft().getLogAgent().logWarningException("Failed to load texture: " + par1ResourceLocation, ioexception);
            par2TextureObject = TextureUtil.field_111001_a;
            this.field_110585_a.put(par1ResourceLocation, par2TextureObject);
            flag = false;
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Registering texture");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Resource location being registered");
            crashreportcategory.addCrashSection("Resource location", par1ResourceLocation);
            crashreportcategory.addCrashSectionCallable("Texture object class", new TextureManagerINNER1(this, (TextureObject)par2TextureObject));
            throw new ReportedException(crashreport);
        }

        this.field_110585_a.put(par1ResourceLocation, par2TextureObject);
        return flag;
    }

    public TextureObject func_110581_b(ResourceLocation par1ResourceLocation)
    {
        return (TextureObject)this.field_110585_a.get(par1ResourceLocation);
    }

    public ResourceLocation func_110578_a(String par1Str, DynamicTexture par2DynamicTexture)
    {
        Integer integer = (Integer)this.field_110584_c.get(par1Str);

        if (integer == null)
        {
            integer = Integer.valueOf(1);
        }
        else
        {
            integer = Integer.valueOf(integer.intValue() + 1);
        }

        this.field_110584_c.put(par1Str, integer);
        ResourceLocation resourcelocation = new ResourceLocation(String.format("dynamic/%s_%d", new Object[] {par1Str, integer}));
        this.func_110579_a(resourcelocation, par2DynamicTexture);
        return resourcelocation;
    }

    public void func_110550_d()
    {
        Iterator iterator = this.field_110583_b.iterator();

        while (iterator.hasNext())
        {
            Tickable tickable = (Tickable)iterator.next();
            tickable.func_110550_d();
        }
    }

    public void func_110549_a(ResourceManager par1ResourceManager)
    {
        Iterator iterator = this.field_110585_a.entrySet().iterator();

        while (iterator.hasNext())
        {
            Entry entry = (Entry)iterator.next();
            this.func_110579_a((ResourceLocation)entry.getKey(), (TextureObject)entry.getValue());
        }
    }
}
