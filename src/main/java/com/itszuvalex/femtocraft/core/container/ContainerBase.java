package com.itszuvalex.femtocraft.core.container;

import com.itszuvalex.femtocraft.network.FemtocraftPacketHandler;
import com.itszuvalex.femtocraft.network.messages.MessageContainerUpdate;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

/**
 * Created by Chris on 8/29/2014.
 */
public abstract class ContainerBase extends Container {
    protected void sendUpdateToCrafter(Container container, ICrafting crafter, int index, int value) {
        if (crafter instanceof EntityPlayerMP) {
            FemtocraftPacketHandler.INSTANCE().sendTo(new MessageContainerUpdate(index, value),
                    (EntityPlayerMP) crafter);
        } else {
            crafter.sendProgressBarUpdate(container, index, value);
        }
    }
}
