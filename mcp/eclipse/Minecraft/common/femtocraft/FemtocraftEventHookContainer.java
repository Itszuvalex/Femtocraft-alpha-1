package femtocraft;

import net.minecraft.util.Icon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class FemtocraftEventHookContainer {
	@ForgeSubscribe
	public void preStitch(TextureStitchEvent.Pre event)
	{
//		event.map.registerIcon(Femtocraft.ID.toLowerCase() +":" + "mass_still");
//		event.map.registerIcon(Femtocraft.ID.toLowerCase() +":" + "mass_flow");
	}
	
	@ForgeSubscribe
	public void postStitch(TextureStitchEvent.Post event)
	{
		//Set Mass Icons
//		Femtocraft.mass.setStillIcon(event.map.getAtlasSprite(Femtocraft.ID.toLowerCase() +":" + "mass_still"));
//		Femtocraft.mass.setFlowingIcon(event.map.getAtlasSprite(Femtocraft.ID.toLowerCase() +":" + "mass_flow"));
	}
}
