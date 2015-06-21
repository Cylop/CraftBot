package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayInEntityAction implements Packet {

    private int entityId;
    private EntityAction action;
    private int boost;

    public PacketPlayInEntityAction(int entityId, EntityAction action, int boost) {
        this.entityId = entityId;
        this.action = action;
        this.boost = boost;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.entityId = stream.readVarInt();
        this.action = EntityAction.fromId(stream.readVarInt());
        this.boost = stream.readVarInt();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeVarInt(entityId);
        stream.writeVarInt(action.getId());
        stream.writeVarInt(boost);
    }

    public enum EntityAction {

        CROUCH(0),
        UNCROUCH(1),
        LEAVE_BED(2),
        START_SPRINTING(3),
        STOP_SPRINTING(4),
        JUMP_WITH_HORSE(5),
        OPEN_INVENTORY(6);

        private int id;

        EntityAction(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static EntityAction fromId(int id) {
            for (EntityAction type : values()) {
                if (type.id == id) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Unknown interaction Id");
        }

    }

}
