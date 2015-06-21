package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutEntityLookRelativeMove extends PacketPlayOutEntity {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean onGround;

    public PacketPlayOutEntityLookRelativeMove(int entityId, double x, double y, double z, float yaw, float pitch, boolean onGround) {
        super(entityId);
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
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

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
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
        this.yaw = stream.readRotation();
        this.pitch = stream.readRotation();
        this.onGround = stream.readBoolean();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        super.write(stream);

        stream.writeFixedPointByte(x);
        stream.writeFixedPointByte(y);
        stream.writeFixedPointByte(z);
        stream.writeRotation(yaw);
        stream.writeRotation(pitch);
        stream.writeBoolean(onGround);
    }

}
