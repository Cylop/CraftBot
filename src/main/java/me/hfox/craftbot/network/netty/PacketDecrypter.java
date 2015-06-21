package me.hfox.craftbot.network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import me.hfox.craftbot.util.EncryptionHandler;

import javax.crypto.Cipher;
import java.util.List;

public class PacketDecrypter extends MessageToMessageDecoder<ByteBuf> {

    private EncryptionHandler handler;

    public PacketDecrypter(Cipher ciper) {
        this.handler = new EncryptionHandler(ciper);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(handler.decrypt(channelHandlerContext, byteBuf));
    }

}
