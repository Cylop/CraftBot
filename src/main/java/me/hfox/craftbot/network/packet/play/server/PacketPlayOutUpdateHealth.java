package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutUpdateHealth implements Packet {

    private float health;
    private int food;
    private float saturation;

    public PacketPlayOutUpdateHealth(float health, int food, float saturation) {
        this.health = health;
        this.food = food;
        this.saturation = saturation;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.health = stream.readFloat();
        this.food = stream.readVarInt();
        this.saturation = stream.readFloat();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeFloat(health);
        stream.writeVarInt(food);
        stream.writeFloat(saturation);
    }

}
