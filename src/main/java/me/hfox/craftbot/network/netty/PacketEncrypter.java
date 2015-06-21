package me.hfox.craftbot.network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.hfox.craftbot.util.EncryptionHandler;

import javax.crypto.Cipher;

public class PacketEncrypter extends MessageToByteEncoder<ByteBuf> {

    private EncryptionHandler handler;

    public PacketEncrypter(Cipher ciper) {
        this.handler = new EncryptionHandler(ciper);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        handler.encrypt(byteBuf, byteBuf2);
    }

}
