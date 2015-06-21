package me.hfox.craftbot.network.stream;

import me.hfox.craftbot.metadata.Metadata;
import me.hfox.craftbot.file.nbt.NBTStreamTools;
import me.hfox.craftbot.file.nbt.NBTTagCompound;
import me.hfox.craftbot.inventory.ItemStack;
import me.hfox.craftbot.world.Location;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

public class PacketOutputStream extends DataOutputStream {

    public PacketOutputStream(OutputStream out) {
        super(out);
    }

    public void writeString(String string) throws IOException {
        writeVarInt(string.length());
        write(string.getBytes("UTF-8"));
    }

    public void writeStringArray(String[] array) throws IOException {
        writeVarInt(array.length);
        for (String string : array) {
            writeString(string);
        }
    }

    public void writeStringList(List<String> list) throws IOException {
        writeVarInt(list.size());
        for (String string : list) {
            writeString(string);
        }
    }

    public void writeVarInt(int paramInt) throws IOException {
        while (true) {
            if ((paramInt & 0xFFFFFF80) == 0) {
                writeByte((byte) paramInt);
                return;
            }

            writeByte((byte) (paramInt & 0x7F | 0x80));
            paramInt >>>= 7;
        }
    }

    public void writeVarInt64(long varInt) throws IOException {
        int length = 10;
        for (int i = 9; i >= 0; i--)
            if (((varInt >> (i * 7)) & (i != 9 ? 0x7F : 0x01)) == 0)
                length--;
        for (int i = 0; i < length; i++)
            writeByte((int) ((i == length - 1 ? 0x00 : 0x80) | ((varInt >> (i * 7)) & (i != 9 ? 0x7F : 0x01))));
    }

    public void writeBytes(byte[] data) throws IOException {
        writeVarInt(data.length);
        write(data);
    }

    public void writeUnsignedByte(int data) throws IOException {
        write(data);
    }

    public void writeUnsignedShort(int data) throws IOException {
        writeShort(data & 0xFFFF);
    }

    public void writeFixedPointByte(double value) throws IOException {
        int i = (int) value * 32;
        writeByte(i);
    }

    public void writeFixedPointInt(double value) throws IOException {
        int i = (int) value * 32;
        writeInt(i);
    }

    public void writeRotation(float value) throws IOException {
        int i = (int) (value * 256D) / 360;
        writeByte(i);
    }

    public void writeUUID(UUID uuid) throws IOException {
        writeUUID(uuid, false);
    }

    public void writeUUID(UUID uuid, boolean dashes) throws IOException {
        if (dashes) {
            writeString(uuid.toString());
        } else {
            writeLong(uuid.getMostSignificantBits());
            writeLong(uuid.getLeastSignificantBits());
        }
    }

    public void writeEnum(Enum value) throws IOException {
        writeString(value.name());
    }

    public void writeObject(Object object) throws IOException {
        ObjectOutputStream output = new ObjectOutputStream(this);
        output.writeObject(object);
    }

    public byte[] toByteArray() {
        if (out instanceof ByteArrayOutputStream) {
            ByteArrayOutputStream output = (ByteArrayOutputStream) out;
            return output.toByteArray();
        }

        return null;
    }

    public void writeJSON(JSONObject json) throws IOException {
        writeString(json.toString(2));
    }

    public void writePosition(int[] position) throws IOException {
        if (position.length != 3) {
            throw new IllegalArgumentException("Invalid position length: " + position.length);
        }

        int x = position[0];
        int y = position[1];
        int z = position[2];
        writeLong(((x & 0x3FFFFFF) << 38) | ((y & 0xFFF) << 26) | (z & 0x3FFFFFF));
    }

    public void writeLocation(Location location) throws IOException {
        writePosition(new int[]{location.getBlockX(), location.getBlockY(), location.getBlockZ()});
    }

    public void writeNBTTagCompound(NBTTagCompound tag) throws IOException {
        NBTStreamTools.writeToStream(this, tag);
    }

    public void writeItem(ItemStack stack) throws IOException {
        writeShort(stack != null ? stack.getId() : -1);
        if (stack != null) {
            writeByte(stack.getAmount());
            writeShort(stack.getData());
        }
    }

    public void writeMetadata(Metadata metadata) throws IOException {
        metadata.write(this);
    }

}
