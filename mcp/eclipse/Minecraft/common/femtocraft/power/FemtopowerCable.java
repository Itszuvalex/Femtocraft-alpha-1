package femtocraft.power;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.power.TileEntity.FemtopowerCableTile;

public class FemtopowerCable extends FemtopowerContainer {
	protected int renderType;
//	public Icon core;
	public Icon coreBorder;
	public Icon connector;
	public Icon coil;
	public Icon coilEdge;
	public Icon border;
	
	public FemtopowerCable(int par1, Material par2Material) {
		super(par1, par2Material);
		setCreativeTab(Femtocraft.femtocraftTab);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new FemtopowerCableTile();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	public FemtopowerCable setRenderType(int id) {
		this.renderType = id;
		return this;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return renderType;
	}
	
	@SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister)
    {
     //   this.field_94336_cN = par1IconRegister.func_94245_a("Femtocraft:FemtopowerCable");
	//	this.core = par1IconRegister.func_94245_a("Femtocraft:FemtopowerCableCore");
		this.coreBorder = par1IconRegister.func_94245_a("Femtocraft:FemtopowerCableCoreBorder");
		this.connector = par1IconRegister.func_94245_a("Femtocraft:FemtopowerConnector");
		this.coil = par1IconRegister.func_94245_a("Femtocraft:FemtopowerCoil");
		this.coilEdge = par1IconRegister.func_94245_a("Femtocraft:FemtopowerCoilEdge");
		this.border = par1IconRegister.func_94245_a("Femtocraft:FemtopowerBorder");
    }
}
