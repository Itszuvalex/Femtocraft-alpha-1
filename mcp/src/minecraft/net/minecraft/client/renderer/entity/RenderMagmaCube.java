package net.minecraft.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelMagmaCube;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderMagmaCube extends RenderLiving
{
    private static final ResourceLocation field_110873_a = new ResourceLocation("textures/entity/slime/magmacube.png");

    public RenderMagmaCube()
    {
        super(new ModelMagmaCube(), 0.25F);
    }

    protected ResourceLocation func_110872_a(EntityMagmaCube par1EntityMagmaCube)
    {
        return field_110873_a;
    }

    protected void scaleMagmaCube(EntityMagmaCube par1EntityMagmaCube, float par2)
    {
        int i = par1EntityMagmaCube.getSlimeSize();
        float f1 = (par1EntityMagmaCube.field_70812_c + (par1EntityMagmaCube.field_70811_b - par1EntityMagmaCube.field_70812_c) * par2) / ((float)i * 0.5F + 1.0F);
        float f2 = 1.0F / (f1 + 1.0F);
        float f3 = (float)i;
        GL11.glScalef(f2 * f3, 1.0F / f2 * f3, f2 * f3);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.scaleMagmaCube((EntityMagmaCube)par1EntityLivingBase, par2);
    }

    protected ResourceLocation func_110775_a(Entity par1Entity)
    {
        return this.func_110872_a((EntityMagmaCube)par1Entity);
    }
}
