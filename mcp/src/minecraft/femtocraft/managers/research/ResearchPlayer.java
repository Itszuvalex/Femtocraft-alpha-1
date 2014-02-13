package femtocraft.managers.research;

import femtocraft.Femtocraft;
import femtocraft.managers.research.EventTechnology.TechnologyDiscoveredEvent;
import femtocraft.managers.research.EventTechnology.TechnologyResearchedEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;

public class ResearchPlayer {
	public final String username;
	private final HashMap<String, ResearchTechnologyStatus> techStatus;

	public static class TechnologyNotFoundException extends Exception {
		public String errMsg;

		public TechnologyNotFoundException(String message) {
			errMsg = message;
		}
	}

	public ResearchPlayer(String username) {
		this.username = username;
		techStatus = new HashMap<String, ResearchTechnologyStatus>();
	}

	// ---------------------------------------------------------

	/**
	 * 
	 * @param name
	 *            Name of researchTechnology to mark as researched
	 * @param force
	 *            Pass true if you want the named researchTechnology to be added if it
	 *            isn't already discovered. This will bypass discover checks.
	 *            This will not post an event.
	 * @return True if researchTechnology successfully marked as researched. False
	 *         otherwise.
	 */
	public boolean researchTechnology(String name, boolean force) {
		ResearchTechnologyStatus tech = techStatus.get(name);
		if (tech == null && !force)
			return false;

		if (tech == null && force) {
			ResearchTechnologyStatus status = techStatus.put(name,
					new ResearchTechnologyStatus(name));
			if (status == null)
				return false;
			status.researched = true;
			return true;
		}

		TechnologyResearchedEvent event = new TechnologyResearchedEvent(
				username, Femtocraft.researchManager.getTechnology(name));
		if (!MinecraftForge.EVENT_BUS.post(event)) {
			tech.researched = true;
			return true;
		}
		return false;
	}

	public boolean discoverTechnology(String name) {
		TechnologyDiscoveredEvent event = new TechnologyDiscoveredEvent(
				username, Femtocraft.researchManager.getTechnology(name));
        return !MinecraftForge.EVENT_BUS.post(event) && techStatus.put(name, new ResearchTechnologyStatus(name)) != null;
    }

	public ResearchTechnologyStatus getTechnology(String name) {
		return techStatus.get(name);
	}

	public ResearchTechnologyStatus removeTechnology(String name) {
		return techStatus.remove(name);
	}

	public boolean canDiscoverTechnology(ResearchTechnology tech) {
		if (tech.prerequisites == null)
			return true;

		for (ResearchTechnology prereq : tech.prerequisites) {
			ResearchTechnologyStatus ts = techStatus.get(prereq.name);
			if (ts == null)
				return false;
			if (!ts.researched)
				return false;
		}

		return true;
	}

	public boolean hasDiscoveredTechnology(ResearchTechnology tech) {
		return hasDiscoveredTechnology(tech.name);
	}

	public boolean hasDiscoveredTechnology(String tech) {
		ResearchTechnologyStatus ts = techStatus.get(tech);
        return ts != null;
    }

	public boolean hasResearchedTechnology(ResearchTechnology tech) {
        return tech == null || hasResearchedTechnology(tech.name);
    }

	public boolean hasResearchedTechnology(String tech) {
		if (tech == null || tech.equals(""))
			return true;

		ResearchTechnologyStatus ts = techStatus.get(tech);
        return ts != null && ts.researched;
    }

	// ---------------------------------------------------------

	public void saveToNBTTagCompound(NBTTagCompound compound) {
		NBTTagList list = new NBTTagList();

		for (ResearchTechnologyStatus status : techStatus.values()) {
			NBTTagCompound cs = new NBTTagCompound();
			cs.setString("techname", status.tech);

			NBTTagCompound data = new NBTTagCompound();
			status.saveToNBTTagCompound(data);

			compound.setTag("data", data);
			list.appendTag(cs);
		}

		compound.setTag("techMap", list);
	}

	public void loadFromNBTTagCompound(NBTTagCompound compound) {
		NBTTagList list = compound.getTagList("techMap");

		for (int i = 0; i < list.tagCount(); ++i) {
			NBTTagCompound cs = (NBTTagCompound) list.tagAt(i);
			String techname = cs.getString("techname");

			NBTTagCompound data = (NBTTagCompound) cs.getTag("data");
			ResearchTechnologyStatus status = new ResearchTechnologyStatus(techname);
			status.loadFromNBTTagCompound(data);

			techStatus.put(username, status);
		}
	}
}
