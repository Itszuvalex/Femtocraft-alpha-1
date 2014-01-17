package femtocraft;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;

public class FemtocraftEventHookContainer {
	@ForgeSubscribe
	public void load(WorldEvent.Load event)
	{
		if(event.world.isRemote) return;
		Femtocraft.researchManager.load(event.world);
	}

	@ForgeSubscribe
	public void save(WorldEvent.Save event)
	{
		if(event.world.isRemote) return;
		Femtocraft.researchManager.save(event.world);
	}
}
