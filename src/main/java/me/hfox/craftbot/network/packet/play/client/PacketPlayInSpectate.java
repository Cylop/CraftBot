package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;
import java.util.UUID;

public class PacketPlayInSpectate implements Packet {

    private UUID target;

    public PacketPlayInSpectate(UUID target) {
        this.target = target;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.target = stream.readUUID();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeUUID(target);
    }

}
