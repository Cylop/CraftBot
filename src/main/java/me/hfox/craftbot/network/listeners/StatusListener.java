package me.hfox.craftbot.network.listeners;

import me.hfox.craftbot.network.NetworkClient;
import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.packet.status.client.PacketStatusInPing;
import me.hfox.craftbot.network.packet.status.client.PacketStatusInRequest;
import me.hfox.craftbot.network.packet.status.server.PacketStatusOutPong;
import me.hfox.craftbot.network.packet.status.server.PacketStatusOutResponse;
import me.hfox.craftbot.network.packet.status.server.PacketStatusOutResponse.PingResponseBuilder;

import java.io.IOException;

public class StatusListener implements PacketListener {

    private NetworkClient network;

    public StatusListener(NetworkClient network) {
        this.network = network;
    }

    @Override
    public void handle(Packet packet) throws IOException {
        if (packet instanceof PacketStatusInRequest) {
            onRequest((PacketStatusInRequest) packet);
        } else if (packet instanceof PacketStatusInPing) {
            onPing((PacketStatusInPing) packet);
        }
    }

    public void onRequest(PacketStatusInRequest packet) throws IOException {
        network.sendPacket(new PacketStatusOutResponse(PingResponseBuilder.start(0, -1)));
    }

    public void onPing(PacketStatusInPing packet) throws IOException {
        network.sendPacket(new PacketStatusOutPong(packet));
    }

}
