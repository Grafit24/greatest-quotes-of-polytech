package com.greatestquotes.controllers;

import com.greatestquotes.Application;
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
        System.out.println("Open edit profile window");
    }

    @FXML
    protected void onLogOutButtonClick() {
        user.reset();
        rootApp.showAuthWindow();
    }

    @FXML
    protected void onEditButtonClick() {
        System.out.println("Go to edit regime");
    }

    @Override
    public void setUser(User user) {
        super.setUser(user);

        if (!user.getRoles().contain(Roles.GUEST)) {
            loginLabel.setText(user.getLogin());
            loginLabel.setOpacity(1.);
            editButton.setOpacity(1.);
            editButton.setDisable(false);
            profileButton.setOpacity(1.);
            profileButton.setDisable(false);
        }
        showQuotes();
    }

    protected void showQuotes() {
        quotes = new Quotes();
        quotes.parseRead(user);
        for (Quote q : quotes)
            if (q.r())
                addRecord(q);
    }

    protected void addRecord(Quote quote) {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("recordTemplate.fxml"));

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
}
