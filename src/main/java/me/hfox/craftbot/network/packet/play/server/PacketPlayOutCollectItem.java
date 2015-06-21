package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutCollectItem implements Packet {

    private int collectedId;
    private int collectorId;

    public PacketPlayOutCollectItem(int collectedId, int collectorId) {
        this.collectedId = collectedId;
        this.collectorId = collectorId;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.collectedId = stream.readVarInt();
        this.collectorId = stream.readVarInt();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeVarInt(collectedId);
        stream.writeVarInt(collectorId);
    }

}
