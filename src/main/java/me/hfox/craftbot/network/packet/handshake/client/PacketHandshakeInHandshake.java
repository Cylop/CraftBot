package me.hfox.craftbot.network.packet.handshake.client;

import me.hfox.craftbot.network.ProtocolState;
import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketHandshakeInHandshake implements Packet {

    private int version;
    private String address;
    private int port;
    private ProtocolState state;

    public PacketHandshakeInHandshake(int version, String address, int port, ProtocolState state) {
        this.version = version;
        this.address = address;
        this.port = port;
        this.state = state;
    }

    public int getVersion() {
        return version;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public ProtocolState getState() {
        return state;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        version = stream.readVarInt();
        address = stream.readString();
        port = stream.readUnsignedShort();
        state = ProtocolState.findById(stream.readVarInt());
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeVarInt(version);
        stream.writeString(address);
        stream.writeUnsignedShort(port);
        stream.writeVarInt(state.getId());
    }

}
