package me.hfox.craftbot.file.nbt;

/**
 * Represents a int value.
 *
 * Created by molenzwiebel on 07-06-15.
 */
public class NBTTagInt extends SimpleNBTTag<Integer> {
    public NBTTagInt() {
        super((byte) 0x3);
        setRead(in -> value = in.readInt());
        setWrite(out -> out.writeInt(value));
    }

    public NBTTagInt(int val) {
        this();
        this.value = val;
    }
}
