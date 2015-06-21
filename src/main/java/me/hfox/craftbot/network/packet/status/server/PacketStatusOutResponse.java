package me.hfox.craftbot.network.packet.status.server;

import me.hfox.craftbot.ChatColor;
import me.hfox.craftbot.CraftStart;
import me.hfox.craftbot.exception.IllegalDescriptionException;
import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;
import me.hfox.craftbot.util.ChatFormatter;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class PacketStatusOutResponse implements Packet {

    private JSONObject json;

    public PacketStatusOutResponse(JSONObject json) {
        this.json = json;
    }

    public PacketStatusOutResponse(PingResponseBuilder response) {
        this.json = response.build();
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        json = stream.readJSON();
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeJSON(json);
    }

    public static class PingResponseBuilder {

        private String protocolName;
        private int protocolVersion; // 47 for 1.8
        private int maxPlayers;
        private int onlinePlayers;
        // private String[] sample;
        private String description;

        private PingResponseBuilder() {
            this.protocolName = CraftStart.class.getPackage().getImplementationTitle();
            this.protocolVersion = 47;
            this.maxPlayers = 0;
            this.onlinePlayers = -1;
            this.description = ChatColor.RED + "Parallax (Laxio)";
        }

        public String getProtocolName() {
            return protocolName;
        }

        public PingResponseBuilder setProtocolName(String protocolName) {
            this.protocolName = protocolName;
            return this;
        }

        public int getProtocolVersion() {
            return protocolVersion;
        }

        public PingResponseBuilder setProtocolVersion(int protocolVersion) {
            this.protocolVersion = protocolVersion;
            return this;
        }

        public int getMaxPlayers() {
            return maxPlayers;
        }

        public PingResponseBuilder setMaxPlayers(int maxPlayers) {
            this.maxPlayers = maxPlayers;
            return this;
        }

        public int getOnlinePlayers() {
            return onlinePlayers;
        }

        public PingResponseBuilder setOnlinePlayers(int onlinePlayers) {
            this.onlinePlayers = onlinePlayers;
            return this;
        }

        public String getDescription() {
            return description;
        }

        public PingResponseBuilder setDescription(String description) {
            setDescription(false, '&', description);
            return this;
        }

        public PingResponseBuilder setDescription(boolean replace, char ch, String motd) {
            this.description = replace ? ChatColor.translate(ch, motd) : motd;
            return this;
        }

        public PingResponseBuilder setDescription(String... lines) {
            setDescription(false, '&', lines);
            return this;
        }

        public PingResponseBuilder setDescription(boolean replace, char ch, String... lines) {
            if (lines.length > 2) {
                throw new IllegalDescriptionException("Multiline descriptions may only contain up to 2 lines");
            }

            if (lines.length <= 0) {
                setDescription(replace, ch, "");
            } else if (lines.length == 1) {
                setDescription(replace, ch, lines[0]);
            } else {
                setDescription(replace, ch, lines[0] + "\n" + ChatColor.RESET + lines[1]);
            }

            return this;
        }

        public PingResponseBuilder setDescription(List<String> lines) {
            String[] strings = new String[lines.size()];
            for (int i = 0; i < lines.size(); i++) {
                String string = lines.get(i);
                strings[i] = string;
            }

            setDescription(false, '&', strings);
            return this;
        }

        public PingResponseBuilder setDescription(boolean replace, char ch, List<String> lines) {
            String[] strings = new String[lines.size()];
            for (int i = 0; i < lines.size(); i++) {
                String string = lines.get(i);
                strings[i] = string;
            }

            setDescription(replace, ch, strings);
            return this;
        }

        public JSONObject build() {
            JSONObject json = new JSONObject();

            JSONObject version = new JSONObject();
            version.put("name", protocolName);
            version.put("protocol", protocolVersion);

            JSONObject players = new JSONObject();
            players.put("max", maxPlayers);
            players.put("online", onlinePlayers);

            JSONObject description = ChatFormatter.serialize(this.description);

            json.put("version", version);
            json.put("players", players);
            json.put("description", description);
            return json;
        }

        public static PingResponseBuilder start(int players, int maxPlayers) {
            PingResponseBuilder builder = new PingResponseBuilder();
            builder.onlinePlayers = players;
            builder.maxPlayers = maxPlayers;
            return builder;
        }

    }

}
