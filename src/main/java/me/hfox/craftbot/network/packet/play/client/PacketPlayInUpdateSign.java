package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.util.ChatFormatter;

import java.io.IOException;

public class PacketPlayInUpdateSign implements Packet {

    private int[] location;
    private String[] lines;

    public PacketPlayInUpdateSign(int[] location, String[] lines) {
        this.location = location;
        this.lines = lines;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.location = stream.readPosition();
        this.lines = new String[4];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = ChatFormatter.parse(stream.readJSON());
        }
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writePosition(location);
        for (String line : lines) {
            stream.writeJSON(ChatFormatter.serialize(line));
        }
    }

}
