package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutAnimation implements Packet {

    private int id;
    private AnimationType type;
    private float x;
    private float y;
    private float z;

    public PacketPlayOutAnimation(int id, AnimationType type) {
        this.id = id;
        this.type = type;
    }

    public PacketPlayOutAnimation(int id, AnimationType type, float x, float y, float z) {
        this(id, type);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.id = stream.readVarInt();
        this.type = AnimationType.fromId(stream.readVarInt());
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeVarInt(id);
        stream.writeVarInt(type.getId());
    }

    public enum AnimationType {

        SWING_ARM(0),
        TAKE_DAMAGE(1),
        LEAVE_BED(2),
        EAT_FOOD(3),
        CRITICAL_EFFECT(4),
        MAGICAL_CRITICAL_EFFECT(5);

        private int id;

        AnimationType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static AnimationType fromId(int id) {
            for (AnimationType type : values()) {
                if (type.id == id) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Unknown animation Id");
        }

    }

}
