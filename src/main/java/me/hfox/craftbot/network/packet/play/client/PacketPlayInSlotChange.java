package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayInSlotChange implements Packet {

    private short slot;

    public PacketPlayInSlotChange(short slot) {
        this.slot = slot;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.slot = stream.readShort();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeShort(slot);
    }

}
