package femtocraft.managers.research;

import net.minecraft.nbt.NBTTagCompound;

public class ResearchTechnologyStatus {
    private final static String techKey = "tech";
    private final static String researchKey = "researched";
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
        compound.setString(techKey, tech);
        compound.setBoolean(researchKey, researched);
    }

    public void loadFromNBTTagCompound(NBTTagCompound compound) {
        tech = compound.getString(techKey);
        researched = compound.getBoolean(researchKey);
    }

    // public Packet250CustomPayload toPacket() {
    // Packet250CustomPayload techPacket = new Packet250CustomPayload();
    // techPacket.channel = ManagerResearch.RESEARCH_CHANNEL;
    //
    // ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
    // DataOutputStream outputStream = new DataOutputStream(bos);
    // try {
    // outputStream.writeUTF(tech);
    // outputStream.writeBoolean(researched);
    // } catch (Exception ex) {
    // ex.printStackTrace();
    // }
    //
    // techPacket.data = bos.toByteArray();
    // techPacket.length = bos.size();
    //
    // return techPacket;
    // }
    //
    // public static ResearchTechnologyStatus fromStream(DataInputStream stream)
    // {
    // String tech = null;
    // boolean researched = false;
    // try {
    // tech = stream.readUTF();
    // researched = stream.readBoolean();
    // } catch (IOException e) {
    // e.printStackTrace();
    // return null;
    // }
    //
    // return new ResearchTechnologyStatus(tech, researched);
    // }
}
