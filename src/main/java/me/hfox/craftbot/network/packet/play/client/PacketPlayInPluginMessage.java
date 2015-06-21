package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.Glacier;
import me.hfox.craftbot.network.channel.PluginChannel;
import me.hfox.craftbot.network.channel.PluginChannelManager;
import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayInPluginMessage implements Packet {

    private String channel;
    private PluginChannel pluginChannel;
    private byte[] data;

    public PacketPlayInPluginMessage(String channel, PluginChannel pluginChannel) {
        this.channel = channel;
        this.pluginChannel = pluginChannel;
        this.data = pluginChannel.write();
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.channel = stream.readString();

        PluginChannelManager manager = Glacier.get().getChannelManager();
        this.pluginChannel = manager.hasChannel(channel) ? manager.create(channel) : null;
        this.data = stream.readBytes();

        if (pluginChannel != null) {
            pluginChannel.read(data);
        }
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeString(channel);
        stream.writeBytes(data);
    }

}
