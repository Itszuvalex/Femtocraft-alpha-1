package net.minecraft.client.renderer.texture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Resource;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class SimpleTexture extends AbstractTexture
{
    private final ResourceLocation field_110568_b;

    public SimpleTexture(ResourceLocation par1ResourceLocation)
    {
        this.field_110568_b = par1ResourceLocation;
    }

    public void func_110551_a(ResourceManager par1ResourceManager) throws IOException
    {
        InputStream inputstream = null;

        try
        {
            Resource resource = par1ResourceManager.func_110536_a(this.field_110568_b);
            inputstream = resource.func_110527_b();
            BufferedImage bufferedimage = ImageIO.read(inputstream);
            boolean flag = false;
            boolean flag1 = false;

            if (resource.func_110528_c())
            {
                try
                {
                    TextureMetadataSection texturemetadatasection = (TextureMetadataSection)resource.func_110526_a("texture");

                    if (texturemetadatasection != null)
                    {
                        flag = texturemetadatasection.func_110479_a();
                        flag1 = texturemetadatasection.func_110480_b();
                    }
                }
                catch (RuntimeException runtimeexception)
                {
                    Minecraft.getMinecraft().getLogAgent().logWarningException("Failed reading metadata of: " + this.field_110568_b, runtimeexception);
                }
            }

            TextureUtil.func_110989_a(this.func_110552_b(), bufferedimage, flag, flag1);
        }
        finally
        {
            if (inputstream != null)
            {
                inputstream.close();
            }
        }
    }
}
