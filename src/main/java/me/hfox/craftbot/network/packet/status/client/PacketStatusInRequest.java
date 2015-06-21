package me.hfox.craftbot.network.packet.status.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketStatusInRequest implements Packet {

    @Override
    public void read(PacketInputStream stream) throws IOException {
        // empty
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        // empty
    }

}
