package femtocraft;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class FemtocraftUtils {

	public static int indexOfForgeDirection(ForgeDirection dir)
	{
		return Arrays.asList(ForgeDirection.VALID_DIRECTIONS).indexOf(dir);
//		switch(dir)
//		{
//		case UP:
//			return 1;
//		case DOWN:
//			return 0;
//		case NORTH:
//			return 2;
//		case SOUTH:
//			return 3;
//		case EAST:
//			return 5;
//		case WEST:
//			return 4;
//		default:
//			return -1;
//		}
	}
	
	public static boolean isPlayerOnline(String username)
	{
		return Arrays.asList(MinecraftServer.getServer().getAllUsernames()).contains(username);
	}
	
	public static EntityPlayer getPlayer(String username)
	{
		return Minecraft.getMinecraft().theWorld.getPlayerEntityByName(username);
	}
	
	public static int compareItem(ItemStack cur, ItemStack in)
	{
		if(cur == null && in != null) return -1;
		if(cur != null && in == null) return 1;
		if(cur == null && in == null) return 0;
		
		if(cur.itemID < in.itemID) return -1;
		if(cur.itemID > in.itemID) return 1;
		
		int damage = cur.getItemDamage();
		int indamage = in.getItemDamage();
		if((damage != OreDictionary.WILDCARD_VALUE) || (indamage != OreDictionary.WILDCARD_VALUE))
		{
			if(damage < indamage) return -1;
			if(damage > indamage) return 1;
		}
		
		return 0;
	}
	
	public static boolean placeItem(ItemStack item, ItemStack[] slots, int[] restrictions)
    {
    	if(item == null) return true;
    	//Attempt to combine, first
    	int amount = item.stackSize;

    	for(int i = 0; i < slots.length; ++i)
    	{
    		boolean restricted = false;
    		for(int r : restrictions)
    		{
    			if(i == r) 
    			{
    				restricted = true;
    				break;
    			}
    		}
    		if(restricted) continue;
    		
    		if(slots[i] != null && slots[i].isItemEqual(item))
    		{
    			ItemStack slot = slots[i];
    			int room = (slot.getMaxStackSize() - slot.stackSize);
    			if(room < amount)
    			{
    				slot.stackSize += room;
    				amount -= room;
    			}
    			else
    			{
    				slot.stackSize += amount;
    				return true;
    			}
    		}
    	}
    	
    	//Place into null locations
    	for(int i = 0; i < slots.length; ++i)
    	{
    		boolean restricted = false;
    		for(int r : restrictions)
    		{
    			if(i == r) 
    			{
    				restricted = true;
    				break;
    			}
    		}
    		if(restricted) continue;
    		
    		if(slots[i] == null)
    		{
    			slots[i] = item.copy();
    			slots[i].stackSize = amount;
    			return true;
    		}
    	}
    	
    	return false;
    }
	
	public static boolean removeItem(ItemStack item, ItemStack[] slots, int[] restrictions)
    {
    	if(item == null) return true;
    	//Attempt to combine, first
    	int amountLeftToRemove = item.stackSize;

    	for(int i = 0; i < slots.length; ++i)
    	{
    		boolean restricted = false;
    		for(int r : restrictions)
    		{
    			if(i == r) 
    			{
    				restricted = true;
    				break;
    			}
    		}
    		if(restricted) continue;
    		
    		if(slots[i] != null && slots[i].isItemEqual(item))
    		{
    			ItemStack slot = slots[i];
    			int amount = slot.stackSize;
    			if(amount < amountLeftToRemove)
    			{
    				slots[i] = null;
    				amountLeftToRemove-=amount;
    			}
    			else
    			{
    				slot.stackSize -= amountLeftToRemove;
    				return true;
    			}
    		}
    	}
    	return false;
    }
	
	public static String capitalize(String input)
	{
		return input.substring(0, 1).toUpperCase() +  input.substring(1);
	}
}
