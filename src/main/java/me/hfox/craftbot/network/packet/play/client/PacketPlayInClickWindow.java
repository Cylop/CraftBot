package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.inventory.ItemStack;
import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayInClickWindow implements Packet {

    private int id;
    private short slot;
    private int button;
    private short action;
    private int mode;
    private ItemStack item;

    public PacketPlayInClickWindow(int id, short slot, int button, short action, int mode, ItemStack item) {
        this.id = id;
        this.slot = slot;
        this.button = button;
        this.action = action;
        this.mode = mode;
        this.item = item;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.id = stream.readUnsignedByte();
        this.slot = stream.readShort();
        this.button = stream.readByte();
        this.action = stream.readShort();
        this.mode = stream.readByte();
        this.item = stream.readItem();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeUnsignedByte(id);
        stream.writeShort(slot);
        stream.writeByte(button);
        stream.writeShort(action);
        stream.writeByte(mode);
        stream.writeItem(item);
    }

}
