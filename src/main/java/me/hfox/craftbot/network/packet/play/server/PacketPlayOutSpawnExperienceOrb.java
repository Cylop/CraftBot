package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.world.Location;

import java.io.IOException;

public class PacketPlayOutSpawnExperienceOrb extends PacketPlayOutSpawn {

    private Location location;
    private short count;

    public PacketPlayOutSpawnExperienceOrb(int entityId, Location location, short count) {
        super(entityId);
        this.location = location;
        this.count = count;
    }

    public Location getLocation() {
        return location;
    }

    public short getCount() {
        return count;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        super.read(stream);

        double x = stream.readFixedPointInt();
        double y = stream.readFixedPointInt();
        double z = stream.readFixedPointInt();
        this.location = new Location(x, y, z);
        this.count = stream.readShort();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        super.write(stream);

        stream.writeFixedPointInt(location.getX());
        stream.writeFixedPointInt(location.getY());
        stream.writeFixedPointInt(location.getZ());
        stream.writeShort(count);
    }

}
