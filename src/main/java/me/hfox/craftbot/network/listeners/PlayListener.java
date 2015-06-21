package me.hfox.craftbot.network.listeners;

import me.hfox.craftbot.network.NetworkClient;
import me.hfox.craftbot.network.packet.Packet;

import java.io.IOException;

public class PlayListener implements PacketListener {

    private NetworkClient network;

    public PlayListener(NetworkClient network) {
        this.network = network;
    }

    @Override
    public void handle(Packet packet) throws IOException {

    }

}
