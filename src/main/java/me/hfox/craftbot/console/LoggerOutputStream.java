package me.hfox.craftbot.console;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerOutputStream extends ByteArrayOutputStream {

    private final String separator = System.getProperty("line.separator");
    private final Logger logger;
    private final Level level;

    public LoggerOutputStream(Logger logger, Level level) {
        this.logger = logger;
        this.level = level;
    }

    public void flush() throws IOException {
        synchronized(this) {
            super.flush();
            String record = this.toString();
            super.reset();
            if(record.length() > 0 && !record.equals(this.separator)) {
                this.logger.log(this.level, record);
            }

        }
    }

}
