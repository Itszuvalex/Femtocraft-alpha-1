package femtocraft.core.liquids;

import net.minecraft.client.Minecraft;
import net.minecraft.item.EnumRarity;
import net.minecraftforge.fluids.Fluid;

public class Mass extends Fluid {

	public Mass() {
		super("Mass");
		setUnlocalizedName("mass");
		setLuminosity(1);
		setDensity(5000);
		setTemperature(600);
		setViscosity(3000);
		setGaseous(false);
		setRarity(EnumRarity.rare);
	}
}
