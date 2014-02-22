package femtocraft.managers.research;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;

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

//	public Packet250CustomPayload toPacket() {
//		Packet250CustomPayload techPacket = new Packet250CustomPayload();
//		techPacket.channel = ManagerResearch.RESEARCH_CHANNEL;
//
//		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
//		DataOutputStream outputStream = new DataOutputStream(bos);
//		try {
//			outputStream.writeUTF(tech);
//			outputStream.writeBoolean(researched);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//
//		techPacket.data = bos.toByteArray();
//		techPacket.length = bos.size();
//
//		return techPacket;
//	}
//
//	public static ResearchTechnologyStatus fromStream(DataInputStream stream) {
//		String tech = null;
//		boolean researched = false;
//		try {
//			tech = stream.readUTF();
//			researched = stream.readBoolean();
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//
//		return new ResearchTechnologyStatus(tech, researched);
//	}
}
