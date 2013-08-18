package net.minecraft.client.resources;

import com.google.common.collect.Sets;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.filefilter.DirectoryFileFilter;

@SideOnly(Side.CLIENT)
public class FolderResourcePack extends AbstractResourcePack
{
    public FolderResourcePack(File par1File)
    {
        super(par1File);
    }

    protected InputStream func_110591_a(String par1Str) throws IOException
    {
        return new BufferedInputStream(new FileInputStream(new File(this.field_110597_b, par1Str)));
    }

    protected boolean func_110593_b(String par1Str)
    {
        return (new File(this.field_110597_b, par1Str)).isFile();
    }

    public Set func_110587_b()
    {
        HashSet hashset = Sets.newHashSet();
        File file1 = new File(this.field_110597_b, "assets/");

        if (file1.isDirectory())
        {
            File[] afile = file1.listFiles((java.io.FileFilter)DirectoryFileFilter.DIRECTORY);
            int i = afile.length;

            for (int j = 0; j < i; ++j)
            {
                File file2 = afile[j];
                String s = func_110595_a(file1, file2);

                if (!s.equals(s.toLowerCase()))
                {
                    this.func_110594_c(s);
                }
                else
                {
                    hashset.add(s.substring(0, s.length() - 1));
                }
            }
        }

        return hashset;
    }
}
