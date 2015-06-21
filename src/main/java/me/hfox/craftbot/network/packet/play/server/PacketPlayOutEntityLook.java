package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutEntityLook extends PacketPlayOutEntity {

    private float yaw;
    private float pitch;
    private boolean onGround;

    public PacketPlayOutEntityLook(int entityId, float yaw, float pitch, boolean onGround) {
        super(entityId);
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
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

        this.yaw = stream.readRotation();
        this.pitch = stream.readRotation();
        this.onGround = stream.readBoolean();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        super.write(stream);

        stream.writeRotation(yaw);
        stream.writeRotation(pitch);
        stream.writeBoolean(onGround);
    }

}
