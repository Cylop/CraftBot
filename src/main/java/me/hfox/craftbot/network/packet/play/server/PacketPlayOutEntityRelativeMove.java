package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutEntityRelativeMove extends PacketPlayOutEntity {

    private double x;
    private double y;
    private double z;
    private boolean onGround;

    public PacketPlayOutEntityRelativeMove(int entityId, double x, double y, double z, boolean onGround) {
        super(entityId);
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

    public boolean isOnGround() {
        return onGround;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        super.read(stream);

        this.x = stream.readFixedPointByte();
        this.y = stream.readFixedPointByte();
        this.z = stream.readFixedPointByte();
        this.onGround = stream.readBoolean();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        super.write(stream);

        stream.writeFixedPointByte(x);
        stream.writeFixedPointByte(y);
        stream.writeFixedPointByte(z);
        stream.writeBoolean(onGround);
    }

}
