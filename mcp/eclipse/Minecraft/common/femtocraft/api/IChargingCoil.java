package femtocraft.api;

import femtocraft.research.TechLevel;

public interface IChargingCoil {
	float powerPerTick();
	TechLevel techLevel();
}
