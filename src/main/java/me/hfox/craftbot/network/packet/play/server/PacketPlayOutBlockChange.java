package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.world.Location;

import java.io.IOException;

public class PacketPlayOutBlockChange implements Packet {

    private Location location;
    private BlockChange change;

    public PacketPlayOutBlockChange(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public BlockChange getChange() {
        return change;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.location = stream.readLocation();
        this.change = BlockChange.fromVarInt(location, stream.readVarInt());
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeLocation(location);
        stream.writeVarInt(change.asVarInt());
    }

    public static class BlockChange {

        private Location location;
        private int id;
        private short data;

        public BlockChange(Location location, int id, short data) {
            this.location = location;
            this.id = id;
            this.data = data;
        }

        public Location getLocation() {
            return location;
        }

        public int getId() {
            return id;
        }

        public short getData() {
            return data;
        }

        public int asVarInt() {
            return (id << 4) | data;
        }

        public static BlockChange fromVarInt(Location location, int blockId) {
            int type = (blockId & 0xFFFFFFF0) >> 4;
            short data = (short) (blockId & 0xF);
            return new BlockChange(location, type, data);
        }

    }

}
