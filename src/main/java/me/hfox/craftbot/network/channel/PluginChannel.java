package me.hfox.craftbot.network.channel;

public interface PluginChannel {

    String getName();

    void read(byte[] bytes);

    byte[] write();

}
