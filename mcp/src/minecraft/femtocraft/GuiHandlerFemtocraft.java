package femtocraft;

import cpw.mods.fml.common.network.IGuiHandler;
import femtocraft.industry.tiles.TileEntityMicroDeconstructor;
import femtocraft.industry.tiles.TileEntityMicroFurnace;
import femtocraft.industry.tiles.TileEntityMicroReconstructor;
import femtocraft.industry.containers.ContainerMicroDeconstructor;
import femtocraft.industry.containers.ContainerMicroFurnace;
import femtocraft.industry.containers.ContainerMicroReconstructor;
import femtocraft.industry.gui.GuiMicroDeconstructor;
import femtocraft.industry.gui.GuiMicroFurnace;
import femtocraft.industry.gui.GuiMicroReconstructor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandlerFemtocraft implements IGuiHandler {

	// Can switch on type of tile entity, or can alternatively switch on type
	// ID.
	// However, going to have to pull tile entity anyways, so might as well just
	// use that.

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity instanceof TileEntityMicroFurnace) {
			return new ContainerMicroFurnace(player.inventory,
					(TileEntityMicroFurnace) tileEntity);
		} else if (tileEntity instanceof TileEntityMicroDeconstructor) {
			return new ContainerMicroDeconstructor(player.inventory,
					(TileEntityMicroDeconstructor) tileEntity);
		} else if (tileEntity instanceof TileEntityMicroReconstructor) {
			return new ContainerMicroReconstructor(player.inventory,
					(TileEntityMicroReconstructor) tileEntity);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity instanceof TileEntityMicroFurnace) {
			return new GuiMicroFurnace(player.inventory,
					(TileEntityMicroFurnace) tileEntity);
		} else if (tileEntity instanceof TileEntityMicroDeconstructor) {
			return new GuiMicroDeconstructor(player.inventory,
					(TileEntityMicroDeconstructor) tileEntity);
		} else if (tileEntity instanceof TileEntityMicroReconstructor) {
			return new GuiMicroReconstructor(player.inventory,
					(TileEntityMicroReconstructor) tileEntity);
		}

		return null;
	}

}
