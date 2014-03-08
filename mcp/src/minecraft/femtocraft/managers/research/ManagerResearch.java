package femtocraft.managers.research;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import femtocraft.Femtocraft;
import femtocraft.managers.research.EventTechnology.TechnologyAddedEvent;
import femtocraft.research.gui.technology.GuiBasicCircuits;

//TODO:  Separate players out into their own files
public class ManagerResearch {
	private static final String playerDataKey = "playerData";
	private static final String dataKey = "data";
	private static final String userKey = "username";

	public static final String RESEARCH_CHANNEL = Femtocraft.ID + ".rman";
	private HashMap<String, ResearchTechnology> technologies;
	private HashMap<String, ResearchPlayer> playerData;

	private final String FILENAME = "FemtocraftResearch.dat";

	private String lastWorldLoaded = "";

	public static ResearchTechnology technologyBasicCircuits = new ResearchTechnology(
			"Basic Circuits", "Farenite, Circuit Boards", EnumTechLevel.MACRO,
			null, new ItemStack(Femtocraft.microCircuitBoard), 2, -3, false,
			null, GuiBasicCircuits.class);
	public static ResearchTechnology technologyMetallurgy = new ResearchTechnology(
			"Metallurgy", "Titanium, Thorium, Platinum", EnumTechLevel.MACRO,
			null, new ItemStack(Femtocraft.ingotTemperedTitanium), -2, -3,
			false, null);
	public static ResearchTechnology technologyWorldStructure = new ResearchTechnology(
			"Basic Chemistry", "Composition of Matter", EnumTechLevel.MACRO,
			null, new ItemStack(Femtocraft.itemMineralLattice), 0, -3, true,
			null);
	// TODO: replace maching icon with micro machine chassis item
	public static ResearchTechnology technologyMachining = new ResearchTechnology(
			"Machining", "Start your industry!", EnumTechLevel.MICRO,
			new ArrayList<ResearchTechnology>(Arrays.asList(
					technologyMetallurgy, technologyBasicCircuits)),
			new ItemStack(Femtocraft.itemMicroPlating), -1, -2, false,
			new ArrayList<ItemStack>(Arrays.asList(new ItemStack(
					Femtocraft.ingotTemperedTitanium), new ItemStack(
					Femtocraft.microCircuitBoard), new ItemStack(
					Femtocraft.ingotTemperedTitanium), new ItemStack(
					Femtocraft.microCircuitBoard), new ItemStack(
					Femtocraft.conductivePowder), new ItemStack(
					Femtocraft.microCircuitBoard), new ItemStack(
					Femtocraft.ingotTemperedTitanium), new ItemStack(
					Femtocraft.microCircuitBoard), new ItemStack(
					Femtocraft.ingotTemperedTitanium))));
	public static ResearchTechnology technologyPaperSchematic = new ResearchTechnology(
			"Paper Schematic", "Like IKEA for reality!", EnumTechLevel.MICRO,
			new ArrayList<ResearchTechnology>(Arrays.asList(
					technologyWorldStructure, technologyMachining)),
			new ItemStack(Femtocraft.paperSchematic), 1, -1, false,
			new ArrayList<ItemStack>(Arrays.asList(new ItemStack(Item.paper),
					new ItemStack(Item.paper), new ItemStack(Item.paper),
					new ItemStack(Item.paper), new ItemStack(Item.paper),
					new ItemStack(Item.paper), new ItemStack(Item.paper),
					new ItemStack(Item.paper), new ItemStack(Item.paper))));

	public ManagerResearch() {
		technologies = new HashMap<String, ResearchTechnology>();
		playerData = new HashMap<String, ResearchPlayer>();

		loadTechnologies();
	}

	private void loadTechnologies() {
		Field[] fields = ManagerResearch.class.getFields();
		for (Field field : fields) {
			if (field.getName().startsWith("technology")) {
				try {
					addTechnology((ResearchTechnology) field.get(null));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Collection<ResearchTechnology> getTechnologies() {
		return technologies.values();
	}

	// --------------------------------------------------

	public boolean addTechnology(ResearchTechnology tech) {
		TechnologyAddedEvent event = new TechnologyAddedEvent(tech);
		if (!MinecraftForge.EVENT_BUS.post(event)) {
			return technologies.put(tech.name, tech) != null;
		}
		return false;
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
				research.researchTechnology(t.name, true);
				// research.discoverTechnology(t.name);
			}
		}
	}

	// --------------------------------------------------

	public void saveToNBTTagCompound(NBTTagCompound compound) {
		NBTTagList list = new NBTTagList();

		for (ResearchPlayer status : playerData.values()) {
			NBTTagCompound cs = new NBTTagCompound();
			cs.setString(userKey, status.username);

			NBTTagCompound data = new NBTTagCompound();
			status.saveToNBTTagCompound(data);

			cs.setCompoundTag(dataKey, data);
			list.appendTag(cs);
		}

		compound.setTag(playerDataKey, list);
	}

	public void loadFromNBTTagCompound(NBTTagCompound compound) {
		NBTTagList list = compound.getTagList(playerDataKey);

		for (int i = 0; i < list.tagCount(); ++i) {
			NBTTagCompound cs = (NBTTagCompound) list.tagAt(i);
			String username = cs.getString(userKey);

			NBTTagCompound data = cs.getCompoundTag(dataKey);
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

	public void syncResearch(ResearchPlayer rp) {
		playerData.put(rp.username, rp);
	}
}
