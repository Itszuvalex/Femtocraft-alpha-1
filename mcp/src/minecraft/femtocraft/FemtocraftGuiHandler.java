package femtocraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import femtocraft.industry.containers.ContainerMicroDeconstructor;
import femtocraft.industry.containers.ContainerMicroFurnace;
import femtocraft.industry.containers.ContainerMicroReconstructor;
import femtocraft.industry.gui.GuiMicroDeconstructor;
import femtocraft.industry.gui.GuiMicroFurnace;
import femtocraft.industry.gui.GuiMicroReconstructor;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroDeconstructor;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroFurnace;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroReconstructor;
import femtocraft.power.containers.ContainerMicroCube;
import femtocraft.power.containers.ContainerNanoCube;
import femtocraft.power.gui.GuiMicroCube;
import femtocraft.power.gui.GuiNanoCube;
import femtocraft.power.tiles.TileEntityNanoCubePort;
import femtocraft.power.tiles.TileEntityPowerMicroCube;
import femtocraft.research.containers.ContainerResearchConsole;
import femtocraft.research.gui.GuiResearch;
import femtocraft.research.gui.GuiResearchConsole;
import femtocraft.research.tiles.TileEntityResearchComputer;
import femtocraft.research.tiles.TileResearchConsole;

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
		} else if (tileEntity instanceof TileEntityNanoCubePort) {
			return new ContainerNanoCube((TileEntityNanoCubePort) tileEntity);
		} else if (tileEntity instanceof TileEntityPowerMicroCube) {
			return new ContainerMicroCube((TileEntityPowerMicroCube) tileEntity);
		} else if (tileEntity instanceof TileResearchConsole) {
			return new ContainerResearchConsole(player.inventory,
					(TileResearchConsole) tileEntity);
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
		} else if (tileEntity instanceof TileEntityNanoCubePort) {
			return new GuiNanoCube((TileEntityNanoCubePort) tileEntity);
		} else if (tileEntity instanceof TileEntityResearchComputer) {
			return new GuiResearch(player.username);
		} else if (tileEntity instanceof TileEntityPowerMicroCube) {
			return new GuiMicroCube((TileEntityPowerMicroCube) tileEntity);
		} else if (tileEntity instanceof TileResearchConsole) {
			return new GuiResearchConsole(player.inventory,
					(TileResearchConsole) tileEntity);
		}

		return null;
	}

}
