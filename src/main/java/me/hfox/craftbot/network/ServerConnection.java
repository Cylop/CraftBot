package me.hfox.craftbot.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import me.hfox.craftbot.bot.GlacierBot;
import me.hfox.craftbot.console.Log;

public class ServerConnection extends Thread {

    private final GlacierBot manager;
    private final String address;
    private final int port;

    private ChannelFuture channel;
    private EventLoopGroup worker;

    public ServerConnection(GlacierBot maanger, String address, int port) {
        this.manager = maanger;
        this.address = address;
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    @Override
    public void run() {
        synchronized (address) {
            worker = new NioEventLoopGroup();

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.handler(new CraftChannelHandler(manager));

            Log.info("Connecting to " + address + ":" + port);
            channel = bootstrap.connect(address, port).syncUninterruptibly();
        }
    }

    public void close() {
        channel.channel().close();
    }

}
