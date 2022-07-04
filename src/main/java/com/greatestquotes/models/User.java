package com.greatestquotes.models;

import com.greatestquotes.utils.HashCode;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {
    private BigInteger id;
    private int role;
    public static final int SUPERUSER = 3;
    public static final int MODERATOR = 2;
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

    public static void createNewUser(String login, String password) {
        String query = "INSERT INTO users (login, password) VALUES (?, ?)";
        Connection c = DBHandler.getConnection();

        try {
            PreparedStatement p = c.prepareStatement(query);
            p.setString(1, login);
            p.setString(2, HashCode.encode(password));
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
