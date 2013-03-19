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

	//Returns current storage amount of container
	public int getCurrentPower();
	
	//Returns max storage amount of container - used for percentage approximations during charging
	public int getMaxPower();
	
	//Returns actual fill percentage - for things like damage values, etc.
	public float getFillPercentage();
	
	//Returns fill percentage for purposes of charging - allows tanks and whatnot to trick pipes into filling them
	//I.E. return getFillPercentage() < .25f ?  getFillPercentage() : .25f;
	public float getFillPercentageForCharging(ForgeDirection from);
	
	//Returns true if container has room and can accept charging from given direction, false otherwise
	public boolean canCharge(ForgeDirection from);
	
	//Increases internal storage by up to amount, from given direction.
	//Returns total amount used to charge
	public int charge(ForgeDirection from, int amount);
	
}
