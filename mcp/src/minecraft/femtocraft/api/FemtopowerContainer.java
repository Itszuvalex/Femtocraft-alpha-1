package femtocraft.api;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import femtocraft.managers.research.TechLevel;

public class FemtopowerContainer implements IFemtopowerContainer {
	private TechLevel level;
	private int maxPower;
	private int currentPower;

	public FemtopowerContainer(TechLevel level, int maxPower) {
		this.level = level;
		this.maxPower = maxPower;
		this.currentPower = 0;
	}

	public void setMaxPower(int maxPower) {
		this.maxPower = maxPower;
	}

	public void setCurrentPower(int currentPower) {
		this.currentPower = currentPower;
	}

	public void setTechLevel(TechLevel level) {
		this.level = level;
	}

	private FemtopowerContainer() {
	}

    @Override
	public boolean canAcceptPowerOfLevel(TechLevel level) {
		return this.level == level;
	}

	@Override
	public TechLevel getTechLevel() {
		return level;
	}

	@Override
	public int getCurrentPower() {
		return currentPower;
	}

	@Override
	public int getMaxPower() {
		return maxPower;
	}

	@Override
	public float getFillPercentage() {
		return ((float) currentPower) / ((float) maxPower);
	}

	@Override
	public float getFillPercentageForCharging() {
		return getFillPercentage();
	}

	@Override
	public float getFillPercentageForOutput() {
		return getFillPercentage();
	}

	@Override
	public boolean canCharge() {
		return true;
	}

	@Override
	public int charge(int amount) {
		int room = maxPower - currentPower;
		amount = room < amount ? room : amount;
		currentPower += amount;
		return amount;
	}

	@Override
	public boolean consume(int amount) {
		if (amount > currentPower)
			return false;

		currentPower -= amount;
		return true;
	}

	public void saveToNBT(NBTTagCompound nbt) {
		nbt.setInteger("maxPower", maxPower);
		nbt.setInteger("currentPower", currentPower);
		nbt.setString("techLevel", level.key);
	}

	public void loadFromNBT(NBTTagCompound nbt) {
		maxPower = nbt.getInteger("maxPower");
		currentPower = nbt.getInteger("currentPower");
		level = TechLevel.getTech(nbt.getString("techLevel"));
	}

	public static FemtopowerContainer createFromNBT(NBTTagCompound nbt) {
		FemtopowerContainer cont = new FemtopowerContainer();
		cont.loadFromNBT(nbt);
		return cont;
	}

	public void addInformationToTooltip(List tooltip) {
		String value = level.getTooltipEnum() + "Power: "
				+ EnumChatFormatting.RESET + currentPower + "/" + maxPower;
		tooltip.add(value);
	}

}
