package femtocraft.entities.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class AntiGravityFX extends EntityFX {
	public static Icon[] icons;
	
	public AntiGravityFX(World par1World, double par2, double par4,
			double par6, double par8, double par10, double par12) {
		super(par1World, par2, par4, par6, par8, par10, par12);
	}

	@Override
	protected void entityInit() {
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		this.setParticleIcon(icons[this.particleAge * icons.length / this.particleMaxAge]);
	}

}
