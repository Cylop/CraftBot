package me.hfox.craftbot.file.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A simple implementation of the NBTTag when a type only has a single value.
 * NBTTags are by definition immutable, hence no setValue is defined.
 *
 * Created by molenzwiebel on 07-06-15.
 */
public abstract class SimpleNBTTag<Type> extends NBTTag {
    protected byte type;
    protected Type value;
    private IOFunction<DataInput> readFunc;
    private IOFunction<DataOutput> writeFunc;

    protected SimpleNBTTag(byte type) {
        super();
        this.type = type;
    }

    /**
     * @see me.hfox.craftbot.file.nbt.NBTTag#getType()
     */
    public byte getType() {
        return type;
    }

    /**
     * Sets the function called whenever this tag needs to be read.
     * @param read
     */
    protected void setRead(IOFunction<DataInput> read) {
        this.readFunc = read;
    }

    /**
     * Sets the function called whenever this tag needs to be written.
     * @param write
     */
    protected void setWrite(IOFunction<DataOutput> write) {
        this.writeFunc = write;
    }

    /**
     * @return the value of this tag
     */
    public Type getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s{type: 0x%s, value: %s}", getClass().getSimpleName(), Integer.toHexString(getType()), value.toString());
    }

    @Override
    public void read(DataInput in) throws IOException {
        try {
            readFunc.call(in);
        } catch (Exception ex) {
            throw new IOException("Error reading tag 0x" + Integer.toHexString(getType()) + ": " + ex.getMessage(), ex);
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        try {
            writeFunc.call(out);
        } catch (Exception ex) {
            throw new IOException("Error writing tag 0x" + Integer.toHexString(getType()) + ": " + ex.getMessage(), ex);
        }
    }

    @FunctionalInterface
    protected interface IOFunction<A> {
        public void call(A arg) throws IOException;
    }
}
