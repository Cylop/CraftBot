package me.hfox.craftbot.network.listeners;

import me.hfox.craftbot.network.packet.Packet;

import java.io.IOException;

public interface PacketListener {

    void handle(Packet packet) throws IOException;

}
