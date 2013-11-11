package net.minecraft.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

public class EntityTracker
{
    private final WorldServer theWorld;

    /**
     * List of tracked entities, used for iteration operations on tracked entities.
     */
    private Set trackedEntities = new HashSet();
    private IntHashMap trackedEntityIDs = new IntHashMap();
    private int entityViewDistance;

    public EntityTracker(WorldServer par1WorldServer)
    {
        this.theWorld = par1WorldServer;
        this.entityViewDistance = par1WorldServer.getMinecraftServer().getConfigurationManager().getEntityViewDistance();
    }

    /**
     * if entity is a player sends all tracked events to the player, otherwise, adds with a visibility and update arate
     * based on the class type
     */
    public void addEntityToTracker(Entity par1Entity)
    {
        if (EntityRegistry.instance().tryTrackingEntity(this, par1Entity))
        {
            return;
        }

        if (par1Entity instanceof EntityPlayerMP)
        {
            this.addEntityToTracker(par1Entity, 512, 2);
            EntityPlayerMP entityplayermp = (EntityPlayerMP)par1Entity;
            Iterator iterator = this.trackedEntities.iterator();

            while (iterator.hasNext())
            {
                EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();

                if (entitytrackerentry.myEntity != entityplayermp)
                {
                    entitytrackerentry.tryStartWachingThis(entityplayermp);
                }
            }
        }
        else if (par1Entity instanceof EntityFishHook)
        {
            this.addEntityToTracker(par1Entity, 64, 5, true);
        }
        else if (par1Entity instanceof EntityArrow)
        {
            this.addEntityToTracker(par1Entity, 64, 20, false);
        }
        else if (par1Entity instanceof EntitySmallFireball)
        {
            this.addEntityToTracker(par1Entity, 64, 10, false);
        }
        else if (par1Entity instanceof EntityFireball)
        {
            this.addEntityToTracker(par1Entity, 64, 10, false);
        }
        else if (par1Entity instanceof EntitySnowball)
        {
            this.addEntityToTracker(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityEnderPearl)
        {
            this.addEntityToTracker(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityEnderEye)
        {
            this.addEntityToTracker(par1Entity, 64, 4, true);
        }
        else if (par1Entity instanceof EntityEgg)
        {
            this.addEntityToTracker(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityPotion)
        {
            this.addEntityToTracker(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityExpBottle)
        {
            this.addEntityToTracker(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityFireworkRocket)
        {
            this.addEntityToTracker(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityItem)
        {
            this.addEntityToTracker(par1Entity, 64, 20, true);
        }
        else if (par1Entity instanceof EntityMinecart)
        {
            this.addEntityToTracker(par1Entity, 80, 3, true);
        }
        else if (par1Entity instanceof EntityBoat)
        {
            this.addEntityToTracker(par1Entity, 80, 3, true);
        }
        else if (par1Entity instanceof EntitySquid)
        {
            this.addEntityToTracker(par1Entity, 64, 3, true);
        }
        else if (par1Entity instanceof EntityWither)
        {
            this.addEntityToTracker(par1Entity, 80, 3, false);
        }
        else if (par1Entity instanceof EntityBat)
        {
            this.addEntityToTracker(par1Entity, 80, 3, false);
        }
        else if (par1Entity instanceof IAnimals)
        {
            this.addEntityToTracker(par1Entity, 80, 3, true);
        }
        else if (par1Entity instanceof EntityDragon)
        {
            this.addEntityToTracker(par1Entity, 160, 3, true);
        }
        else if (par1Entity instanceof EntityTNTPrimed)
        {
            this.addEntityToTracker(par1Entity, 160, 10, true);
        }
        else if (par1Entity instanceof EntityFallingSand)
        {
            this.addEntityToTracker(par1Entity, 160, 20, true);
        }
        else if (par1Entity instanceof EntityHanging)
        {
            this.addEntityToTracker(par1Entity, 160, Integer.MAX_VALUE, false);
        }
        else if (par1Entity instanceof EntityXPOrb)
        {
            this.addEntityToTracker(par1Entity, 160, 20, true);
        }
        else if (par1Entity instanceof EntityEnderCrystal)
        {
            this.addEntityToTracker(par1Entity, 256, Integer.MAX_VALUE, false);
        }
    }

    public void addEntityToTracker(Entity par1Entity, int par2, int par3)
    {
        this.addEntityToTracker(par1Entity, par2, par3, false);
    }

    public void addEntityToTracker(Entity par1Entity, int par2, int par3, boolean par4)
    {
        if (par2 > this.entityViewDistance)
        {
            par2 = this.entityViewDistance;
        }

        try
        {
            if (this.trackedEntityIDs.containsItem(par1Entity.entityId))
            {
                throw new IllegalStateException("Entity is already tracked!");
            }

            EntityTrackerEntry entitytrackerentry = new EntityTrackerEntry(par1Entity, par2, par3, par4);
            this.trackedEntities.add(entitytrackerentry);
            this.trackedEntityIDs.addKey(par1Entity.entityId, entitytrackerentry);
            entitytrackerentry.sendEventsToPlayers(this.theWorld.playerEntities);
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding entity to track");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity To Track");
            crashreportcategory.addCrashSection("Tracking range", par2 + " blocks");
            crashreportcategory.addCrashSectionCallable("Update interval", new CallableEntityTracker(this, par3));
            par1Entity.addEntityCrashInfo(crashreportcategory);
            CrashReportCategory crashreportcategory1 = crashreport.makeCategory("Entity That Is Already Tracked");
            ((EntityTrackerEntry)this.trackedEntityIDs.lookup(par1Entity.entityId)).myEntity.addEntityCrashInfo(crashreportcategory1);

            try
            {
                throw new ReportedException(crashreport);
            }
            catch (ReportedException reportedexception)
            {
                System.err.println("\"Silently\" catching entity tracking error.");
                reportedexception.printStackTrace();
            }
        }
    }

    public void removeEntityFromAllTrackingPlayers(Entity par1Entity)
    {
        if (par1Entity instanceof EntityPlayerMP)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)par1Entity;
            Iterator iterator = this.trackedEntities.iterator();

            while (iterator.hasNext())
            {
                EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();
                entitytrackerentry.removeFromWatchingList(entityplayermp);
            }
        }

        EntityTrackerEntry entitytrackerentry1 = (EntityTrackerEntry)this.trackedEntityIDs.removeObject(par1Entity.entityId);

        if (entitytrackerentry1 != null)
        {
            this.trackedEntities.remove(entitytrackerentry1);
            entitytrackerentry1.informAllAssociatedPlayersOfItemDestruction();
        }
    }

    public void updateTrackedEntities()
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = this.trackedEntities.iterator();

        while (iterator.hasNext())
        {
            EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();
            entitytrackerentry.sendLocationToAllClients(this.theWorld.playerEntities);

            if (entitytrackerentry.playerEntitiesUpdated && entitytrackerentry.myEntity instanceof EntityPlayerMP)
            {
                arraylist.add((EntityPlayerMP)entitytrackerentry.myEntity);
            }
        }

        for (int i = 0; i < arraylist.size(); ++i)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)arraylist.get(i);
            Iterator iterator1 = this.trackedEntities.iterator();

            while (iterator1.hasNext())
            {
                EntityTrackerEntry entitytrackerentry1 = (EntityTrackerEntry)iterator1.next();

                if (entitytrackerentry1.myEntity != entityplayermp)
                {
                    entitytrackerentry1.tryStartWachingThis(entityplayermp);
                }
            }
        }
    }

