package me.hfox.craftbot;

import me.hfox.craftbot.console.Log;
import me.hfox.craftbot.network.channel.PluginChannelManager;

public class Glacier {

    private static Glacier instance;

    private PluginChannelManager channelManager;

    public Glacier() {
        instance = this;
        channelManager = new PluginChannelManager();
    }

    public PluginChannelManager getChannelManager() {
        return channelManager;
    }

    public void handle(String command) {
        if (command.equalsIgnoreCase("stop")) {
            System.exit(0);
            return;
        }

        Log.info("Handled command: " + command);
    }

    public static Glacier get() {
        return instance;
    }

}
