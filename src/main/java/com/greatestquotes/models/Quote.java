package com.greatestquotes.models;

import com.greatestquotes.utils.State;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public record Quote(long id, String quote, String teacher, String subject,
                    Date date, String owner, Permissions permissions) {

    public State deleteQuote(User user) {
        String queryCheckPermissions = "SET @flag = (SELECT BIT_OR(op_delete) as d FROM " +
                "quotes INNER JOIN access ON quotes.id=access.id_record " +
                "WHERE id=? AND (id_creator=? OR id_role IN " + user.getRoles().createTemplate4Query() + " OR " +
                "(id_role=3 AND (SELECT COUNT(*) > 0 FROM moderators_groups WHERE id_moderator=? " +
                "AND id_group IN (SELECT id_role FROM credentials WHERE id_user=id_creator)) AND ?)) " +
                "GROUP BY quotes.id); ";
        String queryDelete = "CALL deleteWithCheck(@flag, ?);";

        try {
            Connection c = DBHandler.getConnection();
            PreparedStatement pPerm = c.prepareStatement(queryCheckPermissions);
            PreparedStatement pDelete = c.prepareStatement(queryDelete);
            // this record
            pPerm.setLong(1, id);
            // user is creator OR
            pPerm.setLong(2, user.getID());
            // one of his role in list OR
            int i = 3;
            for (Role role : user.getRoles())
                if (role.id() != Roles.MODERATOR.id())
                    pPerm.setLong(i++, role.id());
            // he moderator of one of creator's groups.
            pPerm.setLong(i++, user.getID());
            pPerm.setBoolean(i, user.getRoles().contain(Roles.MODERATOR));
            // Delete if he has permissions.
            pDelete.setLong(1, id);
            pPerm.executeUpdate();
            pDelete.executeUpdate();
            return State.DONE;
        } catch (SQLException e) {
            e.printStackTrace();
            return switch (e.getSQLState()) {
                case "08S01" -> State.NO_CONNECTION;
                case "42000" -> State.CUSTOM;
                default -> State.UNKNOWN;
            };
        } finally {
            DBHandler.closeConnection();
        }
    }

    // TODO Доделать(неэффективный запрос на изменения access)
//    public State editQuote(String quote, String teacher, String subject, Date date, User user,
//                           HashMap<Role, Permissions> rolesPermissionsHashMap) {
//        String queryCheckPermissions = "SELECT BIT_OR(op_write) as w FROM " +
//                "quotes INNER JOIN access ON quotes.id=access.id_record " +
//                "WHERE id=? AND (id_creator=? OR id_role IN " + user.getRoles().createTemplate4Query() + " OR " +
//                "(id_role=3 AND (SELECT COUNT(*) > 0 FROM moderators_groups WHERE id_moderator=? " +
//                "AND id_group IN (SELECT id_role FROM credentials WHERE id_user=id_creator)) AND ?)) " +
//                "GROUP BY quotes.id;";
//        String queryUpdateQuotes = "UPDATE quotes SET quote=?, teacher=?, subject=?, date=? WHERE id=?;";
//        String queryUpdateAccess = "CALL editAccess(?, ?, ?, ?, ?)";
//
//        try {
//            Connection c = DBHandler.getConnection();
//            PreparedStatement pPerm = c.prepareStatement(queryCheckPermissions);
//            PreparedStatement pQuotes = c.prepareStatement(queryUpdateQuotes);
//            PreparedStatement pAccess = c.prepareStatement(queryUpdateAccess);
//
//            // this record
//            pPerm.setLong(1, id);
//            // user is creator OR
//            pPerm.setLong(2, user.getID());
//            // one of his role in list OR
//            int i = 3;
//            for (Role role : user.getRoles())
//                if (role.id() != Roles.MODERATOR.id())
//                    pPerm.setLong(i++, role.id());
//            // he moderator of one of creator's groups.
//            pPerm.setLong(i++, user.getID());
//            pPerm.setBoolean(i, user.getRoles().contain(Roles.MODERATOR));
//            ResultSet result = pPerm.executeQuery();
//            boolean flag = result.getBoolean("w");
//
//            if (!flag)
//                return State.NO_PERMISSIONS;
//
//            pQuotes.setString(1, quote);
//            pQuotes.setString(2, teacher);
//            pQuotes.setString(3, subject);
//            pQuotes.setDate(4, new java.sql.Date(date.getTime()));
//            pQuotes.setLong(5, id);
//            pQuotes.executeUpdate();
//
//            for (Map.Entry<Role, Permissions> set: rolesPermissionsHashMap.entrySet()) {
//                Role role = set.getKey();
//                Permissions perm = set.getValue();
//
//            }
//            return State.DONE;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return switch (e.getSQLState()) {
//                case "08S01" -> State.NO_CONNECTION;
//                case "42000" -> State.CUSTOM;
//                default -> State.UNKNOWN;
//            };
//        } finally {
//            DBHandler.closeConnection();
//        }
//    }

    public static State createQuote(String quote, String teacher, String subject, Date date, User user,
                                   HashMap<Role, Permissions> rolesPermissionsHashMap) {
        if (!user.getRoles().contain(Roles.USER))
            return State.NO_PERMISSIONS;
        String queryInsertQuote = "INSERT INTO quotes(quote, teacher, subject, date, id_creator) VALUES (?, ?, ?, ?, ?);";

        String queryGetID = "SELECT id FROM quotes WHERE quote=? AND teacher=? AND subject=? AND date=? AND id_creator=?;";

        StringBuilder queryInsertRolesBuilder = new StringBuilder("INSERT INTO access VALUES ");
        queryInsertRolesBuilder.append("(?, ?, ?, ?, ?),".repeat(rolesPermissionsHashMap.size()));
        queryInsertRolesBuilder.deleteCharAt(queryInsertRolesBuilder.length()-1);
        queryInsertRolesBuilder.append(';');
        String queryInsertRoles = queryInsertRolesBuilder.toString();

        try {
            Connection c = DBHandler.getConnection();
            PreparedStatement pQuote = c.prepareStatement(queryInsertQuote);
            PreparedStatement pGetID = c.prepareStatement(queryGetID);
            PreparedStatement pAccess = c.prepareStatement(queryInsertRoles);

            pQuote.setString(1, quote);
            pQuote.setString(2, teacher);
            pQuote.setString(3, subject);
            pQuote.setDate(4, new java.sql.Date(date.getTime()));
            pQuote.setLong(5, user.getID());
            pQuote.executeUpdate();

            if (!rolesPermissionsHashMap.isEmpty()) {
                pGetID.setString(1, quote);
                pGetID.setString(2, teacher);
                pGetID.setString(3, subject);
                pGetID.setDate(4, new java.sql.Date(date.getTime()));
                pGetID.setLong(5, user.getID());
                ResultSet result = pGetID.executeQuery();
                result.next();
                long id = result.getLong("id");

                int i = 1;
                for (Map.Entry<Role, Permissions> set: rolesPermissionsHashMap.entrySet()) {
                    Role role = set.getKey();
                    Permissions perm = set.getValue();
                    pAccess.setLong(i++, id);
                    pAccess.setLong(i++, role.id());
                    pAccess.setBoolean(i++, perm.r());
                    pAccess.setBoolean(i++, perm.w());
                    pAccess.setBoolean(i++, perm.d());
                }
                pAccess.executeUpdate();
            }
            return State.DONE;
        } catch (SQLException e) {
            e.printStackTrace();
            return switch (e.getSQLState()) {
                case "08S01" -> State.NO_CONNECTION;
                default -> State.UNKNOWN;
            };
        } finally {
            DBHandler.closeConnection();
        }
    }
}
