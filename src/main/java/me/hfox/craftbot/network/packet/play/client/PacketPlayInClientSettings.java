package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayInClientSettings implements Packet {

    private String locale;
    private int distance;
    private int mode;
    private boolean colours;
    private int skinParts;

    public PacketPlayInClientSettings(String locale, int distance, int mode, boolean colours, int skinParts) {
        this.locale = locale;
        this.distance = distance;
        this.mode = mode;
        this.colours = colours;
        this.skinParts = skinParts;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.locale = stream.readString();
        this.distance = stream.readByte();
        this.mode = stream.readByte();
        this.colours = stream.readBoolean();
        this.skinParts = stream.readUnsignedByte();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeString(locale);
        stream.writeByte(distance);
        stream.writeByte(mode);
        stream.writeBoolean(colours);
        stream.writeUnsignedByte(skinParts);
    }

}
