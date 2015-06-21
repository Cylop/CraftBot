package me.hfox.craftbot.bot;

import me.hfox.craftbot.auth.YggdrasilAuthService;
import me.hfox.craftbot.auth.YggdrasilSession;
import me.hfox.craftbot.console.Log;
import me.hfox.craftbot.network.NetworkClient;
import me.hfox.craftbot.network.ProtocolState;
import me.hfox.craftbot.network.ServerConnection;
import me.hfox.craftbot.network.packet.handshake.client.PacketHandshakeInHandshake;
import me.hfox.craftbot.network.packet.login.client.PacketLoginInStart;

import java.io.IOException;
import java.math.BigInteger;
import java.util.UUID;
import java.util.logging.Logger;

public class GlacierBot {

    private static int id = 1;

    private Logger logger;
    private YggdrasilAuthService auth;
    private YggdrasilSession session;
    private String username;
    private String uuid;
    private UUID clientToken;
    private BigInteger accessToken;

    private ServerConnection connection;
    private NetworkClient client;

    public GlacierBot(String account, String password, boolean authenticate) throws Exception {
        if (authenticate) {
            auth = new YggdrasilAuthService();
            session = auth.login(account, password);
            username = session.getSelectedProfile().getName();
            uuid = session.getSelectedProfile().getId();
            clientToken = session.getClientToken();
            accessToken = session.getAccessToken();
        } else {
            username = account.contains("@") ? "bot" + id : account;
            uuid = UUID.randomUUID().toString().replace("-", "");
            id++;
        }

        logger = Logger.getLogger(username);
        logger.setParent(Log.getLogger());

        logger.info("Connected as " + username + " (" + uuid + ")");
    }

    public String getUsername() {
        return username;
    }

    public String getUuid() {
        return uuid;
    }

    public Logger getLogger() {
        return logger;
    }

    public YggdrasilAuthService getAuth() {
        return auth;
    }

    public YggdrasilSession getSession() {
        return session;
    }

    public ServerConnection getConnection() {
        return connection;
    }

    public NetworkClient getClient() {
        return client;
    }

    public void setClient(NetworkClient client) {
        this.client = client;

        try {
            start();
        } catch (Exception ex) {
            Log.exception(ex);
        }
    }

    public void start() throws IOException {
        client.sendPacket(new PacketHandshakeInHandshake(47, connection.getAddress(), connection.getPort(), ProtocolState.LOGIN));
        client.sendPacket(new PacketLoginInStart(username));
    }

    public void connect(String address, int port) {
        connection = new ServerConnection(this, address, port);
        connection.start();
    }

}
