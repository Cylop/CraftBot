package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutJoinGame implements Packet {

    private int entityId;
    private int gamemode;
    private int dimension;
    private int difficulty;
    private int maxPlayers;
    private String level;
    private boolean debug;

    public PacketPlayOutJoinGame() {
        this(1, 1, 0, 0, 60, "default", false);
    }

    public PacketPlayOutJoinGame(int entityId, int gamemode, int dimension, int difficulty, int maxPlayers, String level, boolean debug) {
        this.entityId = entityId;
        this.gamemode = gamemode;
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.maxPlayers = maxPlayers;
        this.level = level;
        this.debug = debug;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.entityId = stream.readInt();
        this.gamemode = stream.readUnsignedByte();
        this.dimension = stream.readByte();
        this.difficulty = stream.readUnsignedByte();
        this.maxPlayers = stream.readUnsignedByte();
        this.level = stream.readString();
        this.debug = !stream.readBoolean();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeInt(entityId); // entity id
        stream.writeUnsignedByte(gamemode); // gamemode
        stream.writeByte(dimension); // dimension
        stream.writeUnsignedByte(difficulty); // difficulty
        stream.writeUnsignedByte(maxPlayers); // max players
        stream.writeString(level); // level type
        stream.writeBoolean(!debug);
    }

}
