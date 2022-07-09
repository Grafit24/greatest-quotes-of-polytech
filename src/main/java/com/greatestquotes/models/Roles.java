package com.greatestquotes.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class Roles extends HashSet<Role> {
    // TODO Сделать так чтобы эти значения парсило при загрузке
    public static final Role USER = new Role(1, "USER");
    public static final Role MODERATOR = new Role(3, "MODERATOR");
    public static final Role SUPERUSER = new Role(2, "SUPERUSER");
    public static final Role GUEST = new Role(4, "GUEST");

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
            if (r.name()==role_name)
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

            while (result.next()) {
                this.add(new Role(result.getLong(1), result.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHandler.closeConnection();
        }
    }
}
