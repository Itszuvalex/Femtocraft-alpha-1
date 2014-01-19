package femtocraft.api;

import net.minecraft.item.ItemStack;
import femtocraft.managers.FemtocraftAssemblerRecipe;

public interface IAssemblerSchematic {
	
	/**
	 * @param stack The IAssemblerSchematic stack
	 * @return The FemtocraftAssemblerRecipe represented with this Schematic
	 */
	FemtocraftAssemblerRecipe getRecipe(ItemStack stack);
	
	/**
	 * 
	 * @param stack ItemStack to encode recipe into
	 * @param recipe Recipe to encode into itemStack
	 * @return True if recipe successfully encoded, false for any other reason (itemStack already has recipe?, failed to read NBT, etc.)
	 */
	boolean setRecipe(ItemStack stack, FemtocraftAssemblerRecipe recipe);
	
	/**
	 * 
	 * @param stack ItemStack to find uses remaining of - generally is remaining damage
	 * @return How many uses remain, or -1 if infinite.
	 */
	int usesRemaining(ItemStack stack);
	
	/**
	 * 
	 * @param stack - ItemStack of this schematic.
	 * @return True if this schematic is still valid, false if schematic is no longer valid (i.e. damage).
	 */
	boolean onAssemble(ItemStack stack);
	
	/**
	 * @param stack ItemStack that is breaking down
	 * @return ItemStack that takes the place @stack, if onAssemble() returns false;
	 */
	ItemStack resultOfBreakdown(ItemStack stack);

}
