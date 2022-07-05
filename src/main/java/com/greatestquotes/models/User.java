package com.greatestquotes.models;

import com.greatestquotes.utils.HashCode;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private long id;
    private String login;
    private String role;

    public User() {
        role = "Guest";
    }

    public String getRole() {
        return role;
    }

    public boolean auth(String login, String password) {
        String query = "SELECT id, login, role FROM `users` WHERE login=? AND password=?;";
        Connection c = DBHandler.getConnection();

        try {
            PreparedStatement p = c.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            p.setString(1, login);
            p.setString(2, HashCode.encode(password));
            ResultSet result = p.executeQuery();
            if (result.first()) {
                this.login = login;
                this.id = result.getLong("id");
                this.role = result.getString("role");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
