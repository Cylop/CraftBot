package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayInTabComplete implements Packet {

    private String text;
    private int[] position;

    public PacketPlayInTabComplete(String text, int[] position) {
        this.text = text;
        this.position = position;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.text = stream.readString();

        boolean pos = stream.readBoolean();
        if (pos) {
            position = stream.readPosition();
        }
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeString(text);
        boolean pos = position != null;
        stream.writeBoolean(pos);
        if (pos) {
            stream.writePosition(position);
        }
    }

}
