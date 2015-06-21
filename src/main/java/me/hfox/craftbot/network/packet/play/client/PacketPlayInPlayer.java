package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayInPlayer implements Packet {

    private boolean onGround;

    public PacketPlayInPlayer(boolean onGround) {
        this.onGround = onGround;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.onGround = stream.readBoolean();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeBoolean(onGround);
    }

}
