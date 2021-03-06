package com.greatestquotes.controllers;

import com.greatestquotes.models.DBHandler;
import com.greatestquotes.models.Quote;
import com.greatestquotes.models.Quotes;
import com.greatestquotes.models.Roles;
import com.greatestquotes.utils.State;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;

public class MainController extends BaseController {

    protected Quotes quotes = null;
    protected boolean view = true;

    @FXML
    protected Button createButton;

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
    protected void onCreateButtonClick() {
        try {
            // connection validation
            DBHandler.getConnection();
            DBHandler.closeConnection();

            messageLabel.setText("");
            rootApp.showCreateWindow();
        } catch (SQLException e) {
            State state = e.getSQLState().equals("08S01") ? State.NO_CONNECTION : State.UNKNOWN;
            messageLabel.setText(state.getText());
        }
    }

    @FXML
    protected void onEditButtonClick() {
        editButton.setVisible(false);
        viewButton.setVisible(true);
        createButton.setVisible(true);
        recordContainer.getChildren().clear();
        view = false;
        showEditableQuotes();
        updateCount();
    }

    @FXML
    protected void onViewButtonClick() {
        viewButton.setVisible(false);
        editButton.setVisible(true);
        createButton.setVisible(false);
        counterLabel.setVisible(false);
        recordContainer.getChildren().clear();
        view = true;
        showReadableQuotes();
    }

    @FXML
    protected void onUpdateButtonClick() {
        update();
        user.parseCount();
        updateCount();
    }

    @Override
    public void initialize() {
        super.initialize();

        loginLabel.setVisible(false);
        counterLabel.setVisible(false);
        messageLabel.setText("");
        createButton.setVisible(false);
        profileButton.setVisible(false);
        editButton.setVisible(false);
        viewButton.setVisible(false);

        if (!user.getRoles().contain(Roles.getGUEST())) {
            loginLabel.setText(user.getLogin());
            loginLabel.setVisible(true);
            editButton.setVisible(true);
            profileButton.setVisible(true);
        }
    }

    protected void loadQuotes() {
        if (quotes == null) {
            quotes = new Quotes();
            quotes.parse(user);
        }
    }

    protected void showReadableQuotes() {
        for (Quote q : quotes)
            if (q.permissions().r())
                addRecord(q);
    }

    protected void showEditableQuotes() {
        for (Quote q : quotes)
            if (q.permissions().w() || q.permissions().d())
                addEditableRecord(q);
    }

    protected void addRecord(Quote quote) {
        FXMLLoader loader = new FXMLLoader(rootApp.getClass().getResource("recordTemplate.fxml"));

        try {
            HBox record = loader.load();
            RecordController controller = loader.getController();
            controller.setQuoteLabel(String.format("??%s??", quote.quote()));
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
            controller.setRootApp(rootApp);
            recordContainer.getChildren().add(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        quotes = null;
        loadQuotes();
        updateWithoutParsing();
    }

    protected void updateWithoutParsing() {
        loginLabel.setText(user.getLogin());
        recordContainer.getChildren().clear();
        if (view) {
            showReadableQuotes();
        }
        else {
            showEditableQuotes();
        }
        updateCount();
    }

    public void deleteQuoteEvent(Quote q) {
        for (int i = 0; i < quotes.size(); i++) {
            if (q.id() == quotes.get(i).id())
                quotes.remove(i);
        }
        user.countAdd(false);
        updateWithoutParsing();
    }

    public void editQuoteEvent(Quote q) {
        for (int i = 0; i < quotes.size(); i++) {
            if (q.id() == quotes.get(i).id())
                quotes.set(i, q);
        }
        updateWithoutParsing();
    }

    protected void updateCount() {
        if (!view)
            counterLabel.setVisible(true);
        counterLabel.setText(String.format("count: %d", user.getCount()));
    }
}
