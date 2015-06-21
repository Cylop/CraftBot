package me.hfox.craftbot.console;

import me.hfox.craftbot.ChatColor;
import org.apache.commons.lang3.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ConsoleFormatter extends Formatter {

    public String format(LogRecord record) {
        Date dat = new Date();
        dat.setTime(record.getMillis());
        String message = this.formatMessage(record);
        String throwable = "";
        if (record.getThrown() != null) {
            StringWriter level = new StringWriter();
            PrintWriter pw = new PrintWriter(level);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();
            throwable = level.toString();
        }

        String level = Log.getLevelNames().get(record.getLevel());
        if (level == null) {
            level = record.getLevel().getLocalizedName();
            Log.getLevelNames().put(record.getLevel(), level);
        }

        String name = record.getLoggerName();
        name = name != null && StringUtils.isNotBlank(name) ? " [" + name + "]" : "";
        return ChatColor.getConsoleString(String.format("[%1$tl:%1$tM:%1$tS %1$Tp %3$s]%2$s: %4$s%5$s%n", dat, name, level, message, throwable));
    }

}
