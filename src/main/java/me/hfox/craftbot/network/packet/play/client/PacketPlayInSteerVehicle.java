package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayInSteerVehicle implements Packet {

    private float sideways;
    private float forward;
    private int flags;

    public PacketPlayInSteerVehicle(float sideways, float forward, int flags) {
        this.sideways = sideways;
        this.forward = forward;
        this.flags = flags;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.sideways = stream.readFloat();
        this.forward = stream.readFloat();
        this.flags = stream.readUnsignedByte();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeFloat(sideways);
        stream.writeFloat(forward);
        stream.writeUnsignedByte(flags);
    }

}
