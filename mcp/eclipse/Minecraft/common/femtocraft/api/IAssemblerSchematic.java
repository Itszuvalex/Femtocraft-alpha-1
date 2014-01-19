package femtocraft.api;

import net.minecraft.item.ItemStack;
import femtocraft.managers.FemtocraftAssemblerRecipe;

public interface IAssemblerSchematic {
	
	/**
	 * 
	 * @return The FemtocraftAssemblerRecipe represented with this Schematic
	 */
	FemtocraftAssemblerRecipe getRecipe();
	
	/**
	 * 
	 * @param stack - ItemStack of this schematic.
	 * @return True if this schematic is still valid, false if schematic is no longer valid (i.e. damage).
	 */
	boolean onAssemble(ItemStack stack);
	
	/**
	 * 
	 * @return ItemStack that takes the place of this item, if onAssemble() returns false;
	 */
	ItemStack resultOfBreakdown();

}
