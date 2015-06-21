package me.hfox.craftbot.file.nbt;

/**
 * Represents a long value.
 *
 * Created by molenzwiebel on 07-06-15.
 */
public class NBTTagLong extends SimpleNBTTag<Long> {
    public NBTTagLong() {
        super((byte) 0x4);
        setRead(in -> value = in.readLong());
        setWrite(out -> out.writeLong(value));
    }

    public NBTTagLong(long val) {
        this();
        this.value = val;
    }
}
