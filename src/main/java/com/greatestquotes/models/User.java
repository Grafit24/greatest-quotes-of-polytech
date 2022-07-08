package com.greatestquotes.models;

import com.greatestquotes.utils.HashCode;
import com.greatestquotes.utils.State;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// TODO Сделать singleton (?)
public class User {
    private long id;
    private String login;
    private Roles roles;

    public User() {
        reset();
    }

    public void reset() {
        roles = new Roles();
        roles.add(Roles.GUEST);
        login = "Guest";
        id = -1;
    }

    public Roles getRoles() {
        return roles;
    }

    public String getLogin() {
        return login;
    }

    public long getID() {
        return id;
    }

    public State auth(String login, String password) {
        String query = "SELECT users.id as id, roles.id as role_id, roles.role_name as role_name FROM " +
                "users INNER JOIN credentials INNER JOIN roles ON users.id=id_user AND id_role=roles.id " +
                "WHERE login=? AND password=?;";

        try {
            Connection c = DBHandler.getConnection();
            PreparedStatement p = c.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            p.setString(1, login);
            p.setString(2, HashCode.encode(password));
            ResultSet result = p.executeQuery();
            if (result.first()) {
                this.id = result.getLong("id");
                this.login = login;
                this.roles.clear();
                long role_id = result.getLong("role_id");
                String role_name = result.getString("role_name");
                this.roles.add(new Role(role_id, role_name));
            } else
                return State.NO_ENTRY;

            while (result.next()) {
                if (this.id != result.getLong("id"))
                    throw new SQLException("Something go wrong with auth query!");
                long role_id = result.getLong("role_id");
                String role_name = result.getString("role_name");
                this.roles.add(new Role(role_id, role_name));
            }
            result.close();
            return State.DONE;
        } catch (SQLException e) {
            e.printStackTrace();
            return switch (e.getSQLState()) {
                case "08S01" -> State.NO_CONNECTION;
                default -> State.UNKNOWN;
            };
        }
    }

    public State edit(String newLogin, String newPassword) {
        String query = "UPDATE users SET login=?, password=? WHERE id=?;";

        try {
            Connection c = DBHandler.getConnection();
            PreparedStatement p = c.prepareStatement(query);
            p.setString(1, newLogin);
            p.setString(2, HashCode.encode(newPassword));
            p.setLong(3, id);
            p.executeUpdate();
            return State.DONE;
        } catch (SQLException e) {
            e.printStackTrace();
            return switch (e.getSQLState()) {
                case "08S01" -> State.NO_CONNECTION;
                case "23000" -> State.DUPLICATE;
                default -> State.UNKNOWN;
            };
        }
    }

    public static State createNewUser(String login, String password) {
        String query = "INSERT INTO users (login, password) VALUES (?, ?)";

        try {
            Connection c = DBHandler.getConnection();
            PreparedStatement p = c.prepareStatement(query);
            p.setString(1, login);
            p.setString(2, HashCode.encode(password));
            p.executeUpdate();
            return State.DONE;
        } catch (SQLException e) {
            e.printStackTrace();
            return switch (e.getSQLState()) {
                case "08S01" -> State.NO_CONNECTION;
                case "23000" -> State.DUPLICATE;
                default -> State.UNKNOWN;
            };
        }
    }
}