    /**
     * does not send the packet to the entity if the entity is a player
     */
    public void sendPacketToAllPlayersTrackingEntity(Entity par1Entity, Packet par2Packet)
    {
        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)this.trackedEntityIDs.lookup(par1Entity.entityId);

        if (entitytrackerentry != null)
        {
            entitytrackerentry.sendPacketToAllTrackingPlayers(par2Packet);
        }
    }

    /**
     * sends to the entity if the entity is a player
     */
    public void sendPacketToAllAssociatedPlayers(Entity par1Entity, Packet par2Packet)
    {
        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)this.trackedEntityIDs.lookup(par1Entity.entityId);

        if (entitytrackerentry != null)
        {
            entitytrackerentry.sendPacketToAllAssociatedPlayers(par2Packet);
        }
    }

    public void removePlayerFromTrackers(EntityPlayerMP par1EntityPlayerMP)
    {
        Iterator iterator = this.trackedEntities.iterator();

        while (iterator.hasNext())
        {
            EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();
            entitytrackerentry.removePlayerFromTracker(par1EntityPlayerMP);
        }
    }

    public void func_85172_a(EntityPlayerMP par1EntityPlayerMP, Chunk par2Chunk)
    {
        Iterator iterator = this.trackedEntities.iterator();

        while (iterator.hasNext())
        {
            EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();

            if (entitytrackerentry.myEntity != par1EntityPlayerMP && entitytrackerentry.myEntity.chunkCoordX == par2Chunk.xPosition && entitytrackerentry.myEntity.chunkCoordZ == par2Chunk.zPosition)
            {
                entitytrackerentry.tryStartWachingThis(par1EntityPlayerMP);
            }
        }
    }
}
