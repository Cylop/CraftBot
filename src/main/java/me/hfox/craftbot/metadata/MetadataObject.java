package me.hfox.craftbot.metadata;

import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public abstract class MetadataObject<T> {

    protected int id;
    protected int type;
    protected T value;

    public MetadataObject(int id, int type, PacketInputStream stream) throws IOException {
        this.id = id;
        this.type = type;
        read(stream);
    }

    public MetadataObject(int id, int type, T value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public T getValue() {
        return value;
    }

    public abstract void read(PacketInputStream stream) throws IOException;

    public abstract void write(PacketOutputStream stream) throws IOException;

}
