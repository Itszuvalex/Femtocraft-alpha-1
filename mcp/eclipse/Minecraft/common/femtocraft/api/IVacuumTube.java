package femtocraft.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public interface IVacuumTube {
	
	public boolean isOverflowing();
	
	//Returns true if item is inserted
	public boolean canInsertItem(ItemStack item);
	public boolean insertItem(ItemStack item);
	
	public boolean hasInput();
	public boolean hasOutput();
	
	public ForgeDirection getInput();
	public ForgeDirection getOutput();
	
	public void clearInput();
	public void clearOutput();
	
	public boolean setInput(ForgeDirection input);
	
	public boolean setOutput(ForgeDirection output);
}
