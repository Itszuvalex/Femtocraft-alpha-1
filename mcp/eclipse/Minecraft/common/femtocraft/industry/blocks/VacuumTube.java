package femtocraft.industry.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.industry.TileEntity.VacuumTubeTile;
import femtocraft.proxy.ClientProxyFemtocraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class VacuumTube extends BlockContainer {
	public Icon indicatorIcon;
	public Icon straightIcon;
	public Icon straightInsetIcon;
	public Icon turnIcon;
	public Icon turnInsetIcon;
	public Icon endIcon;
	public Icon endInsetIcon;

	public VacuumTube(int id) {
		super(id, Material.iron);
        setUnlocalizedName("FemtocraftVacuumTube");
        setHardness(3.5f);
        setStepSound(Block.soundMetalFootstep);
        setCreativeTab(Femtocraft.femtocraftTab);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		// TODO Auto-generated method stub
		return new VacuumTubeTile();
	}


	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		
		
		super.onBlockAdded(par1World, par2, par3, par4);
	}
	

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3,
			int par4, int par5) {
		TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);
		if(tile != null)
		{
			if(tile instanceof VacuumTubeTile)
			{
				VacuumTubeTile tube = (VacuumTubeTile)tile;
				
				((VacuumTubeTile) tile).searchForMissingConnection();
			}
		}
		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
			EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);
		if(tile != null)
		{
			if(tile instanceof VacuumTubeTile)
			{
				VacuumTubeTile tube = (VacuumTubeTile)tile;
				
				((VacuumTubeTile) tile).searchForMissingConnection();
			}
		}
		super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase,
				par6ItemStack);
	}

	@Override
	public void onPostBlockPlaced(World par1World, int par2, int par3,
			int par4, int par5) {
		TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);
		if(tile != null)
		{
			if(tile instanceof VacuumTubeTile)
			{
				VacuumTubeTile tube = (VacuumTubeTile)tile;
				
				((VacuumTubeTile) tile).searchForFullConnections();
			}
		}
		super.onPostBlockPlaced(par1World, par2, par3, par4, par5);
	}

	@Override
	public void onNeighborTileChange(World world, int x, int y, int z,
			int tileX, int tileY, int tileZ) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile != null)
		{
			if(tile instanceof VacuumTubeTile)
			{
				VacuumTubeTile tube = (VacuumTubeTile)tile;
				
				((VacuumTubeTile) tile).searchForMissingConnection();
			}
		}
		super.onNeighborTileChange(world, x, y, z, tileX, tileY, tileZ);
	}


	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4,
			int par5, int par6) {
		
		TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);
		if(tile != null)
		{
			if(tile instanceof VacuumTubeTile)
			{
				VacuumTubeTile tube = (VacuumTubeTile)tile;
				
				((VacuumTubeTile) tile).onBlockBreak();
			}
		}
		
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return ClientProxyFemtocraft.FemtocraftVacuumTubeRenderID;
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
		
		TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);
		if(tile != null)
		{
			if(tile instanceof VacuumTubeTile)
			{
				VacuumTubeTile tube = (VacuumTubeTile)tile;
				
				((VacuumTubeTile) tile).searchForConnection();
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3,
			int par4, Entity par5Entity) {
		super.onEntityCollidedWithBlock(par1World, par2, par3, par4, par5Entity);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		indicatorIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "VacuumTube_indicator");
        straightIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "VacuumTube_side_straight");
        straightInsetIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "VacuumTube_side_straight_inset");
        turnIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "VacuumTube_side_curved");
        turnInsetIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "VacuumTube_side_curved_inset");
        endIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "VacuumTube_end");
        endInsetIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "VacuumTube_end_inset");
	}

}
