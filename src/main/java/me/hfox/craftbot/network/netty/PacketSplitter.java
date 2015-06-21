package me.hfox.craftbot.network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import me.hfox.craftbot.network.stream.PacketInputStream;

import java.io.ByteArrayInputStream;
import java.util.List;

public class PacketSplitter extends ByteToMessageDecoder {

    public PacketSplitter() {
    }

    protected void decode(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf, List<Object> list) throws Exception {
        bytebuf.markReaderIndex();
        byte[] abyte = new byte[3];

        for (int i = 0; i < abyte.length; ++i) {
            if (!bytebuf.isReadable()) {
                bytebuf.resetReaderIndex();
                return;
            }

            abyte[i] = bytebuf.readByte();
            if (abyte[i] >= 0) {

                try (PacketInputStream stream = new PacketInputStream(new ByteArrayInputStream(abyte))) {
                    int j = stream.readVarInt();

                    if (bytebuf.readableBytes() >= j) {
                        list.add(bytebuf.readBytes(j));
                        return;
                    }

                    bytebuf.resetReaderIndex();
                }

                return;
            }
        }

        throw new CorruptedFrameException("length wider than 21-bit");
    }
}