package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutSlotChange implements Packet {

    private short slot;

    public PacketPlayOutSlotChange(short slot) {
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
