package me.hfox.craftbot.file.nbt;

/**
 * Created by molenzwiebel on 07-06-15.
 */
public class NBTTagEnd extends SimpleNBTTag<Void> {
    protected NBTTagEnd() {
        super((byte) 0);

        setRead(in -> {});
        setWrite(out -> {});
    }
}
