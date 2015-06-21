package me.hfox.craftbot.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class EncryptionHandler {

    private Cipher cipher;
    private byte[] before = new byte[0];
    private byte[] after = new byte[0];

    public EncryptionHandler(Cipher cipher) {
        this.cipher = cipher;
    }

    private byte[] store(ByteBuf bytebuf) {
        int i = bytebuf.readableBytes();

        if (before.length < i) {
            before = new byte[i];
        }

        bytebuf.readBytes(before, 0, i);
        return before;
    }

    public ByteBuf decrypt(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf) throws ShortBufferException {
        int i = bytebuf.readableBytes();
        byte[] abyte = store(bytebuf);
        ByteBuf bytebuf1 = channelhandlercontext.alloc().heapBuffer(cipher.getOutputSize(i));

        bytebuf1.writerIndex(cipher.update(abyte, 0, i, bytebuf1.array(), bytebuf1.arrayOffset()));
        return bytebuf1;
    }

    public void encrypt(ByteBuf bytebuf, ByteBuf bytebuf1) throws ShortBufferException {
        int i = bytebuf.readableBytes();
        byte[] abyte = store(bytebuf);
        int j = cipher.getOutputSize(i);

        if (after.length < j) {
            after = new byte[j];
        }

        bytebuf1.writeBytes(after, 0, cipher.update(abyte, 0, i, after));
    }

}
