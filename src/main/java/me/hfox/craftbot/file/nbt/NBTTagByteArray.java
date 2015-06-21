package me.hfox.craftbot.file.nbt;

/**
 * Represents an array of bytes.
 *
 * Created by molenzwiebel on 07-06-15.
 */
public class NBTTagByteArray extends SimpleNBTTag<byte[]> {
    public NBTTagByteArray() {
        super((byte) 0x7);
        setRead(in -> {
            int length = in.readInt();
            value = new byte[length];
            in.readFully(value);
        });
        setWrite(out -> {
            out.writeInt(value.length);
            out.write(value);
        });
    }

    public NBTTagByteArray(byte[] val) {
        this();
        this.value = val;
    }
}
