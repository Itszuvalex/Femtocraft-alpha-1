package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHorse extends RenderLiving
{
    private static final Map field_110852_a = Maps.newHashMap();
    private static final ResourceLocation field_110850_f = new ResourceLocation("textures/entity/horse/horse_white.png");
    private static final ResourceLocation field_110851_g = new ResourceLocation("textures/entity/horse/mule.png");
    private static final ResourceLocation field_110855_h = new ResourceLocation("textures/entity/horse/donkey.png");
    private static final ResourceLocation field_110854_k = new ResourceLocation("textures/entity/horse/horse_zombie.png");
    private static final ResourceLocation field_110853_l = new ResourceLocation("textures/entity/horse/horse_skeleton.png");

    public RenderHorse(ModelBase par1ModelBase, float par2)
    {
        super(par1ModelBase, par2);
    }

    protected void func_110847_a(EntityHorse par1EntityHorse, float par2)
    {
        float f1 = 1.0F;
        int i = par1EntityHorse.func_110265_bP();

        if (i == 1)
        {
            f1 *= 0.87F;
        }
        else if (i == 2)
        {
            f1 *= 0.92F;
        }

        GL11.glScalef(f1, f1, f1);
        super.preRenderCallback(par1EntityHorse, par2);
    }

    protected void func_110846_a(EntityHorse par1EntityHorse, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        if (par1EntityHorse.isInvisible())
        {
            this.mainModel.setRotationAngles(par2, par3, par4, par5, par6, par7, par1EntityHorse);
        }
        else
        {
            this.func_110777_b(par1EntityHorse);
            this.mainModel.render(par1EntityHorse, par2, par3, par4, par5, par6, par7);
        }
    }

    protected ResourceLocation func_110849_a(EntityHorse par1EntityHorse)
    {
        if (!par1EntityHorse.func_110239_cn())
        {
            switch (par1EntityHorse.func_110265_bP())
            {
                case 0:
                default:
                    return field_110850_f;
                case 1:
                    return field_110855_h;
                case 2:
                    return field_110851_g;
                case 3:
                    return field_110854_k;
                case 4:
                    return field_110853_l;
            }
        }
        else
        {
            return this.func_110848_b(par1EntityHorse);
        }
    }

    private ResourceLocation func_110848_b(EntityHorse par1EntityHorse)
    {
        String s = par1EntityHorse.func_110264_co();
        ResourceLocation resourcelocation = (ResourceLocation)field_110852_a.get(s);

        if (resourcelocation == null)
        {
            resourcelocation = new ResourceLocation(s);
            Minecraft.getMinecraft().func_110434_K().func_110579_a(resourcelocation, new LayeredTexture(par1EntityHorse.func_110212_cp()));
            field_110852_a.put(s, resourcelocation);
        }

        return resourcelocation;
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.func_110847_a((EntityHorse)par1EntityLivingBase, par2);
    }

    /**
     * Renders the model in RenderLiving
     */
    protected void renderModel(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        this.func_110846_a((EntityHorse)par1EntityLivingBase, par2, par3, par4, par5, par6, par7);
    }

    protected ResourceLocation func_110775_a(Entity par1Entity)
    {
        return this.func_110849_a((EntityHorse)par1Entity);
    }
}
