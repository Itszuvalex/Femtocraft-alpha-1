package femtocraft.core.multiblock;

/**
 * 
 * @author Chris
 * @description Interface for MultiBlock components for easy implementation.
 */
public interface IMultiBlockComponent {
	/**
	 * 
	 * @return True if this is in valid MultiBlock
	 */
	boolean isValidMultiBlock();

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return True if correctly forms, given controller block at x,y,z.
	 */
	boolean formMultiBlock(int x, int y, int z);
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return True if breaks without errors, given controller block at x,y,z.
	 */
	boolean breakMultiBlock(int x, int y, int z);
}
