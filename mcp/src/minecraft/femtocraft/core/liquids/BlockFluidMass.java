package femtocraft.core.liquids;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockFluidMass extends BlockFluidClassic {
	@SideOnly(Side.CLIENT)
	public Icon stillIcon;
	@SideOnly(Side.CLIENT)
	public Icon flowingIcon;

	public BlockFluidMass(int id) {
		super(id, Femtocraft.mass, Material.water);
		setUnlocalizedName("mass");
		setCreativeTab(Femtocraft.femtocraftTab);
		Femtocraft.mass.setBlockID(id);
	}

	@Override
	public Icon getIcon(int side, int meta) {
		return (side == 0 || side == 1) ? stillIcon : flowingIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		stillIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
				+ ":" + "mass_still");
		flowingIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
				+ ":" + "mass_flow");
	}

}
