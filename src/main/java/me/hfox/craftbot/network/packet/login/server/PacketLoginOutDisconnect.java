package me.hfox.craftbot.network.packet.login.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.util.ChatFormatter;

import java.io.IOException;

public class PacketLoginOutDisconnect implements Packet {

    private String message;

    public PacketLoginOutDisconnect(String message) {
        this.message = message;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        message = ChatFormatter.parse(stream.readJSON());
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeJSON(ChatFormatter.serialize(message));
    }

}
