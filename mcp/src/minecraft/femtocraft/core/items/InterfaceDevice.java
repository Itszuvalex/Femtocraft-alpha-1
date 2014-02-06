package femtocraft.core.items;

import femtocraft.api.IInterfaceDevice;
import femtocraft.research.TechLevel;
import net.minecraft.item.Item;

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
}
