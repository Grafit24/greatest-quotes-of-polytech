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

    protected Quotes quotes = null;
    protected boolean view = true;

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
    protected Button updateButton;

    @FXML
    protected Button viewButton;

    @FXML
    protected Label messageLabel;

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
        editButton.setVisible(false);
        viewButton.setVisible(true);
        recordContainer.getChildren().clear();
        showEditableQuotes();
    }

    @FXML
    protected void onViewButtonClick() {
        viewButton.setVisible(false);
        editButton.setVisible(true);
        recordContainer.getChildren().clear();
        showReadableQuotes();
    }

    @FXML
    protected void onUpdateButtonClick() {
        quotes = null;
        recordContainer.getChildren().clear();
        if (view)
            showReadableQuotes();
        else
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

    protected void loadQuotes() {
        if (quotes == null) {
            quotes = new Quotes();
            quotes.parse(user);
        }
    }

    protected void showReadableQuotes() {
        view = true;
        loadQuotes();
        for (Quote q : quotes)
            if (q.r())
                addRecord(q);
    }

    protected void showEditableQuotes() {
        view = false;
        loadQuotes();
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
            controller.setMessageLabel(messageLabel);
            recordContainer.getChildren().add(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
