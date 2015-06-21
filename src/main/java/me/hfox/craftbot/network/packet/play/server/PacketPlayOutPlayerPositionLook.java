package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.world.Location;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PacketPlayOutPlayerPositionLook implements Packet {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private Map<PlayerTeleportFlag, Boolean> flags;

    public PacketPlayOutPlayerPositionLook(double x, double y, double z, float yaw, float pitch, Map<PlayerTeleportFlag, Boolean> flags) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.flags = flags;
    }

    public PacketPlayOutPlayerPositionLook(double x, double y, double z, float yaw, float pitch, List<PlayerTeleportFlag> flags) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.flags = new HashMap<>();
        for (PlayerTeleportFlag flag : flags) {
            this.flags.put(flag, true);
        }
    }

    public PacketPlayOutPlayerPositionLook(Location location, List<PlayerTeleportFlag> flags) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.flags = new HashMap<>();
        for (PlayerTeleportFlag flag : flags) {
            this.flags.put(flag, true);
        }
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.x = stream.readDouble();
        this.y = stream.readDouble();
        this.z = stream.readDouble();
        this.yaw = stream.readFloat();
        this.pitch = stream.readFloat();
        this.flags = PlayerTeleportFlag.getFlags(stream.readByte());
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeDouble(x);
        stream.writeDouble(y);
        stream.writeDouble(z);
        stream.writeFloat(yaw);
        stream.writeFloat(pitch);
        stream.writeByte(PlayerTeleportFlag.getFlag(flags));
    }

    public enum PlayerTeleportFlag {

        X(0),
        Y(1),
        Z(2),
        YAW(3),
        PITCH(4);

        private int id;

        PlayerTeleportFlag(int id) {
            this.id = id;
        }

        public int mask() {
            return 1 << id;
        }

        public boolean isSet(int i) {
            return (i & mask()) == mask();
        }

        public int apply(int i) {
            i |= mask();
            return i;
        }

        public static Map<PlayerTeleportFlag, Boolean> getFlags(int i) {
            Map<PlayerTeleportFlag, Boolean> flags = new HashMap<>();
            for (PlayerTeleportFlag flag : values()) {
                flags.put(flag, flag.isSet(i));
            }

            return flags;
        }

        public static int getFlag(Map<PlayerTeleportFlag, Boolean> flags) {
            int i = 0;
            for (Entry<PlayerTeleportFlag, Boolean> flag : flags.entrySet()) {
                if (flag.getValue()) {
                    i = flag.getKey().apply(i);
                }
            }

            return i;
        }

    }

}
