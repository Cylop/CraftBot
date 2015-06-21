package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.inventory.ItemStack;
import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutEntityEquipment implements Packet {

    private int entityId;
    private short slot;
    private ItemStack item;

    public PacketPlayOutEntityEquipment(int entityId, short slot, ItemStack item) {
        this.entityId = entityId;
        this.slot = slot;
        this.item = item;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.entityId = stream.readVarInt();
        this.slot = stream.readShort();
        this.item = stream.readItem();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeVarInt(entityId);
        stream.writeShort(slot);
        stream.writeItem(item);
    }

}
