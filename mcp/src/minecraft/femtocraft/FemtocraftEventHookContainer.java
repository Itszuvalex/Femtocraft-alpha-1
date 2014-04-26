package femtocraft;

import femtocraft.common.gui.DisplaySlot;
import femtocraft.industry.items.ItemAssemblySchematic;
import femtocraft.player.PropertiesNanite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;

public class FemtocraftEventHookContainer {
    @ForgeSubscribe
    public void load(WorldEvent.Load event) {
        if (event.world.isRemote) {
            return;
        }
        Femtocraft.researchManager.load(event.world);
    }

    @ForgeSubscribe
    public void save(WorldEvent.Save event) {
        if (event.world.isRemote) {
            return;
        }
        Femtocraft.researchManager.save(event.world);
    }

    @ForgeSubscribe
    public void preTextureStitch(TextureStitchEvent.Pre event) {
        // Skip out of blocks
        if (event.map.textureType == 0) {
            return;
        }
        // Want items
        ItemAssemblySchematic.placeholderIcon = event.map
                .registerIcon(Femtocraft.ID.toLowerCase() + ":"
                                      + "ItemAssemblySchematic");
        DisplaySlot.noPlaceDisplayIcon = event.map.registerIcon(Femtocraft.ID
                                                                        .toLowerCase() + ":" + "NoPlaceSlot");
    }

    @ForgeSubscribe
    public void onEntityConstructing(EntityConstructing event) {
        if (event.entity instanceof EntityPlayer
                && event.entity
                .getExtendedProperties(PropertiesNanite.PROP_TAG) == null) {
            PropertiesNanite.register((EntityPlayer) event.entity);
        }
    }

    @ForgeSubscribe
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!event.entity.worldObj.isRemote
                && event.entity instanceof EntityPlayer) {
            PropertiesNanite.get((EntityPlayer) event.entity).sync();
        }
    }
}
