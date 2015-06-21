package me.hfox.craftbot.network.packet.login.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.util.ProtocolEncryption;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class PacketLoginInEncryptionResponse implements Packet {

    private byte[] sharedSecret;
    private byte[] verifyToken;

    public PacketLoginInEncryptionResponse(SecretKey secretKey, PublicKey publicKey, byte[] verifyToken) {
        this.sharedSecret = ProtocolEncryption.toBytes(1, publicKey, secretKey.getEncoded());
        this.verifyToken = ProtocolEncryption.toBytes(1, publicKey, verifyToken);
    }

    public byte[] getSharedSecret() {
        return sharedSecret;
    }

    public byte[] getVerifyToken() {
        return verifyToken;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        sharedSecret = stream.readBytes();
        verifyToken = stream.readBytes();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeBytes(sharedSecret);
        stream.writeBytes(verifyToken);
    }

    public SecretKey getSecretKey(PrivateKey key) {
        return ProtocolEncryption.getSecretKey(key, sharedSecret);
    }

}
