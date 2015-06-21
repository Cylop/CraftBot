package me.hfox.craftbot.console;

import jline.console.ConsoleReader;
import me.hfox.craftbot.ChatColor;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

    private static Logger logger;
    private static List<Level> levels = new ArrayList<>();
    private static Map<Level, String> levelNames;
    private static PrintStream output;
    private static ConsoleReader reader;

    public static void setup() throws IOException {
        levels.add(Level.ALL);
        levels.add(Level.CONFIG);
        levels.add(Level.FINE);
        levels.add(Level.FINER);
        levels.add(Level.FINEST);
        levels.add(Level.INFO);
        levels.add(Level.OFF);
        levels.add(Level.SEVERE);
        levels.add(Level.WARNING);

        levelNames = new HashMap<>();
        for (Level level : levels) {
            ChatColor color = ChatColor.WHITE;
            if (level == Level.CONFIG) {
                color = ChatColor.AQUA;
            } else if (level == Level.WARNING) {
                color = ChatColor.RED;
            } else if (level == Level.SEVERE) {
                color = ChatColor.YELLOW;
            }

            colour(level, color);
        }

        logger = Logger.getLogger("");
        logger.setUseParentHandlers(false);
        output = System.out;

        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            logger.removeHandler(handler);
        }

        reader = new ConsoleReader(System.in, output);
        logger.addHandler(new ConsoleLogHandler(reader, new ConsoleFormatter()));
        System.setOut(new PrintStream(new LoggerOutputStream(logger, Level.INFO), true));
        System.setErr(new PrintStream(new LoggerOutputStream(logger, Level.WARNING), true));

        new CommandReader().start();
    }

    public static Logger getLogger() {
        return logger;
    }

    private static void colour(Level level, ChatColor color) {
        levelNames.put(level, "" + color + level + ChatColor.RESET);
    }

    public static void log(Level level, String msg) {
        logger.log(level, format(msg));
    }

    public static void debug(String msg) {
        logger.config(format(msg));
    }

    public static void severe(String msg) {
        logger.severe(format(msg));
    }

    public static void warning(String msg) {
        logger.warning(format(msg));
    }

    public static void info(String msg) {
        logger.info(format(msg));
    }

    public static String format(String msg) {
        return ChatColor.getConsoleString(msg) + ChatColor.RESET.toConsole();
    }

    public static void exception(Throwable throwable) {
        throwable.printStackTrace();
        // ErrorHandling.handle(throwable);
    }

    public static ConsoleReader getReader() {
        return reader;
    }

    static List<Level> getLevels() {
        return levels;
    }

    static Map<Level, String> getLevelNames() {
        return levelNames;
    }

}
