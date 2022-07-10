package com.greatestquotes.utils;

public enum State {
    DONE,
    NO_CONNECTION,
    DUPLICATE,
    NO_ENTRY,
    UNKNOWN,
    CUSTOM,
    NO_PERMISSIONS;

    public String getText() {
        return switch(this) {
            case NO_CONNECTION -> "No connection.";
            case UNKNOWN -> "Something go wrong.";
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }
}
