package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayInEnchantItem implements Packet {

    private int id;
    private int enchantment;

    public PacketPlayInEnchantItem(int id, int enchantment) {
        this.id = id;
        this.enchantment = enchantment;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.id = stream.readByte();
        this.enchantment = stream.readByte();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeByte(id);
        stream.writeByte(enchantment);
    }

}
