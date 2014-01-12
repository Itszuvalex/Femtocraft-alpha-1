package femtocraft;

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
}
