package com.greatestquotes.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Quotes extends ArrayList<Quote> {

    public void parse(User user) {
        String query = "SELECT DISTINCT quotes.id as id, quote, teacher, subject, date, login as owner, " +
                "BIT_OR(op_read) as r, BIT_OR(op_write) as w, BIT_OR(op_delete) as d " +
                "FROM quotes INNER JOIN access INNER JOIN users ON quotes.id = access.id_record AND users.id=quotes.id_creator " +
                "WHERE id_creator=? OR id_role IN " + user.getRoles().createTemplate4Query() + " OR " +
                "(id_role=3 AND (SELECT COUNT(*) > 0 FROM moderators_groups WHERE id_moderator=? " +
                "AND id_group IN (SELECT id_role FROM credentials WHERE id_user=id_creator)) AND ?) " +
                "GROUP BY id, quote, teacher, subject, date, owner;";

        try {
            Connection c = DBHandler.getConnection();
            PreparedStatement p = c.prepareStatement(query);
            p.setLong(1, user.getID());
            int i = 2;
            for (Role role : user.getRoles())
                if (role.id() != Roles.getMODERATOR().id())
                    p.setLong(i++, role.id());
            p.setLong(i++, user.getID());
            p.setBoolean(i, user.getRoles().contain(Roles.getMODERATOR()));
            ResultSet result = p.executeQuery();

            while (result.next()) {
                long id = result.getLong("id");
                String quote = result.getString("quote");
                String teacher = result.getString("teacher");
                String subject = result.getString("subject");
                Date date = result.getDate("date");
                String owner = result.getString("owner");
                boolean r = result.getBoolean("r");
                boolean w = result.getBoolean("w");
                boolean d = result.getBoolean("d");
                Quote q = new Quote(id, quote, teacher, subject, date, owner, new Permissions(r, w, d));
                this.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBHandler.closeConnection();
        }
    }
}
