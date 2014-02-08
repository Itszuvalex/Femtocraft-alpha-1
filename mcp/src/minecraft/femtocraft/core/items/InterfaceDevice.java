package femtocraft.core.items;

import femtocraft.api.IInterfaceDevice;
import femtocraft.managers.research.TechLevel;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class InterfaceDevice extends Item implements IInterfaceDevice {
	private TechLevel level;

	public InterfaceDevice(int par1, TechLevel level) {
		super(par1);
		this.level = level;
	}

	@Override
	public TechLevel getInterfaceLevel() {
		return level;
	}
	
	@Override
    public boolean shouldPassSneakingClickToBlock(World par2World, int par4, int par5, int par6)
    {
        return true;
    }
}
