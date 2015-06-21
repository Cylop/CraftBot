package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutRespawn implements Packet {

    private int dimension;
    private int difficulty;
    private int gamemode;
    private String level;

    public PacketPlayOutRespawn(int dimension, int difficulty, int gamemode, String level) {
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.gamemode = gamemode;
        this.level = level;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.dimension = stream.readByte();
        this.difficulty = stream.readUnsignedByte();
        this.gamemode = stream.readUnsignedByte();
        this.level = stream.readString();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeByte(dimension);
        stream.writeUnsignedByte(difficulty);
        stream.writeUnsignedByte(gamemode);
        stream.writeString(level);
    }

}
