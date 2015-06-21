package me.hfox.craftbot.network.packet.status.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketStatusInPing implements Packet {

    private long time;

    public PacketStatusInPing(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
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
