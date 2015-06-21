package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutTimeUpdate implements Packet {

    private long age;
    private long time;

    public PacketPlayOutTimeUpdate(long age, long time) {
        this.age = age;
        this.time = time;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.age = stream.readLong();
        this.time = stream.readLong();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeLong(age);
        stream.writeLong(time);
    }

}
