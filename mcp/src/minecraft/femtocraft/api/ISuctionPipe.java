package femtocraft.api;

import net.minecraftforge.fluids.IFluidHandler;

/**
 * 
 * @author Itszuvalex
 *
 */
public interface ISuctionPipe extends IFluidHandler {
	
	/**
	 * 
	 * @return Pressure of this pipe.  Liquids will flow from high pressure to low pressure pipes.
	 */
	public float getPressure();
}
