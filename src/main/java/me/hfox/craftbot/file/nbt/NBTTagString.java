package me.hfox.craftbot.file.nbt;

/**
 * Represents a string value.
 *
 * Created by molenzwiebel on 07-06-15.
 */
public class NBTTagString extends SimpleNBTTag<String> {
    public NBTTagString() {
        super((byte) 0x8);
        setRead(in -> value = in.readUTF());
        setWrite(out -> out.writeUTF(value));
    }

    public NBTTagString(String val) {
        this();
        this.value = val;
    }
}
