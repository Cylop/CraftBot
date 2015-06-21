package me.hfox.craftbot.exception;

public class IllegalDescriptionException extends RuntimeException {

    private static final long serialVersionUID = 8573443684468529474L;

    public IllegalDescriptionException(String message) {
        super(message);
    }

}
