package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.metadata.Metadata;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutSpawnObject extends PacketPlayOutSpawn {

    private ObjectType type;
    private int x;
    private int y;
    private int z;
    private float yaw;
    private float pitch;
    private Metadata metadata;

    public PacketPlayOutSpawnObject(int entityId, ObjectType type, int x, int y, int z, float yaw, float pitch, Metadata metadata) {
        super(entityId);
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.metadata = metadata;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        super.read(stream);

        this.type = ObjectType.fromId(stream.readByte());
        this.x = stream.readInt();
        this.y = stream.readInt();
        this.z = stream.readInt();
        this.yaw = stream.readRotation();
        this.pitch = stream.readRotation();
        this.metadata = stream.readMetadata();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        super.write(stream);

        stream.writeByte(type.getId());
        stream.writeInt(x);
        stream.writeInt(y);
        stream.writeInt(z);
        stream.writeRotation(yaw);
        stream.writeRotation(pitch);
        stream.writeMetadata(metadata);
    }

    public enum ObjectType {

        BOAT(1),
        ITEM(2),
        MINECART(10),
        MINECART_STORAGE(11),
        MINECART_POWERED(12),
        ACTIVATED_TNT(50),
        ARROW(60),
        SNOWBALL(61),
        EGG(62),
        FIRE_BALL(63),
        FIRE_CHARGE(64),
        ENDERPEARL(65),
        WITHER_SKULL(66),
        FALLING_OBJECT(70),
        ITEM_FRAME(71),
        EYE_OF_ENDER(72),
        POTION(73),
        FALLING_DRAGON_EGG(74),
        EXP_BOTTLE(75),
        FIREWORK_ROCKET(76),
        LEASH_KNOT(77),
        ARMOR_STAND(78),
        FISHING_FLOAT(90);

        private int id;

        ObjectType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static ObjectType fromId(int id) {
            for (ObjectType type : values()) {
                if (type.id == id) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Unknown object Id");
        }

    }

}
