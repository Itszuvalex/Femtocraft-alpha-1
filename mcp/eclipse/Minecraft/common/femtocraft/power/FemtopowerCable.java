package femtocraft.power;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.power.TileEntity.FemtopowerCableTile;
import femtocraft.proxy.ClientProxyFemtocraft;

public class FemtopowerCable extends FemtopowerContainer {
	public Icon coreBorder;
	public Icon connector;
	public Icon coil;
	public Icon coilEdge;
	public Icon border;
	
	public FemtopowerCable(int par1, Material par2Material) {
		super(par1, par2Material);
		setCreativeTab(Femtocraft.femtocraftTab);
		setBlockBounds();
		func_111022_d(Femtocraft.ID.toLowerCase() +":" + "FemtopowerCableCoil");
		func_111022_d(Femtocraft.ID.toLowerCase() +":" + "FemtopowerCableCoreBorder");
		func_111022_d(Femtocraft.ID.toLowerCase() +":" + "FemtopowerCableConnector");
		func_111022_d(Femtocraft.ID.toLowerCase() +":" + "FemtopowerCableCoil");
		func_111022_d(Femtocraft.ID.toLowerCase() +":" + "FemtopowerCableCoilEdge");
		func_111022_d(Femtocraft.ID.toLowerCase() +":" + "FemtopowerCableBorder");
	}
	
	public void setBlockBounds() {
		this.minX = this.minY = this.minZ = 4.0D/16.0D;
		this.maxX = this.maxY = this.maxZ = 12.0D/16.0D;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new FemtopowerCableTile();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return ClientProxyFemtocraft.FemtopowerCableRenderID;
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
     //   this.field_94336_cN = par1IconRegister.func_94245_a("Femtocraft:FemtopowerCable");
	//	this.core = par1IconRegister.func_94245_a("Femtocraft:FemtopowerCableCore");
		this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "FemtopowerCableCoil");
		this.coreBorder = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "FemtopowerCableCoreBorder");
		this.connector = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "FemtopowerCableConnector");
		this.coil = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "FemtopowerCableCoil");
		this.coilEdge = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "FemtopowerCableCoilEdge");
		this.border = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "FemtopowerCableBorder");
    }
}
