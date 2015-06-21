package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.world.Chunk;
import me.hfox.craftbot.world.ChunkSection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PacketPlayOutChunkData implements Packet {

    private int x;
    private int z;
    private ChunkMetadata data;
    private boolean groundUp;

    public PacketPlayOutChunkData(int x, int z, ChunkMetadata data, boolean groundUp) {
        this.x = x;
        this.z = z;
        this.data = data;
        this.groundUp = groundUp;
    }

    public PacketPlayOutChunkData(Chunk chunk, boolean skylight, boolean entireChunk, int bitmask, boolean groundUp) {
        this.x = chunk.getX();
        this.z = chunk.getZ();
        this.data = getData(chunk, skylight, entireChunk, bitmask);
        this.groundUp = groundUp;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.x = stream.readInt();
        this.z = stream.readInt();
        this.groundUp = stream.readBoolean();
        this.data = new ChunkMetadata(stream.readUnsignedShort(), stream.readBytes());
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeInt(x);
        stream.writeInt(z);
        stream.writeBoolean(groundUp);
        stream.writeUnsignedShort(data.bitmask);
        stream.writeBytes(data.data);
    }

    protected static int getByteSize(int sections, boolean entireChunk, boolean skylight) {
        int j = sections * 2 * 16 * 16 * 16;
        int k = sections * 16 * 16 * 16 / 2;
        int l = entireChunk ? sections * 16 * 16 * 16 / 2 : 0;
        int lightSize = skylight ? 256 : 0;

        return j + k + l + lightSize;
    }

    public static ChunkMetadata getData(Chunk chunk, boolean skylight, boolean entireChunk, int bitmask) {
        ChunkSection[] sections = chunk.getSections();
        ChunkMetadata metadata = new ChunkMetadata();
        List<ChunkSection> list = new ArrayList<>();

        for (int i = 0; i < 16; ++i) {
            ChunkSection section = sections[i];
            if ((!skylight || !section.isEmpty()) && (bitmask & 1 << i) != 0) {
                metadata.bitmask |= 1 << i;
                list.add(section);
            }
        }

        metadata.data = new byte[getByteSize(Integer.bitCount(metadata.bitmask), entireChunk, skylight)];

        int pos = 0;
        for (ChunkSection section : list) {
            char[] blockIds = section.getBlockIds();
            for (char id : blockIds) {
                // splits block data into 2 bytes
                metadata.data[pos++] = (byte) (id & 255);
                metadata.data[pos++] = (byte) (id >> 8 & 255);
            }
        }

        for (ChunkSection section : list) {
            // add emitted light nibble array to the data array
            pos = addData(section.getEmittedLight().getData(), metadata.data, pos);
        }

        if (entireChunk) {
            // add skylight nibble array to the data array
            for (ChunkSection section : list) {
                pos = addData(section.getSkyLight().getData(), metadata.data, pos);
            }
        }

        if (skylight) {
            // add biomes to the data array
            addData(chunk.getBiomeIndex(), metadata.data, pos);
        }

        return metadata;
    }

    private static int addData(byte[] newData, byte[] originalData, int dataLength) {
        System.arraycopy(newData, 0, originalData, dataLength, newData.length);
        return dataLength + newData.length;
    }

    public static class ChunkMetadata {

        private int bitmask;
        private byte[] data;

        public ChunkMetadata() {
            // empty constructor
        }

        public ChunkMetadata(int bitmask, byte[] data) {
            this.bitmask = bitmask;
            this.data = data;
        }

        public int getBitmask() {
            return bitmask;
        }

        public byte[] getData() {
            return data;
        }

    }

}
