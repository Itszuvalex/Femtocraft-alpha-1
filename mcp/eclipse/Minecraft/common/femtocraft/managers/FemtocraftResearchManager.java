package femtocraft.managers;

import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import femtocraft.research.PlayerResearch;
import femtocraft.research.Technology;
import femtocraft.research.TechnologyStatus;

public class FemtocraftResearchManager {
	private HashMap<String, Technology> technologies;
	private HashMap<String, PlayerResearch> playerData;
	
	public FemtocraftResearchManager()
	{
		technologies = new HashMap<String, Technology>();
		playerData = new HashMap<String, PlayerResearch>();
	}
	
	//--------------------------------------------------
	
	public Technology addTechnology(Technology tech)
	{
		return technologies.put(tech.name, tech);
	}
	
	public boolean removeTechnology(Technology tech)
	{
		return technologies.remove(tech.name) != null;
	}
	
	public Technology getTechnology(String name)
	{
		return technologies.get(name);
	}
	
	//--------------------------------------------------

	public PlayerResearch addPlayerResearch(String username)
	{
		//Return false if playerData already exists
		if(playerData.containsKey(username)) return null;
		PlayerResearch r = new PlayerResearch(username);
		
		addFreeResearches(r);
		
		playerData.put(username, r);
		return r;
	}
	
	public boolean removePlayerResearch(String username)
	{
		return playerData.remove(username) != null;
	}
	
	public PlayerResearch getPlayerResearch(String username)
	{
		return playerData.get(username);
	}
	
	//--------------------------------------------------
	
	private void addFreeResearches(PlayerResearch research)
	{
		for(Technology t : technologies.values())
		{
			if(t.prerequisites == null)
			{
				research.addTechnology(t.name);
			}
		}
	}
	
	//--------------------------------------------------
	
	public void saveToNBTTagCompound(NBTTagCompound compound)
	{
		NBTTagList list = new NBTTagList();
		
		for(PlayerResearch status : playerData.values())
		{
			NBTTagCompound cs = new NBTTagCompound();
			cs.setString("username", status.username);
			
			NBTTagCompound data = new NBTTagCompound();
			status.saveToNBTTagCompound(data);
			
			compound.setTag("data", data);
			list.appendTag(cs);
		}
		
		compound.setTag("playerData", list);
	}
	
	public void loadFromNBTTagCompound(NBTTagCompound compound)
	{
		NBTTagList list = compound.getTagList("playerData");
		
		for(int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagCompound cs = (NBTTagCompound) list.tagAt(i);
			String username = cs.getString("username");
			
			NBTTagCompound data = (NBTTagCompound) cs.getTag("data");
			PlayerResearch status = new PlayerResearch(username);
			status.loadFromNBTTagCompound(data);
			
			playerData.put(username, status);
		}
	}
	
	public boolean save()
	{
		NBTTagCompound data = new NBTTagCompound();
		saveToNBTTagCompound(data);
		
		return true;
	}
	
	public boolean load()
	{
		return true;
	}
}
