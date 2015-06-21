package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.world.Location;

import java.io.IOException;

public class PacketPlayOutEffect implements Packet {

    private int effectId;
    private Location location;
    private int data;
    private boolean disabled;

    public PacketPlayOutEffect(int effectId, Location location, int data, boolean disabled) {
        this.effectId = effectId;
        this.location = location;
        this.data = data;
        this.disabled = disabled;
    }

    public int getEffectId() {
        return effectId;
    }

    public Location getLocation() {
        return location;
    }

    public int getData() {
        return data;
    }

    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.effectId = stream.readInt();
        this.location = stream.readLocation();
        this.data = stream.readInt();
        this.disabled = stream.readBoolean();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeInt(effectId);
        stream.writeLocation(location);
        stream.writeInt(data);
        stream.writeBoolean(disabled);
    }

}
