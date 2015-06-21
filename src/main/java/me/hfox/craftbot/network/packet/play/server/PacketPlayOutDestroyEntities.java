package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutDestroyEntities implements Packet {

    private int[] entities;

    public PacketPlayOutDestroyEntities(int[] entities) {
        this.entities = entities;
    }

    public int[] getEntities() {
        return entities;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.entities = new int[stream.readVarInt()];
        for (int i = 0; i < entities.length; i++) {
            this.entities[i] = stream.readVarInt();
        }
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeVarInt(entities.length);
        for (int entityId : entities) {
            stream.writeVarInt(entityId);
        }
    }

}
