package femtocraft;

import cpw.mods.fml.common.network.IGuiHandler;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroDeconstructor;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroFurnace;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroReconstructor;
import femtocraft.industry.containers.ContainerMicroDeconstructor;
import femtocraft.industry.containers.ContainerMicroFurnace;
import femtocraft.industry.containers.ContainerMicroReconstructor;
import femtocraft.industry.gui.GuiMicroDeconstructor;
import femtocraft.industry.gui.GuiMicroFurnace;
import femtocraft.industry.gui.GuiMicroReconstructor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class FemtocraftGuiHandler implements IGuiHandler {

	// Can switch on type of tile entity, or can alternatively switch on type
	// ID.
	// However, going to have to pull tile entity anyways, so might as well just
	// use that.

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity instanceof TileEntityBaseEntityMicroFurnace) {
			return new ContainerMicroFurnace(player.inventory,
					(TileEntityBaseEntityMicroFurnace) tileEntity);
		} else if (tileEntity instanceof TileEntityBaseEntityMicroDeconstructor) {
			return new ContainerMicroDeconstructor(player.inventory,
					(TileEntityBaseEntityMicroDeconstructor) tileEntity);
		} else if (tileEntity instanceof TileEntityBaseEntityMicroReconstructor) {
			return new ContainerMicroReconstructor(player.inventory,
					(TileEntityBaseEntityMicroReconstructor) tileEntity);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity instanceof TileEntityBaseEntityMicroFurnace) {
			return new GuiMicroFurnace(player.inventory,
					(TileEntityBaseEntityMicroFurnace) tileEntity);
		} else if (tileEntity instanceof TileEntityBaseEntityMicroDeconstructor) {
			return new GuiMicroDeconstructor(player.inventory,
					(TileEntityBaseEntityMicroDeconstructor) tileEntity);
		} else if (tileEntity instanceof TileEntityBaseEntityMicroReconstructor) {
			return new GuiMicroReconstructor(player.inventory,
					(TileEntityBaseEntityMicroReconstructor) tileEntity);
		}

		return null;
	}

}
