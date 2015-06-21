package me.hfox.craftbot.network.packet.play.client;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayInResourcePackStatus implements Packet {

    private String hash;
    private ResourcePackResult result;

    public PacketPlayInResourcePackStatus(String hash, ResourcePackResult result) {
        this.hash = hash;
        this.result = result;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.hash = stream.readString();
        this.result = ResourcePackResult.fromId(stream.readVarInt());
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeString(hash);
        stream.writeVarInt(result.getId());
    }

    public enum ResourcePackResult {

        LOADED(0),
        DECLINED(1),
        FAILED(2),
        ACCEPTED(3);

        private int id;

        ResourcePackResult(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static ResourcePackResult fromId(int id) {
            for (ResourcePackResult type : values()) {
                if (type.id == id) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Unknown result Id");
        }

    }

}
