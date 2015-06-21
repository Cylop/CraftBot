package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutEntityStatus implements Packet {

    private int entityId;
    private EntityStatus status;

    public PacketPlayOutEntityStatus(int entityId, EntityStatus status) {
        this.entityId = entityId;
        this.status = status;
    }

    public int getEntityId() {
        return entityId;
    }

    public EntityStatus getStatus() {
        return status;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.entityId = stream.readInt();
        this.status = EntityStatus.fromId(stream.readByte());
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeInt(entityId);
        stream.writeByte(status.getId());
    }

    public enum EntityStatus {

        MINECART_SPAWNER_RESET(1),
        LIVING_ENTITY_HURT(2),
        LIVING_ENTITY_DEAD(3),
        IRON_GOLEM_ARMS(4),
        TAMEABLE_TAMING(6),
        TAMEABLE_TAMED(7),
        WOLF_SHAKING_WATER(8),
        EATING(9),
        EATING_GRASS(10),
        TNT_IGNITE(10),
        IRON_GOLEM_ROSE(11),
        VILLAGER_MATING(12),
        ANGRY_VILLAGER(13),
        HAPPY_VILLAGER(14),
        WITCH_ANIMATION(15),
        VILLAGER_CONVERSION(16),
        FIREWORK_EXPLODING(17),
        ANIMAL_IN_LOVE(18),
        RESET_SQUID_ROTATION(19),
        EXPLOSION(20),
        GUARDIAN_SOUND(21),
        ENABLE_REDUCED_DEBUG(22),
        DISABLE_REDUCED_DEBUG(23);

        private int id;

        EntityStatus(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static EntityStatus fromId(int id) {
            for (EntityStatus type : values()) {
                if (type.id == id) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Unknown status Id");
        }

    }

}
