package femtocraft.managers.research;

import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.MinecraftForge;
import femtocraft.Femtocraft;
import femtocraft.managers.research.TechnologyEvent.TechnologyDiscoveredEvent;
import femtocraft.managers.research.TechnologyEvent.TechnologyResearchedEvent;

public class PlayerResearch {
	public final String username;
	private final HashMap<String, TechnologyStatus> techStatus;
	
	public static class TechnologyNotFoundException extends Exception
	{
		public String errMsg;
		
		public TechnologyNotFoundException(String message)
		{
			errMsg = message;
		}
	}
	
	
	public PlayerResearch(String username)
	{
		this.username = username;
		techStatus = new HashMap<String, TechnologyStatus>();
	}
	
	//---------------------------------------------------------
	
	/**
	 * 
	 * @param name Name of technology to mark as researched
	 * @param force Pass true if you want the named technology to be added if it isn't already discovered.  This will bypass discover checks.  This will not post an event.
	 * @return True if technology successfully marked as researched.  False otherwise.
	 */
	public boolean researchTechnology(String name, boolean force)
	{
		TechnologyStatus tech = techStatus.get(name);
		if(tech == null && !force) return false;
		
		if(tech == null && force == true)
		{
			TechnologyStatus status = techStatus.put(name,  new TechnologyStatus(name));
			if(status == null) return false;
			status.researched = true;
			return true;
		}
		
		TechnologyResearchedEvent event = new TechnologyResearchedEvent(username, Femtocraft.researchManager.getTechnology(name));
		if(!MinecraftForge.EVENT_BUS.post(event))
		{
			tech.researched = true;
			return true;
		}
		return false;
	}
	
	public boolean discoverTechnology(String name)
	{
		TechnologyDiscoveredEvent event = new TechnologyDiscoveredEvent(username, Femtocraft.researchManager.getTechnology(name));
		if(!MinecraftForge.EVENT_BUS.post(event))
		{
			return techStatus.put(name, new TechnologyStatus(name)) != null;
		}
		return false;
	}
	
	public TechnologyStatus getTechnology(String name)
	{
		return techStatus.get(name);
	}
	
	public TechnologyStatus removeTechnology(String name)
	{
		return techStatus.remove(name);
	}
	
	public boolean canDiscoverTechnology(Technology tech)
	{
		if(tech.prerequisites == null) return true;
		
		for(Technology prereq : tech.prerequisites)
		{
			TechnologyStatus ts = techStatus.get(prereq.name);
			if(ts == null) return false;
			if(ts.researched == false) return false;
		}
		
		return true;
	}
	
	public boolean hasDiscoveredTechnology(Technology tech)
	{
		return hasDiscoveredTechnology(tech.name);
	}
	
	public boolean hasDiscoveredTechnology(String tech)
	{
		TechnologyStatus ts = techStatus.get(tech);
		if(ts == null) return false;
		return true;
	}
	
	public boolean hasResearchedTechnology(Technology tech)
	{
		if(tech == null) return true;
		return hasResearchedTechnology(tech.name);
	}
	
	public boolean hasResearchedTechnology(String tech) {
		if(tech == null || tech.equals("")) return true;
		
		TechnologyStatus ts = techStatus.get(tech);
		if(ts == null) return false;
		return ts.researched;
	}

	//---------------------------------------------------------
	
	public void saveToNBTTagCompound(NBTTagCompound compound)
	{
		NBTTagList list = new NBTTagList();
		
		for(TechnologyStatus status : techStatus.values())
		{
			NBTTagCompound cs = new NBTTagCompound();
			cs.setString("techname", status.tech);
			
			NBTTagCompound data = new NBTTagCompound();
			status.saveToNBTTagCompound(data);
			
			compound.setTag("data", data);
			list.appendTag(cs);
		}
		
		compound.setTag("techMap", list);
	}
	
	public void loadFromNBTTagCompound(NBTTagCompound compound)
	{
		NBTTagList list = compound.getTagList("techMap");
		
		for(int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagCompound cs = (NBTTagCompound) list.tagAt(i);
			String techname = cs.getString("techname");
			
			NBTTagCompound data = (NBTTagCompound) cs.getTag("data");
			TechnologyStatus status = new TechnologyStatus(techname);
			status.loadFromNBTTagCompound(data);
			
			techStatus.put(username, status);
		}
	}
}
