package me.hfox.craftbot.network.packet.status.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.packet.status.client.PacketStatusInPing;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketStatusOutPong implements Packet {

    private long time;

    public PacketStatusOutPong() {
        this(System.currentTimeMillis());
    }

    public PacketStatusOutPong(PacketStatusInPing packet) {
        this(packet.getTime());
    }

    public PacketStatusOutPong(long time) {
        this.time = time;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.time = stream.readLong();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeLong(time);
    }

}
