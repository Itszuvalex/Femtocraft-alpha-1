package femtocraft.api;

import femtocraft.managers.research.TechLevel;

/**
 * 
 * @author Itszuvalex
 * 
 */
public interface IFemtopowerContainer {

	/**
	 * 
	 * @param level
	 *            TechLevel of power
	 * @return True if can accept power of that level
	 */
	public boolean canAcceptPowerOfLevel(TechLevel level);

	/**
	 * 
	 * @return TechLevel of power this machine will give to the given direciton
	 */
	public TechLevel getTechLevel();

	/**
	 * 
	 * @return Current storage amount of container
	 */
	public int getCurrentPower();

	/**
	 * 
	 * @return Max storage amount of container - used for percentage
	 *         approximations during charging
	 */
	public int getMaxPower();

	/**
	 * 
	 * @return Actual fill percentage - for things like damage values, etc.
	 */
	public float getFillPercentage();

	/**
	 * 
	 * @return Fill percentage for purposes of charging - allows tanks and
	 *         whatnot to trick pipes into filling them I.E. return
	 *         getFillPercentage() < .25f ? getFillPercentage() : .25f;
	 */
	public float getFillPercentageForCharging();

	/**
	 * 
	 * @return Fill percentage for purposes of output - allows tanks and other
	 *         TileEntities to trick pipes into not pulling all of their power.
	 */
	public float getFillPercentageForOutput();

	/**
	 * 
	 * @return True if container has room and can accept charging from direction
	 *         @from false otherwise
	 */
	public boolean canCharge();

	/**
	 * 
	 * @param amount
	 *            Amount attempting to charge.
	 * @return Total amount of @amount used to fill internal tank.
	 */
	public int charge(int amount);

	/**
	 * 
	 * @param amount
	 *            Amount of power to drain from internal storage
	 * @return True if all power was consumed, false otherwise. This anticipates
	 *         all or nothing behavior.
	 */
	public boolean consume(int amount);

}
