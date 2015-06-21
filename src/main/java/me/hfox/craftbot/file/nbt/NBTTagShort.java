package me.hfox.craftbot.file.nbt;

/**
 * Represents a short value.
 *
 * Created by molenzwiebel on 07-06-15.
 */
public class NBTTagShort extends SimpleNBTTag<Short> {
    public NBTTagShort() {
        super((byte) 0x2);
        setRead(in -> value = in.readShort());
        setWrite(out -> out.writeShort(value));
    }

    public NBTTagShort(short val) {
        this();
        this.value = val;
    }
}
