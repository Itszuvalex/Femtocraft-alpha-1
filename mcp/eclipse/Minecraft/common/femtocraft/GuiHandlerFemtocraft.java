package femtocraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import femtocraft.industry.ContainerMicroFurnace;
import femtocraft.industry.TileEntity.MicroFurnaceTile;
import femtocraft.industry.gui.GuiMicroFurnace;

public class GuiHandlerFemtocraft implements IGuiHandler {

	//Can switch on type of tile entity, or can alternatively switch on type ID.
	//However, going to have to pull tile entity anyways, so might as well just use that.
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity instanceof MicroFurnaceTile) {
			return new ContainerMicroFurnace(player.inventory, (MicroFurnaceTile)tileEntity);
		}
		
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(tileEntity instanceof MicroFurnaceTile) {
			return new GuiMicroFurnace(player.inventory, (MicroFurnaceTile)tileEntity);
		}
		
		
		return null;
	}

}
