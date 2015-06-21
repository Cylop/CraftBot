package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutSetExperience implements Packet {

    private float bar;
    private int level;
    private int totalExperience;

    public PacketPlayOutSetExperience(float bar, int level, int totalExperience) {
        this.bar = bar;
        this.level = level;
        this.totalExperience = totalExperience;
    }

    public float getBar() {
        return bar;
    }

    public int getLevel() {
        return level;
    }

    public int getTotalExperience() {
        return totalExperience;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.bar = stream.readFloat();
        this.level = stream.readVarInt();
        this.totalExperience = stream.readVarInt();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeFloat(bar);
        stream.writeVarInt(level);
        stream.writeVarInt(totalExperience);
    }

}
