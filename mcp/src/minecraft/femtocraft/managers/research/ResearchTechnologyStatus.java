/*******************************************************************************
 * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 * Itszuvalex@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/

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
