package com.greatestquotes.controllers;

import com.greatestquotes.Application;
import com.greatestquotes.models.DBHandler;
import com.greatestquotes.models.Quote;
import com.greatestquotes.models.User;
import com.greatestquotes.utils.State;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class RecordEditableController extends RecordController {
    protected Quote quote;
    protected VBox recordContainer;
    protected User user;
    protected HBox record;
    protected Label messageLabel;
    protected Application rootApp;

    @FXML
    protected Button editRecordButton;

    @FXML
    protected Button deleteRecordButton;

    @FXML
    protected void onEditRecordButtonClick() {
        try {
            // connection validation
            DBHandler.getConnection();
            DBHandler.closeConnection();

            messageLabel.setText("");
            rootApp.showEditWindow(quote);
        } catch (SQLException e) {
            State state = e.getSQLState().equals("08S01") ? State.NO_CONNECTION : State.UNKNOWN;
            messageLabel.setText(state.getText());
        }
    }

    @FXML
    protected void onDeleteRecordButtonClick() {
        State state = quote.deleteQuote(user);
        switch (state) {
            case DONE -> {
                messageLabel.setText("");
                recordContainer.getChildren().remove(record);
                if (quote.owner().equals(user.getLogin()))
                    rootApp.getMainWindowController().deleteQuoteEvent(quote);
            }
            case NO_PERMISSIONS -> {
                messageLabel.setText("You haven't permissions for this operation. Data will be updated!");
                rootApp.getMainWindowController().update();
            }
            default -> messageLabel.setText(state.getText());
        }
    }

    public void setRecordContainer(VBox recordContainer) {
        this.recordContainer = recordContainer;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setQuote(Quote q) {
        quote = q;
        setQuoteLabel(String.format("«%s»", q.quote()));
        setTeacherLabel(q.teacher());
        setSubjectLabel(q.subject());
        setDateLabel(q.date());
        setOwnerLabel(q.owner());
        if (q.permissions().w())
            editRecordButton.setVisible(true);
        if (q.permissions().d())
            deleteRecordButton.setVisible(true);
    }

    public void setRecord(HBox record) {
        this.record = record;
    }

    public void setMessageLabel(Label messageLabel) {
        this.messageLabel = messageLabel;
    }

    public void setRootApp(Application rootApp) {
        this.rootApp = rootApp;
    }
}
