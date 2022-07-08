package com.greatestquotes.models;

public record Role(long id, String name) {

    public Role(long id, String name) {
        if (id < 1)
            id = 1;

        this.id = id;
        this.name = name;
    }
}
