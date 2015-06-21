package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.world.Location;

import java.io.IOException;

public class PacketPlayOutUseBed implements Packet {

    private int entityId;
    private Location location;

    public PacketPlayOutUseBed(int entityId, Location location) {
        this.entityId = entityId;
        this.location = location;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.entityId = stream.readVarInt();
        this.location = stream.readLocation();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeVarInt(entityId);
        stream.writeLocation(location);
    }

}
