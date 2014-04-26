package femtocraft.core.items;

import femtocraft.api.IInterfaceDevice;
import femtocraft.managers.research.EnumTechLevel;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class InterfaceDevice extends Item implements IInterfaceDevice {
    private EnumTechLevel level;

    public InterfaceDevice(int par1, EnumTechLevel level) {
        super(par1);
        this.level = level;
    }

    @Override
    public EnumTechLevel getInterfaceLevel() {
        return level;
    }

    @Override
    public boolean shouldPassSneakingClickToBlock(World par2World, int par4,
                                                  int par5, int par6) {
        return true;
    }
}
