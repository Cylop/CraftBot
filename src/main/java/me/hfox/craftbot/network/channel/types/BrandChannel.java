package me.hfox.craftbot.network.channel.types;

import me.hfox.craftbot.console.Log;
import me.hfox.craftbot.network.channel.PluginChannel;

public class BrandChannel implements PluginChannel {

    private String brand;

    public BrandChannel(String brand) {
        this.brand = brand;
    }

    @Override
    public String getName() {
        return "MC|Brand";
    }

    @Override
    public void read(byte[] bytes) {
        this.brand = new String(bytes);
        Log.info("Brand: " + brand);
    }

    @Override
    public byte[] write() {
        return brand.getBytes();
    }

}
