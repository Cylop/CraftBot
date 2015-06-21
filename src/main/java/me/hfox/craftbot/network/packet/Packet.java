package me.hfox.craftbot.network.packet;

import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public interface Packet {

    void read(PacketInputStream stream) throws IOException;

    void write(PacketOutputStream stream) throws IOException;

}
