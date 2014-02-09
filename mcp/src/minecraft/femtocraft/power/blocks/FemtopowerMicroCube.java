package femtocraft.power.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.api.FemtopowerContainer;
import femtocraft.api.IInterfaceDevice;
import femtocraft.power.TileEntity.FemtopowerMicroCubeTile;

public class FemtopowerMicroCube extends FemtopowerTileContainer {
	public Icon outputSide;
	public Icon inputSide;
	
	public FemtopowerMicroCube(int par1) {
		super(par1, Material.iron);
		setCreativeTab(Femtocraft.femtocraftTab);
		setUnlocalizedName("microCube");
		setHardness(3.f);
		setStepSound(Block.soundMetalFootstep);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new FemtopowerMicroCubeTile();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess access, int x,
			int y, int z, int side) {
		TileEntity te = access.getBlockTileEntity(x, y, z);
		
		if(te == null) return this.blockIcon;
		if(!(te instanceof FemtopowerMicroCubeTile)) return this.blockIcon;
		FemtopowerMicroCubeTile cube = (FemtopowerMicroCubeTile)te;
		return cube.outputs[side] ? outputSide : inputSide;
	}

	
//	@Override
//	public boolean isOpaqueCube() {
//		return false;
//	}

//	@Override
//	public boolean renderAsNormalBlock() {
//		return false;
//	}
//	
//	@Override
//	public int getRenderType() {
//		return ClientProxyFemtocraft.FemtopowerMicroCubeRenderID;
//	}
//	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
		
		if(par1World.isRemote) return true;
		ItemStack item = par5EntityPlayer.getCurrentEquippedItem();
		if(item != null && item.getItem() instanceof IInterfaceDevice)
		{
		
			FemtopowerMicroCubeTile tile = (FemtopowerMicroCubeTile)par1World.getBlockTileEntity(par2, par3, par4);
			if(tile != null && !par1World.isRemote)
			{
				ForgeDirection dir = ForgeDirection.getOrientation(par6);
				
				if(par5EntityPlayer.isSneaking())
				{
					dir = dir.getOpposite();
				}
				
				tile.onSideActivate(dir);
				par1World.markBlockForUpdate(par2, par3, par4);
			}
			return true;
		}
		return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer,
				par6, par7, par8, par9);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = inputSide = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()+":" + "MicroCube_input");
		outputSide = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()+":" + "MicroCube_output");
//		side = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "MicroCube_side");
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
			EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase,
				par6ItemStack);
		
		
		TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
		if(te == null) return;
		if(!(te instanceof FemtopowerMicroCubeTile)) return;
		
		FemtopowerMicroCubeTile cube = (FemtopowerMicroCubeTile) te;
		
		NBTTagCompound nbt = par6ItemStack.stackTagCompound;
		if(nbt == null) return;
		
		NBTTagCompound power = (NBTTagCompound) par6ItemStack.stackTagCompound.getTag("power");
		if(power == null) return;
		
		FemtopowerContainer cont = FemtopowerContainer.createFromNBT(power);
		
		cube.setCurrentStorage(cont.getCurrentPower());
	}

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x,
			int y, int z) {
		
		// TODO Add check for owner / assistant / op
		if(!world.isRemote)
		{
			
		}

		return super.removeBlockByPlayer(world, player, x, y, z);
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4,
			int par5, int par6) {
		TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
		if(te != null && te instanceof FemtopowerMicroCubeTile)
		{
			FemtopowerMicroCubeTile cube = (FemtopowerMicroCubeTile) te;
			
			ItemStack stack = new ItemStack(Block.blocksList[par5]);
			if(!stack.hasTagCompound())
			{
				stack.stackTagCompound = new NBTTagCompound();
			}
			
			FemtopowerContainer cont = new FemtopowerContainer(cube.getTechLevel(ForgeDirection.UNKNOWN), cube.getMaxPower());
			cont.setCurrentPower(cube.getCurrentPower());
			
			NBTTagCompound power = new NBTTagCompound();
			cont.saveToNBT(power);
			
			stack.stackTagCompound.setTag("power", power);
			
			par1World.spawnEntityInWorld(new EntityItem(par1World, par2 + .5d, par3 + .5d, par4 + .5d, stack));
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}
}
