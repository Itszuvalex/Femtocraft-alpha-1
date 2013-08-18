package net.minecraft.client.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public abstract class AbstractClientPlayer extends EntityPlayer
{
    public static final ResourceLocation field_110314_b = new ResourceLocation("textures/entity/steve.png");
    private ThreadDownloadImageData field_110316_a;
    private ThreadDownloadImageData field_110315_c;
    private ResourceLocation field_110312_d;
    private ResourceLocation field_110313_e;

    public AbstractClientPlayer(World par1World, String par2Str)
    {
        super(par1World, par2Str);
        this.func_110302_j();
    }

    protected void func_110302_j()
    {
        System.out.println("Setting up custom skins");

        if (this.username != null && !this.username.isEmpty())
        {
            this.field_110312_d = func_110311_f(this.username);
            this.field_110313_e = func_110299_g(this.username);
            this.field_110316_a = func_110304_a(this.field_110312_d, this.username);
            this.field_110315_c = func_110307_b(this.field_110313_e, this.username);
        }
    }

    public ThreadDownloadImageData func_110309_l()
    {
        return this.field_110316_a;
    }

    public ThreadDownloadImageData func_110310_o()
    {
        return this.field_110315_c;
    }

    public ResourceLocation func_110306_p()
    {
        return this.field_110312_d;
    }

    public ResourceLocation func_110303_q()
    {
        return this.field_110313_e;
    }

    public static ThreadDownloadImageData func_110304_a(ResourceLocation par0ResourceLocation, String par1Str)
    {
        return func_110301_a(par0ResourceLocation, func_110300_d(par1Str), field_110314_b, new ImageBufferDownload());
    }

    public static ThreadDownloadImageData func_110307_b(ResourceLocation par0ResourceLocation, String par1Str)
    {
        return func_110301_a(par0ResourceLocation, func_110308_e(par1Str), (ResourceLocation)null, (IImageBuffer)null);
    }

    private static ThreadDownloadImageData func_110301_a(ResourceLocation par0ResourceLocation, String par1Str, ResourceLocation par2ResourceLocation, IImageBuffer par3IImageBuffer)
    {
        TextureManager texturemanager = Minecraft.getMinecraft().func_110434_K();
        Object object = texturemanager.func_110581_b(par0ResourceLocation);

        if (object == null)
        {
            object = new ThreadDownloadImageData(par1Str, par2ResourceLocation, par3IImageBuffer);
            texturemanager.func_110579_a(par0ResourceLocation, (TextureObject)object);
        }

        return (ThreadDownloadImageData)object;
    }

    public static String func_110300_d(String par0Str)
    {
        return String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", new Object[] {StringUtils.stripControlCodes(par0Str)});
    }

    public static String func_110308_e(String par0Str)
    {
        return String.format("http://skins.minecraft.net/MinecraftCloaks/%s.png", new Object[] {StringUtils.stripControlCodes(par0Str)});
    }

    public static ResourceLocation func_110311_f(String par0Str)
    {
        return new ResourceLocation("skins/" + StringUtils.stripControlCodes(par0Str));
    }

    public static ResourceLocation func_110299_g(String par0Str)
    {
        return new ResourceLocation("cloaks/" + StringUtils.stripControlCodes(par0Str));
    }

    public static ResourceLocation func_110305_h(String par0Str)
    {
        return new ResourceLocation("skull/" + StringUtils.stripControlCodes(par0Str));
    }
}
