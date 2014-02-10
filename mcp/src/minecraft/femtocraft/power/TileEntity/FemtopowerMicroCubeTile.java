package femtocraft.power.TileEntity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Arrays;

import femtocraft.Femtocraft;
import femtocraft.FemtocraftUtils;
import femtocraft.managers.research.TechLevel;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.ForgeDirection;

public class FemtopowerMicroCubeTile extends FemtopowerTile {
	public boolean[] outputs;
	static final public String packetChannel = Femtocraft.ID + ".MCube";
	public static final int maxStorage = 10000;
	public static final TechLevel techLevel = TechLevel.MICRO;
	
	public FemtopowerMicroCubeTile() {
		super();
		setMaxStorage(maxStorage);
		setTechLevel(TechLevel.MICRO);
		outputs = new boolean[6];
		Arrays.fill(outputs, false);
		setTechLevel(techLevel);
	}

	public void onSideActivate(ForgeDirection side)
	{
		int i = FemtocraftUtils.indexOfForgeDirection(side);
		outputs[i] = !outputs[i];
	}
	
	private void sendSideStateToClients(ForgeDirection side)
	{
		
	}

	@Override
	public Packet getDescriptionPacket() {
		return generatePacket();
	}
	
	private Packet250CustomPayload generatePacket()
	{
	    ByteArrayOutputStream bos = new ByteArrayOutputStream(13);
	    DataOutputStream outputStream = new DataOutputStream(bos);
	    try {
	        outputStream.writeInt(xCoord);
	        outputStream.writeInt(yCoord);
	        outputStream.writeInt(zCoord);
	        //write the relevant information here... exemple:
	       outputStream.writeByte(generateOutputMask()); 
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	               
	    Packet250CustomPayload packet = new Packet250CustomPayload();
	    
	    
	    
	    packet.channel = packetChannel;
	    packet.data = bos.toByteArray();
	    packet.length = bos.size();
	    
	    return packet;
	}
	
	public byte generateOutputMask()
	{
		byte output = 0;
		
		for(int i = 0; i < 6; i++)
		{
			if(outputs[i])
				output += 1 << i;
		}
//		if(outputs[0])
//			output += 1;
//		if(outputs[1])
//			output+= 1<<1;
//		if(outputs[2])
//			output+= 1<<2;
//		if(outputs[3])
//			output+= 1<<3;
//		if(outputs[4])
//			output+= 1<<4;
//		if(outputs[5])
//			output+= 1<<5;
//		
		return output;
	}
	
	public void parseOutputMask(byte mask)
	{
		byte temp;

		for(int i = 0; i < 6; i++)
		{
			temp = mask;
			outputs[i] = (((temp >> i) & 1) == 1) ? true : false;
		}
	}

	@Override
	public float getFillPercentageForCharging(ForgeDirection from) {
		return outputs[FemtocraftUtils.indexOfForgeDirection(from)] ? 1.f : 0.f;
	}

	@Override
	public float getFillPercentageForOutput(ForgeDirection to) {
		return outputs[FemtocraftUtils.indexOfForgeDirection(to)] ? 1.f : 0.f;
	}

	@Override
	public boolean canCharge(ForgeDirection from) {
		return !outputs[FemtocraftUtils.indexOfForgeDirection(from)] && super.canCharge(from);
	}

	@Override
	public int charge(ForgeDirection from, int amount) {
		if(!outputs[FemtocraftUtils.indexOfForgeDirection(from)])
		{
			return super.charge(from, amount);
		}
		
		return 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		for(int i = 0; i < 6; i++) {
			outputs[i] = par1nbtTagCompound.getBoolean("output" + i);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
		
		for(int i = 0; i < 6; i++) {
			par1nbtTagCompound.setBoolean("output" + i, outputs[i]);
		}
	}

}
