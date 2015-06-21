package me.hfox.craftbot.metadata.types;

import me.hfox.craftbot.metadata.MetadataObject;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class MetadataShort extends MetadataObject<Short> {

    public MetadataShort(int id, int type, PacketInputStream stream) throws IOException {
        super(id, type, stream);
    }

    public MetadataShort(int id, int type, short value) {
        super(id, type, value);
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.value = stream.readShort();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeShort(value);
    }

}
