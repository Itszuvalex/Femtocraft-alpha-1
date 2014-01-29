package femtocraft.api;

import femtocraft.research.TechLevel;

public interface IChargingBase {
	int maxCoilsSupported();
	TechLevel maxTechSupported();
	boolean addCoil(IChargingCoil coil);
}
