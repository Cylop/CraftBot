package me.hfox.craftbot.metadata.types;

import me.hfox.craftbot.metadata.MetadataObject;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class MetadataString extends MetadataObject<String> {

    public MetadataString(int id, int type, PacketInputStream stream) throws IOException {
        super(id, type, stream);
    }

    public MetadataString(int id, int type, String value) {
        super(id, type, value);
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.value = stream.readString();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeString(value);
    }

}
