package femtocraft.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.logging.Level;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import femtocraft.Femtocraft;
import femtocraft.research.PlayerResearch;
import femtocraft.research.Technology;
import femtocraft.research.TechnologyEvent.TechnologyAddedEvent;

public class FemtocraftResearchManager {
	private HashMap<String, Technology> technologies;
	private HashMap<String, PlayerResearch> playerData;
	
	private final String FILENAME = "FemtocraftResearch.dat";
	
	private String lastWorldLoaded = "";
	
	public FemtocraftResearchManager()
	{
		technologies = new HashMap<String, Technology>();
		playerData = new HashMap<String, PlayerResearch>();
	}
	
	//--------------------------------------------------
	
	public boolean addTechnology(Technology tech)
	{
		TechnologyAddedEvent event = new TechnologyAddedEvent(tech);
		if(!MinecraftForge.EVENT_BUS.post(event))
		{
			return technologies.put(tech.name, tech) != null;
		}
		return false;
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
	
	public boolean hasPlayerDiscoveredTechnology(String username, Technology tech)
	{
		return hasPlayerDiscoveredTechnology(username, tech.name);
	}
	
	public boolean hasPlayerDiscoveredTechnology(String username, String tech)
	{
		PlayerResearch pr = playerData.get(username);
		if(pr == null) return false;
		return pr.hasDiscoveredTechnology(tech);
	}
	
	//--------------------------------------------------
	
	public boolean hasPlayerResearchedTechnology(String username, Technology tech)
	{
		return hasPlayerResearchedTechnology(username, tech.name);
	}
	
	public boolean hasPlayerResearchedTechnology(String username, String tech)
	{
		PlayerResearch pr = playerData.get(username);
		if(pr == null) return false;
		return pr.hasResearchedTechnology(tech);
	}
	
	//--------------------------------------------------
	
	private void addFreeResearches(PlayerResearch research)
	{
		for(Technology t : technologies.values())
		{
			if(t.prerequisites == null)
			{
				research.discoverTechnology(t.name);
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
	
	public boolean save(World world)
	{		
		try
		{
			File file = new File(savePath(world), FILENAME);
			if(!file.exists())
			{
				file.createNewFile();
			}
			
			FileOutputStream fileoutputstream = new FileOutputStream(file);
			NBTTagCompound data = new NBTTagCompound();
			saveToNBTTagCompound(data);
			CompressedStreamTools.writeCompressed(data, fileoutputstream);
			fileoutputstream.close();
		}
		catch(Exception exception)
		{
			Femtocraft.logger.log(Level.SEVERE, "Failed to save data from " + FILENAME + " in world - " + savePath(world) + ".");
			exception.printStackTrace();
			return false;
		}

		
		return true;
	}
	
	public boolean load(World world)
	{
		String worldname = world.getWorldInfo().getWorldName();
		if(lastWorldLoaded == worldname) return false;
		lastWorldLoaded = worldname;
		
		try
		{
			File file = new File(savePath(world), FILENAME);
			if(!file.exists())
			{
				Femtocraft.logger.log(Level.WARNING, "No " + FILENAME + " file found for world - " + savePath(world) + ".");
				return false;
			}
			
			FileInputStream fileinputstream = new FileInputStream(file);
			NBTTagCompound data = CompressedStreamTools.readCompressed(fileinputstream);
//			NBTTagCompound data = CompressedStreamTools.read(file);
			loadFromNBTTagCompound(data);
			fileinputstream.close();
		}
		catch(Exception exception)
		{	
			Femtocraft.logger.log(Level.SEVERE, "Failed to load data from " + FILENAME + " in world - " +  savePath(world) + ".");
			exception.printStackTrace();
			return false;
		}

		return true;
	}
	
	private String savePath(World world)
	{
		return Minecraft.getMinecraft().mcDataDir + "/saves/" + world.getSaveHandler().getWorldDirectoryName();
	}
}
