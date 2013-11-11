package net.minecraft.client.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityAuraFX;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.client.particle.EntityBubbleFX;
import net.minecraft.client.particle.EntityCloudFX;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.particle.EntityDropParticleFX;
import net.minecraft.client.particle.EntityEnchantmentTableParticleFX;
import net.minecraft.client.particle.EntityExplodeFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFireworkSparkFX;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.client.particle.EntityFootStepFX;
import net.minecraft.client.particle.EntityHeartFX;
import net.minecraft.client.particle.EntityHugeExplodeFX;
import net.minecraft.client.particle.EntityLargeExplodeFX;
import net.minecraft.client.particle.EntityLavaFX;
import net.minecraft.client.particle.EntityNoteFX;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.particle.EntitySnowShovelFX;
import net.minecraft.client.particle.EntitySpellParticleFX;
import net.minecraft.client.particle.EntitySplashFX;
import net.minecraft.client.particle.EntitySuspendFX;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemRecord;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IWorldAccess;
import org.lwjgl.opengl.ARBOcclusionQuery;
import org.lwjgl.opengl.GL11;

import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.MinecraftForgeClient;

@SideOnly(Side.CLIENT)
public class RenderGlobal implements IWorldAccess
{
    private static final ResourceLocation locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
    private static final ResourceLocation locationSunPng = new ResourceLocation("textures/environment/sun.png");
    private static final ResourceLocation locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
    private static final ResourceLocation locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");
    public List tileEntities = new ArrayList();
    public WorldClient theWorld;

    /** The RenderEngine instance used by RenderGlobal */
    public final TextureManager renderEngine;
    private List worldRenderersToUpdate = new ArrayList();
    private WorldRenderer[] sortedWorldRenderers;
    private WorldRenderer[] worldRenderers;
    private int renderChunksWide;
    private int renderChunksTall;
    private int renderChunksDeep;

    /** OpenGL render lists base */
    private int glRenderListBase;

    /** A reference to the Minecraft object. */
    public Minecraft mc;

    /** Global render blocks */
    public RenderBlocks globalRenderBlocks;

    /** OpenGL occlusion query base */
    private IntBuffer glOcclusionQueryBase;

    /** Is occlusion testing enabled */
    private boolean occlusionEnabled;

    /**
     * counts the cloud render updates. Used with mod to stagger some updates
     */
    private int cloudTickCounter;

    /** The star GL Call list */
    private int starGLCallList;

    /** OpenGL sky list */
    private int glSkyList;

    /** OpenGL sky list 2 */
    private int glSkyList2;

    /** Minimum block X */
    private int minBlockX;

    /** Minimum block Y */
    private int minBlockY;

    /** Minimum block Z */
    private int minBlockZ;

    /** Maximum block X */
    private int maxBlockX;

    /** Maximum block Y */
    private int maxBlockY;

    /** Maximum block Z */
    private int maxBlockZ;

    /**
     * Stores blocks currently being broken. Key is entity ID of the thing doing the breaking. Value is a
     * DestroyBlockProgress
     */
    public Map damagedBlocks = new HashMap();
    private Icon[] destroyBlockIcons;
    private int renderDistance = -1;

    /** Render entities startup counter (init value=2) */
    private int renderEntitiesStartupCounter = 2;

    /** Count entities total */
    private int countEntitiesTotal;

    /** Count entities rendered */
    private int countEntitiesRendered;

    /** Count entities hidden */
    private int countEntitiesHidden;

    /** Occlusion query result */
    IntBuffer occlusionResult = GLAllocation.createDirectIntBuffer(64);

    /** How many renderers are loaded this frame that try to be rendered */
    private int renderersLoaded;

    /** How many renderers are being clipped by the frustrum this frame */
    private int renderersBeingClipped;

    /** How many renderers are being occluded this frame */
    private int renderersBeingOccluded;

    /** How many renderers are actually being rendered this frame */
    private int renderersBeingRendered;

    /**
     * How many renderers are skipping rendering due to not having a render pass this frame
     */
    private int renderersSkippingRenderPass;

    /** Dummy render int */
    private int dummyRenderInt;

    /** World renderers check index */
    private int worldRenderersCheckIndex;

    /** List of OpenGL lists for the current render pass */
    private List glRenderLists = new ArrayList();

    /** All render lists (fixed length 4) */
    private RenderList[] allRenderLists = new RenderList[] {new RenderList(), new RenderList(), new RenderList(), new RenderList()};

    /**
     * Previous x position when the renderers were sorted. (Once the distance moves more than 4 units they will be
     * resorted)
     */
    double prevSortX = -9999.0D;

    /**
     * Previous y position when the renderers were sorted. (Once the distance moves more than 4 units they will be
     * resorted)
     */
    double prevSortY = -9999.0D;

    /**
     * Previous Z position when the renderers were sorted. (Once the distance moves more than 4 units they will be
     * resorted)
     */
    double prevSortZ = -9999.0D;

    /**
     * The offset used to determine if a renderer is one of the sixteenth that are being updated this frame
     */
    int frustumCheckOffset;

    public RenderGlobal(Minecraft par1Minecraft)
    {
        this.mc = par1Minecraft;
        this.renderEngine = par1Minecraft.getTextureManager();
        byte b0 = 34;
        byte b1 = 32;
        this.glRenderListBase = GLAllocation.generateDisplayLists(b0 * b0 * b1 * 3);
        this.occlusionEnabled = OpenGlCapsChecker.checkARBOcclusion();

        if (this.occlusionEnabled)
        {
            this.occlusionResult.clear();
            this.glOcclusionQueryBase = GLAllocation.createDirectIntBuffer(b0 * b0 * b1);
            this.glOcclusionQueryBase.clear();
            this.glOcclusionQueryBase.position(0);
            this.glOcclusionQueryBase.limit(b0 * b0 * b1);
            ARBOcclusionQuery.glGenQueriesARB(this.glOcclusionQueryBase);
        }

        this.starGLCallList = GLAllocation.generateDisplayLists(3);
        GL11.glPushMatrix();
        GL11.glNewList(this.starGLCallList, GL11.GL_COMPILE);
        this.renderStars();
        GL11.glEndList();
        GL11.glPopMatrix();
        Tessellator tessellator = Tessellator.instance;
        this.glSkyList = this.starGLCallList + 1;
        GL11.glNewList(this.glSkyList, GL11.GL_COMPILE);
        byte b2 = 64;
        int i = 256 / b2 + 2;
        float f = 16.0F;
        int j;
        int k;

        for (j = -b2 * i; j <= b2 * i; j += b2)
        {
            for (k = -b2 * i; k <= b2 * i; k += b2)
            {
                tessellator.startDrawingQuads();
                tessellator.addVertex((double)(j + 0), (double)f, (double)(k + 0));
                tessellator.addVertex((double)(j + b2), (double)f, (double)(k + 0));
                tessellator.addVertex((double)(j + b2), (double)f, (double)(k + b2));
                tessellator.addVertex((double)(j + 0), (double)f, (double)(k + b2));
                tessellator.draw();
            }
        }

        GL11.glEndList();
        this.glSkyList2 = this.starGLCallList + 2;
        GL11.glNewList(this.glSkyList2, GL11.GL_COMPILE);
        f = -16.0F;
        tessellator.startDrawingQuads();

        for (j = -b2 * i; j <= b2 * i; j += b2)
        {
            for (k = -b2 * i; k <= b2 * i; k += b2)
            {
                tessellator.addVertex((double)(j + b2), (double)f, (double)(k + 0));
                tessellator.addVertex((double)(j + 0), (double)f, (double)(k + 0));
                tessellator.addVertex((double)(j + 0), (double)f, (double)(k + b2));
                tessellator.addVertex((double)(j + b2), (double)f, (double)(k + b2));
            }
        }

        tessellator.draw();
        GL11.glEndList();
    }

    private void renderStars()
    {
        Random random = new Random(10842L);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();

        for (int i = 0; i < 1500; ++i)
        {
            double d0 = (double)(random.nextFloat() * 2.0F - 1.0F);
            double d1 = (double)(random.nextFloat() * 2.0F - 1.0F);
            double d2 = (double)(random.nextFloat() * 2.0F - 1.0F);
            double d3 = (double)(0.15F + random.nextFloat() * 0.1F);
            double d4 = d0 * d0 + d1 * d1 + d2 * d2;

            if (d4 < 1.0D && d4 > 0.01D)
            {
                d4 = 1.0D / Math.sqrt(d4);
                d0 *= d4;
                d1 *= d4;
                d2 *= d4;
                double d5 = d0 * 100.0D;
                double d6 = d1 * 100.0D;
                double d7 = d2 * 100.0D;
                double d8 = Math.atan2(d0, d2);
                double d9 = Math.sin(d8);
                double d10 = Math.cos(d8);
                double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
                double d12 = Math.sin(d11);
                double d13 = Math.cos(d11);
                double d14 = random.nextDouble() * Math.PI * 2.0D;
                double d15 = Math.sin(d14);
                double d16 = Math.cos(d14);

                for (int j = 0; j < 4; ++j)
                {
                    double d17 = 0.0D;
                    double d18 = (double)((j & 2) - 1) * d3;
                    double d19 = (double)((j + 1 & 2) - 1) * d3;
                    double d20 = d18 * d16 - d19 * d15;
                    double d21 = d19 * d16 + d18 * d15;
                    double d22 = d20 * d12 + d17 * d13;
                    double d23 = d17 * d12 - d20 * d13;
                    double d24 = d23 * d9 - d21 * d10;
                    double d25 = d21 * d9 + d23 * d10;
                    tessellator.addVertex(d5 + d24, d6 + d22, d7 + d25);
                }
            }
        }

        tessellator.draw();
    }

    /**
     * set null to clear
     */
    public void setWorldAndLoadRenderers(WorldClient par1WorldClient)
    {
        if (this.theWorld != null)
        {
            this.theWorld.removeWorldAccess(this);
        }

        this.prevSortX = -9999.0D;
        this.prevSortY = -9999.0D;
        this.prevSortZ = -9999.0D;
        RenderManager.instance.set(par1WorldClient);
        this.theWorld = par1WorldClient;
        this.globalRenderBlocks = new RenderBlocks(par1WorldClient);

        if (par1WorldClient != null)
        {
            par1WorldClient.addWorldAccess(this);
            this.loadRenderers();
        }
    }

