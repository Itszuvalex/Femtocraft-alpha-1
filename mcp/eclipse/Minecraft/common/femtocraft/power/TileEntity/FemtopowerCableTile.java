package femtocraft.power.TileEntity;

import net.minecraft.nbt.NBTTagCompound;

public class FemtopowerCableTile extends FemtopowerTile {

	public FemtopowerCableTile() {
		
	}
	
	public boolean connectedAcross() {
		if(numConnections() == 2) {
			if(connections[0] == true && connections[1] == true) return true;
			if(connections[2] == true && connections[3] == true) return true;
			if(connections[4] == true && connections[5] == true) return true;
		}
		return false;
	}

	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
       super.readFromNBT(par1NBTTagCompound);
    }
	
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
       super.writeToNBT(par1NBTTagCompound);
    }	
}
