package com.greatestquotes.database;

public class User {
    private int role;
    public static final int SUPERUSER = 3;
    public static final int VALIDATOR = 2;
    public static final int USER = 1;
    public static final int GUEST = 0;

    public User() {
        role = 0;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
