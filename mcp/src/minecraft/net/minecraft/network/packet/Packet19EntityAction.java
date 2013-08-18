package net.minecraft.network.packet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.entity.Entity;

public class Packet19EntityAction extends Packet
{
    /** Player ID. */
    public int entityId;

    /** 1=sneak, 2=normal */
    public int state;
    public int field_111009_c;

    public Packet19EntityAction() {}

    @SideOnly(Side.CLIENT)
    public Packet19EntityAction(Entity par1Entity, int par2)
    {
        this(par1Entity, par2, 0);
    }

    @SideOnly(Side.CLIENT)
    public Packet19EntityAction(Entity par1Entity, int par2, int par3)
    {
        this.entityId = par1Entity.entityId;
        this.state = par2;
        this.field_111009_c = par3;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInput par1DataInput) throws IOException
    {
        this.entityId = par1DataInput.readInt();
        this.state = par1DataInput.readByte();
        this.field_111009_c = par1DataInput.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutput par1DataOutput) throws IOException
    {
        par1DataOutput.writeInt(this.entityId);
        par1DataOutput.writeByte(this.state);
        par1DataOutput.writeInt(this.field_111009_c);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleEntityAction(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 9;
    }
}
