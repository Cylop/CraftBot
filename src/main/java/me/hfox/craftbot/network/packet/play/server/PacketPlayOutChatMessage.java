package me.hfox.craftbot.network.packet.play.server;

import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.util.ChatFormatter;
import org.json.JSONObject;

import java.io.IOException;

public class PacketPlayOutChatMessage implements Packet {

    private JSONObject message;
    private MessagePosition position;

    public PacketPlayOutChatMessage(String message, MessagePosition position) {
        this(ChatFormatter.serialize(message), position);
    }

    public PacketPlayOutChatMessage(JSONObject message, MessagePosition position) {
        this.message = message;
        this.position = position;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.message = stream.readJSON();
        this.position = MessagePosition.fromId(stream.readByte());
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeJSON(message);
        stream.writeByte(position.getId());
    }

    public enum MessagePosition {

        CHAT(0),
        SYSTEM_MESSAGE(1),
        ACTION_BAR(2);

        private int id;

        MessagePosition(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static MessagePosition fromId(int id) {
            for (MessagePosition type : values()) {
                if (type.id == id) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Unknown status Id");
        }

    }

}
