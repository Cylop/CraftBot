package me.hfox.craftbot.network.listeners;

import me.hfox.craftbot.auth.AuthService;
import me.hfox.craftbot.auth.Session;
import me.hfox.craftbot.bot.GlacierBot;
import me.hfox.craftbot.console.Log;
import me.hfox.craftbot.network.NetworkClient;
import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.packet.login.client.PacketLoginInEncryptionResponse;
import me.hfox.craftbot.network.packet.login.server.PacketLoginOutEncryptionRequest;
import me.hfox.craftbot.util.ProtocolEncryption;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.math.BigInteger;
import java.security.PublicKey;

public class LoginListener implements PacketListener {

    private NetworkClient network;
    private SecretKey loginKey;

    public LoginListener(NetworkClient network) {
        this.network = network;
    }

    public GlacierBot getBot() {
        return network.getBot();
    }

    @Override
    public void handle(Packet packet) throws IOException {
        if (packet instanceof PacketLoginOutEncryptionRequest) {
            onEncryptionRequest((PacketLoginOutEncryptionRequest) packet);
        }
    }

    @SuppressWarnings("unchecked")
    public void onEncryptionRequest(PacketLoginOutEncryptionRequest packet) throws IOException {
        getBot().getLogger().info("Attempting to authenticate!");
        String serverId = packet.getServer().trim();
        PublicKey publicKey = ProtocolEncryption.getPublicKey(packet.getKey().getEncoded());

        SecretKey secretKey = ProtocolEncryption.generateSecretKey();
        if(!serverId.equals("-")) {
            try {
                AuthService service = getBot().getAuth();
                Session session = getBot().getSession();

                String hash = new BigInteger(ProtocolEncryption.encrypt(serverId, publicKey, secretKey)).toString(16);
                service.authenticate(service.validateSession(session), hash);
            } catch(Exception exception) {
                Log.exception(exception);
                getBot().getConnection().close();
            }
        }

        network.sendPacket(new PacketLoginInEncryptionResponse(secretKey, publicKey, packet.getVerifyToken()));
    }

}
