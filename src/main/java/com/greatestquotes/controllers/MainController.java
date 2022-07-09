package com.greatestquotes.controllers;

import com.greatestquotes.models.Quote;
import com.greatestquotes.models.Quotes;
import com.greatestquotes.models.Roles;
import com.greatestquotes.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainController extends BaseController {

    protected Quotes quotes;

    @FXML
    protected VBox recordContainer;

    @FXML
    protected Label loginLabel;

    @FXML
    protected Label counterLabel;

    @FXML
    protected Button editButton;

    @FXML
    protected Button profileButton;

    @FXML
    protected void onProfileButtonClick() {
        rootApp.showProfileWindow();
    }

    @FXML
    protected void onLogOutButtonClick() {
        user.reset();
        rootApp.showAuthWindow();
    }

    @FXML
    protected void onEditButtonClick() {
        recordContainer.getChildren().clear();
        showEditableQuotes();
    }

    @Override
    public void setUser(User user) {
        super.setUser(user);

        if (!user.getRoles().contain(Roles.GUEST)) {
            loginLabel.setText(user.getLogin());
            loginLabel.setVisible(true);
            editButton.setVisible(true);
            profileButton.setVisible(true);
        }
        showReadableQuotes();
    }

    protected void showReadableQuotes() {
        quotes = new Quotes();
        quotes.parse(user);
        for (Quote q : quotes)
            if (q.r())
                addRecord(q);
    }

    protected void showEditableQuotes() {
        for (Quote q : quotes)
            if (q.w() || q.d())
                addEditableRecord(q);
    }

    protected void addRecord(Quote quote) {
        FXMLLoader loader = new FXMLLoader(rootApp.getClass().getResource("recordTemplate.fxml"));

        try {
            HBox record = loader.load();
            RecordController controller = loader.getController();
            controller.setQuoteLabel(String.format("«%s»", quote.quote()));
            controller.setTeacherLabel(quote.teacher());
            controller.setSubjectLabel(quote.subject());
            controller.setDateLabel(quote.date());
            controller.setOwnerLabel(quote.owner());
            recordContainer.getChildren().add(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void addEditableRecord(Quote quote) {
        FXMLLoader loader = new FXMLLoader(rootApp.getClass().getResource("recordEditableTemplate.fxml"));

        try {
            HBox record = loader.load();
            RecordEditableController controller = loader.getController();
            controller.setQuote(quote);
            controller.setRecordContainer(recordContainer);
            controller.setUser(user);
            controller.setRecord(record);
            recordContainer.getChildren().add(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
