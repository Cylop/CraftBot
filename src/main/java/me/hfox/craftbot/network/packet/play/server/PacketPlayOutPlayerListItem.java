package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketPlayOutPlayerListItem implements Packet {

    private PlayerListItemAction action;

    @Override
    public void read(PacketInputStream stream) throws IOException {

    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {

    }

    public enum PlayerListItemAction {

        ADD_PLAYER(0),
        UPDATE_GAMEMODE(1),
        UPDATE_LATENCY(2),
        UPDATE_DISPLAY_NAME(3),
        REMOVE_PLAYER(4);

        private int id;

        PlayerListItemAction(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static PlayerListItemAction fromId(int id) {
            for (PlayerListItemAction type : values()) {
                if (type.id == id) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Unknown action Id");
        }

    }

}
