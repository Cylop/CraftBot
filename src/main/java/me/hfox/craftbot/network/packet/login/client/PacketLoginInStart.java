package me.hfox.craftbot.network.packet.login.client;

import me.hfox.craftbot.auth.Profile;
import me.hfox.craftbot.network.packet.Packet;
import me.hfox.craftbot.network.stream.PacketInputStream;
import me.hfox.craftbot.network.stream.PacketOutputStream;

import java.io.IOException;

public class PacketLoginInStart implements Packet {

    private String name;
    private Profile profile;

    public PacketLoginInStart(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Profile getProfile() {
        return profile;
    }

    @Override
    public void read(PacketInputStream stream) throws IOException {
        this.name = stream.readString();
        this.profile = new Profile(null, name);
    }

    @Override
    public void write(PacketOutputStream stream) throws IOException {
        stream.writeString(name);
    }

}
