package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutEntityTeleport extends PacketPlayOutEntity {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean onGround;

    public PacketPlayOutEntityTeleport(int entityId, double x, double y, double z, float yaw, float pitch, boolean onGround) {
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

        this.x = stream.readFixedPointInt();
        this.y = stream.readFixedPointInt();
        this.z = stream.readFixedPointInt();
        this.yaw = stream.readRotation();
        this.pitch = stream.readRotation();
        this.onGround = stream.readBoolean();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        super.write(stream);

        stream.writeFixedPointInt(x);
        stream.writeFixedPointInt(y);
        stream.writeFixedPointInt(z);
        stream.writeRotation(yaw);
        stream.writeRotation(pitch);
        stream.writeBoolean(onGround);
    }

}
