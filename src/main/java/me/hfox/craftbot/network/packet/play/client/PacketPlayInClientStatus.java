package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayInClientStatus implements Packet {

    private int action;

    public PacketPlayInClientStatus(int action) {
        this.action = action;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.action = stream.readVarInt();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeVarInt(action);
    }

}
