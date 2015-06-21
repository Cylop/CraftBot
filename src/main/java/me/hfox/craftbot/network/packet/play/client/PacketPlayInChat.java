package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayInChat implements Packet {

    private String message;

    public PacketPlayInChat(String message) {
        this.message = message;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        message = stream.readString();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeString(message);
    }

}
