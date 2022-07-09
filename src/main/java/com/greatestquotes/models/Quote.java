package com.greatestquotes.models;

import com.greatestquotes.utils.State;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public record Quote(long id, String quote, String teacher, String subject,
                    Date date, String owner, boolean r, boolean w, boolean d) {

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

    public void editQuote(String quote, String teacher, String subject, Date date, String owner) {

    }
}
