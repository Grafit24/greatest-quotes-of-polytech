package com.greatestquotes.controllers;

import com.greatestquotes.models.User;
import com.greatestquotes.utils.State;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegController extends BaseController {
    @FXML
    protected TextField loginField;

    @FXML
    protected TextField passwordField;

    @FXML
    protected TextField repeatPasswordField;

    @FXML
    protected Label messageLabel;

    @Override
    protected void initialize() {
        super.initialize();
        messageLabel.setText("");
    }

    @FXML
    protected void onBackButtonClick() {
        rootApp.showAuthWindow();
    }

    @FXML
    protected void onSignUpButtonClick() {
        String login = loginField.getText();
        String password = passwordField.getText();

        boolean loginLengthCondition = login.length() < 256 && login.length() > 5;
        boolean passwordEnterCondition = password.equals(repeatPasswordField.getText());
        boolean notEmpty = login.length() > 0 && password.length() > 0;
        boolean passwordWithoutSpaces = !password.contains(" ") && !login.contains(" ");

        if (!loginLengthCondition)
            messageLabel.setText("Please, use login from 6 to 255 symbols.");
        else if (!passwordEnterCondition)
            messageLabel.setText("Please, repeat password carefully.");
        else if (!notEmpty)
            messageLabel.setText("Please, fill the fields.");
        else if (!passwordWithoutSpaces)
            messageLabel.setText("Please, don't use spaces.");

        if (loginLengthCondition && passwordEnterCondition && notEmpty && passwordWithoutSpaces) {
            State state = User.createNewUser(loginField.getText(), passwordField.getText());
            switch (state) {
                case DONE -> rootApp.showAuthWindow();
                case DUPLICATE -> messageLabel.setText("User with this login already exist! Please, use another login.");
                default -> messageLabel.setText(state.getText());
            }
        }
    }
}
