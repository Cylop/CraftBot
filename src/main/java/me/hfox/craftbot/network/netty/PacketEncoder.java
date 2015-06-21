package me.hfox.craftbot.network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.hfox.craftbot.console.Log;
import me.hfox.craftbot.network.NetworkClient;
import me.hfox.craftbot.network.ProtocolDirection;
import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    private ProtocolDirection direction;

    public PacketEncoder(ProtocolDirection direction) {
        this.direction = direction;
    }

    @Override
    protected void encode(ChannelHandlerContext context, Packet packet, ByteBuf buffer) throws Exception {
        Integer id = context.channel().attr(NetworkClient.PROTOCOL_KEY).get().getPacket(direction, packet);

        if (id == null) {
            throw new IOException("Can't serialize unregistered packet");
        } else {
            PacketOutputStream stream = new PacketOutputStream(new ByteBufOutputStream(buffer));
            stream.writeVarInt(id);
            packet.write(stream);
        }

        Log.info("Sent " + packet.getClass().getSimpleName());
    }

}
