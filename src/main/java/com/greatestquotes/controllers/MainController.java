package com.greatestquotes.controllers;

import com.greatestquotes.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Date;

public class MainController extends BaseController {

    @FXML
    protected VBox recordContainer;

    protected void addRecord(String quote, String teacher, String subject, Date date, String user) {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("recordTemplate.fxml"));

        try {
            HBox record = loader.load();
            RecordController controller = loader.getController();
            controller.setQuoteLabel(quote);
            controller.setTeacherLabel(teacher);
            controller.setSubjectLabel(subject);
            controller.setDateLabel(date);
            controller.setUserLabel(user);
            recordContainer.getChildren().add(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
