package me.hfox.craftbot.file.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Represents a single NBT tag. This tag can be of any type, including the "end"
 * tag that is used to indicate the termination of an array or a dictionary.
 *
 * Created by molenzwiebel. on 07-06-15.
 */
public abstract class NBTTag {
    public static NBTTag create(byte type) {
        switch (type) {
            case 0x0: return new NBTTagEnd();
            case 0x1: return new NBTTagByte();
            case 0x2: return new NBTTagShort();
            case 0x3: return new NBTTagInt();
            case 0x4: return new NBTTagLong();
            case 0x5: return new NBTTagFloat();
            case 0x6: return new NBTTagDouble();
            case 0x7: return new NBTTagByteArray();
            case 0x8: return new NBTTagString();
            case 0x9: return new NBTTagList();
            case 0xA: return new NBTTagCompound();
            case 0xB: return new NBTTagIntArray();
            default: throw new IllegalArgumentException("No NBTTag of type " + type);
        }
    }

    /**
     * @return the byte that indicates that the following data is of the specified type.
     */
    public abstract byte getType();

    /**
     * Called when the input has determined that the next tag is of this nbt tag type.
     * The implementing method is responsible for reading its data from the stream and
     * populating itself with the read data. This is typically the reverse of write.
     * @param in the input to read from
     */
    public abstract void read(DataInput in) throws IOException;

    /**
     * Called when this tag has to be written to the output. The implementing
     * method is responsible for writing all of its data to the stream in a format
     * that can later be read. This is typically the reverse of read.
     * @param out
     */
    public abstract void write(DataOutput out) throws IOException;
}
