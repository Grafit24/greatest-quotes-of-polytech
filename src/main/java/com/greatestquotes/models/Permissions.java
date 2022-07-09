package com.greatestquotes.models;

public record Permissions(boolean r, boolean w, boolean d) {
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (r) builder.append('r');
        else builder.append('-');
        if (w) builder.append('w');
        else builder.append('-');
        if (d) builder.append('d');
        else builder.append('-');
        return builder.toString();
    }
}
