package femtocraft.managers.research;

import net.minecraft.nbt.NBTTagCompound;

public class ResearchTechnologyStatus {
	public String tech;
	public boolean researched;

	public ResearchTechnologyStatus(String tech, boolean researched) {
		this.tech = tech;
		this.researched = researched;
	}

	public ResearchTechnologyStatus(String name) {
		this(name, false);
	}

	private ResearchTechnologyStatus() {
	}

	public void saveToNBTTagCompound(NBTTagCompound compound) {
		compound.setString("tech", tech);
		compound.setBoolean("researched", researched);
	}

	public void loadFromNBTTagCompound(NBTTagCompound compound) {
		tech = compound.getString("tech");
		researched = compound.getBoolean("researched");
	}
}
