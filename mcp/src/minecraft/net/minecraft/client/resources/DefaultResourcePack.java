package net.minecraft.client.resources;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import net.minecraft.client.resources.data.MetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class DefaultResourcePack implements ResourcePack
{
    public static final Set field_110608_a = ImmutableSet.of("minecraft");
    private final Map field_110606_b = Maps.newHashMap();
    private final File field_110607_c;

    public DefaultResourcePack(File par1File)
    {
        this.field_110607_c = par1File;
        this.func_110603_a(this.field_110607_c);
    }

    public InputStream func_110590_a(ResourceLocation par1ResourceLocation) throws IOException
    {
        InputStream inputstream = this.func_110605_c(par1ResourceLocation);

        if (inputstream != null)
        {
            return inputstream;
        }
        else
        {
            File file1 = (File)this.field_110606_b.get(par1ResourceLocation.toString());

            if (file1 != null)
            {
                return new FileInputStream(file1);
            }
            else
            {
                throw new FileNotFoundException(par1ResourceLocation.func_110623_a());
            }
        }
    }

    private InputStream func_110605_c(ResourceLocation par1ResourceLocation)
    {
        return DefaultResourcePack.class.getResourceAsStream("/assets/minecraft/" + par1ResourceLocation.func_110623_a());
    }

    public void func_110604_a(String par1Str, File par2File)
    {
        this.field_110606_b.put((new ResourceLocation(par1Str)).toString(), par2File);
    }

    public boolean func_110589_b(ResourceLocation par1ResourceLocation)
    {
        return this.func_110605_c(par1ResourceLocation) != null || this.field_110606_b.containsKey(par1ResourceLocation.toString());
    }

    public Set func_110587_b()
    {
        return field_110608_a;
    }

    public void func_110603_a(File par1File)
    {
        if (par1File.isDirectory())
        {
            File[] afile = par1File.listFiles();
            int i = afile.length;

            for (int j = 0; j < i; ++j)
            {
                File file2 = afile[j];
                this.func_110603_a(file2);
            }
        }
        else
        {
            this.func_110604_a(AbstractResourcePack.func_110595_a(this.field_110607_c, par1File), par1File);
        }
    }

    public MetadataSection func_135058_a(MetadataSerializer par1MetadataSerializer, String par2Str) throws IOException
    {
        return AbstractResourcePack.func_110596_a(par1MetadataSerializer, DefaultResourcePack.class.getResourceAsStream("/" + (new ResourceLocation("pack.mcmeta")).func_110623_a()), par2Str);
    }

    public BufferedImage func_110586_a() throws IOException
    {
        return ImageIO.read(DefaultResourcePack.class.getResourceAsStream("/" + (new ResourceLocation("pack.png")).func_110623_a()));
    }

    public String func_130077_b()
    {
        return "Default";
    }
}
