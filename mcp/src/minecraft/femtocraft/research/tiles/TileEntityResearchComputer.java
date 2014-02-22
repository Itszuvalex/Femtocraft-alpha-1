package femtocraft.research.tiles;

import net.minecraft.entity.player.EntityPlayer;
import femtocraft.Femtocraft;
import femtocraft.core.tiles.TileEntityBase;

public class TileEntityResearchComputer extends TileEntityBase {
	public TileEntityResearchComputer() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see femtocraft.core.tiles.TileEntityBase#hasGUI()
	 */
	@Override
	public boolean hasGUI() {
		return true;
	}

}
