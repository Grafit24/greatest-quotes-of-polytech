package com.greatestquotes.utils;

public record State(int state) {
    public static final State DONE = new State(-1);
    public static final State NO_CONNECTION = new State(0);
    public static final State DUPLICATE = new State(1);
    public static final State NO_ENTRY = new State(2);
    public static final State UNKNOWN = new State(1000);

    public int getState() {
        return state;
    }
}
