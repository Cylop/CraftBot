package me.hfox.craftbot.file.nbt;

/**
 * Represents a float value.
 *
 * Created by molenzwiebel on 07-06-15.
 */
public class NBTTagFloat extends SimpleNBTTag<Float> {
    public NBTTagFloat() {
        super((byte) 0x5);
        setRead(in -> value = in.readFloat());
        setWrite(out -> out.writeFloat(value));
    }

    public NBTTagFloat(float val) {
        this();
        this.value = val;
    }
}
