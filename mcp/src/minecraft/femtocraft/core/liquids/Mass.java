package femtocraft.core.liquids;

import femtocraft.Femtocraft;
import net.minecraft.client.Minecraft;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;

public class Mass extends Fluid {

	public Mass() {
		super("Mass");
		setUnlocalizedName("Mass");
		setLuminosity(1);
		setDensity(5000);
		setTemperature(600);
		setViscosity(3000);
		setGaseous(false);
		setRarity(EnumRarity.rare);
	}

	@Override
	public Icon getStillIcon() {
		return Femtocraft.mass_block.stillIcon;
	}

	@Override
	public Icon getFlowingIcon() {
		return Femtocraft.mass_block.flowingIcon;
	}
}
