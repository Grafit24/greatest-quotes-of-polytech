package com.greatestquotes.controllers;

import com.greatestquotes.models.Quote;
import com.greatestquotes.models.User;
import com.greatestquotes.utils.State;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RecordEditableController extends RecordController {
    protected Quote quote;
    protected VBox recordContainer;
    protected User user;
    protected HBox record;

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
        else
            System.out.println(state);
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
        if (q.w())
            editRecordButton.setVisible(true);
        if (q.d())
            deleteRecordButton.setVisible(true);
    }

    public void setRecord(HBox record) {
        this.record = record;
    }
}
