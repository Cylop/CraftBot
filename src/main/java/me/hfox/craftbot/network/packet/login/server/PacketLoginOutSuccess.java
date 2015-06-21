package me.hfox.craftbot.network.packet.login.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;
import java.util.UUID;

public class PacketLoginOutSuccess implements Packet {

    private UUID uuid;
    private String username;

    public PacketLoginOutSuccess(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        uuid = stream.readUUID();
        username = stream.readString();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeUUID(uuid, true);
        stream.writeString(username);
    }

}
