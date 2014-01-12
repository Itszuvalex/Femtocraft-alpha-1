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
import femtocraft.Femtocraft;
import femtocraft.research.PlayerResearch;
import femtocraft.research.Technology;

public class FemtocraftResearchManager {
	private HashMap<String, Technology> technologies;
	private HashMap<String, PlayerResearch> playerData;
	
	private final String FILENAME = "FemtocraftResearch.dat";
	
	public FemtocraftResearchManager()
	{
		technologies = new HashMap<String, Technology>();
		playerData = new HashMap<String, PlayerResearch>();
		load();
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
		try
		{
			File file = new File(Minecraft.getMinecraft().mcDataDir + "/saves/" + Minecraft.getMinecraft().theWorld.getSaveHandler().getWorldDirectoryName() + "", FILENAME);
			if(!file.exists())
			{
				file.createNewFile();
			}
			
//			FileOutputStream fileoutputstream = new FileOutputStream(file);
			NBTTagCompound data = new NBTTagCompound();
			saveToNBTTagCompound(data);
			CompressedStreamTools.write(data, file);
//			CompressedStreamTools.writeCompressed(data, fileoutputstream);
//			fileoutputstream.close();
		}
		catch(Exception exception)
		{
			Femtocraft.logger.log(Level.SEVERE, "Failed to save data from " + FILENAME + " in world - " +  Minecraft.getMinecraft().theWorld.getWorldInfo().getWorldName() + ".");
			exception.printStackTrace();
			return false;
		}

		
		return true;
	}
	
	public boolean load()
	{
		try
		{
			File file = new File(Minecraft.getMinecraft().mcDataDir + "/saves/" + Minecraft.getMinecraft().theWorld.getSaveHandler().getWorldDirectoryName() + "", FILENAME);
			if(!file.exists())
			{
				Femtocraft.logger.log(Level.WARNING, "No " + FILENAME + " file found for world - " + Minecraft.getMinecraft().theWorld.getWorldInfo().getWorldName() + ".");
				return false;
			}
			
//			FileInputStream fileinputstream = new FileInputStream(file);
//			NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(fileinputstream);
			NBTTagCompound data = CompressedStreamTools.read(file);
			loadFromNBTTagCompound(data);
//			fileinputstream.close();
		}
		catch(Exception exception)
		{	
			Femtocraft.logger.log(Level.SEVERE, "Failed to load data from " + FILENAME + " in world - " +  Minecraft.getMinecraft().theWorld.getWorldInfo().getWorldName() + ".");
			exception.printStackTrace();
			return false;
		}

		return true;
	}
}
