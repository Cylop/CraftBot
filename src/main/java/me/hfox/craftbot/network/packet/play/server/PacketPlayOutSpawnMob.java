package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.metadata.Metadata;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutSpawnMob extends PacketPlayOutSpawn {

    private EntityType type;
    private int x;
    private int y;
    private int z;
    private float yaw;
    private float pitch;
    private float headPitch;
    private short velocityX;
    private short velocityY;
    private short velocityZ;
    private Metadata metadata;

    public PacketPlayOutSpawnMob(int entityId, EntityType type, int x, int y, int z, float yaw, float pitch, float headPitch, short velocityX, short velocityY, short velocityZ, Metadata metadata) {
        super(entityId);
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.headPitch = headPitch;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.metadata = metadata;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        super.read(stream);

        this.type = EntityType.fromId(stream.readUnsignedByte());
        this.x = stream.readInt();
        this.y = stream.readInt();
        this.z = stream.readInt();
        this.yaw = stream.readRotation();
        this.pitch = stream.readRotation();
        this.headPitch = stream.readRotation();
        this.velocityX = stream.readShort();
        this.velocityY = stream.readShort();
        this.velocityZ = stream.readShort();
        this.metadata = stream.readMetadata();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        super.write(stream);

        stream.writeUnsignedByte(type.getType());
        stream.writeInt(x);
        stream.writeInt(y);
        stream.writeInt(z);
        stream.writeRotation(yaw);
        stream.writeRotation(pitch);
        stream.writeRotation(headPitch);
        stream.writeShort(velocityX);
        stream.writeShort(velocityY);
        stream.writeShort(velocityZ);
        stream.writeMetadata(metadata);
    }

    public enum EntityType {

        MOB(48),
        MONSTER(49),
        CREEPER(50),
        SKELETON(51),
        SPIDER(52),
        GIANT_ZOMBIE(53),
        ZOMBIE(54),
        SLIME(55),
        GHAST(56),
        ZOMBIE_PIGMAN(57),
        ENDERMAN(58),
        CAVE_SPIDER(59),
        SILVERFISH(60),
        BLAZE(61),
        MAGMA_CUBE(62),
        ENDER_DRAGON(63),
        WITHER(64),
        BAT(65),
        WITCH(66),
        ENDERMITE(67),
        GUARDIAN(68),
        PIG(90),
        SHEEP(91),
        COW(92),
        CHICKEN(93),
        SQUID(94),
        WOLF(95),
        MOOSHROOM(96),
        SNOWMAN(97),
        OCELOT(98),
        IRON_GOLEM(99),
        HORSE(100),
        RABBIT(101),
        VILLAGER(120);

        private int type;

        EntityType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public static EntityType fromId(int id) {
            for (EntityType type : values()) {
                if (type.type == id) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Unknown entity type");
        }

    }

}
