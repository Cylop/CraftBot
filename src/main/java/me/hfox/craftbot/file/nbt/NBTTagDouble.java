package me.hfox.craftbot.file.nbt;

/**
 * Represents a double value.
 *
 * Created by molenzwiebel on 07-06-15.
 */
public class NBTTagDouble extends SimpleNBTTag<Double> {
    public NBTTagDouble() {
        super((byte) 0x6);
        setRead(in -> value = in.readDouble());
        setWrite(out -> out.writeDouble(value));
    }

    public NBTTagDouble(double val) {
        this();
        this.value = val;
    }
}
