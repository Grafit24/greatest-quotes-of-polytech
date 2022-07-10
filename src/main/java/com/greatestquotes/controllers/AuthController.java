package com.greatestquotes.controllers;

import com.greatestquotes.utils.State;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthController extends BaseController {

    @FXML
    protected TextField loginField;

    @FXML
    protected PasswordField passwordField;

    @FXML
    protected Label messageLabel;

    @Override
    protected void initialize() {
        super.initialize();
        messageLabel.setText("");
    }

    @FXML
    protected void onGuestButtonClick() {
        user.reset();
        System.out.println(user);
        if (user.getRoles().isEmpty())
            messageLabel.setText("No connection.");
        else
            rootApp.showMainWindow();
    }

    @FXML
    protected void onSignUpButtonClick() {
        rootApp.showRegistrationWindow();
    }

    @FXML
    protected void onSignInButtonClick() {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login.length() == 0 || password.length() == 0)
            messageLabel.setText("Please, fill the fields.");

        State state = user.auth(login, password);
        switch (state) {
            case DONE -> {
                user.parseCount();
                rootApp.showMainWindow();
            }
            case NO_ENTRY -> messageLabel.setText("Wrong login or password!");
            default -> messageLabel.setText(state.getText());
        }
    }
}