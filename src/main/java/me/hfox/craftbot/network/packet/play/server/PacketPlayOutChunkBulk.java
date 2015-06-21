package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.packet.play.server.PacketPlayOutChunkData.ChunkMetadata;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.world.Chunk;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PacketPlayOutChunkBulk implements Packet {

    private boolean skylight;
    private Map<Chunk, ChunkMetadata> chunks;

    public PacketPlayOutChunkBulk(boolean skylight, Map<Chunk, ChunkMetadata> chunks) {
        this.skylight = skylight;
        this.chunks = chunks;
    }

    public PacketPlayOutChunkBulk(boolean skylight, boolean entireChunk, int bitmask, List<Chunk> chunks) {
        this.skylight = skylight;
        this.chunks = new HashMap<>();
        for (Chunk chunk : chunks) {
            ChunkMetadata metadata = PacketPlayOutChunkData.getData(chunk, skylight, entireChunk, bitmask);
            this.chunks.put(chunk, metadata);
        }
    }

    public boolean hasSkyLight() {
        return skylight;
    }

    public Map<Chunk, ChunkMetadata> getChunks() {
        return chunks;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.skylight = stream.readBoolean();

        this.chunks = new HashMap<>();
        int length = stream.readVarInt();
        for (int i = 0; i < length; i++) {
            int x = stream.readInt();
            int z = stream.readInt();
            Chunk chunk = new Chunk(x, z);
            ChunkMetadata metadata = new ChunkMetadata(stream.readUnsignedShort(), stream.readBytes());
            this.chunks.put(chunk, metadata);
        }
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeBoolean(skylight);

        stream.writeVarInt(chunks.size());
        for (Entry<Chunk, ChunkMetadata> entry : chunks.entrySet()) {
            Chunk chunk = entry.getKey();
            ChunkMetadata metadata = entry.getValue();
            stream.writeInt(chunk.getX());
            stream.writeInt(chunk.getZ());
            stream.writeUnsignedShort(metadata.getBitmask());
            stream.writeBytes(metadata.getData());
        }
    }

}
