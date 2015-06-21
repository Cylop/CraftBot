package me.hfox.craftbot.metadata.types;

import me.hfox.craftbot.metadata.MetadataObject;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.world.Location;

import java.io.IOException;

public class MetadataRotation extends MetadataObject<Location> {

    public MetadataRotation(int id, int type, PacketInputStream stream) throws IOException {
        super(id, type, stream);
    }

    public MetadataRotation(int id, int type, Location value) {
        super(id, type, value);
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        float pitch = stream.readFloat();
        float yaw = stream.readFloat();
        float roll = stream.readFloat();
        this.value = new Location(0, 0, 0, pitch, yaw);
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeFloat(value.getPitch());
        stream.writeFloat(value.getYaw());
        stream.writeFloat(0);
    }

}
