package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.inventory.ItemStack;
import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayInBlockPlace implements Packet {

    private int[] location;
    private BlockFace face;
    private ItemStack item;
    private byte x;
    private byte y;
    private byte z;

    public PacketPlayInBlockPlace(int[] location, BlockFace face, ItemStack item, byte x, byte y, byte z) {
        this.location = location;
        this.face = face;
        this.item = item;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.location = stream.readPosition();
        this.face = BlockFace.fromId(stream.readByte());
        this.item = stream.readItem();
        this.x = stream.readByte();
        this.y = stream.readByte();
        this.z = stream.readByte();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writePosition(location);
        stream.writeByte(face.getId());
        stream.writeItem(item);
        stream.writeByte(x);
        stream.writeByte(y);
        stream.writeByte(z);
    }

    public enum BlockFace {

        MINUS_Y(0),
        PLUS_Y(1),
        MINUS_Z(2),
        PLUS_Z(3),
        MINUS_X(4),
        PLUS_X(5),
        UNKNOWN(255);

        private int id;

        BlockFace(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static BlockFace fromId(int id) {
            for (BlockFace type : values()) {
                if (type.id == id) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Unknown interaction Id");
        }

    }

    public enum DigStatus {

        STARTED_DIGGING(0),
        CANCELLED_DIGGING(1),
        FINISHED_DIGGING(2),
        DROP_STACK(3),
        DROP_ITEM(4),
        SHOOT_ARROW(5);

        private int id;

        DigStatus(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static DigStatus fromId(int id) {
            for (DigStatus type : values()) {
                if (type.id == id) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Unknown interaction Id");
        }

    }

}
