package me.hfox.craftbot.file.nbt;

/**
 * Represents a byte value.
 *
 * Created by molenzwiebel on 07-06-15.
 */
public class NBTTagByte extends SimpleNBTTag<Byte> {
    public NBTTagByte() {
        super((byte) 0x1);
        setRead(in -> value = in.readByte());
        setWrite(out -> out.writeByte(value));
    }

    public NBTTagByte(byte val) {
        this();
        this.value = val;
    }
}
