package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.world.Location;

import java.io.IOException;

public class PacketPlayOutBlockBreakAnimation implements Packet {

    private int entityId;
    private Location location;
    private byte stage;

    public PacketPlayOutBlockBreakAnimation(int entityId, Location location, byte stage) {
        this.entityId = entityId;
        this.location = location;
        this.stage = stage;
    }

    public int getEntityId() {
        return entityId;
    }

    public Location getLocation() {
        return location;
    }

    public byte getStage() {
        return stage;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.entityId = stream.readVarInt();
        this.location = stream.readLocation();
        this.stage = stream.readByte();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeVarInt(entityId);
        stream.writeLocation(location);
        stream.writeByte(stage);
    }

}
