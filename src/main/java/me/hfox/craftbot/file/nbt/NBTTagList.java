package me.hfox.craftbot.file.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * TODO: THIS TAG LIST DOES NOT SUPPORT CONCURRENCY. IF THE LIST IS MODIFIED WHILE WRITING, IT WILL THROW A CONCURRENTMODIFICATIONEXCEPTION!
 *
 * Created by molenzwiebel on 07-06-15.
 */
public class NBTTagList extends NBTTag {
    private List<NBTTag> contents;
    private byte contentType = 0;

    public NBTTagList() {
        super();
    }

    public NBTTagList(Collection<NBTTag> contents) {
        this();
        this.contents = new ArrayList<NBTTag>(contents);
    }

    @Override
    public byte getType() {
        return (byte) 0x9;
    }

    @Override
    public String toString() {
        return String.format("NBTTagList{type: 0xA, contents: %s}", contents.toString());
    }

    @Override
    public void read(DataInput in) throws IOException {
        this.contentType = in.readByte();
        int length = in.readInt();

        this.contents = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            NBTTag tag = NBTTag.create(contentType);
            tag.read(in);
            contents.add(tag);
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeByte(contentType);
        out.writeInt(contents.size());

        for (NBTTag tag : contents) {
            tag.write(out);
        }
    }

    private void ensureCorrect(int idx, byte type) {
        if (contentType != type) throw new IllegalArgumentException("Cannot get NBTTag of type " + type + " from list of type " + contentType);
        if (idx < 0 || idx >= contents.size()) throw new ArrayIndexOutOfBoundsException("Tried to read index " + idx + ", list only has " + contents.size() + " elements.");
    }

    public void add(NBTTag value) {
        if (contentType == 0) {
            contentType = value.getType();
        } else if (contentType != value.getType()) {
            throw new IllegalArgumentException("Cannot add NBTTag of type " + value.getType() + " to list of type " + contentType);
        }

        contents.add(value);
    }

    public byte getByte(int idx) {
        ensureCorrect(idx, (byte) 0x1);
        return ((NBTTagByte) contents.get(idx)).value;
    }

    public short getShort(int idx) {
        ensureCorrect(idx, (byte) 0x2);
        return ((NBTTagShort) contents.get(idx)).value;
    }

    public int getInt(int idx) {
        ensureCorrect(idx, (byte) 0x3);
        return ((NBTTagInt) contents.get(idx)).value;
    }

    public long getLong(int idx) {
        ensureCorrect(idx, (byte) 0x4);
        return ((NBTTagLong) contents.get(idx)).value;
    }

    public float getFloat(int idx) {
        ensureCorrect(idx, (byte) 0x5);
        return ((NBTTagFloat) contents.get(idx)).value;
    }

    public double getDouble(int idx) {
        ensureCorrect(idx, (byte) 0x6);
        return ((NBTTagDouble) contents.get(idx)).value;
    }

    public byte[] getByteArray(int idx) {
        ensureCorrect(idx, (byte) 0x7);
        return ((NBTTagByteArray) contents.get(idx)).value;
    }

    public String getString(int idx) {
        ensureCorrect(idx, (byte) 0x8);
        return ((NBTTagString) contents.get(idx)).value;
    }

    public NBTTagList getList(int idx) {
        ensureCorrect(idx, (byte) 0x9);
        return (NBTTagList) contents.get(idx);
    }

    public NBTTagCompound getCompound(int idx) {
        ensureCorrect(idx, (byte) 0xA);
        return (NBTTagCompound) contents.get(idx);
    }

    public int[] getIntArray(int idx) {
        ensureCorrect(idx, (byte) 0xB);
        return ((NBTTagIntArray) contents.get(idx)).value;
    }
}
