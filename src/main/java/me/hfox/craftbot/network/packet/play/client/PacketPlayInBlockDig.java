package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayInBlockDig implements Packet {

    private DigStatus status;
    private int[] location;
    private BlockFace face;

    public PacketPlayInBlockDig(DigStatus status, int[] location, BlockFace face) {
        this.status = status;
        this.location = location;
        this.face = face;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.status = DigStatus.fromId(stream.readByte());
        this.location = stream.readPosition();
        this.face = BlockFace.fromId(stream.readByte());
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeByte(status.getId());
        stream.writePosition(location);
        stream.writeByte(face.getId());
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

            throw new IllegalArgumentException("Unknown face Id");
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

            throw new IllegalArgumentException("Unknown status Id");
        }

    }

}
