package me.hfox.craftbot.metadata.types;

import me.hfox.craftbot.metadata.MetadataObject;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class MetadataFloat extends MetadataObject<Float> {

    public MetadataFloat(int id, int type, PacketInputStream stream) throws IOException {
        super(id, type, stream);
    }

    public MetadataFloat(int id, int type, float value) {
        super(id, type, value);
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.value = stream.readFloat();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeFloat(value);
    }

}
