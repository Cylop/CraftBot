package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayInPlayerLook implements Packet {

    private float yaw;
    private float pitch;
    private boolean onGround;

    public PacketPlayInPlayerLook(float yaw, float pitch, boolean onGround) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.yaw = stream.readFloat();
        this.pitch = stream.readFloat();
        this.onGround = stream.readBoolean();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeFloat(yaw);
        stream.writeFloat(pitch);
        stream.writeBoolean(onGround);
    }

}
