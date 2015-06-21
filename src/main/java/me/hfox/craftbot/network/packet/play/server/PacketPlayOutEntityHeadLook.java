package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutEntityHeadLook extends PacketPlayOutEntity {

    private float yaw;

    public PacketPlayOutEntityHeadLook(int entityId, float yaw) {
        super(entityId);
        this.yaw = yaw;
    }

    public float getYaw() {
        return yaw;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        super.read(stream);
        this.yaw = stream.readRotation();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        super.write(stream);
        stream.writeRotation(yaw);
    }

}
