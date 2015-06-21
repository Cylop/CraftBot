package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayInAnimation implements Packet {

    @Override
    public void read(PacketInputStream stream) throws IOException {
        // nothing
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        // nothing
    }

}
