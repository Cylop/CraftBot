package me.hfox.craftbot.metadata.types;

import me.hfox.craftbot.metadata.MetadataObject;
import me.hfox.craftbot.inventory.ItemStack;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class MetadataSlot extends MetadataObject<ItemStack> {

    public MetadataSlot(int id, int type, PacketInputStream stream) throws IOException {
        super(id, type, stream);
    }

    public MetadataSlot(int id, int type, ItemStack value) {
        super(id, type, value);
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.value = stream.readItem();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeItem(value);
    }

}
