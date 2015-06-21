package me.hfox.craftbot.network.stream;

import me.hfox.craftbot.file.nbt.NBTStreamTools;
import me.hfox.craftbot.file.nbt.NBTTagCompound;
import me.hfox.craftbot.inventory.ItemStack;
import me.hfox.craftbot.metadata.Metadata;
import me.hfox.craftbot.world.Location;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PacketInputStream extends DataInputStream {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    public PacketInputStream(InputStream in) {
        super(in);
    }

    public String readString() throws IOException {
        int length = readVarInt();
        // Logging.getLogger().info("Reading a string with " + length + " characters");
        byte[] data = new byte[length];
        for (int i = 0; i < length; i++) {
            data[i] = (byte) read();
        }
        String string = new String(data, UTF8);
        // Logging.getLogger().info("Reading " + string);

        return string;
    }

    public String[] readStringArray() throws IOException {
        int size = readVarInt();

        String[] array = new String[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = readString();
        }

        return array;
    }

    public List<String> readStringList() throws IOException {
        int size = readVarInt();

        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(readString());
        }

        return list;
    }

    public int readVarInt() throws IOException {
        int i = 0;
        int j = 0;
        while (true) {
            int k = read();
            if (k == -1)
                throw new IOException("End of stream");

            i |= (k & 0x7F) << j++ * 7;

            if (j > 5)
                throw new IOException("VarInt too big");

            if ((k & 0x80) != 128)
                break;
        }

        return i;
    }

    public long readVarInt64() throws IOException {
        long varInt = 0;
        for (int i = 0; i < 10; i++) {
            byte b = readByte();
            varInt |= ((long) (b & (i != 9 ? 0x7F : 0x01))) << (i * 7);

            if (i == 9 && (((b & 0x80) == 0x80) || ((b & 0x7E) != 0)))
                throw new IOException("VarInt too big");
            if ((b & 0x80) != 0x80)
                break;
        }

        return varInt;
    }

    public byte[] readBytes() throws IOException {
        return readBytes(readVarInt());
    }

    public byte[] readBytes(int length) throws IOException {
        if (length < 0)
            throw new IOException("Invalid array length");
        byte[] data = new byte[length];
        readFully(data);
        return data;
    }

    public double readFixedPointByte() throws IOException {
        return (double) readByte() / 32;
    }

    public double readFixedPointInt() throws IOException {
        return (double) readInt() / 32;
    }

    public float readRotation() throws IOException {
        return (float) (readByte() / 360) * 256F;
    }

    public UUID readUUID() throws IOException {
        return readUUID(false);
    }

    public UUID readUUID(boolean dashes) throws IOException {
        if (dashes) {
            return UUID.fromString(readString());
        } else {
            return new UUID(readLong(), readLong());
        }
    }

    public <T extends Enum> T readEnum(Class<T> cls) throws IOException {
        T[] options = cls.getEnumConstants();
        String received = readString();
        for (T option : options) {
            if (option.name().equals(received)) {
                return option;
            }
        }

        return null;
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        ObjectInputStream stream = new ObjectInputStream(this);
        return stream.readObject();
    }

    public JSONObject readJSON() throws IOException {
        String string = readString();
        return new JSONObject(string);
    }

    public int[] readPosition() throws IOException {
        long val = readLong();
        int x = (int) val >> 38;
        int y = (int) (val >> 26) & 0xFFF;
        int z = (int) val << 38 >> 38;
        return new int[]{x, y, z};
    }

    public Location readLocation() throws IOException {
        int[] position = readPosition();
        return new Location(position[0], position[1], position[2]);
    }

    public NBTTagCompound readNBTTagCompound() throws IOException {
        return NBTStreamTools.readFromCompressedStream(this);
    }

    public ItemStack readItem() throws IOException {
        ItemStack item = null;

        short id = readShort();
        if (id > 0) {
            byte amount = readByte();
            short data = readShort();
            item = new ItemStack(id, data, amount);
            item.setNBT(readNBTTagCompound());
        }

        return item;
    }

    public Metadata readMetadata() throws IOException {
        Metadata metadata = new Metadata();
        metadata.read(this);
        return metadata;
    }

}
