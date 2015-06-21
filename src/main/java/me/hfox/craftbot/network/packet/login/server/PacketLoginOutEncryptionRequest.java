package me.hfox.craftbot.network.packet.login.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.util.ProtocolEncryption;

import java.io.IOException;
import java.security.PublicKey;

public class PacketLoginOutEncryptionRequest implements Packet {

    private String server;
    private PublicKey key;
    private byte[] verifyToken;

    public PacketLoginOutEncryptionRequest(String server, PublicKey key, byte[] verifyToken) {
        this.server = server;
        this.key = key;
        this.verifyToken = verifyToken;
    }

    public String getServer() {
        return server;
    }

    public PublicKey getKey() {
        return key;
    }

    public byte[] getVerifyToken() {
        return verifyToken;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        server = stream.readString();
        key = ProtocolEncryption.getPublicKey(stream.readBytes());
        verifyToken = stream.readBytes();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeString(server);
        stream.writeBytes(key.getEncoded());
        stream.writeBytes(verifyToken);
    }

}
