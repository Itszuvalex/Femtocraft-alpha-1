package net.minecraft.client.resources;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

@SideOnly(Side.CLIENT)
public class ResourcePackRepositoryEntry
{
    private final File field_110523_b;
    private ResourcePack field_110524_c;
    private PackMetadataSection field_110521_d;
    private BufferedImage field_110522_e;
    private ResourceLocation field_110520_f;

    final ResourcePackRepository field_110525_a;

    private ResourcePackRepositoryEntry(ResourcePackRepository par1ResourcePackRepository, File par2File)
    {
        this.field_110525_a = par1ResourcePackRepository;
        this.field_110523_b = par2File;
    }

    public void func_110516_a() throws IOException
    {
        this.field_110524_c = (ResourcePack)(this.field_110523_b.isDirectory() ? new FolderResourcePack(this.field_110523_b) : new FileResourcePack(this.field_110523_b));
        this.field_110521_d = (PackMetadataSection)this.field_110524_c.func_135058_a(this.field_110525_a.field_110621_c, "pack");

        try
        {
            this.field_110522_e = this.field_110524_c.func_110586_a();
        }
        catch (IOException ioexception)
        {
            ;
        }

        if (this.field_110522_e == null)
        {
            this.field_110522_e = this.field_110525_a.field_110620_b.func_110586_a();
        }

        this.func_110517_b();
    }

    public void func_110518_a(TextureManager par1TextureManager)
    {
        if (this.field_110520_f == null)
        {
            this.field_110520_f = par1TextureManager.func_110578_a("texturepackicon", new DynamicTexture(this.field_110522_e));
        }

        par1TextureManager.func_110577_a(this.field_110520_f);
    }

    public void func_110517_b()
    {
        if (this.field_110524_c instanceof Closeable)
        {
            IOUtils.closeQuietly((Closeable)this.field_110524_c);
        }
    }

    public ResourcePack func_110514_c()
    {
        return this.field_110524_c;
    }

    public String func_110515_d()
    {
        return this.field_110524_c.func_130077_b();
    }

    public String func_110519_e()
    {
        return this.field_110521_d == null ? EnumChatFormatting.RED + "Invalid pack.mcmeta (or missing \'pack\' section)" : this.field_110521_d.func_110461_a();
    }

    public boolean equals(Object par1Obj)
    {
        return this == par1Obj ? true : (par1Obj instanceof ResourcePackRepositoryEntry ? this.toString().equals(par1Obj.toString()) : false);
    }

    public int hashCode()
    {
        return this.toString().hashCode();
    }

    public String toString()
    {
        return String.format("%s:%s:%d", new Object[] {this.field_110523_b.getName(), this.field_110523_b.isDirectory() ? "folder" : "zip", Long.valueOf(this.field_110523_b.lastModified())});
    }

    ResourcePackRepositoryEntry(ResourcePackRepository par1ResourcePackRepository, File par2File, ResourcePackRepositoryFilter par3ResourcePackRepositoryFilter)
    {
        this(par1ResourcePackRepository, par2File);
    }
}
