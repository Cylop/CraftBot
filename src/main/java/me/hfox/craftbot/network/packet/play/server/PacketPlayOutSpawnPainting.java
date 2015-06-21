package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.world.Location;

import java.io.IOException;

public class PacketPlayOutSpawnPainting extends PacketPlayOutSpawn {

    private String title;
    private Location position;
    private Direction direction;

    public PacketPlayOutSpawnPainting(int entityId, String title, Location position, Direction direction) {
        super(entityId);
        this.title = title;
        this.position = position;
        this.direction = direction;
    }

    public String getTitle() {
        return title;
    }

    public Location getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        super.read(stream);

        this.title = stream.readString();
        this.position = stream.readLocation();
        this.direction = Direction.getById(stream.readUnsignedByte());
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        super.write(stream);

        stream.writeString(title);
        stream.writeLocation(position);
        stream.writeUnsignedByte(direction.getId());
    }

    public enum Direction {

        NORTH(0),
        WEST(1),
        SOUTH(2),
        EAST(3);

        private int id;

        Direction(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Direction getById(int id) {
            for (Direction direction : values()) {
                if (direction.getId() == id) {
                    return direction;
                }
            }

            throw new IllegalArgumentException("Invalid direction id supplied: " + id);
        }

    }

}
