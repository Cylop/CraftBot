package me.hfox.craftbot.file.nbt;

import me.hfox.craftbot.network.stream.PacketInputStream;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * Contains utility methods for reading and writing to streams.
 *
 * Created by molenzwiebel on 07-06-15.
 */
public class NBTStreamTools {

    public static NBTTagCompound readFromStream(PacketInputStream in) throws IOException {
        byte[] bytes = in.readBytes();
        return readFromCompressedStream(new ByteArrayInputStream(bytes));
    }

    /**
     * Takes {@param in}, gunzips it and returns the root tag.
     * @param in the input
     * @return the read tag
     * @throws IOException when an error occurs during reading
     * @throws IllegalArgumentException when an illegal state during the reading is encountered
     */
    public static NBTTagCompound readFromCompressedStream(InputStream in) throws IOException {
        DataInputStream gunzippedIn = new DataInputStream(new BufferedInputStream(new GZIPInputStream(in)));

        NBTTagCompound result = null;

        try {
            byte id = gunzippedIn.readByte();
            if (id != 0xA) throw new IllegalArgumentException("Expected root tag to be a compound.");
            gunzippedIn.readUTF(); // Name

            result = new NBTTagCompound();
            result.read(gunzippedIn);
        } finally {
            gunzippedIn.close();
        }

        return result;
    }

    /**
     * Writes {@param tag} to {@param out}. Note that this method should only be called when
     * writing to a file because it is different than {@link me.hfox.craftbot.file.nbt.NBTTag#write(DataOutput)}
     * because it also writes the tag and the name. This method is almost always used in
     * conjunction with {@link #readFromCompressedStream(InputStream)}.
     * @param out the stream to write to
     * @param tag the tag to write
     * @throws IOException when an error occurs during writing
     */
    public static void writeToStream(DataOutput out, NBTTag tag) throws IOException {
        out.writeByte(tag.getType());
        if (tag.getType() != 0) {
            out.writeUTF("");
            tag.write(out);
        }
    }
}
