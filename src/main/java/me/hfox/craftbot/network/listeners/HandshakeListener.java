package me.hfox.craftbot.network.listeners;

import me.hfox.craftbot.network.NetworkClient;
import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.packet.handshake.client.PacketHandshakeInHandshake;

public class HandshakeListener implements PacketListener {

    private NetworkClient network;

    public HandshakeListener(NetworkClient network) {
        this.network = network;
    }

    @Override
    public void handle(Packet packet) {
        if (packet instanceof PacketHandshakeInHandshake) {
            onHandshake((PacketHandshakeInHandshake) packet);
        }
    }

    public void onHandshake(PacketHandshakeInHandshake packet) {
        network.setProtocol(packet.getState());
    }

}
