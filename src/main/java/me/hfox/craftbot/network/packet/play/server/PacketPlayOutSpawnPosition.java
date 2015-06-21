package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.world.Location;

import java.io.IOException;

public class PacketPlayOutSpawnPosition implements Packet {

    private Location location;

    public PacketPlayOutSpawnPosition(Location location) {
        this.location = location;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.location = stream.readLocation();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeLocation(location);
    }

}
