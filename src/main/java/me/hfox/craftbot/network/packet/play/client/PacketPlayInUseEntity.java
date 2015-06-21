package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayInUseEntity implements Packet {

    private int id;
    private InteractionType type;
    private float x;
    private float y;
    private float z;

    public PacketPlayInUseEntity(int id, InteractionType type) {
        this.id = id;
        this.type = type;
    }

    public PacketPlayInUseEntity(int id, InteractionType type, float x, float y, float z) {
        this(id, type);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.id = stream.readVarInt();
        this.type = InteractionType.fromId(stream.readVarInt());
        if (type == InteractionType.INTERACT_AT) {
            this.x = stream.readFloat();
            this.y = stream.readFloat();
            this.z = stream.readFloat();
        }
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeVarInt(id);
        stream.writeVarInt(type.getId());
        if (type == InteractionType.INTERACT_AT) {
            stream.writeFloat(x);
            stream.writeFloat(y);
            stream.writeFloat(z);
        }
    }

    public enum InteractionType {

        INTERACT(0),
        ATTACK(1),
        INTERACT_AT(2);

        private int id;

        InteractionType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static InteractionType fromId(int id) {
            for (InteractionType type : values()) {
                if (type.id == id) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Unknown interaction Id");
        }

    }

}
