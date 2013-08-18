package net.minecraft.client.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;

@SideOnly(Side.CLIENT)
class ThreadDownloadImageDataINNER1 extends Thread
{
    final ThreadDownloadImageData field_110932_a;

    ThreadDownloadImageDataINNER1(ThreadDownloadImageData par1ThreadDownloadImageData)
    {
        this.field_110932_a = par1ThreadDownloadImageData;
    }

    public void run()
    {
        HttpURLConnection httpurlconnection = null;

        try
        {
            httpurlconnection = (HttpURLConnection)(new URL(ThreadDownloadImageData.func_110554_a(this.field_110932_a))).openConnection(Minecraft.getMinecraft().func_110437_J());
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(false);
            httpurlconnection.connect();

            if (httpurlconnection.getResponseCode() / 100 == 2)
            {
                BufferedImage bufferedimage = ImageIO.read(httpurlconnection.getInputStream());

                if (ThreadDownloadImageData.func_110555_b(this.field_110932_a) != null)
                {
                    bufferedimage = ThreadDownloadImageData.func_110555_b(this.field_110932_a).parseUserSkin(bufferedimage);
                }

                this.field_110932_a.func_110556_a(bufferedimage);
                return;
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return;
        }
        finally
        {
            if (httpurlconnection != null)
            {
                httpurlconnection.disconnect();
            }
        }
    }
}