    /**
     * Loads all the renderers and sets up the basic settings usage
     */
    public void loadRenderers()
    {
        if (this.theWorld != null)
        {
            Block.leaves.setGraphicsLevel(this.mc.gameSettings.fancyGraphics);
            this.renderDistance = this.mc.gameSettings.renderDistance;
            int i;

            if (this.worldRenderers != null)
            {
                for (i = 0; i < this.worldRenderers.length; ++i)
                {
                    this.worldRenderers[i].stopRendering();
                }
            }

            i = 64 << 3 - this.renderDistance;

            if (i > 400)
            {
                i = 400;
            }

            this.renderChunksWide = i / 16 + 1;
            this.renderChunksTall = 16;
            this.renderChunksDeep = i / 16 + 1;
            this.worldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
            this.sortedWorldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
            int j = 0;
            int k = 0;
            this.minBlockX = 0;
            this.minBlockY = 0;
            this.minBlockZ = 0;
            this.maxBlockX = this.renderChunksWide;
            this.maxBlockY = this.renderChunksTall;
            this.maxBlockZ = this.renderChunksDeep;
            int l;

            for (l = 0; l < this.worldRenderersToUpdate.size(); ++l)
            {
                ((WorldRenderer)this.worldRenderersToUpdate.get(l)).needsUpdate = false;
            }

            this.worldRenderersToUpdate.clear();
            this.tileEntities.clear();

            for (l = 0; l < this.renderChunksWide; ++l)
            {
                for (int i1 = 0; i1 < this.renderChunksTall; ++i1)
                {
                    for (int j1 = 0; j1 < this.renderChunksDeep; ++j1)
                    {
                        this.worldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide + l] = new WorldRenderer(this.theWorld, this.tileEntities, l * 16, i1 * 16, j1 * 16, this.glRenderListBase + j);

                        if (this.occlusionEnabled)
                        {
                            this.worldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide + l].glOcclusionQuery = this.glOcclusionQueryBase.get(k);
                        }

                        this.worldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide + l].isWaitingOnOcclusionQuery = false;
                        this.worldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide + l].isVisible = true;
                        this.worldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide + l].isInFrustum = true;
                        this.worldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide + l].chunkIndex = k++;
                        this.worldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide + l].markDirty();
                        this.sortedWorldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide + l] = this.worldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide + l];
                        this.worldRenderersToUpdate.add(this.worldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide + l]);
                        j += 3;
                    }
                }
            }

            if (this.theWorld != null)
            {
                EntityLivingBase entitylivingbase = this.mc.renderViewEntity;

                if (entitylivingbase != null)
                {
                    this.markRenderersForNewPosition(MathHelper.floor_double(entitylivingbase.posX), MathHelper.floor_double(entitylivingbase.posY), MathHelper.floor_double(entitylivingbase.posZ));
                    Arrays.sort(this.sortedWorldRenderers, new EntitySorter(entitylivingbase));
                }
            }

            this.renderEntitiesStartupCounter = 2;
        }
    }

    /**
     * Renders all entities within range and within the frustrum. Args: pos, frustrum, partialTickTime
     */
    public void renderEntities(Vec3 par1Vec3, ICamera par2ICamera, float par3)
    {
        int pass = MinecraftForgeClient.getRenderPass();
        if (this.renderEntitiesStartupCounter > 0)
        {
            if (pass > 0)
            {
                return;
            }
            --this.renderEntitiesStartupCounter;
        }
        else
        {
            this.theWorld.theProfiler.startSection("prepare");
            TileEntityRenderer.instance.cacheActiveRenderInfo(this.theWorld, this.mc.getTextureManager(), this.mc.fontRenderer, this.mc.renderViewEntity, par3);
            RenderManager.instance.cacheActiveRenderInfo(this.theWorld, this.mc.getTextureManager(), this.mc.fontRenderer, this.mc.renderViewEntity, this.mc.pointedEntityLiving, this.mc.gameSettings, par3);
            if (pass == 0) // no indentation to shrink patch
            {
            this.countEntitiesTotal = 0;
            this.countEntitiesRendered = 0;
            this.countEntitiesHidden = 0;
            EntityLivingBase entitylivingbase = this.mc.renderViewEntity;
            RenderManager.renderPosX = entitylivingbase.lastTickPosX + (entitylivingbase.posX - entitylivingbase.lastTickPosX) * (double)par3;
            RenderManager.renderPosY = entitylivingbase.lastTickPosY + (entitylivingbase.posY - entitylivingbase.lastTickPosY) * (double)par3;
            RenderManager.renderPosZ = entitylivingbase.lastTickPosZ + (entitylivingbase.posZ - entitylivingbase.lastTickPosZ) * (double)par3;
            TileEntityRenderer.staticPlayerX = entitylivingbase.lastTickPosX + (entitylivingbase.posX - entitylivingbase.lastTickPosX) * (double)par3;
            TileEntityRenderer.staticPlayerY = entitylivingbase.lastTickPosY + (entitylivingbase.posY - entitylivingbase.lastTickPosY) * (double)par3;
            TileEntityRenderer.staticPlayerZ = entitylivingbase.lastTickPosZ + (entitylivingbase.posZ - entitylivingbase.lastTickPosZ) * (double)par3;
            }
            this.mc.entityRenderer.enableLightmap((double)par3);
            this.theWorld.theProfiler.endStartSection("global");
            List list = this.theWorld.getLoadedEntityList();
            if (pass == 0) // no indentation for smaller patch size
            {
            this.countEntitiesTotal = list.size();
            }
            int i;
            Entity entity;

            for (i = 0; i < this.theWorld.weatherEffects.size(); ++i)
            {
                entity = (Entity)this.theWorld.weatherEffects.get(i);
                if (!entity.shouldRenderInPass(pass)) continue;
                ++this.countEntitiesRendered;

                if (entity.isInRangeToRenderVec3D(par1Vec3))
                {
                    RenderManager.instance.renderEntity(entity, par3);
                }
            }

            this.theWorld.theProfiler.endStartSection("entities");

            for (i = 0; i < list.size(); ++i)
            {
                entity = (Entity)list.get(i);
                if (!entity.shouldRenderInPass(pass)) continue;
                boolean flag = entity.isInRangeToRenderVec3D(par1Vec3) && (entity.ignoreFrustumCheck || par2ICamera.isBoundingBoxInFrustum(entity.boundingBox) || entity.riddenByEntity == this.mc.thePlayer);

                if (!flag && entity instanceof EntityLiving)
                {
                    EntityLiving entityliving = (EntityLiving)entity;

                    if (entityliving.getLeashed() && entityliving.getLeashedToEntity() != null)
                    {
                        Entity entity1 = entityliving.getLeashedToEntity();
                        flag = par2ICamera.isBoundingBoxInFrustum(entity1.boundingBox);
                    }
                }

                if (flag && (entity != this.mc.renderViewEntity || this.mc.gameSettings.thirdPersonView != 0 || this.mc.renderViewEntity.isPlayerSleeping()) && this.theWorld.blockExists(MathHelper.floor_double(entity.posX), 0, MathHelper.floor_double(entity.posZ)))
                {
                    ++this.countEntitiesRendered;
                    RenderManager.instance.renderEntity(entity, par3);
                }
            }

            this.theWorld.theProfiler.endStartSection("tileentities");
            RenderHelper.enableStandardItemLighting();

            for (i = 0; i < this.tileEntities.size(); ++i)
            {
                TileEntity tile = (TileEntity)tileEntities.get(i);
                if (tile.shouldRenderInPass(pass) && par2ICamera.isBoundingBoxInFrustum(tile.getRenderBoundingBox()))
                {
                    TileEntityRenderer.instance.renderTileEntity(tile, par3);
                }
            }

            this.mc.entityRenderer.disableLightmap((double)par3);
            this.theWorld.theProfiler.endSection();
        }
    }

    /**
     * Gets the render info for use on the Debug screen
     */
    public String getDebugInfoRenders()
    {
        return "C: " + this.renderersBeingRendered + "/" + this.renderersLoaded + ". F: " + this.renderersBeingClipped + ", O: " + this.renderersBeingOccluded + ", E: " + this.renderersSkippingRenderPass;
    }

    /**
     * Gets the entities info for use on the Debug screen
     */
    public String getDebugInfoEntities()
    {
        return "E: " + this.countEntitiesRendered + "/" + this.countEntitiesTotal + ". B: " + this.countEntitiesHidden + ", I: " + (this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered);
    }

    /**
     * Goes through all the renderers setting new positions on them and those that have their position changed are
     * adding to be updated
     */
    private void markRenderersForNewPosition(int par1, int par2, int par3)
    {
        par1 -= 8;
        par2 -= 8;
        par3 -= 8;
        this.minBlockX = Integer.MAX_VALUE;
        this.minBlockY = Integer.MAX_VALUE;
        this.minBlockZ = Integer.MAX_VALUE;
        this.maxBlockX = Integer.MIN_VALUE;
        this.maxBlockY = Integer.MIN_VALUE;
        this.maxBlockZ = Integer.MIN_VALUE;
        int l = this.renderChunksWide * 16;
        int i1 = l / 2;

        for (int j1 = 0; j1 < this.renderChunksWide; ++j1)
        {
            int k1 = j1 * 16;
            int l1 = k1 + i1 - par1;

            if (l1 < 0)
            {
                l1 -= l - 1;
            }

            l1 /= l;
            k1 -= l1 * l;

            if (k1 < this.minBlockX)
            {
                this.minBlockX = k1;
            }

            if (k1 > this.maxBlockX)
            {
                this.maxBlockX = k1;
            }

            for (int i2 = 0; i2 < this.renderChunksDeep; ++i2)
            {
                int j2 = i2 * 16;
                int k2 = j2 + i1 - par3;

                if (k2 < 0)
                {
                    k2 -= l - 1;
                }

                k2 /= l;
                j2 -= k2 * l;

                if (j2 < this.minBlockZ)
                {
                    this.minBlockZ = j2;
                }

                if (j2 > this.maxBlockZ)
                {
                    this.maxBlockZ = j2;
                }

                for (int l2 = 0; l2 < this.renderChunksTall; ++l2)
                {
                    int i3 = l2 * 16;

                    if (i3 < this.minBlockY)
                    {
                        this.minBlockY = i3;
                    }

                    if (i3 > this.maxBlockY)
                    {
                        this.maxBlockY = i3;
                    }

                    WorldRenderer worldrenderer = this.worldRenderers[(i2 * this.renderChunksTall + l2) * this.renderChunksWide + j1];
                    boolean flag = worldrenderer.needsUpdate;
                    worldrenderer.setPosition(k1, i3, j2);

                    if (!flag && worldrenderer.needsUpdate)
                    {
                        this.worldRenderersToUpdate.add(worldrenderer);
                    }
                }
            }
        }
    }

    /**
     * Sorts all renderers based on the passed in entity. Args: entityLiving, renderPass, partialTickTime
     */
    public int sortAndRender(EntityLivingBase par1EntityLivingBase, int par2, double par3)
    {
        this.theWorld.theProfiler.startSection("sortchunks");

        for (int j = 0; j < 10; ++j)
        {
            this.worldRenderersCheckIndex = (this.worldRenderersCheckIndex + 1) % this.worldRenderers.length;
            WorldRenderer worldrenderer = this.worldRenderers[this.worldRenderersCheckIndex];

            if (worldrenderer.needsUpdate && !this.worldRenderersToUpdate.contains(worldrenderer))
            {
                this.worldRenderersToUpdate.add(worldrenderer);
            }
        }

        if (this.mc.gameSettings.renderDistance != this.renderDistance)
        {
            this.loadRenderers();
        }

        if (par2 == 0)
        {
            this.renderersLoaded = 0;
            this.dummyRenderInt = 0;
            this.renderersBeingClipped = 0;
            this.renderersBeingOccluded = 0;
            this.renderersBeingRendered = 0;
            this.renderersSkippingRenderPass = 0;
        }

        double d1 = par1EntityLivingBase.lastTickPosX + (par1EntityLivingBase.posX - par1EntityLivingBase.lastTickPosX) * par3;
        double d2 = par1EntityLivingBase.lastTickPosY + (par1EntityLivingBase.posY - par1EntityLivingBase.lastTickPosY) * par3;
        double d3 = par1EntityLivingBase.lastTickPosZ + (par1EntityLivingBase.posZ - par1EntityLivingBase.lastTickPosZ) * par3;
        double d4 = par1EntityLivingBase.posX - this.prevSortX;
        double d5 = par1EntityLivingBase.posY - this.prevSortY;
        double d6 = par1EntityLivingBase.posZ - this.prevSortZ;

        if (d4 * d4 + d5 * d5 + d6 * d6 > 16.0D)
        {
            this.prevSortX = par1EntityLivingBase.posX;
            this.prevSortY = par1EntityLivingBase.posY;
            this.prevSortZ = par1EntityLivingBase.posZ;
            this.markRenderersForNewPosition(MathHelper.floor_double(par1EntityLivingBase.posX), MathHelper.floor_double(par1EntityLivingBase.posY), MathHelper.floor_double(par1EntityLivingBase.posZ));
            Arrays.sort(this.sortedWorldRenderers, new EntitySorter(par1EntityLivingBase));
        }

        RenderHelper.disableStandardItemLighting();
        byte b0 = 0;
        int k;

        if (this.occlusionEnabled && this.mc.gameSettings.advancedOpengl && !this.mc.gameSettings.anaglyph && par2 == 0)
        {
            byte b1 = 0;
            int l = 16;
            this.checkOcclusionQueryResult(b1, l);

            for (int i1 = b1; i1 < l; ++i1)
            {
                this.sortedWorldRenderers[i1].isVisible = true;
            }

            this.theWorld.theProfiler.endStartSection("render");
            k = b0 + this.renderSortedRenderers(b1, l, par2, par3);

            do
            {
                this.theWorld.theProfiler.endStartSection("occ");
                int j1 = l;
                l *= 2;

                if (l > this.sortedWorldRenderers.length)
                {
                    l = this.sortedWorldRenderers.length;
                }

                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glDisable(GL11.GL_FOG);
                GL11.glColorMask(false, false, false, false);
                GL11.glDepthMask(false);
                this.theWorld.theProfiler.startSection("check");
                this.checkOcclusionQueryResult(j1, l);
                this.theWorld.theProfiler.endSection();
                GL11.glPushMatrix();
                float f = 0.0F;
                float f1 = 0.0F;
                float f2 = 0.0F;

                for (int k1 = j1; k1 < l; ++k1)
                {
                    if (this.sortedWorldRenderers[k1].skipAllRenderPasses())
                    {
                        this.sortedWorldRenderers[k1].isInFrustum = false;
                    }
                    else
                    {
                        if (!this.sortedWorldRenderers[k1].isInFrustum)
                        {
                            this.sortedWorldRenderers[k1].isVisible = true;
                        }

                        if (this.sortedWorldRenderers[k1].isInFrustum && !this.sortedWorldRenderers[k1].isWaitingOnOcclusionQuery)
                        {
                            float f3 = MathHelper.sqrt_float(this.sortedWorldRenderers[k1].distanceToEntitySquared(par1EntityLivingBase));
                            int l1 = (int)(1.0F + f3 / 128.0F);

                            if (this.cloudTickCounter % l1 == k1 % l1)
                            {
                                WorldRenderer worldrenderer1 = this.sortedWorldRenderers[k1];
                                float f4 = (float)((double)worldrenderer1.posXMinus - d1);
                                float f5 = (float)((double)worldrenderer1.posYMinus - d2);
                                float f6 = (float)((double)worldrenderer1.posZMinus - d3);
                                float f7 = f4 - f;
                                float f8 = f5 - f1;
                                float f9 = f6 - f2;

                                if (f7 != 0.0F || f8 != 0.0F || f9 != 0.0F)
                                {
                                    GL11.glTranslatef(f7, f8, f9);
                                    f += f7;
                                    f1 += f8;
                                    f2 += f9;
                                }

                                this.theWorld.theProfiler.startSection("bb");
                                ARBOcclusionQuery.glBeginQueryARB(ARBOcclusionQuery.GL_SAMPLES_PASSED_ARB, this.sortedWorldRenderers[k1].glOcclusionQuery);
                                this.sortedWorldRenderers[k1].callOcclusionQueryList();
                                ARBOcclusionQuery.glEndQueryARB(ARBOcclusionQuery.GL_SAMPLES_PASSED_ARB);
                                this.theWorld.theProfiler.endSection();
                                this.sortedWorldRenderers[k1].isWaitingOnOcclusionQuery = true;
                            }
                        }
                    }
                }

                GL11.glPopMatrix();

                if (this.mc.gameSettings.anaglyph)
                {
                    if (EntityRenderer.anaglyphField == 0)
                    {
                        GL11.glColorMask(false, true, true, true);
                    }
                    else
                    {
                        GL11.glColorMask(true, false, false, true);
                    }
                }
                else
                {
                    GL11.glColorMask(true, true, true, true);
                }

                GL11.glDepthMask(true);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_FOG);
                this.theWorld.theProfiler.endStartSection("render");
                k += this.renderSortedRenderers(j1, l, par2, par3);
            }
            while (l < this.sortedWorldRenderers.length);
        }
        else
        {
            this.theWorld.theProfiler.endStartSection("render");
            k = b0 + this.renderSortedRenderers(0, this.sortedWorldRenderers.length, par2, par3);
        }

        this.theWorld.theProfiler.endSection();
        return k;
    }

    private void checkOcclusionQueryResult(int par1, int par2)
    {
        for (int k = par1; k < par2; ++k)
        {
            if (this.sortedWorldRenderers[k].isWaitingOnOcclusionQuery)
            {
                this.occlusionResult.clear();
                ARBOcclusionQuery.glGetQueryObjectuARB(this.sortedWorldRenderers[k].glOcclusionQuery, ARBOcclusionQuery.GL_QUERY_RESULT_AVAILABLE_ARB, this.occlusionResult);

                if (this.occlusionResult.get(0) != 0)
                {
                    this.sortedWorldRenderers[k].isWaitingOnOcclusionQuery = false;
                    this.occlusionResult.clear();
                    ARBOcclusionQuery.glGetQueryObjectuARB(this.sortedWorldRenderers[k].glOcclusionQuery, ARBOcclusionQuery.GL_QUERY_RESULT_ARB, this.occlusionResult);
                    this.sortedWorldRenderers[k].isVisible = this.occlusionResult.get(0) != 0;
                }
            }
        }
    }

    /**
     * Renders the sorted renders for the specified render pass. Args: startRenderer, numRenderers, renderPass,
     * partialTickTime
     */
    private int renderSortedRenderers(int par1, int par2, int par3, double par4)
    {
        this.glRenderLists.clear();
        int l = 0;

        for (int i1 = par1; i1 < par2; ++i1)
        {
            if (par3 == 0)
            {
                ++this.renderersLoaded;

                if (this.sortedWorldRenderers[i1].skipRenderPass[par3])
                {
                    ++this.renderersSkippingRenderPass;
                }
                else if (!this.sortedWorldRenderers[i1].isInFrustum)
                {
                    ++this.renderersBeingClipped;
                }
                else if (this.occlusionEnabled && !this.sortedWorldRenderers[i1].isVisible)
                {
                    ++this.renderersBeingOccluded;
                }
                else
                {
                    ++this.renderersBeingRendered;
                }
            }

            if (!this.sortedWorldRenderers[i1].skipRenderPass[par3] && this.sortedWorldRenderers[i1].isInFrustum && (!this.occlusionEnabled || this.sortedWorldRenderers[i1].isVisible))
            {
                int j1 = this.sortedWorldRenderers[i1].getGLCallListForPass(par3);

                if (j1 >= 0)
                {
                    this.glRenderLists.add(this.sortedWorldRenderers[i1]);
                    ++l;
                }
            }
        }

        EntityLivingBase entitylivingbase = this.mc.renderViewEntity;
        double d1 = entitylivingbase.lastTickPosX + (entitylivingbase.posX - entitylivingbase.lastTickPosX) * par4;
        double d2 = entitylivingbase.lastTickPosY + (entitylivingbase.posY - entitylivingbase.lastTickPosY) * par4;
        double d3 = entitylivingbase.lastTickPosZ + (entitylivingbase.posZ - entitylivingbase.lastTickPosZ) * par4;
        int k1 = 0;
        int l1;

        for (l1 = 0; l1 < this.allRenderLists.length; ++l1)
        {
            this.allRenderLists[l1].func_78421_b();
        }

        for (l1 = 0; l1 < this.glRenderLists.size(); ++l1)
        {
            WorldRenderer worldrenderer = (WorldRenderer)this.glRenderLists.get(l1);
            int i2 = -1;

            for (int j2 = 0; j2 < k1; ++j2)
            {
                if (this.allRenderLists[j2].func_78418_a(worldrenderer.posXMinus, worldrenderer.posYMinus, worldrenderer.posZMinus))
                {
                    i2 = j2;
                }
            }

            if (i2 < 0)
            {
                i2 = k1++;
                this.allRenderLists[i2].func_78422_a(worldrenderer.posXMinus, worldrenderer.posYMinus, worldrenderer.posZMinus, d1, d2, d3);
            }

            this.allRenderLists[i2].func_78420_a(worldrenderer.getGLCallListForPass(par3));
        }

        this.renderAllRenderLists(par3, par4);
        return l;
    }

    /**
     * Render all render lists
     */
    public void renderAllRenderLists(int par1, double par2)
    {
        this.mc.entityRenderer.enableLightmap(par2);

        for (int j = 0; j < this.allRenderLists.length; ++j)
        {
            this.allRenderLists[j].func_78419_a();
        }

        this.mc.entityRenderer.disableLightmap(par2);
    }

    public void updateClouds()
    {
        ++this.cloudTickCounter;

        if (this.cloudTickCounter % 20 == 0)
        {
            Iterator iterator = this.damagedBlocks.values().iterator();

            while (iterator.hasNext())
            {
                DestroyBlockProgress destroyblockprogress = (DestroyBlockProgress)iterator.next();
                int i = destroyblockprogress.getCreationCloudUpdateTick();

                if (this.cloudTickCounter - i > 400)
                {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Renders the sky with the partial tick time. Args: partialTickTime
     */
    public void renderSky(float par1)
    {
        IRenderHandler skyProvider = null;
        if ((skyProvider = this.mc.theWorld.provider.getSkyRenderer()) != null)
        {
            skyProvider.render(par1, this.theWorld, mc);
            return;
        }
        if (this.mc.theWorld.provider.dimensionId == 1)
        {
            GL11.glDisable(GL11.GL_FOG);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            RenderHelper.disableStandardItemLighting();
            GL11.glDepthMask(false);
            this.renderEngine.bindTexture(locationEndSkyPng);
            Tessellator tessellator = Tessellator.instance;

            for (int i = 0; i < 6; ++i)
            {
                GL11.glPushMatrix();

                if (i == 1)
                {
                    GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (i == 2)
                {
                    GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (i == 3)
                {
                    GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
                }

                if (i == 4)
                {
                    GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
                }

                if (i == 5)
                {
                    GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
                }

                tessellator.startDrawingQuads();
                tessellator.setColorOpaque_I(2631720);
                tessellator.addVertexWithUV(-100.0D, -100.0D, -100.0D, 0.0D, 0.0D);
                tessellator.addVertexWithUV(-100.0D, -100.0D, 100.0D, 0.0D, 16.0D);
                tessellator.addVertexWithUV(100.0D, -100.0D, 100.0D, 16.0D, 16.0D);
                tessellator.addVertexWithUV(100.0D, -100.0D, -100.0D, 16.0D, 0.0D);
                tessellator.draw();
                GL11.glPopMatrix();
            }

            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
        }
        else if (this.mc.theWorld.provider.isSurfaceWorld())
        {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            Vec3 vec3 = this.theWorld.getSkyColor(this.mc.renderViewEntity, par1);
            float f1 = (float)vec3.xCoord;
            float f2 = (float)vec3.yCoord;
            float f3 = (float)vec3.zCoord;
            float f4;

            if (this.mc.gameSettings.anaglyph)
            {
                float f5 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
                float f6 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
                f4 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
                f1 = f5;
                f2 = f6;
                f3 = f4;
            }

            GL11.glColor3f(f1, f2, f3);
            Tessellator tessellator1 = Tessellator.instance;
            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_FOG);
            GL11.glColor3f(f1, f2, f3);
            GL11.glCallList(this.glSkyList);
            GL11.glDisable(GL11.GL_FOG);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            RenderHelper.disableStandardItemLighting();
            float[] afloat = this.theWorld.provider.calcSunriseSunsetColors(this.theWorld.getCelestialAngle(par1), par1);
            float f7;
            float f8;
            float f9;
            float f10;

            if (afloat != null)
            {
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glShadeModel(GL11.GL_SMOOTH);
                GL11.glPushMatrix();
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(MathHelper.sin(this.theWorld.getCelestialAngleRadians(par1)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
                f4 = afloat[0];
                f7 = afloat[1];
                f8 = afloat[2];
                float f11;

                if (this.mc.gameSettings.anaglyph)
                {
                    f9 = (f4 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
                    f10 = (f4 * 30.0F + f7 * 70.0F) / 100.0F;
                    f11 = (f4 * 30.0F + f8 * 70.0F) / 100.0F;
                    f4 = f9;
                    f7 = f10;
                    f8 = f11;
                }

                tessellator1.startDrawing(6);
                tessellator1.setColorRGBA_F(f4, f7, f8, afloat[3]);
                tessellator1.addVertex(0.0D, 100.0D, 0.0D);
                byte b0 = 16;
                tessellator1.setColorRGBA_F(afloat[0], afloat[1], afloat[2], 0.0F);

                for (int j = 0; j <= b0; ++j)
                {
                    f11 = (float)j * (float)Math.PI * 2.0F / (float)b0;
                    float f12 = MathHelper.sin(f11);
                    float f13 = MathHelper.cos(f11);
                    tessellator1.addVertex((double)(f12 * 120.0F), (double)(f13 * 120.0F), (double)(-f13 * 40.0F * afloat[3]));
                }

                tessellator1.draw();
                GL11.glPopMatrix();
                GL11.glShadeModel(GL11.GL_FLAT);
            }

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GL11.glPushMatrix();
            f4 = 1.0F - this.theWorld.getRainStrength(par1);
            f7 = 0.0F;
            f8 = 0.0F;
            f9 = 0.0F;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f4);
            GL11.glTranslatef(f7, f8, f9);
            GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(this.theWorld.getCelestialAngle(par1) * 360.0F, 1.0F, 0.0F, 0.0F);
            f10 = 30.0F;
            this.renderEngine.bindTexture(locationSunPng);
            tessellator1.startDrawingQuads();
            tessellator1.addVertexWithUV((double)(-f10), 100.0D, (double)(-f10), 0.0D, 0.0D);
            tessellator1.addVertexWithUV((double)f10, 100.0D, (double)(-f10), 1.0D, 0.0D);
            tessellator1.addVertexWithUV((double)f10, 100.0D, (double)f10, 1.0D, 1.0D);
            tessellator1.addVertexWithUV((double)(-f10), 100.0D, (double)f10, 0.0D, 1.0D);
            tessellator1.draw();
            f10 = 20.0F;
            this.renderEngine.bindTexture(locationMoonPhasesPng);
            int k = this.theWorld.getMoonPhase();
            int l = k % 4;
            int i1 = k / 4 % 2;
            float f14 = (float)(l + 0) / 4.0F;
            float f15 = (float)(i1 + 0) / 2.0F;
            float f16 = (float)(l + 1) / 4.0F;
            float f17 = (float)(i1 + 1) / 2.0F;
            tessellator1.startDrawingQuads();
            tessellator1.addVertexWithUV((double)(-f10), -100.0D, (double)f10, (double)f16, (double)f17);
            tessellator1.addVertexWithUV((double)f10, -100.0D, (double)f10, (double)f14, (double)f17);
            tessellator1.addVertexWithUV((double)f10, -100.0D, (double)(-f10), (double)f14, (double)f15);
            tessellator1.addVertexWithUV((double)(-f10), -100.0D, (double)(-f10), (double)f16, (double)f15);
            tessellator1.draw();
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            float f18 = this.theWorld.getStarBrightness(par1) * f4;

            if (f18 > 0.0F)
            {
                GL11.glColor4f(f18, f18, f18, f18);
                GL11.glCallList(this.starGLCallList);
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_FOG);
            GL11.glPopMatrix();
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glColor3f(0.0F, 0.0F, 0.0F);
            double d0 = this.mc.thePlayer.getPosition(par1).yCoord - this.theWorld.getHorizon();

            if (d0 < 0.0D)
            {
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0F, 12.0F, 0.0F);
                GL11.glCallList(this.glSkyList2);
                GL11.glPopMatrix();
                f8 = 1.0F;
                f9 = -((float)(d0 + 65.0D));
                f10 = -f8;
                tessellator1.startDrawingQuads();
                tessellator1.setColorRGBA_I(0, 255);
                tessellator1.addVertex((double)(-f8), (double)f9, (double)f8);
                tessellator1.addVertex((double)f8, (double)f9, (double)f8);
                tessellator1.addVertex((double)f8, (double)f10, (double)f8);
                tessellator1.addVertex((double)(-f8), (double)f10, (double)f8);
                tessellator1.addVertex((double)(-f8), (double)f10, (double)(-f8));
                tessellator1.addVertex((double)f8, (double)f10, (double)(-f8));
                tessellator1.addVertex((double)f8, (double)f9, (double)(-f8));
                tessellator1.addVertex((double)(-f8), (double)f9, (double)(-f8));
                tessellator1.addVertex((double)f8, (double)f10, (double)(-f8));
                tessellator1.addVertex((double)f8, (double)f10, (double)f8);
                tessellator1.addVertex((double)f8, (double)f9, (double)f8);
                tessellator1.addVertex((double)f8, (double)f9, (double)(-f8));
                tessellator1.addVertex((double)(-f8), (double)f9, (double)(-f8));
                tessellator1.addVertex((double)(-f8), (double)f9, (double)f8);
                tessellator1.addVertex((double)(-f8), (double)f10, (double)f8);
                tessellator1.addVertex((double)(-f8), (double)f10, (double)(-f8));
                tessellator1.addVertex((double)(-f8), (double)f10, (double)(-f8));
                tessellator1.addVertex((double)(-f8), (double)f10, (double)f8);
                tessellator1.addVertex((double)f8, (double)f10, (double)f8);
                tessellator1.addVertex((double)f8, (double)f10, (double)(-f8));
                tessellator1.draw();
            }

            if (this.theWorld.provider.isSkyColored())
            {
                GL11.glColor3f(f1 * 0.2F + 0.04F, f2 * 0.2F + 0.04F, f3 * 0.6F + 0.1F);
            }
            else
            {
                GL11.glColor3f(f1, f2, f3);
            }

            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, -((float)(d0 - 16.0D)), 0.0F);
            GL11.glCallList(this.glSkyList2);
            GL11.glPopMatrix();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDepthMask(true);
        }
    }

    public void renderClouds(float par1)
    {
        IRenderHandler renderer = null;
        if ((renderer = theWorld.provider.getCloudRenderer()) != null)
        {
            renderer.render(par1, theWorld, mc);
            return;
        }

        if (this.mc.theWorld.provider.isSurfaceWorld())
        {
            if (this.mc.gameSettings.fancyGraphics)
            {
                this.renderCloudsFancy(par1);
            }
            else
            {
                GL11.glDisable(GL11.GL_CULL_FACE);
                float f1 = (float)(this.mc.renderViewEntity.lastTickPosY + (this.mc.renderViewEntity.posY - this.mc.renderViewEntity.lastTickPosY) * (double)par1);
                byte b0 = 32;
                int i = 256 / b0;
                Tessellator tessellator = Tessellator.instance;
                this.renderEngine.bindTexture(locationCloudsPng);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                Vec3 vec3 = this.theWorld.getCloudColour(par1);
                float f2 = (float)vec3.xCoord;
                float f3 = (float)vec3.yCoord;
                float f4 = (float)vec3.zCoord;
                float f5;

                if (this.mc.gameSettings.anaglyph)
                {
                    f5 = (f2 * 30.0F + f3 * 59.0F + f4 * 11.0F) / 100.0F;
                    float f6 = (f2 * 30.0F + f3 * 70.0F) / 100.0F;
                    float f7 = (f2 * 30.0F + f4 * 70.0F) / 100.0F;
                    f2 = f5;
                    f3 = f6;
                    f4 = f7;
                }

                f5 = 4.8828125E-4F;
                double d0 = (double)((float)this.cloudTickCounter + par1);
                double d1 = this.mc.renderViewEntity.prevPosX + (this.mc.renderViewEntity.posX - this.mc.renderViewEntity.prevPosX) * (double)par1 + d0 * 0.029999999329447746D;
                double d2 = this.mc.renderViewEntity.prevPosZ + (this.mc.renderViewEntity.posZ - this.mc.renderViewEntity.prevPosZ) * (double)par1;
                int j = MathHelper.floor_double(d1 / 2048.0D);
                int k = MathHelper.floor_double(d2 / 2048.0D);
                d1 -= (double)(j * 2048);
                d2 -= (double)(k * 2048);
                float f8 = this.theWorld.provider.getCloudHeight() - f1 + 0.33F;
                float f9 = (float)(d1 * (double)f5);
                float f10 = (float)(d2 * (double)f5);
                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(f2, f3, f4, 0.8F);

                for (int l = -b0 * i; l < b0 * i; l += b0)
                {
                    for (int i1 = -b0 * i; i1 < b0 * i; i1 += b0)
                    {
                        tessellator.addVertexWithUV((double)(l + 0), (double)f8, (double)(i1 + b0), (double)((float)(l + 0) * f5 + f9), (double)((float)(i1 + b0) * f5 + f10));
                        tessellator.addVertexWithUV((double)(l + b0), (double)f8, (double)(i1 + b0), (double)((float)(l + b0) * f5 + f9), (double)((float)(i1 + b0) * f5 + f10));
                        tessellator.addVertexWithUV((double)(l + b0), (double)f8, (double)(i1 + 0), (double)((float)(l + b0) * f5 + f9), (double)((float)(i1 + 0) * f5 + f10));
                        tessellator.addVertexWithUV((double)(l + 0), (double)f8, (double)(i1 + 0), (double)((float)(l + 0) * f5 + f9), (double)((float)(i1 + 0) * f5 + f10));
                    }
                }

                tessellator.draw();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_CULL_FACE);
            }
        }
    }

    /**
     * Checks if the given position is to be rendered with cloud fog
     */
    public boolean hasCloudFog(double par1, double par3, double par5, float par7)
    {
        return false;
    }

    /**
     * Renders the 3d fancy clouds
     */
    public void renderCloudsFancy(float par1)
    {
        GL11.glDisable(GL11.GL_CULL_FACE);
        float f1 = (float)(this.mc.renderViewEntity.lastTickPosY + (this.mc.renderViewEntity.posY - this.mc.renderViewEntity.lastTickPosY) * (double)par1);
        Tessellator tessellator = Tessellator.instance;
        float f2 = 12.0F;
        float f3 = 4.0F;
        double d0 = (double)((float)this.cloudTickCounter + par1);
        double d1 = (this.mc.renderViewEntity.prevPosX + (this.mc.renderViewEntity.posX - this.mc.renderViewEntity.prevPosX) * (double)par1 + d0 * 0.029999999329447746D) / (double)f2;
        double d2 = (this.mc.renderViewEntity.prevPosZ + (this.mc.renderViewEntity.posZ - this.mc.renderViewEntity.prevPosZ) * (double)par1) / (double)f2 + 0.33000001311302185D;
        float f4 = this.theWorld.provider.getCloudHeight() - f1 + 0.33F;
        int i = MathHelper.floor_double(d1 / 2048.0D);
        int j = MathHelper.floor_double(d2 / 2048.0D);
        d1 -= (double)(i * 2048);
        d2 -= (double)(j * 2048);
        this.renderEngine.bindTexture(locationCloudsPng);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Vec3 vec3 = this.theWorld.getCloudColour(par1);
        float f5 = (float)vec3.xCoord;
        float f6 = (float)vec3.yCoord;
        float f7 = (float)vec3.zCoord;
        float f8;
        float f9;
        float f10;

        if (this.mc.gameSettings.anaglyph)
        {
            f8 = (f5 * 30.0F + f6 * 59.0F + f7 * 11.0F) / 100.0F;
            f10 = (f5 * 30.0F + f6 * 70.0F) / 100.0F;
            f9 = (f5 * 30.0F + f7 * 70.0F) / 100.0F;
            f5 = f8;
            f6 = f10;
            f7 = f9;
        }

        f8 = (float)(d1 * 0.0D);
        f10 = (float)(d2 * 0.0D);
        f9 = 0.00390625F;
        f8 = (float)MathHelper.floor_double(d1) * f9;
        f10 = (float)MathHelper.floor_double(d2) * f9;
        float f11 = (float)(d1 - (double)MathHelper.floor_double(d1));
        float f12 = (float)(d2 - (double)MathHelper.floor_double(d2));
        byte b0 = 8;
        byte b1 = 4;
        float f13 = 9.765625E-4F;
        GL11.glScalef(f2, 1.0F, f2);

        for (int k = 0; k < 2; ++k)
        {
            if (k == 0)
            {
                GL11.glColorMask(false, false, false, false);
            }
            else if (this.mc.gameSettings.anaglyph)
            {
                if (EntityRenderer.anaglyphField == 0)
                {
                    GL11.glColorMask(false, true, true, true);
                }
                else
                {
                    GL11.glColorMask(true, false, false, true);
                }
            }
            else
            {
                GL11.glColorMask(true, true, true, true);
            }

            for (int l = -b1 + 1; l <= b1; ++l)
            {
                for (int i1 = -b1 + 1; i1 <= b1; ++i1)
                {
                    tessellator.startDrawingQuads();
                    float f14 = (float)(l * b0);
                    float f15 = (float)(i1 * b0);
                    float f16 = f14 - f11;
                    float f17 = f15 - f12;

                    if (f4 > -f3 - 1.0F)
                    {
                        tessellator.setColorRGBA_F(f5 * 0.7F, f6 * 0.7F, f7 * 0.7F, 0.8F);
                        tessellator.setNormal(0.0F, -1.0F, 0.0F);
                        tessellator.addVertexWithUV((double)(f16 + 0.0F), (double)(f4 + 0.0F), (double)(f17 + (float)b0), (double)((f14 + 0.0F) * f9 + f8), (double)((f15 + (float)b0) * f9 + f10));
                        tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + 0.0F), (double)(f17 + (float)b0), (double)((f14 + (float)b0) * f9 + f8), (double)((f15 + (float)b0) * f9 + f10));
                        tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + 0.0F), (double)(f17 + 0.0F), (double)((f14 + (float)b0) * f9 + f8), (double)((f15 + 0.0F) * f9 + f10));
                        tessellator.addVertexWithUV((double)(f16 + 0.0F), (double)(f4 + 0.0F), (double)(f17 + 0.0F), (double)((f14 + 0.0F) * f9 + f8), (double)((f15 + 0.0F) * f9 + f10));
                    }

                    if (f4 <= f3 + 1.0F)
                    {
                        tessellator.setColorRGBA_F(f5, f6, f7, 0.8F);
                        tessellator.setNormal(0.0F, 1.0F, 0.0F);
                        tessellator.addVertexWithUV((double)(f16 + 0.0F), (double)(f4 + f3 - f13), (double)(f17 + (float)b0), (double)((f14 + 0.0F) * f9 + f8), (double)((f15 + (float)b0) * f9 + f10));
                        tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + f3 - f13), (double)(f17 + (float)b0), (double)((f14 + (float)b0) * f9 + f8), (double)((f15 + (float)b0) * f9 + f10));
                        tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + f3 - f13), (double)(f17 + 0.0F), (double)((f14 + (float)b0) * f9 + f8), (double)((f15 + 0.0F) * f9 + f10));
                        tessellator.addVertexWithUV((double)(f16 + 0.0F), (double)(f4 + f3 - f13), (double)(f17 + 0.0F), (double)((f14 + 0.0F) * f9 + f8), (double)((f15 + 0.0F) * f9 + f10));
                    }

                    tessellator.setColorRGBA_F(f5 * 0.9F, f6 * 0.9F, f7 * 0.9F, 0.8F);
                    int j1;

                    if (l > -1)
                    {
                        tessellator.setNormal(-1.0F, 0.0F, 0.0F);

                        for (j1 = 0; j1 < b0; ++j1)
                        {
                            tessellator.addVertexWithUV((double)(f16 + (float)j1 + 0.0F), (double)(f4 + 0.0F), (double)(f17 + (float)b0), (double)((f14 + (float)j1 + 0.5F) * f9 + f8), (double)((f15 + (float)b0) * f9 + f10));
                            tessellator.addVertexWithUV((double)(f16 + (float)j1 + 0.0F), (double)(f4 + f3), (double)(f17 + (float)b0), (double)((f14 + (float)j1 + 0.5F) * f9 + f8), (double)((f15 + (float)b0) * f9 + f10));
                            tessellator.addVertexWithUV((double)(f16 + (float)j1 + 0.0F), (double)(f4 + f3), (double)(f17 + 0.0F), (double)((f14 + (float)j1 + 0.5F) * f9 + f8), (double)((f15 + 0.0F) * f9 + f10));
                            tessellator.addVertexWithUV((double)(f16 + (float)j1 + 0.0F), (double)(f4 + 0.0F), (double)(f17 + 0.0F), (double)((f14 + (float)j1 + 0.5F) * f9 + f8), (double)((f15 + 0.0F) * f9 + f10));
                        }
                    }

                    if (l <= 1)
                    {
                        tessellator.setNormal(1.0F, 0.0F, 0.0F);

                        for (j1 = 0; j1 < b0; ++j1)
                        {
                            tessellator.addVertexWithUV((double)(f16 + (float)j1 + 1.0F - f13), (double)(f4 + 0.0F), (double)(f17 + (float)b0), (double)((f14 + (float)j1 + 0.5F) * f9 + f8), (double)((f15 + (float)b0) * f9 + f10));
                            tessellator.addVertexWithUV((double)(f16 + (float)j1 + 1.0F - f13), (double)(f4 + f3), (double)(f17 + (float)b0), (double)((f14 + (float)j1 + 0.5F) * f9 + f8), (double)((f15 + (float)b0) * f9 + f10));
                            tessellator.addVertexWithUV((double)(f16 + (float)j1 + 1.0F - f13), (double)(f4 + f3), (double)(f17 + 0.0F), (double)((f14 + (float)j1 + 0.5F) * f9 + f8), (double)((f15 + 0.0F) * f9 + f10));
                            tessellator.addVertexWithUV((double)(f16 + (float)j1 + 1.0F - f13), (double)(f4 + 0.0F), (double)(f17 + 0.0F), (double)((f14 + (float)j1 + 0.5F) * f9 + f8), (double)((f15 + 0.0F) * f9 + f10));
                        }
                    }

                    tessellator.setColorRGBA_F(f5 * 0.8F, f6 * 0.8F, f7 * 0.8F, 0.8F);

                    if (i1 > -1)
                    {
                        tessellator.setNormal(0.0F, 0.0F, -1.0F);

                        for (j1 = 0; j1 < b0; ++j1)
                        {
                            tessellator.addVertexWithUV((double)(f16 + 0.0F), (double)(f4 + f3), (double)(f17 + (float)j1 + 0.0F), (double)((f14 + 0.0F) * f9 + f8), (double)((f15 + (float)j1 + 0.5F) * f9 + f10));
                            tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + f3), (double)(f17 + (float)j1 + 0.0F), (double)((f14 + (float)b0) * f9 + f8), (double)((f15 + (float)j1 + 0.5F) * f9 + f10));
                            tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + 0.0F), (double)(f17 + (float)j1 + 0.0F), (double)((f14 + (float)b0) * f9 + f8), (double)((f15 + (float)j1 + 0.5F) * f9 + f10));
                            tessellator.addVertexWithUV((double)(f16 + 0.0F), (double)(f4 + 0.0F), (double)(f17 + (float)j1 + 0.0F), (double)((f14 + 0.0F) * f9 + f8), (double)((f15 + (float)j1 + 0.5F) * f9 + f10));
                        }
                    }

                    if (i1 <= 1)
                    {
                        tessellator.setNormal(0.0F, 0.0F, 1.0F);

                        for (j1 = 0; j1 < b0; ++j1)
                        {
                            tessellator.addVertexWithUV((double)(f16 + 0.0F), (double)(f4 + f3), (double)(f17 + (float)j1 + 1.0F - f13), (double)((f14 + 0.0F) * f9 + f8), (double)((f15 + (float)j1 + 0.5F) * f9 + f10));
                            tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + f3), (double)(f17 + (float)j1 + 1.0F - f13), (double)((f14 + (float)b0) * f9 + f8), (double)((f15 + (float)j1 + 0.5F) * f9 + f10));
                            tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + 0.0F), (double)(f17 + (float)j1 + 1.0F - f13), (double)((f14 + (float)b0) * f9 + f8), (double)((f15 + (float)j1 + 0.5F) * f9 + f10));
                            tessellator.addVertexWithUV((double)(f16 + 0.0F), (double)(f4 + 0.0F), (double)(f17 + (float)j1 + 1.0F - f13), (double)((f14 + 0.0F) * f9 + f8), (double)((f15 + (float)j1 + 0.5F) * f9 + f10));
                        }
                    }

                    tessellator.draw();
                }
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    /**
     * Updates some of the renderers sorted by distance from the player
     */
    public boolean updateRenderers(EntityLivingBase par1EntityLivingBase, boolean par2)
    {
        byte b0 = 2;
        RenderSorter rendersorter = new RenderSorter(par1EntityLivingBase);
        WorldRenderer[] aworldrenderer = new WorldRenderer[b0];
        ArrayList arraylist = null;
        int i = this.worldRenderersToUpdate.size();
        int j = 0;
        this.theWorld.theProfiler.startSection("nearChunksSearch");
        int k;
        WorldRenderer worldrenderer;
        int l;
        int i1;
        label136:

        for (k = 0; k < i; ++k)
        {
            worldrenderer = (WorldRenderer)this.worldRenderersToUpdate.get(k);

            if (worldrenderer != null)
            {
                if (!par2)
                {
                    if (worldrenderer.distanceToEntitySquared(par1EntityLivingBase) > 256.0F)
                    {
                        for (l = 0; l < b0 && (aworldrenderer[l] == null || rendersorter.doCompare(aworldrenderer[l], worldrenderer) <= 0); ++l)
                        {
                            ;
                        }

                        --l;

                        if (l > 0)
                        {
                            i1 = l;

                            while (true)
                            {
                                --i1;

                                if (i1 == 0)
                                {
                                    aworldrenderer[l] = worldrenderer;
                                    continue label136;
                                }

                                aworldrenderer[i1 - 1] = aworldrenderer[i1];
                            }
                        }

                        continue;
                    }
                }
                else if (!worldrenderer.isInFrustum)
                {
                    continue;
                }

                if (arraylist == null)
                {
                    arraylist = new ArrayList();
                }

                ++j;
                arraylist.add(worldrenderer);
                this.worldRenderersToUpdate.set(k, (Object)null);
            }
        }

        this.theWorld.theProfiler.endSection();
        this.theWorld.theProfiler.startSection("sort");

        if (arraylist != null)
        {
            if (arraylist.size() > 1)
            {
                Collections.sort(arraylist, rendersorter);
            }

            for (k = arraylist.size() - 1; k >= 0; --k)
            {
                worldrenderer = (WorldRenderer)arraylist.get(k);
                worldrenderer.updateRenderer();
                worldrenderer.needsUpdate = false;
            }
        }

        this.theWorld.theProfiler.endSection();
        k = 0;
        this.theWorld.theProfiler.startSection("rebuild");
        int j1;

        for (j1 = b0 - 1; j1 >= 0; --j1)
        {
            WorldRenderer worldrenderer1 = aworldrenderer[j1];

            if (worldrenderer1 != null)
            {
                if (!worldrenderer1.isInFrustum && j1 != b0 - 1)
                {
                    aworldrenderer[j1] = null;
                    aworldrenderer[0] = null;
                    break;
                }

                aworldrenderer[j1].updateRenderer();
                aworldrenderer[j1].needsUpdate = false;
                ++k;
            }
        }

        this.theWorld.theProfiler.endSection();
        this.theWorld.theProfiler.startSection("cleanup");
        j1 = 0;
        l = 0;

        for (i1 = this.worldRenderersToUpdate.size(); j1 != i1; ++j1)
        {
            WorldRenderer worldrenderer2 = (WorldRenderer)this.worldRenderersToUpdate.get(j1);

            if (worldrenderer2 != null)
            {
                boolean flag1 = false;

                for (int k1 = 0; k1 < b0 && !flag1; ++k1)
                {
                    if (worldrenderer2 == aworldrenderer[k1])
                    {
                        flag1 = true;
                    }
                }

                if (!flag1)
                {
                    if (l != j1)
                    {
                        this.worldRenderersToUpdate.set(l, worldrenderer2);
                    }

                    ++l;
                }
            }
        }

        this.theWorld.theProfiler.endSection();
        this.theWorld.theProfiler.startSection("trim");

        while (true)
        {
            --j1;

            if (j1 < l)
            {
                this.theWorld.theProfiler.endSection();
                return i == j + k;
            }

            this.worldRenderersToUpdate.remove(j1);
        }
    }

    public void drawBlockDamageTexture(Tessellator par1Tessellator, EntityPlayer par2EntityPlayer, float par3)
    {
        drawBlockDamageTexture(par1Tessellator, (EntityLivingBase)par2EntityPlayer, par3);
    }

    public void drawBlockDamageTexture(Tessellator par1Tessellator, EntityLivingBase par2EntityPlayer, float par3)
    {
        double d0 = par2EntityPlayer.lastTickPosX + (par2EntityPlayer.posX - par2EntityPlayer.lastTickPosX) * (double)par3;
        double d1 = par2EntityPlayer.lastTickPosY + (par2EntityPlayer.posY - par2EntityPlayer.lastTickPosY) * (double)par3;
        double d2 = par2EntityPlayer.lastTickPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.lastTickPosZ) * (double)par3;

        if (!this.damagedBlocks.isEmpty())
        {
            GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_SRC_COLOR);
            this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glPolygonOffset(-3.0F, -3.0F);
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            par1Tessellator.startDrawingQuads();
            par1Tessellator.setTranslation(-d0, -d1, -d2);
            par1Tessellator.disableColor();
            Iterator iterator = this.damagedBlocks.values().iterator();

            while (iterator.hasNext())
            {
                DestroyBlockProgress destroyblockprogress = (DestroyBlockProgress)iterator.next();
                double d3 = (double)destroyblockprogress.getPartialBlockX() - d0;
                double d4 = (double)destroyblockprogress.getPartialBlockY() - d1;
                double d5 = (double)destroyblockprogress.getPartialBlockZ() - d2;

                if (d3 * d3 + d4 * d4 + d5 * d5 > 1024.0D)
                {
                    iterator.remove();
                }
                else
                {
                    int i = this.theWorld.getBlockId(destroyblockprogress.getPartialBlockX(), destroyblockprogress.getPartialBlockY(), destroyblockprogress.getPartialBlockZ());
                    Block block = i > 0 ? Block.blocksList[i] : null;

                    if (block == null)
                    {
                        block = Block.stone;
                    }

                    this.globalRenderBlocks.renderBlockUsingTexture(block, destroyblockprogress.getPartialBlockX(), destroyblockprogress.getPartialBlockY(), destroyblockprogress.getPartialBlockZ(), this.destroyBlockIcons[destroyblockprogress.getPartialBlockDamage()]);
                }
            }

            par1Tessellator.draw();
            par1Tessellator.setTranslation(0.0D, 0.0D, 0.0D);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glPolygonOffset(0.0F, 0.0F);
            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glDepthMask(true);
            GL11.glPopMatrix();
        }
    }

    /**
     * Draws the selection box for the player. Args: entityPlayer, rayTraceHit, i, itemStack, partialTickTime
     */
    public void drawSelectionBox(EntityPlayer par1EntityPlayer, MovingObjectPosition par2MovingObjectPosition, int par3, float par4)
    {
        if (par3 == 0 && par2MovingObjectPosition.typeOfHit == EnumMovingObjectType.TILE)
        {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
            GL11.glLineWidth(2.0F);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDepthMask(false);
            float f1 = 0.002F;
            int j = this.theWorld.getBlockId(par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);

            if (j > 0)
            {
                Block.blocksList[j].setBlockBoundsBasedOnState(this.theWorld, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);
                double d0 = par1EntityPlayer.lastTickPosX + (par1EntityPlayer.posX - par1EntityPlayer.lastTickPosX) * (double)par4;
                double d1 = par1EntityPlayer.lastTickPosY + (par1EntityPlayer.posY - par1EntityPlayer.lastTickPosY) * (double)par4;
                double d2 = par1EntityPlayer.lastTickPosZ + (par1EntityPlayer.posZ - par1EntityPlayer.lastTickPosZ) * (double)par4;
                this.drawOutlinedBoundingBox(Block.blocksList[j].getSelectedBoundingBoxFromPool(this.theWorld, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ).expand((double)f1, (double)f1, (double)f1).getOffsetBoundingBox(-d0, -d1, -d2));
            }

            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_BLEND);
        }
    }

    /**
     * Draws lines for the edges of the bounding box.
     */
    private void drawOutlinedBoundingBox(AxisAlignedBB par1AxisAlignedBB)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(3);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        tessellator.draw();
        tessellator.startDrawing(1);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        tessellator.draw();
    }

    /**
     * Marks the blocks in the given range for update
     */
    public void markBlocksForUpdate(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        int k1 = MathHelper.bucketInt(par1, 16);
        int l1 = MathHelper.bucketInt(par2, 16);
        int i2 = MathHelper.bucketInt(par3, 16);
        int j2 = MathHelper.bucketInt(par4, 16);
        int k2 = MathHelper.bucketInt(par5, 16);
        int l2 = MathHelper.bucketInt(par6, 16);

        for (int i3 = k1; i3 <= j2; ++i3)
        {
            int j3 = i3 % this.renderChunksWide;

            if (j3 < 0)
            {
                j3 += this.renderChunksWide;
            }

            for (int k3 = l1; k3 <= k2; ++k3)
            {
                int l3 = k3 % this.renderChunksTall;

                if (l3 < 0)
                {
                    l3 += this.renderChunksTall;
                }

                for (int i4 = i2; i4 <= l2; ++i4)
                {
                    int j4 = i4 % this.renderChunksDeep;

                    if (j4 < 0)
                    {
                        j4 += this.renderChunksDeep;
                    }

                    int k4 = (j4 * this.renderChunksTall + l3) * this.renderChunksWide + j3;
                    WorldRenderer worldrenderer = this.worldRenderers[k4];

                    if (worldrenderer != null && !worldrenderer.needsUpdate)
                    {
                        this.worldRenderersToUpdate.add(worldrenderer);
                        worldrenderer.markDirty();
                    }
                }
            }
        }
    }

    /**
     * On the client, re-renders the block. On the server, sends the block to the client (which will re-render it),
     * including the tile entity description packet if applicable. Args: x, y, z
     */
    public void markBlockForUpdate(int par1, int par2, int par3)
    {
        this.markBlocksForUpdate(par1 - 1, par2 - 1, par3 - 1, par1 + 1, par2 + 1, par3 + 1);
    }

    /**
     * On the client, re-renders this block. On the server, does nothing. Used for lighting updates.
     */
    public void markBlockForRenderUpdate(int par1, int par2, int par3)
    {
        this.markBlocksForUpdate(par1 - 1, par2 - 1, par3 - 1, par1 + 1, par2 + 1, par3 + 1);
    }

    /**
     * On the client, re-renders all blocks in this range, inclusive. On the server, does nothing. Args: min x, min y,
     * min z, max x, max y, max z
     */
    public void markBlockRangeForRenderUpdate(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        this.markBlocksForUpdate(par1 - 1, par2 - 1, par3 - 1, par4 + 1, par5 + 1, par6 + 1);
    }

    /**
     * Checks all renderers that previously weren't in the frustum and 1/16th of those that previously were in the
     * frustum for frustum clipping Args: frustum, partialTickTime
     */
    public void clipRenderersByFrustum(ICamera par1ICamera, float par2)
    {
        for (int i = 0; i < this.worldRenderers.length; ++i)
        {
            if (!this.worldRenderers[i].skipAllRenderPasses() && (!this.worldRenderers[i].isInFrustum || (i + this.frustumCheckOffset & 15) == 0))
            {
                this.worldRenderers[i].updateInFrustum(par1ICamera);
            }
        }

        ++this.frustumCheckOffset;
    }

    /**
     * Plays the specified record. Arg: recordName, x, y, z
     */
    public void playRecord(String par1Str, int par2, int par3, int par4)
    {
        ItemRecord itemrecord = ItemRecord.getRecord(par1Str);

        if (par1Str != null && itemrecord != null)
        {
            this.mc.ingameGUI.setRecordPlayingMessage(itemrecord.getRecordTitle());
        }

        this.mc.sndManager.playStreaming(par1Str, (float)par2, (float)par3, (float)par4);
    }

    /**
     * Plays the specified sound. Arg: soundName, x, y, z, volume, pitch
     */
    public void playSound(String par1Str, double par2, double par4, double par6, float par8, float par9) {}

    /**
     * Plays sound to all near players except the player reference given
     */
    public void playSoundToNearExcept(EntityPlayer par1EntityPlayer, String par2Str, double par3, double par5, double par7, float par9, float par10) {}

    /**
     * Spawns a particle. Arg: particleType, x, y, z, velX, velY, velZ
     */
    public void spawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        try
        {
            this.doSpawnParticle(par1Str, par2, par4, par6, par8, par10, par12);
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception while adding particle");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being added");
            crashreportcategory.addCrashSection("Name", par1Str);
            crashreportcategory.addCrashSectionCallable("Position", new CallableParticlePositionInfo(this, par2, par4, par6));
            throw new ReportedException(crashreport);
        }
    }

    /**
     * Spawns a particle. Arg: particleType, x, y, z, velX, velY, velZ
     */
    public EntityFX doSpawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        if (this.mc != null && this.mc.renderViewEntity != null && this.mc.effectRenderer != null)
        {
            int i = this.mc.gameSettings.particleSetting;

            if (i == 1 && this.theWorld.rand.nextInt(3) == 0)
            {
                i = 2;
            }

            double d6 = this.mc.renderViewEntity.posX - par2;
            double d7 = this.mc.renderViewEntity.posY - par4;
            double d8 = this.mc.renderViewEntity.posZ - par6;
            EntityFX entityfx = null;

            if (par1Str.equals("hugeexplosion"))
            {
                this.mc.effectRenderer.addEffect(entityfx = new EntityHugeExplodeFX(this.theWorld, par2, par4, par6, par8, par10, par12));
            }
            else if (par1Str.equals("largeexplode"))
            {
                this.mc.effectRenderer.addEffect(entityfx = new EntityLargeExplodeFX(this.renderEngine, this.theWorld, par2, par4, par6, par8, par10, par12));
            }
            else if (par1Str.equals("fireworksSpark"))
            {
                this.mc.effectRenderer.addEffect(entityfx = new EntityFireworkSparkFX(this.theWorld, par2, par4, par6, par8, par10, par12, this.mc.effectRenderer));
            }

            if (entityfx != null)
            {
                return (EntityFX)entityfx;
            }
            else
            {
                double d9 = 16.0D;

                if (d6 * d6 + d7 * d7 + d8 * d8 > d9 * d9)
                {
                    return null;
                }
                else if (i > 1)
                {
                    return null;
                }
                else
                {
                    if (par1Str.equals("bubble"))
                    {
                        entityfx = new EntityBubbleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    }
                    else if (par1Str.equals("suspended"))
                    {
                        entityfx = new EntitySuspendFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    }
                    else if (par1Str.equals("depthsuspend"))
                    {
                        entityfx = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    }
                    else if (par1Str.equals("townaura"))
                    {
                        entityfx = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    }
                    else if (par1Str.equals("crit"))
                    {
                        entityfx = new EntityCritFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    }
                    else if (par1Str.equals("magicCrit"))
                    {
                        entityfx = new EntityCritFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                        ((EntityFX)entityfx).setRBGColorF(((EntityFX)entityfx).getRedColorF() * 0.3F, ((EntityFX)entityfx).getGreenColorF() * 0.8F, ((EntityFX)entityfx).getBlueColorF());
                        ((EntityFX)entityfx).nextTextureIndexX();
                    }
                    else if (par1Str.equals("smoke"))
                    {
                        entityfx = new EntitySmokeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    }
                    else if (par1Str.equals("mobSpell"))
                    {
                        entityfx = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, 0.0D, 0.0D, 0.0D);
                        ((EntityFX)entityfx).setRBGColorF((float)par8, (float)par10, (float)par12);
                    }
                    else if (par1Str.equals("mobSpellAmbient"))
                    {
                        entityfx = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, 0.0D, 0.0D, 0.0D);
                        ((EntityFX)entityfx).setAlphaF(0.15F);
                        ((EntityFX)entityfx).setRBGColorF((float)par8, (float)par10, (float)par12);
                    }
                    else if (par1Str.equals("spell"))
                    {
                        entityfx = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    }
                    else if (par1Str.equals("instantSpell"))
                    {
                        entityfx = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                        ((EntitySpellParticleFX)entityfx).setBaseSpellTextureIndex(144);
                    }
                    else if (par1Str.equals("witchMagic"))
                    {
                        entityfx = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                        ((EntitySpellParticleFX)entityfx).setBaseSpellTextureIndex(144);
                        float f = this.theWorld.rand.nextFloat() * 0.5F + 0.35F;
                        ((EntityFX)entityfx).setRBGColorF(1.0F * f, 0.0F * f, 1.0F * f);
                    }
                    else if (par1Str.equals("note"))
                    {
                        entityfx = new EntityNoteFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    }
                    else if (par1Str.equals("portal"))
                    {
                        entityfx = new EntityPortalFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    }
                    else if (par1Str.equals("enchantmenttable"))
                    {
                        entityfx = new EntityEnchantmentTableParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    }
                    else if (par1Str.equals("explode"))
                    {
                        entityfx = new EntityExplodeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    }
                    else if (par1Str.equals("flame"))
                    {
                        entityfx = new EntityFlameFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    }
                    else if (par1Str.equals("lava"))
                    {
                        entityfx = new EntityLavaFX(this.theWorld, par2, par4, par6);
                    }
                    else if (par1Str.equals("footstep"))
                    {
                        entityfx = new EntityFootStepFX(this.renderEngine, this.theWorld, par2, par4, par6);
                    }
                    else if (par1Str.equals("splash"))
                    {
                        entityfx = new EntitySplashFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    }
                    else if (par1Str.equals("largesmoke"))
                    {
                        entityfx = new EntitySmokeFX(this.theWorld, par2, par4, par6, par8, par10, par12, 2.5F);
                    }
                    else if (par1Str.equals("cloud"))
                    {
                        entityfx = new EntityCloudFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    }
                    else if (par1Str.equals("reddust"))
                    {
                        entityfx = new EntityReddustFX(this.theWorld, par2, par4, par6, (float)par8, (float)par10, (float)par12);
                    }
                    else if (par1Str.equals("snowballpoof"))
                    {
                        entityfx = new EntityBreakingFX(this.theWorld, par2, par4, par6, Item.snowball);
                    }
                    else if (par1Str.equals("dripWater"))
                    {
                        entityfx = new EntityDropParticleFX(this.theWorld, par2, par4, par6, Material.water);
                    }
                    else if (par1Str.equals("dripLava"))
                    {
                        entityfx = new EntityDropParticleFX(this.theWorld, par2, par4, par6, Material.lava);
                    }
                    else if (par1Str.equals("snowshovel"))
                    {
                        entityfx = new EntitySnowShovelFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    }
                    else if (par1Str.equals("slime"))
                    {
                        entityfx = new EntityBreakingFX(this.theWorld, par2, par4, par6, Item.slimeBall);
                    }
                    else if (par1Str.equals("heart"))
                    {
                        entityfx = new EntityHeartFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                    }
                    else if (par1Str.equals("angryVillager"))
                    {
                        entityfx = new EntityHeartFX(this.theWorld, par2, par4 + 0.5D, par6, par8, par10, par12);
                        ((EntityFX)entityfx).setParticleTextureIndex(81);
                        ((EntityFX)entityfx).setRBGColorF(1.0F, 1.0F, 1.0F);
                    }
                    else if (par1Str.equals("happyVillager"))
                    {
                        entityfx = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                        ((EntityFX)entityfx).setParticleTextureIndex(82);
                        ((EntityFX)entityfx).setRBGColorF(1.0F, 1.0F, 1.0F);
                    }
                    else
                    {
                        int j;
                        String[] astring;
                        int k;

                        if (par1Str.startsWith("iconcrack_"))
                        {
                            astring = par1Str.split("_", 3);
                            j = Integer.parseInt(astring[1]);

                            if (astring.length > 2)
                            {
                                k = Integer.parseInt(astring[2]);
                                entityfx = new EntityBreakingFX(this.theWorld, par2, par4, par6, par8, par10, par12, Item.itemsList[j], k);
                            }
                            else
                            {
                                entityfx = new EntityBreakingFX(this.theWorld, par2, par4, par6, par8, par10, par12, Item.itemsList[j], 0);
                            }
                        }
                        else if (par1Str.startsWith("tilecrack_"))
                        {
                            astring = par1Str.split("_", 3);
                            j = Integer.parseInt(astring[1]);
                            k = Integer.parseInt(astring[2]);
                            entityfx = (new EntityDiggingFX(this.theWorld, par2, par4, par6, par8, par10, par12, Block.blocksList[j], k)).applyRenderColor(k);
                        }
                    }

                    if (entityfx != null)
                    {
                        this.mc.effectRenderer.addEffect((EntityFX)entityfx);
                    }

                    return (EntityFX)entityfx;
                }
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * Called on all IWorldAccesses when an entity is created or loaded. On client worlds, starts downloading any
     * necessary textures. On server worlds, adds the entity to the entity tracker.
     */
    public void onEntityCreate(Entity par1Entity) {}

    /**
     * Called on all IWorldAccesses when an entity is unloaded or destroyed. On client worlds, releases any downloaded
     * textures. On server worlds, removes the entity from the entity tracker.
     */
    public void onEntityDestroy(Entity par1Entity) {}

    /**
     * Deletes all display lists
     */
    public void deleteAllDisplayLists()
    {
        GLAllocation.deleteDisplayLists(this.glRenderListBase);
    }

    public void broadcastSound(int par1, int par2, int par3, int par4, int par5)
    {
        Random random = this.theWorld.rand;

        switch (par1)
        {
            case 1013:
            case 1018:
                if (this.mc.renderViewEntity != null)
                {
                    double d0 = (double)par2 - this.mc.renderViewEntity.posX;
                    double d1 = (double)par3 - this.mc.renderViewEntity.posY;
                    double d2 = (double)par4 - this.mc.renderViewEntity.posZ;
                    double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                    double d4 = this.mc.renderViewEntity.posX;
                    double d5 = this.mc.renderViewEntity.posY;
                    double d6 = this.mc.renderViewEntity.posZ;

                    if (d3 > 0.0D)
                    {
                        d4 += d0 / d3 * 2.0D;
                        d5 += d1 / d3 * 2.0D;
                        d6 += d2 / d3 * 2.0D;
                    }

                    if (par1 == 1013)
                    {
                        this.theWorld.playSound(d4, d5, d6, "mob.wither.spawn", 1.0F, 1.0F, false);
                    }
                    else if (par1 == 1018)
                    {
                        this.theWorld.playSound(d4, d5, d6, "mob.enderdragon.end", 5.0F, 1.0F, false);
                    }
                }
            default:
        }
    }

    /**
     * Plays a pre-canned sound effect along with potentially auxiliary data-driven one-shot behaviour (particles, etc).
     */
    public void playAuxSFX(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5, int par6)
    {
        Random random = this.theWorld.rand;
        double d0;
        double d1;
        double d2;
        String s;
        int j1;
        int k1;
        double d3;
        double d4;
        double d5;
        double d6;
        double d7;

        switch (par2)
        {
            case 1000:
                this.theWorld.playSound((double)par3, (double)par4, (double)par5, "random.click", 1.0F, 1.0F, false);
                break;
            case 1001:
                this.theWorld.playSound((double)par3, (double)par4, (double)par5, "random.click", 1.0F, 1.2F, false);
                break;
            case 1002:
                this.theWorld.playSound((double)par3, (double)par4, (double)par5, "random.bow", 1.0F, 1.2F, false);
                break;
            case 1003:
                if (Math.random() < 0.5D)
                {
                    this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "random.door_open", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
                }
                else
                {
                    this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "random.door_close", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
                }

                break;
            case 1004:
                this.theWorld.playSound((double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), "random.fizz", 0.5F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F, false);
                break;
            case 1005:
                if (Item.itemsList[par6] instanceof ItemRecord)
                {
                    this.theWorld.playRecord(((ItemRecord)Item.itemsList[par6]).recordName, par3, par4, par5);
                }
                else
                {
                    this.theWorld.playRecord((String)null, par3, par4, par5);
                }

                break;
            case 1007:
                this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.ghast.charge", 10.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
                break;
            case 1008:
                this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.ghast.fireball", 10.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
                break;
            case 1009:
                this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.ghast.fireball", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
                break;
            case 1010:
                this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.zombie.wood", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
                break;
            case 1011:
                this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.zombie.metal", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
                break;
            case 1012:
                this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.zombie.woodbreak", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
                break;
            case 1014:
                this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.wither.shoot", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
                break;
            case 1015:
                this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.bat.takeoff", 0.05F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
                break;
            case 1016:
                this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.zombie.infect", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
                break;
            case 1017:
                this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "mob.zombie.unfect", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
                break;
            case 1020:
                this.theWorld.playSound((double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), "random.anvil_break", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
                break;
            case 1021:
                this.theWorld.playSound((double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), "random.anvil_use", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
                break;
            case 1022:
                this.theWorld.playSound((double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), "random.anvil_land", 0.3F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
                break;
            case 2000:
                int l1 = par6 % 3 - 1;
                int i2 = par6 / 3 % 3 - 1;
                d1 = (double)par3 + (double)l1 * 0.6D + 0.5D;
                d2 = (double)par4 + 0.5D;
                double d8 = (double)par5 + (double)i2 * 0.6D + 0.5D;

                for (int j2 = 0; j2 < 10; ++j2)
                {
                    double d9 = random.nextDouble() * 0.2D + 0.01D;
                    double d10 = d1 + (double)l1 * 0.01D + (random.nextDouble() - 0.5D) * (double)i2 * 0.5D;
                    d7 = d2 + (random.nextDouble() - 0.5D) * 0.5D;
                    d3 = d8 + (double)i2 * 0.01D + (random.nextDouble() - 0.5D) * (double)l1 * 0.5D;
                    d4 = (double)l1 * d9 + random.nextGaussian() * 0.01D;
                    d5 = -0.03D + random.nextGaussian() * 0.01D;
                    d6 = (double)i2 * d9 + random.nextGaussian() * 0.01D;
                    this.spawnParticle("smoke", d10, d7, d3, d4, d5, d6);
                }

                return;
            case 2001:
                k1 = par6 & 4095;

                if (k1 > 0)
                {
                    Block block = Block.blocksList[k1];
                    this.mc.sndManager.playSound(block.stepSound.getBreakSound(), (float)par3 + 0.5F, (float)par4 + 0.5F, (float)par5 + 0.5F, (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                }

                this.mc.effectRenderer.addBlockDestroyEffects(par3, par4, par5, par6 & 4095, par6 >> 12 & 255);
                break;
            case 2002:
                d0 = (double)par3;
                d1 = (double)par4;
                d2 = (double)par5;
                s = "iconcrack_" + Item.potion.itemID + "_" + par6;

                for (j1 = 0; j1 < 8; ++j1)
                {
                    this.spawnParticle(s, d0, d1, d2, random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D);
                }

                j1 = Item.potion.getColorFromDamage(par6);
                float f = (float)(j1 >> 16 & 255) / 255.0F;
                float f1 = (float)(j1 >> 8 & 255) / 255.0F;
                float f2 = (float)(j1 >> 0 & 255) / 255.0F;
                String s1 = "spell";

                if (Item.potion.isEffectInstant(par6))
                {
                    s1 = "instantSpell";
                }

                for (k1 = 0; k1 < 100; ++k1)
                {
                    d7 = random.nextDouble() * 4.0D;
                    d3 = random.nextDouble() * Math.PI * 2.0D;
                    d4 = Math.cos(d3) * d7;
                    d5 = 0.01D + random.nextDouble() * 0.5D;
                    d6 = Math.sin(d3) * d7;
                    EntityFX entityfx = this.doSpawnParticle(s1, d0 + d4 * 0.1D, d1 + 0.3D, d2 + d6 * 0.1D, d4, d5, d6);

                    if (entityfx != null)
                    {
                        float f3 = 0.75F + random.nextFloat() * 0.25F;
                        entityfx.setRBGColorF(f * f3, f1 * f3, f2 * f3);
                        entityfx.multiplyVelocity((float)d7);
                    }
                }

                this.theWorld.playSound((double)par3 + 0.5D, (double)par4 + 0.5D, (double)par5 + 0.5D, "random.glass", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
                break;
            case 2003:
                d0 = (double)par3 + 0.5D;
                d1 = (double)par4;
                d2 = (double)par5 + 0.5D;
                s = "iconcrack_" + Item.eyeOfEnder.itemID;

                for (j1 = 0; j1 < 8; ++j1)
                {
                    this.spawnParticle(s, d0, d1, d2, random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D);
                }

                for (double d11 = 0.0D; d11 < (Math.PI * 2D); d11 += 0.15707963267948966D)
                {
                    this.spawnParticle("portal", d0 + Math.cos(d11) * 5.0D, d1 - 0.4D, d2 + Math.sin(d11) * 5.0D, Math.cos(d11) * -5.0D, 0.0D, Math.sin(d11) * -5.0D);
                    this.spawnParticle("portal", d0 + Math.cos(d11) * 5.0D, d1 - 0.4D, d2 + Math.sin(d11) * 5.0D, Math.cos(d11) * -7.0D, 0.0D, Math.sin(d11) * -7.0D);
                }

                return;
            case 2004:
                for (int k2 = 0; k2 < 20; ++k2)
                {
                    double d12 = (double)par3 + 0.5D + ((double)this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
                    double d13 = (double)par4 + 0.5D + ((double)this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
                    double d14 = (double)par5 + 0.5D + ((double)this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
                    this.theWorld.spawnParticle("smoke", d12, d13, d14, 0.0D, 0.0D, 0.0D);
                    this.theWorld.spawnParticle("flame", d12, d13, d14, 0.0D, 0.0D, 0.0D);
                }

                return;
            case 2005:
                ItemDye.func_96603_a(this.theWorld, par3, par4, par5, par6);
        }
    }

    /**
     * Starts (or continues) destroying a block with given ID at the given coordinates for the given partially destroyed
     * value
     */
    public void destroyBlockPartially(int par1, int par2, int par3, int par4, int par5)
    {
        if (par5 >= 0 && par5 < 10)
        {
            DestroyBlockProgress destroyblockprogress = (DestroyBlockProgress)this.damagedBlocks.get(Integer.valueOf(par1));

            if (destroyblockprogress == null || destroyblockprogress.getPartialBlockX() != par2 || destroyblockprogress.getPartialBlockY() != par3 || destroyblockprogress.getPartialBlockZ() != par4)
            {
                destroyblockprogress = new DestroyBlockProgress(par1, par2, par3, par4);
                this.damagedBlocks.put(Integer.valueOf(par1), destroyblockprogress);
            }

            destroyblockprogress.setPartialBlockDamage(par5);
            destroyblockprogress.setCloudUpdateTick(this.cloudTickCounter);
        }
        else
        {
            this.damagedBlocks.remove(Integer.valueOf(par1));
        }
    }

    public void registerDestroyBlockIcons(IconRegister par1IconRegister)
    {
        this.destroyBlockIcons = new Icon[10];

        for (int i = 0; i < this.destroyBlockIcons.length; ++i)
        {
            this.destroyBlockIcons[i] = par1IconRegister.registerIcon("destroy_stage_" + i);
        }
    }
}
