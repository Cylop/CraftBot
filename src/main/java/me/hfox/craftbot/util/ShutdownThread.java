package me.hfox.craftbot.util;

import me.hfox.craftbot.console.Log;

public class ShutdownThread extends Thread {

    @Override
    public void run() {
        Log.info("Shutting down...");
    }

}
