package in.parapengu.craftbot.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

import static in.parapengu.craftbot.logging.LogLevel.*;

public class Logger {

	private String name;
	private LogLevel level;

	private boolean debug;
	private SimpleDateFormat format;
	private Logger parent;

	protected Logger(String name) {
		this.name = name;
	}

	protected Logger(String name, LogLevel level) {
		this(name);
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public String getPrefix() {
		return (parent != null ? parent.getPrefix() : "") + (name != null ? "[" + name + "] " : "");
	}

	public LogLevel getLevel() {
		return level;
	}

	public Logger setLevel(LogLevel level) {
		this.level = level;
		return this;
	}

	public boolean isDebug() {
		return debug;
	}

	public Logger setDebug(boolean debug) {
		this.debug = debug;
		return this;
	}

	public SimpleDateFormat getFormat() {
		return format;
	}

	public Logger setFormat(SimpleDateFormat format) {
		this.format = format;
		return this;
	}

	public Logger getParent() {
		return parent;
	}

	public Logger setParent(Logger parent) {
		this.parent = parent;
		while(format == null && parent != null) {
			if(parent.getFormat() != null) {
				format = parent.getFormat();
				break;
			}

			parent = parent.getParent();
		}

		return this;
	}

	private Logger print(String message) {
		System.out.println(message);
		return this;
	}

	public Logger log(LogLevel level, Object... messages) {
		if(level == DEBUG && !debug) {
			return this;
		}

		for(Object message : messages) {
			String prefix = "";
			if(format != null || level != null) {
				String formatPre = format != null ? format.format(new Date()) : "";
				String levelPre = level != null ? level.name() : "";
				prefix = "[" + formatPre + (formatPre != null && levelPre != null ? " " : "") + levelPre + "] ";
			}

			String string = prefix + getPrefix() + message.toString();
			print(string);
		}
		return this;
	}

	public Logger log(Object... messages) {
		log(level, messages);
		return this;
	}

	public Logger debug(Object... messages) {
		log(DEBUG, messages);
		return this;
	}

	public Logger info(Object... messages) {
		log(INFO, messages);
		return this;
	}

	public Logger warning(Object... messages) {
		log(WARNING, messages);
		return this;
	}

	public Logger severe(Object... messages) {
		log(SEVERE, messages);
		return this;
	}

}
