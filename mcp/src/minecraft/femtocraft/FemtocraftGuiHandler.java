package femtocraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import femtocraft.industry.containers.ContainerEncoder;
import femtocraft.industry.containers.ContainerMicroDeconstructor;
import femtocraft.industry.containers.ContainerMicroFurnace;
import femtocraft.industry.containers.ContainerMicroReconstructor;
import femtocraft.industry.containers.ContainerNanoInnervator;
import femtocraft.industry.gui.GuiEncoder;
import femtocraft.industry.gui.GuiMicroDeconstructor;
import femtocraft.industry.gui.GuiMicroFurnace;
import femtocraft.industry.gui.GuiMicroReconstructor;
import femtocraft.industry.gui.GuiNanoInnervator;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroDeconstructor;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroFurnace;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroReconstructor;
import femtocraft.industry.tiles.TileEntityEncoder;
import femtocraft.industry.tiles.TileEntityNanoInnervator;
import femtocraft.power.containers.ContainerFemtoCube;
import femtocraft.power.containers.ContainerMicroCube;
import femtocraft.power.containers.ContainerNanoCube;
import femtocraft.power.gui.GuiFemtoCube;
import femtocraft.power.gui.GuiMicroCube;
import femtocraft.power.gui.GuiNanoCube;
import femtocraft.power.tiles.TileEntityFemtoCubePort;
import femtocraft.power.tiles.TileEntityNanoCubePort;
import femtocraft.power.tiles.TileEntityPowerMicroCube;
import femtocraft.research.containers.ContainerResearchConsole;
import femtocraft.research.gui.GuiResearch;
import femtocraft.research.gui.GuiResearchConsole;
import femtocraft.research.tiles.TileEntityResearchComputer;
import femtocraft.research.tiles.TileEntityResearchConsole;

public class FemtocraftGuiHandler implements IGuiHandler {

	// Can switch on type of tile entity, or can alternatively switch on type
	// ID.
	// However, going to have to pull tile entity anyways, so might as well just
	// use that.

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity instanceof TileEntityNanoInnervator) {
			return new ContainerNanoInnervator(player.inventory,
					(TileEntityNanoInnervator) tileEntity);
		} else if (tileEntity instanceof TileEntityBaseEntityMicroFurnace) {
			return new ContainerMicroFurnace(player.inventory,
					(TileEntityBaseEntityMicroFurnace) tileEntity);
		} else if (tileEntity instanceof TileEntityBaseEntityMicroDeconstructor) {
			return new ContainerMicroDeconstructor(player.inventory,
					(TileEntityBaseEntityMicroDeconstructor) tileEntity);
		} else if (tileEntity instanceof TileEntityBaseEntityMicroReconstructor) {
			return new ContainerMicroReconstructor(player.inventory,
					(TileEntityBaseEntityMicroReconstructor) tileEntity);
		} else if (tileEntity instanceof TileEntityEncoder) {
			return new ContainerEncoder(player.inventory,
					(TileEntityEncoder) tileEntity);
		} else if (tileEntity instanceof TileEntityFemtoCubePort) {
			return new ContainerFemtoCube((TileEntityFemtoCubePort) tileEntity);
		} else if (tileEntity instanceof TileEntityNanoCubePort) {
			return new ContainerNanoCube((TileEntityNanoCubePort) tileEntity);
		} else if (tileEntity instanceof TileEntityPowerMicroCube) {
			return new ContainerMicroCube((TileEntityPowerMicroCube) tileEntity);
		} else if (tileEntity instanceof TileEntityResearchConsole) {
			return new ContainerResearchConsole(player.inventory,
					(TileEntityResearchConsole) tileEntity);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity instanceof TileEntityNanoInnervator) {
			return new GuiNanoInnervator(player.inventory,
					(TileEntityNanoInnervator) tileEntity);
		} else if (tileEntity instanceof TileEntityBaseEntityMicroFurnace) {
			return new GuiMicroFurnace(player.inventory,
					(TileEntityBaseEntityMicroFurnace) tileEntity);
		} else if (tileEntity instanceof TileEntityBaseEntityMicroDeconstructor) {
			return new GuiMicroDeconstructor(player.inventory,
					(TileEntityBaseEntityMicroDeconstructor) tileEntity);
		} else if (tileEntity instanceof TileEntityBaseEntityMicroReconstructor) {
			return new GuiMicroReconstructor(player.inventory,
					(TileEntityBaseEntityMicroReconstructor) tileEntity);
		} else if (tileEntity instanceof TileEntityEncoder) {
			return new GuiEncoder(player.inventory,
					(TileEntityEncoder) tileEntity);
		} else if (tileEntity instanceof TileEntityFemtoCubePort) {
			return new GuiFemtoCube((TileEntityFemtoCubePort) tileEntity);
		} else if (tileEntity instanceof TileEntityNanoCubePort) {
			return new GuiNanoCube((TileEntityNanoCubePort) tileEntity);
		} else if (tileEntity instanceof TileEntityResearchComputer) {
			return new GuiResearch(player.username);
		} else if (tileEntity instanceof TileEntityPowerMicroCube) {
			return new GuiMicroCube((TileEntityPowerMicroCube) tileEntity);
		} else if (tileEntity instanceof TileEntityResearchConsole) {
			return new GuiResearchConsole(player.inventory,
					(TileEntityResearchConsole) tileEntity);
		}

		return null;
	}

}
