package me.hfox.craftbot.console;

import me.hfox.craftbot.Glacier;

import java.io.IOException;

public class CommandReader extends Thread {

    public void run() {
        try {
            String string;
            while ((string = Log.getReader().readLine()) != null) {
                Glacier.get().handle(string);
            }
        } catch (IOException ex) {
            // ignore
        }
    }

}
