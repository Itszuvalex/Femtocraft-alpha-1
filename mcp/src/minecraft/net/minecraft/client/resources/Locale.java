package net.minecraft.client.resources;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

@SideOnly(Side.CLIENT)
public class Locale
{
    private static final Splitter field_135030_b = Splitter.on('=').limit(2);
    private static final Pattern field_135031_c = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
    Map field_135032_a = Maps.newHashMap();
    private boolean field_135029_d;

    public synchronized void func_135022_a(ResourceManager par1ResourceManager, List par2List)
    {
        this.field_135032_a.clear();
        Iterator iterator = par2List.iterator();

        while (iterator.hasNext())
        {
            String s = (String)iterator.next();
            String s1 = String.format("lang/%s.lang", new Object[] {s});
            Iterator iterator1 = par1ResourceManager.func_135055_a().iterator();

            while (iterator1.hasNext())
            {
                String s2 = (String)iterator1.next();

                try
                {
                    this.func_135028_a(par1ResourceManager.func_135056_b(new ResourceLocation(s2, s1)));
                }
                catch (IOException ioexception)
                {
                    ;
                }
            }
        }

        this.func_135024_b();
    }

    public boolean func_135025_a()
    {
        return this.field_135029_d;
    }

    private void func_135024_b()
    {
        this.field_135029_d = false;
        Iterator iterator = this.field_135032_a.values().iterator();

        while (iterator.hasNext())
        {
            String s = (String)iterator.next();

            for (int i = 0; i < s.length(); ++i)
            {
                if (s.charAt(i) >= 256)
                {
                    this.field_135029_d = true;
                    break;
                }
            }
        }
    }

    private void func_135028_a(List par1List) throws IOException
    {
        Iterator iterator = par1List.iterator();

        while (iterator.hasNext())
        {
            Resource resource = (Resource)iterator.next();
            this.func_135021_a(resource.func_110527_b());
        }
    }

    private void func_135021_a(InputStream par1InputStream) throws IOException
    {
        Iterator iterator = IOUtils.readLines(par1InputStream, Charsets.UTF_8).iterator();

        while (iterator.hasNext())
        {
            String s = (String)iterator.next();

            if (!s.isEmpty() && s.charAt(0) != 35)
            {
                String[] astring = (String[])Iterables.toArray(field_135030_b.split(s), String.class);

                if (astring != null && astring.length == 2)
                {
                    String s1 = astring[0];
                    String s2 = field_135031_c.matcher(astring[1]).replaceAll("%$1s");
                    this.field_135032_a.put(s1, s2);
                }
            }
        }
    }

    private String func_135026_c(String par1Str)
    {
        String s1 = (String)this.field_135032_a.get(par1Str);
        return s1 == null ? par1Str : s1;
    }

    public String func_135027_a(String par1Str)
    {
        return this.func_135026_c(par1Str);
    }

    public String func_135023_a(String par1Str, Object[] par2ArrayOfObj)
    {
        String s1 = this.func_135026_c(par1Str);

        try
        {
            return String.format(s1, par2ArrayOfObj);
        }
        catch (IllegalFormatException illegalformatexception)
        {
            return "Format error: " + s1;
        }
    }
}
