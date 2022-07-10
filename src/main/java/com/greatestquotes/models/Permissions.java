package com.greatestquotes.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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

    public static HashMap<Role, Permissions> getPermissionsByRecordID(long id) {
        String query = "SELECT * FROM access INNER JOIN roles ON id=id_role WHERE id_record=?;";
        HashMap<Role, Permissions> rolePermissionsHashMap = new HashMap<>();

        try {
            Connection c = DBHandler.getConnection();
            PreparedStatement p = c.prepareStatement(query);
            p.setLong(1, id);
            ResultSet result = p.executeQuery();
            while (result.next()) {
                Role curRole = new Role(result.getLong("id_role"), result.getString("role_name"));
                Permissions curPerm = new Permissions(
                        result.getBoolean("op_read"),
                        result.getBoolean("op_write"),
                        result.getBoolean("op_delete")
                );
                rolePermissionsHashMap.put(curRole, curPerm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rolePermissionsHashMap;
    }
}
