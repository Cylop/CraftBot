package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayInCloseWindow implements Packet {

    private int id;

    public PacketPlayInCloseWindow(int id) {
        this.id = id;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.id = stream.readUnsignedByte();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeUnsignedByte(id);
    }

}
