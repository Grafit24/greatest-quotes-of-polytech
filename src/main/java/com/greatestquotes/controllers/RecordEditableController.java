package com.greatestquotes.controllers;

import com.greatestquotes.models.Quote;
import com.greatestquotes.models.User;
import com.greatestquotes.utils.State;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RecordEditableController extends RecordController {
    protected Quote quote;
    protected VBox recordContainer;
    protected User user;
    protected HBox record;
    protected Label messageLabel;

    @FXML
    protected Button editRecordButton;

    @FXML
    protected Button deleteRecordButton;

    @FXML
    protected void onEditRecordButtonClick() {

    }

    @FXML
    protected void onDeleteRecordButtonClick() {
        State state = quote.deleteQuote(user);
        if (State.DONE.equals(state))
            recordContainer.getChildren().remove(record);
        else if (State.CUSTOM.equals(state))
            messageLabel.setText("You haven't permissions for this operation. Please, update data!");
        else
            messageLabel.setText("Something go wrong.");
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
}
