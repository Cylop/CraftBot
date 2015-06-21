package me.hfox.craftbot.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import me.hfox.craftbot.bot.GlacierBot;
import me.hfox.craftbot.console.Log;
import me.hfox.craftbot.network.listeners.PacketListener;
import me.hfox.craftbot.network.netty.PacketDecrypter;
import me.hfox.craftbot.network.netty.PacketEncrypter;
import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.util.ProtocolEncryption;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.Random;

public class NetworkClient extends SimpleChannelInboundHandler<Packet> {

    public static final AttributeKey<ProtocolState> PROTOCOL_KEY = AttributeKey.valueOf("protocol");
    public static final Random random = new Random();

    private GlacierBot bot;
    private boolean preparing = true;
    private ChannelHandlerContext context;
    private Channel channel;
    private SocketAddress address;
    private PacketListener listener;

    private boolean encrypted;
    private SecretKey secretKey;

    public NetworkClient(GlacierBot bot) {
        this.bot = bot;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
        Log.info("Received " + packet.getClass().getSimpleName());
        listener.handle(packet);
    }

    public void useEncryption(SecretKey secretkey) {
        this.encrypted = true;
        this.channel.pipeline().addBefore("splitter", "decrypt", new PacketDecrypter(ProtocolEncryption.generateCipher(2, secretkey)));
        this.channel.pipeline().addBefore("prepender", "encrypt", new PacketEncrypter(ProtocolEncryption.generateCipher(1, secretkey)));
    }

    public GlacierBot getBot() {
        return bot;
    }

    public boolean isConnected() {
        return channel.isOpen() && channel.isWritable();
    }

    public boolean isPreparing() {
        return preparing;
    }

    public ProtocolState getProtocol() {
        return channel.attr(PROTOCOL_KEY).get();
    }

    public void setProtocol(ProtocolState state) {
        setProtocol(state, false);
    }

    public void setProtocol(ProtocolState state, boolean absent) {
        Log.debug("Setting Protocol as " + state.name());
        if (absent) {
            boolean create = false;
            if (channel.attr(PROTOCOL_KEY).get() == null)
                create = true;

            channel.attr(PROTOCOL_KEY).setIfAbsent(state);
            if (create) listener = state.create(this);
        } else {
            channel.attr(PROTOCOL_KEY).set(state);
            listener = state.create(this);
        }
    }

    public SocketAddress getAddress() {
        return address;
    }

    public void sendPacket(Packet packet) throws IOException {
        if (channel.eventLoop().inEventLoop()) {
            channel.writeAndFlush(packet);
        } else {
            channel.eventLoop().execute(() -> channel.writeAndFlush(packet));
        }
    }

}
