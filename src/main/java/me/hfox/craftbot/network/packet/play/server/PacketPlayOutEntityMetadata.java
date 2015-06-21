package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.metadata.Metadata;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutEntityMetadata extends PacketPlayOutEntity {

    private Metadata metadata;

    public PacketPlayOutEntityMetadata(int entityId, Metadata metadata) {
        super(entityId);
        this.metadata = metadata;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        super.read(stream);
        this.metadata = stream.readMetadata();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        super.write(stream);
        stream.writeMetadata(metadata);
    }

}
