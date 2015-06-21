package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.packet.play.server.PacketPlayOutBlockChange.BlockChange;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.world.Location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PacketPlayOutMultiBlockChange implements Packet {

    private int chunkX;
    private int chunkZ;
    private List<BlockChange> changes;

    public PacketPlayOutMultiBlockChange(int chunkX, int chunkZ, List<BlockChange> changes) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.changes = changes;
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public List<BlockChange> getChanges() {
        return changes;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.chunkX = stream.readInt();
        this.chunkZ = stream.readInt();

        this.changes = new ArrayList<>();
        int length = stream.readVarInt();
        for (int i = 0; i < length; i++) {
            int pos = stream.readUnsignedByte();
            int x = (pos & 0xF0) >> 4;
            int z = pos & 0x0F;
            int y = stream.readUnsignedByte();
            Location location = new Location(x, y, z);
            this.changes.add(BlockChange.fromVarInt(location, stream.readVarInt()));
        }
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeInt(chunkX);
        stream.writeInt(chunkZ);

        stream.writeVarInt(changes.size());
        for (BlockChange change : changes) {
            Location loc = change.getLocation();
            int pos = ((loc.getBlockX() & 0xF0) << 4) & (loc.getBlockZ() & 0x0F);
            stream.writeUnsignedByte(pos);
            stream.writeUnsignedByte(loc.getBlockY());
            stream.writeVarInt(change.asVarInt());
        }
    }

}
