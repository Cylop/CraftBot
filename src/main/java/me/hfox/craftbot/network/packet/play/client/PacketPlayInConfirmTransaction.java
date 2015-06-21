package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayInConfirmTransaction implements Packet {

    private int id;
    private short action;
    private boolean accepted;

    public PacketPlayInConfirmTransaction(int id, short action, boolean accepted) {
        this.id = id;
        this.action = action;
        this.accepted = accepted;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.id = stream.readByte();
        this.action = stream.readShort();
        this.accepted = stream.readBoolean();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeUnsignedByte(id);
        stream.writeShort(action);
        stream.writeBoolean(accepted);
    }

}
