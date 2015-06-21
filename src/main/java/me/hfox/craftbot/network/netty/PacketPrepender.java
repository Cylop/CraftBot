package me.hfox.craftbot.network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.hfox.craftbot.network.stream.PacketOutputStream;

public class PacketPrepender extends MessageToByteEncoder<ByteBuf> {

    /**
     * This method checks if the length of the packet can be stored into a var int (3 bytes)
     *
     * @param channelHandlerContext The channel to send the packet to
     * @param byteBuf The buffer of bytes to be sent
     * @param byteBuf2 The final buffer of bytes to be sent
     * @throws Exception When the length of the data is not a valid var int
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        int i = byteBuf.readableBytes();
        int j = toVarInt(i);

        if (j > 3) {
            throw new IllegalArgumentException("unable to fit " + i + " into " + 3);
        } else {
            PacketOutputStream stream = new PacketOutputStream(new ByteBufOutputStream(byteBuf2));
            byteBuf2.ensureWritable(j + i);
            stream.writeVarInt(i);
            byteBuf2.writeBytes(byteBuf, byteBuf.readerIndex(), i);
        }
    }

    public static int toVarInt(int i) {
        for (int j = 1; j < 5; ++j) {
            if ((i & -1 << j * 7) == 0) {
                return j;
            }
        }

        return 5;
    }

}
