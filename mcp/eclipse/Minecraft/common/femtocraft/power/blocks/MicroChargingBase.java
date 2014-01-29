package femtocraft.power.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.proxy.ClientProxyFemtocraft;

public class MicroChargingBase extends FemtopowerContainer {
	public Icon side;
	public Icon top;
	public Icon bottom;
	public Icon side_inset;
	public Icon coil_inset;
	public Icon coil_column_inset;
	public Icon top_inset;
	public Icon coil_top_inset;

	public MicroChargingBase(int par1) {
		super(par1, Material.iron);
		setCreativeTab(Femtocraft.femtocraftTab);
		setUnlocalizedName("MicroChargingBase");
		setHardness(2.0f);
		setStepSound(Block.soundMetalFootstep);
	}

	@Override
	public int getRenderType() {
		return ClientProxyFemtocraft.FemtocraftChargingBaseRenderID;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		side = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "ChargingBase_side");
		top = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "ChargingBase_top");
		bottom = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "ChargingBase_bottom");
		side_inset = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "ChargingBase_side_inset");
		coil_inset = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "ChargingBase_coil_inset");
		coil_column_inset = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "ChargingBase_coil_column_inset");
		top_inset = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "ChargingBase_top_inset");
		coil_top_inset = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "ChargingBase_coil_top_inset");
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

}
