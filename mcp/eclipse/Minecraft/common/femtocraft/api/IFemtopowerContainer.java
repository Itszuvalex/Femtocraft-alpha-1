/**
 * 
 */
package femtocraft.api;

import net.minecraftforge.common.ForgeDirection;

/**
 * @author Itszuvalex
 *
 */
public interface IFemtopowerContainer {

	public int getCurrentPower();
	
	public int getMaxPower();
	
	public int charge(ForgeDirection from, int amount);
	
}
