package me.hfox.craftbot.metadata.types;

import me.hfox.craftbot.metadata.MetadataObject;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class MetadataByte extends MetadataObject<Byte> {

    public MetadataByte(int id, int type, PacketInputStream stream) throws IOException {
        super(id, type, stream);
    }

    public MetadataByte(int id, int type, byte value) {
        super(id, type, value);
    }

    public boolean hasMask(byte mask) {
        return (value & mask) > 0;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.value = stream.readByte();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeByte(value);
    }

}
