package femtocraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import femtocraft.industry.TileEntity.MicroDeconstructorTile;
import femtocraft.industry.TileEntity.MicroFurnaceTile;
import femtocraft.industry.TileEntity.MicroReconstructorTile;
import femtocraft.industry.containers.ContainerMicroDeconstructor;
import femtocraft.industry.containers.ContainerMicroFurnace;
import femtocraft.industry.containers.ContainerMicroReconstructor;
import femtocraft.industry.gui.GuiMicroDeconstructor;
import femtocraft.industry.gui.GuiMicroFurnace;
import femtocraft.industry.gui.GuiMicroReconstructor;

public class GuiHandlerFemtocraft implements IGuiHandler {

	// Can switch on type of tile entity, or can alternatively switch on type
	// ID.
	// However, going to have to pull tile entity anyways, so might as well just
	// use that.

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity instanceof MicroFurnaceTile) {
			return new ContainerMicroFurnace(player.inventory,
					(MicroFurnaceTile) tileEntity);
		} else if (tileEntity instanceof MicroDeconstructorTile) {
			return new ContainerMicroDeconstructor(player.inventory,
					(MicroDeconstructorTile) tileEntity);
		} else if (tileEntity instanceof MicroReconstructorTile) {
			return new ContainerMicroReconstructor(player.inventory,
					(MicroReconstructorTile) tileEntity);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity instanceof MicroFurnaceTile) {
			return new GuiMicroFurnace(player.inventory,
					(MicroFurnaceTile) tileEntity);
		} else if (tileEntity instanceof MicroDeconstructorTile) {
			return new GuiMicroDeconstructor(player.inventory,
					(MicroDeconstructorTile) tileEntity);
		} else if (tileEntity instanceof MicroReconstructorTile) {
			return new GuiMicroReconstructor(player.inventory,
					(MicroReconstructorTile) tileEntity);
		}

		return null;
	}

}
