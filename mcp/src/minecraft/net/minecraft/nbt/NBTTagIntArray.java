package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagIntArray extends NBTBase
{
    /** The array of saved integers */
    public int[] intArray;

    public NBTTagIntArray(String par1Str)
    {
        super(par1Str);
    }

    public NBTTagIntArray(String par1Str, int[] par2ArrayOfInteger)
    {
        super(par1Str);
        this.intArray = par2ArrayOfInteger;
    }

    /**
     * Write the actual data contents of the tag, implemented in NBT extension classes
     */
    void write(DataOutput par1DataOutput) throws IOException
    {
        par1DataOutput.writeInt(this.intArray.length);

        for (int i = 0; i < this.intArray.length; ++i)
        {
            par1DataOutput.writeInt(this.intArray[i]);
        }
    }

    /**
     * Read the actual data contents of the tag, implemented in NBT extension classes
     */
    void load(DataInput par1DataInput, int par2) throws IOException
    {
        int j = par1DataInput.readInt();
        this.intArray = new int[j];

        for (int k = 0; k < j; ++k)
        {
            this.intArray[k] = par1DataInput.readInt();
        }
    }

    /**
     * Gets the type byte for the tag.
     */
    public byte getId()
    {
        return (byte)11;
    }

    public String toString()
    {
        return "[" + this.intArray.length + " bytes]";
    }

    /**
     * Creates a clone of the tag.
     */
    public NBTBase copy()
    {
        int[] aint = new int[this.intArray.length];
        System.arraycopy(this.intArray, 0, aint, 0, this.intArray.length);
        return new NBTTagIntArray(this.getName(), aint);
    }

    public boolean equals(Object par1Obj)
    {
        if (!super.equals(par1Obj))
        {
            return false;
        }
        else
        {
            NBTTagIntArray nbttagintarray = (NBTTagIntArray)par1Obj;
            return this.intArray == null && nbttagintarray.intArray == null || this.intArray != null && Arrays.equals(this.intArray, nbttagintarray.intArray);
        }
    }

    public int hashCode()
    {
        return super.hashCode() ^ Arrays.hashCode(this.intArray);
    }
}
