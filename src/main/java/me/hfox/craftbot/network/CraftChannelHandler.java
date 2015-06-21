package me.hfox.craftbot.network;

import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import me.hfox.craftbot.bot.GlacierBot;
import me.hfox.craftbot.network.netty.PacketDecoder;
import me.hfox.craftbot.network.netty.PacketEncoder;
import me.hfox.craftbot.network.netty.PacketPrepender;
import me.hfox.craftbot.network.netty.PacketSplitter;

public class CraftChannelHandler extends ChannelInitializer<SocketChannel> {

    private final GlacierBot bot;

    public CraftChannelHandler(GlacierBot bot) {
        this.bot = bot;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        try {
            channel.config().setOption(ChannelOption.TCP_NODELAY, true);
        } catch (ChannelException ex) {
            // ignore?
        }

        NetworkClient client = new NetworkClient(bot); // create network manager
        channel.pipeline().addLast(new ReadTimeoutHandler(30)); // add timeout - addLast("timeout", new ReadTimeoutHandler(30))
        // add legacy_query - addLast(addLast("legacy_query", new LegacyPingHandler(ServerConnection.this))
        channel.pipeline().addLast("splitter", new PacketSplitter()); // add splitter - addLast("splitter", new PacketSplitter())
        channel.pipeline().addLast("decoder", new PacketDecoder(ProtocolDirection.SERVERBOUND)); // add decoder - addLast("decoder", new PacketDecoder(EnumProtocolDirection.SERVERBOUND))
        channel.pipeline().addLast("prepender", new PacketPrepender()); // add prepender - addLast("prepender", new PacketPrepender())
        channel.pipeline().addLast("encoder", new PacketEncoder(ProtocolDirection.CLIENTBOUND)); // add encoder - addLast("encoder", new PacketEncoder(EnumProtocolDirection.CLIENTBOUND))
        // add network manager to global pool
        channel.pipeline().addLast("packet_handler", client); // add packet_handler as network manager
        // switch network manager to handshake mode (create and use Handshake listener)
        bot.setClient(client);
    }

}
