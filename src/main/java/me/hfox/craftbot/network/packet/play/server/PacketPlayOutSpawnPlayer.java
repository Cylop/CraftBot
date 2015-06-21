package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.metadata.Metadata;
import me.hfox.craftbot.inventory.ItemStack;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;
import java.util.UUID;

public class PacketPlayOutSpawnPlayer extends PacketPlayOutSpawn {

    private UUID uuid;
    private int x;
    private int y;
    private int z;
    private float yaw;
    private float pitch;
    private ItemStack currentItem;
    private Metadata metadata;

    public PacketPlayOutSpawnPlayer(int entityId, UUID uuid, int x, int y, int z, float yaw, float pitch, ItemStack currentItem, Metadata metadata) {
        super(entityId);
        this.uuid = uuid;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.currentItem = currentItem;
        this.metadata = metadata;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        super.read(stream);

        this.uuid = stream.readUUID();
        this.x = stream.readInt();
        this.y = stream.readInt();
        this.z = stream.readInt();
        this.yaw = stream.readRotation();
        this.pitch = stream.readRotation();
        this.currentItem = stream.readItem();
        this.metadata = stream.readMetadata();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        super.write(stream);

        stream.writeUUID(uuid);
        stream.writeInt(x);
        stream.writeInt(y);
        stream.writeInt(z);
        stream.writeRotation(yaw);
        stream.writeRotation(pitch);
        stream.writeItem(currentItem);
        stream.writeMetadata(metadata);
    }

}
