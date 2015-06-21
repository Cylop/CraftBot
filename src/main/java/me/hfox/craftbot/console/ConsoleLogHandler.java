package me.hfox.craftbot.console;

import jline.console.ConsoleReader;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ConsoleLogHandler extends ConsoleHandler {

    private ConsoleReader reader;

    public ConsoleLogHandler(ConsoleReader reader, Formatter formatter) {
        this.reader = reader;
        this.setFormatter(formatter);
    }

    public void publish(LogRecord record) {
        try {
            this.reader.print("\r");
            this.reader.flush();
            super.publish(record);
            super.flush();

            try {
                this.reader.redrawLine();
            } catch (Throwable var3) {
                this.reader.getCursorBuffer().clear();
            }

            this.reader.flush();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public synchronized void flush() {}

}
