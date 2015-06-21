package me.hfox.craftbot.metadata;

import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Metadata {

    private Map<Integer, MetadataObject> meta;

    public Metadata() {
        this(null);
    }

    public Metadata(Map<Integer, MetadataObject> meta) {
        this.meta = meta == null ? new HashMap<>() : meta;
    }

    public void read(PacketInputStream stream) throws IOException {
        byte id = stream.readByte();
        do {
            int index = id & 0x1F;
            int type = id >> 5;

            MetadataType metadataType = MetadataType.getType(type);
            if (metadataType == null) {
                throw new IOException("Invalid MetadataType supplied (" + type + ")");
            }

            MetadataObject object = metadataType.getObject(index, type, stream);
            object.read(stream);

            meta.put(index, object);
            id = stream.readByte();
        } while (id != 127);
    }

    public void write(PacketOutputStream stream) throws IOException {
        for (Entry<Integer, MetadataObject> entry : meta.entrySet()) {
            MetadataObject object = entry.getValue();
            int id = (object.getType() << 5 | object.getId() & 0x1F) & 0xFF;
            stream.writeByte(id);
            object.write(stream);
        }
    }

}
