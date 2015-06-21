package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.world.Location;

import java.io.IOException;

public class PacketPlayInPlayerPosition implements Packet {

    private double x;
    private double y;
    private double z;
    private boolean onGround;

    public PacketPlayInPlayerPosition(double x, double y, double z, boolean onGround) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.onGround = onGround;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Location getLocation() {
        return new Location(x, y, z);
    }

    public boolean isOnGround() {
        return onGround;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.x = stream.readDouble();
        this.y = stream.readDouble();
        this.z = stream.readDouble();
        this.onGround = stream.readBoolean();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeDouble(x);
        stream.writeDouble(y);
        stream.writeDouble(z);
        stream.writeBoolean(onGround);
    }

}
