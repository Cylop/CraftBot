package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutEntityAttach implements Packet {

    private int entityId;
    private int vehicleId;
    private boolean leash;

    public PacketPlayOutEntityAttach(int entityId, int vehicleId, boolean leash) {
        this.entityId = entityId;
        this.vehicleId = vehicleId;
        this.leash = leash;
    }

    public int getEntityId() {
        return entityId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public boolean isLeash() {
        return leash;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.entityId = stream.readInt();
        this.vehicleId = stream.readInt();
        this.leash = stream.readBoolean();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        stream.writeInt(vehicleId);
        stream.writeBoolean(leash);
    }

}
