package femtocraft.api;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.utils.ISaveable;

public class PowerContainer implements IPowerContainer, ISaveable {
	private EnumTechLevel level;
	private int maxPower;
	private int currentPower;

	public PowerContainer(EnumTechLevel level, int maxPower) {
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

	public void setTechLevel(EnumTechLevel level) {
		this.level = level;
	}

	public PowerContainer() {
		level = EnumTechLevel.MACRO;
		maxPower = 0;
		currentPower = 0;
	}

	@Override
	public boolean canAcceptPowerOfLevel(EnumTechLevel level) {
		return this.level == level;
	}

	@Override
	public EnumTechLevel getTechLevel() {
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

	@Override
	public void saveToNBT(NBTTagCompound nbt) {
		nbt.setInteger("maxPower", maxPower);
		nbt.setInteger("currentPower", currentPower);
		nbt.setString("enumTechLevel", level.key);
	}

	@Override
	public void loadFromNBT(NBTTagCompound nbt) {
		maxPower = nbt.getInteger("maxPower");
		currentPower = nbt.getInteger("currentPower");
		level = EnumTechLevel.getTech(nbt.getString("enumTechLevel"));
	}

	public static PowerContainer createFromNBT(NBTTagCompound nbt) {
		PowerContainer cont = new PowerContainer();
		cont.loadFromNBT(nbt);
		return cont;
	}

	public void addInformationToTooltip(List tooltip) {
		String value = level.getTooltipEnum() + "Power: "
				+ EnumChatFormatting.RESET + currentPower + "/" + maxPower;
		tooltip.add(value);
	}

}
