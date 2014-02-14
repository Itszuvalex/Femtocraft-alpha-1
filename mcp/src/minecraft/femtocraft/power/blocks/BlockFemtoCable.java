package femtocraft.power.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.power.tiles.TileEntityFemtoCable;
import femtocraft.proxy.ProxyClient;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFemtoCable extends BlockMicroCable {
	public BlockFemtoCable(int par1, Material par2Material) {
		super(par1, par2Material);
		setCreativeTab(Femtocraft.femtocraftTab);
		setUnlocalizedName("blockFemtoCable");
		setHardness(1.0f);
		setStepSound(Block.soundStoneFootstep);
		setBlockBounds();
	}

	public void setBlockBounds() {
		this.minX = this.minY = this.minZ = 4.0D / 16.0D;
		this.maxX = this.maxY = this.maxZ = 12.0D / 16.0D;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityFemtoCable();
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "FemtopowerCableCoil");
		this.coreBorder = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" +
				"FemtopowerCableCoreBorder");
		this.connector = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "FemtopowerCableConnector");
		this.coil = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "FemtopowerCableCoil");
		this.coilEdge = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "FemtopowerCableCoilEdge");
		this.border = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "FemtopowerCableBorder");
	}
}
