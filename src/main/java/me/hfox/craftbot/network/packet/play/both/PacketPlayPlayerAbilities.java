package me.hfox.craftbot.network.packet.play.both;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayPlayerAbilities implements Packet {

    private int flags;
    private float flyingSpeed;
    private float walkingSpeed;

    public PacketPlayPlayerAbilities(int flags, float flyingSpeed, float walkingSpeed) {
        this.flags = flags;
        this.flyingSpeed = flyingSpeed;
        this.walkingSpeed = walkingSpeed;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.flags = stream.readByte();
        this.flyingSpeed = stream.readFloat();
        this.walkingSpeed = stream.readFloat();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeByte(flags);
        stream.writeFloat(flyingSpeed);
        stream.writeFloat(walkingSpeed);
    }

}
