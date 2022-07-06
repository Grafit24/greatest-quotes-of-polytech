package com.greatestquotes.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.Date;

public class RecordController extends HBox {
    @FXML
    protected Label quoteLabel;

    @FXML
    protected Label dateLabel;

    @FXML
    protected Label subjectLabel;

    @FXML
    protected Label teacherLabel;

    @FXML
    protected Label userLabel;

    public void setQuoteLabel(String quote) {
        quoteLabel.setText(quote);
    }

    public void setDateLabel(Date date) {
        dateLabel.setText(date.toString());
    }

    public void setSubjectLabel(String subject) {
        subjectLabel.setText(subject);
    }

    public void setTeacherLabel(String teacher) {
        teacherLabel.setText(teacher);
    }

    public void setUserLabel(String user) {
        userLabel.setText(user);
    }
}
