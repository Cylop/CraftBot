package me.hfox.craftbot.file.nbt;

/**
 * Represents an array of ints.
 *
 * Created by molenzwiebel on 07-06-15.
 */
public class NBTTagIntArray extends SimpleNBTTag<int[]> {
    public NBTTagIntArray() {
        super((byte) 0xB);
        setRead(in -> {
            int length = in.readInt();
            value = new int[length];
            for (int i = 0; i < length; i++) value[i] = in.readInt();
        });
        setWrite(out -> {
            out.writeInt(value.length);
            for (int i : value) out.writeInt(i);
        });
    }

    public NBTTagIntArray(int[] val) {
        this();
        this.value = val;
    }
}
