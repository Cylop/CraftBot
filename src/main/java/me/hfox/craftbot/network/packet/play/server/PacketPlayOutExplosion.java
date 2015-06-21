package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.world.Location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PacketPlayOutExplosion implements Packet {

    private float x;
    private float y;
    private float z;
    private float radius;
    private List<Location> records;
    private float motionX;
    private float motionY;
    private float motionZ;

    public PacketPlayOutExplosion(float x, float y, float z, float radius, List<Location> records, float motionX, float motionY, float motionZ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
        this.records = records;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getRadius() {
        return radius;
    }

    public List<Location> getRecords() {
        return records;
    }

    public float getMotionX() {
        return motionX;
    }

    public float getMotionY() {
        return motionY;
    }

    public float getMotionZ() {
        return motionZ;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.x = stream.readFloat();
        this.y = stream.readFloat();
        this.z = stream.readFloat();
        this.radius = stream.readFloat();

        this.records = new ArrayList<>();
        int length = stream.readInt();
        for (int i = 0; i < length; i++) {
            int relX = stream.readByte();
            int relY = stream.readByte();
            int relZ = stream.readByte();
            this.records.add(new Location(relX, relY, relZ));
        }

        this.motionX = stream.readFloat();
        this.motionY = stream.readFloat();
        this.motionZ = stream.readFloat();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {

    }

}
