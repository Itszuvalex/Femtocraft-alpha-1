package femtocraft.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

/**
 * @author Itszuvalex
 * 
 */
public interface IVacuumTube {

	/**
	 * 
	 * @return Returns True if tube cannot accept any more items.
	 */
	public boolean isOverflowing();

	/**
	 * 
	 * @param item
	 *            ItemStack to be inserted into tube.
	 * @return True if ItemStack is able to be put into tube.
	 */
	public boolean canInsertItem(ItemStack item);

	/**
	 * 
	 * @param item
	 *            ItemStack to be inserted into tube.
	 * @return True if ItemStack was successfully put into tube.
	 */
	public boolean insertItem(ItemStack item);

	/**
	 * 
	 * @return True if Tube is connected via its Input.
	 */
	public boolean hasInput();

	/**
	 * 
	 * @return True if Tube is connected via its Output;
	 */
	public boolean hasOutput();

	/**
	 * 
	 * @return Direction of Input.
	 */
	public ForgeDirection getInput();

	/**
	 * 
	 * @return Direction of Output;
	 */
	public ForgeDirection getOutput();

	/**
	 * Clears this tube's Input, and clear appropriate flags (and tell other
	 * tubes to disconnect as well, if needed)
	 */
	public void clearInput();

	/**
	 * Clears this tube's Output, and clear appropriate flags (and tell other
	 * tubes to disconnect as well, if needed)
	 */
	public void clearOutput();

	/**
	 * 
	 * @param input
	 *            Direction tube will now accept input from.
	 * @return True if input successfully set.
	 */
	public boolean setInput(ForgeDirection input);

	/**
	 * 
	 * @param output
	 *            Direction tube will now send output to.
	 * @return True if input successfully set.
	 */
	public boolean setOutput(ForgeDirection output);
}
