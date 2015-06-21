package me.hfox.craftbot.network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.hfox.craftbot.console.Log;
import me.hfox.craftbot.network.NetworkClient;
import me.hfox.craftbot.network.ProtocolDirection;
import me.hfox.craftbot.network.ProtocolState;
import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;

import java.io.IOException;
import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    private ProtocolDirection direction;

    public PacketDecoder(ProtocolDirection direction) {
        this.direction = direction;
    }

    @Override
    protected void decode(ChannelHandlerContext channel, ByteBuf byteBuf, List<Object> out) throws Exception {
        if (byteBuf.readableBytes() > 0) {
            ByteBufInputStream bbis = new ByteBufInputStream(byteBuf);
            PacketInputStream stream = new PacketInputStream(bbis);
            int id = stream.readVarInt();

            ProtocolState state = channel.channel().attr(NetworkClient.PROTOCOL_KEY).get();
            Log.debug("#" + id + " (" + state.name() + ")");

            Class<? extends Packet> type = state.getPacket(direction, id);
            if (type == null) {
                byteBuf.clear();
                throw new IOException("Bad packet id " + id);
            }

            Packet packet = ProtocolState.build(type);
            packet.read(stream);

            if (byteBuf.readableBytes() > 0) {
                throw new IOException("Packet #" + id + " was larger than expected");
            }

            out.add(packet);
        }
    }

}
