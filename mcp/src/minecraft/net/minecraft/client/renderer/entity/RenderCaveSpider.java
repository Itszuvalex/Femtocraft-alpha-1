package net.minecraft.client.renderer.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderCaveSpider extends RenderSpider
{
    private static final ResourceLocation field_110893_a = new ResourceLocation("textures/entity/spider/cave_spider.png");

    public RenderCaveSpider()
    {
        this.shadowSize *= 0.7F;
    }

    protected void scaleSpider(EntityCaveSpider par1EntityCaveSpider, float par2)
    {
        GL11.glScalef(0.7F, 0.7F, 0.7F);
    }

    protected ResourceLocation func_110892_a(EntityCaveSpider par1EntityCaveSpider)
    {
        return field_110893_a;
    }

    protected ResourceLocation func_110889_a(EntitySpider par1EntitySpider)
    {
        return this.func_110892_a((EntityCaveSpider)par1EntitySpider);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.scaleSpider((EntityCaveSpider)par1EntityLivingBase, par2);
    }

    protected ResourceLocation func_110775_a(Entity par1Entity)
    {
        return this.func_110892_a((EntityCaveSpider)par1Entity);
    }
}
