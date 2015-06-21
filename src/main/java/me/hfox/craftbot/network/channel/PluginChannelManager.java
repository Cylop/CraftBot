package me.hfox.craftbot.network.channel;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.hfox.craftbot.network.channel.types.BrandChannel;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;

public class PluginChannelManager {

    private BiMap<String, Class<? extends PluginChannel>> channels;

    public PluginChannelManager() {
        channels = HashBiMap.create();
        channels.put("MC|Brand", BrandChannel.class);
    }

    public boolean hasChannel(String channel) {
        return channels.get(channel) != null;
    }

    public PluginChannel create(String channel) {
        Class<? extends PluginChannel> cls = channels.get(channel);
        if (cls == null) {
            throw new IllegalArgumentException("Could not find a channel with the name '" + channel + "'");
        }

        final ReflectionFactory reflection = ReflectionFactory.getReflectionFactory();
        final Constructor constructor;
        try {
            constructor = reflection.newConstructorForSerialization(cls, Object.class.getDeclaredConstructor());
            return (PluginChannel) constructor.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
