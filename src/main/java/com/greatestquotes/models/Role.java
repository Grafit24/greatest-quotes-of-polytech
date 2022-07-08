package com.greatestquotes.models;

public record Role(long id, String name) {

    public Role {
        if (id < 1)
            id = 1;

    }
}
