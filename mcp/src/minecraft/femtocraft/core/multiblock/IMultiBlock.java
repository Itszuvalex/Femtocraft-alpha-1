package femtocraft.core.multiblock;

import net.minecraft.world.World;

/**
 * 
 * @author Itszuvalex Helper
 * 
 * @explanation interface for better structure of MultiBlock behavior classes
 * 
 */
public interface IMultiBlock {

	/**
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return True if this MultiBlock can form in the given world, with the
	 *         block at x,y,z as its controller block.
	 */
	boolean canForm(World world, int x, int y, int z);

	/**
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return True if this MultiBlock can form in the given world, with the
	 *         block at x,y,z as its controller block. This will return false if
	 *         any blocks that would be used are already in a MultiBlock.
	 */
	boolean canFormStrict(World world, int x, int y, int z);

	/**
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param c_x
	 * @param c_y
	 * @param c_z
	 * @return True if the block at x, y, z is in the MultiBlock with the
	 *         controller at c_x, c_y, c_z
	 */
	boolean isBlockInMultiBlock(World world, int x, int y, int z, int c_x,
			int c_y, int c_z);

	/**
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return True if this MultiBlock correctly forms in the given world, with
	 *         the block at x,y,z as the controller block.
	 */
	boolean formMultiBlock(World world, int x, int y, int z);

	/**
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return True if this MultiBlock correctly forms in the given world, using
	 *         the block given at x,y,z anywhere in the MultiBlock
	 */
	boolean formMultiBlockWithBlock(World world, int x, int y, int z);

	/**
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return True if this MultiBlock breaks with no errors in the given world,
	 *         using the block at x,y,z as the controller block.
	 */
	boolean breakMultiBlock(World world, int x, int y, int z);
}
