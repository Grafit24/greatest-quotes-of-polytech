package com.greatestquotes.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class Roles extends HashSet<Role> {
    private static Role USER;
    private static Role MODERATOR;
    private static Role SUPERUSER;
    private static Role GUEST;

    /* Генерируют заглушку для запроса, вида (?, ...),
    где count(?)=roles.size()
     */
    public String createTemplate4Query() {
        StringBuilder builder = new StringBuilder();
        builder.append('(');
        for (Role role : this)
            if (role.id() != Roles.MODERATOR.id())
                builder.append("?,");
        builder.deleteCharAt(builder.length()-1);
        builder.append(')');
        return builder.toString();
    }

    public boolean contain(Role role) {
        return this.stream().toList().contains(role);
    }

    public Role getByName(String role_name) {
        for (Role r : this) {
            if (r.name().equals(role_name))
                return r;
        }
        return null;
    }

    public void parse() {
        String query = "SELECT * FROM roles WHERE NOT role_name IN ('USER', 'MODERATOR', 'GUEST', 'SUPERUSER');";

        try {
            Connection c = DBHandler.getConnection();
            PreparedStatement p = c.prepareStatement(query);
            ResultSet result = p.executeQuery();

            while (result.next())
                this.add(new Role(result.getLong(1), result.getString(2)));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHandler.closeConnection();
        }
    }

    public static void initConstants() {
        String query = "SELECT * FROM roles WHERE role_name IN ('USER', 'MODERATOR', 'GUEST', 'SUPERUSER');";

        try {
            Connection c = DBHandler.getConnection();
            PreparedStatement p = c.prepareStatement(query);
            ResultSet result = p.executeQuery();

            while (result.next()) {
                long id = result.getLong(1);
                String role_name = result.getString(2);
                switch (role_name) {
                    case "USER" -> USER = new Role(id, role_name);
                    case "MODERATOR" -> MODERATOR = new Role(id, role_name);
                    case "SUPERUSER" -> SUPERUSER = new Role(id, role_name);
                    case "GUEST" -> GUEST = new Role(id, role_name);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHandler.closeConnection();
        }
    }

    public static Role getUSER() {
        if (USER == null)
            initConstants();
        return USER;
    }

    public static Role getGUEST() {
        if (GUEST == null)
            initConstants();
        return GUEST;
    }

    public static Role getMODERATOR() {
        if (MODERATOR == null)
            initConstants();
        return MODERATOR;
    }

    public static Role getSUPERUSER() {
        if (SUPERUSER == null)
            initConstants();
        return SUPERUSER;
    }
}
