package com.greatestquotes.controllers;

import com.greatestquotes.models.Permissions;
import com.greatestquotes.models.Quote;
import com.greatestquotes.utils.State;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EditController extends CreateController {
    protected Quote q;

    @FXML
    protected AnchorPane ownerPanel;

    @Override
    protected void initialize() {
        super.initialize();
        headerLabel.setText("Edit");
        setQuoteData();
        if (!user.getLogin().equals(q.owner()))
            ownerPanel.setVisible(false);
    }

    @FXML
    protected void onSaveButtonClick() {
        String quote = quoteField.getText();
        String teacher = teacherField.getText();
        String subject = subjectField.getText();
        Date date = Date.from(dateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        State state = q.editQuote(quote, teacher, subject, date, user, rolePermissionsHashMap);
        if (State.DONE.equals(state)) {
            rootApp.getMainWindowController().editQuoteEvent(q);
            stage.close();
        }
    }

    public void setQuote(Quote quote) {
        this.q = quote;
        rolePermissionsHashMap = Permissions.getPermissionsByRecordID(quote.id());
    }

    protected void setQuoteData() {
        quoteField.setText(q.quote());
        teacherField.setText(q.teacher());
        subjectField.setText(q.subject());
        ownerLabel.setText(q.owner());
        LocalDate ld = LocalDate.of(1900+q.date().getYear(), q.date().getMonth()+1, q.date().getDate());
        dateField.setValue(ld);
    }

}
