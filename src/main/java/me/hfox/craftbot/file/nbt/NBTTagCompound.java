package me.hfox.craftbot.file.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by molenzwiebel on 07-06-15.
 */
public class NBTTagCompound extends NBTTag {
    private HashMap<String, NBTTag> values;

    public NBTTagCompound() {
        super();
        this.values = new HashMap<>();
    }

    public NBTTagCompound(Map<String, NBTTag> values) {
        this.values = new HashMap<>(values);
    }

    @Override
    public byte getType() {
        return (byte) 0xA;
    }

    @Override
    public String toString() {
        return String.format("NBTTagCompound{type: 0xA, values: %s}", values.toString());
    }

    @Override
    public void read(DataInput in) throws IOException {
        values.clear();

        byte kind;
        while ((kind = in.readByte()) != 0) {
            String name = in.readUTF();
            NBTTag tag = NBTTag.create(kind);
            tag.read(in);
            values.put(name, tag);
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        for (Map.Entry<String, NBTTag> entry : values.entrySet()) {
            out.writeByte(entry.getValue().getType());
            if (entry.getValue().getType() != 0) {
                out.writeUTF(entry.getKey());
                entry.getValue().write(out);
            }
        }

        out.writeByte(0);
    }

    public void set(String s, NBTTag nbtbase) {
        values.put(s, nbtbase);
    }

    public void setByte(String s, byte b0) {
        values.put(s, new NBTTagByte(b0));
    }

    public void setShort(String s, short short1) {
        values.put(s, new NBTTagShort(short1));
    }

    public void setInt(String s, int i) {
        values.put(s, new NBTTagInt(i));
    }

    public void setLong(String s, long i) {
        values.put(s, new NBTTagLong(i));
    }

    public void setFloat(String s, float f) {
        values.put(s, new NBTTagFloat(f));
    }

    public void setDouble(String s, double d0) {
        values.put(s, new NBTTagDouble(d0));
    }

    public void setString(String s, String s1) {
        values.put(s, new NBTTagString(s1));
    }

    public void setByteArray(String s, byte[] abyte) {
        values.put(s, new NBTTagByteArray(abyte));
    }

    public void setIntArray(String s, int[] aint) {
        values.put(s, new NBTTagIntArray(aint));
    }

    public void setBoolean(String s, boolean flag) {
        this.setByte(s, (byte) (flag ? 1 : 0));
    }

    private void ensureCorrect(String key, byte type) {
        if (!values.containsKey(key)) throw new IllegalArgumentException("Compound has no tag named " + key);
        if (values.get(key).getType() != type) throw new IllegalArgumentException("Tag for key " + key + " is not of type " + type);
    }

    public byte getByte(String key) {
        ensureCorrect(key, (byte) 0x1);
        return ((NBTTagByte) values.get(key)).value;
    }

    public short getShort(String key) {
        ensureCorrect(key, (byte) 0x2);
        return ((NBTTagShort) values.get(key)).value;
    }

    public int getInt(String key) {
        ensureCorrect(key, (byte) 0x3);
        return ((NBTTagInt) values.get(key)).value;
    }

    public long getLong(String key) {
        ensureCorrect(key, (byte) 0x4);
        return ((NBTTagLong) values.get(key)).value;
    }

    public float getFloat(String key) {
        ensureCorrect(key, (byte) 0x5);
        return ((NBTTagFloat) values.get(key)).value;
    }

    public double getDouble(String key) {
        ensureCorrect(key, (byte) 0x6);
        return ((NBTTagDouble) values.get(key)).value;
    }

    public byte[] getByteArray(String key) {
        ensureCorrect(key, (byte) 0x7);
        return ((NBTTagByteArray) values.get(key)).value;
    }

    public String getString(String key) {
        ensureCorrect(key, (byte) 0x8);
        return ((NBTTagString) values.get(key)).value;
    }

    public NBTTagList getList(String key) {
        ensureCorrect(key, (byte) 0x9);
        return (NBTTagList) values.get(key);
    }

    public NBTTagCompound getCompound(String key) {
        ensureCorrect(key, (byte) 0xA);
        return (NBTTagCompound) values.get(key);
    }

    public int[] getIntArray(String key) {
        ensureCorrect(key, (byte) 0xB);
        return ((NBTTagIntArray) values.get(key)).value;
    }
}
