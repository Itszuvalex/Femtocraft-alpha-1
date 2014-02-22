package femtocraft.managers.research;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import femtocraft.Femtocraft;
import femtocraft.managers.research.EventTechnology.TechnologyAddedEvent;
import femtocraft.research.technology.TechnologyBasicCircuits;
import femtocraft.research.technology.TechnologyMetallurgy;
import femtocraft.research.technology.TechnologyStructureOfTheWorld;

//TODO:  Separate players out into their own files
public class ManagerResearch {
	public static final String RESEARCH_CHANNEL = Femtocraft.ID + ".rman";
	private HashMap<String, ResearchTechnology> technologies;
	private HashMap<String, ResearchPlayer> playerData;

	private final String FILENAME = "FemtocraftResearch.dat";

	private String lastWorldLoaded = "";

	public ManagerResearch() {
		technologies = new HashMap<String, ResearchTechnology>();
		playerData = new HashMap<String, ResearchPlayer>();

		loadTechnologies();
	}

	private void loadTechnologies() {
		addTechnology(new TechnologyBasicCircuits());
		addTechnology(new TechnologyMetallurgy());
		addTechnology(new TechnologyStructureOfTheWorld());
	}

	public Collection<ResearchTechnology> getTechnologies() {
		return technologies.values();
	}

	// --------------------------------------------------

	public boolean addTechnology(ResearchTechnology tech) {
		TechnologyAddedEvent event = new TechnologyAddedEvent(tech);
		return !MinecraftForge.EVENT_BUS.post(event)
				&& technologies.put(tech.name, tech) != null;
	}

	public boolean removeTechnology(ResearchTechnology tech) {
		return technologies.remove(tech.name) != null;
	}

	public ResearchTechnology getTechnology(String name) {
		return technologies.get(name);
	}

	// --------------------------------------------------

	public ResearchPlayer addPlayerResearch(String username) {
		// Return playerData for a player. If it doesn't exist, makes it.
		ResearchPlayer rp = playerData.get(username);
		if (rp != null)
			return rp;
		ResearchPlayer r = new ResearchPlayer(username);

		addFreeResearches(r);

		playerData.put(username, r);
		return r;
	}

	public boolean removePlayerResearch(String username) {
		return playerData.remove(username) != null;
	}

	public ResearchPlayer getPlayerResearch(String username) {
		return playerData.get(username);
	}

	// --------------------------------------------------

	public boolean hasPlayerDiscoveredTechnology(String username,
			ResearchTechnology tech) {
		return hasPlayerDiscoveredTechnology(username, tech.name);
	}

	public boolean hasPlayerDiscoveredTechnology(String username, String tech) {
		ResearchPlayer pr = playerData.get(username);
		return pr != null && pr.hasDiscoveredTechnology(tech);
	}

	// --------------------------------------------------

	public boolean hasPlayerResearchedTechnology(String username,
			ResearchTechnology tech) {
		return hasPlayerResearchedTechnology(username, tech.name);
	}

	public boolean hasPlayerResearchedTechnology(String username, String tech) {
		ResearchPlayer pr = playerData.get(username);
		return pr != null && pr.hasResearchedTechnology(tech);
	}

	// --------------------------------------------------

	private void addFreeResearches(ResearchPlayer research) {
		for (ResearchTechnology t : technologies.values()) {
			if (t.prerequisites == null) {
				research.discoverTechnology(t.name);
			}
		}
	}

	// --------------------------------------------------

	public void saveToNBTTagCompound(NBTTagCompound compound) {
		NBTTagList list = new NBTTagList();

		for (ResearchPlayer status : playerData.values()) {
			NBTTagCompound cs = new NBTTagCompound();
			cs.setString("username", status.username);

			NBTTagCompound data = new NBTTagCompound();
			status.saveToNBTTagCompound(data);

			cs.setCompoundTag("data", data);
			list.appendTag(cs);
		}

		compound.setTag("playerData", list);
	}

	public void loadFromNBTTagCompound(NBTTagCompound compound) {
		NBTTagList list = compound.getTagList("playerData");

		for (int i = 0; i < list.tagCount(); ++i) {
			NBTTagCompound cs = (NBTTagCompound) list.tagAt(i);
			String username = cs.getString("username");

			NBTTagCompound data = (NBTTagCompound) cs.getTag("data");
			ResearchPlayer status = new ResearchPlayer(username);
			status.loadFromNBTTagCompound(data);

			playerData.put(username, status);
		}
	}

	public boolean save(World world) {
		try {
			File file = new File(savePath(world), FILENAME);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fileoutputstream = new FileOutputStream(file);
			NBTTagCompound data = new NBTTagCompound();
			saveToNBTTagCompound(data);
			CompressedStreamTools.writeCompressed(data, fileoutputstream);
			fileoutputstream.close();
		} catch (Exception exception) {
			Femtocraft.logger.log(Level.SEVERE, "Failed to save data from "
					+ FILENAME + " in world - " + savePath(world) + ".");
			exception.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean load(World world) {
		String worldName = world.getWorldInfo().getWorldName();
		if (lastWorldLoaded.equals(worldName)) {
			return false;
		}

		lastWorldLoaded = worldName;

		try {
			File file = new File(savePath(world), FILENAME);
			if (!file.exists()) {
				Femtocraft.logger.log(Level.WARNING, "No " + FILENAME
						+ " file found for world - " + savePath(world) + ".");
				return false;
			}

			FileInputStream fileinputstream = new FileInputStream(file);
			NBTTagCompound data = CompressedStreamTools
					.readCompressed(fileinputstream);
			// NBTTagCompound data = CompressedStreamTools.read(file);
			loadFromNBTTagCompound(data);
			fileinputstream.close();
		} catch (Exception exception) {
			Femtocraft.logger.log(Level.SEVERE, "Failed to load data from "
					+ FILENAME + " in world - " + savePath(world) + ".");
			exception.printStackTrace();
			return false;
		}

		return true;
	}

	private String savePath(World world) {
		return Minecraft.getMinecraft().mcDataDir + "/saves/"
				+ world.getSaveHandler().getWorldDirectoryName();
	}
}
