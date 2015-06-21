package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutEntityVelocity implements Packet {

    private int entityId;
    private short x;
    private short y;
    private short z;

    public PacketPlayOutEntityVelocity(int entityId, short x, short y, short z) {
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getEntityId() {
        return entityId;
    }

    public short getX() {
        return x;
    }

    public short getY() {
        return y;
    }

    public short getZ() {
        return z;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.entityId = stream.readVarInt();
        this.x = stream.readShort();
        this.y = stream.readShort();
        this.z = stream.readShort();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeVarInt(entityId);
        stream.writeShort(x);
        stream.writeShort(y);
        stream.writeShort(z);
    }

}
