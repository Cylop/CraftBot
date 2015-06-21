package me.hfox.craftbot.metadata.types;

import me.hfox.craftbot.metadata.MetadataObject;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.world.Location;

import java.io.IOException;

public class MetadataLocation extends MetadataObject<Location> {

    public MetadataLocation(int id, int type, PacketInputStream stream) throws IOException {
        super(id, type, stream);
    }

    public MetadataLocation(int id, int type, Location value) {
        super(id, type, value);
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        int x = stream.readInt();
        int y = stream.readInt();
        int z = stream.readInt();
        this.value = new Location(x, y, z);
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeInt(value.getBlockX());
        stream.writeInt(value.getBlockY());
        stream.writeInt(value.getBlockZ());
    }

}
