package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.world.Location;

import java.io.IOException;

public class PacketPlayOutBlockAction implements Packet {

    private Location location;
    private int primaryData;
    private int secondaryData;
    private int blockType;

    public PacketPlayOutBlockAction(Location location, int primaryData, int secondaryData, int blockType) {
        this.location = location;
        this.primaryData = primaryData;
        this.secondaryData = secondaryData;
        this.blockType = blockType;
    }

    public Location getLocation() {
        return location;
    }

    public int getPrimaryData() {
        return primaryData;
    }

    public int getSecondaryData() {
        return secondaryData;
    }

    public int getBlockType() {
        return blockType;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.location = stream.readLocation();
        this.primaryData = stream.readUnsignedByte();
        this.secondaryData = stream.readUnsignedByte();
        this.blockType = stream.readVarInt();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeLocation(location);
        stream.writeUnsignedByte(primaryData);
        stream.writeUnsignedByte(secondaryData);
        stream.writeVarInt(blockType);
    }
}
