package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutSoundEffect implements Packet {

    private String name;
    private int posX;
    private int posY;
    private int posZ;
    private float volume;
    private int pitch;

    public PacketPlayOutSoundEffect(String name, int posX, int posY, int posZ, float volume, int pitch) {
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.volume = volume;
        this.pitch = pitch;
    }

    public String getName() {
        return name;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getPosZ() {
        return posZ;
    }

    public float getVolume() {
        return volume;
    }

    public int getPitch() {
        return pitch;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.name = stream.readString();
        this.posX = stream.readInt();
        this.posY = stream.readInt();
        this.posZ = stream.readInt();
        this.volume = stream.readFloat();
        this.pitch = stream.readUnsignedByte();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeString(name);
        stream.writeInt(posX);
        stream.writeInt(posY);
        stream.writeInt(posZ);
        stream.writeFloat(volume);
        stream.writeUnsignedByte(pitch);
    }

}

